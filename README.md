# zendesk-coding-challenge

## Prerequisites
- JDK 11 or JDK 14
- Maven 3.6.1
- Your favorite IDE

## Assumptions
- Timestamps are in ascending order
- Vehicle numbers are unique and is in Singapore vehicle number format "xxx #### y", where x and y are capital letters, and the # represents digits between 0-9

## How to add test cases
- Test cases should be in input folder
- Input folder should be in the root folder where you run the app from
- Test cases should be in .txt format

## How to run
### 1. From your IDE
- Run the `Main` class : `src/com/java/Main.java`
- Input the name of test file (e.g. `testFile.txt`) that you want to run in command line

### 2. Using jar file
- Run `mvn clean package` from project root folder
- Copy `target/zendesk-coding-challenge-1.0-SNAPSHOT.jar` file to a folder
- Make `input` folder in the same folder as the jar file
- Add your test files to the `input` folder
- Run `java -jar zendesk-coding-challenge-1.0-SNAPSHOT.jar`
- Input the name of test file (e.g. `testFile.txt`) that you want to run in command line

![image](https://user-images.githubusercontent.com/39576685/115996498-0f39cb00-a612-11eb-816f-bc0e935d59a5.png)


## How to run unit tests
- Run `mvn test` from project root folder

