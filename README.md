# Failsafe Helper Library

Failsafe is a lightweight, zero-dependency library for handling failures in Java 8+. It works by intercepting failures and
performing recovery logic via configurable strategies.

This library, built as a wrapper around the Failsafe library, aims to streamline the process of handling failures and
offering a more user-friendly interface for developers to handle failures in Java.

## Installation

To use this Failsafe Utility library in your project, follow these steps:

1. Add following repository in your `settings.xml` file

```xml

<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/rohit-walia/failsafe-helper</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

2. Add the Maven dependency to your pom.xml file. Make sure to use the latest version available:

```xml

<dependency>
    <groupId>org.failsafe</groupId>
    <artifactId>failsafe-helper</artifactId>
    <version>${failsafe-helper.version}</version>
</dependency>
```

3. Run mvn install

## Usage Examples

#### Handling Failures with Retries

Below examples demonstrate how you can use this library for retrying logic on failure. This is especially useful in automated
testing projects where you want to improve the reliability of your tests by wrapping some test steps in a retry block.
Common use cases: login, search and find operations, etc.

See the [RetryAgain](core/src/main/java/org/failsafe/failsafe/retry/RetryAgain.java)
and [RetryUntil](core/src/main/java/org/failsafe/failsafe/retry/RetryUntil.java) for more available functions.

```Java
void retryOnFailureExamples() {
  RetryAgain.once(() -> {
    // code here. If it fails, it will retry once before error out
  });

  RetryAgain.onceWithDelay(() -> {
    // code here. If it fails, it will retry once, with a 3 second delay, before error out
  }, 3);

  RetryUntil.attemptLimit(() -> {
    // code here. If it fails, it will retry twice before error out
  }, 2);

  RetryUntil.attemptLimitWithDelay(() -> {
    // code here. If it fails, it will retry twice, with a 3 second delay, before error out
  }, 2, 3);
}
```

#### Handling Failures with Fallback

Below examples demonstrate how you can use this library for fallback logic on failure. This is especially useful in cases
where you want to fallback to secondary logic if the primary logic fails.

See the [RetryAgain](core/src/main/java/org/failsafe/failsafe/fallback/FallbackTo.java) for more available functions.

```Java
void fallbackOnFailureExamples() {
  FallbackTo.logger(() -> {
    // primary code here.
  }, "This message will be logged if the primary code throws an exception or error.");
}
```

# Tools, libraries, and technologies

### JUnit5

This project uses JUnit5 for testing. See [here](https://junit.org/junit5/docs/current/user-guide/) for more information.

### Lombok

This project uses lombok to decrease boilerplate code. If you are using Intellij please install the Lombok Plugin. If
you are using Eclipse STS follow the instructions [here](https://projectlombok.org/setup/eclipse).
If you are using another IDE you can see if it is supported on the Lombok website [here](https://projectlombok.org).

### Failsafe

Failsafe is the failure handling and retry library used in this project. See [here](https://failsafe.dev/)
for more information.

### Code Quality

As part of the build, there are several code quality checks running against the code base. All code quality files can be
found in the root of the project under the [codequality](.codequality) directory.

#### CheckStyle

The project runs checkstyle plugin to validate java code formatting and enforce best coding standards.

#### PMD

The project runs PMD code analysis to find common programming flaws like unused variables, empty catch blocks, unnecessary
object creation, etc...
