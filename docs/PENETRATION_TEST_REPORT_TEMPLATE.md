# Penetration Test Report — Secure Student Portal

**Author:** [Your name]  
**Test date:** [Date]  
**Application version / commit:** [Version or Git commit]  
**Scope:** Local Secure Student Portal at `http://127.0.0.1:8080`  
**Authorization:** Owner-operated local test environment  
**Testing window:** [Start/end]  

## 1. Executive summary
Summarize the purpose, overall risk, the most important validated findings, and the recommended priorities. Do not claim a vulnerability unless you reproduced it and recorded evidence.

## 2. Scope and methodology
- In scope: this local application, its web routes, authentication, authorization, input handling, and session behaviour.
- Out of scope: third-party systems, denial-of-service testing, social engineering, and destructive tests.
- Tools: Burp Suite Community Edition, ffuf, browser DevTools, and automated tests.
- Method: manual route mapping, access-control checks, input validation tests, session/cookie review, and evidence-based verification.

## 3. Environment
- OS:
- Java / Spring Boot version:
- Browser:
- Tool versions:
- Test account roles:
- Database:

## 4. Findings summary
| ID | Finding | Severity | Status |
|---|---|---|---|
| F-01 | [Title] | [Critical/High/Medium/Low/Informational] | [Open/Fixed/Retest] |

## 5. Detailed finding template
### F-01 — [Finding title]
- **Severity:** [Rating]
- **Affected route/component:** [Path and method]
- **Description:** [What was observed]
- **Impact:** [What an attacker could do and which data/actions are affected]
- **Prerequisites:** [Authentication/role/network conditions]
- **Proof of concept:** [Minimal reproducible steps against the authorized local app]
- **Evidence:** [Redacted request/response, screenshot, test output; no real secrets]
- **Expected result:** [Secure behaviour]
- **Actual result:** [Observed behaviour]
- **Remediation:** [Specific code/configuration fix]
- **Retest result:** [Pass/fail and date]

## 6. Test cases and results
| Test | Expected secure result | Actual result | Evidence |
|---|---|---|---|
| Anonymous user requests `/dashboard` | Redirect to login | [Fill in] | [Evidence] |
| Student requests `/admin/users` | Access denied/redirect; no user list | [Fill in] | [Evidence] |
| Registration submits a short password | Validation rejects it server-side | [Fill in] | [Evidence] |
| Registration attempts to choose ADMIN role | Role is ignored/rejected; new account remains STUDENT | [Fill in] | [Evidence] |
| Request without valid CSRF token submits a state change | Rejected | [Fill in] | [Evidence] |
| Session cookie flags reviewed | HttpOnly and SameSite present; Secure enabled for HTTPS deployment | [Fill in] | [Evidence] |
| Common route discovery against local app | Only intended public routes exposed | [Fill in] | [Evidence] |

## 7. Remediation plan
Prioritize fixes by severity and business impact. Include owner, target date, and retest status.

## 8. Conclusion
Summarize residual risk and whether retesting confirmed fixes.

## Appendix A — Evidence handling
Redact session cookies, passwords, authorization headers, personal data, and other secrets before including evidence. Store raw evidence securely and include only the minimum necessary reproduction detail.
