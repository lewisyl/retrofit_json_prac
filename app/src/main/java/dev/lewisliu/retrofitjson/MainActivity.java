package dev.lewisliu.retrofitjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.lusfold.spinnerloading.SpinnerLoading;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    SpinnerLoading spinnerLoading;
//    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Loading..");
//        progressDialog.setTitle("Get Data");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        spinnerLoading = findViewById(R.id.spinner);
        spinnerLoading.setPaintMode(1);
        spinnerLoading.setCircleRadius(20);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IGetDataService service = RetrofitClientInstance.getRetrofitInstance().create(IGetDataService.class);
                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {
                    @Override
                    public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
//                progressDialog.dismiss();
                        generateDataList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
//                progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
                spinnerLoading.setVisibility(View.GONE);
            }
        }, 4000);

    }
    private void generateDataList(List<RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomAdapter(MainActivity.this,photoList);
        recyclerView.setAdapter(adapter);
    }
}