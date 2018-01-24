package silajit.com.expensetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayExpensesDetailsActivity extends AppCompatActivity {
    List<Expenses> expensesList;
    ListView listView;

   RegistrationDatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expenses_details);

        mDatabase = new RegistrationDatabaseHelper(this);

        expensesList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.recyclerViewList);

        loadExpensesFromDatabase();
    }

    private void loadExpensesFromDatabase() {
        Cursor cursor = mDatabase.getAllExpenses();

        if (cursor.moveToFirst()) {
            do {
                expensesList.add(new Expenses(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());

            ExpensesAdapter adapter = new ExpensesAdapter(this, R.layout.expenses_list_data, expensesList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}
