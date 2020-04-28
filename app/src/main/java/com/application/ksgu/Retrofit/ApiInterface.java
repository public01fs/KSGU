package com.application.ksgu.Retrofit;

import com.application.ksgu.Cons;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataDaerah;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.Model.Ditkapel;
import com.application.ksgu.Model.DitkapelService;
import com.application.ksgu.Model.Kapal;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.Model.Login;
import com.application.ksgu.Model.Negara;
import com.application.ksgu.Model.Pelaut;
import com.application.ksgu.Model.PelautService;
import com.application.ksgu.Model.Resend;

import java.util.ArrayList;
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

    @GET(API + "data-layanan/{note}")
    Call<List<Layanan>> getLayanan(@Path("note") String note);

    @GET(API + "data-nota/{id}")
    Call<List<DataNota>> getDataNota(@Path("id") int id);

    @GET(API + "data-checklist/{id}")
    Call<List<Checklist>> getCheckList(@Path("id") int id);

    @GET(API + "data-daerah")
    Call<List<DataDaerah>> getDaerah();

    @FormUrlEncoded
    @POST("https://www.google.com/recaptcha/api/siteverify")
    Call<ResponseBody> verifiy(@Field("secret") String secret,
                               @Field("response") String response);

    @Multipart
    @POST(API + "register/save")
    Call<ResponseBody> saveRegister(@Part("name") RequestBody name,
                                    @Part("email") RequestBody email,
                                    @Part("password") RequestBody password,
                                    @Part("password_confirmation") RequestBody password_confirm,
                                    @Part("alamat") RequestBody alamat,
                                    @Part("daerah_id") RequestBody daerah_id,
                                    @Part("telepon") RequestBody telepon,
                                    @Part MultipartBody.Part ktp);

    @FormUrlEncoded
    @POST(API + "register/verification")
    Call<Login> verification(@Field("confirm") String confirm,
                             @Field("email") String email);

    @FormUrlEncoded
    @POST(API + "register/resend-code")
    Call<Resend> resend(@Field("email") String email);

    @FormUrlEncoded
    @POST(API + "auth/login")
    Call<Login> login(@Field("email") String email,
                      @Field("password") String password);

    @Multipart
    @POST(API + "user/update-profile")
    Call<Login> updateProfile(@Part MultipartBody.Part img,
                              @Part("password") RequestBody password,
                              @Part("confirm-password") RequestBody confirm,
                              @Part("name") RequestBody name,
                              @Part("npwp") RequestBody npwp,
                              @Part("alamat") RequestBody alamat,
                              @Part("daerah_id") RequestBody daerah_id,
                              @Part("telepon") RequestBody telepon,
                              @Part("agen_id") RequestBody agen_id,
                              @Part("agen_nama") RequestBody agen_nama,
                              @Part("agen_alamat") RequestBody agen_alamat,
                              @Part("agen_daerah") RequestBody agen_daerah,
                              @Part("id") RequestBody id);

    @Multipart
    @POST(API + "surat/save")
    Call<ResponseBody> savePermohonan(@Part("idkantor") RequestBody idkantor,
                                      @Part("jenis_id") RequestBody jenis_id,
                                      @Part("surat_no") RequestBody surat_no,
                                      @Part("surat_hal") RequestBody surat_hal,
                                      @Part("surat_pengirim") RequestBody surat_pengirim,
                                      @Part("surat_pengirim_kota") RequestBody surat_pengirim_kota,
                                      @Part("surat_npwp") RequestBody surat_npwp,
                                      @Part("surat_tgl") RequestBody surat_tgl,
                                      @Part("require_check") RequestBody require_check,
                                      @Part("kapal_id") RequestBody kapal_id,
                                      @Part("kapal_name") RequestBody kapal_name,
                                      @Part("kapal_gt") RequestBody kapal_gt,
                                      @Part("kapal_cs") RequestBody kapal_cs,
                                      @Part("kapal_bendera") RequestBody kapal_bendera,
                                      @Part("kapal_pemilik") RequestBody kapal_pemilik,
                                      @Part("kapal_posisi") RequestBody kapal_posisi,
                                      @Part("namaprsh") RequestBody namaprsh,
                                      @Part("alamatprsh") RequestBody alamatprsh,
                                      @Part("identitasprsh") RequestBody identitasprsh,
                                      @Part("jmlkapal") RequestBody jmlkapal,
                                      @Part("totalgt") RequestBody totalgt,
                                      @Part("idpelaut") RequestBody idpelaut,
                                      @Part("namapelaut") RequestBody namapelaut,
                                      @Part("kodepelaut") RequestBody kodepelaut,
                                      @Part("tempatlahir") RequestBody tempatlahir,
                                      @Part("tgllahir") RequestBody tgllahir,
                                      @Part("umur") RequestBody umur,
                                      @Part("jk") RequestBody jk,
                                      @Part("statuspelaut") RequestBody statuspelaut,
                                      @Part("sertifikat") RequestBody sertifikat,
                                      @Part("fotopelaut") RequestBody fotopelaut
                                      );

    @FormUrlEncoded
    @POST(API + "ditkapel-auto")
    Call<List<Ditkapel>> getDitkapel(@Field("term") String term);

    @FormUrlEncoded
    @POST(API + "ditkapel-service")
    Call<List<DitkapelService>> getDitkapelService(@Field("no_tanda_pendaftaran") String no_pendaftaran);

    @FormUrlEncoded
    @POST(API + "pelaut-auto")
    Call<List<Pelaut>> getPelaut(@Field("term") String term);

    @FormUrlEncoded
    @POST(API + "pelaut-service")
    Call<PelautService> getPelautService(@Field("KodePelaut") String kodepelaut);

    @FormUrlEncoded
    @POST(API + "negara-auto")
    Call<List<Negara>> getNegara(@Field("term") String term);

    @FormUrlEncoded
    @POST(API + "kapal-auto")
    Call<List<Kapal>> getKapal(@Field("term") String term);

}
