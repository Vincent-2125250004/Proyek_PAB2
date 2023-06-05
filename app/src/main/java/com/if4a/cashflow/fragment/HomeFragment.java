package com.if4a.cashflow.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.if4a.cashflow.R;
import com.if4a.cashflow.activity.MainActivity;
import com.if4a.cashflow.adapter.AdapterCash;
import com.if4a.cashflow.api.APIRequestData;
import com.if4a.cashflow.api.RetroServer;
import com.if4a.cashflow.models.ModelCash;
import com.if4a.cashflow.models.ModelResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvRiwayat;
    private RecyclerView.Adapter adRiwayat;
    private RecyclerView.LayoutManager lmRiwayat;
    private List<ModelCash> listRiwayat;

    private double saldo =0.0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRiwayat = view.findViewById(R.id.rv_riwayat);

        lmRiwayat = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        rvRiwayat.setLayoutManager(lmRiwayat);

    }

    private void retrieveRiwayat() {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listRiwayat = response.body().getData();

//                Toast.makeText(getView().getContext(), "Berhasil" + kode + pesan + listRiwayat, Toast.LENGTH_SHORT).show();
                adRiwayat = new AdapterCash(listRiwayat, getView().getContext());
                rvRiwayat.setAdapter(adRiwayat);

                adRiwayat.notifyDataSetChanged();

                calculatedSaldo();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(getView().getContext(), "Gagal menghubungi Server:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveRiwayat();
    }

    private void calculatedSaldo (){
        saldo=0.0;

        for (ModelCash cash :listRiwayat){
            if (cash.getTipe_transaksi().equalsIgnoreCase("Kredit")){
                saldo -= cash.getJumlah();
            } else if (cash.getTipe_transaksi().equalsIgnoreCase("Debit")) {
                saldo += cash.getJumlah();
            }
        }

        TextView tvSaldo = getView().findViewById(R.id.DigitSaldo);
        tvSaldo.setText(String.valueOf(saldo));
    }
}