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




## How to Use
Interactive Text-Based Menu (CLI)
ระบบจัดการข้อมูลผ่าน Console ตอนเริ่มต้นโปรแกรม
- **เมนู 1:** เพิ่มการ์ตูนเรื่องใหม่พร้อมชื่อผู้แต่งและตอนแรก
- **เมนู 2:** ค้นหาการ์ตูนในระบบและเพิ่มตอนใหม่ 
- **เมนู 3:** ลบการ์ตูนพร้อมตอนย่อยทั้งหมด 
- **เมนู 0:** สิ้นสุดการตั้งค่าเพื่อเริ่มรันระบบ REST API ปกติ



## API Endpoints

- Method,Endpoint,Description,Status Code
GET,/,ดึงรายชื่อการ์ตูนทั้งหมด,200 OK
GET,/{id},ดึงข้อมูลการ์ตูนรายเรื่องตาม ID
POST,/,เพิ่มการ์ตูนเรื่องใหม่ลงในระบบ
PUT,/{id},แก้ไขชื่อเรื่องหรือชื่อผู้แต่ง
DELETE,/{id},ลบการ์ตูน (รวมถึงทุกตอนอัตโนมัติ)
