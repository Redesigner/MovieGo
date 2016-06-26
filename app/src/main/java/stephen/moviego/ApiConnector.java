package stephen.moviego;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class getJSON extends AsyncTask<String,Void,JSONObject> {
    private Context mContext = null;
    protected final String tag ="getJSON";

    public getJSON(Context context){//potential memory leak? Look for solution
        mContext = context;
    }
    protected JSONObject doInBackground(String... params) {
        String key = mContext.getString(R.string.api_key);
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String setting = params[0];
        if(!(setting.equals("popular")||setting.equals("top_rated"))){
            setting="popular";
        }
        JSONObject jsonObject = null;
        Log.v("Get_JSon",setting);
        try {
            //try to connect!
            //First we gotta build the Uri though
            Uri apiRequestUri = Uri.parse("http://api.themoviedb.org/3/movie/")
                    .buildUpon()
                    .appendPath(setting)
                    .appendQueryParameter("api_key", key)
                    .build();
            Log.v("Get_JSon_URL",apiRequestUri.toString());
            URL url = new URL(apiRequestUri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            String JSONString = buffer.toString();
            jsonObject = new JSONObject(JSONString);
        } catch (IOException e) {
            Log.e("ApiConnector", "URL was malformed");
            return null;
        } catch (JSONException e) {
            Log.e("ApiConnector", "Failed to create JSON object");
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ApiConnector", "Error closing stream", e);
                    return null;
                }
            }
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}


public class ApiConnector {

    protected final String tag ="ApiConnector";

    public static ArrayList<String> getPosterUris(String setting, Context context){
        getJSON collectData = new getJSON(context);
        ArrayList<String> result = new ArrayList<String>();
        try {
            JSONObject jsonObject = collectData.execute(setting).get();
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                result.add(i,results.getJSONObject(i).getString("poster_path"));
            }
        }catch (JSONException|ExecutionException|InterruptedException e){
            Log.e(tag,e.getMessage());
        }
        return result;
    }

    public static JSONObject getMovieData (String setting, int index, Context context){
        getJSON collectData = new getJSON(context);
        JSONObject result = null;
        try {
            JSONObject jsonObject = collectData.execute(setting).get();
            JSONArray mainArray = jsonObject.getJSONArray("results");
            result = mainArray.getJSONObject(index);
        }catch (JSONException|ExecutionException|InterruptedException e){
            Log.e(tag,e.getMessage());
            return null;
        }
        return result;
    }
}
