FROM eclipse-temurin:24-jdk-jammy AS build
    VOLUME /tmp
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} app.jar
    # Ensure the 'data' directory exists and copy the H2 database file
    RUN mkdir -p /data
    COPY data/factoryDB.mv.db /data/factoryDB.mv.db
    # Assuming the actual H2 file is factoryDB.mv.db
    #COPY data/factoryDB.trace.db /data/factoryDB.trace.db 
    # If trace file exists
    ENTRYPOINT ["java","-jar","/app.jar"]