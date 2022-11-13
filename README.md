# Fortify interview demo - Alza shop frontend E2E Automated Tests

This project holds two basic tests suites for web-browser based frontend automated testing of
the [Alza](https://www.alza.cz/EN/) product

* Smoke tests
* Integration tests

Purpose of each of the above-listed test categories as well as general rules for their design and implementation
category are described on a [dedicated WIKI section](TODO: add link here)

## Content

* [Local environment setup](#local-environment-setup)
  * [Java](#java)
  * [Gradle](#gradle)
  * [Set up IDE](#set-up-ide)
    * [Plugins](#plugins)
    * [Plugin installation](#plugin-installation)
* [Set up of test environment](#set-up-of-test-environment)
* [Environment / System properties](#environment--system-properties)
    * [List of important properties](#list-of-important-properties)
* [Run the tests](#run-the-tests)
    * [example](#example)
* [Test reports](#test-reports)
    * [GitHub](#github)
    * [Local](#local)

## Local environment setup

### Java
Java is a main development language used in Test automation projects.
Install Open JDK 11.0.2 from [https://jdk.java.net/archive/](https://jdk.java.net/archive/) 
Direct download link:
- [windows](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip)
- [linux](https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz)
- [mac](https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_osx-x64_bin.tar.gz)

***following lines are for windows based OS***
- Extract zip to C:\Program Files\Java
- set environment path
  - in Windows search for `edit the system environment variables`
  - In `System properties` dialog open tab `Advanced` and click on `Environment Variables` button 
  - In panel `User variables` for *< your account >*
    - find or create `JAVA_HOME` with value `C:\Program Files\Java\jdk-11.0.2`, where `jdk-11.0.2` is folder with java jdk.
    - update `Path` variable. Add this value:` %JAVA_HOME%\bin`
- check the default version
  - open terminal (search for `cmd`)
  - type command `java -version`
  - the response should begin with `openjdk version "11.0.2"`

### Gradle
Gradle wrapper is delivered with this project.
To make local build functional place [`init.gradle`](.github/workflows/init.gradle) file to home directory.
For windows based system it is: `C:/Users/<your account>/.gradle/`

In case something is not functional, install gradle to your system.
You can follow [installation guide](https://gradle.org/install/)

### Set up IDE

Recommended IDE for automated tests is **Intellij IDEA Community Edition** (it's free). You can download it [here](#https://www.jetbrains.com/idea/download)

#### plugins
In order to write tests easier, it is recommended to install following plugins:
- Lombok
- Gherkin
- Cucumber for Java

#### Plugin installation
- press `CTRL+SHIFT+A` and type `plugins` then select `Plugins` form the list
- click on `Marketplace` and type the plugin’s name
- then click on install next to it

once you install all plugins, restart the IDE

# Set up of test environment
no special settings is required.

# Environment / System properties
## List of important properties
no special settings is required.

# Run the tests
To run tests you need to set up environment properties. For details please consult section [List of important properties](#list-of-important-properties) 


For test execution are defined dedicated gradle tasks

```
./gradlew -PactiveEnvironment=<environment> -PcucumberTags=<cucumber_tags> <test_runner_task>
```

* `environment`
    - can be one of these values `default`, `dev`, `at`, `prod` see [serenity.conf](src/test/resources/serenity.conf) for all available environments
    - if `-PactiveEnvironment` omitted, then default environment is `default` with external links to services
* `cucumber_tags`
    - defines the execution scope when it should be different from preset settings in the runner (`test_runner_task`)
    - e.g. `(@smoke or @ui) and (not @slow)`
* `test_runner_task`
    - defines the set of tests to run. Available values:
        - `integrationTest` - runs all tests (in feature files annotated as `@Integration`)
        - `smokeTest` - runs a limited amount of tests (in feature files annotated as `@Smoke`). Should be used to check
          critical functionality, but not to run whole test suit.
        - `debugTest` - used for development of tests (in feature files annotated as `@debug`)

### Examples

run **integration** test for **at** environment

```
./gradlew -PactiveEnvironment=at integrationTest
```

run **integration** test with defaults (environment `default` = `at` with external links)

```
./gradlew integrationTest
```

run **integration** test with default environment and scope of tests is limited to `@TestSuite:account`

```
./gradlew.bat integrationTest -PcucumberTags='@TestSuite:account'
```

or the same as above via environment variable (env var is set by PowerShell (PS), use the set method for your Operating
system)

```
$env:CUCUMBER_FILTER_TAGS="@TestSuite:account"
./gradlew.bat integrationTest
```

***Important note:*** Normally has precedence `-PcucumberTags`, but this behaviour is changed in build.gradle so env var
has precedence.

# Test reports

### GitHub

once github action finish the run, the report is generated and attached as **Artifact** to the job. Navigate
to `Actions` then from table *All workflows* choose a job you are interested in (click on title of the job). Details
about job are displayed. Scroll down to `Artifacts` and download a file ***test-results***. Unpack the archive and
search for ***index.html***

### Local

once the test finish the run, start an `aggregate` gradle task

```
./gradlew aggregate
```

it will generate a [Serenity](https://serenity-bdd.github.io/theserenitybook/latest/index.html) report which is located
at folder [target/site/serenity](./target/site/serenity) where you need open an [index.html](./target/site/serenity/index.html) file
