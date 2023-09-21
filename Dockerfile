# Use an official Scala runtime as a parent image
FROM openjdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/scala-2.13/scala_mysql-assembly-0.1.0-SNAPSHOT.jar /app/

EXPOSE 4300
# Run the Scala application when the container starts
CMD ["java", "-jar", "scala_mysql-assembly-0.1.0-SNAPSHOT.jar"]