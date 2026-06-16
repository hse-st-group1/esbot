# Summary of Manual API Testing
## Were all status codes as expected? 
Yes, they behaved as expected based on their implementation. The only exception was the one for evaluating a quiz answer. If an evaluation was to be added for an answer that already had one, it threw a 500 Internal Server Error instead of a 400 Bad Request. This is based on the fact that no check was implemented to determine whether an evaluation already exists for the corresponding answer and is planned to be patched at a later date.

## Did the error messages provide useful feedback? 
Mostly yes, although it might be good to make the 400 Bad Request errors more specific.
Also, the 500 Internal Server Error could be handled differently (e.g., more specific errors).
Example: too-long messages that exceed the limit set in the database throw a 500 Internal Server Error instead of an error indicating that the message is too long. This can be mitigated with proper validation in the frontend.

## Did any request behave unexpectedly?
No, with the exception of the above mentioned issues.
