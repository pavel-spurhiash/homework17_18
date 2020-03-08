package com.gmail.pashasimonpure.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.gmail.pashasimonpure.app.repository.ConnectionRepository;
import com.gmail.pashasimonpure.app.repository.DocumentRepository;
import com.gmail.pashasimonpure.app.repository.model.Document;
import com.gmail.pashasimonpure.app.service.DocumentService;
import com.gmail.pashasimonpure.app.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ConnectionRepository connectionRepository;
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(
            ConnectionRepository connectionRepository,
            DocumentRepository documentRepository) {
        this.connectionRepository = connectionRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public List<DocumentDTO> findAll() {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                List<Document> documents = documentRepository.findAll(connection);
                List<DocumentDTO> documentsDTO = new ArrayList<>();

                for (Document document : documents) {
                    DocumentDTO documentDTO = convert(document);
                    documentsDTO.add(documentDTO);
                }

                connection.commit();
                return documentsDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();

    }

    @Override
    public DocumentDTO get(Long id) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {

                Document document = documentRepository.get(connection, id);
                DocumentDTO documentDTO = convert(document);

                connection.commit();
                return documentDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    @Override
    public void add(DocumentDTO documentDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = new Document();
                //generate unique number:
                document.setUniqueNumber(UUID.randomUUID().toString());
                document.setName(documentDTO.getName());
                document.setDescription(documentDTO.getDescription());
                documentRepository.add(connection, document);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                documentRepository.deleteById(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private DocumentDTO convert(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();

        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        documentDTO.setName(document.getName());
        documentDTO.setDescription(document.getDescription());

        return documentDTO;
    }

}