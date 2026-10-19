import React from 'react'
import { Search } from 'lucide-react'

const EmptyState = ({ title, message, action, icon: Icon = Search }) => {
  return (
    <div className="flex flex-col items-center justify-center p-8 text-center">
      <div className="p-4 rounded-full bg-slate-100 mb-4">
        <Icon className="h-8 w-8 text-slate-500" />
      </div>
      <h3 className="text-h3 mb-2">{title}</h3>
      <p className="text-body text-slate-500 mb-6">{message}</p>
      {action && (
        <div className="mt-4">{action}</div>
      )}
    </div>
  )
}

export default EmptyState