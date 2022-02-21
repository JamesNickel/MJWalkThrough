package com.myhome.mjwalkthrough;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_APP_WALK_THROUGH_IS_VISITED = "app_walk_through_is_visited";
    private TextView txtFirst, txtSecond;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txtFirst = findViewById(R.id.txt_first);
        txtSecond = findViewById(R.id.txt_second);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        showWalkThrough();
    }

    void showWalkThrough(){
        boolean dialogVisited = preferences.getBoolean(KEY_APP_WALK_THROUGH_IS_VISITED, false);
        if(!dialogVisited){
            List<ItemHollowView> viewList = new ArrayList<>();
            viewList.add(new ItemHollowView(
                    txtFirst,
                    "First title",
                    "First description"
            ));
            viewList.add(new ItemHollowView(
                    txtSecond,
                    "Second title",
                    "Second description"
            ));
            new HollowViewManager(this)
                    .setViewList(viewList)
                    .setListener(() -> {
                        //preferences.edit().putBoolean(KEY_APP_WALK_THROUGH_IS_VISITED, true).apply();
                    })
                    .start();
        }
    }
}