# GridFsStorage
Example project that shows how to store files in MongoDB (GridFS) using Spring Boot. It's ready for deploying with Docker. Thanks for @afucher for his FileStorage repo that I used as a base.

Use Gradle to build a project:

`gradle clean  && gradle build && gradle buildDocker`

Then deploy a stack:


`docker stack up name_of_stack -c docker/docker-compose.yml grid`
