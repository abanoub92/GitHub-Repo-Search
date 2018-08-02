package com.abanoub.unit.githubreposearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abanoub.unit.githubreposearch.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** Input text for search of repo */
    private EditText mSearchBoxEditText;

    /** TextView for display the search URL */
    private TextView mUrlDisplayTextView;

    /** TextView for display the search result */
    private TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* reference by id to search EditText view xml */
        mSearchBoxEditText = findViewById(R.id.et_search_box);

        /* reference by id to url display TextView xml */
        mUrlDisplayTextView = findViewById(R.id.tv_url_display);

        /* reference by id to search result TextView xml */
        mSearchResultsTextView = findViewById(R.id.tv_github_search_results_json);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                makeGithubSearchQuery();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method retrieves the search text from the EditText, constructs
     * the URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our (not yet created) {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery(){
        String query = mSearchBoxEditText.getText().toString();
        URL githubQuery = NetworkUtils.buildUrl(query);
        mUrlDisplayTextView.setText(githubQuery.toString());
        new GithubQueryTask().execute(githubQuery);
    }


    private class GithubQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = "";
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "An error while getting the JSON data", e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null)
                mSearchResultsTextView.setText(s);
        }
    }
}
