package vunt.com.vn.threadasynctaskhandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_gallery);
        recyclerView.setHasFixedSize(true);
        List<String> pathFiles =
                (List<String>) getIntent().getSerializableExtra(SplashActivity.EXTRA_IMAGE);
        MyAdapter adapter = new MyAdapter(pathFiles);
        recyclerView.setAdapter(adapter);
    }
}
