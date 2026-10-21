import React, { useState, useEffect } from 'react'
import { useAuth } from '../../context/AuthContext'
import { useToast } from '../../context/ToastContext'
import JobCard from '../../components/jobs/JobCard'
import JobFilters from '../../components/jobs/JobFilters'
import Pagination from '../../components/common/Pagination'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import ErrorMessage from '../../components/common/ErrorMessage'
import EmptyState from '../../components/common/EmptyState'
import jobApi from '../../api/jobApi'
import skillApi from '../../api/skillApi'

const JobsPage = () => {
  const { user } = useAuth()
  const { addToast } = useToast()
  const [jobs, setJobs] = useState([])
  const [skills, setSkills] = useState([])
  const [filters, setFilters] = useState({})
  const [currentPage, setCurrentPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchSkills = async () => {
      try {
        const response = await skillApi.getAllSkills()
        setSkills(response.data)
      } catch (err) {
        const data = err.response?.data

        const message =
            typeof data === 'string'
                ? data
                : data?.message ||
                data?.error ||
                Object.values(data || {})[0] ||
                'Failed to load jobs'

        setError(message)
        addToast(message, 'error')
      }
    }

    fetchSkills()
  }, [addToast])

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        setIsLoading(true)
        const params = {
          ...filters,
          pageNum: currentPage - 1,
          pageSize: 10,
        }

        const response = await jobApi.getJobsByFilters(params)
        setJobs(response.data.content)
        setTotalPages(response.data.totalPages)
      } catch (err) {
        setError(err.response?.data || 'Failed to load jobs')
        addToast(err.response?.data || 'Failed to load jobs', 'error')
      } finally {
        setIsLoading(false)
      }
    }

    fetchJobs()
  }, [filters, currentPage, addToast])

  const handleFilter = (newFilters) => {
    setFilters(newFilters)
    setCurrentPage(1)
  }

  const handlePageChange = (page) => {
    setCurrentPage(page)
  }

  if (isLoading) return <LoadingSpinner size="lg" className="py-12" />
  if (error) return <ErrorMessage message={error} className="my-8" />

  return (
    <div className="space-y-8 fade-in">
      <header>
        <h1 className="text-h1">Job Opportunities</h1>
        <p className="text-body text-slate-500 mt-2">Discover roles that match your technical profile.</p>
      </header>

      <JobFilters onFilter={handleFilter} filters={filters} skills={skills} />

      {jobs.length > 0 ? (
        <div className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {jobs.map((job) => (
              <JobCard key={job.id} job={job} />
            ))}
          </div>

          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </div>
      ) : (
        <EmptyState
          title="No jobs found"
          message="Try adjusting your filters or check back later for new listings."
        />
      )}
    </div>
  )
}

export default JobsPage