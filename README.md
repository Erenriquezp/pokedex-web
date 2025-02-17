# **🛍️ Pokédex Web**  
Welcome to **Pokédex Web**, a modern and advanced web application to explore detailed Pokémon information! 🔥⚡  

## **🔹 Project Overview**  
Pokédex Web allows you to **search, view, and analyze Pokémon data**, including **stats, sprites, and evolution chains**.  
It is built with a **high-performance architecture**, leveraging modern web technologies and optimized database storage.  
![image](https://github.com/user-attachments/assets/2718aeae-9047-445c-a3bf-895fff8f7bb4)

---

## **🚀 Key Features**  

### **🔍 Search & Pokémon Details**  
✅ **Real-time search** by name.  
✅ **Comprehensive Pokémon details**: ID, height, weight, base experience, types, and abilities.  
✅ **Optimized image loading**: High-quality sprites, including shiny and alternate versions.  

### **📊 Stats & Advanced Data**  
✅ **Dynamic stat visualization** (HP, attack, defense, speed, etc.).  
✅ **Efficient data management** with API integration and local database storage.  
✅ **Optimized pagination** for smooth navigation between Pokémon.  

### **🖼️ Sprites & 3D Models**  
✅ **Multiple sprite styles**: Classic 2D, Dream World, Home, and Official Artwork.  
✅ **Shiny versions** available for Pokémon with special variations.  
✅ **Fast and adaptive loading** for a smooth experience.  

### **🔄 Pokémon Evolution**  
✅ **Interactive visualization** of evolution chains.  
✅ **Support for complex evolution trees** with multiple paths and conditions.  

### **⚡ Performance & Optimization**  
✅ **Reactive architecture with WebFlux** for ultra-fast loading.  
✅ **Optimized SQL database storage** for instant searches.  
✅ **Spring Boot + WebClient** for efficient API calls.  
✅ **Batch processing with Spring Batch** for data preloading.  
✅ **Parallelized processing** to handle multiple requests smoothly.  

### **🎨 Modern Web Interface**  
✅ **Dark 🌙 and Light ☀️ modes** for personalized themes.  
✅ **Responsive design** using Bootstrap and modern CSS.  
✅ **Smooth animations** for data visualization.  

---

## **🛠️ Technologies Used**  

### **📌 Backend:**  
- **Spring Boot** → Main backend framework.  
- **Spring WebFlux** → Reactive programming for optimized API calls.  
- **Spring Data JPA** → Efficient database management.  
- **WebClient** → Non-blocking HTTP client for external API requests.  
- **Spring Batch** → Optimized batch data loading.  
- **Hibernate** → ORM for MySQL database integration.  
- **MySQL** → Primary database for persistent storage.  

### **🌐 Frontend:**  
- **HTML5, CSS3, JavaScript** → Core web technologies.  
- **Fetch API** → Asynchronous data fetching without page reloads.  

---

## **📂 Project Structure**  
```
/pokedex-web
│── src/
│   ├── main/
│   │   ├── java/ec/edu/uce/pokedexweb/
│   │   │   ├── controller/    # REST Controllers
│   │   │   ├── models/        # JPA Entities
│   │   │   ├── repository/    # Database Repositories
│   │   │   ├── service/       # Business Logic Services
│   │   │   ├── config/        # Application Configuration
│   ├── resources/
│   │   ├── static/
│   │   │   ├── css/           # Stylesheets
│   │   │   ├── js/            # Frontend Logic
│   │   │   ├── pages/         # HTML Views
│   │   ├── templates/         # Thymeleaf Templates
│── pom.xml                    # Maven Dependencies
│── README.md                   # Documentation
```

---

## **🔧 Installation & Setup**  

### **1️⃣ Clone the Repository**  
```sh
git clone https://github.com/Erenriquezp/pokedex-web.git
cd pokedex-web
```

### **2️⃣ Set Up the Database**  
1. **Create the MySQL database:**  
```sql
CREATE DATABASE pokedex_web;
```
2. **Configure `application.properties`:**  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pokedex_web
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### **3️⃣ Run the Application**  
```sh
mvn spring-boot:run
```

### **4️⃣ Access the Web Application**  
📌 **Open in your browser:**  
```sh
http://localhost:8080/pages/index.html
```

---

## **🛠️ REST API Endpoints**  
📌 **Get Pokémon details by name:**  
```http
GET /api/pokemon/name/{name}
```
📌 **Get Pokémon stats:**  
```http
GET /api/stats/{name}
```
📌 **Get Pokémon sprites:**  
```http
GET /api/sprites/{name}
```
📌 **Get Pokémon evolution chain:**  
```http
GET /api/evolution/{name}
```

---

## **📢 Contributions**  
Contributions are welcome! Feel free to submit a **Pull Request** or report any issues.  

---

## **📜 License**  
This project is licensed under the **MIT License**, meaning you are free to modify and distribute it.  

---

🎉 **Thanks for visiting Pokédex Web!** 🏆💡
