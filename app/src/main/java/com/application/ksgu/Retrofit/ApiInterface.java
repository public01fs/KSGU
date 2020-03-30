package com.application.ksgu.Retrofit;

import com.application.ksgu.Cons;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.Model.Layanan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    String API = Cons.SERVER_URL;

    @GET(API + "data-layanan")
    Call<List<Layanan>> getLayanan();

    @GET(API + "data-nota/{id}")
    Call<List<DataNota>> getDataNota(@Path("id") int id);
}
