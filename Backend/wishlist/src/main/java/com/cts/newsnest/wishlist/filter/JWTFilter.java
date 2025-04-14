package com.cts.newsnest.wishlist.filter;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                throw new ServletException("JWT Token is missing");
            }

            String jwtToken = authHeader.substring(7);

            try{
                JwtParser jwtParser = (JwtParser) Jwts.parser().setSigningKey("secret");
                Jwt jwtObj = jwtParser.parse(jwtToken);
                Claims claimObj = (Claims) jwtObj.getBody();
//                if(claimObj.get("role") == "user"){
                    HttpSession session = request.getSession();
                    session.setAttribute("claims", claimObj.getSubject());
                    request.setAttribute("user", "passed");
//                }
            }
            catch(MalformedJwtException ex){
                throw new ServletException("Illegel modification in token");
            }



            filterChain.doFilter(request, response);
        }
    }
}
