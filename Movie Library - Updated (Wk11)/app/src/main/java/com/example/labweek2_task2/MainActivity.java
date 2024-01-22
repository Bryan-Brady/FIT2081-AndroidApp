package com.example.labweek2_task2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.labweek2_task2.provider.Movie;
import com.example.labweek2_task2.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//Main activity promises to fulfill requirements of NavigationView.OnNavigationItemSelectedListener
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText getMovieView;
    private EditText getYearView;
    private EditText getCountryView;
    private EditText getGenreView;
    private EditText getCostView;
    private EditText getKeywordsView;
    private EditText getCommentsView;

    private TextView getAMView;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav;
    ListView listView;

    FloatingActionButton fab;

    //this is just a Vector in c++, a Dynamic array
    ArrayList<String> listData;
    ArrayAdapter<String> listDataAdapter;


    final String TITLE_KEY = "TITLE";
    final String YEAR_KEY= "YEAR";
    final String COUNTRY_KEY= "COUNTRY";
    final String GENRE_KEY = "GENRE";
    final String COST_KEY= "COST";
    final String KEYWORDS_KEY = "KEYWORDS";
    final String COMMENTS_KEY = "COMMENTS";

    final String AM_KEY = "ADDED MOVIES";
    final String ADDMOVIE = "addMovies";

    String name;
    int year;
    String country;
    String genre;
    int cost;
    String keywords;
    String comments;
    int addedMovies;

    private SharedPreferences sP;

    ArrayList<MovieEditText> rvData = new ArrayList<>();

    private MovieViewModel movieVM; //movie View Model

    private List<Movie> movies;

    private ArrayList<Movie> dbAList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/movies_table");
    DatabaseReference ref2 = database.getReference("/movies_table/BigBudget");

    //week 10
    View motionLayer;
    int iniX;
    int iniY;

    int finalX;
    int finalY;

    int distanceX;
    int distanceY;

    //week 11
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);


        getMovieView = findViewById(R.id.movieName);
        getYearView = findViewById(R.id.year);
        getCountryView = findViewById(R.id.country);
        getGenreView = findViewById(R.id.genre);
        getCostView = findViewById(R.id.cost);
        getKeywordsView = findViewById(R.id.keywords);
        getCommentsView = findViewById(R.id.comment);
        getAMView = findViewById(R.id.moviesAdded);


        sP = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        name = sP.getString(TITLE_KEY, "");
        year = sP.getInt(YEAR_KEY, 0);
        country = sP.getString(COUNTRY_KEY, "");
        genre = sP.getString(GENRE_KEY, "");
        cost = sP.getInt(COST_KEY, 0);
        keywords = sP.getString(KEYWORDS_KEY, "");
        comments = sP.getString(COMMENTS_KEY, "");
        addedMovies = sP.getInt(AM_KEY, 0);


        getMovieView.setText(name);
        getYearView.setText(String.valueOf(year));
        getCountryView.setText(country);
        getGenreView.setText(genre);
        getCostView.setText(String.valueOf(cost));
        getKeywordsView.setText(keywords);
        getCommentsView.setText(comments);
        getAMView.setText(String.valueOf(addedMovies));



        //App requests permission to send, receive and read sms (permission also added in the XML manifest
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        // creates the broadCastReceiver object
        myBroadCastReceiver broadCastReceiver = new myBroadCastReceiver();

        //registers broadCastReceiver object with intent from SMSReceiver class


        //so this like registers broadcastReceiver object (of myBroadCastReceiver class) into the SMSReceiver class to receive broadcast (intent) sent by sms receiver,
        //the SMS_filter is simply just the key to determine which class broadCastReceiver object will take intent from (in this case SMSReceiver.java)
        registerReceiver(broadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        toolbar = findViewById(R.id.toolbar_id);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        nav = findViewById(R.id.nav_id);

        //tells my main activity to use this toolbar
        setSupportActionBar(toolbar);

        //links navigation view with the toolbar (acts like a bridge between drawer and tool layout)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        // "Toggles" drawerLayout so that it uses the Bridge (which was toggle)
        drawerLayout.addDrawerListener(toggle);

        //sync hamburger icon of drawerLayout to Toolbar
        toggle.syncState();


        //create handler for navigation drawer items
        nav.setNavigationItemSelectedListener(this);
        //After adding implement NavigationView.OnNavigationItemSelectedListener in MainActivity,
        //and auto creating the class using alt shift enter,
        //it will auto create class onNavigationItemSelected

        listView = findViewById(R.id.listview_id);

        listData = new ArrayList<>();


        //now we need to link listData array into listView
        //this can be done through an Adapter

        listDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(listDataAdapter);


        fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpMovieEditText();
                addItemToList();
            }
        });
        movieVM = new ViewModelProvider(this).get(MovieViewModel.class);

        movieVM.getMovies().observe(this, newData -> {
            movies = newData;
        });
        Log.d("week7", movies + "");




        //week 10 and 11 (motionLayer)
        motionLayer = findViewById(R.id.motionLayer);

        /*
        motionLayer.setOnTouchListener(new View.OnTouchListener() {
            //Triggers twice unlike button, when click/press and release
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //By default, if onTouch returns true, it will automatically do DOWN MOVE UP.
                //we have added getEventType in order to:
                //1. Differentiate which type of movement (DOWN, MOVE, UP i.e: press screen, move screen, release screen) it is currently doing
                //2. Obtain the initial and final x and y coordinates (because we were able to differentiate when the motion is DOWN, MOVE, UP
                //3. Do specific methods based on the X and Y coordinates (x > 100 || < -100 is horizontal swipe, y > 90 || < -90 is vertical swipe)
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();

                Log.d("week_10","Touch: "+getEventType(motionEvent));
                Log.d("week_10", "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")");

                Log.d("week_10", "Distance Coords: (" + String.valueOf(distanceX) + ", " + String.valueOf(distanceY) + ")");

                if (distanceX > 100 || distanceX < -100){
                    Log.d("week_10", "Horizontal Swipe");
                    if (distanceX > 100) {
                        Log.d("week_10", "Type: Left --> Right");
                        doAddMovieButton();

                    } else if (distanceX < -100){
                        Log.d("week_10", "Type: Right --> Left");
                        Toast.makeText(getApplicationContext(), "Set Fields to Default", Toast.LENGTH_SHORT).show();
                        setFieldsDefault();
                    }

                } else if (distanceY > 90 || distanceY < -90){
                    Log.d("week_10", "Vertical Swipe");
                    clearFields();
                }

                distanceX = 0;
                distanceY = 0;

                //if false, when touched once it wont return again when release
                return true;
            }
        });
         */

        //week 11
        gestureDetector= new GestureDetector(this,new MyWeek11GestureHandlers());
        motionLayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });


    }

    class MyWeek11GestureHandlers extends GestureDetector.SimpleOnGestureListener{

        //Why onSingleTapConfirmed and not onSingleTapUp?
        //Cause onSingleTapConfirmed will only be run if the first tap is confirmed as just a one tap
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("week_11", "onSingleTapConfirmed");
            int incrementCost = getCost() + 150;
            getCostView.setText(String.valueOf(incrementCost));
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("week_11", "onDoubleTap");
            setFieldsDefault();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("week_11", String.valueOf(distanceX));
            int xToInt = (int)distanceX;
            int modifiedYear = getYear() + xToInt;
            getYearView.setText(String.valueOf(modifiedYear));
            Log.d("week_11", "onScroll");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("week_11", "onFling");
            moveTaskToBack(true);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("week_11", "onLongPress");
            clearFields();
        }
    }


public String getEventType(MotionEvent event){
        String eventType = "UNKNOWN";
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                iniX=(int) event.getX();
                iniY=(int) event.getY();
                eventType="DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                eventType="MOVE";
                break;
            case MotionEvent.ACTION_UP:
                finalX=(int) event.getX();
                finalY=(int) event.getY();

                distanceX = finalX - iniX;
                distanceY = finalY - iniY;


                eventType="UP";
                break;

        }
        return eventType;
}


    @Override
    //Creates the option menu
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflates menu
        getMenuInflater().inflate(R.menu.options_menu_list, menu);
        return true;
    }

    @Override
    //When an item in the options is selected, this method will run
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();


        switch(id){
            case R.id.options_menu_clear_all_id:
                clearFields();
                break;
            case R.id.options_menu_lower_case_title_id:
                name = getMovieName().toLowerCase();
                getMovieView.setText(name);
                break;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //closes the navigation layout when its clicked
        //GravityCompat references where closeDrawer closes layout (in this case START)
        //drawerLayout.closeDrawer(GravityCompat.START);

        int id=item.getItemId();

        switch(id){
            case R.id.drawer_layout_add_item_id:
                addItemToList();
                break;
            case R.id.drawer_layout_remove_last_item_id:
                removeLastItem();
                break;
            case R.id.drawer_layout_remove_all_movies_id:
                removeAllItems();
                movieVM.removeAll();
                ref.removeValue();
                break;
            case R.id.drawer_layout_double_costs_id:
                int doubledCost = getCost() * 2;
                getCostView.setText(String.valueOf(doubledCost));
                break;
            case R.id.drawer_layout_list_all_movies_id:

               /*
                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                Log.d("week6.1", rvData + "");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("getrvdatakey", rvData);
                intent.putExtras(bundle);
                startActivity(intent);
                */

                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("getdbalist", dbAList);
                Log.d("week7",bundle + "");

                intent.putExtras(bundle);

                startActivity(intent);

                openRecyclerActivity();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openRecyclerActivity() {
        Intent intent = new Intent(this, RecyclerActivity.class);
                startActivity(intent);
    }

    private void addItemToRecyclerView(){

    }

    private void addItemToList(){
        listData.add(getMovieName() + " | " + getYear());
        listDataAdapter.notifyDataSetChanged();
    }

    private void removeLastItem(){
        if(listData.size()>0){
            listData.remove(listData.size()-1);
            listDataAdapter.notifyDataSetChanged();
        }
    }

    private void removeAllItems(){
        listData.clear();
        listDataAdapter.notifyDataSetChanged();
    }


    class myBroadCastReceiver extends BroadcastReceiver{

        //everytime smsReceiver sends broadcast, this function will be executed
        @Override
        public void onReceive(Context context, Intent intent) {

            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            //converts received string into tokens, and set for ; as separation (like spacebar)
            StringTokenizer sT = new StringTokenizer(msg, ";" );


            String movieName = sT.nextToken();
            String movieYearString = sT.nextToken();
            String movieCountry = sT.nextToken();
            String movieGenre = sT.nextToken();
            String movieCost = sT.nextToken();
            String movieKeywords = sT.nextToken();
            String movieComments = sT.nextToken();
            String duringYearString = sT.nextToken();

            int intMovieYear = Integer.parseInt(movieYearString);
            int intDuringYear = Integer.parseInt(duringYearString);
            int finalYear = intMovieYear + intDuringYear;
            String movieYear = String.valueOf(finalYear);

            getMovieView.setText(movieName);
            getYearView.setText(movieYear);
            getCountryView.setText(movieCountry);
            getGenreView.setText(movieGenre);
            getCostView.setText(movieCost);
            getKeywordsView.setText(movieKeywords);
            getCommentsView.setText(movieComments);

        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Put the entered genre (getGenre()) into the bundle (named outState here)
        genre = getGenre();
        outState.putString(GENRE_KEY, genre.toLowerCase());



    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        super.onRestoreInstanceState(inState);

        //Obtain the saved movie name entered by user from bundle and store it to the name
        name = getMovieName().toUpperCase();
        //Obtain the saved genre entered by user from the bundle and store it to the genre
        genre = inState.getString(GENRE_KEY);




        //Display the tex of title and genre as instructed
        getMovieView.setText(name);

        getGenreView.setText(genre);


    }


    public void doAddMovieButton(){

        addItemToList();

        SharedPreferences.Editor editor = sP.edit();

        editor.putString(TITLE_KEY, getMovieName());
        editor.putInt(YEAR_KEY, getYear());
        editor.putString(COUNTRY_KEY, getCountry());
        editor.putString(GENRE_KEY, getGenre());
        editor.putInt(COST_KEY, getCost());
        editor.putString(KEYWORDS_KEY,getKeywords());
        editor.putString(COMMENTS_KEY, getComments());
        editor.putInt(AM_KEY, incrementAM());

        editor.apply();

        getAMView.setText(String.valueOf(addedMovies));

        Toast.makeText(getApplicationContext(), "Movie: " + getMovieName() + " - Added! :)", Toast.LENGTH_SHORT).show();

        /*

        dataSource.add(getMovieName());
        dataSource.add(String.valueOf(getYear()));
        dataSource.add(getCountry());
        dataSource.add(getGenre());
        dataSource.add(String.valueOf(getCost()));
        dataSource.add(getKeywords());
        dataSource.add(getComments());


         */
        //adapter.notifyDataSetChanged();

        setUpMovieEditText();


        //week 7
        //add stuff into database




        Movie movie = new Movie(getMovieName(), getYear(), getCountry(), getGenre(), getCost(), getKeywords(), getComments());
        //movieVM.insertNewMovies(movie);
        movieVM.insertNewMovies(movie);





        Log.d(ADDMOVIE, "pressed");
        Log.d("week7", movies + "");
        Log.d("week7", movies.size() + "");

        dbAList = new ArrayList<Movie>(movies);



        Log.d("week7",dbAList + "");


        //create reference to firebase to access


        ref.push().setValue(new Movie(getMovieName(), getYear(), getCountry(), getGenre(), getCost(), getKeywords(), getComments()));

        if (getCost() > 40)
            ref2.push().setValue(new Movie(getMovieName(), getYear(), getCountry(), getGenre(), getCost(), getKeywords(), getComments()));


    }


    public void toast(View view){
        doAddMovieButton();
    }



    public void clearAll(View view){
        clearFields();
    }





    public void toast2(View view){
        Toast.makeText(getApplicationContext(),   getMovieName() + "; " + getGenre() + "; " + getComments(), Toast.LENGTH_SHORT).show();
    }

    public String getMovieName(){
        String enteredMovie = getMovieView.getText().toString();
        return enteredMovie;
    }

    public int getYear(){
        int enteredYear = Integer.parseInt(getYearView.getText().toString());
        return enteredYear;
    }
    public String getCountry(){
        String enteredCountry = getCountryView.getText().toString();
        return enteredCountry;
    }

    public String getGenre(){
        String enteredGenre = getGenreView.getText().toString();
        return enteredGenre;
    }

    public int getCost(){
        int enteredCost = Integer.parseInt(getCostView.getText().toString());
        return enteredCost;
    }
    public String getKeywords(){
        String enteredKeywords = getKeywordsView.getText().toString();
        return enteredKeywords;
    }
    public String getComments(){
       String enteredComment = getCommentsView.getText().toString();
       return enteredComment;
    }

    public int incrementAM(){
        addedMovies++;
        return addedMovies;
    }

    public void clearFields(){
        getMovieView.setText("");
        getYearView.setText("");
        getCountryView.setText("");
        getGenreView.setText("");
        getCostView.setText("");
        getKeywordsView.setText("");
        getCommentsView.setText("");
    }

    public void setFieldsDefault(){
        getMovieView.setText("Princess Mononoke");
        getYearView.setText("1997");
        getCountryView.setText("Japan");
        getGenreView.setText("Action");
        getCostView.setText("20");
        getKeywordsView.setText("Anime;Fantasy;Drama");
        getCommentsView.setText("This is the greatest movie of all time");
    }

    public void setUpMovieEditText(){
        String theTitle = getMovieName();
        String theYear = String.valueOf(getYear());
        String theCountry = getCountry();
        String theGenre = getGenre();
        String theCost = String.valueOf(getCost());
        String theKeywords = getKeywords();
        String theComments = getComments();

        rvData.add(new MovieEditText(theTitle, theYear, theCountry, theGenre, theCost, theKeywords, theComments));


    }
}