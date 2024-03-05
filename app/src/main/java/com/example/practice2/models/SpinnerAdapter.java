package com.example.practice2.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.practice2.R;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private int[] imgs = {R.drawable.xemay,R.drawable.oto,R.drawable.maybay};

    public SpinnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int i) {
        return imgs[i];
    }

    public int getItemPosition(int imgID) {
        for(int i = 0 ; i<imgs.length;i++) {
            if(imgs[i] == imgID) return i;
        }
        return -1;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = LayoutInflater.from(context).inflate(R.layout.image_item,viewGroup,false);
        ImageView imgView = item.findViewById(R.id.img_item);
        imgView.setImageResource(imgs[i]);
        return item;
    }
}
