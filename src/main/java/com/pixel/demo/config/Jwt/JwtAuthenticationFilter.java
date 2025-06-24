package com.pixel.demo.config.Jwt;

import com.pixel.demo.model.User;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.security.JWTUtils;
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

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final RedisBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        if (blacklistService.isBlacklisted(jwtToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token in blacklist");
            return;
        }

        final String userIdStr = jwtUtils.extractUserId(jwtToken);
        if (userIdStr == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final Long userId = Long.parseLong(userIdStr);
            User user = userService.findUserById(userId);
            UserDetails userDetails = new CustomUserDetails(user, user.getEmailDataList().get(0).getEmail());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user ID format");
            return;
        } catch (IndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User has no email");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
