FROM dperiwal/st_repo:gilhari
WORKDIR /Home/Desktop/output
ADD bin ./bin
ADD config ./config
ADD gilhari_service.config .
EXPOSE 8081
CMD ["node","/node/node_modules/gilhari_rest_server/gilhari_rest_server.js","gilhari_service.config"]
