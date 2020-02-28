package com.example.listoffilmssequenia.data.ui;


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

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.data.network.RetrofitClient;
import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.data.model.ResponseFilms;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment implements OnClickFilmListener, OnClickGenreListener, com.example.listoffilmssequenia.data.ui.View {

    private static final String TAG = "ListFragment";
    private FilmsAdapter filmsAdapter;
    private GenresAdapter genresAdapter;

    private List<Film> films = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    List<String> genres2 = new ArrayList<>();
    MvpPresenter mvpPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        MvpInteractor mvpInteractor = new MvpInteractor();
        mvpPresenter = new MvpPresenter(this, mvpInteractor);
        mvpPresenter.getListOfFilms();

     //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Фильмы");

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
    public void onClickGenre(int position, boolean isGenreChecked) {
        if(!isGenreChecked){
            filmsAdapter.setFilms(films);
        }else{
            mvpPresenter.onClickGenre(int position, boolean isGenreChecked);
            /*
            List<Film> filmsCurrentGenre = new ArrayList<>();
            for (int i = 0; i < films.size(); i++) {
                for (int j = 0; j < films.get(i).getGenres().size(); j++) {
                    if(films.get(i).getGenres().get(j).equals(genres2.get(position))){
                        filmsCurrentGenre.add(films.get(i));
                    }
                }
            }
            filmsAdapter.setFilms(filmsCurrentGenre);
            */
        }
    }

    @Override
    public void setPressedGenreFilms(List<Film> filmsCurrentGenre) {
        filmsAdapter.setFilms(filmsCurrentGenre);
    }

    @Override
    public void setListOfFilms(List<Film> films) {
        filmsAdapter.setFilms(films);
    }

    @Override
    public void setListOfGenres(List<String> genres2) {
        genresAdapter.setGenres(genres2);
    }

    @Override
    public void setError(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
