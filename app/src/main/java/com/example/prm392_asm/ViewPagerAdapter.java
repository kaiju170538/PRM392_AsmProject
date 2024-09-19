package com.example.prm392_asm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FeaturedFragment();

            case 1:
                return new DealsFragment();

            case 2:
                return new CategoriesFragment();

            default:
                return new DealsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
