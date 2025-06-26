#!/bin/bash

echo "Starting Bookstore API Tests..."
echo "====================================="

echo
echo "Running Unit Tests..."
echo "====================================="
./mvnw test -Dtest="*Test" -Dspring.profiles.active=test

echo
echo "Running Integration Tests..."
echo "====================================="
./mvnw test -Dtest="*IntegrationTest" -Dspring.profiles.active=test

echo
echo "Running All Tests with Coverage..."
echo "====================================="
./mvnw clean test -Dspring.profiles.active=test

echo
echo "Tests completed! Check target/site/jacoco/index.html for coverage report." 