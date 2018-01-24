package silajit.com.expensetracker;



public class Income {

        int id;
        String  source, joiningDate;
        double salary;

    public Income(int id, String source, double salary, String joiningDate) {
        this.id = id;
        this.source = source;
        this.salary = salary;
        this.joiningDate = joiningDate;


    }


    public int getId() {
        return id;
    }


    public String getSource() {
        return source;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public double getSalary() {
        return salary;
    }



    public void setSource(String source) {
        this.source = source;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }



    public void setSalary(double salary) {
        this.salary = salary;
    }
}

