package com.thanarat.comicreader.repository;

import com.thanarat.comicreader.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Long> {

}