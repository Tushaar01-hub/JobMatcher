import React from 'react'
import { AlertCircle } from 'lucide-react'

const ErrorMessage = ({ message, className = '' }) => {
  if (!message) return null

  return (
    <div className={`flex items-center p-4 mb-4 text-red-800 bg-red-50 rounded-md ${className}`}>
      <AlertCircle className="w-5 h-5 mr-2" />
      <span className="text-sm">{message}</span>
    </div>
  )
}

export default ErrorMessage