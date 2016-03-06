package org.marek.retrofit2usage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.marek.retrofit2usage.rest.RestClient;
import org.marek.retrofit2usage.rest.response.TransportListModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //RETROFIT CALL
                Call<TransportListModel<String>> request = RestClient.getService().getSuppliers("");
                //Response<TransportListModel<String>> response = null;

                    //ASYNC CALL
                    request.enqueue(new Callback<TransportListModel<String>>() {
                        @Override
                        public void onResponse(Call<TransportListModel<String>> call, Response<TransportListModel<String>> response) {
                            Log.d(TAG, "SUCCESS");
                            Log.d(TAG, response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<TransportListModel<String>> call, Throwable t) {
                            Log.d(TAG, "FAILURE:"+t.getMessage());
                        }
                    });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
