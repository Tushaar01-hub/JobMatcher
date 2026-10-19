import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Plus, Trash2, Search } from 'lucide-react'
import SkillBadge from '../../components/jobs/SkillBadge'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import Input from '../../components/common/Input'
import Button from '../../components/common/Button'
import userApi from '../../api/userApi'
import skillApi from '../../api/skillApi'

const SkillsPage = () => {
  const { user } = useAuth()
  const { addToast } = useToast()
  const [userSkills, setUserSkills] = useState([])
  const [allSkills, setAllSkills] = useState([])
  const [searchTerm, setSearchTerm] = useState('')
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true)
        const [userSkillsResponse, allSkillsResponse] = await Promise.all([
          userApi.getUserSkills(user.userId),
          skillApi.getAllSkills(),
        ])

        setUserSkills(userSkillsResponse.data)
        setAllSkills(allSkillsResponse.data)
      } catch (err) {
        setError(err.response?.data || 'Failed to load skills')
        addToast(err.response?.data || 'Failed to load skills', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [user.userId, addToast])

  const filteredSkills = allSkills.filter((skill) => {
    return skill.name.toLowerCase().includes(searchTerm.toLowerCase())
  })

  const handleRemoveSkill = async (skillId) => {
    try {
      await userApi.removeSkillFromUser(user.userId, skillId)

      setUserSkills(
          userSkills.filter((skill) => skill.skillId !== skillId)
      )

      addToast('Skill removed successfully', 'success')
    } catch (err) {
      addToast(
          err.response?.data || 'Failed to remove skill',
          'error'
      )
    }
  }

  const handleAddSkill = async (skillId) => {
    try {
      await userApi.addSkillToUser(user.userId, skillId)

      const newSkill = allSkills.find((skill) => skill.id === skillId)

      setUserSkills([
        ...userSkills,
        {
          userId: user.userId,
          skillId: newSkill.id,
          skillName: newSkill.name,
        },
      ])

      addToast('Skill added successfully', 'success')
    } catch (err) {
      addToast(
          err.response?.data || 'Failed to add skill',
          'error'
      )
    }
  }

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Your Skills</h1>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-medium text-gray-900">My Skills</h2>
          <div className="flex items-center">
            <Input
              type="text"
              id="search"
              name="search"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search skills"
              className="w-64"
            />
          </div>
        </div>

        {userSkills.length > 0 ? (
          <div className="flex flex-wrap gap-2 mb-6">
            {userSkills.map((skill) => (
              <SkillBadge
                key={skill.skillId}
                skill={{ id: skill.skillId, name: skill.skillName }}
                onRemove={handleRemoveSkill}
              />
            ))}
          </div>
        ) : (
          <EmptyState
            title="No skills added"
            message="Add skills to improve your job matches"
            icon={Search}
          />
        )}
      </div>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-medium text-gray-900">All Skills</h2>
          <Button variant="outline" size="small">
            <Plus className="h-4 w-4 mr-1" />
            Add Skill
          </Button>
        </div>

        {filteredSkills.length > 0 ? (
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-3">
            {filteredSkills.map((skill) => (
              <div
                key={skill.id}
                className="flex items-center justify-between p-2 bg-gray-50 rounded-md hover:bg-gray-100 transition-colors"
              >
                <span className="text-sm text-gray-700">{skill.name}</span>
                <Button
                  type="button"
                  onClick={() => handleAddSkill(skill.id)}
                  variant="outline"
                  size="small"
                  className="ml-2"
                >
                  <Plus className="h-3 w-3" />
                </Button>
              </div>
            ))}
          </div>
        ) : (
          <EmptyState
            title="No skills found"
            message="Try searching for a specific skill"
            icon={Search}
          />
        )}
      </div>
    </div>
  )
}

export default SkillsPage