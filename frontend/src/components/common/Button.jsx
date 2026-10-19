import React from 'react'

const Button = ({
  children,
  type = 'button',
  onClick,
  disabled = false,
  className = '',
  variant = 'primary',
  size = 'md',
  fullWidth = false,
}) => {
  const baseClasses = 'btn rounded-lg font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-offset-2'
  const variantClasses = {
    primary: 'btn-primary',
    secondary: 'btn-secondary',
    ghost: 'btn-ghost',
    danger: 'bg-error text-white hover:bg-error-hover',
    success: 'bg-success text-white hover:bg-success-hover',
  }
  const sizeClasses = {
    sm: 'btn-sm px-3 py-1.5 text-sm',
    md: 'btn-md px-4 py-2 text-base',
    lg: 'btn-lg px-6 py-3 text-lg',
  }
  const fullWidthClasses = fullWidth ? 'w-full' : ''

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${fullWidthClasses} ${className}`}
    >
      {children}
    </button>
  )
}

export default Button