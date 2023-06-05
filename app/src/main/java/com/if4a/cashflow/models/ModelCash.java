package com.if4a.cashflow.models;

import java.util.Date;

public class ModelCash {

    public String id, nama, tipe_transaksi, keterangan, tanggal_transaksi;
    public int jumlah;
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTipe_transaksi() {
        return tipe_transaksi;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
