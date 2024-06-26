package com.zhmenko.author.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.author.service.AuthorService;
import com.zhmenko.exception.PathVariableException;
import com.zhmenko.util.ServletUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@Singleton
@WebServlet(value = "/authors", name = "AuthorServlet")
public class AuthorServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AuthorServlet.class);
    private final AuthorService authorService;

    @Inject
    public AuthorServlet(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Handles HTTP POST requests to add a new author.
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException if an error occurs while reading or writing data to/from the client
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        AuthorInsertRequest authorInsertRequest = ServletUtils.readRequestBody(request, AuthorInsertRequest.class);
        final AuthorResponse authorResponse = authorService.addAuthor(authorInsertRequest);
        log.info("author added: {}", authorInsertRequest);
        ServletUtils.writeJsonResponse(response, authorResponse, HttpServletResponse.SC_CREATED);
    }

    /**
     * get author by specified id (path variable) if exist
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
            Long authorId = Long.parseLong(pathVariable);
            AuthorResponse author = authorService.getAuthorById(authorId);
            ServletUtils.writeJsonResponse(response, author, HttpServletResponse.SC_OK);
        } else {
            final List<AuthorResponse> all = authorService.getAll();
            ServletUtils.writeJsonResponse(response, all, HttpServletResponse.SC_OK);
        }
    }


    /**
     * Updates an author by id if author exist
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if no author id is specified in the path variable
     */
    @Override
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            Long authorId = Long.parseLong(pathVariable);
            AuthorModifyRequest authorModifyRequest = ServletUtils.readRequestBody(request, AuthorModifyRequest.class);
            final AuthorResponse authorResponse = authorService.updateAuthor(authorModifyRequest, authorId);
            ServletUtils.writeJsonResponse(response, authorResponse, HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify author id for update");
    }

    /**
     * Deletes an author by id.
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if no author id is specified in the path variable
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            Long authorId = Long.parseLong(pathVariable);
            final AuthorResponse authorResponse = authorService.deleteAuthorById(authorId);
            ServletUtils.writeJsonResponse(response, authorResponse, HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify author id for delete");
    }
}
