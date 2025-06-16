# Project Documentation

This directory contains comprehensive documentation for the project's architecture and quality assurance processes.

## Directory Structure

```
docs/
├── architecture/
│   └── ARCHITECTURE.md    # System architecture documentation with diagrams
├── quality/
│   └── QUALITY_CHECKS.md  # Quality assurance and testing documentation
└── README.md             # This file
```

## Documentation Overview

### Architecture Documentation
The architecture documentation (`architecture/ARCHITECTURE.md`) provides:
- System overview and high-level architecture
- Frontend and backend component details
- Security architecture
- Data flow diagrams
- Deployment architecture
- Quality assurance architecture

### Quality Checks Documentation
The quality checks documentation (`quality/QUALITY_CHECKS.md`) covers:
- Testing strategy
- Code quality metrics
- Quality gates
- Monitoring and metrics
- Security quality checks
- Documentation requirements
- CI/CD pipeline
- Performance testing
- Accessibility standards

## How to Use This Documentation

1. Start with the architecture documentation to understand the system design
2. Review the quality checks documentation to understand testing and quality requirements
3. Use the diagrams as reference for system design and implementation
4. Follow the quality gates and metrics for maintaining code quality

## Diagrams

All diagrams in the documentation are created using Mermaid.js and can be viewed:
- Directly in GitHub (if using GitHub)
- Using any Mermaid.js compatible viewer
- Using the Mermaid Live Editor (https://mermaid.live)

## Contributing to Documentation

When updating the documentation:
1. Keep diagrams up to date with system changes
2. Maintain consistent formatting
3. Update quality metrics as requirements change
4. Add new sections as needed
5. Ensure all diagrams are properly formatted in Mermaid.js syntax

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