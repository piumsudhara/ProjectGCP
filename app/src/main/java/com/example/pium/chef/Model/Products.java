package com.example.pium.chef.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Products implements Serializable
{
    @SerializedName("code")
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("descrip")
    @Expose
    public String descrip;

    @SerializedName("price")
    @Expose
    public String price;

    @SerializedName("pic")
    @Expose
    public String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
