package com.pixel.demo.config.Jwt;

import com.pixel.demo.model.User;
import com.pixel.demo.security.JWTUtils;
import com.pixel.demo.service.CustomUserDetailsService;
import com.pixel.demo.service.RedisBlacklistService;
import com.pixel.demo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final RedisBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userIdStr;
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        if (blacklistService.isBlacklisted(jwtToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token in blacklist");
            return;
        }
        userIdStr = jwtUtils.extractUserId(jwtToken);
        if (userIdStr != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Long userId = Long.parseLong(userIdStr);
            Optional<User> userOpt = userService.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmailData().get(0).getEmail());
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
