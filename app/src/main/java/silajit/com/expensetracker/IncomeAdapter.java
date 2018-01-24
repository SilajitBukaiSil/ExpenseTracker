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


public class IncomeAdapter extends ArrayAdapter<Income> {

    Context mCtx;
    int layoutRes;
    List<Income> incomeList;
  RegistrationDatabaseHelper mDatabase;

    public IncomeAdapter(Context mCtx, int layoutRes, List<Income> incomeList,RegistrationDatabaseHelper mDatabase) {
        super(mCtx, layoutRes, incomeList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.incomeList = incomeList;
        this.mDatabase = mDatabase;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewSource = view.findViewById(R.id.textViewIncomeSource);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoinDate = view.findViewById(R.id.textViewJoiningDate);

        final Income income = incomeList.get(position);

        textViewSource.setText(income.getSource());
        textViewSalary.setText(String.valueOf(income.getSalary()));
        textViewJoinDate.setText(income.getJoiningDate());

        view.findViewById(R.id.buttonDeleteIncomeSource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIncomeDetails(income);
            }
        });

        view.findViewById(R.id.buttonEditIncomeSource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIncomeDetails(income);
            }
        });

        return view;
    }

    private void updateIncomeDetails(final Income income) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.income_edit_details, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final Spinner spinner = view.findViewById(R.id.spinnerIncomeSource);
        final EditText editTextSalary = view.findViewById(R.id.editTextIncome_salary);


        editTextSalary.setText(String.valueOf(income.getSalary()));


        view.findViewById(R.id.buttonUpdateIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String salary = editTextSalary.getText().toString().trim();
                String source = spinner.getSelectedItem().toString().trim();


                if (salary.isEmpty()) {
                    editTextSalary.setError("Salary can't be empty");
                    editTextSalary.requestFocus();
                    return;
                }


                if (mDatabase.updateIncome(income.getId(), source, Double.valueOf(salary))) {
                    Toast.makeText(mCtx, "Income Details Updated", Toast.LENGTH_SHORT).show();
                    loadIncomeDetailsFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteIncomeDetails(final Income income) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mDatabase.deleteIncome(income.getId()))
                    loadIncomeDetailsFromDatabaseAgain();
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

    private void loadIncomeDetailsFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllIncome();

        incomeList.clear();
        if (cursor.moveToFirst()) {
            do {
                incomeList.add(new Income(
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


