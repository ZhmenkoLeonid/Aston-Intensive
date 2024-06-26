package com.zhmenko;


import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String str = "http://localhost:8080/servlet_rest_war/users/5";
        String str1 = "http://localhost:8080/servlet_rest_war/users/";
        String str5 = "http://localhost:8080/servlet_rest_war/users/?name=1";
        String str2 = "http://localhost:8080/servlet_rest_war/users";
        String str10 = "http://localhost:8080/servlet_rest_war/users/1";

        String str3 = "http://localhost:8080/servlet_rest_war/users/books";
        String str6 = "http://localhost:8080/servlet_rest_war/users/books/?name=1";
        String str7 = "http://localhost:8080/servlet_rest_war/users/books/";
        String str8 = "http://localhost:8080/servlet_rest_war/users/books/1";
        String str9 = "http://localhost:8080/servlet_rest_war/users/books/1?name=1";
        final String[] split = str.split("/");
/*        //SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();
        //Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AuthorEntity> critQuery = builder.createQuery(AuthorEntity.class);
            Root<AuthorEntity> root = critQuery.from(AuthorEntity.class);
            critQuery.select(root);
            final UserEntity userEntity = session.get(UserEntity.class, 1);
            final UserEntity load = session.load(UserEntity.class, 2);
            final Set<BookEntity> bookEntitySet = load.getBookEntitySet();
            Query<AuthorEntity> query = session.createQuery(critQuery);

            List<AuthorEntity> results = query.getResultList();
            System.out.println(results.toString());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }*/
    }
}
