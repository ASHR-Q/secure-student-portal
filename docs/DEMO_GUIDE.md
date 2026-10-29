# Secure Student Portal — Demonstration Guide

This guide provides a short local demonstration of the implemented authentication and authorization controls. Adjust the duration and format to the submission requirements.

## Before the demonstration

Requirements: Java 21 and Maven 3.8+.

1. Run the automated suite: `mvn test`.
2. Start the portal with a unique, local-only administrator credential:

   ```bash
   PORTAL_ADMIN_USERNAME=capstoneadmin PORTAL_ADMIN_PASSWORD='Use-A-Long-Unique-Password-Here!' mvn spring-boot:run
   ```

3. Open `http://localhost:8080`. Do not reuse real credentials or show secrets in screenshots or recordings.

## Suggested walkthrough

1. Show the public home and registration pages.
2. Register a student with a password of at least 12 characters, then sign in and show the authenticated dashboard.
3. Show that a student cannot access `/admin/users`.
4. Sign in as the configured administrator and show that `/admin/users` is available.
5. Briefly explain the corresponding automated checks: password hashing, server-side validation, role assignment, login behavior, admin access control, and CSRF enforcement.
6. Close by stating the test limitation clearly: automated tests were run; manual/external penetration testing was not performed.

Stop the local server with `Ctrl+C` after the demonstration. Confirm any required presentation format, duration, and evidence-submission rules with the course or submission instructions.
