package fi.jamk.basicui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BasicUI2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_ui2);
        AutoCompleteTextView actv = (AutoCompleteTextView)
                findViewById(R.id.login); // add stings to control
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new String[]
                        {"Pasi","Juha","Kari","Jouni","Esa","Hannu"});
        actv.setAdapter(aa);
    }

    public void buttonLoginClicked(View view){
        AutoCompleteTextView loginText = (AutoCompleteTextView) findViewById(R.id.login);
        EditText passwordText = (EditText) findViewById(R.id.password);

        String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        Toast.makeText(getApplicationContext(), login + " " + password, Toast.LENGTH_SHORT).show();
    }
}
