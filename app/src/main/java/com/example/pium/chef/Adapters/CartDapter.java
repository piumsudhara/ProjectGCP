package com.example.pium.chef.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pium.chef.Model.Cart;
import com.example.pium.chef.Model.Products;
import com.example.pium.chef.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartDapter extends ArrayAdapter<Cart>
{
    private Context context;
    private ArrayList<Cart> objects;
    private static LayoutInflater inflater = null;

    public CartDapter(@NonNull Context context, int resource, @NonNull ArrayList<Cart> objects)
    {
        super(context, resource, objects);
        try
        {
            this.context = context;
            this.objects = objects;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        catch (Exception e)
        {

        }
    }

    public int getCount() {
        return objects.size();
    }

    public Products getItem(Products position) {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder {
        public TextView display_code;
        public TextView display_name;
        public TextView display_number;
        public ImageView display_image;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        final ViewHolder holder;
        try
        {
            if (convertView == null)
            {
                vi = inflater.inflate(R.layout.cart_row, null);
                holder = new ViewHolder();

                holder.display_code = (TextView) vi.findViewById(R.id.fCode);
                holder.display_name = (TextView) vi.findViewById(R.id.fName);
                holder.display_number = (TextView) vi.findViewById(R.id.fPrice);
                holder.display_image = (ImageView) vi.findViewById(R.id.fImage);

                vi.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_code.setText(objects.get(position).getProductCode());
            holder.display_name.setText(objects.get(position).getProductName());
            holder.display_number.setText(objects.get(position).getProductPrice());


            Picasso.with(context)
                    .load(objects.get(position).getProductPic())
                    .resize(120, 120)
                    .centerInside()
                    .into(holder.display_image);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vi;
    }
}
