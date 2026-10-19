import api from './client'

const authApi = {
  register: (userData) => api.post('/auth/register', userData),
  login: (credentials) => api.post('/auth/login', credentials),
  googleLogin: (token) => api.post('/auth/google', { token }),
  // For OAuth2 callback handling
  getUserFromToken: (token) => {
    return api.get('/auth/user', {
      params: { token },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  },
}

export default authApi