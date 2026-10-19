import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useToast } from '../../context/ToastContext'
import { Briefcase, Users, Settings, ArrowRight } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import jobApi from '../../api/jobApi'
import skillApi from '../../api/skillApi'
import userApi from '../../api/userApi'

const AdminDashboardPage = () => {
  const { addToast } = useToast()
  const [stats, setStats] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)
  // console.log('ADMIN DASHBOARD FETCH STARTED')
  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true)
        const [jobsResponse, skillsResponse, usersResponse] = await Promise.all([
          jobApi.getAllJobs(),
          skillApi.getAllSkills(),
          userApi.getAllUsers(),
        ])

        setStats({
          totalJobs: jobsResponse.data.length,
          totalSkills: skillsResponse.data.length,
          totalUsers: usersResponse.data.length,
        })
      } catch (err) {
        setError(err.response?.data || 'Failed to load admin dashboard data')
        addToast(err.response?.data || 'Failed to load admin dashboard data', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [addToast])

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Admin Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div className="flex items-center mb-4">
            <Briefcase className="h-6 w-6 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900">Total Jobs</h3>
          </div>
          <p className="text-3xl font-bold text-gray-900">{stats?.totalJobs || 0}</p>
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div className="flex items-center mb-4">
            <Settings className="h-6 w-6 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900">Total Skills</h3>
          </div>
          <p className="text-3xl font-bold text-gray-900">{stats?.totalSkills || 0}</p>
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div className="flex items-center mb-4">
            <Users className="h-6 w-6 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900">Total Users</h3>
          </div>
          <p className="text-3xl font-bold text-gray-900">{stats?.totalUsers || 0}</p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h2 className="text-xl font-medium text-gray-900 mb-4">Quick Actions</h2>
          <div className="space-y-3">
            <Link
              to="/admin/jobs"
              className="block p-3 bg-gray-50 hover:bg-gray-100 rounded-md text-gray-700 hover:text-gray-900 transition-colors"
            >
              Manage Jobs
            </Link>
            <Link
              to="/admin/skills"
              className="block p-3 bg-gray-50 hover:bg-gray-100 rounded-md text-gray-700 hover:text-gray-900 transition-colors"
            >
              Manage Skills
            </Link>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h2 className="text-xl font-medium text-gray-900 mb-4">Tips</h2>
          <ul className="space-y-2">
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Regularly review job listings</span>
            </li>
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Keep the skill catalogue up-to-date</span>
            </li>
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Monitor user activity and engagement</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  )
}

export default AdminDashboardPage