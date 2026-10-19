import React from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

// Layouts
import MainLayout from '../layouts/MainLayout'
import AdminLayout from '../layouts/AdminLayout'

// Public Pages
import LandingPage from '../pages/public/LandingPage'
import LoginPage from '../pages/public/LoginPage'
import RegisterPage from '../pages/public/RegisterPage'
import OAuth2Callback from '../pages/public/OAuth2Callback'

// User Pages
import DashboardPage from '../pages/user/DashboardPage'
import JobsPage from '../pages/user/JobsPage'
import JobDetailsPage from '../pages/user/JobDetailsPage'
import MatchesPage from '../pages/user/MatchesPage'
import SkillsPage from '../pages/user/SkillsPage'
import ProfilePage from '../pages/user/ProfilePage'

// Admin Pages
import AdminDashboardPage from '../pages/admin/AdminDashboardPage'
import AdminJobsPage from '../pages/admin/AdminJobsPage'
import AdminSkillsPage from '../pages/admin/AdminSkillsPage'

// Protected Route Component
const ProtectedRoute = ({ children, allowedRoles }) => {
    const { user, loading } = useAuth()

    if (loading) {
        return <div>Loading...</div>
    }

    if (!user) {
        return <Navigate to="/login" replace />
    }

    if (allowedRoles && !allowedRoles.includes(user.role)) {
        return <Navigate to="/dashboard" replace />
    }

    return children
}

// Admin Route Component
const AdminRoute = ({ children }) => {
  return <ProtectedRoute allowedRoles={['ADMIN']}>{children}</ProtectedRoute>
}

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/auth/callback" element={<OAuth2Callback />} />

      {/* Authenticated Routes */}
      <Route element={<MainLayout />}>
        <Route
          path="/dashboard"
          element={(
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          )}
        />
        <Route
          path="/jobs"
          element={(
            <ProtectedRoute>
              <JobsPage />
            </ProtectedRoute>
          )}
        />
        <Route
          path="/jobs/:id"
          element={(
            <ProtectedRoute>
              <JobDetailsPage />
            </ProtectedRoute>
          )}
        />
        <Route
          path="/matches"
          element={(
            <ProtectedRoute>
              <MatchesPage />
            </ProtectedRoute>
          )}
        />
        <Route
          path="/skills"
          element={(
            <ProtectedRoute>
              <SkillsPage />
            </ProtectedRoute>
          )}
        />
        <Route
          path="/profile"
          element={(
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          )}
        />
      </Route>

      {/* Admin Routes */}
      <Route element={<AdminRoute><AdminLayout /></AdminRoute>}>
        <Route path="/admin" element={<AdminDashboardPage />} />
        <Route path="/admin/jobs" element={<AdminJobsPage />} />
        <Route path="/admin/skills" element={<AdminSkillsPage />} />
      </Route>

      {/* Catch-all route */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}

export default AppRoutes