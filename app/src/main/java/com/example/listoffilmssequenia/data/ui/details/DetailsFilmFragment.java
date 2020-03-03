package com.example.listoffilmssequenia.data.ui.details;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.data.model.Film;
import com.example.listoffilmssequenia.data.ui.mainactivity.OnBackClickListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.example.listoffilmssequenia.data.ui.mainactivity.MainActivity.BUNDLE_KEY_CLICKED_FILM;

public class DetailsFilmFragment extends Fragment {

    private Film film;
    private View view;

    private TextView textName;
    private TextView textYears;
    private TextView textRating;
    private TextView textDescription;
    private ImageView imageView;

    private OnBackClickListener onBackClickListener;

    private ImageButton toolbarImageButtonBack;

    public DetailsFilmFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_film, container, false);

        getBundle();
        initView();
        setDataIntoView();
        initToolbar();

        return view;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null)
            film = (Film) bundle.getSerializable(BUNDLE_KEY_CLICKED_FILM);
    }

    private void initView() {
        textName = view.findViewById(R.id.film_name);
        textYears = view.findViewById(R.id.film_year);
        textRating = view.findViewById(R.id.film_rating);
        textDescription = view.findViewById(R.id.film_description);
        imageView = view.findViewById(R.id.film_image);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_fragment_details);
        toolbarImageButtonBack = toolbar.findViewById(R.id.toolbar_image_button_back);
        toolbarImageButtonBack.setVisibility(View.VISIBLE);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(film.getName());
    }

    @SuppressLint("SetTextI18n")
    private void setDataIntoView() {

        textName.setText(film.getLocalized_name());
        textYears.setText(Integer.toString(film.getYear()));
        textRating.setText(Float.toString(film.getRating()));
        textDescription.setText(film.getDescription());

        Picasso.with(getActivity())
                .load(film.getImage_url())
                .placeholder(R.mipmap.ic_launcherq)
                .error(R.mipmap.ic_launcherq)
                .into(imageView);
    }

    public void setListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbarImageButtonBack.setVisibility(View.GONE);
        if (onBackClickListener != null) {
            onBackClickListener.onBackClick();
        }
    }
}
