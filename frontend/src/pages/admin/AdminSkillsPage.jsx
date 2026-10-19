import React, { useState, useEffect } from 'react'
import { useToast } from '../../context/ToastContext'
import { Plus, Trash2, Edit } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import skillApi from '../../api/skillApi'

const AdminSkillsPage = () => {
  const { addToast } = useToast()
  const [skills, setSkills] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [currentSkill, setCurrentSkill] = useState(null)
  const [formData, setFormData] = useState({
    name: '',
  })

  useEffect(() => {
    fetchSkills()
  }, [])

  const fetchSkills = async () => {
    try {
      setIsLoading(true)
      const response = await skillApi.getAllSkills()
      setSkills(response.data)
    } catch (err) {
      setError(err.response?.data || 'Failed to load skills')
      addToast('Failed to load skills', 'error')
    } finally {
      setIsLoading(false)
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this skill?')) return
    try {
      await skillApi.deleteSkill(id)
      setSkills(skills.filter((skill) => skill.id !== id))
      addToast('Skill deleted successfully', 'success')
    } catch (err) {
      addToast(
          typeof err.response?.data === 'string'
              ? err.response.data
              : err.response?.data?.error || 'Failed to delete skill',
          'error'
      )
    }
  }

  const handleOpenModal = (skill = null) => {
    if (skill) {
      setCurrentSkill(skill)
      setFormData({ name: skill.name })
    } else {
      setCurrentSkill(null)
      setFormData({ name: '' })
    }
    setIsModalOpen(true)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      if (currentSkill) {
        await skillApi.updateSkill(currentSkill.id, formData)
        addToast('Skill updated successfully', 'success')
      } else {
        await skillApi.createSkill(formData)
        addToast('Skill created successfully', 'success')
      }
      setIsModalOpen(false)
      fetchSkills()
    } catch (err) {
      addToast(err.response?.data || 'Failed to save skill', 'error')
    }
  }

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Manage Skills</h1>
        <Button onClick={() => handleOpenModal()}>
          <Plus className="h-4 w-4 mr-1" />
          Add Skill
        </Button>
      </div>

      {skills.length > 0 ? (
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {skills.map((skill) => (
                <tr key={skill.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{skill.name}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
                    <button onClick={() => handleOpenModal(skill)} className="text-indigo-600 hover:text-indigo-900">
                      <Edit className="h-4 w-4" />
                    </button>
                    <button onClick={() => handleDelete(skill.id)} className="text-red-600 hover:text-red-900">
                      <Trash2 className="h-4 w-4" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <EmptyState title="No skills found" message="Create your first skill to get started." />
      )}

      {isModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg p-6 max-w-md w-full">
            <h2 className="text-xl font-bold mb-4">{currentSkill ? 'Edit Skill' : 'Create Skill'}</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <Input
                label="Name"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
              />
              <div className="flex justify-end space-x-2 pt-4">
                <Button type="button" variant="outline" onClick={() => setIsModalOpen(false)}>
                  Cancel
                </Button>
                <Button type="submit">Save</Button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default AdminSkillsPage