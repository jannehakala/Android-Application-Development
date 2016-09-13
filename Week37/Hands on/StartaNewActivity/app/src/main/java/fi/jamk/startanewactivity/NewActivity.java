package fi.jamk.startanewactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // get data from calling intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("message");

            TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText(message);
        }

    }

    public void closeActivity(View view){
        Intent intent = new Intent();
        intent.putExtra("result", 20);
        setResult(RESULT_OK, intent);
        finish();
    }
}
