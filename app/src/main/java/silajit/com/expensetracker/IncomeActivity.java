package silajit.com.expensetracker;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class IncomeActivity extends  AppCompatActivity implements View.OnClickListener {

    EditText mEditTextSalary;
    Spinner mSpinnerSource;
    Button mAddIncomeDetails ,mViewList;
    RegistrationDatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        mDatabase = new RegistrationDatabaseHelper(this);

        mEditTextSalary = (EditText) findViewById(R.id.editTextIncome_salary);
        mSpinnerSource = (Spinner) findViewById(R.id.spinnerIncomeSource);
        mAddIncomeDetails = (Button) findViewById(R.id.buttonAddIncome);
        mViewList = (Button) findViewById(R.id.buttonViewAllIncomes);
        mAddIncomeDetails.setOnClickListener(this);
        mViewList.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddIncome:
                mAddIncome();
                break;
            case R.id.buttonViewAllIncomes:
                startActivity(new Intent(this, IncomeDisplayDetails.class));
                break;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mAddIncome() {

        String source = mSpinnerSource.getSelectedItem().toString();
        String salary = mEditTextSalary.getText().toString().trim();


        if (salary.isEmpty()) {
            mEditTextSalary.setError("Salary can't be empty");
            mEditTextSalary.requestFocus();
            return;
        }


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        if (mDatabase.addIncome(source, Double.parseDouble(salary),joiningDate))
            Toast.makeText(this, "Income Details Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add Income", Toast.LENGTH_SHORT).show();
    }

}

