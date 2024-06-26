package com.zhmenko.user_book.servlet;

import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user_book.model.UserBookRequest;
import com.zhmenko.user_book.service.UserBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBookServletTest {
    private final String INPUT_JSON = """
            {
               "user_id": 1,
               "books_id": [
                  1,
                  2
               ]
            }
            """;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private UserBookServlet servlet;
    @Mock
    private UserBookService service;


    @Test
    void testPostRequest() throws Exception {
        mockRequestReader(INPUT_JSON);
        doNothing().when(service).addBooksToUser(any(UserBookRequest.class));
        servlet.doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testNotExistUserPostRequest() throws IOException {
        mockRequestReader(INPUT_JSON);
        doThrow(new UserNotFoundException(1L)).when(service).addBooksToUser(any(UserBookRequest.class));

        assertThrows(UserNotFoundException.class, () -> servlet.doPost(request, response));
    }

    @Test
    void testNotExistBookPostRequest() throws IOException {
        mockRequestReader(INPUT_JSON);
        doThrow(new BookNotFoundException(1L)).when(service).addBooksToUser(any(UserBookRequest.class));

        assertThrows(BookNotFoundException.class, () -> servlet.doPost(request, response));
    }

    @Test
    void testDeleteRequest() throws Exception {
        mockRequestReader(INPUT_JSON);
        doNothing().when(service).deleteBookFromUser(any(UserBookRequest.class));
        servlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testNotExistUserDeleteRequest() throws IOException {
        mockRequestReader(INPUT_JSON);
        doThrow(new UserNotFoundException(1L)).when(service).deleteBookFromUser(any(UserBookRequest.class));

        assertThrows(UserNotFoundException.class, () -> servlet.doDelete(request, response));
    }

    @Test
    void testNotExistBookDeleteRequest() throws IOException {
        mockRequestReader(INPUT_JSON);
        doThrow(new BookNotFoundException(1L)).when(service).deleteBookFromUser(any(UserBookRequest.class));

        assertThrows(BookNotFoundException.class, () -> servlet.doDelete(request, response));
    }

    ////////
    private void mockRequestReader(final String requestJsonStr) throws IOException {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(requestJsonStr.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(reader);
    }
}