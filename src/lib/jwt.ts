import {jwtDecode} from 'jwt-decode';

interface JWTPayload {
  sub: string;
  exp: number;
}

interface JWTPayload {
  sub: string; // User identifier (e.g., email)
  exp: number; // Token expiration time (in seconds since epoch)
}

export const getToken = () => localStorage.getItem('token');
export const setToken = (token: string) => localStorage.setItem('token', token);
export const removeToken = () => localStorage.removeItem('token');

export const getRole = (): string | null => localStorage.getItem('role');
export const setRole = (role: string) => localStorage.setItem('role', role);
export const removeRole = () => localStorage.removeItem('role');

export const getUsername = (): string | null => localStorage.getItem('username');
export const setUsername = (username: string) => localStorage.setItem('username', username);
export const removeUsername = () => localStorage.removeItem('username');

export const isTokenValid = (token: string): boolean => {
  try {
    const decoded = jwtDecode<JWTPayload>(token);
    return decoded.exp * 1000 > Date.now(); // Convert expiration to milliseconds
  } catch {
    return false;
  }
};

export const getDecodedToken = (): JWTPayload | null => {
  const token = getToken();
  if (!token) return null;

  try {
    return jwtDecode<JWTPayload>(token);
  } catch {
    return null;
  }
};
export const getEmailFromToken = (): string | null => {
  const decoded = getDecodedToken();
  return decoded?.sub || null;
};
