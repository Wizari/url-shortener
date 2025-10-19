package com.gmail.wizaripost.url_shortener.repository

import com.gmail.wizaripost.url_shortener.model.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ShortUrlRepository : JpaRepository<ShortUrl, Long> {

    fun findByShortKey(shortKey: String): ShortUrl?

    @Modifying
    @Query("UPDATE ShortUrl s SET s.clickCount = s.clickCount + 1 WHERE s.shortKey = :shortKey")
    fun incrementClickCount(shortKey: String)

    @Query("SELECT s FROM ShortUrl s WHERE s.shortKey = :shortKey AND (s.expiryDate IS NULL OR s.expiryDate > :now)")
    fun findActiveByShortKey(shortKey: String, now: LocalDateTime): ShortUrl?
}