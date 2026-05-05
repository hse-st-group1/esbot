
# Static Code Analysis

## Tools
### 1. PMD
(Linter, Complexity, Dead Code)

#### Used ruleset: 
- Best Practices: enforce generally accepted best practices
- Code Style: enforce a specific coding style
- Design: help discover design issues
- Error Prone: detect constructs that are either broken, extremely confusing or prone to runtime errors
- Performance: flag suboptimal code

#### Unused rulesets:
- Documentation
- Multithreading
- Security

→ No multithreading or security related code was implemented, therefore we choose to leave these categories out. Documentation too was left out, given that it has no impact on the code itself. 

#### Justification: 
Bug Prevention & Clean Code → Improve maintainability and prevent errors

#### Findings:
- PMD Failure: hse_st_group1.esbot.AIServiceUnavailableException:3 Rule:MissingSerialVersionUID Priority:3 Classes implementing Serializable should set a serialVersionUID.
- PMD Failure: hse_st_group1.esbot.AIServiceUnavailableException:4 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'errorMessage' is not assigned and could be declared final.

- PMD Failure: hse_st_group1.esbot.EsbotApplication:7 Rule:UseUtilityClass Priority:3 All methods are static. Consider adding a private no-args constructor to prevent instantiation..
- PMD Failure: hse_st_group1.esbot.EsbotApplication:9 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'args' is not assigned and could be declared final.

- PMD Failure: hse_st_group1.esbot.controller.TestController:7 Rule:AtLeastOneConstructor Priority:3 Each class should declare at least one constructor.
- PMD Failure: hse_st_group1.esbot.controller.TestController:7 Rule:TestClassWithoutTestCases Priority:3 The class 'TestController' might be a test class, but it contains no test cases..

- PMD Failure: hse_st_group1.esbot.model.Message:43 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.

- PMD Failure: hse_st_group1.esbot.model.QuizAnswer:38 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.

- PMD Failure: hse_st_group1.esbot.model.QuizRequest:44 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizRequestContent.

- PMD Failure: hse_st_group1.esbot.model.Session:43 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.
- PMD Failure: hse_st_group1.esbot.model.Session:47 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.
- PMD Failure: hse_st_group1.esbot.model.Session:63 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.

- PMD Failure: hse_st_group1.esbot.model.User:27 Rule:ShortClassName Priority:4 Avoid short class names like User.

- PMD Failure: hse_st_group1.esbot.services.MessageService:18 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'messageRepository' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.MessageService:18 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'aiService' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.MessageService:24 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'message' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.MessageService:27 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'timestamp' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.MessageService:27 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.
- PMD Failure: hse_st_group1.esbot.services.MessageService:27 Rule:ReplaceJavaUtilDate Priority:3 Usage of java.util.Date should be replaced with classes from java.time.
- PMD Failure: hse_st_group1.esbot.services.MessageService:29 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'response' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.MessageService:36 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'aiResponse' could be declared final.

- PMD Failure: hse_st_group1.esbot.services.QuizEvaluationService:18 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizAnswerRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizEvaluationService:20 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizEvaluationRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizEvaluationService:23 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'answer' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizEvaluationService:24 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'evaluation' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizEvaluationService:25 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'quizEvaluation' could be declared final.

- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:18 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizRequestRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:19 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizItemRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:22 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizRequestRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:22 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'quizRequestRepository' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:22 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'aiService' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:22 Rule:LongVariable Priority:3 Avoid excessively long variable names like quizItemRepository.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:22 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'quizItemRepository' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:29 Rule:MethodArgumentCouldBeFinal Priority:3 Parameter 'quizRequest' is not assigned and could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:31 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'content' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:35 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'questions' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:45 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'items' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:47 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'question' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:48 Rule:LocalVariableCouldBeFinal Priority:3 Local variable 'item' could be declared final.
- PMD Failure: hse_st_group1.esbot.services.QuizRequestService:48 Rule:AvoidInstantiatingObjectsInLoops Priority:3 Avoid instantiating new objects inside loops.

#### Evaluation: 
The PMD code analyzer found flaws in our application. Some rules were deemed unnessesary because it contradicts with the readability of the code. False positives will be addressed using a custom ruleset.
- Rule:LongVariable
    - Rule should be ignored because it contradicts with code readability.
- Rule:ShortClassName
    - Rule should be ignored because it contradicts with code readability.
- Rule:ReplaceJavaUtilDate
    - Java util date should be replaced by java.time because java.date is mutable, not threadsafe and is error-prone.
- Rule:LocalVariableCouldBeFinal
    - Some variables used in our services could be declared as final. Depending on further development this has to be assessed and implemented or ignored.
- Rule:MethodArgumentCouldBeFinal
    - Some methods used in our services and in the EsbotAplication file could be declared as final. Depending on further development this has to be assessed and implemented or ignored.
- Rule:AtLeastOneConstructor and Rule:TestClassWithoutTestCases
    - For our smoke test the testcontroller was implemented without any further use. This file will be ignored in the configuration of PMD, because a constructor and testcases are not necessary for our smoke test.
- Rule:MissingSerialVersionUID
    - Our AIServiceException extends a RuntimeException and is missing a SerialVersionUID which needs to be addressed.
- Rule:UseUtilityClass
    - EsbotApplication is springboots entry point and can be ignored safely suppressed because it doesn't make to sense to implement constructors here. The file is in the custom ruleset ignored but should feature a @SupressWaring Bean in the application file in the future.
- Rule:AvoidInstantiatingObjectsInLoops
    - The QuizRequestService is using a mock for the aiservice which is not implemented yet. A Loop is used to save all quiz items out of a list. This issue needs addressing in further development.

### 2. OWASP
(Dependency Checker)

#### Justification: 
To find Package Vulnerabilities → Improve security

#### Findings:
- log4j-api-2.25.3.jar (pkg:maven/org.apache.logging.log4j/log4j-api@2.25.3, cpe:2.3:a:apache:log4j:2.25.3:*:*:*:*:*:*:*): CVE-2026-34478(6.9), CVE-2026-34480(6.9), CVE-2026-34481(6.3)
- postgresql-42.7.10.jar (pkg:maven/org.postgresql/postgresql@42.7.10, cpe:2.3:a:postgresql:postgresql_jdbc_driver:42.7.10:*:*:*:*:*:*:*): CVE-2026-42198(7.5)
- spring-boot-data-commons-4.0.5.jar (pkg:maven/org.springframework.boot/spring-boot-data-commons@4.0.5, cpe:2.3:a:pivotal_software:spring_data_commons:4.0.5:*:*:*:*:*:*:*, cpe:2.3:a:vmware:spring_boot:4.0.5:*:*:*:*:*:*:*): CVE-2026-40972(7.5), CVE-2026-40975(7.5), CVE-2026-40976(9.1), CVE-2026-40973(7.0)
- spring-boot-sql-4.0.5.jar (pkg:maven/org.springframework.boot/spring-boot-sql@4.0.5, cpe:2.3:a:vmware:spring_boot:4.0.5:*:*:*:*:*:*:*, cpe:2.3:a:www-sql_project:www-sql:4.0.5:*:*:*:*:*:*:*): CVE-2026-40972(7.5), CVE-2026-40975(7.5), CVE-2026-40976(9.1), CVE-2026-40973(7.0)
- spring-boot-web-server-4.0.5.jar (pkg:maven/org.springframework.boot/spring-boot-web-server@4.0.5, cpe:2.3:a:vmware:spring_boot:4.0.5:*:*:*:*:*:*:*): CVE-2026-40972(7.5), CVE-2026-40975(7.5), CVE-2026-40976(9.1), CVE-2026-40973(7.0)
- tomcat-embed-core-11.0.20.jar (pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@11.0.20, cpe:2.3:a:apache:tomcat:11.0.20:*:*:*:*:*:*:*, cpe:2.3:a:apache_tomcat:apache_tomcat:11.0.20:*:*:*:*:*:*:*): CVE-2026-34486(7.5), CVE-2026-34487(7.5), CVE-2026-34483(7.5)

#### Evaluation: 
Using the OWASP Dependency Check Plugin for Maven some critical (CVSS > 7) vulnerabilities were discovered. The discovered vulnerabilites were log4j-api-2.25.3.jar, postgresql-42.7.10.jar, spring-boot-data-commons-4.0.5.jar, spring-boot-web-server-4.0.5.jar and tomcat-embed-core-11.0.20.jar. These vulnerabilities should be mitigated by updating the dependencies to the newest version.
- A update of the springframework from 4.0.5 to 4.0.6 mitigated the following vulnerabilities:
    - log4j-api-2.25.3.jar
    - spring-boot-data-commons-4.0.5.jar
    - spring-boot-web-server-4.0.5.jar
    - tomcat-embed-core-11.0.20.jar
- A update of postgresql from 42.7.10 to 42.7.11 mitigated the following vulnerability:
    - postgresql-42.7.10.jar
In conclusion all dependencies should be kept up to date to mitigate risks and potential vulnerable applications.

### Execution of both tools
Both are invoked during build: 
```mvn clean install```

To view pmd report:
```xdg-open target/site/pmd.html```

Sources (PMD):
- https://docs.pmd-code.org/latest/pmd_userdocs_making_rulesets.html
- https://docs.pmd-code.org/latest/pmd_userdocs_tools_maven.html
