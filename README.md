This is an small api to upload file or data to s3 bucket with spring boot using localstack.

Steps to reproduce

1. Clone repo
2. Run docker-compose up
3. You'll need declared previously next variables in you local pc: AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY
4. Create a s3 bucket with the next command: aws --endpoint-url=http://localhost:4566 s3 mb s3://{your_bucket_name}
5. Run api spring-boot:run 🚀 Note: if you're using mac use ./mvnw spring-boot:run
6. Test the api via your prefered app (postman, thunder client on vs code, ...)
7. You'll have two endpoints
  - GET: To see all bucket objects
  - POST: Create an object in the bucket
8. Y'll be able to see objects with the next command: aws --endpoint-url=http://localhost:4566 s3 ls s3://{your_bucket_name}
9. If you want to update docker image remember firstly run package command. Note: if you're using mac is ./mvnw package  
10. Enjoy! This is a basic example use this as kickoff, use your best skills as developer to improve te code.
