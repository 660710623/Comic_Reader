package com.thanarat.comicreader.controller;

import com.thanarat.comicreader.entity.Comic;
import com.thanarat.comicreader.repository.ComicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

    private final ComicRepository comicRepository;


    public ComicController(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }


    @GetMapping
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    @GetMapping("/search")
    public List<Comic> searchByTitle(@RequestParam String title) {
        return comicRepository.findAll().stream()
                .filter(c -> c.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getComicById(@PathVariable Long id) {
        try {
            Comic comic = comicRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("ไม่พบการ์ตูน ID: " + id));
            return ResponseEntity.ok(comic);
        } catch (RuntimeException e) {
            // ดัก Exception แล้วส่งข้อความสวยๆ กลับไปพร้อมสถานะ 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }