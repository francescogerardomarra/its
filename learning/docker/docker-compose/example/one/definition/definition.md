# Example 1 Definition

- in this chapter, we created a simple Docker Compose project to show in practice the concepts illustrated until here;
- the project is composed of **3 services**:
  - **frontend**;
  - **backend**;
  - **db**.
  
**How the project works:**

- an HTML page with an input text is shown to a user who reaches the application;
- the user insert a text and press button `send`;
- the backend service is a Spring Boot project that exposes the API and contains the business logic to save the data to db;


- db is a Postgres service to preserve the data.
