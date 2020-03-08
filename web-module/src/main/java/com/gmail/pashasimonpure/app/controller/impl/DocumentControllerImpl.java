package com.gmail.pashasimonpure.app.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.validation.Valid;

import com.gmail.pashasimonpure.app.controller.DocumentController;
import com.gmail.pashasimonpure.app.service.DocumentService;
import com.gmail.pashasimonpure.app.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentControllerImpl implements DocumentController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final DocumentService documentService;

    public DocumentControllerImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String getDocuments(Model model) {
        List<DocumentDTO> documentsDTO = documentService.findAll();
        model.addAttribute("documents", documentsDTO);
        return "documents";
    }

    @GetMapping("/create")
    public String newDocument(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "new_document";
    }

    @PostMapping("/create")
    public String addDocument(Model model, @Valid @ModelAttribute(name = "document") DocumentDTO document, BindingResult bindingResult) {
        logger.info("/create POST: " + document.toString());

        if (bindingResult.hasErrors()) {
            return "new_document";
        }

        documentService.add(document);
        return "redirect:/";
    }

    @GetMapping("/document")
    public String getDocument(@RequestParam(name = "id") Long id, Model model) {
        DocumentDTO documentDTO = documentService.get(id);
        model.addAttribute("document", documentDTO);
        return "view_document";
    }

    @PostMapping("/delete")
    public String deleteDocument(@RequestParam(name = "id") Long id) {
        documentService.deleteById(id);
        return "redirect:/";
    }

}