package com.example.labweek2_task2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.labweek2_task2.provider.Movie;
import com.example.labweek2_task2.provider.MovieViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity  {

    //ArrayList<MovieEditText> dataSource = new ArrayList<>();
    ArrayList<Movie> dataSourceDB = new ArrayList<>();

    movieRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        //Log.d("week7", movies.size() + "");



        //dataSource = this.getIntent().getExtras().getParcelableArrayList("getrvdatakey");

        dataSourceDB = this.getIntent().getExtras().getParcelableArrayList("getdbalist");

        Log.d("week7", dataSourceDB + "");

        recyclerView = findViewById(R.id.recycler_layout_id);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new movieRecyclerViewAdapter(dataSourceDB);
        recyclerView.setAdapter(adapter);


    }

}