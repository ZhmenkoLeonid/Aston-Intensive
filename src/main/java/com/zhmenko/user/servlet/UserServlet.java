package com.zhmenko.user.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.exception.PathVariableException;
import com.zhmenko.user.model.request.UserInsertRequest;
import com.zhmenko.user.model.request.UserModifyRequest;
import com.zhmenko.user.model.response.UserResponse;
import com.zhmenko.user.service.UserService;
import com.zhmenko.util.ServletUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * Servlet that serve requests linked to users
 */

@Singleton
@WebServlet(value = "/users", name = "UserServlet")
public class UserServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(UserServlet.class);
    private final UserService userService;

    @Inject
    public UserServlet(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        UserInsertRequest userInsertRequest = ServletUtils.readRequestBody(request, UserInsertRequest.class);
        final UserResponse userResponse = userService.addUser(userInsertRequest);
        log.info("user added: {}", userInsertRequest);
        ServletUtils.writeJsonResponse(response, userResponse, HttpServletResponse.SC_CREATED);
    }

    /**
     * get user by specified id (path variable) if exist
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
            Long userId = Long.parseLong(pathVariable);
            UserResponse user = userService.getUserById(userId);
            ServletUtils.writeJsonResponse(response, user, HttpServletResponse.SC_OK);
        } else {
            final List<UserResponse> usersResponse = userService.getAll();
            ServletUtils.writeJsonResponse(response, usersResponse, HttpServletResponse.SC_OK);
        }
    }


    /**
     * Updates a user by id if user exist
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     */
    @Override
    public void doPut(final HttpServletRequest request, final HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            Long userId = Long.parseLong(pathVariable);
            UserModifyRequest userModifyRequest = ServletUtils.readRequestBody(request, UserModifyRequest.class);
            final UserResponse userResponse = userService.updateUser(userModifyRequest, userId);
            ServletUtils.writeJsonResponse(response, userResponse, HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify user id for update");
    }

    /**
     * Deletes a user by id.
     *
     * @param request  the {@link HttpServletRequest} object that
     *                 contains the request the client made of
     *                 the servlet
     * @param response the {@link HttpServletResponse} object that
     *                 contains the response the servlet returns
     *                 to the client
     * @throws NumberFormatException if path variable id can't be parsed to Integer
     * @throws PathVariableException if no user id is specified in the path variable
     */
    @Override
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {
        String pathVariable = ServletUtils.getPathVariable(request);
        if (!pathVariable.isEmpty()) {
            Long userId = Long.parseLong(pathVariable);
            final UserResponse userResponse = userService.deleteUserById(userId);
            ServletUtils.writeJsonResponse(response, userResponse, HttpServletResponse.SC_OK);
        } else throw new PathVariableException("Need to specify user id for delete");
    }
}
