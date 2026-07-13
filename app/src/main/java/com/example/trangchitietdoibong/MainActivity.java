package com.example.trangchitietdoibong;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCauThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView từ XML
        rvCauThu = findViewById(R.id.rvCauThu);
        // Thiết lập dạng danh sách cuộn dọc
        rvCauThu.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo API từ RetrofitClient
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        
        // Gọi API để lấy danh sách cầu thủ từ MySQL (chạy ngầm)
        api.getCauThu().enqueue(new Callback<List<CauThu>>() {
            @Override
            public void onResponse(Call<List<CauThu>> call, Response<List<CauThu>> response) {
                // Nếu lấy dữ liệu thành công
                if (response.isSuccessful() && response.body() != null) {
                    List<CauThu> list = response.body();
                    // Đưa danh sách vào Adapter để hiển thị lên màn hình
                    rvCauThu.setAdapter(new CauThuAdapter(list));
                } else {
                    Log.e("API", "Lỗi dữ liệu: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CauThu>> call, Throwable t) {
                // Nếu gặp lỗi kết nối (sai IP, chưa bật XAMPP...)
                Log.e("API_ERROR", "Không thể kết nối Server: " + t.getMessage());
            }
        });
    }
}
