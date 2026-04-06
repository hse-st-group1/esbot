# UX Evaluation Plan and Acceptance Criteria

## 1. Evaluation scope

### 1.1 UX - Factors

- **Learnability**
    - Fast first prompt to the LLM.
- **Operability**
    - Refining, editing and controlling the chat messages.
    - Usage of Copy and Paste for convinience.
    - Notice Edge Cases
- **User Interface Aestetics**
    - Clear and readable output.

### 1.2 User Journeys

- **First use of ESBot**
    - User visits the website.
    - User submits their first prompt intuitively.
    - User is able to read and understand the response.

- **Editing and Refining Chat Messages**
    - User submits a prompt.
    - User tries to refine their desired output and control the chat messages.
    - User is able to control the messages to their desired output.

- **Copy and Paste for Chat Messages**
    - User submits a prompt.
    - User copies the answer and reuse it as part of a new question.
    - User is able to see that the copied part is used for the generation of the new answer.

- **Readable Output**
    - User submits a prompt.
    - User receives the answer of the prompt.
    - User is able to read and understand the given answer.

- **Notice Edge Cases**
    - User submits a prompt.
    - User receives the answer of the prompt.
    - User is able to detect that the required prompt is out of scope.

## 2. Method set

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

- **Scenario-based Tests**
    - "Explain heuristic evaluation"
    - "Generate Questions to Cognitive Walkthrough"
    - "Analyse Answers regarding questions"

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
|**Learnability**| Time to submitting first prompt | <5 minutes |
|**Usability**| SUS Score | >70pts. |
|**Experience**| UEQ Score | >4pts. in average|
|**User Interface Aestetics**| Time to identify own messages and AI generated messages in the chat history | <1 minute|
|**Efficiency**| Measured usefulness of a generated responses | >70%|
|**Constitution**| Time to identify a AI generated response | <2 minute of receiving an answer |
|**Consistency**| Similarity of answers of the same prompt | >70% similarity |

- System Usability Scale (SUS)
    - SUS Score has to be in the acceptable range. That means an average score of atleast 72pts must be achieved.
    - If the score is between 50 and 70pts the questions and answers must be reviewed. The test system has to be adjusted.
    - If the score is below 50pts, the system requires a full review with major adjustments.

- User Experience Questionaire (UEQ)
    - The UEQ must be in an acceptable range. Every Score below 3 must be examined thoroughly and the system evaluated according to this metric.
    - The scores should build an average value, which counts as the baseline for the score measurement.

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

- **Learnability:** First time user cant submit a prompt <5 minutes.
- **Usability:** SUS Score <60%.
- **Experience:** UEQ Score <3pts. average.
- **User Interface Aestetics:** Messages are not distiguishable from eachother.
- **Efficiency:** Generated responses dont meet usefulness criteria <50%.
- **Constitution:** AI generated responses are not recognizable as AI generated.
- **Consistency:** Similarity of answers are <50%.
- **UX Criteria which cant be measured must be reviewed and redone before release.**


