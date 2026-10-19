import React from 'react'
import { Search } from 'lucide-react'

const EmptyState = ({ title, message, action, icon: Icon = Search }) => {
  return (
    <div className="flex flex-col items-center justify-center p-8 text-center">
      <div className="p-4 rounded-full bg-gray-100 mb-4">
        <Icon className="w-8 h-8 text-gray-500" />
      </div>
      <h3 className="text-lg font-medium text-gray-900 mb-1">{title}</h3>
      <p className="text-sm text-gray-500 mb-4">{message}</p>
      {action && (
        <div className="mt-2">{action}</div>
      )}
    </div>
  )
}

export default EmptyState