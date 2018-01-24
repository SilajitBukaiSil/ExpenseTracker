package silajit.com.expensetracker;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpensesActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditTextExpense;
    Spinner mSpinnerList;
    Button mAddExpense;
    Button mViewExpenseList;
    RegistrationDatabaseHelper mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        mDatabase = new RegistrationDatabaseHelper(this);

        mEditTextExpense = (EditText) findViewById(R.id.expenese_editText);
        mSpinnerList = (Spinner) findViewById(R.id.spinnerExpenses);
        mAddExpense = (Button) findViewById(R.id.buttonAddexpenses);
        mViewExpenseList = (Button) findViewById(R.id.display_expense_button);
        mAddExpense.setOnClickListener(this);
        mViewExpenseList.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddexpenses:
                mAddExpenses();
                break;
            case R.id.display_expense_button:
                startActivity(new Intent(this, DisplayExpensesDetailsActivity.class));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mAddExpenses() {
        String expense = mEditTextExpense.getText().toString().trim();
        String list = mSpinnerList.getSelectedItem().toString();


        if (expense.isEmpty()) {
            mEditTextExpense.setError("Expenses amount can't be empty");
            mEditTextExpense.requestFocus();
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        if (mDatabase.addExpenses(list, Double.parseDouble(expense),joiningDate))
            Toast.makeText(this, "Expenses Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add Expenses", Toast.LENGTH_SHORT).show();
    }
}
