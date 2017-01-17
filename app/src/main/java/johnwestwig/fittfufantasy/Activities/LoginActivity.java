package johnwestwig.fittfufantasy.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.R;
import johnwestwig.fittfufantasy.Utilities.StoredSettings;

import static android.R.id.input;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login Activity";

    private EditText emailEditText, passwordEditText, firstNameEditText, lastNameEditText;
    private Button loginButton, registerButton;

    private boolean nameFieldsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /* Setup toolbar */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /* Initialize UI Elements */
        emailEditText = (EditText) findViewById(R.id.login_email_editText);
        passwordEditText = (EditText) findViewById(R.id.login_password_editText);
        firstNameEditText = (EditText) findViewById(R.id.login_first_name_editText);
        lastNameEditText = (EditText) findViewById(R.id.login_last_name_editText);
        loginButton = (Button) findViewById(R.id.login_login_button);
        registerButton = (Button) findViewById(R.id.login_register_button);

        /* Attempt auto-login */
        attemptAutoLogin();

        /* Initialize Event Handlers */
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameFieldsVisible) {
                    register();
                } else {
                    firstNameEditText.setVisibility(View.VISIBLE);
                    lastNameEditText.setVisibility(View.VISIBLE);
                    nameFieldsVisible = true;
                }
            }
        });
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        params.put("email", emailEditText.getText().toString());
        params.put("password", passwordEditText.getText().toString());

        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                StoredSettings storedSettings = new StoredSettings(getApplicationContext());
                try {
                    storedSettings.setToken(response.getString("token"));
                    gotoLeagueActivity();
                } catch (JSONException e) {
                    Log.v(TAG, e.getMessage());
                }
            }
        };
        request.send("/login", Request.Method.POST, params);
    }

    private void register() {
        Map<String, String> params = new HashMap<>();
        params.put("email", emailEditText.getText().toString());
        params.put("password", passwordEditText.getText().toString());
        params.put("firstName", firstNameEditText.getText().toString());
        params.put("lastName", lastNameEditText.getText().toString());

        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {

            }
        };
        request.send("/register", Request.Method.POST, params);
    }

    private void attemptAutoLogin() {
        APIRequest request = new APIRequest(getApplicationContext()) {
            @Override
            public void onCompleted(JSONObject response) {
                gotoLeagueActivity();
            }
        };
        request.send("/api/validateToken", Request.Method.GET, null);
    }

    private void gotoLeagueActivity() {
        Intent intent = new Intent(this, LeagueActivity.class);
        startActivity(intent);
    }
}
