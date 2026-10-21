import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Link } from 'react-router-dom'
import { Briefcase, CheckCircle, XCircle, ArrowRight, Zap } from 'lucide-react'
import MatchCard from '../../components/jobs/MatchCard'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import Button from '../../components/common/Button'
import matchApi from '../../api/matchApi'

const MatchesPage = () => {
  const { user } = useAuth()
  const { addToast } = useToast()
  const [matches, setMatches] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        setIsLoading(true)
        const response = await matchApi.getUserMatches(user.userId)
        setMatches(response.data)
      } catch (err) {
        const data = err.response?.data

        const message =
            typeof data === 'string'
                ? data
                : data?.message ||
                data?.error ||
                Object.values(data || {})[0] ||
                'Failed to load matches'

        setError(message)
        addToast(message, 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchMatches()
  }, [user.userId, addToast])

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header>
        <h1 className="text-h1">Your Job Matches</h1>
        <p className="text-body text-slate-500 mt-2">Here are the jobs that best match your skills and experience.</p>
      </header>

      {matches.length > 0 ? (
        <div className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {matches.map((match) => (
              <MatchCard key={match.jobId} match={match} />
            ))}
          </div>

          <div className="card p-6 bg-indigo-50 border border-indigo-200">
            <div className="flex items-center">
              <Zap className="h-6 w-6 text-indigo-600 mr-3" />
              <div>
                <h3 className="text-h4 mb-1">How We Calculate Matches</h3>
                <p className="text-body text-slate-600">
                  We compare your skills against each job's requirements to determine a percentage match. The higher the percentage, the better the fit for your profile.
                </p>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <EmptyState
          title="No matches found"
          message="Add more skills to see job recommendations that match your profile."
          action={
            <Link to="/skills">
              <Button variant="primary">
                Add Skills
                <ArrowRight className="h-4 w-4 ml-2" />
              </Button>
            </Link>
          }
        />
      )}
    </div>
  )
}

export default MatchesPage