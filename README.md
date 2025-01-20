# Dynamic Form Manager

Dynamic Form Manager is a full-stack web application designed to enable dynamic form generation, secure data submission, efficient data management, and insightful visualization. The application supports multiple services, each with its own unique form configuration, and provides a dashboard for administrators to monitor submissions and trends.

---

## Summary

Dynamic Form Manager is a robust solution for managing service-specific forms dynamically. It allows users to:
- Dynamically generate forms based on service configurations.
- Submit and validate form data securely.
- View submitted data with filtering, sorting, and pagination.
- Visualize key metrics and trends through an admin dashboard.

The application is built with a modern tech stack, ensuring scalability, security, and a user-friendly experience.

---

### Demo

[![Watch the demo](https://github.com/raju4789/dynamic-form-manager/blob/main/demo/demo_thumbnail.png)](https://github.com/raju4789/dynamic-form-manager/blob/main/demo/demo.mp4)


<img width="1728" alt="Screenshot 2568-01-19 at 22 29 07" src="https://github.com/user-attachments/assets/7bebc4f2-87df-43ac-af57-5953f25396cd" />


<img width="1727" alt="Screenshot 2568-01-19 at 22 30 06" src="https://github.com/user-attachments/assets/6dd4373f-48a8-457a-82e4-37e36361cdc8" />


<img width="1728" alt="Screenshot 2568-01-19 at 22 30 59" src="https://github.com/user-attachments/assets/438036b5-c320-4aa2-b73b-01c21280e545" />


<img width="1728" alt="Screenshot 2568-01-19 at 22 33 59" src="https://github.com/user-attachments/assets/f01a59a0-f3bf-4c21-80ac-6d14be187661" />


<img width="1704" alt="Screenshot 2568-01-19 at 22 34 24" src="https://github.com/user-attachments/assets/f8f98068-0ca5-4146-9764-745d8717e275" />


## Features

### Backend
- **Dynamic Form Configuration**: Load service-specific field configurations from the database.
- **Secure Data Submission**: Validate and store form submissions securely.
- **Data Retrieval**: Retrieve submitted data with filtering, sorting, and pagination.
- **Dashboard**: Visualize submission trends and service usage statistics.
- **Authentication**: Secure endpoints with JWT-based authentication.
- **Authorization**: Role based authorization.

### Frontend
- **Dynamic Form Rendering**: Generate forms dynamically based on service configurations.
- **User-Friendly UI**: Intuitive interface for form submission and data visualization.
- **Dashboard**: Interactive charts and metrics for administrators.
- **Responsive Design**: Optimized for both desktop and mobile devices.

---

## Tech Stack

### Backend
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL (for relational data) and MongoDB (for form submissions)
- **Authentication**: JWT
- **Build Tool**: Maven
- **Containerization**: Docker

### Frontend
- **Framework**: React
- **Styling**: Material-UI and Styled Components
- **State Management**: React Hooks
- **Build Tool**: Vite
- **Testing**: Vitest and React Testing Library

---

## Directory Structure

```
raju4789-dynamic-form-manager/
├── backend/                # Backend codebase
│   ├── src/                # Source code
│   ├── test/               # Unit tests
│   ├── docker-compose.yml  # Docker configuration for backend services
│   └── pom.xml             # Maven configuration
└── ui/                     # Frontend codebase
    ├── src/                # Source code
    ├── public/             # Static assets
    ├── Dockerfile          # Docker configuration for frontend
    └── package.json        # NPM configuration
```

---

## Setup Instructions

### Prerequisites
- **Java 17**
- **Node.js 18+**
- **Docker**
- **MySQL** and **MongoDB** (if not using Docker)

### Backend Setup
1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Start the backend services using Docker:
   ```bash
   docker-compose up -d
   ```
4. Access the backend API at `http://localhost:8080`.

### Frontend Setup
1. Navigate to the `ui` directory:
   ```bash
   cd ui
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Access the frontend at `http://localhost:8001`.

---

## Database Setup

### MySQL
1. Create a database named `finclutech_db`.
2. Update the `application.yml` file in the backend with your MySQL credentials.

### MongoDB
1. Ensure MongoDB is running on `localhost:27017`.
2. Update the `application.yml` file in the backend with your MongoDB URI.
3. Import the sample data from `backend/src/main/resources/user_forms.json`(OPTIONAL).

   ```
    docker exec -it {docker id} mongoimport --db submissions_db --collection user_forms --file ./user_forms.json --jsonArray
   ```

---

## API Endpoints

### Authentication
- **POST** `/api/v1/auth/authenticate`: Authenticate a user and retrieve a JWT token.
- **POST** `/api/v1/auth/validateToken`: Validate a JWT token.

### Form Management
- **GET** `/api/v1/manage/service`: Retrieve all services.
- **GET** `/api/v1/manage/service/fields/{id}`: Retrieve fields for a specific service.
- **GET** `/api/v1/manage/language`: Retrieve all supported languages.

### User Forms
- **POST** `/api/v1/form/submit`: Submit form data.
- **GET** `/api/v1/form/getFormData`: Retrieve submitted forms by service ID and user ID.

### Dashboard
- **GET** `/api/v1/dashboard/stats`: Retrieve dashboard statistics.

### API Documentation
Access the Swagger API documentation at: [Swagger API Documentation](http://localhost:8080/swagger-ui.html)

---

## Additional Notes

- **Swagger Documentation**: Access API documentation at `http://localhost:8080/swagger-ui.html`.
- **Admin Credentials**: Use the following credentials to log in as an admin:
  - Username: `admin`
  - Password: `admin@2025`
- **User Credentials**: Use the following credentials to log in as a user:
  - Username: `user`
  - Password: `admin@2025`

---

**Git Ingest** : https://gitingest.com/raju4789/dynamic-form-manager/tree/main

## Contributors

- **Raju Methuku**
  - [LinkedIn](https://www.linkedin.com/in/raju-m-l-n/)
  - [Medium](https://medium.com/@narasimha4789)

---

## License

This project is licensed under the MIT License.
```
