# Dockerfile
# 指定镜像
FROM eclipse-temurin:8-jre

# 挂载项目日志与文件上传本地目录
VOLUME ["/mnt/log/self-cook-book/"]

# 复制jar包到容器中
COPY ./self-cook-book/target/self-cookbook-api-1.0.0.jar /self-cookbook-api.jar

# 暴露端口
EXPOSE 8281
# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 启动命令
ENTRYPOINT ["java", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/mnt/log/self-cook-book/dump.hprof", "-Xms1024m", "-Xmx2048m", "-jar", "/self-cookbook-api.jar"]