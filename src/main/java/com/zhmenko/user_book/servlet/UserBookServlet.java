package com.zhmenko.user_book.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.exception.NotProvidedException;
import com.zhmenko.user_book.model.UserBookRequest;
import com.zhmenko.user_book.service.UserBookService;
import com.zhmenko.util.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * UserBookServlet is a servlet that handles HTTP requests related to adding and removing books from a user.
 * It uses the UserBookService to perform these operations.
 */
@Singleton
@WebServlet(value = "/usersbooks", name = "UserBookServlet")
public class UserBookServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(UserBookServlet.class);

    private final UserBookService userBookService;

    @Inject
    public UserBookServlet(final UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    /**
     * Handles HTTP POST requests by adding books to a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        UserBookRequest userBookRequest = ServletUtils.readRequestBody(request, UserBookRequest.class);
        userBookService.addBooksToUser(userBookRequest);
        log.info("to user with id {} added books - {}", userBookRequest.getUserId(), userBookRequest.getBooksId());
        response.setStatus(HttpServletResponse.SC_CREATED);
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
     * Handles HTTP DELETE requests by removing books from a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {
        UserBookRequest userBookRequest = ServletUtils.readRequestBody(request, UserBookRequest.class);
        userBookService.deleteBookFromUser(userBookRequest);
        log.info("from user with id {} deleted books - {}", userBookRequest.getUserId(), userBookRequest.getBooksId());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
