import React, { useState, useEffect } from 'react'
import { useToast } from '../../context/ToastContext'
import { Plus, Trash2, Edit } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Modal from '../../components/common/Modal'
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

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header className="flex justify-between items-center">
        <div>
          <h1 className="text-h1">Manage Skills</h1>
          <p className="text-body text-slate-500 mt-2">Maintain the global skill catalogue.</p>
        </div>
        <Button onClick={() => handleOpenModal()}>
          <Plus className="h-4 w-4 mr-2" />
          Add Skill
        </Button>
      </header>

      {skills.length > 0 ? (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-slate-200">
              <thead className="bg-slate-50">
                <tr>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-slate-500 uppercase tracking-wider">Name</th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-slate-500 uppercase tracking-wider">Actions</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-slate-200">
                {skills.map((skill) => (
                  <tr key={skill.id} className="hover:bg-slate-50 transition-colors">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-slate-900">{skill.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-3">
                      <button onClick={() => handleOpenModal(skill)} className="text-indigo-600 hover:text-indigo-900">
                        <Edit className="h-4 w-4" />
                      </button>
                      <button onClick={() => handleDelete(skill.id)} className="text-rose-600 hover:text-rose-900">
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
        <EmptyState title="No skills found" message="Create your first skill to get started." />
      )}

      <Modal
        open={isModalOpen}
        onOpenChange={setIsModalOpen}
        title={currentSkill ? 'Edit Skill' : 'Create Skill'}
        description="Enter the name of the skill below."
      >
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            label="Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            required
          />
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

export default AdminSkillsPage