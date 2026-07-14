/* History iframe logic. Runs same-origin as the parent app, so it shares
   localStorage and can read the JWT the parent stored after Google login. */

const API_BASE = "";
const TOKEN_KEY = "qm_token";

const tabs = document.getElementById("histTabs");
const statusEl = document.getElementById("histStatus");
const scrollEl = document.getElementById("histScroll");

let currentOp = "ADD";

function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

tabs.addEventListener("click", (e) => {
  const btn = e.target.closest(".hist-tab");
  if (!btn) return;
  [...tabs.children].forEach((c) => c.classList.remove("active"));
  btn.classList.add("active");
  currentOp = btn.dataset.op;
  load();
});

async function load() {
  const token = getToken();
  if (!token) {
    statusEl.textContent = "Sign in on the main page to see history.";
    scrollEl.innerHTML = "";
    return;
  }

  const path =
    currentOp === "__errors__"
      ? "/api/v1/quantities/history/errored"
      : `/api/v1/quantities/history/operation/${currentOp}`;

  statusEl.textContent = "Loading…";

  try {
    const res = await fetch(`${API_BASE}${path}`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (res.status === 401) {
      statusEl.textContent = "Session expired. Sign in again on the main page.";
      scrollEl.innerHTML = "";
      return;
    }

    const data = await res.json();
    render(data);
  } catch (err) {
    statusEl.textContent = "Could not load history.";
    scrollEl.innerHTML = "";
  }
}

function render(items) {
  if (!items || items.length === 0) {
    statusEl.textContent = "";
    scrollEl.innerHTML = '<p class="hist-empty">No records yet.</p>';
    return;
  }

  statusEl.textContent = `${items.length} record${items.length === 1 ? "" : "s"}`;

  const rows = items
    .slice()
    .reverse()
    .map((it) => {
      const errClass = it.error ? "err" : "";
      const result = it.error
        ? it.errorMessage || "Error"
        : `${it.resultString ?? it.resultValue ?? ""} ${it.resultUnit ?? ""}`.trim();
      return `
        <tr class="${errClass}">
          <td>${escapeHtml(it.operation ?? "")}</td>
          <td>${escapeHtml(String(it.thisValue))} ${escapeHtml(it.thisUnit ?? "")}</td>
          <td>${escapeHtml(String(it.thatValue))} ${escapeHtml(it.thatUnit ?? "")}</td>
          <td>${escapeHtml(result)}</td>
        </tr>`;
    })
    .join("");

  scrollEl.innerHTML = `
    <table class="hist-table">
      <thead>
        <tr><th>Op</th><th>This</th><th>That</th><th>Result</th></tr>
      </thead>
      <tbody>${rows}</tbody>
    </table>`;
}

function escapeHtml(str) {
  return String(str)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;");
}

window.addEventListener("message", (event) => {
  if (event.origin !== window.location.origin) return;
  if (event.data && event.data.type === "qm-refresh") {
    load();
  }
});

load();
