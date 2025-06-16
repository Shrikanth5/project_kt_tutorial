#!/bin/bash

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Java 17 is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 first."
    exit 1
fi

# Build and run the application
echo "Building and starting the backend application..."
./mvnw clean install
./mvnw spring-boot:run 