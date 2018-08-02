package com.abanoub.unit.githubreposearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    /** TextView for display the error message if loading data is failed */
    private TextView mErrorMessageDisplay;

    /** ProgressBar for display an indicator while data is display */
    private ProgressBar mLoadingIndicator;

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

        /* reference by id to error message TextView xml */
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        /* reference by id to progress bar xml */
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView(){
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage(){
        // First, hide the currently visible data
        mSearchResultsTextView.setVisibility(View.INVISIBLE);

        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
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
        protected void onPreExecute() {
            super.onPreExecute();
            // show the indicator while the data is fetching
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = null;
            try {
                // fetching the data from the internet and extract it from JSON
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "An error while getting the JSON data", e);
            }
            // return the result data
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Hide the indicator when the data is fetch
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                //display the mSearchResultsTextView to display the data
                showJsonDataView();

                // adding the data to text view to show it
                mSearchResultsTextView.setText(s);
            }else
                // if the data is null display the error message
                showErrorMessage();
        }
    }
}
