# Review of Team 02's ESBot Project by Team 01

**Project / product:** ESBot 

**Review object(s):** System Description, Requirements, Specification, Implementation of Feature AskCourseQuestions 

**Review type:** Walkthrough (Feature Implementation) and Peer Review (System Description, Requirements, Specification) 

**Date (planned / actual):** 
Walkthrough: 01 May 2026, 
Peer Review: 27 April - 03 May 2026 

**Author(s):** Team 02 

**Reviewers:** Team 01

---

## 2. Master Plan (MP)

### 2.1 Masterplan — header

<!-- Fill the table below during planning. Redundant with the document header above is OK if you copy values here for a self-contained MP export. -->

| Field | Value |
|-------|-------|
| Review No. | REV-2026-001 |
| Project | ESBot |
| Project manager | - |
| Quality expert / manager | - |
| Moderator | Team 01 |
| Author(s) | Team 02 |

### 2.2 Review objects

| # | Review objects | Abbr. |
|---|----------------|-------|
| 1 | [docs/esbot.md](https://github.com/StudyBoat/esbot/blob/main/docs/esbot.md) | DES |
| 2 | [docs/spec/requirements.md](https://github.com/StudyBoat/esbot/blob/main/docs/spec/requirements.md) | REQ |
| 3 | [docs/spec/spec.md](https://github.com/StudyBoat/esbot/blob/main/docs/spec/spec.md)| SPEC |
| 4 | [docs/spec/data-model.md](https://github.com/StudyBoat/esbot/blob/main/docs/spec/data-model.md) | DataModel |
| 5 | [ESBot.Domain/Entities.Message.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Domain/Entities/Message.cs) | EntityMessage |
| 6 | [ESBot.Domain/Entities.UserSession.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Domain/Entities/UserSession.cs) | EntitySession |
| 7 | [ESBot.Tests/MessageTest.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/MessageTest.cs) | UnitTestsMessage|
| 8 | [ESBot.Tests/UserSessionTest.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/UserSessionTest.cs) | UnitTestsSession|
| 9 | [ESBot.Tests/features/AnswerCourseQuestions.feature](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/features/AnswerCourseQuestions.feature) | FeatureAnswerQuestion |
| 10 | [ESBot.Tests/steps/AnswerCourseQuestionsSteps.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/steps/AnswerCourseQuestionsSteps.cs) | StepsAnswerQuestion |
| 11 | [ESBot.Tests/steps/StepSupport.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/steps/StepSupport.cs) | FeatureTestHelper |

### 2.3 Reference documents

<!-- List materials reviewers need for context (requirements baseline, architecture, API spec, course brief). Use Abbr. for cross-references. -->

| # | Reference documents | Abbr. |
|---|---------------------|-------|
| 1 | [docs/TechStack.md](https://github.com/StudyBoat/esbot/blob/main/docs/TechStack.md) | TeamInfo |

### 2.4 Checklists / scenarios 

<!-- Specify which checklists, reading guides, or test scenarios reviewers should follow (course checklist, OWASP skim, API contract checks). -->

| # | Checklists / scenarios | Checklist content |
|---|-------------------------|------------------|
| 1 | Documentation Review | Is Documentation complete? Is code in accordance with documentation? |
| 2 | Code Review Checklist | Is code correct? Is code readable? Are errors handled? Are tests useful and reasonable? |

### 2.5 Reviewer assignment

<!-- Up to 10 reviewers: assign names and which chapters, objects, or checklists each person covers. Use Abbr. for initials or short IDs. -->

| Reviewer | Names (and chapters / checklists or scenarios assigned to the review) | Abbr. |
|:--------:|---------------------------------------------------------------------------|-------|
| 1 | Team 01 | DES, REQ, SPEC, DataModel, EntityMessage, EntitySession, UnitTestsMessage, UnitTestsSessions, FeatureAnswerQuestion, StepsAnswerQuestion , FeatureTestHelper |

### 2.6 Kick-off

<!-- Optional but recommended: align on scope, Master Plan, and context before individual preparation. -->

| Date / time / location |
|------------------------|
| 28 April 2026, 19:30, online Team Meeting |

### 2.7 Individual preparation

<!-- Planning figures for the preparation phase. “Optimal” rows support effort estimation (rate × time ≈ size). For documents without NLOC, substitute pages or words and state that in the Size unit cell. -->

| Individual preparation | Value | Unit |
|------------------------|-------|------|
| Submission of findings by | 30 April 2026, EOD | — |
| Size of review objects | <!-- Enter the total Non-Comment Lines of Code (NLOC) for code; for specification-only reviews, use pages or words and note the unit here. --> | NLOC |
| Optimal inspection rate | <!-- Target or measured inspection speed (e.g. NLOC per hour per reviewer). --> | NLOC/h |
| Optimal inspection time | <!-- Planned total inspection time in hours (placeholder 0.00 until estimated or measured). --> | h |


### 2.8 Review meeting

| Date / time / location |
|------------------------|
| 01 May 2026, 10:00, Webex Meeting |

### 2.9 Additional milestones (optional)

<!-- Not in the classic Masterplan sheet; useful for ESBot course tracking (rework, closure). -->

| Milestone | Planned date / time | Actual date / time |
|-----------|---------------------|---------------------|
| End of individual preparation | 30 April 2026 | 30 April 2026 |
| Rework deadline | - | - |
| Follow-up / closure | - | - |

---

## 3. List of findings (LoF)

Suggested values: **Type** — defect, question, suggestion; **Severity** — blocking, major, minor, editorial (define team scale); **Status** — open, accepted, rejected, deferred, fixed (update through meeting and rework).

| ID | Location (file / section / module) | Summary | Category | Type | Found by Reviewer | Status | Severity | Notes / meeting decision |
|----|-------------------------------------|---------|------|------|----------|--------|-------|--------------------------|
| F-001 | DataModel | Why are submitted answers sorted? | Logic | Question | H | <!-- --> | <!-- --> | <!-- --> |
| F-002 | DataModel | Why is the answer to QuizItems expected immediately? | Logic | Question | H | <!-- --> | <!-- --> | <!-- --> |
| F-003 | DataModel | Why are QuizItems stored as a List and not a Set? | Code Logic | Question | H | <!-- --> | <!-- --> | <!-- --> |
| F-004 | EntityMessage | Why is Message a Subtype of ImmutableProperties? | Code Logic | Question | J | <!-- --> | <!-- --> | <!-- --> |
| F-005 | EntityMessage | Why is both the Foreign Key as well as the whole Entity of Session listed as properties of Message? | Redundancy | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-006 | EntityMessage | What is the purpose of the method IEnumerable <string> GetImmutableProperties()? Why is the method defined in the same file as the entity properties?| Code Logic | Question | L, J | <!-- --> | <!-- --> | <!-- --> |
| F-007 | EntitySession | Why is both the Foreign Key as well as the whole Entity of User listed as properties of Session? | Redundancy | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-008 | EntitySession | Regarding the EndedAt property: What is the use of keeping a session saved in the system after it has been ended? | Logic | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-009 | EntitySession | Why are methods and enity properties defined in the same file? | Architecture | Question | J | <!-- --> | <!-- --> | <!-- --> |
| F-010 | EntitySession | Why is there no method to add QuizRequests similar to the AddMessage(Message message)? | Completeness | Question | J | <!-- --> | <!-- --> | <!-- --> |
| F-011 | EntitySession | Why is an exception thrown within the entity but not handled im service? | Error Handling | Question | H | <!-- --> | <!-- --> | <!-- --> |
| F-012 | UnitTestsMessage | Can the _context constant be given a more specific name? | Clean Code | Suggestion | J | <!-- --> | <!-- --> | <!-- --> |
| F-013 | UnitTestsMessage -  Message_Creation_WithValidData_ShouldSucceed() | Method name is a mix of pascal and snail case | Clean Code | Defect | L, J | <!-- --> | <!-- --> | <!-- --> |
| F-014 | UnitTestsMessage -  Message_Creation_WithValidData_ShouldSucceed() | createdAt <= UtcNow (line 47): Is this test actually useful? | Code Logic | Question | L, J | <!-- --> | <!-- --> | <!-- --> |
| F-015 | UnitTestsMessage | Consider renaming retrieved(line 130) to retrievedSession for better readability? | Clean Code | Suggestion | L | <!-- --> | <!-- --> |
| F-016 | FeatureAnswerCourseQuestion | Is it correct to mention in the feature file that a mock is used, given that feature files are supposed to be readable for people with not technological background knowledge? | Correctness | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-017 | FeatureAnswerCourseQuestion | Is it useful to test if the mock returns content as expected? | Logic | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-018 | StepsAnswerCourseQuestion - Given “I open a session …” | Why are states set as null instead of actually creating a session entity? | Code Logic | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-019 | StepsAnswerCourseQuestion | Unclear naming: What is string p0? | Clean Code | Defect | L | <!-- --> | <!-- --> | <!-- --> |
| F-020 | StepsAnswerCourseQuestion | Is actually the application logic being tested ot the test logic? | Logic | Question | L | <!-- --> | <!-- --> | <!-- --> |
| F-021 | StepsAnswerCourseQuestion and FeatureTestHelper| Should not several parts of the FeatureTestHelper be moved to a service? Are the tests not actually testing the FeatureTestHelper's logic instad of the application logic? | Logic | Question | Team 01 | <!-- --> | <!-- --> | <!-- --> |
| F-022 | DES - High-Level Expectations| "AI-generated responses should be embedded in a controlled interaction flow, ensuring clarity and usability." - What is meant with "controlled interaction flow" in this context?| Clarity | Question | J | <!-- --> | <!-- --> | <!-- --> |
| F-023 | DES - Summary | Is the summary fitting for the project? I think it may be too focused on the context of the Software Testing lab.| Logic | Question | J | <!-- --> | <!-- --> | <!-- --> |
| F-024 | REQ - Non-Functional Requirements | "Response times of 2-5 seconds ..." -> Is this not a bit too ambitious? | Feasibility | Question | L, J | <!-- --> | <!-- --> | <!-- --> |
| F-025 | SPEC - Success Criteria | SC-001: -> Is this realistic? | Feasibility | Question | L | <!-- --> | <!-- --> | <!-- --> |

---

## 4. Data Summary (DS)

<!-- Key metrics for this review. Fill after preparation and/or after rework. -->

| Metric | Value | Notes |
|--------|-------|-------|
| Size of review object | 11 files (4 docs, 2 code files, 5 test files) <!-- e.g. pages, LOC, #requirements --> | <!-- --> |
| Preparation effort (hours, optional) | 2-4 | <!-- --> |
| Number of findings (initial) | 25 | <!-- --> |
| Number of findings after meeting | <!-- --> | <!-- --> |
| Rework effort (hours, author) | <!-- --> | <!-- --> |
| Re-inspection required? | no | <!-- --> |

---

## 5. Review Report (RR)

### 5.1 Summary

<!-- Short executive summary: object reviewed, outcome, overall quality impression. -->

### 5.2 Review outcome

- **Review object state after review:** <!-- e.g. accepted with changes, requires re-inspection, not accepted -->
- **Major risks or themes:** <!-- bullet list -->

### 5.3 Decisions and follow-up

| Topic | Decision | Responsible | Due date |
|-------|----------|-------------|----------|
| <!-- --> | <!-- --> | <!-- --> | <!-- --> |

### 5.4 Positive observations (optional)

<!-- What was done well; good practices worth keeping. -->

### 5.5 Lessons learned (optional)

<!-- Process improvements for the next review. -->

### 5.6 Sign-off

| Role | Name | Signature / date |
|------|------|------------------|
| Moderator | Team 01 | <!-- --> |
| Author | Team 02 | <!-- --> |

---

<!-- End of template -->