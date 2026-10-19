import React from 'react'

const SkillBadge = ({ skill, onRemove }) => {
  return (
    <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-gray-100 text-gray-800">
      {skill.name}
      {onRemove && (
        <button
          onClick={() => onRemove(skill.id)}
          className="ml-2 text-gray-400 hover:text-gray-600 focus:outline-none"
        >
          <svg className="h-3 w-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      )}
    </span>
  )
}

export default SkillBadge