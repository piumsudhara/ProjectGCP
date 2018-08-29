package com.example.pium.chef.Interface;

import com.example.pium.chef.Model.Cart;
import com.example.pium.chef.Model.Products;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePath
{
    @GET("get/products")
    Call<List<Products>> getProducts();

    @GET("get/Sudhara")
    Call<List<Cart>> getCart();
}
