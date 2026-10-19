import React, { useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import LoadingSpinner from '../../components/common/LoadingSpinner'

const OAuth2Callback = () => {
  const { login } = useAuth()
  const { addToast } = useToast()
  const navigate = useNavigate()
  const location = useLocation()

  useEffect(() => {
    const handleOAuthCallback = async () => {
      // Extract token from URL query parameters
      const params = new URLSearchParams(location.search)
      const token = params.get('token')

      if (token) {
        try {
          // Store the token and redirect to dashboard
          login(token)
          addToast('Google login successful!', 'success')
          navigate('/dashboard')
        } catch (error) {
          addToast('Failed to process Google login', 'error')
          navigate('/login')
        }
      } else {
        addToast('Google login failed: No token received', 'error')
        navigate('/login')
      }
    }

    handleOAuthCallback()
  }, [location, login, addToast, navigate])

  return (
    <div className="min-h-screen flex items-center justify-center">
      <LoadingSpinner size="large" />
    </div>
  )
}

export default OAuth2Callback