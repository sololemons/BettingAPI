Here’s the corrected version of your `README.md` with proper syntax and formatting. You can copy and paste this directly into your `README.md` file:

```markdown
# 🎰 Betting API

## 📌 Overview  
The **Betting API** is a RESTful web service built with **Spring Boot** that allows users to place bets, manage their accounts, and authenticate using JWT tokens.  

---

## 🚀 Features  
✅ **User Authentication** – Secure registration & login using JWT  
✅ **Bet Placement** – Users can place and view their bets.  
✅ **API Documentation** – Integrated with Swagger UI  
✅ **Error Handling** – Proper HTTP status codes, messages, and exceptions to efficiently handle errors.  

---

## 🛠️ Tech Stack  

- **Backend**: Spring Boot, Spring Security, JWT Authentication  
- **Database**: MySQL  
- **Security**: JWT Authentication & Password Hashing  
- **Build Tool**: Maven  
- **API Documentation**: Swagger  

---

## 📥 Installation & Setup  

### 1️⃣ Clone the Repository  
```sh
git clone https://github.com/your-username/betting-api.git
cd betting-api
```

### 2️⃣ Navigate to IntelliJ IDEA  
1. Open IntelliJ IDEA.  
2. Select **Open** and navigate to the `betting-api` directory.  
3. IntelliJ will automatically detect the Maven project and load the dependencies.  

### 3️⃣ Configure the Database  
1. Ensure MySQL is installed and running on your machine.  
2. Create a new database named `betting_db` (or any name you prefer).  
3. Update the `application.properties` file in the `src/main/resources` directory with your database credentials:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/betting_db
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   ```

### 4️⃣ Run the Application  
1. Navigate to the `BettingApiApplication.java` file in the `src/main/java/com/example/bettingapi` directory.  
2. Right-click and select **Run** to start the Spring Boot application.  

### 5️⃣ Access the API  
- The API will be running at `http://localhost:8080`.  
- Access the Swagger UI documentation at `http://localhost:8080/swagger-ui.html`.  

---

## 📚 API Endpoints  

### Authentication  
- **POST** `/api/auth/register` – Register a new user.  
- **POST** `/api/auth/login` – Authenticate and receive a JWT token.  

### Bets  
- **POST** `/placebet/add/bet/?id={userId}` – Place a new bet.  
- **POST** `/betslip/user/{userId}/bet/{betId}` – Retrieve betslips by the user and the bet ID.  

> **Note**: For a complete list of endpoints, refer to the Swagger UI documentation.  

---

## 🔒 Security  
- JWT tokens are used for authentication.  
- Passwords are securely hashed using BCrypt.  

---

## 🛑 Error Handling  
The API returns appropriate HTTP status codes and error messages for various scenarios, such as:  
- `400 Bad Request` – Invalid input data.  
- `401 Unauthorized` – Missing or invalid JWT token.  
- `404 Not Found` – Resource not found.  
- `500 Internal Server Error` – Server-side issues.  

---

## 📄 License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.  

---

## 🙏 Acknowledgments  
- Built with ❤️ using **Spring Boot**.  
- Special thanks to the **Spring Security** and **JWT** libraries for making authentication seamless.  

---

Feel free to contribute, report issues, or suggest improvements! 🚀  
```



😊
