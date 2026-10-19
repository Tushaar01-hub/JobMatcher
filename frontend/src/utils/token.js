export const decodeToken = (token) => {
  try {
    return jwtDecode(token)
  } catch (error) {
    console.error('Failed to decode token:', error)
    return null
  }
}

export const getToken = () => {
  return localStorage.getItem('jobmatcher_token')
}

export const setToken = (token) => {
  localStorage.setItem('jobmatcher_token', token)
}

export const removeToken = () => {
  localStorage.removeItem('jobmatcher_token')
}