package com.abanoub.unit.githubreposearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
