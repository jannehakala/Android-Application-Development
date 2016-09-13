package fi.jamk.startanewactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int NEW_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivity(View view){
        //Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();

        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }

    public void startActivityWithResult(View view){
        Intent intent = new Intent(this, NewActivity.class);
        startActivityForResult(intent, NEW_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == NEW_ACTIVITY && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            int result = extras.getInt("result");
            Toast.makeText(this, "result = " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
