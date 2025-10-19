package com.gmail.wizaripost.url_shortener.controller

import com.gmail.wizaripost.url_shortener.service.UrlShortenerService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class ShortenRequest(
    @field:NotBlank
//    @field:Pattern(regexp = "https?://.+")
    val url: String
)

data class ShortenResponse(
    val shortUrl: String
)

@RestController
class UrlController(
    private val urlShortenerService: UrlShortenerService
) {

    @PostMapping("/api/shorten")
    fun shorten(@Valid @RequestBody request: ShortenRequest): ResponseEntity<ShortenResponse> {
        println(request)
        val shortUrl = urlShortenerService.shortenUrl(request.url)
        val shortLink = "http://localhost:9100/${shortUrl.shortKey}"
        return ResponseEntity.ok(ShortenResponse(shortLink))
    }

    @GetMapping("/{shortKey}")
    fun redirect(@PathVariable shortKey: String): ResponseEntity<Unit> {
        val shortUrl = urlShortenerService.findActiveUrlByShortKey(shortKey)
            ?: return ResponseEntity.notFound().build()

        urlShortenerService.incrementClick(shortKey)

        return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, shortUrl.originalUrl)
            .build()
    }
}