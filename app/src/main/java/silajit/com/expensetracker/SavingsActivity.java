package silajit.com.expensetracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

public class SavingsActivity extends AppCompatActivity {
    GraphView mBarGraph,mLineGraph,mPointGraph;
    TextView totalIncome,totalExpenses,totalSavings;
    Button mSavings;
    Double income,expenses,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        totalIncome=(TextView)findViewById(R.id.totalIncomeView);
        totalExpenses=(TextView)findViewById(R.id.totalExpensesView);
        totalSavings=(TextView)findViewById(R.id.totalSavingView);

        Bundle b=getIntent().getExtras();
        totalIncome.setText(b.getCharSequence("amount"));
        totalExpenses.setText(b.getCharSequence("expenses"));


        mSavings=(Button)findViewById(R.id.savingsButton);
        mSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = Double.parseDouble(totalIncome.getText().toString());
                expenses = Double.parseDouble(totalExpenses.getText().toString());
                total = income-expenses;
                totalSavings.setText(Double.toString(total));



            }
        });


//        mBarGraph=(GraphView)findViewById(R.id.BarGraph);
//        mLineGraph=(GraphView)findViewById(R.id.Linegraph);
//        mPointGraph=(GraphView)findViewById(R.id.Pointgraph);
//        BarGraphSeries<DataPoint> barseries=new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, -2),
//                new DataPoint(1, 7),
//                new DataPoint(2,4),
//                new DataPoint(3,5),
//                new DataPoint(5,2),
//                new DataPoint(4,8)
//        });
//        mBarGraph.addSeries(barseries);
//        barseries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int)data.getX()*255/4,(int)Math.abs(data.getY()*255/6),100);
//            }
//        });
//        barseries.setSpacing(50);
//        barseries.setDrawValuesOnTop(true);
//        barseries.setValuesOnTopColor(Color.BLUE);
//        barseries.setValuesOnTopSize(40);
//
//        barseries.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(SavingsActivity.this,"On Data Point Clicked;"+dataPoint,Toast.LENGTH_LONG).show();
//            }
//        });
//        LineGraphSeries<DataPoint> lineseries=new LineGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, -2),
//                new DataPoint(1, 7),
//                new DataPoint(2,4),
//                new DataPoint(3,5),
//                new DataPoint(5,2),
//                new DataPoint(4,8)
//        });
//
//
//        mLineGraph.addSeries(lineseries);
//        lineseries.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(SavingsActivity.this,"On Data Point Clicked;"+dataPoint,Toast.LENGTH_LONG).show();
//            }
//        });
//
//        PointsGraphSeries<DataPoint> pointseries = new PointsGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, -2),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        mPointGraph.addSeries(pointseries);
//        pointseries.setShape(PointsGraphSeries.Shape.POINT);
//
//        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, -1),
//                new DataPoint(1, 4),
//                new DataPoint(2, 2),
//                new DataPoint(3, 1),
//                new DataPoint(4, 5)
//        });
//        mPointGraph.addSeries(series2);
//        series2.setShape(PointsGraphSeries.Shape.RECTANGLE);
//        series2.setColor(Color.RED);
//
//        PointsGraphSeries<DataPoint> series3 = new PointsGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 0),
//                new DataPoint(1, 3),
//                new DataPoint(2, 1),
//                new DataPoint(3, 0),
//                new DataPoint(4, 4)
//        });
//        mPointGraph.addSeries(series3);
//        series3.setShape(PointsGraphSeries.Shape.TRIANGLE);
//        series3.setColor(Color.YELLOW);
//
//        PointsGraphSeries<DataPoint> series4 = new PointsGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 2),
//                new DataPoint(2, 0),
//                new DataPoint(3, -1),
//                new DataPoint(4, 3)
//        });
//        mPointGraph.addSeries(series4);
//        series4.setColor(Color.GREEN);
//        series4.setCustomShape(new PointsGraphSeries.CustomShape() {
//            @Override
//            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
//                paint.setStrokeWidth(10);
//                canvas.drawLine(x-20, y-20, x+20, y+20, paint);
//                canvas.drawLine(x+20, y-20, x-20, y+20, paint);
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_email:
                Intent mEmailIntent=new Intent(Intent.ACTION_SENDTO);
                mEmailIntent.setData(Uri.parse("mailto:"));
                startActivity(mEmailIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
