## Local Verification

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