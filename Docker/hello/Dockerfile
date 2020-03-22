FROM ubuntu:latest

LABEL maintainer="http://github.com/X3NNY/DOJ"

COPY /lorun /lorun
COPY /judge.py /

RUN sed -i s@/archive.ubuntu.com/@/mirrors.aliyun.com/@g /etc/apt/sources.list
RUN apt clean
RUN apt update
RUN apt install -y python3 python gcc g++ openjdk-11-jdk
