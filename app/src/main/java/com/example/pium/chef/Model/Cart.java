package com.example.pium.chef.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cart implements Serializable
{
    @SerializedName("productCode")
    public String productCode;

    @SerializedName("productName")
    @Expose
    public String productName;

    @SerializedName("qty")
    @Expose
    public String qty;

    @SerializedName("productPrice")
    @Expose
    public String productPrice;

    @SerializedName("productPic")
    @Expose
    public String productPic;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
