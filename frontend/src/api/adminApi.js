import api from './client'

const adminApi = {
  getAllUsers: () => api.get('/users'),
  getAllJobs: () => api.get('/jobs'),
  // Admin endpoints are already covered by skillApi and jobApi
}

export default adminApi