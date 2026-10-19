import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, CheckCircle, XCircle, Users } from 'lucide-react'

const MatchCard = ({ match }) => {
  return (
    <div className="bg-white rounded-lg shadow-sm border border-gray-200 hover:shadow-md transition-shadow p-4">
      <div className="flex items-start">
        <div className="flex-1">
          <div className="flex items-center mb-2">
            <Briefcase className="h-5 w-5 text-primary mr-2" />
            <h3 className="text-lg font-medium text-gray-900 truncate">
              <Link to={`/jobs/${match.jobId}`} className="hover:text-primary">
                {match.title}
              </Link>
            </h3>
          </div>
          <p className="text-sm text-gray-500 mb-2">
            {match.company}
          </p>

          <div className="mb-3">
            <div className="flex items-center mb-1">
              <span className="text-sm font-medium text-gray-700 mr-1">Match:</span>
              <div className="flex items-center">
                <span className="text-lg font-bold text-gray-900 mr-1">
                  {match.matchPercentage.toFixed(0)}%
                </span>
                <div className="w-20 h-2 bg-gray-200 rounded-full overflow-hidden">
                  <div
                    className="h-full bg-primary"
                    style={{ width: `${match.matchPercentage}%` }}
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="mb-2">
            <h4 className="text-sm font-medium text-gray-700 mb-1">Matched Skills</h4>
            <div className="flex flex-wrap gap-2">
              {match.matchedSkills?.map((skill, index) => (
                <span
                  key={index}
                  className="flex items-center px-2 py-1 bg-green-50 text-green-800 text-xs rounded-full"
                >
                  <CheckCircle className="h-3 w-3 mr-1 text-green-500" />
                  {skill}
                </span>
              ))}
            </div>
          </div>

          {match.missingSkills?.length > 0 && (
            <div>
              <h4 className="text-sm font-medium text-gray-700 mb-1">Missing Skills</h4>
              <div className="flex flex-wrap gap-2">
                {match.missingSkills?.map((skill, index) => (
                  <span
                    key={index}
                    className="flex items-center px-2 py-1 bg-red-50 text-red-800 text-xs rounded-full"
                  >
                    <XCircle className="h-3 w-3 mr-1 text-red-500" />
                    {skill}
                  </span>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default MatchCard