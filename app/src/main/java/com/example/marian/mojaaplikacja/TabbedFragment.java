package com.example.marian.mojaaplikacja;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabbedFragment extends Fragment {

    private TabLayout tabs;

    private ViewPager viewPager;
    public static Fragment mf;
    public  FloatingActionButton fab1;

    public TabbedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_tabbed, container, false);

        tabs = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewPag);
        fab1 = (FloatingActionButton) v.findViewById(R.id.fab1);

        viewPager.setAdapter(new CustomFragmentPageAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewPager);

        return v;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mf = this;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Trening w terenie");

    }




}
