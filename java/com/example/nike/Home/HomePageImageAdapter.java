package com.example.nike.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nike.R;

import java.util.ArrayList;

public class HomePageImageAdapter extends BaseAdapter {
    /* PROPERTY */
    private ArrayList<HomePageImage> _homePageImages;
    private Context _context;

    /* CONSTRUCTOR */
    public HomePageImageAdapter() { }

    public HomePageImageAdapter(ArrayList<HomePageImage> _homePageImages, Context context) {
        this._homePageImages = _homePageImages;
        this._context = context;
    }

    /* SETTER - GETTER */
    public ArrayList<HomePageImage> get_homeImages() {
        return _homePageImages;
    }

    public void set_homeImages(ArrayList<HomePageImage> _homePageImages) {
        this._homePageImages = _homePageImages;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }
    /* FUNCTION */

    @Override
    public int getCount() {
        return this._homePageImages.size();
    }

    @Override
    public Object getItem(int position) {
        return this._homePageImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomePageImageView homePageImageView;

        if (convertView == null)
        {
            homePageImageView = new HomePageImageView();
            convertView = LayoutInflater.from(this._context).inflate(R.layout.home_page_image, null);
            homePageImageView._imv_homePageImage = convertView.findViewById(R.id.imv_homePageImage);
            convertView.setTag(homePageImageView);
        }
        else
        {
            homePageImageView = (HomePageImageView) convertView.getTag();
        }

        Glide.with(homePageImageView._imv_homePageImage.getContext()).load(this._homePageImages.get(position).get_homePageImageLink()).into(homePageImageView._imv_homePageImage);

        return convertView;
    }

    /* View */
    class HomePageImageView
    {
        private ImageView _imv_homePageImage;
    }
}
