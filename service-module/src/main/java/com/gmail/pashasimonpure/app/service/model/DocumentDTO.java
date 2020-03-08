package com.gmail.pashasimonpure.app.service.model;

import javax.validation.constraints.Size;

public class DocumentDTO {

    private Long id;

    private String uniqueNumber;

    @Size(min = 3, max = 32)
    private String name;

    @Size(min = 5, max = 100)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "id=" + id +
                ", uniqueNumber='" + uniqueNumber + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}