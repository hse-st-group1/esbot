# UX Evaluation Plan and Acceptance Criteria

## 1. Evaluation scope

### 1.1 UX - Factors

- **Learnability**
    - Fast first prompt to the LLM.
- **Operability**
    - Refining, editing and controlling the chat messages.
    - Efficient Interaction (Response Time).
    - Handling of unclear input.
    - Get notified in Edge Cases.
- **Accessibility**
    - Clear and readable output.

### 1.2 User Journeys

- **First use of ESBot**
    - User visits the website.
    - User submits their first prompt.
    - User is able to read and understand the response.

- **Editing and Refining Chat Messages**
    - User submits a prompt.
    - User receives an answer from the LLM.
    - User submits additional prompts to refine the request to the desired output.
    - User is able to control the messages to their desired output.

- **Handling of Unclear Input**
    - User submits an unclear prompt.
    - User receives an answer from the LLM.
    - User is able to see that the request was unclear or ambiguous and needs to correct the prompt.
    - User submits a corrected prompt.
    - User is able to read and understand the response.

- **Readable Output**
    - User submits a prompt.
    - User receives the answer of the prompt.
    - User is able to read and understand the given answer.

- **Notice Edge Cases**
    - User submits a prompt.
    - User receives an answer from the LLM.
    - User is notified by the LLM that the required prompt is not in the scope of the course material.

## 2. Method set

The evaluation is split into two phases.
The first phase is an internal Cognitive walkthrough and heuristic evaluation.

- **Heuristic evaluation**
    - Visibility of System Status 
        - Output: thinking/evaluating.
    - Aesthetic and Minimalist Design
        - Only relevant content is displayed.
    - Consistency and Standards
        - Used symbols are within standards and used consistently.
    - User Control and Freedom
        - Users can easily start a new chat or abort a prompt to the LLM.
    - Error Prevention
        - LLM notifies users if the question is out of scope or ambiguous.

- **Conginitive Walkthrough**
    - Users should be able to open a saved session and/or submit a prompt.
    - Users are able to interpret and understand outputs.
    - Users know the next steps in their workflow.

In the second phase, a combination of think-aloud sessions and scenario-based tests is used. The participants will get learning tasks and verbalize their thoughts while using ESBot.

- **Scenario-based Tests**
    - "Explain heuristic evaluation"
    - "Generate Questions to Cognitive Walkthrough"
    - "Analyse Answers regarding questions"

- **Think-aloud Session**
    - Participants verbalize every step and every thought while interacting
    - User experience can be evaluated based on the observations
    - Identify misunderstandings and issues


## 3. Participants and Setup

- **Target group:**
    - Students with little or no experience of AI
    - Students with AI experience
    - 10 - 15 participants
- **Material:** 
    - Test Account for each participant for evaluation purposes
    - Example study material provided by the course
    - Tasks defined by Scenario-based Tests
    - LLM Logging of submitted requests
    - System Usability Scale survey
    - User Experience Questionaire sheet
- **Duration:** 
    - 45min for each participant

## 4. Metrics and acceptance criteria

### Metrics

| **Factors**    | **Metric** | **Acceptance**                |
|----------------|------------|-------------------------------|
|**Learnability**| Time to submitting first prompt | <2 minutes |
|**Usability**| SUS Score | >70pts. |
|**Experience**| UEQ Score | >4pts. in average|
|**Appearance**| Time to identify own messages and AI generated messages in the chat history | <1 minute |
|**Pragmatic Quality**| Measured usefulness of a generated responses | >70% |
|**Efficiency**| Time of receiving an AI generated response | <30 seconds |
|**Trust**| Time to identify an AI generated response | <1 minute of receiving an answer |
|**Credibility**| Similarity of answers of the same prompt | >70% similarity |

- System Usability Scale (SUS)
    - SUS Score has to be in the acceptable range. That means an average score of more than 70pts must be achieved.
    - If the score is between 50 and 70pts the questions and answers must be reviewed. The test system has to be adjusted.
    - If the score is below 50pts, the system requires a full review with major adjustments.

- User Experience Questionaire (UEQ)
    - The UEQ must be in an acceptable range. Every Score below 4 must be examined thoroughly and the system evaluated for the measured metric.
    - The scores build an average value, which counts as the baseline for the score measurement.

## 5. Findings template

- **ID+Title:**
- **Journey/Description:**
- **Issue/Cause:**
- **Severity:**
- **Impact:**
- **Evidence:**
- **ISO 25010:**
- **Recommendation:**


## 6. Quality gate proposal

- **Learnability:** First time user can't submit a prompt <5 minutes. 
- **Usability:** SUS Score <70%.
- **Experience:** UEQ Score <4pts. average.
- **Appearance:** Messages are not distiguishable from eachother.
- **Pragmatic Quality:** Generated responses don't meet usefulness criteria <50%.
- **Efficiency:** Time of receiving a response exceeds the defined threshold.
- **Trust:** AI generated responses are not recognizable as AI generated.
- **Consistency:** Similarity of answers are <50%.
- **UX Criteria which can't be measured must be reviewed and redone before release.**


