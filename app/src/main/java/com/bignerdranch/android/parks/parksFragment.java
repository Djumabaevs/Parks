package com.bignerdranch.android.parks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.parks.adapter.ParkRecyclerViewAdapter;
import com.bignerdranch.android.parks.model.Park;

import java.util.List;


public class parksFragment extends Fragment {

    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter recyclerViewAdapter;
    private List<Park> parkList;

    public parksFragment() {
        // Required empty public constructor
    }

    public static parksFragment newInstance() {
        parksFragment fragment = new parksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}