package com.example.listoffilmssequenia;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ListFragment extends Fragment implements OnClickFilmListener, OnClickGenreListener {

    private static final String TAG = "ListFragment";
    private FilmsAdapter filmsAdapter;
    private GenresAdapter genresAdapter;

    private List<Film> films = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        filmsAdapter = new FilmsAdapter(this);
        genresAdapter = new GenresAdapter(this);

        RecyclerView recyclerViewFilm = view.findViewById(R.id.recycler_view_films);
        recyclerViewFilm.setHasFixedSize(true);
        recyclerViewFilm.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewFilm.setAdapter(filmsAdapter);


        RecyclerView recyclerViewGenre = view.findViewById(R.id.recycler_view_genres);
        recyclerViewGenre.setHasFixedSize(true);
        recyclerViewGenre.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewGenre.setAdapter(genresAdapter);

        RetrofitClient.getApi().getListOfFilms().enqueue(new Callback<ResponseFilms>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFilms> call, @NonNull Response<ResponseFilms> response) {
                ResponseFilms responseFilms = response.body();
                films.addAll(responseFilms.getFilms());
                filmsAdapter.setFilms(responseFilms.getFilms());

                Log.d("ListFragment", "" + films.size());
                for (int i = 0; i < films.size(); i++) {
                    genres.addAll(films.get(i).getGenres());
                }

                genresAdapter.setGenres(genres);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFilms> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return view;
    }


    @Override
    public void onClickFilm(int position) {
        DetailsFragment detailsFragment = new DetailsFragment(films.get(position));
        //  Bundle bundle = new Bundle();
        // bundle.putString("source", source);
        //  bundle.putString("imageUrl", imageUrl);
        // recipeDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_container, detailsFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onClickGenre(int position) {

    }
}
