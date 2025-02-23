# Dynamic Form Manager

Dynamic Form Manager is a comprehensive full-stack web application designed to streamline the creation, submission, and management of dynamic forms. It empowers organizations to configure service-specific forms, securely collect and manage data, and gain actionable insights through an intuitive admin dashboard. The application is built with a modern, scalable tech stack, ensuring a seamless user experience and robust performance.

---

## Key Features

### Backend Capabilities
- **Dynamic Form Configuration**: Load and manage service-specific form fields dynamically from the database.
- **Secure Data Submission**: Validate and store form submissions with robust security measures.
- **Data Management**: Retrieve submitted data with advanced filtering and sorting options.
- **Dashboard Analytics**: Visualize submission trends and service usage statistics through interactive charts.
- **Authentication & Authorization**: Secure endpoints with JWT-based authentication and role-based access control.
- **API Documentation**: Access detailed API documentation through Swagger UI."
- **Containerization**: Dockerize backend services for easy deployment and scalability.
- **Database Integration**: Utilize MySQL and MongoDB for relational and document-based data storage.
- **Testing**: Implement unit tests for backend services.
- **Observability**: Implemented logging, metrics and tracing for monitoring application health.

### Frontend Capabilities
- **Dynamic Form Rendering**: Automatically generate forms based on service configurations.
- **User-Friendly Interface**: Intuitive and responsive UI for form submission and data visualization.
- **Admin Dashboard**: Interactive metrics and charts for monitoring submissions and trends.
- **Responsive Design**: Optimized for desktop.

### DEVOPS Capabilities
- This project features a robust DevOps setup with an integrated Continuous Integration (CI) pipeline triggered for every pull request. The pipeline ensures high-quality code by running automated tests, performing code quality checks, validating builds, and scanning for vulnerabilities. It supports scalable configurations for different environments and provides real-time notifications and detailed reports on test results, code coverage, and build performance. With built-in security checks and compliance tools, the pipeline fosters collaboration, enhances code reliability, and ensures adherence to industry standards, enabling seamless development and deployment processes.

  <img width="1454" alt="Screenshot 2568-02-18 at 09 05 38" src="https://github.com/user-attachments/assets/cda5f15d-c3db-4b67-9454-96e75542826e" />


---

## Tech Stack

### Backend
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL (service and user data) and MongoDB (form submissions)
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

## Application Architecture

The application is structured to ensure modularity, scalability, and maintainability. Below is the directory structure:

```
dynamic-form-manager/
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

## DB Design

![Dynamic From Manager DB Design](https://github.com/user-attachments/assets/35b06628-8f51-44c2-b58d-118636dd051c)


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
   docker-compose build --no-cache
   ```
3. Start the backend services using Docker:
   ```bash
   docker-compose up -d
   ```

4. Add vault secrets in vault ui using `http://0.0.0.0:8200/ui/vault/auth?with=token` with Token = `root`
   and create secrets with name `dynamic-form-manager` and add below values 

```json
{
  "JWT_SECRET": "6a8e1e15d6b7892e8e6f13d7bc54b6c07d398c57923c8b2044973203e7023704",
  "MONGO_URI": "mongodb://mongodb:27017/submissions_db",
  "MYSQL_PASSWORD": "admin@2025",
  "MYSQL_URL": "jdbc:mysql://mysql:3306/finclutech_db",
  "MYSQL_USER": "admin"
}
```
<img width="1727" alt="Screenshot 2568-02-16 at 15 57 48" src="https://github.com/user-attachments/assets/940d13b9-2754-4d7c-ae44-fbdf67c914bb" />




5. Access the backend API at `http://localhost:8080`.
   

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

## Database Configuration(Not needed if you run docker compose file)

### MySQL
1. Create a database named `finclutech_db`.
2. Update the `application.yml` file in the backend with your MySQL credentials.

### MongoDB
1. Ensure MongoDB is running on `localhost:27017`.
2. Update the `application.yml` file in the backend with your MongoDB URI.
3. (Optional) Import sample data:
   ```bash
   docker exec -it {docker_id} mongoimport --db submissions_db --collection user_forms --file ./user_forms.json --jsonArray
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

<img width="1726" alt="Screenshot 2568-02-17 at 07 33 38" src="https://github.com/user-attachments/assets/6373fd98-16ac-4137-8a93-74e48d611ae3" />

---

## Demo

Explore the application in action through the demo video(available in demo folder)

### App Screenshots
![Screenshot 1](https://github.com/user-attachments/assets/7bebc4f2-87df-43ac-af57-5953f25396cd)
![Screenshot 2](https://github.com/user-attachments/assets/6dd4373f-48a8-457a-82e4-37e36361cdc8)
![Screenshot 3](https://github.com/user-attachments/assets/438036b5-c320-4aa2-b73b-01c21280e545)
![Screenshot 4](https://github.com/user-attachments/assets/f01a59a0-f3bf-4c21-80ac-6d14be187661)
![Screenshot 5](https://github.com/user-attachments/assets/f8f98068-0ca5-4146-9764-745d8717e275)
<img width="1726" alt="Screenshot 2568-02-16 at 17 44 48" src="https://github.com/user-attachments/assets/fa06b8c8-3860-4a28-af03-cb2feb4fe807" />

---

### Grafana Dashboard
Grafana Dashboard : http://localhost:3000/

<img width="1700" alt="Screenshot 2568-01-23 at 18 05 59" src="https://github.com/user-attachments/assets/df307916-8404-47ec-b171-40b78de0a13d" />
<img width="1726" alt="Screenshot 2568-01-23 at 18 07 11" src="https://github.com/user-attachments/assets/affd184a-ee55-4054-ad80-0d377d432a27" />



## Git Repository

Access the complete source code and additional resources on Git Ingest:  
[Dynamic Form Manager Git Repository](https://gitingest.com/raju4789/dynamic-form-manager/tree/main)

---

## Additional Notes

- **Admin Credentials**:  
  - Username: `admin`  
  - Password: `admin@4789`  

- **User Credentials**:  
  - Username: `user`  
  - Password: `admin@4789`  

- **Swagger Documentation**: Access API documentation at `http://localhost:8080/swagger-ui.html`.

---

## Contributors

- **Raju Methuku**  
  - [LinkedIn](https://www.linkedin.com/in/raju-m-l-n/)  
  - [Medium](https://medium.com/@narasimha4789)

---

## License

This project is licensed under the MIT License.
