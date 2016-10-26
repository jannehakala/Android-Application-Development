package fi.jamk.sgkygolfcourses;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements Response{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Golfcourse> mGolfCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        FetchDataTask task = new FetchDataTask();
        task.response = this;
        task.execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");

        mRecyclerView = (RecyclerView) findViewById(R.id.golfCourseRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("SGKY - Kentät");

    }

    @Override
    public void onJSONparserComplete(List<Golfcourse> golfCourseList){
        this.mGolfCourseList = golfCourseList;
        mAdapter = new GolfcourseAdapter(this, mGolfCourseList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
