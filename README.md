What you’ll need
- Java 1.8

How to build<br />
 gradlew clean build
 
How to run<br />
 parser.jar is located ../WallethubParser/build/libs<br />
Examples: <br />
 -java -jar "parser.jar" --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100<br />
 -java -jar "parser.jar" --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 

If argument --accesslog is absent, application tries to find ./access.log
