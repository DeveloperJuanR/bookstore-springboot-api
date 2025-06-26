package com.example.bookstore.controller;

import com.example.bookstore.model.Publisher;
import com.example.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @PostMapping
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return publisherRepository.save(publisher);
    }
}
