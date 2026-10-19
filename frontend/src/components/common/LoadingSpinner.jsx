import React from 'react'

const LoadingSpinner = ({ size = 'md', className = '' }) => {
  const sizeClasses = {
    sm: 'h-6 w-6',
    md: 'h-8 w-8',
    lg: 'h-12 w-12',
  }

  return (
    <div className={`flex items-center justify-center ${className}`}>
      <div
        className={`animate-spin rounded-full border-4 border-primary border-t-transparent ${sizeClasses[size]}`}
      ></div>
    </div>
  )
}

export default LoadingSpinner