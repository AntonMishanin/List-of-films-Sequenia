package com.example.listoffilmssequenia;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {

    private Film film;

    public DetailsFragment(Film film) {
        this.film = film;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView textName = view.findViewById(R.id.film_name);
        TextView textYears = view.findViewById(R.id.film_year);
        TextView textRating = view.findViewById(R.id.film_rating);
        TextView textDescription = view.findViewById(R.id.film_description);
        ImageView imageView = view.findViewById(R.id.film_image);
        textName.setText(film.getName());
        textYears.setText(film.getYear()+"");
        textRating.setText(film.getRating()+"");
        textDescription.setText(film.getDescription());

        Picasso.with(getActivity())
                .load(film.getImage_url())
                .into(imageView);

        return view;
    }

}
