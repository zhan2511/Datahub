package org.example.datahub.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return CURRENT_USER.get();
    }

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    // Note: we don't filter requests here because we do it in each controller method using @PreAuthorize annotation
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
        throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long userId = jwtUtil.getCurrentUserId(token);
            CURRENT_USER.set(userId);
        }

        chain.doFilter(request, response);

        CURRENT_USER.remove();
    }
}
