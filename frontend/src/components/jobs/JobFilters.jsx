import React, { useState } from 'react'
import Input from '../common/Input'
import Button from '../common/Button'
import { Search, Filter, X } from 'lucide-react'

const JobFilters = ({ onFilter, filters, skills }) => {
  const [localFilters, setLocalFilters] = useState(filters || {})

  const handleChange = (e) => {
    const { name, value } = e.target
    setLocalFilters((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onFilter(localFilters)
  }

  const handleClear = () => {
    setLocalFilters({})
    onFilter({})
  }

  return (
    <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-4 mb-6">
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <Input
            label="Title"
            name="title"
            value={localFilters.title || ''}
            onChange={handleChange}
            placeholder="Search by title"
          />

          <Input
            label="Company"
            name="company"
            value={localFilters.company || ''}
            onChange={handleChange}
            placeholder="Search by company"
          />

          <Input
            label="Minimum Salary"
            name="minSalary"
            type="number"
            value={localFilters.minSalary || ''}
            onChange={handleChange}
            placeholder="Minimum salary"
          />

          <div>
            <label htmlFor="experienceLevel" className="block text-sm font-medium text-gray-700 mb-1">
              Experience Level
            </label>
            <select
              id="experienceLevel"
              name="experienceLevel"
              value={localFilters.experienceLevel || ''}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            >
              <option value="">All Levels</option>
              <option value="FRESHER">Fresher</option>
              <option value="ZERO_TO_ONE">0-1 years</option>
              <option value="TWO_TO_FIVE">2-5 years</option>
              <option value="FIVE_TO_TEN">5-10 years</option>
              <option value="TEN_PLUS">10+ years</option>
            </select>
          </div>

          <div>
            <label htmlFor="skillName" className="block text-sm font-medium text-gray-700 mb-1">
              Skill
            </label>
            <select
              id="skillName"
              name="skillName"
              value={localFilters.skillName || ''}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            >
              <option value="">All Skills</option>
              {skills?.map((skill) => (
                <option key={skill.id} value={skill.name}>
                  {skill.name}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="flex justify-end space-x-2">
          <Button type="button" onClick={handleClear} variant="outline">
            <X className="h-4 w-4 mr-1" />
            Clear
          </Button>
          <Button type="submit">
            <Filter className="h-4 w-4 mr-1" />
            Filter
          </Button>
        </div>
      </form>
    </div>
  )
}

export default JobFilters