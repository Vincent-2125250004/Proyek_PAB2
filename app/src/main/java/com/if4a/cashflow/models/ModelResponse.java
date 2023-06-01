package com.if4a.cashflow.models;

import java.util.List;

public class ModelResponse {

    private String kode,pesan;
    private List<ModelCash> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelCash> getData() {
        return data;
    }
}
