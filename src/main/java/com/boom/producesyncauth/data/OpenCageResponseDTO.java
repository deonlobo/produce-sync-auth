package com.boom.producesyncauth.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class OpenCageResponseDTO {
    private List<OpenCageResults> results;
    private String documentation;

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public List<OpenCageResults> getResults() {
        return results;
    }

    public void setResults(List<OpenCageResults> results) {
        this.results = results;
    }
}
