import api from './client'

const matchApi = {
  getUserMatches: (userId) => api.get(`/users/${userId}/matches`),
}

export default matchApi