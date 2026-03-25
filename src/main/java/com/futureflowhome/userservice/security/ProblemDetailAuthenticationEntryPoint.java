package com.futureflowhome.userservice.security;

import com.futureflowhome.userservice.exception.ApiProblemDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class ProblemDetailAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ProblemDetailResponseWriter writer;

    public ProblemDetailAuthenticationEntryPoint(ProblemDetailResponseWriter writer) {
        this.writer = writer;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        String detail = (authException != null && StringUtils.hasText(authException.getMessage()))
                ? authException.getMessage()
                : "Credentials are missing or invalid";
        ProblemDetail pd = ApiProblemDetails.of(HttpStatus.UNAUTHORIZED, "Unauthorized", detail, "UNAUTHORIZED", request);
        writer.write(response, pd);
    }
}
