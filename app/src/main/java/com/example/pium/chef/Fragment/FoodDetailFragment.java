package com.example.pium.chef.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pium.chef.Model.Products;
import com.example.pium.chef.Model.Users;
import com.example.pium.chef.Other.UILConfig;
import com.example.pium.chef.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.UnsupportedEncodingException;


public class FoodDetailFragment extends Fragment {

    public static final String PREFS = "prefFile";
    final String LOG = "HomeDetailFragment";

    TextView fName;
    TextView fDesc;
    TextView fPrice;
    TextView fCode;
    String img_url;
    ImageView imageView;

    public FoodDetailFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_food_detail, container, false);

        ImageLoader.getInstance().init(UILConfig.config(FoodDetailFragment.this.getActivity()));

        final Products product = (Products) getArguments().getSerializable("productList");

        fName = (TextView) v.findViewById(R.id.food_name);
        fPrice = (TextView) v.findViewById(R.id.food_price);
        fDesc = (TextView) v.findViewById(R.id.food_description);
        fCode = (TextView) v.findViewById(R.id.food_Code);
        imageView = (ImageView)v.findViewById(R.id.img_food);

        img_url = product.pic;
        String imgPass = img_url;

        final TextView code, name, desc, id, price;
        final String qty, imurl;

        if(product != null)
        {
            fName.setText(product.name);
            fDesc.setText(product.descrip);
            fPrice.setText(product.price);
            fCode.setText(product.id);
            ImageLoader.getInstance().displayImage(img_url, imageView);
        }

        SharedPreferences preferences = FoodDetailFragment.this.getActivity().getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);

        name = fName;
        desc = fDesc;
        qty = ""+1;
        price = fPrice;
        code = fCode;
        imurl = img_url;

        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.btnCart);
        if(fab !=null)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                RequestParams params = new RequestParams();

                String cus = "Sudhara";
                String pCode = code.getText().toString();
                String pName =  name.getText().toString();
                String pQty = "1";
                String pPrice = price.getText().toString();
                String pic = imurl;
                String status = "1";
                String pStatus = "WAITING";

                params.put("username", cus);
                params.put("code", pCode);
                params.put("name", pName);
                params.put("qty", pQty);
                params.put("price", pPrice);
                params.put("pic", pic);
                params.put("oStatus", status);
                params.put("pStatus", pStatus);



                try {
                    invokeWS(params);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        return v;
    }
    public void invokeWS(RequestParams params) throws UnsupportedEncodingException
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.10.86.246:8080/ChefService/rest/android/insert/cart", params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                System.out.println(response);

                try
                {
                    if(response.equals("true"))
                    {
                        Toast.makeText(getContext(), "Product Successfully Added to Cart!", Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(getContext(), "Cart Error. Try Again!", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(getContext(), "Error Occured [Server's response might be invalid]!", Toast.LENGTH_LONG).show();
                    ex.printStackTrace();

                }
            }
        });
    }



}
