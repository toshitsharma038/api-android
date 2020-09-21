package com.example.videoapp.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoapp.DetailsActivity;
import com.example.videoapp.OnItemClick;
import com.example.videoapp.R;
import com.example.videoapp.adaptors.GridSpacingItemDecoration;
import com.example.videoapp.adaptors.MoviePageListAdaptor;
import com.example.videoapp.models.Result;
import com.example.videoapp.viewmodels.MainViewModel;
import com.example.videoapp.viewmodels.MainViewModelFactory;


public class PopolarVideoFragment extends Fragment implements OnItemClick {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MoviePageListAdaptor adaptor;
    private MainViewModel viewModel;
    private String sort_criteria = "popular";
    private GridLayoutManager gridLayoutManager;
    private Intent intent;


    public PopolarVideoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_popolar_video, container, false);

        viewModel = ViewModelProviders.of(this,new MainViewModelFactory(sort_criteria))
                .get(MainViewModel.class);

        recyclerView = (RecyclerView)view.findViewById(R.id.pr);
        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        }else {
            gridLayoutManager = new GridLayoutManager(getActivity(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,18,false));
        }

        adaptor = new MoviePageListAdaptor(this);

        recyclerView.setAdapter(adaptor);

        viewModel.geLlistLiveData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                if (results!=null){
                    adaptor.submitList(results);
                }
            }
        });

        return view;
    }

    @Override
    public void OnMovieItemClick(Result movie) {

       intent = new Intent(getContext(), DetailsActivity.class);
       startActivity(intent);

    }
}
