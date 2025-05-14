# Java Playwright Test Automation Framework

This is a UI Test Automation Framework built using **Java**, **Playwright**, **TestNG**, and **ExtentReports**. It is designed to be modular, maintainable, and scalable for web application testing.

---

## 🛠 Tech Stack

- **Java**
- **Playwright for Java**
- **TestNG** - Test framework
- **ExtentReports** - Test report generation
- **Maven** - Build and dependency management

---

## 📁 Project Structure
```
├── .github/workflows # GitHub Actions for CI/CD
│ ├── main.yml
│ └── playwright.yml
├── src
│ └── main
│ └── java
│ ├── factory # Factory classes
│ │ ├── PlaywrightFactory.java
│ │ └── SessionManagement.java
│ ├── pages # Page Object Model
│ │ ├── HomePage.java
│ │ ├── LoginPage.java
│ │ └── ROListPage.java
│ └── test
│ ├── baseTest # Base setup/teardown
│ │ └── BaseTest.java
│ ├── tests # Test cases
│ │ ├── HomePageTest.java
│ │ └── ROListPageTest.java
│ └── testutils # Utilities
│ ├── AdditionalDescriptions.java
│ ├── JiraTestCaseUtils.java
│ ├── Listeners.java
│ ├── MergeReport.java
│ ├── MyScreenRecorder.java
│ └── TestUtils.java
├── resources/config
│ └── config.properties # Environment config
├── testng.xml # TestNG suite file
├── pom.xml # Maven dependencies
└── .gitignore
```
---

---

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Node.js (for Playwright)
- Git

### Install Dependencies

```bash
mvn clean install
```

---

### 🚀 Running Tests

## ✅ Option 1: Via TestNG Suite

Run the `regression.xml` suite file from your IDE or use:

```bash
mvn test -DsuiteXmlFile=test_suites/regression.xml
```

## ✅ Option 2: Single Test Class

```bash
mvn -Dtest=LoginPageTest test
```
---
### Reports
ExtentReports will be generated under the Reports directory.

Supports screenshots, logs, and video recording.

---
### Features
Page Object Model for better code organization.

GitHub Actions CI pipeline.

Utilities for JIRA integration and reporting.

Configurable via config.properties.
---
### CI/CD
Tests are automatically triggered via GitHub Actions (.github/workflows/main.yml).

---
