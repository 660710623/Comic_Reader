# Comic_Reader
ระบบ Backend สำหรับจัดการข้อมูลการ์ตูนและตอน พัฒนาด้วย Java Spring Boot 

## Tech Stack (เทคโนโลยีที่ใช้)
- Language: Java 21
- Framework: Spring Boot 3.x
- Database: PostgreSQL (Relational Database)
- Container: Docker & Docker Compose
- ORM: Spring Data JPA (Hibernate)

## Database Schema (โครงสร้างข้อมูล)
- Comic Entity: เก็บข้อมูลชื่อเรื่อง (Title), ผู้แต่ง (Author)
- Chapter Entity: เก็บเลขตอน (Chapter Number), ชื่อตอน, และเชื่อมโยงกับ Comic ID

## How to Run

1. Clone โปรเจคนี้ลงเครื่อง
2. เปิด Docker Desktop อยู่
3. รันคำสั่ง docker-compose up -d เพื่อเปิดใช้งาน PostgreSQL
4. กด Run โปรเจคผ่าน IntelliJ หรือใช้คำสั่ง ./mvnw spring-boot:run

## API Endpoints

- GET /api/comics - ดูรายชื่อการ์ตูนทั้งหมดและตอนที่มี
- GET /api/comics/{id} - ดูรายละเอียดการ์ตูนรายเรื่อง
- GET /api/comics/search?title=... - ค้นหาการ์ตูนตามชื่อเรื่อง
