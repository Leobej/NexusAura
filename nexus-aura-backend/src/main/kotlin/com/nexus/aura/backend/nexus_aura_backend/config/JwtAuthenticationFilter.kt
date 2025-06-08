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

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtBlacklistService: JwtBlacklistService   // <-- Add this
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/api/auth/")
    }


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.removePrefix("Bearer ").trim()
            if (jwtUtil.validateToken(jwt) && !jwtBlacklistService.isTokenBlacklisted(jwt)) {
                val username = jwtUtil.getUsernameFromToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(username)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                // Optional: You could explicitly reject here
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }
        }
        filterChain.doFilter(request, response)
    }
}