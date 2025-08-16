🎓 Online Course Management Portal – Backend

This is the backend of the **Online Course Management Portal**, built using **Spring Boot** and **MySQL**.  
It provides RESTful API endpoints for **authentication, course management, enrollments, and user dashboards**.  

Authentication and authorization are implemented using **JWT tokens** for secure access.  


🚀 Features
- Spring Boot REST API architecture  
- JWT-based authentication with **role-based access control**  
- MySQL database integration with **Spring Data JPA (Hibernate)**  
- CRUD operations for courses and enrollments  
- Separate APIs for **Admin** and **Student** functionalities  
- Mock payment API integration for enrollments  
- CORS-enabled for frontend communication  
- Error handling and custom exceptions  
- Swagger/OpenAPI documentation (if enabled)  



## 📦 Tech Stack
- **Backend Framework**: Spring Boot  
- **Database**: MySQL  
- **ORM**: Spring Data JPA (Hibernate)  
- **Authentication**: JWT (JSON Web Token)  
- **Build Tool**: Maven  
- **API Documentation**: Swagger/OpenAPI  



## ⚙️ Running the Backend

### 1. Clone the repository

git clone https://github.com/YOUR_GITHUB_USERNAME/Online_Course_Management_Portal_BackEnd.git

2. Open in your IDE
Use STS, IntelliJ IDEA, Eclipse, or VS Code (with Java support).

3. Configure MySQL Database
Update application.properties with your database credentials:

**properties**

spring.datasource.url=jdbc:mysql://localhost:3306/online_course_portal
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

**4. Run the Application**
mvn spring-boot:run
