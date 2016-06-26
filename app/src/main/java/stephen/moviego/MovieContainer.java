package stephen.moviego;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import stephen.moviego.ApiConnector;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieContainer extends Fragment{
    private final String tag = "MovieContainerFragment";
    private Activity mActivity;
    private View mView;
    ApiConnector apiConnector = new ApiConnector();
    String movietype = "popular";
    public MovieContainer() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onResume(){
        super.onResume();
        /*String currentMovietype=PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.list_type_key),"");
        if (!(currentMovietype.equals(movietype)))
            refresh(mView);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if(getArguments()!=null)
            movietype = getString(getArguments().getInt("List type"));
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        setMoviePosters(mView);
        return mView;
    }

    public void refresh(View view){
        setMoviePosters(view);
    }

    private void setMoviePosters(View view){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        //movietype = settings.getString(getString(R.string.list_type_key),"");
        Activity mainActivity = mActivity;
        GridView movieThumbnails = (GridView) view.findViewById(R.id.movie_grid);
        ArrayList<String> movieposters = apiConnector.getPosterUris(movietype, getContext());
        ImageAdapter adapter = new ImageAdapter(mainActivity,movieposters);
        Log.e(tag, adapter.toString());
        movieThumbnails.setAdapter(adapter);
    }
}
