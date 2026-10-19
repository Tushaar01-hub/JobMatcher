import React, { useState, useEffect } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import { Briefcase, DollarSign, Users, ArrowLeft, Bookmark, CheckCircle, XCircle } from 'lucide-react'
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

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <div className="flex items-center">
        <Button
          onClick={() => navigate(-1)}
          variant="ghost"
          size="sm"
          className="mr-4"
        >
          <ArrowLeft className="h-4 w-4 mr-1" />
          Back
        </Button>
        <h1 className="text-h1">{job?.title}</h1>
      </div>

      <div className="card p-6">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2 space-y-8">
            <section>
              <h2 className="text-h3 mb-4">Job Description</h2>
              <div className="prose max-w-none">
                <p className="text-body text-slate-700 whitespace-pre-line">{job?.description}</p>
              </div>
            </section>

            <section>
              <h2 className="text-h3 mb-4">Required Skills</h2>
              <div className="flex flex-wrap gap-2">
                {job?.skills?.map((skill, index) => (
                  <span
                    key={index}
                    className="px-3 py-1.5 bg-slate-100 text-sm rounded-full text-slate-800"
                  >
                    {skill}
                  </span>
                ))}
              </div>
            </section>
          </div>

          <div className="space-y-6">
            <div className="card p-6 bg-slate-50">
              <h3 className="text-h4 mb-4">Job Details</h3>
              <div className="space-y-4">
                <div className="flex items-center">
                  <Briefcase className="h-5 w-5 text-slate-400 mr-3" />
                  <div>
                    <p className="text-small text-slate-500">Company</p>
                    <p className="text-body font-medium text-slate-900">{job?.company}</p>
                  </div>
                </div>

                <div className="flex items-center">
                  <DollarSign className="h-5 w-5 text-slate-400 mr-3" />
                  <div>
                    <p className="text-small text-slate-500">Salary</p>
                    <p className="text-body font-medium text-slate-900">
                      {job?.salary?.toLocaleString()}
                    </p>
                  </div>
                </div>

                <div className="flex items-center">
                  <Users className="h-5 w-5 text-slate-400 mr-3" />
                  <div>
                    <p className="text-small text-slate-500">Experience Level</p>
                    <p className="text-body font-medium text-slate-900">{job?.experienceLevel}</p>
                  </div>
                </div>
              </div>
            </div>

            <Link to="/matches" className="block">
              <Button variant="primary" size="lg" className="w-full">
                <Bookmark className="h-4 w-4 mr-2" />
                View Your Matches
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JobDetailsPage