export const EXPERIENCE_LEVELS = [
  { value: 'FRESHER', label: 'Fresher' },
  { value: 'ZERO_TO_ONE', label: '0-1 years' },
  { value: 'TWO_TO_FIVE', label: '2-5 years' },
  { value: 'FIVE_TO_TEN', label: '5-10 years' },
  { value: 'TEN_PLUS', label: '10+ years' },
]

export const JOB_SORT_OPTIONS = [
  { value: 'title', label: 'Title' },
  { value: 'company', label: 'Company' },
  { value: 'salary', label: 'Salary' },
  { value: 'experienceLevel', label: 'Experience Level' },
]

export const SORT_DIRECTIONS = [
  { value: 'asc', label: 'Ascending' },
  { value: 'desc', label: 'Descending' },
]

export const DEFAULT_PAGE_SIZE = 10

export const API_ERROR_MESSAGES = {
  NETWORK_ERROR: 'Network error. Please check your connection.',
  UNAUTHORIZED: 'Session expired. Please log in again.',
  FORBIDDEN: 'You do not have permission to perform this action.',
  NOT_FOUND: 'The requested resource was not found.',
  CONFLICT: 'A resource with that identifier already exists.',
  SERVER_ERROR: 'An unexpected error occurred. Please try again later.',
}