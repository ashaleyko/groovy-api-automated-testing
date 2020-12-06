FROM openjdk:8-jdk

COPY ./gradlew /testapp/gradlew
COPY ./gradle /testapp/gradle
RUN cd /testapp && ./gradlew --dry-run

COPY . /testapp
RUN cd /testapp && ./gradlew testClasses
