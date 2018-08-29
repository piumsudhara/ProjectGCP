package com.example.pium.chef.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.pium.chef.Interface.ServicePath;
import com.example.pium.chef.Model.Cart;
import com.example.pium.chef.Adapters.CartDapter;
import com.example.pium.chef.RetrofitClient.GetCartItems;
import com.example.pium.chef.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private ArrayList<Cart> cartList;
    private ListView lv;
    CartDapter adapter;
    private ServicePath apiInterface;
    View view;
    Button btnCheckout;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_cart, container, false);

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        btnCheckout = (Button) view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ListView) view.findViewById(R.id.lvCart);
        apiInterface = GetCartItems.getRetrofitApiClient().create(ServicePath.class);

        cartList  = new ArrayList<Cart>();

        adapter= new CartDapter(getContext(), 0, cartList);
        lv.setAdapter(adapter);

        getProduct();
    }

    private void getProduct() {

        Call<List<Cart>> call = apiInterface.getCart();
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {

                List<Cart> cart = response.body();
                Log.d("TEST", "onResponse: "+response.body().size());
                if(cart.size()>0){
                    cartList.addAll(cart);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
            }
        });

    }
}
