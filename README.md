# EventLink Backend# EventLink Backend - Project 3 CST 438# EventLink Backend - Project 3 CST 438



A Spring Boot backend API for EventLink - a social event discovery and ticket comparison platform.



## What is EventLink?Backend API for EventLink - Smart Ticket Comparison & Social Event PlannerBackend API for EventLink - Smart Ticket Comparison & Social Event Planner



EventLink is a mobile application that helps users discover local events, compare ticket prices across multiple platforms, and coordinate event attendance with friends and family. Think of it as combining the event discovery of Eventbrite with the price comparison of Kayak, plus social features to attend events together.



## Key Features## 📋 Project Overview## 🎯 Available Issues (Unassigned)



### Event Discovery & Search

- Search for local events by location, date, category, and keywords

- Browse trending and popular events in your areaEventLink is a social event planning application that helps users discover events, compare ticket prices, and coordinate with friends. This backend provides REST API endpoints for the React Native frontend.**Total Issues: 18 unassigned tasks ready to be picked up!**

- Filter events by price range, venue type, and distance



### Price Comparison

- Compare ticket prices across multiple platforms (Ticketmaster, SeatGeek, etc.)## 🛠️ Technology Stack```

- Show lowest, average, and highest prices for each event

- Track price changes and get alerts for price drops🔧 Database & Infrastructure (5 issues)



### Social Event Planning- **Framework:** Spring Boot 3.x with Java 17├── #12 Add PostgreSQL and JPA dependencies to build.gradle

- Create user profiles and connect with friends/family

- See what events your connections are attending- **Security:** Spring Security with OAuth2 (Google Authentication)├── #13 Configure Supabase database connection in application.properties  

- Coordinate group attendance and share events

- Social feeds showing friends' event activity- **Database:** PostgreSQL (Supabase)├── #14 Create User entity with JPA annotations



### User Management- **Build Tool:** Gradle├── #15 Create UserRepository interface for database operations

- OAuth2 authentication (Google, GitHub)

- User profiles with preferences and event history- **Deployment:** Heroku with GitHub Actions CI/CD└── #22 Create EventAttendance entity and repository

- Friend/family connection system

- Event attendance tracking- **Testing:** JUnit 5



## Technology Stack🚀 API Endpoints (6 issues)



- **Backend Framework:** Spring Boot 3.x with Java 17## 🚀 Current Features├── #16 Create GET /api/users/profile endpoint

- **Database:** PostgreSQL (Supabase)

- **Authentication:** OAuth2 + JWT tokens├── #21 Create GET /api/events/search endpoint

- **External APIs:** Ticketmaster, SeatGeek

- **Deployment:** Heroku with CI/CD### ✅ Implemented├── #23 Create POST /api/events/{id}/attend endpoint

- **Security:** Spring Security

- OAuth2 authentication with Google├── #24 Create GET /api/events/attending endpoint

## API Overview

- Basic Spring Security configuration├── #26 Create GET /api/events/friends endpoint

This backend provides RESTful endpoints for:

- User authentication and profile management- Heroku deployment setup with CI/CD└── #25 Create Friend entity for user connections

- Event search and retrieval

- Price comparison data- Environment variable configuration

- Social features (friends, event sharing)

- Event attendance tracking- Basic project structure🔐 Authentication & Security (2 issues)  



## Getting Started├── #17 Add JWT token generation after OAuth2 login



### Prerequisites### 🚧 In Development└── #18 Configure CORS for React Native frontend

- Java 17+

- PostgreSQL database- Database integration with Supabase

- OAuth2 credentials (Google/GitHub)

- User management endpoints🌐 External APIs (2 issues)

### Quick Setup

```bash- Event search and management├── #19 Set up Ticketmaster API client service

git clone https://github.com/Project-tres-fiesta/project-3-group-8-backend.git

cd project-3-group-8-backend- External API integration (Ticketmaster/SeatGeek)└── #20 Set up SeatGeek API client service

./gradlew bootRun

```- JWT token generation for mobile



Access the API at `http://localhost:8080`- Social features (friends, event sharing)📋 Testing & Documentation (3 issues)



## Project Context├── #27 Expand JUnit test suite for service classes



This is the backend component for CST 438 Project 3 at California State University, Monterey Bay. The project demonstrates full-stack development with Spring Boot backend and React Native frontend integration.## 🔧 Setup & Installation├── #28 Create Postman collection for all API endpoints



## Team└── #29 Add OpenAPI/Swagger documentation for all endpoints



- **Backend Development:** Spring Boot API, database design, external API integration### Prerequisites```

- **Frontend Development:** React Native mobile app (separate repository)

- **DevOps:** Heroku deployment, CI/CD pipeline setup- Java 17+

- Gradle 7+## 🔗 Quick Links to Issues

- PostgreSQL database (Supabase account)

- Google OAuth2 credentials**High Priority (Start Here):**

- [#12 - Add Database Dependencies](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/12) ⏱️ 30min

### Local Development- [#13 - Configure Database Connection](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/13) ⏱️ 1-2hrs  

1. Clone the repository- [#17 - JWT Token Generation](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/17) ⏱️ 2-3hrs

```bash- [#21 - Event Search Endpoint](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/21) ⏱️ 2-3hrs

git clone https://github.com/Project-tres-fiesta/project-3-group-8-backend.git

cd project-3-group-8-backend**Medium Priority:**

```- [#16 - User Profile Endpoint](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/16) ⏱️ 1-2hrs

- [#19 - Ticketmaster API Client](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/19) ⏱️ 3-4hrs

2. Create `.env` file with your credentials:- [#20 - SeatGeek API Client](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/20) ⏱️ 3-4hrs

```env- [#23 - 'I'm Going' Endpoint](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/23) ⏱️ 2hrs

GOOGLE_CLIENT_ID=your_google_client_id- [#28 - Postman Collection](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues/28) ⏱️ 2-3hrs

GOOGLE_CLIENT_SECRET=your_google_client_secret

DATABASE_URL=your_supabase_connection_string**All Issues:** [View All Issues](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues)

```

## 🏗️ Architecture Overview

3. Run the application:

```bash- **Framework:** Spring Boot 3.5.7 with Java 17

./gradlew bootRun- **Database:** Supabase PostgreSQL  

```- **Authentication:** OAuth2 (Google/GitHub) → JWT for mobile

- **External APIs:** Ticketmaster + SeatGeek for event data

The application will be available at `http://localhost:8080`- **Deployment:** Heroku with CI/CD via GitHub Actions



## 📚 API Documentation## 🚀 Getting Started



### Authentication Endpoints1. **Pick an issue** from the list above

- `GET /` - Public home page2. **Assign yourself** to the issue  

- `GET /oauth2/authorization/google` - Google OAuth2 login3. **Create a branch** from `main`

4. **Work on the task** following the acceptance criteria

### Planned API Endpoints5. **Create a PR** when done and link it to the issue

- `GET /api/users/profile` - Get user profile
- `GET /api/events/search` - Search events
- `POST /api/events/{id}/attend` - Mark attending event
- `GET /api/events/attending` - Get user's events

## 🤝 Contributing

1. Check [Issues](https://github.com/Project-tres-fiesta/project-3-group-8-backend/issues) for available tasks
2. Assign yourself to an issue
3. Create a feature branch: `git checkout -b feature/issue-number`
4. Make your changes and commit: `git commit -m "feat: description"`
5. Push and create a Pull Request
6. Link your PR to the issue

## 📦 Deployment

The application automatically deploys to Heroku when PRs are merged to `main` branch via GitHub Actions.

## 👥 Team Members

- **Jorge Barrera** (@JorgeBarr983) - OAuth2 Implementation
- **JZavala210** (@JZavala210) - DevOps & Deployment Setup

## 📄 License

This project is for educational purposes as part of CST 438 at California State University, Monterey Bay.