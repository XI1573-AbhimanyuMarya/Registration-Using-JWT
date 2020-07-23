package com.demo.registration.security.jwt;

import com.demo.registration.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        log.info("Authorization Header:  " + authorizationHeader);
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.myUserDetailService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                log.info("usernamePasswordAuthenticationToken  " + usernamePasswordAuthenticationToken);

                log.info("get Credentials:  " + usernamePasswordAuthenticationToken.getCredentials());
                log.info("get Principal:  " + usernamePasswordAuthenticationToken.getPrincipal());
                log.info("get Authentication:  " + usernamePasswordAuthenticationToken.isAuthenticated());

                usernamePasswordAuthenticationToken.setDetails
                        (new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);


    }
}
