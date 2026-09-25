package com.example.demo.controller;

import com.example.demo.model.Url;
import com.example.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")  // Abhi ke liye sab origins allow, baad mein specific frontend URL denge
public class UrlController {

    @Autowired
    private UrlService urlService;

    // URL shorten karne wala API
    @PostMapping("/api/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");

        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest().body("originalUrl is required");
        }

        Url savedUrl = urlService.createShortUrl(originalUrl);

        Map<String, Object> response = Map.of(
                "shortCode", savedUrl.getShortCode(),
                "originalUrl", savedUrl.getOriginalUrl(),
                "shortUrl", "http://localhost:8080/" + savedUrl.getShortCode()
        );

        return ResponseEntity.ok(response);
    }

    // Short URL pe click hote hi original URL pe redirect karega
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        Optional<Url> urlOptional = urlService.getUrlByShortCode(shortCode);

        if (urlOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlOptional.get().getOriginalUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);  // 302 redirect
    }
}