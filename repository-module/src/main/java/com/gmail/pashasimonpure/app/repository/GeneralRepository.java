package com.gmail.pashasimonpure.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

    T get(Connection connection, Long id) throws SQLException;

    List<T> findAll(Connection connection) throws SQLException;

    void deleteById(Connection connection, Long id) throws SQLException;

}