package com.gmail.pashasimonpure.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.app.repository.DocumentRepository;
import com.gmail.pashasimonpure.app.repository.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepositoryImpl extends GeneralRepositoryImpl<Document> implements DocumentRepository {

    @Override
    public Document add(Connection connection, Document document) throws SQLException {

        String sql = "INSERT INTO document (unique_number, name, description) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, document.getUniqueNumber());
            statement.setString(2, document.getName());
            statement.setString(3, document.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    document.setId(id);
                    return document;
                } else {
                    throw new SQLException("Creating document failed, no ID obtained.");
                }
            }
        }

    }

    @Override
    public Document get(Connection connection, Long id) throws SQLException {

        String sql = "SELECT id, unique_number, name, description FROM document WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {

                Document document = null;
                if (rs.next()) {
                    document = new Document();
                    document.setId(rs.getLong("id"));
                    document.setUniqueNumber(rs.getString("unique_number"));
                    document.setName(rs.getString("name"));
                    document.setDescription(rs.getString("description"));

                }
                return document;
            }
        }

    }

    @Override
    public List<Document> findAll(Connection connection) throws SQLException {

        String sql = "SELECT id, unique_number, name, description FROM document";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Document> documents = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Document document = new Document();

                    document.setId(rs.getLong("id"));
                    document.setUniqueNumber(rs.getString("unique_number"));
                    document.setName(rs.getString("name"));
                    document.setDescription(rs.getString("description"));

                    documents.add(document);
                }
                return documents;
            }
        }

    }

    @Override
    public void deleteById(Connection connection, Long id) throws SQLException {

        String sql = "DELETE FROM document WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting document failed, no rows affected.");
            }
        }
    }

}