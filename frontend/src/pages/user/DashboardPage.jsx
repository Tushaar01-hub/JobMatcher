import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Briefcase, Users, Search, Bookmark, ArrowRight, Zap, Target } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import jobApi from '../../api/jobApi'
import matchApi from '../../api/matchApi'
import userApi from '../../api/userApi'
import MatchCard from '../../components/jobs/MatchCard'

const DashboardPage = () => {
  const { user } = useAuth()
  const { addToast } = useToast()
  const [stats, setStats] = useState(null)
  const [topMatches, setTopMatches] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true)
        const [jobsResponse, matchesResponse, userResponse] = await Promise.all([
          jobApi.getAllJobs(),
          matchApi.getUserMatches(user.userId),
          userApi.getUserSkills(user.userId),
        ])

        setStats({
          totalJobs: jobsResponse.data.length,
          totalSkills: userResponse.data.length,
          topMatchPercentage: matchesResponse.data[0]?.matchPercentage || 0,
        })

        setTopMatches(matchesResponse.data.slice(0, 3))
      } catch (err) {
        setError(err.response?.data || 'Failed to load dashboard data')
        addToast(err.response?.data || 'Failed to load dashboard data', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [user.userId, addToast])

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header>
        <h1 className="text-h1">Dashboard</h1>
        <p className="text-body text-slate-500 mt-2">Welcome back, {user?.email.split('@')[0]}. Here is your overview.</p>
      </header>

      {/* Summary Cards */}
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
            <Users className="h-6 w-6" />
          </div>
          <div>
            <p className="text-small font-semibold uppercase tracking-wider">Your Skills</p>
            <p className="text-3xl font-bold text-slate-900">{stats?.totalSkills || 0}</p>
          </div>
        </div>

        <div className="card p-6 flex items-center space-x-4">
          <div className="p-3 bg-violet-50 rounded-lg text-violet-600">
            <Target className="h-6 w-6" />
          </div>
          <div>
            <p className="text-small font-semibold uppercase tracking-wider">Top Match</p>
            <p className="text-3xl font-bold text-slate-900">{stats?.topMatchPercentage.toFixed(0) || 0}%</p>
          </div>
        </div>
      </div>

      {/* Top Matches Section */}
      <section>
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-h3">Top Job Matches</h2>
          <Link
            to="/matches"
            className="text-sm font-semibold text-primary hover:text-indigo-700 flex items-center transition-colors"
          >
            View All Matches
            <ArrowRight className="h-4 w-4 ml-1" />
          </Link>
        </div>

        {topMatches.length > 0 ? (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {topMatches.map((match) => (
              <MatchCard key={match.jobId} match={match} />
            ))}
          </div>
        ) : (
          <div className="card p-12 text-center">
            <Bookmark className="h-12 w-12 text-slate-300 mx-auto mb-4" />
            <h3 className="text-h4 mb-2">No matches yet</h3>
            <p className="text-body text-slate-500 max-w-sm mx-auto">Add more skills to your profile to see tailored job recommendations.</p>
          </div>
        )}
      </section>

      {/* Quick Actions & Tips */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <section className="card p-6">
          <h2 className="text-h4 mb-6">Quick Actions</h2>
          <div className="space-y-4">
            <Link to="/jobs" className="flex items-center p-4 bg-slate-50 rounded-lg hover:bg-indigo-50 transition-colors group">
              <Search className="h-5 w-5 text-slate-500 group-hover:text-primary mr-4" />
              <div>
                <p className="font-semibold text-slate-900">Browse Jobs</p>
                <p className="text-small">Explore all available opportunities</p>
              </div>
            </Link>
            <Link to="/skills" className="flex items-center p-4 bg-slate-50 rounded-lg hover:bg-indigo-50 transition-colors group">
              <Zap className="h-5 w-5 text-slate-500 group-hover:text-primary mr-4" />
              <div>
                <p className="font-semibold text-slate-900">Manage Skills</p>
                <p className="text-small">Update your technical profile</p>
              </div>
            </Link>
          </div>
        </section>

        <section className="bg-indigo-900 p-6 rounded-xl shadow-sm text-white">
          <h2 className="text-xl font-bold mb-6 text-white">Platform Tips</h2>
          <ul className="space-y-4">
            <li className="flex items-start">
              <span className="h-6 w-6 rounded-full bg-indigo-700 flex items-center justify-center text-xs font-bold mr-3 mt-0.5 text-indigo-400">1</span>
              <p className="text-sm text-indigo-50 font-medium">The more skills you add, the more accurate our matching algorithm becomes.</p>
            </li>
            <li className="flex items-start">
              <span className="h-6 w-6 rounded-full bg-indigo-700 flex items-center justify-center text-xs font-bold mr-3 mt-0.5 text-indigo-400">2</span>
              <p className="text-sm text-indigo-50 font-medium">Regularly check for new job postings to stay ahead of the crowd.</p>
            </li>
          </ul>
        </section>
      </div>
    </div>
  )
}

export default DashboardPage