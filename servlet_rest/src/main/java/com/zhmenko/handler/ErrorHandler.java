package com.zhmenko.handler;

import com.zhmenko.exception.EntityNotFoundException;
import com.zhmenko.exception.NotProvidedException;
import com.zhmenko.handler.model.Error;
import com.zhmenko.util.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


/**
 * Servlet that handles all exceptions and wraps them to response.
 */
@WebServlet(value = "/error", displayName = "ErrorHandlerServlet")
public class ErrorHandler extends HttpServlet {

    /**
     * Handles HTTP GET requests.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    /**
     * Handles HTTP POST requests.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    /**
     * Handles HTTP PUT requests.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    /**
     * Handles HTTP DELETE requests.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    /**
     * Handles the request and wraps the exception to a response.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String servletName = Optional.of((String) request.getAttribute("javax.servlet.error.servlet_name"))
                .orElse("Unknown");
        String requestUri = Optional.of((String) request.getAttribute("javax.servlet.error.request_uri"))
                .orElse("Unknown");
        Error responseBody = switch (throwable) {
            case EntityNotFoundException entityNotFoundException -> new Error(
                    HttpServletResponse.SC_NOT_FOUND,
                    servletName,
                    requestUri,
                    entityNotFoundException.getMessage());
            case NotProvidedException notProvidedException -> new Error(
                    HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                    servletName,
                    requestUri,
                    notProvidedException.getMessage());
            case RuntimeException runtimeException -> new Error(
                    HttpServletResponse.SC_BAD_REQUEST,
                    servletName,
                    requestUri,
                    runtimeException.getMessage());
            default -> new Error(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    servletName,
                    requestUri,
                    throwable.getMessage());
        };

        ServletUtils.writeJsonResponse(response, responseBody, responseBody.getStatusCode());
    }
}