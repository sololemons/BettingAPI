Here is a modern and well-structured **README** file for your **Betting API**, following best practices.  

---

# 🎰 Betting API  

### A secure and scalable RESTful API for online betting, built with **Spring Boot** and **JWT authentication**.  

---

## 🚀 Features  

✔ **User Authentication** – Secure login and registration using **JWT**  
✔ **Bet Placement** – Users can place and track their bets  
✔ **Betting History** – Retrieve all bets placed by a user  
✔ **API Documentation** – Integrated with **Swagger UI**  
✔ **Secure Access** – Role-based authentication using **Spring Security**  
✔ **Error Handling** – Consistent HTTP responses with proper error messages  

---

## 🛠️ Tech Stack  

- **Backend**: Spring Boot, Spring Security, JWT Authentication  
- **Database**: MySQL  
- **Security**: Password Hashing (BCrypt) & JWT  
- **Build Tool**: Maven  
- **API Documentation**: Swagger  

---

## 📥 Installation & Setup  

### 1️⃣ Clone the Repository  
```sh
git clone https://github.com/your-username/betting-api.git
cd betting-api
```

### 2️⃣ Open in IntelliJ IDEA  
1. Open **IntelliJ IDEA**  
2. Select **"Open Existing Project"**  
3. Choose the cloned repository folder  

### 3️⃣ Set Up the Database  
Ensure **MySQL** is running and create a new database:  

```sql
CREATE DATABASE betting;

```
Then Import the Databse in My Main Folder
Then, update your database credentials in `src/main/resources/application.properties`:  

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/betting_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4️⃣ Build and Run the Application  
Run the following commands:  

```sh
mvn clean install
mvn spring-boot:run
```

OR  

Run the `BettingApiApplication` class inside IntelliJ IDEA.  

---

## 📖 API Documentation  

After starting the application, the **Swagger UI** documentation will be available at:  

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  

---





## 🛡️ Security  

- Passwords are securely **hashed** using **BCrypt**.  
- **JWT Tokens** are used for authentication.  
  

---





🔥 **Now you’re ready to start betting!** 🚀🎰  

---

