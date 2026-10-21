import { createContext, useContext, useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'

const AuthContext = createContext()

const TOKEN_KEY = 'jobmatcher_token'

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(localStorage.getItem(TOKEN_KEY))
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token)

        setUser({
          email: decoded.sub,
          userId: decoded.userId,
          role: decoded.authorities[0].replace('ROLE_', ''),
        })
      } catch (error) {
        console.error('Invalid token', error)
        logout()
      }
    }

    setLoading(false)
  }, [token])

  const login = (newToken) => {
    localStorage.setItem(TOKEN_KEY, newToken)
    setToken(newToken)

    try {
      const decoded = jwtDecode(newToken)

      setUser({
        email: decoded.sub,
        userId: decoded.userId,
        role: decoded.authorities[0].replace('ROLE_', ''),
      })
    } catch (error) {
      console.error('Invalid token', error)
    }
  }

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY)
    setToken(null)
    setUser(null)
    navigate('/login')
  }

  const register = (userData) => {
    // Registration logic would go here
    // For now, we'll just simulate it
    console.log('Registering user:', userData)
  }

  return (
      <AuthContext.Provider
          value={{ user, token, loading, login, logout, register }}
      >
      {children}
    </AuthContext.Provider>
  )
}

const useAuth = () => {
  return useContext(AuthContext)
}

export { AuthProvider, useAuth }