package com.example.trangchitietdoibong;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Lớp cấu hình Retrofit: Dùng để kết nối giữa Android và Server
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    // URL của Server: 10.0.2.2 là địa chỉ thay thế localhost cho máy ảo Android
                    // 8080 là cổng của Server (bạn kiểm tra trong XAMPP/Laragon)
                    .baseUrl("http://10.0.2.2:8080/") 
                    // Gson dùng để tự động chuyển JSON từ PHP thành Object Java
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
