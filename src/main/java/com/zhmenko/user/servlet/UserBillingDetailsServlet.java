package com.zhmenko.user.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zhmenko.exception.NotProvidedException;
import com.zhmenko.user.model.request.BillingDetailsInsertRequest;
import com.zhmenko.user.model.request.BillingDetailsModifyRequest;
import com.zhmenko.user.model.response.BillingDetailsResponse;
import com.zhmenko.user.service.UserService;
import com.zhmenko.util.ServletUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
@WebServlet(value = "/users/billings", name = "UserBooksServlet")
public class UserBillingDetailsServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(UserBillingDetailsServlet.class);

    private final UserService userService;

    @Inject
    public UserBillingDetailsServlet(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles HTTP POST requests by adding billing to a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        BillingDetailsInsertRequest billingDetailsInsertRequest =
                ServletUtils.readRequestBody(request, BillingDetailsInsertRequest.class);
        final BillingDetailsResponse billingDetailsResponse = userService.addBillingDetails(billingDetailsInsertRequest);
        log.info("to user with id {} added billing with id {}", billingDetailsInsertRequest.getUserId(),
                billingDetailsResponse.getId());
        ServletUtils.writeJsonResponse(response, billingDetailsResponse, HttpServletResponse.SC_CREATED);
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
     * Handles HTTP DELETE requests by removing billing from a user.
     *
     * @param request  the HTTP request containing the UserBookRequest object.
     * @param response the HTTP response to be sent back to the client.
     */
    @Override
    public void doDelete(final HttpServletRequest request, final HttpServletResponse response) {
        BillingDetailsModifyRequest billingDetailsModifyRequest =
                ServletUtils.readRequestBody(request, BillingDetailsModifyRequest.class);
        final BillingDetailsResponse billingDetailsResponse = userService.removeBillingDetails(billingDetailsModifyRequest);
        log.info("from user with id {} removed billing with id {}", billingDetailsResponse.getUserId(),
                billingDetailsResponse.getId());
        ServletUtils.writeJsonResponse(response, billingDetailsResponse, HttpServletResponse.SC_OK);
    }
}
