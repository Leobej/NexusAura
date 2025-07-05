package com.nexus.aura.backend.nexus_aura_backend.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.secret}") secretBase64: String,
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long
) {
    private val jwtSecret: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretBase64))

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(jwtSecret)
            .compact()
    }

    fun getExpirationDate(token: String): Date {
        val claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token)
        return claims.body.expiration
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token)
            true
        } catch (e: io.jsonwebtoken.ExpiredJwtException) {
            // Optionally log: Token expired
            false
        } catch (e: io.jsonwebtoken.security.SecurityException) {
            // Optionally log: Invalid signature
            false
        } catch (e: io.jsonwebtoken.MalformedJwtException) {
            // Optionally log: Malformed token
            false
        } catch (e: io.jsonwebtoken.UnsupportedJwtException) {
            // Optionally log: Unsupported token
            false
        } catch (e: IllegalArgumentException) {
            // Optionally log: Illegal argument
            false
        } catch (e: Exception) {
            // Optionally log: Unknown error
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token)
        return claims.body.subject
    }
}
