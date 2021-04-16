package com.bignerdranch.android.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.parks.adapter.ViewPagerAdapter;
import com.bignerdranch.android.parks.model.Park;
import com.bignerdranch.android.parks.model.ParkViewModel;

import java.util.List;


public class DetailsFragment extends Fragment {

    private ParkViewModel parkViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.details_viewpager);

        TextView parkName = view.findViewById(R.id.details_park_name);
        TextView parkDes = view.findViewById(R.id.details_park_designation);

        TextView description = view.getRootView().findViewById(R.id.details_description);
        TextView activities = view.getRootView().findViewById(R.id.details_activities);
        TextView entranceFees = view.getRootView().findViewById(R.id.details_entrancefees);
        TextView opHours = view.getRootView().findViewById(R.id.details_operatinghours);
        TextView detailsTopics = view.getRootView().findViewById(R.id.details_topics);
        TextView directions = view.getRootView().findViewById(R.id.details_directions);

        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);

        parkViewModel.getSelectedPark().observe(this, new Observer<Park>() {
            @Override
            public void onChanged(Park park) {
                parkName.setText(park.getName());
                parkDes.setText(park.getDesignation());
                description.setText(park.getDescription());

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < park.getActivities().size(); i++) {
                    stringBuilder.append(park.getActivities().get(i).getId()).append(" | ");
                }
                activities.setText(stringBuilder);

                viewPagerAdapter = new ViewPagerAdapter(park.getImages());
                viewPager.setAdapter(viewPagerAdapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}