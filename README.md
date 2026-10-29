# Secure Student Portal — Capstone

A local training application for secure web development and authorized penetration testing. Stack: Java 21, Spring Boot, Spring Security, Thymeleaf, Spring Data JPA, H2, Maven.

## Requirements
- Java 21 (`java -version`)
- Maven 3.8+ (`mvn -version`)

## Run locally
From this project directory:

```bash
mvn clean test
PORTAL_ADMIN_USERNAME=capstoneadmin PORTAL_ADMIN_PASSWORD='Use-A-Long-Unique-Password-Here!' mvn spring-boot:run
```

Open `http://localhost:8080`. Register a student account, then sign in as the configured admin. Use a unique local-only admin password; do not reuse a real password. Admin bootstrap creates the account only when both environment variables are provided. If the admin already exists, changing the environment variable does not reset its password.

To stop the app, press `Ctrl+C`.

## Implemented controls
- Spring Security form authentication.
- BCrypt password hashing (work factor 12).
- Role-based server-side protection for `/admin/**`.
- CSRF protection remains enabled; forms use Thymeleaf integration.
- Server-side Bean Validation for registration.
- Unique usernames and generic duplicate-username response.
- Session ID rotation on authentication and one concurrent session per account.
- HttpOnly and SameSite=Lax session cookie settings.
- No stack traces or internal exception messages in browser error pages.
- Student registration always assigns `STUDENT`; a user cannot choose `ADMIN` through the registration form.

## Verification and demonstration
- Run automated security-flow and smoke tests with `mvn test`.
- See [the security verification report](docs/SECURITY_VERIFICATION_REPORT.md) for tested behavior, results, and limitations.
- See [the local demonstration guide](docs/DEMO_GUIDE.md) for a suggested walkthrough.
- Automated checks are not a substitute for the authorized manual penetration-testing steps below.

## Known limitations / next hardening tasks
This is a capstone starter, not production-ready software. It uses a local H2 database and HTTP for localhost. For deployment, use PostgreSQL, HTTPS, `Secure` cookies, secrets management, rate limiting / account lockout, security headers with a tested Content Security Policy, audit logging, database migrations, and dependency scanning. Add password reset and email verification only if in scope. Avoid real personal data.

## Two-week plan
1. Build/run the app and understand its routes and database.
2. Review validation, authentication, password hashing, sessions, and RBAC.
3. Automated tests for registration, role restrictions, and CSRF are implemented and run with `mvn test`; session behavior still needs coverage.
4. Run an authorized local manual test using browser DevTools and Burp Suite Community.
5. Use ffuf only against `http://127.0.0.1:8080` (your own local app) to discover routes.
6. Record reproducible evidence, severity, impact, and remediation in `docs/PENETRATION_TEST_REPORT_TEMPLATE.md`.

## Safe test scope
Only test this app running locally or systems for which you have explicit permission. Do not point scanners at public systems without authorization.

## Git
```bash
git init
git add .
git commit -m "Build secure student portal capstone starter"
```
