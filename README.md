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

Interactive Text-Based Menu (CLI)
ระบบจัดการข้อมูลผ่าน Console ตอนเริ่มต้นโปรแกรม
- **เมนู 1:** เพิ่มการ์ตูนเรื่องใหม่พร้อมชื่อผู้แต่งและตอนแรก
- **เมนู 2:** ค้นหาการ์ตูนในระบบและเพิ่มตอนใหม่ 
- **เมนู 3:** ลบการ์ตูนพร้อมตอนย่อยทั้งหมด 
- **เมนู 0:** สิ้นสุดการตั้งค่าเพื่อเริ่มรันระบบ REST API ปกติ



## How to Use
เมื่อโปรแกรมเริ่มทำงาน ให้สังเกตที่หน้าจอ Console/Terminal เพื่อกรอกข้อมูลตามลำดับดังนี้:
1. Insert Comic title: กรอกชื่อการ์ตูน (เช่น Vinland saga)
2. Insert Comic author: กรอกชื่อผู้แต่ง (เช่น Makoto Yukimura)
3. Insert Chapter Title: กรอกชื่อตอน (เช่น ที่ไหนสักแห่งที่ไม่ใช่ที่นี่)

หลังจากกรอกเสร็จ ระบบจะทำการบันทึกลง PostgreSQL และแจ้งสถานะการทำงาน

## API Endpoints

- GET /api/comics - ดูรายชื่อการ์ตูนทั้งหมดและตอนที่มี
- GET /api/comics/{id} - ดูรายละเอียดการ์ตูนรายเรื่อง
- GET /api/comics/search?title=... - ค้นหาการ์ตูนตามชื่อเรื่อง
