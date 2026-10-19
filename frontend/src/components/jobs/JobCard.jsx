import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, Users, DollarSign, BookOpen } from 'lucide-react'

const JobCard = ({ job }) => {
  return (
    <div className="bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow p-4">
      <div className="flex items-start">
        <div className="flex-1">
          <div className="flex items-center mb-2">
            <Briefcase className="h-5 w-5 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900 truncate">
              <Link to={`/jobs/${job.id}`} className="hover:text-primary">
                {job.title}
              </Link>
            </h3>
          </div>
          <p className="text-sm text-gray-500 mb-2">
            {job.company} • {job.experienceLevel}
          </p>
          <div className="flex items-center text-sm text-gray-600 mb-3">
            <DollarSign className="h-4 w-4 mr-1" />
            <span>{job.salary?.toLocaleString()}</span>
          </div>
          <div className="flex flex-wrap gap-2">
            {job.skills?.slice(0, 3).map((skill) => (
              <span
                key={skill}
                className="px-2 py-1 bg-gray-100 text-xs rounded-full text-gray-700"
              >
                {skill}
              </span>
            ))}
            {job.skills?.length > 3 && (
              <span className="px-2 py-1 bg-gray-100 text-xs rounded-full text-gray-700">
                +{job.skills?.length - 3}
              </span>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default JobCard