import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Plus, Trash2, Search, ArrowRight, Zap } from 'lucide-react'
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
        const data = err.response?.data

        const message =
            typeof data === 'string'
                ? data
                : data?.message ||
                data?.error ||
                Object.values(data || {})[0] ||
                'Failed to load skills'

        setError(message)
        addToast(message, 'error')
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
      setUserSkills(userSkills.filter((skill) => skill.skillId !== skillId))
      addToast('Skill removed successfully', 'success')
    }  catch (err) {
      const data = err.response?.data

      const message =
          typeof data === 'string'
              ? data
              : data?.message ||
              data?.error ||
              Object.values(data || {})[0] ||
              'Failed to remove skill'

      addToast(message, 'error')
    }
  }

  const handleAddSkill = async (skillId) => {
    try {
      await userApi.addSkillToUser(user.userId, skillId)
      const newSkill = allSkills.find((skill) => skill.id === skillId)
      setUserSkills([...userSkills, {
        userId: user.userId,
        skillId: newSkill.id,
        skillName: newSkill.name,
      }])
      addToast('Skill added successfully', 'success')
    } catch (err) {
      const data = err.response?.data

      const message =
          typeof data === 'string'
              ? data
              : data?.message ||
              data?.error ||
              Object.values(data || {})[0] ||
              'Failed to add skill'

      addToast(message, 'error')
    }
  }

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header>
        <h1 className="text-h1">Your Skills</h1>
        <p className="text-body text-slate-500 mt-2">Manage your technical profile to improve your job matches.</p>
      </header>

      <div className="card p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-h3">Your Current Skills</h2>
          {userSkills.length > 0 && (
            <span className="text-sm font-medium text-slate-500">
              {userSkills.length} {userSkills.length === 1 ? 'skill' : 'skills'}
            </span>
          )}
        </div>

        {userSkills.length > 0 ? (
          <div className="flex flex-wrap gap-3">
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
            title="No skills added yet"
            message="Adding skills helps our matching engine find the best opportunities for you."
            icon={Zap}
          />
        )}
      </div>

      <div className="card p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-h3">Add New Skills</h2>
          <div className="w-64">
            <Input
              type="text"
              placeholder="Search skills..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              icon={<Search className="h-4 w-4 text-slate-400" />}
            />
          </div>
        </div>

        {filteredSkills.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
            {filteredSkills.map((skill) => (
              <div
                key={skill.id}
                className={`flex items-center justify-between p-3 rounded-lg transition-colors ${
                  userSkills.some(s => s.skillId === skill.id)
                    ? 'bg-slate-100 text-slate-500'
                    : 'bg-slate-50 hover:bg-slate-100'
                }`}
              >
                <span className="text-sm font-medium">{skill.name}</span>
                {userSkills.some(s => s.skillId === skill.id) ? (
                  <span className="text-xs font-medium text-slate-400">Added</span>
                ) : (
                  <Button
                    onClick={() => handleAddSkill(skill.id)}
                    variant="ghost"
                    size="sm"
                    className="text-primary hover:text-indigo-700"
                  >
                    <Plus className="h-4 w-4" />
                  </Button>
                )}
              </div>
            ))}
          </div>
        ) : (
          <EmptyState
            title="No skills found"
            message="Try searching for a different skill or check back later."
            icon={Search}
          />
        )}
      </div>

      <div className="card p-6 bg-indigo-50 border border-indigo-200">
        <div className="flex items-center">
          <Zap className="h-6 w-6 text-indigo-600 mr-3" />
          <div>
            <h3 className="text-h4 mb-1">Why Skills Matter</h3>
            <p className="text-body text-slate-600">
              The more skills you add, the more accurate our matching algorithm becomes. Each skill you add helps us find roles that are a better fit for your technical background.
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SkillsPage