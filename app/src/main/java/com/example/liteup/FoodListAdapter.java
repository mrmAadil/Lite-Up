package com.example.liteup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Food> foodList;

    public FoodListAdapter(Context context, int layout, ArrayList<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textName, textPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textName = (TextView) row.findViewById(R.id.textName);
            holder.textPrice = (TextView) row.findViewById(R.id.textPrice);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView2);
            row.setTag(holder);

        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Food food = foodList.get(position);

        holder.textName.setText(food.getName());
        holder.textPrice.setText("Rs "+food.getPrice());

        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0,foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
