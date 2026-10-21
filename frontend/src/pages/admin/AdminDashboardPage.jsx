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
        const data = err.response?.data

        const message =
            typeof data === 'string'
                ? data
                : data?.message ||
                data?.error ||
                Object.values(data || {})[0] ||
                'Failed to load admin dashboard data'

        setError(message)
        addToast(message, 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [addToast])

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header>
        <h1 className="text-h1">Admin Dashboard</h1>
        <p className="text-body text-slate-500 mt-2">Platform performance and administrative statistics.</p>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card p-6 flex items-center space-x-4">
          <div className="p-3 bg-indigo-50 rounded-lg text-primary">
            <Briefcase className="h-6 w-6" />
          </div>
          <div>
            <p className="text-small font-semibold uppercase tracking-wider">Total Jobs</p>
            <p className="text-3xl font-bold text-slate-900">{stats?.totalJobs || 0}</p>
          </div>
        </div>

        <div className="card p-6 flex items-center space-x-4">
          <div className="p-3 bg-emerald-50 rounded-lg text-emerald-600">
            <Settings className="h-6 w-6" />
          </div>
          <div>
            <p className="text-small font-semibold uppercase tracking-wider">Total Skills</p>
            <p className="text-3xl font-bold text-slate-900">{stats?.totalSkills || 0}</p>
          </div>
        </div>

        <div className="card p-6 flex items-center space-x-4">
          <div className="p-3 bg-violet-50 rounded-lg text-violet-600">
            <Users className="h-6 w-6" />
          </div>
          <div>
            <p className="text-small font-semibold uppercase tracking-wider">Total Users</p>
            <p className="text-3xl font-bold text-slate-900">{stats?.totalUsers || 0}</p>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <section className="card p-6">
          <h2 className="text-h4 mb-6">Manage Entities</h2>
          <div className="space-y-4">
            <Link
              to="/admin/jobs"
              className="flex items-center justify-between p-4 bg-slate-50 rounded-lg hover:bg-indigo-50 transition-colors group"
            >
              <div>
                <p className="font-semibold text-slate-900">Manage Jobs</p>
                <p className="text-small">Add, edit, or remove listings</p>
              </div>
              <ArrowRight className="h-4 w-4 text-slate-400 group-hover:text-primary transition-colors" />
            </Link>
            <Link
              to="/admin/skills"
              className="flex items-center justify-between p-4 bg-slate-50 rounded-lg hover:bg-indigo-50 transition-colors group"
            >
              <div>
                <p className="font-semibold text-slate-900">Manage Skills</p>
                <p className="text-small">Update global technical skill catalogue</p>
              </div>
              <ArrowRight className="h-4 w-4 text-slate-400 group-hover:text-primary transition-colors" />
            </Link>
          </div>
        </section>

        <section className="card p-6 bg-slate-900 text-white">
          <h2 className="text-h4 mb-6 text-white">Administrative Guidance</h2>
          <ul className="space-y-4">
            <li className="flex items-start">
              <span className="h-6 w-6 rounded-full bg-slate-800 flex items-center justify-center text-xs font-bold mr-3 mt-0.5 text-indigo-400">1</span>
              <p className="text-sm text-slate-300">Adding a diverse set of skills improves the precision of the matching algorithm for all users.</p>
            </li>
            <li className="flex items-start">
              <span className="h-6 w-6 rounded-full bg-slate-800 flex items-center justify-center text-xs font-bold mr-3 mt-0.5 text-indigo-400">2</span>
              <p className="text-sm text-slate-300">Ensure job listings specify appropriate experience levels and realistic salary bands.</p>
            </li>
          </ul>
        </section>
      </div>
    </div>
  )
}

export default AdminDashboardPage