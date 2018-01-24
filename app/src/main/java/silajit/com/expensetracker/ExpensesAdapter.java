package silajit.com.expensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class ExpensesAdapter extends ArrayAdapter<Expenses> {

    Context mCtx;
    int layoutRes;
    List<Expenses> expensesList;
    RegistrationDatabaseHelper mDatabase;

    public ExpensesAdapter(Context mCtx, int layoutRes, List<Expenses> expensesList, RegistrationDatabaseHelper mDatabase) {
        super(mCtx, layoutRes, expensesList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.expensesList = expensesList;
        this.mDatabase = mDatabase;


    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewExpense = view.findViewById(R.id.textViewExpenses);
        TextView textViewAmount = view.findViewById(R.id.textViewAmount);
        TextView textViewAdedDate = view.findViewById(R.id.textViewAddedDate);

        final Expenses expenses = expensesList.get(position);

        textViewExpense.setText(expenses.getExpenseList());
        textViewAmount.setText(String.valueOf(expenses.getExpenseAmount()));
        textViewAdedDate.setText(expenses.getExpenseAddDate());

        view.findViewById(R.id.buttonDeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExpenses(expenses);
            }
        });

        view.findViewById(R.id.buttonEditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateExpenses(expenses);
            }
        });

        return view;
    }

    private void updateExpenses(final Expenses expenses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.edit_expense_details, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextAmount = view.findViewById(R.id.editTextExpenses);
        final Spinner spinner = view.findViewById(R.id.edit_expenses_spinner);

        editTextAmount.setText(String.valueOf(expenses.getExpenseAmount()));


        view.findViewById(R.id.buttonUpdateExpenses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String list = spinner.getSelectedItem().toString().trim();
                String amount = editTextAmount.getText().toString().trim();


                if (amount.isEmpty()) {
                    editTextAmount.setError("Expenses can't be empty");
                    editTextAmount.requestFocus();
                    return;
                }

                if (mDatabase.updateExpenses(expenses.getId(),list, Double.valueOf(amount))) {
                    Toast.makeText(mCtx, "Expenses Updated", Toast.LENGTH_SHORT).show();
                    loadExpensesFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteExpenses(final Expenses expenses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mDatabase.deleteExpenses(expenses.getId()))
                    loadExpensesFromDatabaseAgain();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadExpensesFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllExpenses();

        expensesList.clear();
        if (cursor.moveToFirst()) {
            do {
                expensesList.add(new Expenses(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}

