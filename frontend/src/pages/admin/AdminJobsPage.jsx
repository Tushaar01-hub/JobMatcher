import React, { useState, useEffect } from 'react'
import { useToast } from '../../context/ToastContext'
import { Plus, Trash2, Edit } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Modal from '../../components/common/Modal'
import jobApi from '../../api/jobApi'
import skillApi from '../../api/skillApi'

const AdminJobsPage = () => {
  const { addToast } = useToast()
  const [jobs, setJobs] = useState([])
  const [skills, setSkills] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [currentJob, setCurrentJob] = useState(null)
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    company: '',
    salary: '',
    experienceLevel: 'FRESHER',
    skillIds: [],
  })

  useEffect(() => {
    fetchData()
  }, [])

  const fetchData = async () => {
    try {
      setIsLoading(true)
      const [jobsResponse, skillsResponse] = await Promise.all([
        jobApi.getAllJobs(),
        skillApi.getAllSkills(),
      ])
      setJobs(jobsResponse.data)
      setSkills(skillsResponse.data)
    } catch (err) {
      setError(err.response?.data || 'Failed to load jobs')
      addToast('Failed to load jobs', 'error')
    } finally {
      setIsLoading(false)
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this job?')) return
    try {
      await jobApi.deleteJob(id)
      setJobs(jobs.filter((job) => job.id !== id))
      addToast('Job deleted successfully', 'success')
    } catch (err) {
      addToast(
          typeof err.response?.data === 'string'
              ? err.response.data
              : err.response?.data?.error || 'Failed to delete job',
          'error'
      )
    }
  }

  const handleOpenModal = (job = null) => {
    if (job) {
      setCurrentJob(job)
      setFormData({
        title: job.title,
        description: job.description,
        company: job.company,
        salary: job.salary,
        experienceLevel: job.experienceLevel,
        skillIds: job.skills ? job.skills.map(s => typeof s === 'object' ? s.id : s) : [],
      })
    } else {
      setCurrentJob(null)
      setFormData({
        title: '',
        description: '',
        company: '',
        salary: '',
        experienceLevel: 'FRESHER',
        skillIds: [],
      })
    }
    setIsModalOpen(true)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const payload = {
        ...formData,
        salary: Number(formData.salary),
        skillIds: formData.skillIds.map(Number),
      }

      if (currentJob) {
        await jobApi.updateJob(currentJob.id, payload)
        addToast('Job updated successfully', 'success')
      } else {
        await jobApi.createJob(payload)
        addToast('Job created successfully', 'success')
      }
      setIsModalOpen(false)
      fetchData()
    } catch (err) {
      const data = err.response?.data

      const message =
          typeof data === 'string'
              ? data
              : data?.message ||
              data?.error ||
              Object.values(data || {})[0] ||
              'Failed to save job'

      addToast(message, 'error')
    }
  }

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header className="flex justify-between items-center">
        <div>
          <h1 className="text-h1">Manage Jobs</h1>
          <p className="text-body text-slate-500 mt-2">Create and manage your job listings.</p>
        </div>
        <Button onClick={() => handleOpenModal()}>
          <Plus className="h-4 w-4 mr-2" />
          Add Job
        </Button>
      </header>

      {jobs.length > 0 ? (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-slate-200">
              <thead className="bg-slate-50">
                <tr>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-slate-500 uppercase tracking-wider">Title</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-slate-500 uppercase tracking-wider">Company</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-slate-500 uppercase tracking-wider">Salary</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-slate-500 uppercase tracking-wider">Level</th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-slate-500 uppercase tracking-wider">Actions</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-slate-200">
                {jobs.map((job) => (
                  <tr key={job.id} className="hover:bg-slate-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-slate-900">{job.title}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-slate-600">{job.company}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-slate-600">${job.salary?.toLocaleString()}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-slate-600">{job.experienceLevel}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-3">
                      <button onClick={() => handleOpenModal(job)} className="text-indigo-600 hover:text-indigo-900">
                        <Edit className="h-4 w-4" />
                      </button>
                      <button onClick={() => handleDelete(job.id)} className="text-rose-600 hover:text-rose-900">
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <EmptyState title="No jobs found" message="Create your first job listing to get started." />
      )}

      <Modal
        open={isModalOpen}
        onOpenChange={setIsModalOpen}
        title={currentJob ? 'Edit Job' : 'Create Job'}
        description="Enter the details of the job listing below."
        size="lg"
      >
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Title"
            value={formData.title}
            onChange={(e) => setFormData({ ...formData, title: e.target.value })}
            required
          />
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Description (10-300 chars)</label>
            <textarea
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              rows="3"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              required
            />
          </div>
          <Input
            label="Company"
            value={formData.company}
            onChange={(e) => setFormData({ ...formData, company: e.target.value })}
            required
          />
          <Input
            label="Salary"
            type="number"
            value={formData.salary}
            onChange={(e) => setFormData({ ...formData, salary: e.target.value })}
            required
          />
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Experience Level</label>
            <select
              className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 bg-white"
              value={formData.experienceLevel}
              onChange={(e) => setFormData({ ...formData, experienceLevel: e.target.value })}
            >
              <option value="FRESHER">Fresher</option>
              <option value="ZERO_TO_ONE">0-1 years</option>
              <option value="TWO_TO_FIVE">2-5 years</option>
              <option value="FIVE_TO_TEN">5-10 years</option>
              <option value="TEN_PLUS">10+ years</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-2">Required Skills</label>
            <div className="max-h-40 overflow-y-auto border border-slate-300 p-3 rounded-lg space-y-2">
              {skills.map((skill) => (
                <label key={skill.id} className="flex items-center space-x-2 text-sm cursor-pointer hover:bg-slate-50 p-1 rounded">
                  <input
                    type="checkbox"
                    className="rounded border-slate-300 text-indigo-600 focus:ring-indigo-500"
                    checked={formData.skillIds.includes(skill.id)}
                    onChange={(e) => {
                      const id = skill.id
                      setFormData({
                        ...formData,
                        skillIds: e.target.checked
                          ? [...formData.skillIds, id]
                          : formData.skillIds.filter((sId) => sId !== id),
                      })
                    }}
                  />
                  <span>{skill.name}</span>
                </label>
              ))}
            </div>
          </div>
          <div className="flex justify-end space-x-3 pt-6">
            <Button type="button" variant="secondary" onClick={() => setIsModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit">Save</Button>
          </div>
        </form>
      </Modal>
    </div>
  )
}

export default AdminJobsPage