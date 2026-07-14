package com.example.trangchitietdoibong;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("app_api/get_cauthu.php")
    Call<List<CauThu>> getCauThu();
}
