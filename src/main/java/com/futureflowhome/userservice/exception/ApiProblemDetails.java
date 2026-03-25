package com.futureflowhome.userservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

/**
 * Builds {@link ProblemDetail} bodies with a stable JSON shape: RFC 7807 fields plus {@value #PROPERTY_CODE}.
 */
public final class ApiProblemDetails {

    public static final String PROPERTY_CODE = "code";

    private ApiProblemDetails() {
    }

    public static ProblemDetail of(
            HttpStatus status,
            String title,
            String detail,
            String code,
            HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        if (request != null) {
            pd.setInstance(URI.create(request.getRequestURI()));
        }
        if (code != null) {
            pd.setProperty(PROPERTY_CODE, code);
        }
        return pd;
    }
}
