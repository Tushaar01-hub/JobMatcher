import api from './client'

const userApi = {
  getAllUsers: () => api.get('/users'),

  getUserProfile: (userId) => api.get(`/users/${userId}`),
  updateUserProfile: (userId, userData) => api.put(`/users/${userId}`, userData),
  getUserSkills: (userId) => api.get(`/users/${userId}/skills`),
  addSkillToUser: (userId, skillId) => api.post(`/users/${userId}/skills/${skillId}`),
  removeSkillFromUser: (userId, skillId) => api.delete(`/users/${userId}/skills/${skillId}`),
}

export default userApi