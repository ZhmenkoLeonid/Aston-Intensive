package com.zhmenko.book.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.book.service.BookService;
import com.zhmenko.exception.PathVariableException;
import com.zhmenko.util.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that serve requests linked to books
 */

@Singleton
@WebServlet(value = "/books", name = "BookServlet")
public class BookServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(BookServlet.class);
    private final BookService bookService;

    @Inject
    public BookServlet(final BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Handles HTTP POST requests to add a new book.
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        BookInsertRequest bookInsertRequest = ServletUtils.readRequestBody(request, BookInsertRequest.class);
        bookService.addBook(bookInsertRequest);
        log.info("book added: %s", bookInsertRequest);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * get book by specified id (path variable) if exist
     *
     * @param request  an {@link HttpServletRequest} object that
     *                 contains the request the client has made
     *                 of the servlet
     * @param response an {@link HttpServletResponse} object that
     *                 contains the response the servlet sends
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if there is no path variable in request
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            int bookId = Integer.parseInt(pathVariable);
            BookResponse book = bookService.getBookById(bookId);
            ServletUtils.writeJsonResponse(response, book, HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify book id to get");
    }


    /**
     * Updates a book by id if book exist
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if no book id is specified in the path variable
     */
    @Override
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            int bookId = Integer.parseInt(pathVariable);
            BookModifyRequest bookModifyRequest = ServletUtils.readRequestBody(request, BookModifyRequest.class);
            bookService.updateBook(bookModifyRequest, bookId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify book id for update");
    }

    /**
     * Deletes a book by id.
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if no book id is specified in the path variable
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            int bookId = Integer.parseInt(pathVariable);
            bookService.deleteBookById(bookId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify book id for delete");
    }
}
