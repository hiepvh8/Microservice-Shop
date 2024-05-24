FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} cloudgateway.jar

ENTRYPOINT ["java", "-jar", "/cloudgateway.jar"]

EXPOSE 9090

kubectl create secret docker-registry harbor-registry-secret `
  --docker-server=harbor.io.vn `
  --docker-username=hiepvh8 `
  --docker-password=Zmxncbv2002 `
  --docker-email=hiepvh8@gmail.com

