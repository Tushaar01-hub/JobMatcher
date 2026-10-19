import React from 'react'
import { AlertCircle } from 'lucide-react'

const ErrorMessage = ({ message, className = '' }) => {
  if (!message) return null

  return (
    <div className={`flex items-center p-4 rounded-lg bg-error/10 text-error ${className}`}>
      <AlertCircle className="h-5 w-5 mr-3 flex-shrink-0" />
      <span className="text-sm font-medium">{message}</span>
    </div>
  )
}

export default ErrorMessage