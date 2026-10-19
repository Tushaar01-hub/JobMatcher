import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Link, useNavigate } from 'react-router-dom'
import { User, LogOut, Settings, Briefcase, Mail, ArrowLeft, Zap } from 'lucide-react'
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

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <div className="flex items-center">
        <Button
          onClick={() => navigate('/dashboard')}
          variant="ghost"
          size="sm"
          className="mr-4"
        >
          <ArrowLeft className="h-4 w-4 mr-1" />
          Back
        </Button>
        <h1 className="text-h1">Profile</h1>
      </div>

      <div className="card p-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="md:col-span-1">
            <div className="flex flex-col items-center">
              <div className="p-6 bg-indigo-50 rounded-full mb-4">
                <User className="h-16 w-16 text-indigo-600" />
              </div>
              <h2 className="text-h3 mb-1">{profile?.name}</h2>
              <p className="text-body text-slate-500 mb-6">{profile?.email}</p>
              <Button
                onClick={handleLogout}
                variant="danger"
                size="sm"
                className="w-full"
              >
                <LogOut className="h-4 w-4 mr-2" />
                Logout
              </Button>
            </div>
          </div>

          <div className="md:col-span-2 space-y-6">
            <div className="card p-6 bg-slate-50">
              <div className="flex items-center mb-4">
                <Mail className="h-5 w-5 text-slate-400 mr-3" />
                <h3 className="text-h4">Contact Information</h3>
              </div>
              <div className="space-y-3">
                <div>
                  <p className="text-small text-slate-500">Email</p>
                  <p className="text-body font-medium text-slate-900">{profile?.email}</p>
                </div>
              </div>
            </div>

            <div className="card p-6 bg-slate-50">
              <div className="flex items-center mb-4">
                <Settings className="h-5 w-5 text-slate-400 mr-3" />
                <h3 className="text-h4">Account Details</h3>
              </div>
              <div className="space-y-3">
                <div>
                  <p className="text-small text-slate-500">Role</p>
                  <p className="text-body font-medium text-slate-900 capitalize">{profile?.role}</p>
                </div>
              </div>
            </div>

            <div className="card p-6 bg-slate-50">
              <div className="flex items-center mb-4">
                <Briefcase className="h-5 w-5 text-slate-400 mr-3" />
                <h3 className="text-h4">Skills</h3>
              </div>
              <div className="space-y-3">
                <div>
                  <p className="text-small text-slate-500">Total Skills</p>
                  <p className="text-body font-medium text-slate-900">
                    {profile?.skills?.length || 0} skills
                  </p>
                </div>
                <Link to="/skills">
                  <Button variant="outline" size="sm" className="mt-3">
                    <Settings className="h-4 w-4 mr-2" />
                    Manage Skills
                  </Button>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="card p-6 bg-indigo-50 border border-indigo-200">
        <div className="flex items-center">
          <Zap className="h-6 w-6 text-indigo-600 mr-3" />
          <div>
            <h3 className="text-h4 mb-1">Profile Tips</h3>
            <p className="text-body text-slate-600">
              Keeping your profile up-to-date helps our matching algorithm find the best opportunities for you. Regularly review and update your skills to ensure you're seeing the most relevant job matches.
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProfilePage