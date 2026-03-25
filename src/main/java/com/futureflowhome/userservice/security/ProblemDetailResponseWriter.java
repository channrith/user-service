package com.futureflowhome.userservice.security;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ProblemDetailResponseWriter {

    private final JacksonJsonHttpMessageConverter messageConverter;

    public ProblemDetailResponseWriter(JsonMapper jsonMapper) {
        this.messageConverter = new JacksonJsonHttpMessageConverter(jsonMapper);
    }

    public void write(HttpServletResponse response, ProblemDetail detail) throws IOException {
        response.setStatus(detail.getStatus());
        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        messageConverter.write(detail, MediaType.APPLICATION_PROBLEM_JSON, outputMessage);
    }
}
