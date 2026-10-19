import React, { useState, useEffect } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Briefcase, DollarSign, Users, Calendar, ArrowLeft, Bookmark } from 'lucide-react'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import Button from '../../components/common/Button'
import jobApi from '../../api/jobApi'

const JobDetailsPage = () => {
  const { id } = useParams()
  const { user } = useAuth()
  const { addToast } = useToast()
  const navigate = useNavigate()
  const [job, setJob] = useState(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchJob = async () => {
      try {
        setIsLoading(true)
        const response = await jobApi.getJobById(id)
        setJob(response.data)
      } catch (err) {
        setError(err.response?.data || 'Failed to load job details')
        addToast(err.response?.data || 'Failed to load job details', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchJob()
  }, [id, addToast])

  if (isLoading) return <LoadingSpinner size="large" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-6">
      <div className="flex items-center mb-6">
        <Button
          onClick={() => navigate(-1)}
          variant="outline"
          size="small"
          className="mr-4"
        >
          <ArrowLeft className="h-4 w-4 mr-1" />
          Back
        </Button>
        <h1 className="text-2xl font-bold text-gray-900">{job?.title}</h1>
      </div>

      <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="md:col-span-2 space-y-6">
            <div>
              <h2 className="text-xl font-medium text-gray-900 mb-2">Job Description</h2>
              <p className="text-gray-700 whitespace-pre-line">{job?.description}</p>
            </div>

            <div>
              <h2 className="text-xl font-medium text-gray-900 mb-2">Required Skills</h2>
              <div className="flex flex-wrap gap-2">
                {job?.skills?.map((skill, index) => (
                  <span
                    key={index}
                    className="px-3 py-1 bg-gray-100 text-sm rounded-full text-gray-800"
                  >
                    {skill}
                  </span>
                ))}
              </div>
            </div>
          </div>

          <div className="space-y-6">
            <div className="bg-gray-50 rounded-lg p-4">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Job Details</h3>
              <div className="space-y-4">
                <div className="flex items-center">
                  <Briefcase className="h-5 w-5 text-gray-400 mr-2" />
                  <div>
                    <p className="text-sm text-gray-500">Company</p>
                    <p className="text-sm font-medium text-gray-900">{job?.company}</p>
                  </div>
                </div>

                <div className="flex items-center">
                  <DollarSign className="h-5 w-5 text-gray-400 mr-2" />
                  <div>
                    <p className="text-sm text-gray-500">Salary</p>
                    <p className="text-sm font-medium text-gray-900">
                      {job?.salary?.toLocaleString()}
                    </p>
                  </div>
                </div>

                <div className="flex items-center">
                  <Users className="h-5 w-5 text-gray-400 mr-2" />
                  <div>
                    <p className="text-sm text-gray-500">Experience Level</p>
                    <p className="text-sm font-medium text-gray-900">{job?.experienceLevel}</p>
                  </div>
                </div>
              </div>
            </div>

            <div className="flex space-x-2">
              <Link to="/matches" className="w-full">
                <Button variant="primary" size="large" className="w-full">
                  <Bookmark className="h-4 w-4 mr-1" />
                  View Matches
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JobDetailsPage