package com.thanarat.comicreader;

import com.thanarat.comicreader.entity.Comic;
import com.thanarat.comicreader.entity.Chapter;
import com.thanarat.comicreader.repository.ComicRepository;
import com.thanarat.comicreader.repository.ChapterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ComicreaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicreaderApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ComicRepository comicRepo, ChapterRepository chapterRepo, HandlerMappingIntrospector mvcHandlerMappingIntrospector) {
		return args -> {
			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.println("======= ระบบจัดการข้อมูลการ์ตูน =======");
				System.out.println("1. เพิ่มเรื่องใหม่ (พร้อมชื่อผู้แต่ง และตอนแรก)");
				System.out.println("2. เพิ่มตอนใหม่ (จากเรื่องที่มีอยู่แล้ว)");
				System.out.println("3. ลบการ์ตูน (และตอนทั้งหมด)");
				System.out.println("4. ลบเฉพาะตอน (จากเรื่องที่มีอยู่)");
				System.out.println("5. แก้ไขชื่อเรื่อง (และชื่อผู้แต่ง)");
				System.out.println("6. แก้ไขชื่อตอน (จากเรื่องที่มีอยู่)");
				System.out.println("0. ออกจากโปรแกรม");
				System.out.print("เลือกเมนู (0-6): ");

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
						ch1.setTitle(chapterTitle);
						ch1.setComic(savedComic);
						chapterRepo.save(ch1);

						System.out.println("บันทึกเรื่อง " + title + " พร้อมตอนที่ 1 เรียบร้อย");

					} else if (choice.equals("2")) {
						// เมนูที่ 2: เพิ่มตอนใหม่
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ยังไม่มีการ์ตูนในระบบ");
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
						nextCh.setTitle(chTitle);
						nextCh.setComic(targetComic);
						chapterRepo.save(nextCh);

						System.out.println("เพิ่มตอนที่ " + nextNum + " ของเรื่อง " + targetComic.getTitle() + " สำเร็จ!");

					} else if (choice.equals("3")) {
						// เมนูที่ 3: ลบการ์ตูน
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ไม่มีข้อมูลให้ลบ");
							continue;
						}

						System.out.println("\nเลือกเรื่องที่ต้องการลบ:");
						allComics.forEach(c -> System.out.println(c.getId() + ". "  +c.getTitle()));
						System.out.print("พิมพ์ ID ที่ต้องการลบ: ");
						Long deleteId = Long.parseLong(sc.nextLine());

						if (comicRepo.existsById(deleteId)) {
							comicRepo.deleteById(deleteId);
							System.out.println("ลบข้อมูลเรียบร้อยแล้ว");
						} else {
							System.out.println("ไม่พบ ID ดังกล่าว");
						}
					} else if (choice.equals("4")) {
						List<Chapter> allChapters = chapterRepo.findAll();
						if (allChapters.isEmpty()) {
							System.out.println("ไม่มีตอนให้ลบ");
							continue;
						}
						allChapters.forEach(ch -> System.out.println(ch.getId() + ". ตอนที่ " +ch.getChapterNumber() +" "+ ch.getTitle()));
						System.out.print("พิมพ์ ID ของ 'ตอน' ที่ต้องการลบ: ");
						Long delchapId = Long.parseLong(sc.nextLine());

						chapterRepo.findById(delchapId).ifPresentOrElse(chapter -> {
							// ดึง ID ของ Comic ออกมาเก็บไว้ก่อน
							Long comicId = chapter.getComic().getId();

							// 2. สั่งลบตอนนั้นทิ้ง
							chapterRepo.delete(chapter);
							System.out.println("🗑️ ลบตอนเรียบร้อย กำลังจัดลำดับเลขตอนใหม่...");

							// 3. Re-indexing: ดึงตอนที่เหลือของเรื่องนั้นมาเรียงใหม่
							List<Chapter> remainingChapters = chapterRepo.findByComicIdOrderByChapterNumberAsc(comicId);
							int newNumber = 1;
							for (Chapter c : remainingChapters) {
								c.setChapterNumber(newNumber++);
							}

							// 4. บันทึกทับ
							chapterRepo.saveAll(remainingChapters);
							System.out.println("จัดลำดับเลขตอนใหม่สำเร็จ!");

						}, () -> System.out.println("ไม่พบ ID ตอนที่ระบุ"));

				}else if (choice.equals("5")) {
						// เมนูที่ 4: แก้ชื่อเรื่องและชื่อผู้เเต่ง
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ไม่มีข้อมูลให้แก้ไข");
							continue;
						}
						allComics.forEach(c -> System.out.println(c.getId() + ". " + c.getTitle()));
						System.out.print("พิมพ์ ID เรื่องที่ต้องการแก้ไข: ");
						Long id = Long.parseLong(sc.nextLine());

						comicRepo.findById(id).ifPresentOrElse(comic -> {
							System.out.print("ชื่อเรื่องใหม่ (เดิม: " + comic.getTitle() + "): ");
							comic.setTitle(sc.nextLine());
							System.out.print("ชื่อผู้แต่งใหม่ (เดิม: " + comic.getAuthor() + "): ");
							comic.setAuthor(sc.nextLine());

							comicRepo.save(comic);
							System.out.println("อัปเดตข้อมูลสำเร็จ!");
						}, () -> System.out.println("ไม่พบการ์ตูน ID นี้"));
					}
					else if (choice.equals("6")) {
						List<Comic> allComics = comicRepo.findAll();
						if (allComics.isEmpty()) {
							System.out.println("ไม่มีข้อมูลการ์ตูนในระบบ");
							continue;
						}

						System.out.println("\n--- เลือกการ์ตูนที่ต้องการแก้ไข ---");
						allComics.forEach(c -> System.out.println(c.getId() + ". " + c.getTitle()));
						System.out.print("พิมพ์ ID ของเรื่อง: ");
						Long comicId = Long.parseLong(sc.nextLine());

						comicRepo.findById(comicId).ifPresentOrElse(comic -> {

							List<Chapter> chapters = chapterRepo.findByComicIdOrderByChapterNumberAsc(comicId);
							if (chapters.isEmpty()) {
								System.out.println("การ์ตูนเรื่องนี้ยังไม่มีตอนย่อย");
							} else {
								System.out.println("\n--- รายชื่อตอนของเรื่อง: " + comic.getTitle() + " ---");
								chapters.forEach(ch -> System.out.println("ID: " + ch.getId() + " | ตอนที่: " + ch.getChapterNumber() + " | ชื่อเดิม: " + ch.getTitle()));

								System.out.print("พิมพ์ ID ของตอนที่ต้องการแก้ชื่อ: ");
								Long chapId = Long.parseLong(sc.nextLine());


								chapterRepo.findById(chapId).ifPresentOrElse(chapterToEdit -> {
									System.out.print("กรอกชื่อตอนใหม่: ");
									String newName = sc.nextLine();

									chapterToEdit.setTitle(newName);
									chapterRepo.save(chapterToEdit);

									System.out.println("อัปเดตชื่อตอนเรียบร้อยแล้ว");
								}, () -> System.out.println("ไม่พบ ID ตอนดังกล่าว"));
							}

						}, () -> System.out.println("ไม่พบ ID การ์ตูนเรื่องนี้"));
				} else if (choice.equals("0")) {
						System.out.println("ออกจากโปรแกรมเรียบร้อย");
						break; // ออกจาก Loop เพื่อไปรัน Spring Boot ปกติ
					} else {
						System.out.println("กรุณาเลือก 0, 1, 2, 3, 4, 5, 6");
					}
				} catch (Exception e) {
					System.err.println("เกิดข้อผิดพลาด: " + e.getMessage());
				}
			}
		};
	}
}