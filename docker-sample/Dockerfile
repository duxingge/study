FROM arm64v8/openjdk:8

LABEL maintainer="wjx"

ADD docker-sample-0.0.1-SNAPSHOT.jar /docker-sample.jar
#RUN bash -c 'touch /docker-sample.jar'
ENTRYPOINT ["java", "-jar", "/docker-sample.jar"]
EXPOSE 8081

# 之后运行      docker build -t myapp . 来构建文件
# 最后运行app:  docker run -it --name myapp -p 8081:8081 myapp