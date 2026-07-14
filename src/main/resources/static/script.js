/* =========================================================================
   Quantity Measurement — frontend app.js
   Talks to the Spring Boot backend (JWT bearer auth issued after Google
   OAuth2 login). Designed to be served from the SAME origin as the backend
   (src/main/resources/static), so API_BASE can stay empty. If you serve
   this frontend from a different origin/port, set API_BASE to the
   backend's origin, e.g. "http://localhost:8080".
   ========================================================================= */

const API_BASE = "";
const TOKEN_KEY = "qm_token";
const USER_KEY = "qm_user";

/* ---------------------------------------------------------------------- */
/* Unit catalogs — must match the backend enums exactly                    */
/* ---------------------------------------------------------------------- */
const UNITS = {
  LengthUnit: [
    { value: "FEET", label: "Feet" },
    { value: "INCHES", label: "Inches" },
    { value: "YARDS", label: "Yards" },
    { value: "CENTIMETERS", label: "Centimeters" },
    { value: "METER", label: "Meter" },
    { value: "METERS", label: "Meters" },
  ],
  WeightUnit: [
    { value: "KILOGRAM", label: "Kilogram" },
    { value: "GRAM", label: "Gram" },
    { value: "POUND", label: "Pound" },
    { value: "OUNCE", label: "Ounce" },
    { value: "TONNE", label: "Tonne" },
  ],
  TemperatureUnit: [
    { value: "CELSIUS", label: "Celsius" },
    { value: "FAHRENHEIT", label: "Fahrenheit" },
    { value: "KELVIN", label: "Kelvin" },
  ],
  VolumeUnit: [
    { value: "LITRE", label: "Litre" },
    { value: "MILLILITRE", label: "Millilitre" },
    { value: "GALLON", label: "Gallon" },
  ],
};

const OPERATORS = [
  { symbol: "+", op: "ADD", label: "Addition" },
  { symbol: "−", op: "SUBTRACT", label: "Subtraction" },
  { symbol: "÷", op: "DIVIDE", label: "Division" },
];

/* ---------------------------------------------------------------------- */
/* State                                                                   */
/* ---------------------------------------------------------------------- */
const state = {
  type: "LengthUnit",
  action: "compare", // compare | convert | arithmetic
  operatorIndex: 0,
};

let debounceTimer = null;

/* ---------------------------------------------------------------------- */
/* DOM refs                                                                 */
/* ---------------------------------------------------------------------- */
const el = (id) => document.getElementById(id);

const loginBtn = el("loginBtn");
const logoutBtn = el("logoutBtn");
const userChip = el("userChip");
const userAvatar = el("userAvatar");
const userName = el("userName");
const signinGate = el("signinGate");
const appBody = el("appBody");

const typeGrid = el("typeGrid");
const actionRow = el("actionRow");

const fromToForm = el("fromToForm");
const arithmeticForm = el("arithmeticForm");

const fromLabel = el("fromLabel");
const toLabel = el("toLabel");
const fromValue = el("fromValue");
const toValue = el("toValue");
const fromUnit = el("fromUnit");
const toUnit = el("toUnit");
const fromError = el("fromError");
const toError = el("toError");

const value1 = el("value1");
const value2 = el("value2");
const unit1 = el("unit1");
const unit2 = el("unit2");
const value1Error = el("value1Error");
const value2Error = el("value2Error");
const operatorBtn = el("operatorBtn");

const resultPanel = el("resultPanel");
const resultLabel = el("resultLabel");
const resultValue = el("resultValue");
const resultUnit = el("resultUnit");

const historyToggle = el("historyToggle");
const historyWrap = el("historyWrap");
const historyChev = el("historyChev");
const historyFrame = el("historyFrame");

const apiHint = el("apiHint");

/* ---------------------------------------------------------------------- */
/* Auth                                                                     */
/* ---------------------------------------------------------------------- */
function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

function getUser() {
  try {
    return JSON.parse(localStorage.getItem(USER_KEY) || "null");
  } catch {
    return null;
  }
}

function setSession(data) {
  localStorage.setItem(TOKEN_KEY, data.token);
  localStorage.setItem(
      USER_KEY,
      JSON.stringify({ name: data.name, email: data.email, picture: data.picture })
  );
}

function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

function refreshAuthUI() {
  const token = getToken();
  const user = getUser();

  if (token && user) {
    loginBtn.classList.add("hidden");
    userChip.classList.remove("hidden");
    userAvatar.src = user.picture || "";
    userName.textContent = user.name || user.email || "Signed in";
    signinGate.classList.add("hidden");
    appBody.classList.remove("hidden");
  } else {
    loginBtn.classList.remove("hidden");
    userChip.classList.add("hidden");
    signinGate.classList.remove("hidden");
    appBody.classList.add("hidden");
  }
}

function openGoogleLoginPopup() {
  const width = 480;
  const height = 620;
  const left = window.screenX + (window.outerWidth - width) / 2;
  const top = window.screenY + (window.outerHeight - height) / 2;

  const popup = window.open(
      `${API_BASE}/api/auth/login`,
      "qm-google-login",
      `width=${width},height=${height},left=${left},top=${top}`
  );

  if (!popup) {
    alert("Please allow pop-ups for this site to sign in with Google.");
    return;
  }

  const poll = setInterval(() => {
    if (popup.closed) {
      clearInterval(poll);
      return;
    }
    // Only readable once the popup navigates back to our own origin
    // (Google's pages are cross-origin and throw on access — that's expected).
    let sameOrigin = false;
    try {
      sameOrigin = popup.location.origin === window.location.origin;
    } catch {
      sameOrigin = false;
    }

    if (sameOrigin) {
      try {
        const text = popup.document.body.innerText.trim();
        const data = JSON.parse(text);
        if (data && data.token) {
          setSession(data);
          clearInterval(poll);
          popup.close();
          refreshAuthUI();
          notifyHistoryFrame();
        }
      } catch {
        // page landed on our origin but isn't the JSON payload yet — keep polling
      }
    }
  }, 400);
}

function handleUnauthorized() {
  clearSession();
  refreshAuthUI();
  showResult(true, "Your session expired. Please sign in again.");
}

loginBtn.addEventListener("click", openGoogleLoginPopup);
logoutBtn.addEventListener("click", () => {
  clearSession();
  refreshAuthUI();
  resultPanel.classList.add("hidden");
});

/* ---------------------------------------------------------------------- */
/* API helper                                                               */
/* ---------------------------------------------------------------------- */
async function apiPost(path, body) {
  const res = await fetch(`${API_BASE}${path}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${getToken()}`,
    },
    body: JSON.stringify(body),
  });

  if (res.status === 401) {
    handleUnauthorized();
    throw new Error("Unauthorized");
  }

  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    const message = data.message || data.error || "Something went wrong.";
    throw new Error(message);
  }
  return data;
}

/* ---------------------------------------------------------------------- */
/* Unit dropdown population                                                 */
/* ---------------------------------------------------------------------- */
function populateUnitSelect(select, type, preferredValue) {
  const options = UNITS[type];
  select.innerHTML = options
      .map((u) => `<option value="${u.value}">${u.label}</option>`)
      .join("");
  if (preferredValue && options.some((u) => u.value === preferredValue)) {
    select.value = preferredValue;
  }
}

function refreshAllUnitSelects() {
  populateUnitSelect(fromUnit, state.type);
  populateUnitSelect(toUnit, state.type);
  populateUnitSelect(unit1, state.type);
  populateUnitSelect(unit2, state.type);
  populateUnitSelect(resultUnit, state.type);
  // sensible default second unit if list has more than one option
  const opts = UNITS[state.type];
  if (opts.length > 1) {
    toUnit.value = opts[1].value;
    unit2.value = opts[1].value;
  }
  resultUnit.value = unit1.value;
}

/* ---------------------------------------------------------------------- */
/* Type / action selection                                                  */
/* ---------------------------------------------------------------------- */
typeGrid.addEventListener("click", (e) => {
  const btn = e.target.closest(".type-card");
  if (!btn) return;
  [...typeGrid.children].forEach((c) => c.classList.remove("active"));
  btn.classList.add("active");
  state.type = btn.dataset.type;
  refreshAllUnitSelects();
  updateArithmeticAvailability();
  clearErrors();
  scheduleCompute();
});

actionRow.addEventListener("click", (e) => {
  const btn = e.target.closest(".action-btn");
  if (!btn) return;
  [...actionRow.children].forEach((c) => c.classList.remove("active"));
  btn.classList.add("active");
  state.action = btn.dataset.action;
  syncFormVisibility();
  clearErrors();
  scheduleCompute();
});

function syncFormVisibility() {
  const isArithmetic = state.action === "arithmetic";
  const isConvert = state.action === "convert";
  fromToForm.classList.toggle("hidden", isArithmetic);
  arithmeticForm.classList.toggle("hidden", !isArithmetic);

  fromLabel.textContent = "From";

  // Conversion only needs From Value + From Unit + To Unit — no To Value.
  toValue.classList.toggle("hidden", isConvert);
  toValue.parentElement.classList.toggle("no-value", isConvert);
  toError.classList.toggle("hidden", isConvert);

  if (isConvert) {
    toLabel.textContent = "To Unit";
    toValue.value = "";
  } else {
    toLabel.textContent = "To";
  }

  clearErrors();
  resultPanel.classList.add("hidden");
}

function updateArithmeticAvailability() {
  const isTemp = state.type === "TemperatureUnit";
  if (isTemp && OPERATORS[state.operatorIndex].op === "DIVIDE") {
    state.operatorIndex = 0;
    operatorBtn.textContent = OPERATORS[0].symbol;
  }
}

/* ---------------------------------------------------------------------- */
/* Operator cycling (arithmetic)                                            */
/* ---------------------------------------------------------------------- */
operatorBtn.addEventListener("click", () => {
  let next = (state.operatorIndex + 1) % OPERATORS.length;
  if (state.type === "TemperatureUnit" && OPERATORS[next].op === "DIVIDE") {
    next = (next + 1) % OPERATORS.length;
  }
  state.operatorIndex = next;
  operatorBtn.textContent = OPERATORS[next].symbol;
  operatorBtn.title = OPERATORS[next].label;
  toggleResultUnitVisibility();
  scheduleCompute();
});

function toggleResultUnitVisibility() {
  const isDivide = OPERATORS[state.operatorIndex].op === "DIVIDE";
  resultUnit.classList.toggle("hidden", isDivide && state.action === "arithmetic");
}

resultUnit.addEventListener("change", scheduleCompute);

/* ---------------------------------------------------------------------- */
/* Validation                                                               */
/* ---------------------------------------------------------------------- */
function clearErrors() {
  [fromError, toError, value1Error, value2Error].forEach((n) => (n.textContent = ""));
  [fromValue, toValue, value1, value2].forEach((n) =>
      n.parentElement.classList.remove("invalid")
  );
}

function validateNumberField(input, errorNode, { allowNegative }) {
  const raw = input.value.trim();
  input.parentElement.classList.remove("invalid");
  errorNode.textContent = "";

  if (raw === "") {
    input.parentElement.classList.add("invalid");
    errorNode.textContent = "Required";
    return null;
  }
  const num = Number(raw);
  if (Number.isNaN(num)) {
    input.parentElement.classList.add("invalid");
    errorNode.textContent = "Enter a valid number";
    return null;
  }
  if (!allowNegative && num < 0) {
    input.parentElement.classList.add("invalid");
    errorNode.textContent = "Must be zero or positive";
    return null;
  }
  return num;
}

function validateCurrentForm() {
  const allowNegative = state.type === "TemperatureUnit";

  if (state.action === "arithmetic") {
    const v1 = validateNumberField(value1, value1Error, { allowNegative });
    const v2 = validateNumberField(value2, value2Error, { allowNegative });
    return v1 === null || v2 === null ? null : { v1, v2 };
  } else if (state.action === "convert") {
    const v1 = validateNumberField(fromValue, fromError, { allowNegative });
    // No "To Value" field on the Conversion screen — the target unit's
    // value is irrelevant to the backend conversion, so we default it to 0.
    return v1 === null ? null : { v1, v2: 0 };
  } else {
    const v1 = validateNumberField(fromValue, fromError, { allowNegative });
    const v2 = validateNumberField(toValue, toError, { allowNegative });
    return v1 === null || v2 === null ? null : { v1, v2 };
  }
}

/* ---------------------------------------------------------------------- */
/* Compute (debounced, auto-runs as the user types / changes selections)   */
/* ---------------------------------------------------------------------- */
[fromValue, toValue, value1, value2].forEach((input) =>
    input.addEventListener("input", scheduleCompute)
);
[fromUnit, toUnit, unit1, unit2].forEach((select) =>
    select.addEventListener("change", scheduleCompute)
);

function scheduleCompute() {
  clearTimeout(debounceTimer);
  debounceTimer = setTimeout(runCompute, 350);
}

function makeQuantityDTO(value, unit) {
  return { value, unit, measurementType: state.type };
}

async function runCompute() {
  if (!getToken()) return;
  const values = validateCurrentForm();
  if (!values) {
    resultPanel.classList.add("hidden");
    return;
  }

  try {
    if (state.action === "compare") {
      const dto = await apiPost(`${API_BASE}/api/v1/quantities/compare`, {
        thisQuantityDTO: makeQuantityDTO(values.v1, fromUnit.value),
        thatQuantityDTO: makeQuantityDTO(values.v2, toUnit.value),
      });
      const equal = dto.resultString === "true";
      showResult(dto.error, dto.error ? dto.errorMessage : equal ? "Equal ✓" : "Not Equal ✗");
    } else if (state.action === "convert") {
      const dto = await apiPost(`${API_BASE}/api/v1/quantities/convert`, {
        thisQuantityDTO: makeQuantityDTO(values.v1, fromUnit.value),
        thatQuantityDTO: makeQuantityDTO(values.v2, toUnit.value),
      });
      if (dto.error) {
        showResult(true, dto.errorMessage);
      } else {
        showResult(false, formatNumber(dto.resultValue), dto.resultUnit);
      }
    } else {
      const op = OPERATORS[state.operatorIndex].op;
      const target = makeQuantityDTO(values.v2, resultUnit.value || unit1.value);
      let path = "/api/v1/quantities/add-with-target-unit";
      if (op === "SUBTRACT") path = "/api/v1/quantities/subtract-with-target-unit";
      if (op === "DIVIDE") path = "/api/v1/quantities/divide";

      const dto = await apiPost(`${API_BASE}${path}`, {
        thisQuantityDTO: makeQuantityDTO(values.v1, unit1.value),
        thatQuantityDTO: makeQuantityDTO(values.v2, unit2.value),
        targetQuantityDTO: op === "DIVIDE" ? undefined : target,
      });

      if (dto.error) {
        showResult(true, dto.errorMessage);
      } else if (op === "DIVIDE") {
        showResult(false, formatNumber(dto.resultValue), null, "Ratio");
      } else {
        showResult(false, formatNumber(dto.resultValue), dto.resultUnit);
      }
    }
    notifyHistoryFrame();
  } catch (err) {
    if (err.message !== "Unauthorized") {
      showResult(true, err.message);
    }
  }
}

function formatNumber(n) {
  if (typeof n !== "number") return n;
  const rounded = Math.round(n * 10000) / 10000;
  return String(rounded);
}

function showResult(isError, text, unitValue, labelOverride) {
  resultPanel.classList.remove("hidden");
  resultPanel.classList.toggle("is-error", !!isError);
  resultLabel.textContent = labelOverride || (isError ? "Error" : "Result");
  resultValue.textContent = text;

  if (!isError && unitValue) {
    resultUnit.classList.remove("hidden");
    if ([...resultUnit.options].some((o) => o.value === unitValue)) {
      resultUnit.value = unitValue;
    }
  } else {
    resultUnit.classList.add("hidden");
  }
}

/* ---------------------------------------------------------------------- */
/* History iframe sync                                                      */
/* ---------------------------------------------------------------------- */
function notifyHistoryFrame() {
  if (historyFrame.contentWindow) {
    historyFrame.contentWindow.postMessage({ type: "qm-refresh" }, window.location.origin);
  }
}

historyToggle.addEventListener("click", () => {
  const isOpen = !historyWrap.classList.contains("hidden");
  historyWrap.classList.toggle("hidden", isOpen);
  historyChev.classList.toggle("open", !isOpen);
  if (!isOpen) notifyHistoryFrame();
});

/* ---------------------------------------------------------------------- */
/* Init                                                                      */
/* ---------------------------------------------------------------------- */
function init() {
  refreshAuthUI();
  refreshAllUnitSelects();
  syncFormVisibility();
  toggleResultUnitVisibility();
  apiHint.textContent =
      API_BASE === ""
          ? "Served from the same origin as the API."
          : `API base: ${API_BASE}`;
}

init();