package com.if4a.cashflow.api;

import com.if4a.cashflow.models.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {

    @GET("retrievecash.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("createcash.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("tipe_transaksi") String tipe_transaksi,
            @Field("jumlah") String jumlah,
            @Field("tanggal_transaksi") String tanggal_transaksi,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("updatecash.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("tipe_transaksi") String tipe_transaksi,
            @Field("jumlah") String jumlah,
            @Field("tanggal_transaksi") String tanggal_transaksi,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("deletecash.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );

}
