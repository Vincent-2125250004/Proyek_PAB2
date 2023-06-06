package com.if4a.cashflow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.if4a.cashflow.R;
import com.if4a.cashflow.activity_detail.DetailActivity;
import com.if4a.cashflow.api.APIRequestData;
import com.if4a.cashflow.api.RetroServer;
import com.if4a.cashflow.models.ModelResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private EditText etJumlahTrans, etKeterangan, etNamaTrans;
    private TextView tvId;
    private ImageView IconTipeTrans;
    private Spinner spTipeTrans;
    private ImageButton Backhome;
    private MaterialButton btnUbah;
    private String  Nama, Jumlah, TipeTransaksi, Tanggal, Keterangan;
    private String yId, yNama, yJumlah, yTipeTransaksi, yTanggal, yKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent intent = getIntent();
        yId = intent.getStringExtra("varId");
        yNama = intent.getStringExtra("varNama");
        yJumlah = String.valueOf(intent.getStringExtra("varJumlah"));
        yTipeTransaksi = intent.getStringExtra("varTipeTransaksi");
        yTanggal = intent.getStringExtra("varTanggalTransaksi");
        yKeterangan = intent.getStringExtra("varKeterangan");
        String datestring = intent.getStringExtra("varTanggalTransaksi");

        tvId = findViewById(R.id.tv_idUbah);
        etJumlahTrans = findViewById(R.id.et_JumlahTransaksiU);
        etKeterangan = findViewById(R.id.et_KeteranganU);
        etNamaTrans = findViewById(R.id.et_NamaTransaksiU);
        DatePicker Datepicker = findViewById(R.id.dp_tanggaltransaksiU);
        IconTipeTrans = findViewById(R.id.IconTipeTransaksi);
        spTipeTrans = findViewById(R.id.sp_TipeTransaksiU);
        Backhome = findViewById(R.id.BackHomeUbah);
        btnUbah = findViewById(R.id.btn_ubah);


        etNamaTrans.setText(yNama);
        etJumlahTrans.setText(yJumlah);
        etKeterangan.setText(yKeterangan);

        Datepicker.setClickable(false);

        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spTipeTrans.getAdapter();

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipeTrans.setAdapter(adapter);

        int position = adapter.getPosition(yTipeTransaksi);
        if (position != -1) {
            spTipeTrans.setSelection(position);
        }

        spTipeTrans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIcon(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNamaTrans.getText().toString();
                Jumlah = etJumlahTrans.getText().toString();
                Keterangan = etKeterangan.getText().toString();
                TipeTransaksi = spTipeTrans.getSelectedItem().toString();
                Tanggal = yTanggal;

                if (Nama.trim().isEmpty()) {
                    etNamaTrans.setError("Nama Transaksi Tidak Boleh Kosong");
                } else if (Jumlah.trim().isEmpty()) {
                    etJumlahTrans.setError("Jumlah Transaksi tidak boleh kosong");
                } else if (Keterangan.trim().isEmpty()) {
                    etKeterangan.setError("Keterangan tidak boleh kosong");
                } else if (TextUtils.isEmpty(TipeTransaksi)) {
                    TextView errorText = (TextView) spTipeTrans.getSelectedView();
                    errorText.setError("Pilih item");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Pilih item");
                    return;
                } else {
                    ubahCash();
                }

            }
        });
    }

    private void updateIcon(int position) {
        String selectedvalue = spTipeTrans.getItemAtPosition(position).toString();
        if (selectedvalue.equalsIgnoreCase("Kredit")) {
            etJumlahTrans.setTextColor(Color.RED);
            IconTipeTrans.setImageResource(R.drawable.minus_01);
        } else if (selectedvalue.equalsIgnoreCase("Debit")) {
            etJumlahTrans.setTextColor(Color.GREEN);
            IconTipeTrans.setImageResource(R.drawable.plus_01);
        }
    }

    private void ubahCash() {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, TipeTransaksi, Jumlah, Tanggal, Keterangan);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode" + kode + ", Pesan" + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}