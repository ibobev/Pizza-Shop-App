package com.f91268.pizzashop.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.f91268.pizzashop.fragments.CustomerDetailsFragment;
import com.f91268.pizzashop.fragments.FavoriteFragment;
import com.f91268.pizzashop.fragments.PizzaMenuFragment;

public class ScreenSlideAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 3;

    public ScreenSlideAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0 :
                return new PizzaMenuFragment();
            case 1 :
                return new CustomerDetailsFragment();
            case 2 :
                return new FavoriteFragment();
        }

        return new PizzaMenuFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}
