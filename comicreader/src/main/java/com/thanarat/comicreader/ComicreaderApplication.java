package com.thanarat.comicreader;

import com.thanarat.comicreader.entity.Comic;
import com.thanarat.comicreader.entity.Chapter;
import com.thanarat.comicreader.repository.ComicRepository;
import com.thanarat.comicreader.repository.ChapterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class ComicreaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicreaderApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ComicRepository comicRepo, ChapterRepository chapterRepo) {
		return args -> {
			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.println("======= ระบบจัดการข้อมูลการ์ตูน =======");
				System.out.println("1. เพิ่มเรื่องใหม่ (พร้อมชื่อผู้แต่ง และตอนแรก)");
				System.out.println("2. เพิ่มตอนใหม่ (จากเรื่องที่มีอยู่แล้ว)");
				System.out.println("3. ลบการ์ตูน (และตอนทั้งหมด)");
				System.out.println("0. ออกจากโปรแกรม");
				System.out.print("เลือกเมนู (0-3): ");

				String choice = sc.nextLine();

				try {
					if (choice.equals("1")) {
						// เมนูที่ 1: เพิ่มเรื่องใหม่
						System.out.print("กรอกชื่อเรื่อง: ");
						String title = sc.nextLine();
						System.out.print("กรอกชื่อผู้แต่ง: ");
						String author = sc.nextLine();
						System.out.print("กรอกชื่อตอนที่ 1: ");
						String chapterTitle = sc.nextLine();

						// บันทึกเรื่องใหม่
						Comic newComic = new Comic();
						newComic.setTitle(title);
						newComic.setAuthor(author);
						Comic savedComic = comicRepo.save(newComic);

						// บันทึกตอนที่ 1
						Chapter ch1 = new Chapter();
						ch1.setChapterNumber(1);
						ch1.setTitle("ตอนที่ 1: " + chapterTitle);
						ch1.setComic(savedComic);
						chapterRepo.save(ch1);

						System.out.println("บันทึกเรื่อง " + title + " พร้อมตอนที่ 1 เรียบร้อย");

					} else if (choice.equals("2")) {
						// เพิ่มตอนลงเรื่องเดิม
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ยังไม่มีการ์ตูนในระบบ กรุณาเลือกเมนู 1 ก่อน");
							continue;
						}

						System.out.println("\nรายชื่อการ์ตูนในระบบ:");
						allComics.forEach(c -> System.out.println(c.getId() + ". " + c.getTitle()));
						System.out.print("พิมพ์ ID ของเรื่องที่ต้องการเพิ่มตอน: ");
						Long comicId = Long.parseLong(sc.nextLine());

						Comic targetComic = comicRepo.findById(comicId)
								.orElseThrow(() -> new Exception("ไม่พบ ID นี้ในระบบ"));

						long count = chapterRepo.countByComicId(targetComic.getId());
						int nextNum = (int) count + 1;

						System.out.print("กรอกชื่อตอนที่ " + nextNum + ": ");
						String chTitle = sc.nextLine();

						Chapter nextCh = new Chapter();
						nextCh.setChapterNumber(nextNum);
						nextCh.setTitle("ตอนที่ " + nextNum + ": " + chTitle);
						nextCh.setComic(targetComic);
						chapterRepo.save(nextCh);

						System.out.println("เพิ่มตอนที่ " + nextNum + " ของเรื่อง " + targetComic.getTitle() + " สำเร็จ!");

					} else if (choice.equals("3")) {
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ไม่มีข้อมูลให้ลบ");
							continue;
						}

						System.out.println("\nเลือกเรื่องที่ต้องการลบ:");
						allComics.forEach(c -> System.out.println(c.getId() + ". " + c.getTitle()));
						System.out.print("พิมพ์ ID ที่ต้องการลบ: ");
						Long deleteId = Long.parseLong(sc.nextLine());

						if (comicRepo.existsById(deleteId)) {
							comicRepo.deleteById(deleteId);
							System.out.println("ลบข้อมูลเรียบร้อยแล้ว");
						} else {
							System.out.println("ไม่พบ ID ดังกล่าว");
						}
					}  else if (choice.equals("0")) {
						System.out.println("ออกจากโปรแกรมเรียบร้อย");
						break; // ออกจาก Loop เพื่อไปรัน Spring Boot ปกติ
					} else {
						System.out.println("กรุณาเลือก 0, 1 หรือ 2");
					}
				} catch (Exception e) {
					System.err.println("เกิดข้อผิดพลาด: " + e.getMessage());
				}
			}
		};
	}
}