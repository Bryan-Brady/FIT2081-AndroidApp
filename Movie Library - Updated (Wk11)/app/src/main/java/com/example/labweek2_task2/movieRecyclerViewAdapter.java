package com.example.labweek2_task2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labweek2_task2.provider.Movie;

import java.util.ArrayList;

public class movieRecyclerViewAdapter extends RecyclerView.Adapter<movieRecyclerViewAdapter.MyViewHolder>  {
    ArrayList<Movie> getData;



    public movieRecyclerViewAdapter(ArrayList<Movie> getData) {
        this.getData = getData;
    }

    @NonNull
    @Override
    public movieRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Create an instance of layout so V becomes card
        // v now contains the text views of card layout
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_layout, parent, false);

        //Acts as a container for my text views
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    //Binds data inside view holder
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.movieText.setText("Movie: ");
        holder.movieYear.setText("Year: ");
        holder.movieCountry.setText("Country: ");
        holder.movieGenre.setText("Genre: ");
        holder.movieCost.setText("Cost: ");
        holder.movieKeywords.setText("Keywords: ");
        holder.movieComments.setText("Comments: ");
        holder.movieCostInUsd.setText("Cost in USD: ");



        holder.getMovieCV.setText(getData.get(position).getName());
        holder.getYearCV.setText(String.valueOf(getData.get(position).getYear()));
        holder.getCountryCV.setText(getData.get(position).getCountry());
        holder.getGenreCV.setText(getData.get(position).getGenre());
        holder.getCostCV.setText(String.valueOf(getData.get(position).getCost()));
        holder.getKeywordsCV.setText(getData.get(position).getKeywords());
        holder.getCommentsCV.setText(getData.get(position).getComments());

        //int ausCost = Integer.parseInt(getData.get(position).getCost());
        int ausCost = getData.get(position).getCost();
        double usdCost = ausCost * 0.75;

        holder.getMovieCostInUsd.setText(String.valueOf(usdCost));
    }

    @Override
    public int getItemCount() {
        return getData.size();

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView movieText;
        TextView movieYear;
        TextView movieCountry;
        TextView movieGenre;
        TextView movieCost;
        TextView movieKeywords;
        TextView movieComments;
        TextView movieCostInUsd;


        TextView getMovieCV;
        TextView getYearCV;
        TextView getCountryCV;
        TextView getGenreCV;
        TextView getCostCV;
        TextView getKeywordsCV;
        TextView getCommentsCV;
        TextView getMovieCostInUsd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movieText = itemView.findViewById(R.id.movieText);
            movieYear = itemView.findViewById(R.id.yearText);
            movieCountry = itemView.findViewById(R.id.countryText);
            movieGenre = itemView.findViewById(R.id.genreText);
            movieCost = itemView.findViewById(R.id.costText);
            movieKeywords = itemView.findViewById(R.id.keywordsText);
            movieComments = itemView.findViewById(R.id.commentsText);
            movieCostInUsd = itemView.findViewById(R.id.costInUsdText);




            getMovieCV = itemView.findViewById(R.id.cv_movie_id);
            getYearCV = itemView.findViewById(R.id.cv_year_id);
            getCountryCV = itemView.findViewById(R.id.cv_country_id);
            getGenreCV = itemView.findViewById(R.id.cv_genre_id);
            getCostCV = itemView.findViewById(R.id.cv_cost_id);
            getKeywordsCV = itemView.findViewById(R.id.cv_keywords_id);
            getCommentsCV = itemView.findViewById(R.id.cv_comments_id);
            getMovieCostInUsd = itemView.findViewById(R.id.cv_cost_in_usd);

        }
    }

}
