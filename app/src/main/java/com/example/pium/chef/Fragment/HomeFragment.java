package com.example.pium.chef.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ami.fundapter.BindDictionary;
import com.ami.fundapter.FunDapter;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.example.pium.chef.Model.Products;
import com.example.pium.chef.Model.Users;
import com.example.pium.chef.Other.UILConfig;
import com.example.pium.chef.R;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;

import static com.example.pium.chef.Home.PREFS;

public class HomeFragment extends Fragment implements AsyncResponse, AdapterView.OnItemClickListener {

    final static String URL = "http://10.10.86.246.103/Chef/all_products.php";

    private ArrayList<Products> productList;
    private ListView lv;
    FunDapter<Products> adapter;

    View view;

    public HomeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageLoader.getInstance().init(UILConfig.config(HomeFragment.this.getActivity()));

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(HomeFragment.this.getActivity(), this);
        taskRead.execute(URL);

        return view;
    }
    public void processFinish(String s) {

        productList = new JsonConverter<Products>().toArrayList(s, Products.class);

        BindDictionary dic = new BindDictionary();

        /*dic.addStringField(R.id.tvHidden, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position)
            {
                return item.id;
            }
        }); */

        dic.addStringField(R.id.fName, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.name;
            }
        });

       /* dic.addStringField(R.id.tvDesc, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.descrip;
            }
        }).visibilityIfNull(View.GONE); */

        dic.addDynamicImageField(R.id.fImage, new StringExtractor<Products>() {
            @Override
            public String getStringValue(Products item, int position) {
                return item.pic;
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView img) {
                ImageLoader.getInstance().displayImage(url, img);
            }
        });

        adapter = new FunDapter<>(HomeFragment.this.getActivity(), productList, R.layout.fragment_home_row, dic);
        lv = (ListView)view.findViewById(R.id.lvProduct);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Products selectedProduct = productList.get(position);
        Fragment detailFragment = new FoodDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("productList", selectedProduct);
        detailFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frag, detailFragment).addToBackStack("Chef").commit();


    }
}
