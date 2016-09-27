package fi.jamk.asynctaskexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button asyncTaskButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncTaskButton = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void asynctaskButtonClicked(View view)  {
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute(){
            asyncTaskButton.setEnabled(false);
        }

        protected Void doInBackground(Void... params){
            for (int i = 1; i <= 100; i++){
                publishProgress(i);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    Log.e("ASYNCTASKEXAMPLE", "Background thread is interrupted");
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... params){
            progressBar.setProgress(params[0]);
        }

        protected void onPostExecute(Void params){
            asyncTaskButton.setEnabled(true);
        }
    }
}
