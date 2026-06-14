# API Endpoints

## Automatic Endpoint Documentation 
The API Endpoints are automatically documented using OpenAPI.
1. Start up Docker with ```docker compose up```
2. Start Backend with from the application/backend folder with ```mvn spring-boot:run```
3. The Documentation can then be viewed here: 
    - [SwaggerUI](http://localhost:8080/swagger-ui/index.html )
    - [API Docs](http://localhost:8080/v3/api-docs )

---
## Manual Endpoint Documentation
**Base URL:** localhost:8080

---
### chat-service-controller

---
#### GET /sessions
**Parameters:** -

**Request Body:** UUID userId

**Responses:** 200 OK

**Errors:** 
- 400 – Bad Request - if sessionId is not a UUID
- 404 – Not Found - if no session with passed sessionId exists

**Returns:** List of sessionIds

**Function:** Get the sessionIds of all Sessions belonging to a user as a list.

---
#### POST /sessions
**Parameters:** -

**Request Body:** UUID userId

**Response:** 201 OK

**Errors:**
- 400 - Bad request - if userId is not a UUID
- 404 - Not Found - if no user with passed userId exists

**Returns:** UUID sessionId

**Function:** Create a Session for a user and returns the sessionId of the created Session.

---
#### POST /sessions/{sessionId}/quiz
**Parameters:** UUID sessionID

**Request Body:** QuizRequestDTO

    Example:  
    {
        "quizRequestContent": "string",
        "count": 0,
        "difficulty": "EASY",
    } 

**Response:** 201 OK

**Errors:**
- 400 - Bad Request - if passed values are in wrong format or outside of boundary values (e.g. difficulty is not a value of enum)
- 400 - Bad Request - if sessionID is not a UUID
- 404 - NotFound - if session with passed sessionID does not exist

**Returns:** QuizRequestDTO
    
    Example:  
    {
        "quizRequestContent": "string",
        "count": 0,
        "difficulty": "EASY",
        "quizItemDTOs": [
            {
                "quizItemID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "quizRequestID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "question": "string",
                "quizAnswers": [
                    {
                        "quizAnswerID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        "answer": "string",
                        "timeStamp": "2026-06-13T23:02:26.104Z"
                    }
                ],
                "quizEvaluations": [
                    {
                        "evaluationID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        "quizAnswerID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        "evaluation": "string"
                    }
                ]
            }
        ]
    } 

**Function:** Create a quiz within a session for a user and returns the created QuizResquest as QuizRequestDTO.

---
#### POST /sessions/{sessionId}/quiz/{quizItemId}/answer
**Parameters:** UUID sessionId, UUID quizItemId

**Request Body:** Answer as string

**Response:** 201 OK

**Errors:**
- 400 - Bad Request - if sessionId or quizItemId are not UUIDs
- 404 - Not Found - if no session with passed sessionId or no quizItem with passed quizItemId exists
- 500 - Internal Server Error - if an Evaluation already exists for the quizItemId

**Returns:** Evaluation of answer as string

**Function:** User can submit an answer for a question of a QuizItem and receive an evaluation of their answer.

---
#### GET /sessions/{sessionId}/messages
**Parameters:** UUID sessionId

**Request Body:** UUID userId

**Response:** 200 OK

**Errors:**
- 400 – Bad Request - if the userID passed in the request Body or sessionID  in the path is not a UUID
- 403 – Forbidden - if session does not match to passed userId
- 404 – Not Found - if no session with passed sessionId exists

**Returns:** List of MessageDTOs

    Example: 
    [
        {
            "messageID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "messageContent": "string",
            "timestamp": "2026-06-13T23:08:45.403Z",
            "sender": true,
            "messageType": "string"
        }
        {
            "messageID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "messageContent": "string",
            "timestamp": "2026-06-13T23:08:45.403Z",
            "sender": true,
            "messageType": "string"
        }
    ]

**Function:** Get all Messages from a Session based on sessionId.

---
#### POST /sessions/{sessionId}/messages
**Parameters:** UUID sessionId

**Request Body:** Message from user as string

**Response:** 201 OK

**Errors:**
- 400 - bad Request - if sessionID is not a UUID
- 404 - Not Found - if no session with passed sessionID exists

**Returns:** response of LLM as MessageDTO

    Example:
    {
        "messageID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "messageContent": "string",
        "timestamp": "2026-06-13T23:12:13.373Z",
        "sender": true,
        "messageType": "string"
    }

**Function:** user sends a message and receives a response.

---
#### GET /sessions/{sessionId}
**Parameters:** UUID sessionId

**Request Body:** UUID userId

**Response:** 200 OK

**Errors:**
- 400 – Bad Request - if the userID passed in the request Body or sessionID  in the path is not a UUID
- 403 – Forbidden - if session does not match to passed userId
- 404 – Not Found - if no session with passed sessionId exists

**Returns:** SessionMetatdataDTO

    Example:
    {
        "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "startedAt": "2026-06-13T23:28:07.608Z",
        "lastAccessed": "2026-06-13T23:28:07.608Z"
    }

**Function:** Get the metadata of a session.

---
#### DELETE /sessions/{sessionId}
**Parameters:** UUID sessionId

**Request Body:** UUID userId

**Response:** 200 OK

**Errors:**
- 400 – Bad Request - if the userID passed in the request Body or sessionID  in the path is not a UUID
- 403 – Forbidden - if session does not match to passed userId
- 404 – Not Found - if no session with passed sessionId exists

**Returns:** Confirmation that session was deleted as string.

**Function:** Delete a Session based on sessionId.

---
#### GET /sessions/{sessionId}/complete
**Parameters:** UUID sessionId

**Request Body:** UUID userId

**Response:** 200 OK

**Errors:**
- 400 – Bad Request - if the userID passed in the request Body or sessionID  in the path is not a UUID
- 403 – Forbidden - if session does not match to passed userId
- 404 – Not Found - if no session with passed sessionId exists

**Returns:** SessionDTO

    Example:
    {
        "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "startedAt": "2026-06-13T23:30:46.619Z",
        "lastAccessed": "2026-06-13T23:30:46.619Z",
        "messages": [
            {
                "messageID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "sessionID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "messageContent": "string",
                "timestamp": "2026-06-13T23:30:46.619Z",
                "sender": true,
                "messageType": "string"
            }
        ],
        "quizRequests": [
            {
                "quizRequestContent": "string",
                "count": 0,
                "difficulty": "EASY",
                "quizItemDTOs": [
                    {
                        "quizItemID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        "quizRequestID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                        "question": "string",
                        "quizAnswers": [
                            {
                                "quizAnswerID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                "answer": "string",
                                "timeStamp": "2026-06-13T23:30:46.619Z"
                            }
                        ],
                        "quizEvaluations": [
                            {
                                "evaluationID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                "quizAnswerID": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                "evaluation": "string"
                            }
                        ]
                    }
                ]
            }
        ]
    }

**Function:** Retrieves all information associated with a Session (including all Messages and QuizRequests)

---
### es-bot-health-controller

---
#### GET /health
**Parameters:** -

**Request Body:** -

**Response:** 200 OK

**Returns:** Message that backend is running and reachable as string

**Function:** To check wheter the backend is running.

---
### test-controller

---
####  GET /
**Parameters:** -

**Request Body:** -

**Response:** -

**Returns:** "Hello World"

**Function:** Basic Test Controller

---
### Schemas

---
#### SessionDTO Schema:  
<pre>
SessionDTO object:  
    sessionID UUID
    userId UUID
    startedAt stringdate-time
    lastAccessed stringdate-time
    messages array<MessageDTO>
        Message DTO object
            messageID UUID
            sessionID UUID
            messageContent string
            timestamp stringdate-time
            sender boolean
            messageType string
    quizRequests array<QuizRequestDTO>
        QuizRequestDTO object
            quizRequestContent string
            count integerint32
            difficulty string:
                Enum:
                    #0"EASY"
                    #1"MEDIUM"
                    #2"HARD"
            QuizItemDTOs array<QuizItemDTO>
                QuizItemDTO object
                    quizItemID UUID
                    quizRequestID UUID
                    question string
                    quizAnswers array<QuizAnswerDTO>
                        QuizAnswerDTO object
                            quizAnswerID UUID
                            answer string
                            timeStamp stringdate-time
                    quizEvaluations array<QuizEvaluationDTO>
                        QuizEvaluationDTO object
                            evaluationID UUID
                            quizAnswerID UUID
                            evaluation string
</pre>

#### SessionMetadataDTO Schema:  
<pre>
SessionMetadataDTO object:  
    sessionID UUID
    userId UUID
    startedAt stringdate-time
    lastAccessed stringdate-time
</pre>

#### MessageDTO Schema:  
<pre>
Message DTO object:  
    messageID UUID
    sessionID UUID
    messageContent string
    timestamp stringdate-time
    sender boolean
    messageType string
</pre>

#### QuizRequestDTO Schema:  
<pre>
QuizRequestDTO object:  
    quizRequestContent string
    count integerint32
    difficulty string:
        Enum:
            #0"EASY"
            #1"MEDIUM"
            #2"HARD"
    QuizItemDTOs array<QuizItemDTO>
        QuizItemDTO object
            quizItemID UUID
            quizRequestID UUID
            question string
            quizAnswers array<QuizAnswerDTO>
                QuizAnswerDTO object
                    quizAnswerID UUID
                    answer string
                    timeStamp stringdate-time
            quizEvaluations array<QuizEvaluationDTO>
                QuizEvaluationDTO object
                    evaluationID UUID
                    quizAnswerID UUID
                    evaluation string
</pre>

#### QuizItemDTO Schema:  
<pre>
QuizItemDTO object:  
    quizItemID UUID
    quizRequestID UUID
    question string
    quizAnswers array<QuizAnswerDTO>
        QuizAnswerDTO object
            quizAnswerID UUID
            answer string
            timeStamp stringdate-time
    quizEvaluations array<QuizEvaluationDTO>
        QuizEvaluationDTO object
            evaluationID UUID
            quizAnswerID UUID
            evaluation string
</pre>

#### QuizAnswerDTO Schema:  
<pre>
QuizAnswerDTO object:  
    quizAnswerID UUID
    answer string
    timeStamp stringdate-time
</pre>

#### QuizEvaluationDZO Schema:  
<pre>
QuizEvaluationDTO object:  
    evaluationID UUID
    quizAnswerID UUID
    evaluation string
</pre>