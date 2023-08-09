# Social Media API Documentation

Welcome to the documentation for the Social Media API! This API allows you to manage user registration, authentication, posts, likes, follows, and more for a social media platform.

## Endpoints

### Authentication

- Register a new user:
    - Endpoint: `/api/v1/auth/register`
    - Method: `POST`
    - Description: Register a new user account.

- Authenticate user:
    - Endpoint: `/api/v1/auth/authenticate`
    - Method: `POST`
    - Description: Authenticate a user and obtain an access token.

- Refresh access token:
    - Endpoint: `/api/v1/auth/refresh-token`
    - Method: `POST`
    - Description: Refresh an expired access token.

### Posts

- Create a new post:
    - Endpoint: `/api/v1/post/create`
    - Method: `POST`
    - Description: Create a new post.

- Get all posts:
    - Endpoint: `/api/v1/post/all`
    - Method: `GET`
    - Description: Get a list of all posts.

- Get post by ID:
    - Endpoint: `/api/v1/post/{id}`
    - Method: `GET`
    - Description: Get a specific post by its ID.

- Delete a post:
    - Endpoint: `/api/v1/post/delete/{id}`
    - Method: `DELETE`
    - Description: Delete a post by its ID.

- Get all posts by user ID:
    - Endpoint: `/api/v1/post/user/all/{userId}`
    - Method: `GET`
    - Description: Get all posts by a specific user.

- Get posts by users followed by a user:
    - Endpoint: `/api/v1/post/following/{userId}`
    - Method: `GET`
    - Description: Get posts from users followed by a specific user.

### Likes

- Create a new like:
    - Endpoint: `/api/v1/like/create`
    - Method: `POST`
    - Description: Create a new like for a post.

- Delete a like:
    - Endpoint: `/api/v1/like/delete`
    - Method: `DELETE`
    - Description: Delete a like for a post.

- Get likes for a post:
    - Endpoint: `/api/v1/like/{postId}`
    - Method: `GET`
    - Description: Get likes for a specific post.

- Check if a user has liked a post:
    - Endpoint: `/api/v1/like/{isLiked}`
    - Method: `GET`
    - Description: Check if a user has liked a specific post.

- Get all likes by user ID:
    - Endpoint: `/api/v1/like/all/{userId}`
    - Method: `GET`
    - Description: Get all likes by a specific user.

### Follows

- Create a new follow:
    - Endpoint: `/api/v1/follow/create`
    - Method: `POST`
    - Description: Create a new follow relationship.

- Delete a follow:
    - Endpoint: `/api/v1/follow/delete`
    - Method: `DELETE`
    - Description: Delete a follow relationship.

## Dockerization

This project is dockerized for easy deployment and containerization. You can build and run the API using Docker.

### Build Docker Image

To build the Docker image, navigate to the project root and execute:
The app will run on port 8099 if you build with docker
- example: `localhost:8099/api/v1/auth/register`

command below refers to my make file which calls all the required command together
```bash 
make all
```

## Building Locally

This project can be easily ran on your localhost too.
### Building locally without docker
- Java 17
- postgres
- Maven
- create postgres database with name: socialMediaApp
- supply your credentials in the `application.properties` file
- The app will run on port 8084 if you build without docker
- example: `localhost:8080/api/v1/auth/register`
- run button || or cd into the app directory and then run this command on your terminal
- `mvn spring-boot:run -Dspring-boot.run.main=com.example.socialmediaapi.SocialMediaApiApplication
  `