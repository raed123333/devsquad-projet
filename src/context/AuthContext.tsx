import React, { createContext, useContext, useEffect, useState } from 'react';
import { getDecodedToken, getRole, getToken, isTokenValid, removeRole, removeToken, setRole, setToken } from '../lib/jwt';
import api from '../lib/axios';

interface User {
  role: string;
}

interface AuthContextType {
  isAuthenticated: boolean;
  user: User | null;
  login?: (token: string, role: string) => void;
  logout: () => void;
  checkAuth: () => boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<User | null>(null);

  const checkAuth = () => {
    const token = getToken();
    if (!token || !isTokenValid(token)) {
      handleLogout();
      return false;
    }
    return true;
  };

  const handleLogout = () => {
    removeToken();
    removeRole();
    setUser(null);
    setIsAuthenticated(false);
    api.defaults.headers.common['Authorization'] = '';
  };

  const initializeAuth = () => {
    const token = getToken();
    const role = getRole();
    if (token && isTokenValid(token) && role) {
      setUser({ role });
      setIsAuthenticated(true);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      handleLogout();
    }
  };

  useEffect(() => {
    initializeAuth();
  }, []);

  // Refresh token check every 5 minutes
  useEffect(() => {
    const interval = setInterval(() => {
      if (isAuthenticated && !checkAuth()) {
        handleLogout();
      }
    }, 5 * 60 * 1000);

    return () => clearInterval(interval);
  }, [isAuthenticated]);

  const login = (token: string, role: string) => {
    setToken(token);
    setRole(role);

    const decoded = getDecodedToken();
    if (decoded) {
      setUser({ role });
      setIsAuthenticated(true);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    } else {
      handleLogout();
    }
  };

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        user,
        login,
        logout: handleLogout,
        checkAuth,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
