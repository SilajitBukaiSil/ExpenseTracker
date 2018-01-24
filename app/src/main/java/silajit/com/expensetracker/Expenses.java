package silajit.com.expensetracker;



public class Expenses {
    int id;
    String expenseList, expenseAddDate;
    double expenseAmount;

    public Expenses(int id, String expenseList, double expenseAmount, String expenseAddDate) {
        this.id = id;
        this.expenseList = expenseList;
        this.expenseAddDate = expenseAddDate;
        this.expenseAmount = expenseAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExpenseList(String expenseList) {
        this.expenseList = expenseList;
    }

    public void setExpenseAddDate(String expenseAddDate) {
        this.expenseAddDate = expenseAddDate;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public int getId() {
        return id;
    }

    public String getExpenseList() {
        return expenseList;
    }

    public String getExpenseAddDate() {
        return expenseAddDate;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }
}

