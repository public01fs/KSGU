package com.application.ksgu.Retrofit;

import com.application.ksgu.Cons;
import com.application.ksgu.Model.Checklist;
import com.application.ksgu.Model.DataDaerah;
import com.application.ksgu.Model.DataNota;
import com.application.ksgu.Model.DetailSurat;
import com.application.ksgu.Model.Ditkapel;
import com.application.ksgu.Model.DitkapelService;
import com.application.ksgu.Model.FileBerkas;
import com.application.ksgu.Model.Kantor;
import com.application.ksgu.Model.Kapal;
import com.application.ksgu.Model.Layanan;
import com.application.ksgu.Model.Login;
import com.application.ksgu.Model.Negara;
import com.application.ksgu.Model.Pelaut;
import com.application.ksgu.Model.PelautService;
import com.application.ksgu.Model.Resend;
import com.application.ksgu.Model.Save;
import com.application.ksgu.Model.Surat;

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
import retrofit2.http.Query;

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
    Call<Save> savePermohonan(@Part("IDKANTOR") RequestBody idkantor,
                              @Part("JENIS_ID") RequestBody jenis_id,
                              @Part("SURAT_NO") RequestBody surat_no,
                              @Part("SURAT_HAL") RequestBody surat_hal,
                              @Part("SURAT_PENGIRIM") RequestBody surat_pengirim,
                              @Part("SURAT_PENGIRIM_KOTA") RequestBody surat_pengirim_kota,
                              @Part("SURAT_NPWP") RequestBody surat_npwp,
                              @Part("SURAT_TGL") RequestBody surat_tgl,
                              @Part("require_check") RequestBody require_check,
                              @Part("KAPAL_ID") RequestBody kapal_id,
                              @Part("KAPAL_NAME") RequestBody kapal_name,
                              @Part("KAPAL_GT") RequestBody kapal_gt,
                              @Part("KAPAL_CS") RequestBody kapal_cs,
                              @Part("KAPAL_BENDERA") RequestBody kapal_bendera,
                              @Part("KAPAL_PEMILIK") RequestBody kapal_pemilik,
                              @Part("KAPAL_POSISI") RequestBody kapal_posisi,
                              @Part("NAMAPRSH") RequestBody namaprsh,
                              @Part("ALAMATPRSH") RequestBody alamatprsh,
                              @Part("IDENTITASPRSH") RequestBody identitasprsh,
                              @Part("JMLKAPAL") RequestBody jmlkapal,
                              @Part("TOTALGT") RequestBody totalgt,
                              @Part("IDPELAUT") RequestBody idpelaut,
                              @Part("NAMAPELAUT") RequestBody namapelaut,
                              @Part("KODEPELAUT") RequestBody kodepelaut,
                              @Part("TEMPATLAHIR") RequestBody tempatlahir,
                              @Part("TGLLAHIR") RequestBody tgllahir,
                              @Part("UMUR") RequestBody umur,
                              @Part("JK") RequestBody jk,
                              @Part("STATUSPELAUT") RequestBody statuspelaut,
                              @Part("SERTIFIKAT") RequestBody sertifikat,
                              @Part("FOTOPELAUT") RequestBody fotopelaut,
                              @Part List<MultipartBody.Part> nota_id,
                              @Part List<MultipartBody.Part> parent_id,
                              @Part("lampiran") RequestBody lampiran,
                              @Part("checklist") RequestBody checklist);

    @GET(API + "surat/new")
    Call<List<Surat>> getSuratNew();

    @GET(API + "surat/detail/{surat_id}")
    Call<DetailSurat> detailSurat(@Path("surat_id") String surat);

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

    @GET(API + "data-kantor")
    Call<Kantor> getKantor();

    @Multipart
    @POST(API + "surat/upload")
    Call<FileBerkas> uploadSurat(@Part MultipartBody.Part img);
}
