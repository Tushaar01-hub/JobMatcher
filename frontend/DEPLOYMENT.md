# JobMatcher Frontend Deployment Guide

## Prerequisites

- Node.js 18+
- npm 9+
- A running instance of the JobMatcher backend

## Deployment Options

### Option 1: Static File Hosting (Recommended)

1. Build the frontend:
   ```bash
   npm run build
   ```

2. The build output will be in the `dist` folder

3. Deploy the contents of the `dist` folder to your preferred static file host:
   - Vercel
   - Netlify
   - AWS S3
   - Firebase Hosting
   - GitHub Pages
   - Any other static file hosting service

4. Configure environment variables:
   - `VITE_API_BASE_URL`: URL of your deployed backend
   - `VITE_FRONTEND_URL`: URL of your deployed frontend

### Option 2: Docker

1. Build the Docker image:
   ```bash
   docker build -t jobmatcher-frontend .
   ```

2. Run the container:
   ```bash
   docker run -p 5173:5173 -e VITE_API_BASE_URL=http://your-backend-url -e VITE_FRONTEND_URL=http://your-frontend-url jobmatcher-frontend
   ```

### Option 3: Node.js Server

1. Install dependencies:
   ```bash
   npm install
   ```

2. Build the application:
   ```bash
   npm run build
   ```

3. Start the server:
   ```bash
   npm run preview
   ```

## Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
VITE_API_BASE_URL=http://your-backend-url
VITE_FRONTEND_URL=http://your-frontend-url
```

## Backend Configuration

Ensure your backend is properly configured to:

1. Allow requests from your frontend's domain
2. Have CORS properly configured
3. Serve the frontend from the same domain as the backend (recommended)

## Post-Deployment

1. Verify all frontend features work with the deployed backend
2. Test authentication flows (local login and Google OAuth2)
3. Test all API endpoints and error handling
4. Verify responsive design on different devices

## Troubleshooting

If you encounter issues:

1. Check the browser console for errors
2. Verify environment variables are correctly set
3. Ensure the backend is running and accessible
4. Check network requests in the browser's developer tools

## Monitoring

Set up monitoring for your frontend deployment to track:

- Page load times
- Error rates
- User engagement metrics

## Continuous Deployment

For production environments, set up continuous deployment with:

- GitHub Actions
- GitLab CI/CD
- CircleCI
- Jenkins

## Scaling

For high-traffic applications, consider:

- CDN for static assets
- Server-side rendering if needed
- Load balancing
- Caching strategies

## Security

1. Keep all dependencies updated
2. Use HTTPS for all production deployments
3. Implement proper authentication and authorization
4. Regularly audit your deployment for vulnerabilities