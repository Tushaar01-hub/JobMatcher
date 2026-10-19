import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Link, useNavigate } from 'react-router-dom'
import { User, LogOut, Settings, Briefcase, Mail, ArrowLeft } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import Button from '../../components/common/Button'
import userApi from '../../api/userApi'

const ProfilePage = () => {
  const { user, logout } = useAuth()
  const { addToast } = useToast()
  const navigate = useNavigate()
  const [profile, setProfile] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setIsLoading(true)
        const response = await userApi.getUserProfile(user.userId)
        setProfile(response.data)
      } catch (err) {
        setError(err.response?.data || 'Failed to load profile')
        addToast(err.response?.data || 'Failed to load profile', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchProfile()
  }, [user.userId, addToast])

  const handleLogout = () => {
    logout()
    addToast('You have been logged out', 'info')
    navigate('/login')
  }

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <div className="flex items-center mb-6">
        <Button
          onClick={() => navigate('/dashboard')}
          variant="outline"
          size="small"
          className="mr-4"
        >
          <ArrowLeft className="h-4 w-4 mr-1" />
          Back
        </Button>
        <h1 className="text-2xl font-bold text-gray-900">Profile</h1>
      </div>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div className="flex flex-col md:flex-row items-center">
          <div className="flex-shrink-0 mb-4 md:mb-0 md:mr-6">
            <div className="p-4 rounded-full bg-primary/10">
              <User className="h-12 w-12 text-primary" />
            </div>
          </div>
          <div className="flex-1">
            <h2 className="text-xl font-bold text-gray-900 mb-1">{profile?.name}</h2>
            <p className="text-gray-600 mb-4">{profile?.email}</p>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
              <div className="bg-gray-50 rounded-lg p-4">
                <div className="flex items-center mb-2">
                  <Mail className="h-5 w-5 text-gray-400 mr-2" />
                  <span className="text-sm font-medium text-gray-900">Email</span>
                </div>
                <p className="text-sm text-gray-700">{profile?.email}</p>
              </div>

              <div className="bg-gray-50 rounded-lg p-4">
                <div className="flex items-center mb-2">
                  <Settings className="h-5 w-5 text-gray-400 mr-2" />
                  <span className="text-sm font-medium text-gray-900">Role</span>
                </div>
                <p className="text-sm text-gray-700 capitalize">{profile?.role}</p>
              </div>

              <div className="bg-gray-50 rounded-lg p-4">
                <div className="flex items-center mb-2">
                  <Briefcase className="h-5 w-5 text-gray-400 mr-2" />
                  <span className="text-sm font-medium text-gray-900">Skills</span>
                </div>
                <p className="text-sm text-gray-700">
                  {profile?.skills?.length || 0} skills
                </p>
              </div>
            </div>

            <div className="flex space-x-4">
              <Link to="/skills">
                <Button variant="outline" size="medium">
                  <Settings className="h-4 w-4 mr-1" />
                  Manage Skills
                </Button>
              </Link>
              <Button
                onClick={handleLogout}
                variant="danger"
                size="medium"
                className="flex items-center"
              >
                <LogOut className="h-4 w-4 mr-1" />
                Logout
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProfilePage