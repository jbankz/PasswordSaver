package bankzworld.com;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bankzworld.com.db.PasswordContract;
import bankzworld.com.db.PasswordDbHelper;

public class Details extends AppCompatActivity {

    private PasswordDbHelper passwordDbHelper;
    private TextView mTextView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        passwordDbHelper = new PasswordDbHelper(this);
        mTextView = (TextView) findViewById(R.id.result);
        cursor = passwordDbHelper.getAllData();

        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recieveId();
                return true;
            }
        });

        getData();

    }

    private void recieveId() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(input);
        builder.setCancelable(false);
        builder.setTitle("Delete");
        builder.setMessage("Enter the id of the data you wish to delete!");
        builder.setPositiveButton("Ok!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String id = input.getText().toString();
                passwordDbHelper.deleteItem(id);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void getData() {
        cursor = passwordDbHelper.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                stringBuffer.append("Id: " + cursor.getString(0) + "\n");
                stringBuffer.append("Type: " + cursor.getString(1) + "\n");
                stringBuffer.append("Username: " + cursor.getString(2) + "\n");
                stringBuffer.append("Password: " + cursor.getString(3) + "\n" + "\n");
            }
            mTextView.setText(stringBuffer.toString());
        } else {
            Toast.makeText(this, "No Data to Retrieve", Toast.LENGTH_SHORT).show();
        }
    }

}
