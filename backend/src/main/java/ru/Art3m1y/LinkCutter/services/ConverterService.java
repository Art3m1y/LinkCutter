package ru.Art3m1y.LinkCutter.services;

import org.springframework.stereotype.Service;

@Service
public class ConverterService {
    private final String availableCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encodeId(long id) {
        StringBuilder encoded = new StringBuilder();

        while (id > 0) {
            encoded.append(availableCharacters.charAt((int) (id % 62)));

            id /= 62;
        }

        return encoded.reverse().toString();
    }

    public long decodeShortenedURI(String shortenedURI) {
        shortenedURI = new StringBuilder(shortenedURI).reverse().toString();

        long id = 0;

        int k = 0;

        for (int i = 0; i < shortenedURI.length(); i++) {
            id += Math.pow(62, k) * availableCharacters.indexOf(shortenedURI.charAt(i));

            k += 1;
        }

        return id;
    }
}
