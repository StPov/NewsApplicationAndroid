package com.example.newsapplication.Data.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedSources {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("sources")
    @Expose
    private List<SearchedSource> sourceList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SearchedSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SearchedSource> sourceList) {
        this.sourceList = sourceList;
    }
}
