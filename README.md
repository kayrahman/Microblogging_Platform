The Microblogging Platform project aims to develop a scalable
microservice-based backend system for a social media platform where users can
create posts, follow other users, and interact with each other. The system will be
built using Java, Spring Boot, JPA for data persistence, and Eureka server for
service discovery. An API Gateway will be implemented to manage and route
requests to various microservices efficiently.
Features:
User Service: This microservice manages user authentication, registration, and
profile management. It utilizes Spring Security for user authentication and
authorization. You may skip the Spring Security part, then use manual userID
checking while hitting the APIs.
Post Service: This microservice handles CRUD operations for posts, including
creating, updating, deleting, and retrieving posts. It also manages interactions
such as likes, comments, and sharing.
Follow Service: Manages the relationship between users, allowing users to
follow/unfollow other users and retrieve a list of followers/following for a given
user.
API Gateway: Acts as a single entry point for clients to interact with the
microservices. It handles routing, load balancing, authentication, and other
cross-cutting concerns.
Eureka Server: Provides service discovery and registration for all microservices.
It enables dynamic scaling and fault tolerance by allowing services to find and
communicate with each other without hardcoding endpoints.
Project Structure:
 Each microservice will be implemented as a separate Spring Boot application.
 They will communicate with each other via RESTful APIs.
 The API Gateway will sit in front of the microservices, routing requests based
on the URL path.
 Eureka server will manage service registration and discovery.
 Docker containers can be used to dep
