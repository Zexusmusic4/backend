package com.example.backend.controller;

import com.example.backend.model.Song;
import com.example.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Song> uploadSong(
            @RequestParam("title") String title,
            @RequestParam("artist") String artist,
            @RequestParam("producer") String producer,
            @RequestParam("year") int year,
            @RequestParam("genre") String genre,
            @RequestParam("image") MultipartFile image,
            @RequestParam("songData") MultipartFile songData
    ) {
        try {
            Song song = new Song();
            song.setTitle(title);
            song.setArtist(artist);
            song.setProducer(producer);
            song.setYear(year);
            song.setGenre(genre);
            song.setImage(image.getBytes());
            song.setSongData(songData.getBytes());
            songRepository.save(song);
            return new ResponseEntity<>(song, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        return new ResponseEntity<>(songRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Song>> searchSongs(@RequestParam("query") String query) {
        return new ResponseEntity<>(songRepository.findByTitleContainingIgnoreCase(query), HttpStatus.OK);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Song>> getSongsByGenre(@RequestParam("genre") String genre) {
        return new ResponseEntity<>(songRepository.findByGenreIgnoreCase(genre), HttpStatus.OK);
    }

    @GetMapping("/artist")
    public ResponseEntity<List<Song>> getSongsByArtist(@RequestParam("artist") String artist) {
        return new ResponseEntity<>(songRepository.findByArtistIgnoreCase(artist), HttpStatus.OK);
    }

    @GetMapping("/year")
    public ResponseEntity<List<Song>> getSongsByYear(@RequestParam("year") int year) {
        return new ResponseEntity<>(songRepository.findByYear(year), HttpStatus.OK);
    }
    @GetMapping("/downloads")
    public ResponseEntity<List<Song>> getSongsSortedByDownloads() {
        return new ResponseEntity<>(songRepository.findAllByOrderByDownloadsDesc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return songRepository.findById(id)
                .map(song -> new ResponseEntity<>(song, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getSongImage(@PathVariable Long id) {
        return songRepository.findById(id)
                .map(song -> ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(song.getImage()))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{id}/song")
    public ResponseEntity<byte[]> getSongData(@PathVariable Long id) {
        return songRepository.findById(id)
                .map(song -> ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(song.getSongData()))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/incrementDownloads")
    public ResponseEntity<Song> incrementDownloads(@PathVariable Long id) {
        return songRepository.findById(id)
                .map(song -> {
                    song.setDownloads(song.getDownloads() + 1);
                    songRepository.save(song);
                    return new ResponseEntity<>(song, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}