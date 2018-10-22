spring 1.5  redis 3 
spring 2 redis 4,5 ile calisiyor ,
"org.springframework.data.redis.serializer.SerializationException:
local class incompatible: stream classdesc serialVersionUID = 500, local class serialVersionUID = 420
"
---
curl -X POST -d "grant_type=password&username=b&password=b&client_id=demo&client_secret=demo" http://localhost:18085/oauth/token

curl -X POST --user "demo:demo" -d "grant_type=password&username=b&password=b" http://localhost:18085/oauth/token

curl http://localhost:18084/api/employee-list-all/ -H "Authorization: Bearer 719b5c4f-42af-4a17-a7f1-81a0ae86ced2"

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=b&password=b&client_id=demo&client_secret=demo&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -d "grant_type=password&username=b&password=s&client_id=demo&client_secret=demo&scope=demoScope" http://localhost:18085/oauth/token

curl http://localhost:18085/api/user/hello -H "Authorization: Bearer 3dc7ca50-7091-41a5-8e3f-a00c56ff9b46"
