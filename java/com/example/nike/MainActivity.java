package com.example.nike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.nike.Fragments.BagFragment;
import com.example.nike.Fragments.HomeFragment;
import com.example.nike.Fragments.ProfileFragment;
import com.example.nike.Fragments.ShopFragment;
import com.example.nike.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding _binding;

    /* BottomNavigationView */
    BottomNavigationView _bnv_bottomNavigationView;
    HomeFragment _homeFragment = new HomeFragment();
    ShopFragment _shopFragment = new ShopFragment();
    BagFragment _bagFragment = new BagFragment();
    ProfileFragment _profileFragment = new ProfileFragment();

    private int _tabIndexReturned = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        this._bnv_bottomNavigationView = findViewById(R.id.bnv_bottomNavigationView);

        /*                          PUSH DATA
        mDatabase = FirebaseDatabase.getInstance().getReference("Size");
        mDatabase.setValue("41");
        mDatabase.getDatabase().getReference("Products").child("p1");*/

        Intent intentFragment = getIntent();
        if (intentFragment.getExtras() != null && intentFragment.getExtras().containsKey("tabIndexReturned"))
        {
            this._tabIndexReturned = intentFragment.getExtras().getInt("tabIndexReturned");
            if (this._tabIndexReturned == 0)
                this.ReplaceFragment(this._homeFragment);
            else if (this._tabIndexReturned == 1)
                this.ReplaceFragment(this._shopFragment);
            else if (this._tabIndexReturned == 2)
                this.ReplaceFragment(this._bagFragment);
            else if (this._tabIndexReturned == 3)
                this.ReplaceFragment(this._profileFragment);

            this.ChangeColorOfSelectedItem();
        }
        else
        {
             this.ReplaceFragment(this._homeFragment);
             this.ChangeColorOfSelectedItem();
        }

        this.HandlesTheBottomNavigationView();

    }

    private void HandlesTheBottomNavigationView()
    {
        this._binding.bnvBottomNavigationView.setOnItemSelectedListener(item ->
        {
            if (item.getItemId() == R.id.itm_home)
                this.ReplaceFragment(this._homeFragment);
            else if (item.getItemId() == R.id.itm_shop)
                this.ReplaceFragment(this._shopFragment);
            else if (item.getItemId() == R.id.itm_bag)
                this.ReplaceFragment(this._bagFragment);
            else if (item.getItemId() == R.id.itm_profile)
                this.ReplaceFragment(this._profileFragment);

            this.ChangeColorOfSelectedItem();

            return true;
        });
    }

    private void ReplaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frl_frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void ChangeColorOfSelectedItem()
    {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked}, // Trạng thái item được chọn
                        new int[]{-android.R.attr.state_checked}  // Trạng thái mặc định
                },
                new int[]{
                        ContextCompat.getColor(this, R.color.black), // Màu khi item được chọn
                        ContextCompat.getColor(this, R.color.pale_gray)    // Màu mặc định
                }
        );

        // Áp dụng ColorStateList cho item trong BottomNavigationView
        this._bnv_bottomNavigationView.setItemIconTintList(colorStateList); // Áp dụng cho biểu tượng
        this._bnv_bottomNavigationView.setItemTextColor(colorStateList); // Áp dụng cho tiêu đề (nếu có)
    }

}