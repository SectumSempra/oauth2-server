
***********password

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=b&password=b&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -d "grant_type=password&username=b&password=b&client_id=demo&client_secret=demo&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=b&password=b&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=abc&password=abc&scope=demoScope" http://localhost:18085/oauth/token

****************client_credentials


curl -X POST  -d "grant_type=client_credentials&client_id=demo&client_secret=demo&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=client_credentials&scope=demoScope" http://localhost:18085/oauth/token


****************implicit  ??????

curl -X POST  -d "grant_type=implicit&client_id=demo&scope=demoScope"  http://localhost:18085/oauth/authorize?response_type=token



curl -X POST http://localhost:18085/oauth/token?response_type=token&client_id=demo&scope=read


http://localhost:18085/auth?response_type=token&client_id=demo&&scope=read&state=xcoiv98y3md22vwsuye3kch

*****************authorization_code


http://localhost:18085/oauth/authorize?response_type=code&client_id=test&redirect_url=http://localhost:8484/client/login&scope=read



*********DATA ****

curl http://localhost:18084/api/currentUser -H "Authorization: Bearer d1a891bc-2612-408f-ac1f-3bffa0f26fcd" -H "Content-type: application/json; charset=utf-8"


curl http://localhost:18084/api/employee-list-all/ -H "Authorization: Bearer ec8b60c2-3368-482f-b7be-b5f6770f291c" -H "Content-type: application/json; charset=utf-8"


curl http://localhost:18084/api/get-all-regions  -H "Authorization: Bearer 156b7e1e-3045-4940-a87b-19648c0ce4c7" -H "Content-type: application/json; charset=utf-8"



curl http://localhost:18085/principal -H "Authorization: Bearer ec8b60c2-3368-482f-b7be-b5f6770f291c"


 curl -X POST -H "Content-Type: application/x-www-form-urlencoded"  -d "grant_type=password&username=abc&password=abc&scope=demoScope" http://localhost:18085/oauth/token



abc user /
 curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=abc&password=abc&scope=demoScope" http://localhost:18085/oauth/token

 curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=abc&password=abc&scope=demoScope" http://localhost:18085/oauth/token


bb user /

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=bb&password=b&scope=demoScope" http://localhost:18085/oauth/token
 
b user /

 curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password&username=b&password=b&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v" "http://localhost:18085/oauth/token?grant_type=refresh_token&refresh_token=a6a6e479-eb00-42f0-8913-c9d52032a03e"


bb sms/
curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password-sms&username=bb&password=b&scope=demoScope" http://localhost:18085/oauth/token

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password-sms&username=b&password=b&scope=demoScope" http://localhost:18085/oauth/token


curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=mfa&mfa_token=9f5c5c38-acaf-4cb8-926c-fd78a29f304e&mfa_code=000000" http://localhost:18085/oauth/token


b smsm/

curl -X POST -H "Authorization: Basic ZGVtbzpkZW1v"  -d "grant_type=password-sms&username=b&password=b&scope=demoScope" http://localhost:18085/oauth/token



--docker redis-
docker run  -p 6379:6379 -d --name localredis redis redis-server --protected-mode no 

******************

auth-server: http://localhost:18085

server:
  port: 8484
  context-path: /client

security:
    oauth2:
      client:
        client-id: test
        client-secret: test
        access-token-uri: ${auth-server}/oauth/token
        user-authorization-uri: ${auth-server}/oauth/authorize
      resource:
        user-info-uri: ${auth-server}/principal

@SpringBootApplication
@Configuration
@RestController
@EnableOAuth2Sso
public class ClientApplication {