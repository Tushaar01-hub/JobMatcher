import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, CheckCircle, XCircle, Users } from 'lucide-react'

const MatchCard = ({ match }) => {
  return (
    <div className="card p-6 border border-slate-200 rounded-xl shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start">
        <div className="flex-1">
          <div className="flex items-center mb-3">
            <Briefcase className="h-5 w-5 text-primary mr-3" />
            <h3 className="text-lg font-medium text-slate-900">
              <Link to={`/jobs/${match.jobId}`} className="hover:text-primary transition-colors">
                {match.title}
              </Link>
            </h3>
          </div>
          <p className="text-sm text-slate-500 mb-3">{match.company}</p>

          {/* Match Percentage Indicator */}
          <div className="mb-4">
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <span className="text-sm font-medium text-slate-700 mr-1">Match:</span>
                <div className="flex items-center">
                  <span className="text-2xl font-bold text-slate-900 mr-2">
                    {match.matchPercentage.toFixed(0)}%
                  </span>
                  <div className="w-24 h-2 bg-slate-200 rounded-full overflow-hidden">
                    <div
                      className="h-full bg-indigo-600"
                      style={{ width: `${match.matchPercentage}%` }}
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Matched Skills */}
          <div className="mb-4">
            <h4 className="text-sm font-medium text-slate-700 mb-2">Matched Skills</h4>
            <div className="flex flex-wrap gap-2">
              {match.matchedSkills?.map((skill, index) => (
                <span
                  key={index}
                  className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium bg-emerald-50 text-emerald-800"
                >
                  <CheckCircle className="h-3.5 w-3.5 mr-1 text-emerald-500" />
                  {skill}
                </span>
              ))}
            </div>
          </div>

          {/* Missing Skills */}
          {match.missingSkills?.length > 0 && (
            <div>
              <h4 className="text-sm font-medium text-slate-700 mb-2">Missing Skills</h4>
              <div className="flex flex-wrap gap-2">
                {match.missingSkills?.map((skill, index) => (
                  <span
                    key={index}
                    className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium bg-rose-50 text-rose-800"
                  >
                    <XCircle className="h-3.5 w-3.5 mr-1 text-rose-500" />
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