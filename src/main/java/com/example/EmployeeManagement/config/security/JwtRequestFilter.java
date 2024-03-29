package com.example.EmployeeManagement.config.security;

import com.example.EmployeeManagement.dto.CustomUserDetail;
import com.example.EmployeeManagement.service.impl.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailServiceImpl myUserDetailServiceImplementation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);


                username = jwtUtil.extractUsername(jwt);


                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    CustomUserDetail customUserDetail = myUserDetailServiceImplementation.loadUserByUsername(username);
                    if (jwtUtil.validateToken(jwt, customUserDetail)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customUserDetail, null, customUserDetail.getAuthorities());
                        //error here
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.setHeader("Access-Control-Allow-Origin", "*");
            return;
        }
    }

}