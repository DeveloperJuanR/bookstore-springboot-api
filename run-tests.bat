@echo off
echo Starting Bookstore API Tests...
echo =====================================

echo.
echo Running Unit Tests...
echo =====================================
call mvnw.cmd test -Dtest="*Test" -Dspring.profiles.active=test

echo.
echo Running Integration Tests...
echo =====================================
call mvnw.cmd test -Dtest="*IntegrationTest" -Dspring.profiles.active=test

echo.
echo Running All Tests with Coverage...
echo =====================================
call mvnw.cmd clean test -Dspring.profiles.active=test

echo.
echo Tests completed! Check target/site/jacoco/index.html for coverage report.
pause 