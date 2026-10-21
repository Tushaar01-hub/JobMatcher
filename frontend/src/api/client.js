import axios from 'axios'
import { jwtDecode } from 'jwt-decode'
// import { useAuth } from '../context/AuthContext'

// Create axios instance
const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
})

// Add request interceptor for JWT
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('jobmatcher_token')

    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
}, (error) => {
    return Promise.reject(error)
})

// Add response interceptor for 401 errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
      if (
          error.response &&
          error.response.status === 401 &&
          !error.config.url.includes('/auth/login')
      ) {
          localStorage.removeItem('jobmatcher_token')
          window.location.href = '/login'
      }
    return Promise.reject(error)
  }
)

export default api