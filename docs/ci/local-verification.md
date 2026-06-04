## Local Verification

### Run the Test Suite locally

```bash
mvn surefire:test -Dtest=* && mvn verify -P pmd-linter,dependency-check
```

### Static Analysis with PMD-linter

```bash
mvn verify -P pmd-linter
```
or
```bash
mvn clean verify -P pmd-linter
```

To view pmd report:
```bash
xdg-open target/site/pmd.html
```

### Static Analysis with Dependency Check
```bash
mvn verify -P dependency-check
```
or
```bash
mvn clean verify -P dependency-check
```

### Git Hook Setup

- Copy the Pre-Commit Hook into .git/hooks

```bash
cp ~/software_testing/utils/pre-commit ~/software_testing/.git/hooks/precommit
```

- After copying the pre-commit hook into the folder Unit Tests and Linting will be executed with every commit