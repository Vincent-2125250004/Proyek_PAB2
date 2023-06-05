package com.if4a.cashflow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.if4a.cashflow.R;
import com.if4a.cashflow.api.APIRequestData;
import com.if4a.cashflow.api.RetroServer;
import com.if4a.cashflow.models.ModelResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private EditText etJumlahTrans, etKeterangan, etNamaTrans;
    private ImageView IconTipeTrans;
    private Spinner spTipeTrans;
    private ImageButton Backhome;
    private MaterialButton btnSimpan;
    private String Nama, Jumlah, TipeTransaksi, Tanggal, Keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etJumlahTrans = findViewById(R.id.et_JumlahTransaksi);
        etKeterangan = findViewById(R.id.et_Keterangan);
        etNamaTrans = findViewById(R.id.et_NamaTransaksi);
        DatePicker DatePicker = findViewById(R.id.dp_tanggaltransaksi);
        IconTipeTrans = findViewById(R.id.IconTipeTransaksi);
        spTipeTrans = findViewById(R.id.sp_TipeTransaksi);
        Backhome = findViewById(R.id.BackHomeTambah);
        btnSimpan = findViewById(R.id.btn_simpan);

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH);
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(startYear, startMonth, startDay);
        long minDate = calendar.getTimeInMillis();
        calendar.set(endYear, endMonth, endDay);
        long maxDate = calendar.getTimeInMillis();
        DatePicker.setMinDate(minDate);
        DatePicker.setMaxDate(maxDate);

        DatePicker.init(startYear, startMonth, startDay, null);

        int dayOfMonth = DatePicker.getDayOfMonth();
        int month = DatePicker.getMonth() + 1;
        int year = DatePicker.getYear();
        String selectedDate = year + "/" + month + "/" + dayOfMonth;


        TipeTransaksi = spTipeTrans.getSelectedItem().toString();
        spTipeTrans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIcon(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNamaTrans.getText().toString();
                Jumlah = etJumlahTrans.getText().toString();
                Keterangan = etKeterangan.getText().toString();
                TipeTransaksi = spTipeTrans.getSelectedItem().toString();
                Tanggal = selectedDate;

                Intent intent = new Intent(TambahActivity.this, UbahActivity.class);
                intent.putExtra("selectedYear", String.valueOf(year));
                intent.putExtra("selectedMonth", String.valueOf(month));
                intent.putExtra("selectedDayOfMonth", String.valueOf(dayOfMonth));

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
                    tambahCash();
                }
            }
        });

        Backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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


    private void tambahCash() {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(Nama, TipeTransaksi, Jumlah, Tanggal, Keterangan);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "\n Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}