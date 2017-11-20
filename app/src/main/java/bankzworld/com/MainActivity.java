package bankzworld.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import bankzworld.com.db.PasswordDbHelper;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText mAccountType;
    private TextInputEditText mUserName;
    private TextInputEditText mPassword;
    private PasswordDbHelper passwordDbHelper;

    private String account, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passwordDbHelper = new PasswordDbHelper(this);

        mAccountType = (TextInputEditText) findViewById(R.id.et_account_type);
        mUserName = (TextInputEditText) findViewById(R.id.et_username);
        mPassword = (TextInputEditText) findViewById(R.id.et_password);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPassword();
            }
        });
    }

    public void addPassword() {
        // Add a new developer record
        account = mAccountType.getText().toString();
        username = mUserName.getText().toString();
        password = mPassword.getText().toString();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mAccountType.setError("Fields can't be Empty");
            mUserName.setError("Fields can't be Empty");
            mPassword.setError("Fields can't be Empty");
        } else {
            Boolean result = passwordDbHelper.insertData(account, username, password);
            if (result == true) {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updatePassword() {
        // Add a new developer record
        account = mAccountType.getText().toString();
        username = mUserName.getText().toString();
        password = mPassword.getText().toString();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mAccountType.setError("Fields can't be Empty");
            mUserName.setError("Fields can't be Empty");
            mPassword.setError("Fields can't be Empty");
        } else {
            updateId();
        }
    }

    private void updateId() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(input);
        builder.setCancelable(false);
        builder.setTitle("Update");
        builder.setMessage("Enter the id of the data you wish to update!");
        builder.setPositiveButton("Ok!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String id = input.getText().toString();
                Boolean result = passwordDbHelper.updateData(id, account, username, password);
                if (result == true) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            updatePassword();
            return true;
        }
        if (id == R.id.action_saved) {
            Intent i = new Intent(this, Details.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
