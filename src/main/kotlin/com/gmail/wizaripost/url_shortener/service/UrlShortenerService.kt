package com.gmail.wizaripost.url_shortener.service

import com.gmail.wizaripost.url_shortener.model.ShortUrl
import com.gmail.wizaripost.url_shortener.repository.ShortUrlRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UrlShortenerService(
    private val shortUrlRepository: ShortUrlRepository,
    private val keyGenerator: KeyGeneratorService
) {

    fun shortenUrl(originalUrl: String, expiryDate: LocalDateTime? = null): ShortUrl {
        val shortKey = keyGenerator.generateUniqueKey()
        val shortUrl = ShortUrl(
            originalUrl = originalUrl,
            shortKey = shortKey,
            expiryDate = expiryDate
        )
        return shortUrlRepository.save(shortUrl)
    }

    fun findActiveUrlByShortKey(shortKey: String): ShortUrl? {
        return shortUrlRepository.findActiveByShortKey(shortKey, LocalDateTime.now())
    }

    fun incrementClick(shortKey: String) {
        shortUrlRepository.incrementClickCount(shortKey)
    }
}