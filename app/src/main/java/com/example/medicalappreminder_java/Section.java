package com.example.medicalappreminder_java;

import java.util.List;

public class Section {
    private String sectionName;
    private List<Medicine> sectionItems;

    public Section(String sectionName, List<Medicine> sectionItems) {
        this.sectionName = sectionName;
        this.sectionItems = sectionItems;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Medicine> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<Medicine> sectionItems) {
        this.sectionItems = sectionItems;
    }
}
