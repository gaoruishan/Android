package demo.grs.com.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import demo.grs.com.android.R;
import demo.grs.com.android.base.BaseActivity;
import demo.grs.com.android.model.LocationWithWeather;
import demo.grs.com.android.sercive.AMapLocationService;

public class MainActivity extends BaseActivity {

    AMapLocationService.LocationListener mListener = new AMapLocationService.LocationListener() {
        @Override
        public void onReceivedLocation(LocationWithWeather loc) {
            t.setText(loc.address+"  "+loc.weather);
        }

        @Override
        public void onLocationError() {

        }
    };
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.tv);
        AMapLocationService.registerListener(mListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AMapLocationService.unregisterListener(mListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
