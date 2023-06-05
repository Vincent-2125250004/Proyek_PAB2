package com.if4a.cashflow.activity_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;


import com.google.android.material.button.MaterialButton;
import com.if4a.cashflow.R;
import com.if4a.cashflow.activity.UbahActivity;
import com.if4a.cashflow.adapter.AdapterCash;
import com.if4a.cashflow.models.ModelCash;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public TextView tvNamaTransDetail, tvTanggalTransaksiDetail, tvDetailJumlah,
            tvNamaTransaksi, tvJumlahTrans1, tvJumlahPembayaran, tvKeterangan, tvTipetransaksi;
    public MaterialButton btn_ubah, btn_delete;
    public ImageButton Backhome;
    public ImageView IconKreditDebit;

    private List<ModelCash> listriwayat;
    private Context ctx;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        tvNamaTransDetail = findViewById(R.id.tv_namatransDetail);
        tvTanggalTransaksiDetail = findViewById(R.id.tv_tanggaltransaksidetail);
        tvDetailJumlah = findViewById(R.id.tv_detailjumlah);
        tvNamaTransaksi = findViewById(R.id.tv_namatransaksi);
        tvJumlahTrans1 = findViewById(R.id.tv_jumlahtrans1);
        tvJumlahPembayaran = findViewById(R.id.tv_jumlahpembayaran);
        tvKeterangan = findViewById(R.id.tv_Keterangan);
        tvTipetransaksi = findViewById(R.id.tv_tipetransaksi);
        IconKreditDebit = findViewById(R.id.IconKreditDebit);
        Backhome = findViewById(R.id.BackHome);
        btn_ubah = findViewById(R.id.btn_Ubah);
        btn_delete = findViewById(R.id.btn_Delete);

        id =intent.getStringExtra("varId");
        tvNamaTransDetail.setText(intent.getStringExtra("varNama"));
        tvNamaTransaksi.setText(intent.getStringExtra("varNama"));
        tvTanggalTransaksiDetail.setText(intent.getStringExtra("varTanggalTransaksi"));
        tvKeterangan.setText(intent.getStringExtra("varKeterangan"));
        tvJumlahTrans1.setText(String.valueOf(intent.getStringExtra("varJumlah")));
        tvJumlahPembayaran.setText(String.valueOf(intent.getStringExtra("varJumlah")));
        tvDetailJumlah.setText(String.valueOf(intent.getStringExtra("varJumlah")));
        tvTipetransaksi.setText(intent.getStringExtra("varTipeTransaksi"));



        if (tvTipetransaksi.getText().equals("Kredit")) {
            tvDetailJumlah.setTextColor(getResources().getColor(R.color.merah));
            tvJumlahTrans1.setTextColor(getResources().getColor(R.color.merah));
            tvJumlahPembayaran.setTextColor(getResources().getColor(R.color.merah));

            tvJumlahTrans1.setText("-Rp." + String.valueOf(intent.getStringExtra("varJumlah")));
            tvJumlahPembayaran.setText("-Rp." + String.valueOf(intent.getStringExtra("varJumlah")));
            tvDetailJumlah.setText("-Rp." + String.valueOf(intent.getStringExtra("varJumlah")));

            IconKreditDebit.setImageResource(R.drawable.money_send_svgrepo_com);
        } else if (tvTipetransaksi.getText().equals("Debit")) {
            tvDetailJumlah.setTextColor(getResources().getColor(R.color.hijau));
            tvJumlahTrans1.setTextColor(getResources().getColor(R.color.hijau));
            tvJumlahPembayaran.setTextColor(getResources().getColor(R.color.hijau));

            tvJumlahTrans1.setText("+Rp." + String.valueOf(intent.getStringExtra("varJumlah")));
            tvJumlahPembayaran.setText("+Rp." + String.valueOf(intent.getStringExtra("varJumlah")));
            tvDetailJumlah.setText("+Rp." + String.valueOf(intent.getStringExtra("varJumlah")));

            IconKreditDebit.setImageResource(R.drawable.money_recive_svgrepo_com);
        }


        Backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUbah = new Intent(DetailActivity.this, UbahActivity.class);
                intentUbah.putExtra("varId", intent.getStringExtra("varId"));
                intentUbah.putExtra("varNama", intent.getStringExtra("varNama"));
                intentUbah.putExtra("varKeterangan", intent.getStringExtra("varKeterangan"));
                intentUbah.putExtra("varJumlah", String.valueOf(intent.getStringExtra("varJumlah")));
                intentUbah.putExtra("varTipeTransaksi", intent.getStringExtra("varTipeTransaksi"));
                intentUbah.putExtra("varTanggalTransaksi", intent.getStringExtra("varTanggalTransaksi"));
                startActivity(intentUbah);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterCash AC = new AdapterCash(listriwayat, ctx);
                AC.hapusCash(intent.getStringExtra("varId"));

            }
        });
    }
}