endpoints
### -- start - get access token
curl --location 'http://localhost:8801/api/users/login' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dGVzdDp0ZXN0' \
--data '{
        "userName": "test",
        "password": "test"

    }'

### - end

### - start - get access token with refresh token

curl --location 'http://localhost:8801/api/refrehtoken/loginWithRefreshToken' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F3F52E7AA753FB783B63039E32F32B49' \
--data '{
        "token": "07fc20c6-16ff-41ee-992c-536b131cf3c1"

    }'

### - end 


#### Start - get call with access token

curl --location 'http://localhost:8801/api/test' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzQ0OTgzMTcwLCJleHAiOjE3NDQ5ODMyNzh9.tZRLrwnI1JoGf8FRslbqIsTrUPg5hr1QcGzJuqtkK7g' \
--header 'Cookie: JSESSIONID=F3F52E7AA753FB783B63039E32F32B49'

### - end
