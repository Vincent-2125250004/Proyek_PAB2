package com.if4a.cashflow.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.cashflow.R;
import com.if4a.cashflow.activity_detail.DetailActivity;
import com.if4a.cashflow.api.APIRequestData;
import com.if4a.cashflow.api.RetroServer;
import com.if4a.cashflow.models.ModelCash;
import com.if4a.cashflow.models.ModelResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCash extends RecyclerView.Adapter<AdapterCash.VHCash> {
    public List<ModelCash> listriwayat;
    private Context ctx;



    public AdapterCash(List<ModelCash> listriwayat, Context ctx) {
        this.listriwayat = listriwayat;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterCash.VHCash onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat, parent, false);
        return new VHCash(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCash.VHCash holder, int position) {
        AlphaAnimation AA = new AlphaAnimation(0.0f,1.0f);
        AA.setDuration(500);
        holder.itemView.startAnimation(AA);

        ModelCash MC = listriwayat.get(position);
        holder.tvId.setText(MC.getId());
        holder.tvNamaTrans.setText(MC.getNama());
        holder.tvTipeTransaksi.setText(MC.getTipe_transaksi());
        if (MC.getTipe_transaksi().equals("Kredit")) {
            holder.Icon.setImageResource(R.drawable.money_send_svgrepo_com);
            holder.tvJumlah.setText("-Rp." + String.valueOf(MC.getJumlah()));
            holder.tvJumlah.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.merah));
        } else if (MC.getTipe_transaksi().equals("Debit")) {
            holder.Icon.setImageResource(R.drawable.money_recive_svgrepo_com);
            holder.tvJumlah.setText("+Rp." + String.valueOf(MC.getJumlah()));
            holder.tvJumlah.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.hijau));
        }
        holder.tvTanggalTrans.setText(MC.getTanggal_transaksi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("varId", MC.getId());
                intent.putExtra("varNama", MC.getNama());
                intent.putExtra("varKeterangan", MC.getKeterangan());
                intent.putExtra("varJumlah", String.valueOf(MC.getJumlah()));
                intent.putExtra("varTanggalTransaksi", MC.getTanggal_transaksi());
                intent.putExtra("varTipeTransaksi", MC.getTipe_transaksi());
                holder.itemView.getContext().startActivity(intent);

            }

        });

    }


    @Override
    public int getItemCount() {
        return listriwayat.size();
    }

    public class VHCash extends RecyclerView.ViewHolder {

        TextView tvNamaTrans, tvTanggalTrans, tvTipeTransaksi, tvJumlah, tvDigitSaldo, tvId;
        ImageView Icon;

        public VHCash(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvDigitSaldo = itemView.findViewById(R.id.DigitSaldo);
            Icon = itemView.findViewById(R.id.IconKreditDebit);
            tvNamaTrans = itemView.findViewById(R.id.tv_namatrans);
            tvTanggalTrans = itemView.findViewById(R.id.tv_tanggal);
            tvTipeTransaksi = itemView.findViewById(R.id.tv_jenistransaksi);
            tvJumlah = itemView.findViewById(R.id.tv_jumlahtrans);

        }

    }

    public void hapusCash(String idCash){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardDelete(idCash);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ctx, "Kode" +kode + " /nPesan" + pesan, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(ctx, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<ModelCash> getListRiwayat(){
        return listriwayat;
    }

    public List<ModelCash> getData(List<ModelCash> datalist) {
        return listriwayat;
    }





}
