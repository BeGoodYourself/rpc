::protoc -I=../protos --java_out=../api/src/main/java ../protos/message.proto
protoc -I=../protos --java_out=../sample-api/src/main/java ../protos/CalService.proto
pause