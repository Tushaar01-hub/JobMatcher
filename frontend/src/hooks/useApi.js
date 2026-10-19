import { useState, useCallback } from 'react'
import { useToast } from '../context/ToastContext'

const useApi = (apiFunc) => {
  const [data, setData] = useState(null)
  const [error, setError] = useState(null)
  const [isLoading, setIsLoading] = useState(false)
  const { addToast } = useToast()

  const request = useCallback(
    async (...args) => {
      setIsLoading(true)
      setError(null)

      try {
        const response = await apiFunc(...args)
        setData(response.data)
        return response.data
      } catch (err) {
        setError(err.response?.data || 'An error occurred')
        addToast(err.response?.data || 'An error occurred', 'error')
        throw err
      } finally {
        setIsLoading(false)
      }
    },
    [apiFunc, addToast]
  )

  return { data, error, isLoading, request }
}

export default useApi