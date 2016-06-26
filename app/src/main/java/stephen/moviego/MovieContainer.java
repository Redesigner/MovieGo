package stephen.moviego;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MovieContainer extends Fragment{

    protected Activity mActivity;
    protected View mView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if(getArguments()!=null)
            movietype = getString(getArguments().getInt("List type"));
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        setMoviePosters(mView);
        return mView;
    }

    private void setMoviePosters(View view){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Activity mainActivity = mActivity;
        GridView movieThumbnails = (GridView) view.findViewById(R.id.movie_grid);
        ArrayList<String> movieposters = apiConnector.getPosterUris(movietype, getContext());
        ImageAdapter adapter = new ImageAdapter(mainActivity,movieposters);
        String tag = "MovieContainerFragment";
        Log.e(tag, adapter.toString());
        movieThumbnails.setAdapter(adapter);
    }
}
