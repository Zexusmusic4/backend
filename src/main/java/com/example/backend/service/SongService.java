package com.example.backend.service;

import com.example.backend.model.Song;
import com.example.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    public List<Song> searchSongsByArtist(String artist) {
        return songRepository.findByArtistContainingIgnoreCase(artist);
    }

    public List<Song> searchSongsByGenre(String genre) {
        return songRepository.findByGenreContainingIgnoreCase(genre);
    }

    public List<Song> searchSongsByYear(int year) {
        return songRepository.findByYear(year);
    }

    public Song saveSong(Song song) {
        return songRepository.save(song);
    }
}
