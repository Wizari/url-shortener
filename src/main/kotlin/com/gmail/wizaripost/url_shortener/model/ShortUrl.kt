package com.gmail.wizaripost.url_shortener.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "short_urls",
    indexes = [Index(name = "idx_short_key", columnList = "short_key", unique = true)]
)
data class ShortUrl(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "original_url", nullable = false, length = 2048)
    val originalUrl: String,

    @Column(name = "short_key", nullable = false, unique = true, length = 10)
    val shortKey: String,

    @Column(name = "created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "click_count", nullable = false)
    var clickCount: Long = 0,

    @Column(name = "expiry_date")
    val expiryDate: LocalDateTime? = null
)