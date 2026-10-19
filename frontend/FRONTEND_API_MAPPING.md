# JobMatcher Frontend API Mapping

This document maps frontend features to backend API endpoints.

## Authentication

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| Login | `/auth/login` | POST | `{email, password}` | `{accessToken}` | JWT contains userId, email, and authorities |
| Register | `/auth/register` | POST | `{name, email, password}` | `{name, email}` | - |
| Google Login | `/oauth2/authorization/google` | GET | - | Redirect to frontend with token in query string | OAuth2 flow |
| Logout | - | - | - | - | Clear localStorage and redirect to login |

## Jobs

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| List Jobs | `/jobs` | GET | - | `List<JobResponseDTO>` | - |
| Job Details | `/jobs/{id}` | GET | - | `JobResponseDTO` | - |
| Filter Jobs | `/jobs/filters` | GET | Query parameters | `Page<JobResponseDTO>` | Supports pagination and sorting |
| Create Job | `/jobs` | POST | `JobRequestDTO` | `JobResponseDTO` | Admin only |
| Update Job | `/jobs/{id}` | PUT | `JobUpdateRequestDTO` | `JobUpdatedResponseDTO` | Admin only |
| Delete Job | `/jobs/{id}` | DELETE | - | - | Admin only |

## Skills

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| List Skills | `/skills` | GET | - | `List<SkillResponseDTO>` | - |
| Skill Details | `/skills/{id}` | GET | - | `SkillResponseDTO` | - |
| Create Skill | `/admin/skills` | POST | `SkillRequestDTO` | `SkillResponseDTO` | Admin only |
| Update Skill | `/admin/skills/{id}` | PUT | `SkillRequestDTO` | `SkillResponseDTO` | Admin only |
| Delete Skill | `/admin/skills/{id}` | DELETE | - | - | Admin only |

## User Skills

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| List User Skills | `/users/{userId}/skills` | GET | - | `List<UserSkillResponseDTO>` | - |
| Add Skill to User | `/users/{userId}/skills/{skillId}` | POST | - | `UserSkillResponseDTO` | - |
| Remove Skill from User | `/users/{userId}/skills/{skillId}` | DELETE | - | - | - |

## Matches

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| List Matches | `/users/{userId}/matches` | GET | - | `List<JobResponseMatchDTO>` | Sorted by match percentage |

## Users

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| User Profile | `/users/{userId}` | GET | - | `UserResponseDTO` | - |
| List Users | `/users` | GET | - | `List<UserResponseDTO>` | Admin only |

## Admin

| Frontend Feature | Backend Endpoint | HTTP Method | Request Body | Response | Notes |
|-------------------|-------------------|-------------|--------------|---------|-------|
| Admin Dashboard | `/admin` | GET | - | Admin dashboard data | Admin only |

## Error Handling

The frontend handles the following error responses:

- 400 Bad Request: Validation errors
- 401 Unauthorized: Invalid or expired token
- 403 Forbidden: Insufficient permissions
- 404 Not Found: Resource not found
- 409 Conflict: Duplicate resource
- 500 Internal Server Error: Server errors

The frontend displays appropriate error messages to the user and logs errors to the console.