package com.f91268.pizzashop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.f91268.pizzashop.R;

public class PizzaMenuFragment extends Fragment {

    public PizzaMenuFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pizza_menu_list, container, false);

        /*View rootView = inflater.inflate(R.layout.pizza_menu_list,container,false);

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<MenuItem> menuItems = activity.getArrayListMenu();

        PizzaMenuAdapter adapter = new PizzaMenuAdapter(getActivity(),menuItems);

        final ListView pizzaMenuList = rootView.findViewById(R.id.pizza_list);
        pizzaMenuList.setAdapter(adapter);

        return rootView;*/

    }

}
