package com.example.demo.service;

import com.example.demo.model.Url;
import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    // URL ko shorten karne ka main method
    public Url createShortUrl(String originalUrl) {
        String shortCode = generateUniqueShortCode();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);

        return urlRepository.save(url);
    }

    // Random unique short code generate karta hai
    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateRandomCode();
        } while (urlRepository.existsByShortCode(shortCode));
        return shortCode;
    }

    // 6-character random alphanumeric code banata hai
    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    // Short code se original URL dhundhta hai (redirect ke liye)
    public Optional<Url> getUrlByShortCode(String shortCode) {
        Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);

        // Agar mila, toh click count badhao
        urlOptional.ifPresent(url -> {
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
        });

        return urlOptional;
    }
}

