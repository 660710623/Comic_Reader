package com.thanarat.comicreader.controller;

import com.thanarat.comicreader.entity.Comic;
import com.thanarat.comicreader.repository.ComicRepository;
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
    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
        return comicRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());a
    }
}