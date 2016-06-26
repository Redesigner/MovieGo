package stephen.moviego;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;

public class MovieInformation extends AppCompatActivity {

    private int id;
    private String movietype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        id=getIntent().getIntExtra("Id",0);
        updateData();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.flip_in,R.anim.flip_out);
        super.onResume();
    }

    private void updateData(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        movietype = settings.getString(getString(R.string.list_type_key),"");
        JSONObject movieData = ApiConnector.getMovieData(movietype, id, this);
        try {
            String posterUrl = "http://image.tmdb.org/t/p/w342" + movieData.get("poster_path");
            Log.v("",posterUrl);
            Picasso.with(this).load(posterUrl).into((ImageView) findViewById(R.id.movieThumbnail));
            TextView title = (TextView)findViewById(R.id.movie_title);
            TextView date = (TextView)findViewById(R.id.movie_date);
            TextView synopsis = (TextView)findViewById(R.id.movie_overview);
            RatingBar score = (RatingBar)findViewById(R.id.ratingBar);
            title.setText(movieData.getString("title"));
            date.setText(movieData.getString("release_date"));
            synopsis.setText(movieData.getString("overview"));
            float scoref = Float.parseFloat(movieData.getString("vote_average"))/2;
            score.setRating(scoref);
            Log.v("MovieInformation", scoref+"");

        }catch(JSONException e){
            Log.e("MovieInformation", e.getMessage());
        }
    }

}
