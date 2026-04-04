# ESBot Project Constitution

<!--
SYNC IMPACT REPORT
=================
Version: 0.0.0 → 1.0.0 (MAJOR - Initial constitution ratification)
Ratification Date: 2026-04-04
Last Amended: 2026-04-04

Core Principles (5 principles):
- I. Test-First Development (non-negotiable TDD discipline)
- II. Educational Excellence & Transparency
- III. Comprehensive Test Coverage by Tier
- IV. Clear Test-to-Requirement Traceability
- V. Three-Tier Testable Architecture

Added Sections:
- Development Standards (flexible technology stack, quality gates)
- Lab Exercise Integration (how testing integrates with course)

Template Updates Required:
- ✅ plan-template.md: References "Constitution Check" gate - now defined
- ⚠ spec-template.md: Consider referencing mandatory test structure
- ⚠ tasks-template.md: May clarify testing task types per principles
-->

## Core Principles

### I. Test-First Development (NON-NEGOTIABLE)

Test-driven development (TDD) is mandatory for all code. Tests MUST be written before implementation, following the Red-Green-Refactor cycle: (1) write failing tests that specify requirements, (2) implement minimal code to pass tests, (3) refactor for clarity. No code merges without passing test coverage demonstrating the feature works as specified.

### II. Educational Excellence & Transparency

ESBot is a Software Testing course project. Code must educate learners about testing practices. Test implementations MUST include clear rationale explaining the testing technique used (unit, integration, mock-based, etc.). Intentional defects for learning must be marked with `// INTENTIONAL DEFECT [reason]: [description]` and cross-referenced in exercise write-ups. Code review evaluates educational clarity alongside correctness.

### III. Comprehensive Test Coverage by Tier

ESBot's three-tier architecture (frontend, backend, database) must be tested independently. Unit tests verify individual components; integration tests verify component interactions, API contracts, and database persistence; acceptance tests validate end-to-end workflows. External integrations (LLM services) must support mock/stub implementations for reliable CI/CD. Target minimum: 80% code coverage with focus on critical learning paths (authentication, session persistence, quiz workflows).

### IV. Clear Test-to-Requirement Traceability

Every user story in spec.md maps to at least one automated test case. Tests must be named to clearly indicate what requirement they validate. Test documentation references the specification section(s) that justify the test. Code review verifies this traceability; tests without clear requirement links are rejected and require rework.

### V. Three-Tier Testable Architecture

Frontend and backend tiers must be independently testable and deployable. Component/unit tests target individual units; integration tests verify tier interactions with other systems (APIs, databases, external services). Tiers communicate via well-defined contracts; tight coupling that prevents independent testing is a code smell. LLM service integration includes comprehensive mocks to prevent non-deterministic CI failures.

## Development Standards

**Target Platform**: Web application accessible via browser; no installation required.

**Architecture**: Three-tier design with frontend (user interface), backend (application logic and API), and database (persistent storage). LLM services integrated via backend as optional dependency.

**Technology Stack**: Teams choose technology based on project needs. Examples include Spring Boot/Angular (current team choice), Python/React, Node.js/Vue, etc. Technology decisions must be documented in feature specifications and justified in PRs if new languages/frameworks are introduced.

**Testing Frameworks**: Teams select test frameworks appropriate to their tech stack. Examples: JUnit/Mockito (Java), pytest (Python), Jest/Vitest (JavaScript), Selenium/Cypress (E2E).

**CI/CD**: GitHub Actions with mandatory test gates. All PRs must have passing tests before merge (zero test failures allowed).

**Code Quality Gates**:
- All tests must pass in CI (non-negotiable)
- Minimum 80% code coverage for new code
- Error handling tested (happy path + error scenarios)
- No hardcoded secrets or production data

## Lab Exercise Integration

Each lab submission includes updated test suites demonstrating the specific learning objective. Submissions must include: (1) executable test suite with passing results, (2) test coverage report, (3) write-up explaining testing strategy employed and why it was chosen, (4) self-assessment of test quality and coverage gaps. Tests serve as executable specification and learning artifact.

## Governance

This constitution is the foundational governance document for ESBot. All contributors acknowledge and adhere to these principles. The constitution supersedes conflicting informal guidance.

**Amendment Procedure**: Changes to this document require (1) documented rationale explaining the governance problem or improvement, (2) team consensus, (3) migration plan if existing code must be updated.

**Version Policy**: Follow semantic versioning:
- MAJOR: Removal or fundamental redefinition of a principle
- MINOR: New principle or significant expansion of existing guidance
- PATCH: Clarifications, wording refinements, non-semantic corrections

**Compliance Review**: All PRs verify constitutional alignment before merge. Violations are addressed in review feedback, discussing which principle applies and why the change is needed.

**Version**: 1.0.0 | **Ratified**: 2026-04-04 | **Last Amended**: 2026-04-04
