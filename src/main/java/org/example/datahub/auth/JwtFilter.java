package org.example.datahub.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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
            // Note: we set a default authority ROLE_AUTHENTICATED to indicate that the user is authenticated
            List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_AUTHENTICATED")
            );
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userId, null, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CURRENT_USER.set(userId);
        }

        chain.doFilter(request, response);

        CURRENT_USER.remove();
    }
}
