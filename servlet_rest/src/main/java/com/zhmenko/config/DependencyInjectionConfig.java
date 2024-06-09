package com.zhmenko.config;

import com.google.inject.servlet.ServletModule;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.data.dao.AuthorDaoImpl;
import com.zhmenko.author.mapper.AuthorCollectionMapper;
import com.zhmenko.author.mapper.AuthorCollectionMapperImpl;
import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.author.mapper.AuthorMapperImpl;
import com.zhmenko.author.service.AuthorService;
import com.zhmenko.author.service.AuthorServiceImpl;
import com.zhmenko.author.servlet.AuthorServlet;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.dao.BookDaoImpl;
import com.zhmenko.book.mapper.BookCollectionMapper;
import com.zhmenko.book.mapper.BookCollectionMapperImpl;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.book.mapper.BookMapperImpl;
import com.zhmenko.book.service.BookService;
import com.zhmenko.book.service.BookServiceImpl;
import com.zhmenko.book.servlet.BookServlet;
import com.zhmenko.connection.ConnectionManager;
import com.zhmenko.connection.ConnectionManagerImpl;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.data.dao.UserDaoImpl;
import com.zhmenko.user.mapper.UserCollectionMapper;
import com.zhmenko.user.mapper.UserCollectionMapperImpl;
import com.zhmenko.user.mapper.UserMapper;
import com.zhmenko.user.mapper.UserMapperImpl;
import com.zhmenko.user.service.UserService;
import com.zhmenko.user.service.UserServiceImpl;
import com.zhmenko.user.servlet.UserServlet;
import com.zhmenko.user_book.data.dao.UserBookDao;
import com.zhmenko.user_book.data.dao.UserBookDaoImpl;
import com.zhmenko.user_book.service.UserBookService;
import com.zhmenko.user_book.service.UserBookServiceImpl;
import com.zhmenko.user_book.servlet.UserBookServlet;

public class DependencyInjectionConfig extends ServletModule {

    /**
     * Configures dependency injection
     */
    @Override
    protected void configureServlets() {
        serve("/users/*", "/users").with(UserServlet.class);
        serve("/books/*", "/books").with(BookServlet.class);
        serve("/authors/*", "/authors").with(AuthorServlet.class);
        serve("/usersbooks/*", "/usersbooks").with(UserBookServlet.class);
        // DAO binding
        bind(UserDao.class).to(UserDaoImpl.class);

        bind(UserBookDao.class).to(UserBookDaoImpl.class);

        bind(BookDao.class).to(BookDaoImpl.class);

        bind(AuthorDao.class).to(AuthorDaoImpl.class);

        bind(ConnectionManager.class).to(ConnectionManagerImpl.class);

        //Service binding
        bind(UserService.class).to(UserServiceImpl.class);

        bind(BookService.class).to(BookServiceImpl.class);

        bind(AuthorService.class).to(AuthorServiceImpl.class);

        bind(UserBookService.class).to(UserBookServiceImpl.class);

        // Mappers bindings
        bind(UserMapper.class).to(UserMapperImpl.class);

        bind(UserCollectionMapper.class).to(UserCollectionMapperImpl.class);

        bind(BookMapper.class).to(BookMapperImpl.class);

        bind(BookCollectionMapper.class).to(BookCollectionMapperImpl.class);

        bind(AuthorMapper.class).to(AuthorMapperImpl.class);

        bind(AuthorCollectionMapper.class).to(AuthorCollectionMapperImpl.class);

    }
}