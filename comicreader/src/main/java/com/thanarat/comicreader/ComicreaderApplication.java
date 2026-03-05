package com.thanarat.comicreader;

import com.thanarat.comicreader.entity.Comic;
import com.thanarat.comicreader.entity.Chapter;
import com.thanarat.comicreader.repository.ComicRepository;
import com.thanarat.comicreader.repository.ChapterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
			System.out.println("Insert Comic title");
			String title = sc.nextLine();
			System.out.println("Insert Comic authora");
			String author = sc.nextLine();
			Comic Comic_title = comicRepo.findAll().stream()
					.filter(c -> title.equals(c.getTitle()))
					.findFirst()
					.orElseGet(() -> {
						Comic newComic = new Comic();
						newComic.setTitle(title);
						newComic.setAuthor(author);

						return comicRepo.save(newComic);
					});

			// 2. เช็คจำนวนตอนปัจจุบันที่มีในระบบ
			long countForThisComic = chapterRepo.countByComicId(Comic_title.getId());
			int nextChapterNum = (int) countForThisComic + 1;

			// 3. สร้างตอนใหม่ (รันเลขต่อจากเดิม)

			System.out.println("Insert Chapter Title");
			String ChapterNum = sc.nextLine();
			Chapter nextChapter = new Chapter();
			nextChapter.setChapterNumber(nextChapterNum);
			nextChapter.setTitle("ตอนที่ " + nextChapterNum + ": "+ChapterNum);
			nextChapter.setComic(Comic_title); // ผูกเข้ากับ Attack on Titan

			chapterRepo.save(nextChapter);

			System.out.println("------------------------------------------");
			System.out.println("บันทึก: " + Comic_title.getTitle());
			System.out.println("เพิ่มตอนที่: " + nextChapterNum + " เรียบร้อยแล้ว!");
			System.out.println("------------------------------------------");

		};

		}

}