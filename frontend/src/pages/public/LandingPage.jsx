import React from 'react'
import { Link } from 'react-router-dom'
import { Briefcase, Users, Search, CheckCircle, XCircle, ArrowRight, ShieldCheck, Zap, Database } from 'lucide-react'
import Button from '../../components/common/Button'

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-indigo-50/20 to-slate-50">
      {/* Hero Section */}
      <section className="relative overflow-hidden pt-16 pb-20 lg:pt-24 lg:pb-32">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
            <div className="lg:col-span-7 text-center lg:text-left">
              <div className="inline-flex items-center px-3 py-1 rounded-full bg-indigo-50 border border-indigo-100 text-indigo-700 text-xs font-semibold mb-6">
                <Zap className="h-3.5 w-3.5 mr-1.5" />
                Advanced Skill-Based Matching Engine
              </div>
              <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-slate-900 tracking-tight mb-6">
                Find Jobs That Match <span className="text-transparent bg-clip-text bg-gradient-to-r from-indigo-600 to-violet-600">Your Skills.</span>
              </h1>
              <p className="text-lg sm:text-xl text-slate-600 mb-8 max-w-2xl mx-auto lg:mx-0">
                Stop searching through outdated listings. JobMatcher compares your technical stack with open opportunities, highlighting what you match and what you need to learn.
              </p>
              <div className="flex flex-col sm:flex-row justify-center lg:justify-start gap-4">
                <Link to="/login">
                  <Button variant="primary" size="lg" className="w-full sm:w-auto shadow-lg shadow-indigo-200">
                    Find Your Matches
                    <ArrowRight className="h-4 w-4 ml-2" />
                  </Button>
                </Link>
                <Link to="/jobs">
                  <Button variant="secondary" size="lg" className="w-full sm:w-auto">
                    Browse All Jobs
                  </Button>
                </Link>
              </div>
            </div>

            {/* Right Side Showcase Element */}
            <div className="lg:col-span-5">
              <div className="relative mx-auto max-w-md lg:max-w-none">
                <div className="absolute -inset-1 rounded-2xl bg-gradient-to-r from-indigo-600 to-violet-600 opacity-20 blur-xl"></div>
                <div className="relative card bg-white/80 backdrop-blur-sm p-6 space-y-6">
                  <div className="flex items-center justify-between border-b border-slate-100 pb-4">
                    <div className="flex items-center space-x-3">
                      <div className="h-10 w-10 rounded-lg bg-indigo-50 flex items-center justify-center text-indigo-600 font-bold">
                        JB
                      </div>
                      <div>
                        <h4 className="font-semibold text-slate-900">Java Backend Developer</h4>
                        <p className="text-xs text-slate-500">TechCorp Solutions • Full-Time</p>
                      </div>
                    </div>
                    <span className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-semibold bg-emerald-50 text-emerald-700">
                      92% Match
                    </span>
                  </div>

                  <div className="space-y-3">
                    <div className="text-xs font-semibold text-slate-400 uppercase tracking-wider">Matched Skills</div>
                    <div className="flex flex-wrap gap-2">
                      <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-medium bg-emerald-50 text-emerald-800">
                        <CheckCircle className="h-3 w-3 mr-1 text-emerald-500" /> Java
                      </span>
                      <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-medium bg-emerald-50 text-emerald-800">
                        <CheckCircle className="h-3 w-3 mr-1 text-emerald-500" /> Spring Boot
                      </span>
                      <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-medium bg-emerald-50 text-emerald-800">
                        <CheckCircle className="h-3 w-3 mr-1 text-emerald-500" /> PostgreSQL
                      </span>
                      <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-medium bg-slate-100 text-slate-600">
                        +2 matched
                      </span>
                    </div>
                  </div>

                  <div className="space-y-3 pt-2">
                    <div className="text-xs font-semibold text-slate-400 uppercase tracking-wider">Missing Skills</div>
                    <div className="flex flex-wrap gap-2">
                      <span className="inline-flex items-center px-2.5 py-1 rounded-md text-xs font-medium bg-rose-50 text-rose-800">
                        <XCircle className="h-3 w-3 mr-1 text-rose-500" /> Docker
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Trust / Value Strip */}
      <section className="bg-white border-y border-slate-200 py-12">
        <div className="container">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div className="flex items-start space-x-4">
              <div className="p-3 rounded-lg bg-indigo-50 text-indigo-600">
                <Zap className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold text-slate-900 mb-1">Smart Skill Matching</h3>
                <p className="text-sm text-slate-500">Instant algorithmic comparison against job requirements.</p>
              </div>
            </div>
            <div className="flex items-start space-x-4">
              <div className="p-3 rounded-lg bg-indigo-50 text-indigo-600">
                <Search className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold text-slate-900 mb-1">Personalized Discovery</h3>
                <p className="text-sm text-slate-500">Surface relevant roles ranked by percentage compatibility.</p>
              </div>
            </div>
            <div className="flex items-start space-x-4">
              <div className="p-3 rounded-lg bg-indigo-50 text-indigo-600">
                <ShieldCheck className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold text-slate-900 mb-1">Skill Gap Insights</h3>
                <p className="text-sm text-slate-500">Know exactly which missing skills are holding you back.</p>
              </div>
            </div>
            <div className="flex items-start space-x-4">
              <div className="p-3 rounded-lg bg-indigo-50 text-indigo-600">
                <Database className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold text-slate-900 mb-1">Real-time Filtering</h3>
                <p className="text-sm text-slate-500">Dynamic multi-parameter filtering powered by JPA Specifications.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* How JobMatcher Works */}
      <section className="py-20 bg-slate-50">
        <div className="container text-center">
          <h2 className="text-3xl font-bold text-slate-900 mb-4">How JobMatcher Works</h2>
          <p className="text-slate-600 max-w-2xl mx-auto mb-16">Three simple steps to discover roles tailored to your exact technical background.</p>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="card p-8 bg-white text-left relative">
              <div className="text-5xl font-black text-indigo-100 mb-4">01</div>
              <h3 className="text-xl font-bold text-slate-900 mb-2">Build Your Profile</h3>
              <p className="text-slate-600 text-sm">Select your core competencies and technical skills from our structured global catalogue.</p>
            </div>
            <div className="card p-8 bg-white text-left relative">
              <div className="text-5xl font-black text-indigo-100 mb-4">02</div>
              <h3 className="text-xl font-bold text-slate-900 mb-2">Discover Matches</h3>
              <p className="text-slate-600 text-sm">Our server computes precise percentage overlaps, instantly ranking roles from highest to lowest compatibility.</p>
            </div>
            <div className="card p-8 bg-white text-left relative">
              <div className="text-5xl font-black text-indigo-100 mb-4">03</div>
              <h3 className="text-xl font-bold text-slate-900 mb-2">Improve Your Profile</h3>
              <p className="text-slate-600 text-sm">Identify missing requirements for target roles, update your skills, and watch your compatibility scores rise.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Matching Engine Showcase */}
      <section className="py-20 bg-white border-y border-slate-200">
        <div className="container">
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
            <div className="lg:col-span-6 space-y-6">
              <span className="text-indigo-600 font-semibold text-sm tracking-wider uppercase">Deep Transparency</span>
              <h2 className="text-3xl font-bold text-slate-900">See exactly why a job matches you.</h2>
              <p className="text-slate-600">
                Unlike traditional boards that show basic descriptions, JobMatcher breaks down compatibility at the granular skill level. You instantly see your matched assets alongside precise gaps to address.
              </p>
              <ul className="space-y-3 text-sm text-slate-700">
                <li className="flex items-center">
                  <CheckCircle className="h-5 w-5 text-emerald-500 mr-2" /> Automated intersection of user profile vs job prerequisites
                </li>
                <li className="flex items-center">
                  <CheckCircle className="h-5 w-5 text-emerald-500 mr-2" /> Redis-backed caching for low-latency matching performance
                </li>
                <li className="flex items-center">
                  <CheckCircle className="h-5 w-5 text-emerald-500 mr-2" /> Instant cache invalidation when your skill set changes
                </li>
              </ul>
            </div>
            <div className="lg:col-span-6">
              <div className="card p-6 bg-slate-50 border border-slate-200 space-y-4">
                <div className="flex justify-between items-center">
                  <div>
                    <h4 className="font-bold text-slate-900 text-lg">Senior Backend Engineer</h4>
                    <p className="text-xs text-slate-500">CloudScale Systems</p>
                  </div>
                  <div className="text-right">
                    <span className="text-xl font-black text-indigo-600">87%</span>
                    <p className="text-[10px] uppercase font-semibold text-slate-400">Match Score</p>
                  </div>
                </div>
                <div className="border-t border-slate-200 pt-4 space-y-3">
                  <div>
                    <span className="text-xs font-semibold text-emerald-700 block mb-1">Matched Skills</span>
                    <div className="flex flex-wrap gap-1.5">
                      <span className="px-2 py-0.5 bg-emerald-100 text-emerald-800 rounded text-xs">Java</span>
                      <span className="px-2 py-0.5 bg-emerald-100 text-emerald-800 rounded text-xs">Spring Data JPA</span>
                      <span className="px-2 py-0.5 bg-emerald-100 text-emerald-800 rounded text-xs">PostgreSQL</span>
                      <span className="px-2 py-0.5 bg-emerald-100 text-emerald-800 rounded text-xs">Redis</span>
                    </div>
                  </div>
                  <div>
                    <span className="text-xs font-semibold text-rose-700 block mb-1">Missing Skills</span>
                    <div className="flex flex-wrap gap-1.5">
                      <span className="px-2 py-0.5 bg-rose-100 text-rose-800 rounded text-xs">Kubernetes</span>
                      <span className="px-2 py-0.5 bg-rose-100 text-rose-800 rounded text-xs">Kafka</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Verified Stats */}
      <section className="py-16 bg-slate-900 text-white">
        <div className="container text-center">
          <h2 className="text-2xl font-bold mb-12 text-slate-200">Built on Solid Backend Fundamentals</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="p-6">
              <div className="text-4xl font-black text-indigo-400 mb-2">100%</div>
              <p className="text-slate-400 text-sm font-medium">RESTful Architecture</p>
            </div>
            <div className="p-6">
              <div className="text-4xl font-black text-indigo-400 mb-2">JPA</div>
              <p className="text-slate-400 text-sm font-medium">Dynamic Specification Filtering</p>
            </div>
            <div className="p-6">
              <div className="text-4xl font-black text-indigo-400 mb-2">Redis</div>
              <p className="text-slate-400 text-sm font-medium">Cache-Aside with TTL Invalidation</p>
            </div>
          </div>
        </div>
      </section>

      {/* Final CTA */}
      <section className="py-20 bg-gradient-to-r from-indigo-600 to-violet-600 text-white text-center">
        <div className="container max-w-4xl">
          <h2 className="text-3xl sm:text-4xl font-extrabold mb-4">Ready to find your best job matches?</h2>
          <p className="text-indigo-100 mb-8 text-lg">Join today and let our matching engine connect you with relevant opportunities instantly.</p>
          <div className="flex justify-center gap-4">
            <Link to="/register">
              <Button size="lg" className="bg-white text-indigo-600 hover:bg-indigo-50">
                Get Started Now
              </Button>
            </Link>
            <Link to="/jobs">
              <Button size="lg" variant="ghost" className="text-white bg-white/10 border border-white/40 hover:bg-white/20">
                Browse Jobs
              </Button>
            </Link>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-slate-900 text-slate-400 py-12 border-t border-slate-800">
        <div className="container flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0">
          <div className="flex items-center space-x-2">
            <Briefcase className="h-6 w-6 text-indigo-500" />
            <span className="font-bold text-white text-lg">JobMatcher</span>
          </div>
          <p className="text-xs">© {new Date().getFullYear()} JobMatcher Platform. All rights reserved.</p>
          <div className="flex space-x-6 text-sm">
            <Link to="/jobs" className="hover:text-white transition-colors">Jobs</Link>
            <Link to="/matches" className="hover:text-white transition-colors">Matches</Link>
            <Link to="/skills" className="hover:text-white transition-colors">Skills</Link>
            <Link to="/profile" className="hover:text-white transition-colors">Profile</Link>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default LandingPage