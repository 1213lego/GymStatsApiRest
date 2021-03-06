package com.gymstatsapirest.security.jwt;
import com.gymstatsapirest.security.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthTokenFilter extends OncePerRequestFilter.
 *
 * org.springframework.web.filter.OncePerRequestFilter
 * -> Executes once per request. This is a filter base class that is used to guarantee a single execution per request dispatch. It provides a doFilterInternal method with HttpServletRequest and HttpServletResponse arguments.
 *
 * In JwtAuthTokenFilter class, the doFilterInternal method will do:
 *
 * get JWT token from header
 * validate JWT
 * parse username from validated JWT
 * load data from users table, then build an authentication object
 * set the authentication object to Security Context
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = getJwt(request);
            if (jwt!=null && jwtProvider.validateJwtToken(jwt)) {
                String username = jwtProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }
    private String getJwt(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ","");
        }

        return null;
    }
}
