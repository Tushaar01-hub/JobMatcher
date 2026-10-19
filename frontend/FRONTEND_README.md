# JobMatcher Frontend

## Overview

This is the frontend application for JobMatcher, a job-matching platform that connects users with relevant job opportunities based on their skills, experience level, and preferences.

## Tech Stack

- React 18
- Vite 5
- Tailwind CSS 3
- React Router 6
- Axios 1
- Lucide React (icons)
- Radix UI (primitives)
- JWT-decode

## Project Structure

```
jobmatcher-frontend/
├── public/
├── src/
│   ├── api/            # API service layer
│   ├── components/     # Reusable components
│   ├── context/        # React context providers
│   ├── hooks/          # Custom hooks
│   ├── layouts/        # Layout components
│   ├── pages/          # Page components
│   ├── routes/         # Route configuration
│   ├── utils/          # Utility functions
│   ├── App.jsx         # Main app component
│   └── main.jsx        # Entry point
├── .env.example    # Environment variables example
├── tailwind.config.js
├── vite.config.js
└── package.json
```

## Setup Instructions

### Prerequisites

- Node.js 18+
- npm 9+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Tushaar01-hub/JobMatcher.git
   cd JobMatcher/frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create `.env` file:
   ```bash
   cp .env.example .env
   ```

4. Configure environment variables in `.env`:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   VITE_FRONTEND_URL=http://localhost:5173
   ```

### Running the Application

```bash
npm run dev
```

The application will be available at `http://localhost:5173`

### Building for Production

```bash
npm run build
```

## Backend Dependency

This frontend requires the JobMatcher backend to be running. The backend repository is available at:

https://github.com/Tushaar01-hub/JobMatcher

## Authentication Flow

1. **Local Login**:
   - User submits email/password
   - Backend returns JWT
   - Frontend stores JWT in localStorage
   - JWT contains userId, email, and authorities

2. **Google OAuth2**:
   - User clicks Google login button
   - Redirects to Google OAuth2 flow
   - After authentication, backend redirects to frontend with token in query string
   - Frontend extracts token and stores it

## API Endpoints

The frontend communicates with the backend using the following endpoints:

### Authentication

- `POST /auth/register`
- `POST /auth/login`
- `GET /oauth2/authorization/google` (Google OAuth2 flow)

### Jobs

- `GET /jobs`
- `GET /jobs/{id}`
- `GET /jobs/filters` (with query parameters)

### Skills

- `GET /skills`
- `GET /skills/{id}`
- `POST /admin/skills` (admin only)
- `PUT /admin/skills/{id}` (admin only)
- `DELETE /admin/skills/{id}` (admin only)

### User Skills

- `GET /users/{userId}/skills`
- `POST /users/{userId}/skills/{skillId}`
- `DELETE /users/{userId}/skills/{skillId}`

### Matches

- `GET /users/{userId}/matches`

## Frontend Architecture

The frontend follows a clean architecture with:

1. **API Layer**: Centralized API service with Axios instance
2. **Context Providers**: Authentication and toast notifications
3. **Component Library**: Reusable UI components
4. **Page Components**: Page-level components
5. **Layout Components**: Main layout with navbar
6. **Routing**: React Router with protected routes

## Key Features

- **Job Matching**: Shows match percentage, matched skills, and missing skills
- **Dynamic Job Filtering**: Multiple filter options with pagination
- **User Profile Management**: View and edit profile information
- **Skill Management**: Add and remove skills
- **Admin Dashboard**: Manage jobs and skills (admin only)
- **Responsive Design**: Works on desktop and mobile devices

## Deployment

To deploy the frontend:

1. Build the application:
   ```bash
   npm run build
   ```

2. The build output will be in the `dist` folder

3. Serve the contents of the `dist` folder using your preferred static file server

## Contributing

Contributions are welcome. Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.