package com.example.backend.repository;

import com.example.backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitleContainingIgnoreCase(String title);
    List<Song> findByGenreIgnoreCase(String genre);
    List<Song> findByArtistIgnoreCase(String artist);
    List<Song> findByYear(int year);
    List<Song> findAllByOrderByDownloadsDesc();

    List<Song> findByArtistContainingIgnoreCase(String artist);

    List<Song> findByGenreContainingIgnoreCase(String genre);
}