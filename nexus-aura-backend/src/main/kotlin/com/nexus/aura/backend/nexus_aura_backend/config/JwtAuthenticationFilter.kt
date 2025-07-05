package com.nexus.aura.backend.nexus_aura_backend.config

import com.nexus.aura.backend.nexus_aura_backend.service.CustomUserDetailsService
import com.nexus.aura.backend.nexus_aura_backend.service.JwtBlacklistService
import com.nexus.aura.backend.nexus_aura_backend.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.slf4j.LoggerFactory

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtBlacklistService: JwtBlacklistService
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        logger.debug("Checking if should filter path: $path")
        return path.startsWith("/api/auth/")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("JwtAuthenticationFilter invoked for path: ${request.servletPath}")
        val authHeader = request.getHeader("Authorization")
        log.debug("Authorization header: $authHeader")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.removePrefix("Bearer ").trim()
            log.debug("Extracted JWT: $jwt")
            val isValid = jwtUtil.validateToken(jwt)
            val isBlacklisted = jwtBlacklistService.isTokenBlacklisted(jwt)
            log.debug("JWT valid: $isValid, JWT blacklisted: $isBlacklisted")
            if (isValid && !isBlacklisted) {
                val username = jwtUtil.getUsernameFromToken(jwt)
                log.debug("JWT valid. Username from token: $username")
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
                log.info("Authentication set in SecurityContext for user: $username")
            } else {
                if (!isValid) log.warn("JWT is invalid. Rejecting request.")
                if (isBlacklisted) log.warn("JWT is blacklisted. Rejecting request.")
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }
        } else {
            log.warn("No valid Authorization header found. Rejecting request.")
        }
        filterChain.doFilter(request, response)
    }
}