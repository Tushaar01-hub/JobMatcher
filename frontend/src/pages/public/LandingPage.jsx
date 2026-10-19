import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, Users, Search, ArrowRight } from 'lucide-react'
import Button from '../../components/common/Button'

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center mb-16">
          <div className="flex justify-center mb-4">
            <Briefcase className="h-12 w-12 text-primary" />
          </div>
          <h1 className="text-4xl font-bold text-gray-900 mb-4">JobMatcher</h1>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto mb-8">
            Find your perfect job match based on your skills and experience.
          </p>
          <div className="flex flex-col sm:flex-row justify-center gap-4">
            <Link to="/login">
              <Button variant="primary" size="large">
                <ArrowRight className="h-5 w-5 mr-2" />
                Get Started
              </Button>
            </Link>
            <Link to="/register">
              <Button variant="outline" size="large">
                Create Account
              </Button>
            </Link>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16">
          <div className="bg-white rounded-lg shadow-sm p-6 text-center border border-gray-200">
            <div className="p-4 rounded-full bg-blue-50 mb-4">
              <Users className="h-6 w-6 text-blue-500" />
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">User-Friendly</h3>
            <p className="text-gray-600">Easy-to-use interface for job seekers and employers.</p>
          </div>
          <div className="bg-white rounded-lg shadow-sm p-6 text-center border border-gray-200">
            <div className="p-4 rounded-full bg-blue-50 mb-4">
              <Search className="h-6 w-6 text-blue-500" />
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">Smart Matching</h3>
            <p className="text-gray-600">Advanced algorithm to find the best job matches.</p>
          </div>
          <div className="bg-white rounded-lg shadow-sm p-6 text-center border border-gray-200">
            <div className="p-4 rounded-full bg-blue-50 mb-4">
              <Briefcase className="h-6 w-6 text-blue-500" />
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">Comprehensive</h3>
            <p className="text-gray-600">Complete job listings with all the details you need.</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default LandingPage