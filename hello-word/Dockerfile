FROM arm64v8/openjdk:8

LABEL maintainer="wjx"

ADD hello-word-0.0.1-SNAPSHOT.jar /hello-word.jar
#RUN bash -c 'touch /hello-word.jar'
ENTRYPOINT ["java", "-jar", "/hello-word.jar"]
EXPOSE 8081

# 之后运行      docker build -t helloworld . 来构建文件
# 最后运行app:  docker run -it --name myapp -p 8081:8081 myapp