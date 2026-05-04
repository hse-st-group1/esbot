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

#### Evaluation: 
TO DO

### 2. OWASP
(Dependency Checker)

#### Justification: 
To find Package Vulnerabilities → Improve security

#### Evaluation: 
TO DO

### Execution of both tools
Both are invoked during build: 
```mvn clean install```

To view pmd report:
```xdg-open target/site/pmd.html```

Sources (PMD):
- https://docs.pmd-code.org/latest/pmd_userdocs_making_rulesets.html
- https://docs.pmd-code.org/latest/pmd_userdocs_tools_maven.html


