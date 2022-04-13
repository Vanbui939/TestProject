# Amperfii Autotest

1. Download IntelliJ IDEA: https://www.jetbrains.com/idea/download/#section=windows
2. Download Java JDK 8 or over
3. Download Maven
4. Set up Java and Mvn in Environment variables
5. Add Plugin: Cucumber for Java (Settings -> Plugins)
6. Clone code from github: https://github.com/Amperfii/amperfii-autotest.git
7. To run testcase, use command: mvn clean test (default: run in Chrome and dev environment)
   7.1 To run in staging: -D environment=qa
   7.3 To run with another browser: -D browser=FIREFOX (/EDGE/SAFARI)
   7.4 To run underground: -D headless=true
   7.5 To upload report to S3: -D uploadS3=true
