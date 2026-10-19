import api from './client'

const jobApi = {
  getAllJobs: () => api.get('/jobs'),
  getJobById: (id) => api.get(`/jobs/${id}`),
  createJob: (jobData) => api.post('/admin/jobs', jobData),
  updateJob: (id, jobData) => api.put(`/admin/jobs/${id}`, jobData),
  deleteJob: (id) => api.delete(`/admin/jobs/${id}`),
  searchJobsByTitle: (title) => api.get(`/jobs/search`, { params: { title } }),
  searchJobsByExperienceLevel: (experienceLevel) => api.get(`/jobs/filter/experiencelevel`, { params: { explvl: experienceLevel } }),
  searchJobsBySalary: (salary) => api.get(`/jobs/filter/salary`, { params: { minSalary: salary } }),
  searchJobsByCompany: (company) => api.get(`/jobs/filter/company`, { params: { company } }),
  searchJobsBySkill: (skill) => api.get(`/jobs/filter/skill`, { params: { skill } }),
  getJobsByFilters: (params) => api.get('/jobs/filters', { params }),
}

export default jobApi