package com.zhmenko.user.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.exception.NotProvidedException;
import com.zhmenko.user.model.request.UserBookModifyRequest;
import com.zhmenko.user.model.response.UserResponse;
import com.zhmenko.user.service.UserService;
import com.zhmenko.util.ServletUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * UserBookServlet is a servlet that handles HTTP requests related to adding and removing books from a user.
 */
@Singleton
@WebServlet(value = "/users/books", name = "UserBooksServlet")
public class UserBookServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(UserBookServlet.class);

    private final UserService userService;

    @Inject
    public UserBookServlet(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles HTTP POST requests by adding book to a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        UserBookModifyRequest userBookModifyRequest = ServletUtils.readRequestBody(request, UserBookModifyRequest.class);
        final UserResponse userResponse = userService.addBook(userBookModifyRequest);
        log.info("to user with id {} added book with id {}", userBookModifyRequest.getUserId(), userBookModifyRequest.getBookId());
        ServletUtils.writeJsonResponse(response, userResponse, HttpServletResponse.SC_CREATED);
    }

    /**
     * Handles HTTP GET requests. This method is not implemented as it is not provided.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        throw new NotProvidedException("GET NOT PROVIDED! USE POST or DELETE");
    }

    /**
     * Handles HTTP PUT requests. This method is not implemented as it is not provided.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {
        throw new NotProvidedException("PUT NOT PROVIDED! USE POST or DELETE");
    }

    /**
     * Handles HTTP DELETE requests by removing book from a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {
        UserBookModifyRequest userBookModifyRequest = ServletUtils.readRequestBody(request, UserBookModifyRequest.class);
        final UserResponse userResponse = userService.removeBook(userBookModifyRequest);
        log.info("from user with id {} removed book with id {}",
                userBookModifyRequest.getUserId(),
                userBookModifyRequest.getBookId());
        ServletUtils.writeJsonResponse(response, userResponse, HttpServletResponse.SC_OK);
    }
}
