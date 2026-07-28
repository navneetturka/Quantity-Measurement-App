import axios from "axios";

// Two backends now instead of one: auth-service issues/validates login,
// quantity-service does the actual measurement work. Each gets its own
// base URL and its own axios instance.
export const AUTH_BASE_URL =
  import.meta.env.VITE_AUTH_BASE_URL || "http://localhost:8081";

export const QUANTITY_BASE_URL =
  import.meta.env.VITE_QUANTITY_BASE_URL || "http://localhost:8082";

export const TOKEN_KEY = "qm_token";
export const USER_KEY = "qm_user";

// Both services accept the same JWT (they share app.jwt.secret), so both
// clients attach it and both bounce to /login on a 401 the same way.
function createApiClient(baseURL) {
  const client = axios.create({ baseURL });

  client.interceptors.request.use((config) => {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  });

  client.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response && error.response.status === 401) {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_KEY);
        if (window.location.pathname !== "/login") {
          window.location.href = "/login";
        }
      }
      return Promise.reject(error);
    }
  );

  return client;
}

export const authApi = createApiClient(AUTH_BASE_URL);
export const quantityApi = createApiClient(QUANTITY_BASE_URL);
