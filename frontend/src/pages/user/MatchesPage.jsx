import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import MatchCard from '../../components/jobs/MatchCard'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
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
        setError(err.response?.data || 'Failed to load matches')
        addToast(err.response?.data || 'Failed to load matches', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchMatches()
  }, [user.userId, addToast])

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Job Matches</h1>

      {matches.length > 0 ? (
        <div className="space-y-4">
          {matches.map((match) => (
            <MatchCard key={match.jobId} match={match} />
          ))}
        </div>
      ) : (
        <EmptyState
          title="No matches found"
          message="Add more skills to see job recommendations that match your profile."
        />
      )}
    </div>
  )
}

export default MatchesPage