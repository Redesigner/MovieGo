package stephen.moviego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=null){
                return;
            }
            MovieContainer moviesList = new MovieContainer();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, moviesList).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setTabs();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTabs(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("left");
        spec.setContent(R.id.tabContainer1);
        spec.setIndicator("Top Rated");
        tabHost.addTab(spec);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("right");
        spec2.setContent(R.id.tabContainer2);
        spec2.setIndicator("Popular");
        tabHost.addTab(spec2);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                panView(tabId);
            }
        });
    }

    private void panView(String direction){
        Bundle settings = new Bundle();
        if(direction.equals("right")){
            settings.putInt("List type", R.string.list_type_top_rated);
            MovieContainer fragment = new MovieContainer();
            fragment.setArguments(settings);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.righttranslate_in,R.anim.righttranslate_out);
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if(direction.equals("left")){
            settings.putInt("List type", R.string.list_type_popular);
            MovieContainer fragment = new MovieContainer();
            fragment.setArguments(settings);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.lefttranslate_in,R.anim.lefttranslate_out);
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
