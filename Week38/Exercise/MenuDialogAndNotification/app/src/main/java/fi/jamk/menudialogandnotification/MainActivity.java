package fi.jamk.menudialogandnotification;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int notification_id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_music:

                Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                actionIntent.setData(Uri.parse("http://www.spotify.fi"));

                PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);
                Notification notification  = new Notification.Builder(this)
                        .setContentTitle("Music notification")
                        .setContentText("This is awesome music notification")
                        .setSmallIcon(R.drawable.music)
                        .setContentIntent(actionPendingIntent)
                        .setAutoCancel(true).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(notification_id, notification);
                return true;
            case R.id.action_quit:
                ExitDialogFragment eDialog = new ExitDialogFragment();
                eDialog.show(getFragmentManager(), "exit");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static class ExitDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class to create a Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_exit)
                    .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Close application
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
