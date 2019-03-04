FROM circleci/openjdk:8-jdk

RUN curl -sL https://deb.nodesource.com/setup_11.x | sudo -E bash -
RUN sudo apt-get install -y nodejs
RUN sudo npm install -g serverless

