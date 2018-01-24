package silajit.com.expensetracker;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private ScrollView scrollView;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private Button buttonLogin;
    private Button buttonRegistration;

//    private AppCompatTextView textViewLinkForgetPassword;
//    private AppCompatTextView textViewLinkHelp;

    private InputValidation inputValidation;
    private RegistrationDatabaseHelper registrationDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.nestedScrollView);


        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        buttonLogin = (Button) findViewById(R.id.appCompatButtonLogin);
        buttonRegistration=(Button)findViewById(R.id.appCompatButtonRegistration);

//        textViewLinkForgetPassword = (AppCompatTextView) findViewById(R.id.textViewLinkForgetPassword);
//        textViewLinkHelp = (AppCompatTextView) findViewById(R.id.textViewLinkHelp);
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        buttonRegistration.setOnClickListener(this);
//        textViewLinkForgetPassword.setOnClickListener(this);
//        textViewLinkHelp.setOnClickListener(this);
    }
    private void initObjects() {
        registrationDatabaseHelper = new RegistrationDatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;

            // Navigate to RegistrationActivity
            case R.id.appCompatButtonRegistration:
                Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intentRegister);
                break;

//            case R.id.textViewLinkForgetPassword:
//                Intent intentForgetPassword=new Intent(getApplicationContext(),ForgetPasswordActivity.class);
//                intentForgetPassword.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
//                emptyInputEditText();
//                startActivity(intentForgetPassword);
//                break;
//            case R.id.textViewLinkHelp:
//                Intent intentHelp = new Intent(getApplicationContext(), HelpActivity.class);
//                startActivity(intentHelp);
//                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail,  getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword,  getString(R.string.error_message_password_text))) {
            return;
        }

        if (registrationDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim(),
                                            textInputEditTextPassword.getText().toString().trim())) {

                Intent accountsIntent = new Intent(LoginActivity.this, SignInActivity.class);
                Bundle b=new Bundle();
                b.putString("mail", textInputEditTextEmail.getText().toString());
                accountsIntent.putExtras(b);
                startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(scrollView, R.string.error_valid_email_password_text, Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


}

