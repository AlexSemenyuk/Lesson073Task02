package org.itstep.dao;

import java.util.List;


public interface GenericDao<P, U, I, S> {
    List<P> findAllProducts();

    List<U> findAllUsers();

    U findUserByLoginAndPassword(S login, S password);

    void saveUserToDB(S login, S password);

}


