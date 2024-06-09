package com.zhmenko.connection;

import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.data.dao.AuthorDaoImpl;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.dao.BookDaoImpl;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.data.dao.UserDaoImpl;
import com.zhmenko.user_book.data.dao.UserBookDao;
import com.zhmenko.user_book.data.dao.UserBookDaoImpl;

public class DependencyInjectionConfigTest extends ServletModule {
    private String jdbcUrl;
    private String username;
    private String password;

    public DependencyInjectionConfigTest(final String jdbcUrl, final String username, final String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void configureServlets() {
        bind(UserDao.class).to(UserDaoImpl.class);

        bind(UserBookDao.class).to(UserBookDaoImpl.class);

        bind(BookDao.class).to(BookDaoImpl.class);

        bind(AuthorDao.class).to(AuthorDaoImpl.class);
    }

    @Provides
    ConnectionManager connectionManager() {
        return new ConnectionManagerTest(jdbcUrl, username, password);
    }
}
