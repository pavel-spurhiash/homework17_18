package com.gmail.pashasimonpure.app.service;

import java.util.List;

import com.gmail.pashasimonpure.app.service.model.DocumentDTO;

public interface DocumentService {

    void add(DocumentDTO documentDTO);

    DocumentDTO get(Long id);

    List<DocumentDTO> findAll();

    void deleteById(Long id);

}