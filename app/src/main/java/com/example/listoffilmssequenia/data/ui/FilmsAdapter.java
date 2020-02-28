package com.example.listoffilmssequenia.data.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.data.model.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.Holder> {

    List<Film> films = new ArrayList<>();
    OnClickFilmListener onClickFilmListener;

    public FilmsAdapter(OnClickFilmListener onClickFilmListener) {
        this.onClickFilmListener = onClickFilmListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    public void setFilms(List<Film> films) {
        this.films.clear();
        this.films.addAll(films);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView filmsName;
        ImageView filmsImage;

        public Holder(@NonNull View itemView) {
            super(itemView);

            filmsName = itemView.findViewById(R.id.films_name);
            filmsImage = itemView.findViewById(R.id.films_image);
        }

        public void bind(final int position) {

            filmsName.setText(films.get(position).getName());

            Picasso.with(itemView.getContext())
                    .load(films.get(position).getImage_url())
                    .into(filmsImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickFilmListener.onClickFilm(position);
                }
            });
        }
    }
}
