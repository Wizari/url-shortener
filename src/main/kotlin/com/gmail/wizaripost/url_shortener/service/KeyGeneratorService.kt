package com.gmail.wizaripost.url_shortener.service

import com.gmail.wizaripost.url_shortener.repository.ShortUrlRepository
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom

@Service
class KeyGeneratorService(private val shortUrlRepository: ShortUrlRepository) {

    private val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val keyLength = 6

    fun generateUniqueKey(): String {
        var key: String
        do {
            key = generateRandomKey()
        } while (shortUrlRepository.findByShortKey(key) != null)
        return key
    }

    private fun generateRandomKey(): String {
        return (1..keyLength)
            .map { charset[ThreadLocalRandom.current().nextInt(charset.length)] }
            .joinToString("")
    }
}