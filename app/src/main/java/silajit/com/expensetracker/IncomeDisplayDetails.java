package silajit.com.expensetracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class IncomeDisplayDetails extends AppCompatActivity {
    List<Income> incomeList;
    ListView listView;
    RegistrationDatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_display_details);

        mDatabase = new RegistrationDatabaseHelper(this);

        incomeList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.recyclerViewList);

        loadIncomeDetailsFromDatabase();
    }
    private void loadIncomeDetailsFromDatabase() {
        Cursor cursor = mDatabase.getAllIncome();

        if (cursor.moveToFirst()) {
            do {
                incomeList.add(new Income(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());

            IncomeAdapter adapter = new IncomeAdapter(this, R.layout.income_list_data, incomeList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}
