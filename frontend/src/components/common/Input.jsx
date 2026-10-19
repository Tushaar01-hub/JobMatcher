import React from 'react'

const Input = ({
  label,
  type = 'text',
  id,
  name,
  value,
  onChange,
  placeholder,
  error,
  disabled = false,
  required = false,
  className = '',
  inputClassName = '',
  labelClassName = '',
}) => {
  return (
    <div className={`space-y-2 ${className}`}>
      {label && (
        <label htmlFor={id} className={`block text-sm font-medium text-slate-700 ${labelClassName}`}>
          {label} {required && <span className="text-error text-xs ml-1">*</span>}
        </label>
      )}
      <input
        type={type}
        id={id}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        required={required}
        className={`input ${inputClassName} ${error ? 'input-error' : ''}`}
      />
      {error && <p className="mt-1 text-xs text-error">{error}</p>}
    </div>
  )
}

export default Input