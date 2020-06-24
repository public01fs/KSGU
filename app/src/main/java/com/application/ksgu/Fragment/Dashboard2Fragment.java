package com.application.ksgu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.ksgu.Adapter.DokumenListAdapter;
import com.application.ksgu.Adapter.ItemListAdapter;
import com.application.ksgu.Adapter.NewsAdapter;
import com.application.ksgu.ListDokumenActivity;
import com.application.ksgu.LoginActivity2;
import com.application.ksgu.Main3Activity;
import com.application.ksgu.Model.Image;
import com.application.ksgu.Model.Item;
import com.application.ksgu.Model.News;
import com.application.ksgu.PermohonanActivity;
import com.application.ksgu.R;
import com.application.ksgu.Retrofit.ApiInterface;
import com.application.ksgu.Retrofit.ServiceGenerator;
import com.application.ksgu.SessionManager;
import com.application.ksgu.Widget.SpacesItemDecoration;
import com.artcak.artcakbase.recycleview.GridSpacingItemDecoration;
import com.artcak.artcakbase.tools.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.application.ksgu.Cons.KEY_EMAIL;
import static com.application.ksgu.Cons.KEY_IMG;
import static com.application.ksgu.Cons.KEY_NAME;

public class Dashboard2Fragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    View view;
    SessionManager sessionManager;
    RecyclerView rv_menu1,rv_menu2,rv_news;
    ItemListAdapter itemListAdapter,itemListAdapter1;
    List<Item> itemList     = new ArrayList<>();
    List<Item> itemList1    = new ArrayList<>();
    List<News> newsList     = new ArrayList<>();
    Tools tools = new Tools(getActivity());
    NewsAdapter newsAdapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SliderLayout sliderLayout;
    List<Image> images;
    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view            = inflater.inflate(R.layout.fragment_dashboard2, container, false);
        sessionManager  = new SessionManager(getContext());
        rv_news         = view.findViewById(R.id.rv_news);
        rv_menu1        = view.findViewById(R.id.rv_menu1);
        rv_menu2        = view.findViewById(R.id.rv_menu2);
        sliderLayout    = view.findViewById(R.id.slider);
        prefs           = getActivity().getSharedPreferences("layanan", Context.MODE_PRIVATE);
        editor          = prefs.edit();
        images          = gson.fromJson(prefs.getString("foto",""), new TypeToken<List<Image>>(){}.getType());

        setBerita();
        setMenu1();
        setMenu2();
        loadImage(images);
//        getImage();

        return view;
    }

    private void setBerita(){
        newsList.add(new News(R.drawable.gambar_1,"Gambar 1"));
        newsList.add(new News(R.drawable.gambar_2,"Gambar 2"));
        newsList.add(new News(R.drawable.gambar_3,"Gambar 3"));
        newsList.add(new News(R.drawable.gambar_4,"Gambar 4"));

        newsAdapter = new NewsAdapter(newsList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rv_news.addItemDecoration(new SpacesItemDecoration(30));
        rv_news.setLayoutManager(linearLayoutManager);
        rv_news.setItemAnimator(new DefaultItemAnimator());
        rv_news.setAdapter(newsAdapter);
    }

    private void setMenu1(){
        itemList.add(new Item("Status Hukum dan Sertifikasi Kapal",R.drawable.ic_law,"1"));
        itemList.add(new Item("Keselamatan Berlayar",R.drawable.ic_float,"2"));
        itemList.add(new Item("Penjagaan,Patroli dan Penyidikan",R.drawable.ic_alert,"3"));
        itemList.add(new Item("Bagian Tata Usaha",R.drawable.ic_administartion,"4"));

        itemListAdapter = new ItemListAdapter(getContext(),itemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rv_menu1.setLayoutManager(linearLayoutManager);
        rv_menu1.setItemAnimator(new DefaultItemAnimator());
        rv_menu1.setAdapter(itemListAdapter);

        itemListAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item obj, int position) {
//                if (sessionManager.isLoggedIn()){
//                    Intent i = new Intent(getContext(), ListDokumenActivity.class);
//                    i.putExtra("title",obj.getJudul());
//                    i.putExtra("bidang_id",obj.getId());
//                    startActivity(i);
//                } else {
//                    startActivity(new Intent(getContext(), LoginActivity2.class));
//                }
                Intent i = new Intent(getContext(), ListDokumenActivity.class);
                i.putExtra("title",obj.getJudul());
                i.putExtra("bidang_id",obj.getId());
                startActivity(i);
            }
        });
    }

    private void setMenu2(){
        itemList1.add(new Item("PPMKK\n",R.drawable.ic_ppmkk,"PPMKK"));
        itemList1.add(new Item("Keselamatan Kapal",R.drawable.ic_kapal,"KESELAMATAN KAPAL"));
        itemList1.add(new Item("Pendaftaran dan Pengukuran Kapal",R.drawable.ic_ppk,"PPK"));
        itemList1.add(new Item("Kepelautan\n",R.drawable.ic_pelaut,"KEPELAUTAN"));
        itemList1.add(new Item("Keselamatan Berlayar",R.drawable.ic_stb,"STB"));
        itemList1.add(new Item("P3\n",R.drawable.ic_p3,"P3"));

        itemListAdapter1 = new ItemListAdapter(getContext(),itemList1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rv_menu2.setLayoutManager(linearLayoutManager);
        rv_menu2.setItemAnimator(new DefaultItemAnimator());
        rv_menu2.setAdapter(itemListAdapter1);

        itemListAdapter1.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Item obj, int position) {
                if (sessionManager.isLoggedIn()){
                    editor.putString("title",obj.getJudul());
                    editor.putString("data",obj.getId());
                    editor.commit();
                    startActivity(new Intent(getContext(), PermohonanActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity2.class));
                }
            }
        });
    }

    private void getImage(){
        ApiInterface apiInterface   = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Image>> call      = apiInterface.getImage();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()){
                    if (response.body().size() > 0) {
                        loadImage(response.body());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });
//        int[] image = {R.drawable.gambar_1,R.drawable.gambar_2,R.drawable.gambar_3,R.drawable.gambar_4};
//        String[] title = {"Gambar 1","Gambar 2","Gambar 3","Gambar 4"};
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Gambar 1",R.drawable.gambar_1);
//        file_maps.put("Gambar 2",R.drawable.gambar_2);
//        file_maps.put("Gambar 3",R.drawable.gambar_3);
//        file_maps.put("Gambar 4", R.drawable.gambar_4);


    }

    private void loadImage(List<Image> images){
        for (int i = 0; i < images.size(); i++) {
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView
                    .description("")
                    .image(images.get(i).getLinkUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",images.get(i).getLinkDescription());

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.movePrevPosition();
//        sliderLayout.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));
        sliderLayout.addOnPageChangeListener(this);

//        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
