## TC-UI-01: Session-Create Session without User

19.06.2026 Muenzer
Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application in Firefox
2. Click "new Session" without being logged in


Expected Result:
System should prevent session creation and display an error message indicating that the user is not logged in.

Actual Result:
Error message is displayed: "You are not logged in"

Status:
PASS

## TC-UI-02: Session-Create new Session

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application in Firefox
2. click on "new Session"

Expected Result:
a new chat session is created and appears in the sidebar

Actual Result:
a new chat session is created and appears in the sidebar

Status:
PASS

## TC-UI-03: Session-Get old sessions

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application in Firefox


Expected Result:
Previously created chat sessions are displayed in the sidebar.

Actual Result:
Old sessions appear in the sidebar

Status:
PASS

## TC-UI-04: Message- Send long Message(1000+Symbols)

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application
2. Enter very long message
3. Click send

Expected Result:
Message is processed and response appears in chat or System should validate input length and reject messages exceeding allowed limit

Actual Result:
No response in UI, Network request returns HTTP 500 Internal Server Error, no Error shown to User

Status:
FAIL

## TC-UI-05: Message-Special Characters Input

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application
2. Enter message: !@#$%^&*() 😄 {}
3. Click send

Expected Result:
Message is correctly displayed and processed

Actual Result:
Message is sent and displayed correctly

Status:
PASS

## TC-UI-06: Message-Multiple Messages Sequentially

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application
2. Send message "Hallo"
3. Send message "Rabbit?"
4. Send message "Test"
5. Fast Spam "1", "1", "1", "1"
 
Expected Result:
All messages appear in correct order with responses

Actual Result:
Messages are displayed correctly

Status:
PASS

## TC-UI-07: Message-Send Message

19.06.2026 Muenzer

Environment:
- OS: Linux Mint 22.3
- Browser: Firefox
- LLM mock: Mock AI Service (static response)

Steps:
1. Open application
2. Send message "Rabbit?"

Expected Result:
The message is displayed in the chat and a response is returned from the backend.

Actual Result:
The message is displayed in the chat and a response is returned correctly.

Status:
PASS

## Manual-Testing Reflection
Manual testing was intuitive for the implemented user interface. On the other hand it also took a long time to setup because user data had to be changed in the database (hard coded testuser) and made some tests tedious. In TC-UI-04 no result was send back with no  UI notification and HTTP status code 500. The reason behind that are database restrictions implemented in the backend. In our UI and in the backend no input-validation is done, for that reason this manual test was unsuccessful. In comparison manual tests take way longer than automated tests and always require user input. Going forward it is recommended to use automation wherever possible.