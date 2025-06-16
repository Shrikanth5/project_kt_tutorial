# Project Management System

## Description
The Project Management System is a web application designed to facilitate project documentation and management. It allows administrators and project managers to add project documentation in specific project folders, while employees and sales teams can log in to access and view project details if they have the necessary permissions.

## Application Setup and Run

### Prerequisites
- Node.js (v14 or higher)
- npm (v6 or higher)
- Java 17 or higher
- Maven

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Create a `.env` file in the root directory with the following content:
   ```
   REACT_APP_API_BASE_URL=http://localhost:8080/api
   ```
4. Start the development server:
   ```bash
   npm start
   ```
   The application will be available at http://localhost:3000.

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend server will start on http://localhost:8080.

### Accessing the Application
- Use the provided credentials to log in as an administrator, project manager, or employee.
- Navigate to the project section to view or add project documentation. 
