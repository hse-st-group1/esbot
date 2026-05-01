# Review of Team 02's ESBot Project by Team 01

**Project / product:** ESBot 

**Review object(s):** System Description, Requirements, Specification, Implementation of Feature AskCourseQuestions 

**Review type:** Walkthrough (Feature Implementation) and Peer Review (System Description, Requirements, Specification) 

**Date (planned / actual):** 
Walkthrough: 01 May 2026, 
Peer Review: 27 April - 01 May 2026 

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
| 8 | [ESBot.Tests/features/AnswerCourseQuestions.feature](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/features/AnswerCourseQuestions.feature) | FeatureAnswerQuestion |
| 9 | [ESBot.Tests/steps/AnswerCourseQuestionsSteps.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/steps/AnswerCourseQuestionsSteps.cs) | StepsAnswerQuestion |
| 10 | [ESBot.Tests/steps/StepSupport.cs](https://github.com/SSJGodGogeta/ESBot/blob/master/ESBot.Tests/steps/StepSupport.cs) | StepSupport |

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
| 1 | Team 01 | DES, REQ, SPEC, DataModel, EntityMessage, EntitySession, UnitTestsMessage, UnitTestsSessions, FeatureAnswerQuestion, StepsAnswerQuestion , StepSupport |

### 2.6 Kick-off

<!-- Optional but recommended: align on scope, Master Plan, and context before individual preparation. -->

| Date / time / location |
|------------------------|
| 28 April 2026, 19:30, online Team Meeting |

### 2.7 Individual preparation

<!-- Planning figures for the preparation phase. “Optimal” rows support effort estimation (rate × time ≈ size). For documents without NLOC, substitute pages or words and state that in the Size unit cell. -->

| Individual preparation | Value | 
|------------------------|-------|
| Submission of findings by | 30 April 2026, EOD | 

### 2.8 Review meeting

| Date / time / location / duration |
|------------------------|
| 01 May 2026, 10:00, Webex Meeting, 1h|

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

| ID | Location (file / section / module) | Summary | Category | Type | Author's Response | Status | Severity | Meeting Decision |
|----|----|----|----|----|----|----|----|----|
| F-001 | DataModel | Why are submitted answers sorted? | Logic | Question  | - | <!-- --> | <!-- --> | <!-- --> |
| F-002 | DataModel | Why is the answer to QuizItems expected immediately? | Logic | Question | - | <!-- --> | <!-- --> | <!-- --> |
| F-003 | DataModel | Why are QuizItems stored as a List and not a Set? | Code Logic | Question | - | <!-- --> | <!-- --> | <!-- --> |
| F-004 | EntityMessage | Why is Message a Subtype of ImmutableProperties? | Code Logic | Question | The Interface ImmutableProperties was used to gather all entity properties of which the values are supposed to be not updateable (see BaseMethod.cs). This is supposed to make database requests easier.  | closed | - | - |
| F-005 | EntityMessage | Why is both the Foreign Key as well as the whole Entity of Session listed as properties of Message? | Redundancy | Question | C# Specific Programming/Style dependent on Framework | closed | - | - |
| F-006 | EntityMessage | Why is the maximum length of a message 4000 char? | Contraint | Question | Team Choice | closed | - | - |
| F-007 | EntitySession | Why is both the Foreign Key as well as the whole Entity of User listed as properties of Session? | Redundancy | Question | --> F-005 | closed | - | - |
| F-008 | EntitySession | Regarding the EndedAt property: What is the use of keeping a session saved in the system after it has been ended? | Logic | Question | Session is used to keep track of a conversation (list of messages) as well as to mock Keycloak (EndedAt). EndedAt is extended if user sends a new message. Ended Sessions are kept in the system, in case the user wants to access the conversation again later.| open - Suggestion: Rename EndedAt to LastAccessedAt | minor | Consideration to rename property |
| F-009 | EntitySession | Why are methods and enity properties defined in the same file? | Architecture | Question | Add Message is not aactually a necessary method, but was propably added to help with the unit tests. The used EntityFrameworkCore should already provide this functionality.  | open | minor | Check if method is even necessary |
| F-010 | EntitySession | Why is there no method to add QuizRequests similar to the AddMessage(Message message)? | Completeness | Question | --> F-009 | closed | - | - |
| F-011 | EntitySession | Why is an exception thrown within the entity but not handled im service? | Error Handling | Question | --> F-009 | closed | - | - |
| F-012 | UnitTestsMessage | What is the _context constant used for? | Logic | Question | Used to specify Entities, Relationships and Database rules = Interface to Database | closed | - | - |
| F-013 | UnitTestsMessage | Why do the unit tests even need a database connection? | Logik | Question | Lab Exercise only specifies that the unit test should not use a persistent database. Therfore an in-memory databse was used.| closed | - | - |
| F-014 | UnitTestsMessage -  Message_Creation_WithValidData_ShouldSucceed() | Method name is a mix of pascal and snail case | Clean Code | Defect | Method name is output to console with space instad of underscore so that the output is more readable.| closed | - | - |
| F-015 | UnitTestsMessage -  Message_Creation_WithValidData_ShouldSucceed() | createdAt <= UtcNow (line 47): Is this test actually useful? | Code Logic | Question | Test is supposed to check wether timestamp was saved at all. Incorrect timestamp is assumed to be impossible.| closed | - | - |
| F-016 | UnitTestsMessage | Consider renaming retrieved (line 130) to retrievedSession for better readability? | Clean Code | Suggestion | IDE shows Type. | open | - | Consider to rename to retrievedSession. |
| F-017 | FeatureAnswerCourseQuestion | Is it correct to mention in the feature file that a mock is used, given that feature files are supposed to be readable for people with not technological background knowledge? | Correctness | Question | From IT-Perspective: It would otherwise suggest incorrect scenario. From Non-Technical-Perspective: Too technical. --> Conflict: No optimal solution possible. | closed | - | - |
| F-018 | FeatureAnswerCourseQuestion | Is it correct to mention the expected answer before stating the question? | Logic | Question | Answer need to be determined so that behaviour is deterministic and can be tested. | open | - | Consideration to move the content within AND to a THEN statement |
| F-019 | StepsAnswerCourseQuestion - Given “I open a session …” | Why are states set as null instead of actually creating a session entity? | Code Logic | Question | Helper class was used, so that new objects do not need to be created every time. This way, only the relevant states need to be set. | closed | - | - |
| F-020 | StepsAnswerCourseQuestion | Unclear naming: What is string p0? | Clean Code | Defect | p0 is first passed parameter | closed | - | - |
| F-021 | StepsAnswerCourseQuestion and StepSupport| Should not several parts of the StepSupport be moved to a service? Are the tests not actually testing the StepSupport's logic instad of the application logic? | Logic | Question | Every team member implemented tests differently. Some preffered to use less abstraction. As a consequence, some methods in StepSupport contain a lot of logic. | open | medium | Consideration to outsource logic to different services. |
| F-022 | DES - High-Level Expectations| "AI-generated responses should be embedded in a controlled interaction flow, ensuring clarity and usability." - What is meant with "controlled interaction flow" in this context?| Clarity | Question | esbot.md is the same for all forked repositories. | closed | - | *[1] |
| F-023 | DES - Summary | Is the summary fitting for the project? I think it may be too focused on the context of the Software Testing lab.| Logic | Question | --> F-022 | closed | - | *[1] |
| F-024 | REQ - Non-Functional Requirements | "Response times of 2-5 seconds ..." -> Is this not a bit too ambitious? | Feasibility | Question | This is the timeframe in which the first reaction of the system is expected. It does not need to be a completed response from the LLM. | closed | - | - |

*[1]: Question for lecturer: Why are we supposed to check esbot.md as stated in docs/exercises/ex6_reviews.md lin 47 if it is the same for every group?

---

## 4. Data Summary (DS)

<!-- Key metrics for this review. Fill after preparation and/or after rework. -->

| Metric | Value | Notes |
|--------|-------|-------|
| Size of review object | 11 files (4 docs, 2 code files, 5 test files) <!-- e.g. pages, LOC, #requirements --> | <!-- --> |
| Preparation effort (hours, optional) | 2-4 | <!-- --> |
| Number of findings (initial) | 23 | <!-- --> |
| Number of findings after meeting | 24 | <!-- --> |
| Rework effort (hours, author) | unknown | <!-- --> |
| Re-inspection required? | no | <!-- --> |

---

## 5. Review Report (RR)

### 5.1 Summary

<!-- Short executive summary: object reviewed, outcome, overall quality impression. -->
The overall quality of the reviewd objects was good. Four mostly minor issues were found (see List of Findings, Status: open) that may be resolved at a later date, independently from out team. 

### 5.2 Review outcome

- **Object state after review:** accepted
- **Major risks or themes:** - F-021: Consideration to outsource logic of StepSupport to different services.

### 5.3 Decisions and follow-up

| Topic | Decision | Responsible | Due date |
|-------|----------|-------------|----------|
| F-008: Rename EndedAt to LastAccesedAt | Consideration to rename property | Team02 | - |
| F-009: AddMessages method in EntityMessages | Check if method is even necessary | Team02 | - |
| F-016: Rename retrieved (line 130, UnitTestsMessage) to retrievedSession | Consider to rename to retrievedSession. | Team02 | - |
| F-021: Outsource logic of StepSupport to different services. | Consideration to outsource logic of StepSupport to different services. | Team02 | - |

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