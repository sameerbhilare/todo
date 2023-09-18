@set MAVEN_HOME=C:\apache-maven-3.9.4\bin
@set JAVA_HOME=C:\Program Files\Java\jdk-17.0.4.1
@set PATH=%PATH%;%MAVEN_HOME%;%JAVA_HOME%;

CALL mvn clean -DskipTests install
CALL java -Dspring.profiles.active=dev -jar target/todo-bl-0.0.1-SNAPSHOT.jar
