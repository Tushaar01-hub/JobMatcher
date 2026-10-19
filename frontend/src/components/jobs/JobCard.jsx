import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, DollarSign, Users, Bookmark } from 'lucide-react'

const JobCard = ({ job }) => {
  return (
    <div className="card p-6 border border-slate-200 rounded-xl shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start">
        <div className="flex-1">
          <div className="flex items-center mb-3">
            <Briefcase className="h-5 w-5 text-primary mr-3" />
            <h3 className="text-lg font-medium text-slate-900">
              <Link to={`/jobs/${job.id}`} className="hover:text-primary transition-colors">
                {job.title}
              </Link>
            </h3>
          </div>
          <p className="text-sm text-slate-500 mb-3">{job.company}</p>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-4">
            <div className="flex items-center">
              <DollarSign className="h-4 w-4 text-slate-400 mr-2" />
              <span className="text-sm text-slate-700">
                {job.salary?.toLocaleString()}
              </span>
            </div>
            <div className="flex items-center">
              <Users className="h-4 w-4 text-slate-400 mr-2" />
              <span className="text-sm text-slate-700">
                {job.experienceLevel}
              </span>
            </div>
          </div>

          <div className="mb-4">
            <h4 className="text-sm font-medium text-slate-700 mb-2">Required Skills</h4>
            <div className="flex flex-wrap gap-2">
              {job.skills?.slice(0, 3).map((skill, index) => (
                <span
                  key={index}
                  className="px-2.5 py-1 bg-slate-100 text-xs rounded-full text-slate-800"
                >
                  {skill}
                </span>
              ))}
              {job.skills?.length > 3 && (
                <span className="px-2.5 py-1 bg-slate-100 text-xs rounded-full text-slate-800">
                  +{job.skills?.length - 3}
                </span>
              )}
            </div>
          </div>

          <div className="pt-4 border-t border-slate-100">
            <Link
              to={`/jobs/${job.id}`}
              className="inline-flex items-center text-sm font-medium text-primary hover:text-indigo-700 transition-colors"
            >
              View Details
              <Bookmark className="h-4 w-4 ml-1" />
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JobCard