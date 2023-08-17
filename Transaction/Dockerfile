# 使用 Java 8 作为基础镜像
FROM openjdk:8-jre-alpine

# 设置工作目录
WORKDIR /app

# 复制应用程序 JAR 文件到容器
COPY target/Synpulse_Take_Home_Assessment.jar /app/

# 运行 Java 应用程序
CMD ["java", "-jar", "Synpulse_Take_Home_Assessment.jar"]