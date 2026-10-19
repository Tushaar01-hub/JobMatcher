import api from './client'

const skillApi = {
  getAllSkills: () => api.get('/skills'),
  getSkillById: (id) => api.get(`/skills/${id}`),
  createSkill: (skillData) => api.post('/admin/skills', skillData),
  updateSkill: (id, skillData) => api.put(`/admin/skills/${id}`, skillData),
  deleteSkill: (id) => api.delete(`/admin/skills/${id}`),
}

export default skillApi