import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './auth/Login';
import AdminDashboard from './pages/admin/AdminDashboard';
import Profile from './auth/Profile';
import HRDashboard from './pages/hr/HRDashboard';
import { AuthProvider, useAuth } from './context/AuthContext';
import Register from './auth/Register';
import ForgotPasswordPage from './pages/password/ForgotPasswordPage';
import ResetPasswordPage from './pages/password/ResetPasswordPage';

// PrivateRoute Component
const PrivateRoute = ({ element, requiredRole }: { element: JSX.Element; requiredRole?: string }) => {
  const { isAuthenticated, user } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && user?.role !== requiredRole) {
    return <Navigate to="/login" replace />;
  }

  return element;
};

const App: React.FC = () => {
  const { isAuthenticated, user, checkAuth } = useAuth();

  useEffect(() => {
    checkAuth(); // Ensure the auth status is up-to-date on mount
  }, [checkAuth]);

  return (
    <Router>
      <Routes>
        {/* Public route for login */}
        <Route
          path="/login"
          element={isAuthenticated ? <Navigate to="/" replace /> : <Login />}
        />
  
        {/* Public route for register */}
        <Route
          path="/register"
          element={isAuthenticated ? <Navigate to="/" replace /> : <Register />}
        />
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/reset-password" element={<ResetPasswordPage />} />
  
        {/* Role-based home redirection */}
        <Route
          path="/"
          element={
            isAuthenticated ? (
              user?.role === 'ADMIN' ? (
                <Navigate to="/admin" replace />
              ) : user?.role === 'EMPLOYEE' ? (
                <Navigate to="/profile" replace />
              ) : (
                <Navigate to="/rhdashboard" replace />
              )
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
  
        {/* Protected routes */}
        <Route
          path="/admin"
          element={<PrivateRoute element={<AdminDashboard />} requiredRole="ADMIN" />}
        />
        <Route
          path="/profile"
          element={<PrivateRoute element={<Profile />}  />}
        />
        <Route
          path="/rhdashboard"
          element={<PrivateRoute element={<HRDashboard />} requiredRole="RH" />}
        />
  
        {/* Catch-all route */}
        <Route
          path="*"
          element={
            isAuthenticated ? (
              user?.role === 'ADMIN' ? (
                <Navigate to="/admin" replace />
              ) : user?.role === 'EMPLOYEE' ? (
                <Navigate to="/profile" replace />
              ) : (
                <Navigate to="/rhdashboard" replace />
              )
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
      </Routes>
    </Router>
  );
};

// Wrap the entire app in AuthProvider
const Root: React.FC = () => (
  <AuthProvider>
    <App />
  </AuthProvider>
);

export default Root;
