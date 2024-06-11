package com.zhmenko.book.servlet;

import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.book.service.BookService;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.PathVariableException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServletTest {
    private final String SERVLET_PATH = "/books";
    private final String REQUEST_URI_WITH_PATH_VARIABLE = "servlet_rest_war/books/1";
    private final String REQUEST_URI_WITHOUT_PATH_VARIABLE = "servlet_rest_war/books";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private BookServlet servlet;
    @Mock
    private BookService service;
    private StringWriter jsonResponse;

    @Test
    void testGetRequest() throws IOException, JSONException {
        mockRequestPath(true);
        mockResponseWriter();
        String expectedJson = """
                {
                   "id": 1,
                   "name": "book_name",
                   "author_name": "author",
                   "users_name": [
                      "user_1",
                      "user_2"
                   ]
                }
                """;
        final BookResponse bookResponse = new BookResponse(1,
                "book_name",
                "author",
                List.of("user_1", "user_2"));
        when(service.getBookById(1)).thenReturn(bookResponse);
        servlet.doGet(request, response);

        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(response).setCharacterEncoding("UTF-8");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        JSONAssert.assertEquals(expectedJson, jsonResponse.toString(), JSONCompareMode.LENIENT);
    }

    @Test
    void testNotExistBookGetRequest() throws Exception {
        mockRequestPath(true);

        given(service.getBookById(1)).willThrow((new BookNotFoundException(1)));

        assertThrows(BookNotFoundException.class, () -> servlet.doGet(request, response));
    }

    @Test
    void testBlankPathVariableBookGetRequest() throws Exception {
        mockRequestPath(false);
        assertThrows(PathVariableException.class, () -> servlet.doGet(request, response));
    }

    @Test
    void testPutRequest() throws Exception {
        mockRequestPath(true);
        jsonResponse = new StringWriter();
        final String inputJson = """
                {
                    "id": 1,
                    "name": "book_name",
                    "author_id": 1
                }
                """;
        mockRequestReader(inputJson);
        doNothing().when(service).updateBook(any(BookModifyRequest.class), eq(1));
        servlet.doPut(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testNotExistBookPutRequest() throws Exception {
        mockRequestPath(true);
        final String inputJson = """
                {
                    "id": 1,
                    "name": "book_name",
                    "author_id": 1
                }
                """;
        mockRequestReader(inputJson);
        doThrow(new BookNotFoundException(1)).when(service).updateBook(any(BookModifyRequest.class), eq(1));

        assertThrows(BookNotFoundException.class, () -> servlet.doPut(request, response));
    }

    @Test
    void testBlankPathVariableBookPutRequest() {
        mockRequestPath(false);
        assertThrows(PathVariableException.class, () -> servlet.doPut(request, response));
    }

    @Test
    void testPostRequest() throws Exception {
        jsonResponse = new StringWriter();
        final String inputJson = """
                {
                    "name": "book_name",
                    "author_id": 1
                }
                """;
        mockRequestReader(inputJson);
        doNothing().when(service).addBook(any(BookInsertRequest.class));
        servlet.doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDeleteRequest() throws Exception {
        mockRequestPath(true);
        doNothing().when(service).deleteBookById(1);

        servlet.doDelete(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDeleteNotExistBookRequest() throws Exception {
        mockRequestPath(true);
        doThrow(new BookNotFoundException(1)).when(service).deleteBookById(eq(1));

        assertThrows(BookNotFoundException.class, () -> servlet.doDelete(request, response));
    }

    @Test
    void testBlankPathVariableBookDeleteRequest() {
        mockRequestPath(false);
        assertThrows(PathVariableException.class, () -> servlet.doDelete(request, response));
    }

    //////////
    private void mockRequestReader(final String requestJsonStr) throws IOException {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(requestJsonStr.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(reader);
    }


    private void mockResponseWriter() throws IOException {
        jsonResponse = new StringWriter();
        PrintWriter pw = new PrintWriter(jsonResponse);
        when(response.getWriter()).thenReturn(pw);
    }

    private void mockRequestPath(boolean isPathVariableExist) {
        when(request.getServletPath()).thenReturn(SERVLET_PATH);
        if (isPathVariableExist) when(request.getRequestURI()).thenReturn(REQUEST_URI_WITH_PATH_VARIABLE);
        else when(request.getRequestURI()).thenReturn(REQUEST_URI_WITHOUT_PATH_VARIABLE);
    }
}