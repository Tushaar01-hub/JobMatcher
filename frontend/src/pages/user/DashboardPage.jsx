import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Briefcase, Users, Search, Bookmark, ArrowRight } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import jobApi from '../../api/jobApi'
import matchApi from '../../api/matchApi'
import userApi from '../../api/userApi'

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

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Dashboard</h1>

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
            <Users className="h-6 w-6 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900">Your Skills</h3>
          </div>
          <p className="text-3xl font-bold text-gray-900">{stats?.totalSkills || 0}</p>
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div className="flex items-center mb-4">
            <Search className="h-6 w-6 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900">Top Match</h3>
          </div>
          <p className="text-3xl font-bold text-gray-900">{stats?.topMatchPercentage.toFixed(0) || 0}%</p>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-medium text-gray-900">Top Job Matches</h2>
          <Link
            to="/matches"
            className="text-sm font-medium text-primary hover:text-primary-hover flex items-center"
          >
            View All
            <ArrowRight className="h-4 w-4 ml-1" />
          </Link>
        </div>

        {topMatches.length > 0 ? (
          <div className="space-y-4">
            {topMatches.map((match) => (
              <div key={match.jobId} className="border-b border-gray-200 pb-4 last:border-b-0">
                <div className="flex justify-between items-center">
                  <div>
                    <h3 className="text-lg font-medium text-gray-900">
                      <Link to={`/jobs/${match.jobId}`} className="hover:text-primary">
                        {match.title}
                      </Link>
                    </h3>
                    <p className="text-sm text-gray-500">{match.company}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-lg font-bold text-gray-900">
                      {match.matchPercentage.toFixed(0)}%
                    </p>
                    <p className="text-sm text-gray-500">Match</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="text-center py-8">
            <Bookmark className="h-12 w-12 text-gray-400 mx-auto mb-2" />
            <p className="text-gray-500">No matches found. Add more skills to see recommendations.</p>
          </div>
        )}
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h2 className="text-xl font-medium text-gray-900 mb-4">Quick Actions</h2>
          <div className="space-y-3">
            <Link
              to="/jobs"
              className="block p-3 bg-gray-50 hover:bg-gray-100 rounded-md text-gray-700 hover:text-gray-900 transition-colors"
            >
              Browse Jobs
            </Link>
            <Link
              to="/skills"
              className="block p-3 bg-gray-50 hover:bg-gray-100 rounded-md text-gray-700 hover:text-gray-900 transition-colors"
            >
              Manage Skills
            </Link>
            <Link
              to="/matches"
              className="block p-3 bg-gray-50 hover:bg-gray-100 rounded-md text-gray-700 hover:text-gray-900 transition-colors"
            >
              View Matches
            </Link>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h2 className="text-xl font-medium text-gray-900 mb-4">Tips</h2>
          <ul className="space-y-2">
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Add more skills to improve your matches</span>
            </li>
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Check your profile for completeness</span>
            </li>
            <li className="flex items-start">
              <span className="h-2 w-2 bg-primary rounded-full mt-2 mr-2" />
              <span className="text-gray-700">Regularly review your matches</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  )
}

export default DashboardPage