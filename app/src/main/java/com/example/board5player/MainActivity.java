package com.example.board5player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    View view;
    int top;
    private ConstraintLayout constraintLayout;

    int left;
    int[] location;
    ConstraintLayout.LayoutParams layoutParams;
    private HashMap<Integer,int[]> map;
    private EditText angle,degree;
    private Button change;
    boolean called = false;
    private double base;
    private double perpendicular;
    private int centerY;
    private int centerX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        view = findViewById(R.id.view);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int centerX = metrics.widthPixels;
        int centerY = metrics.heightPixels;

        angle = findViewById(R.id.angle);
        degree = findViewById(R.id.angleDegree);
        change = findViewById(R.id.reflect);

        map = new HashMap<>();
        constraintLayout = findViewById(R.id.constraint_layout);

        layoutParams = new ConstraintLayout.LayoutParams(
                4,4);


        location = new int[2];
        imageView.getLocationInWindow(location);

        base();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!angle.getText().toString().trim().isEmpty() && !degree.getText().toString().trim().isEmpty()){
                    System.out.println(angle.getText().toString());
                    System.out.println(degree.getText().toString());
                    change();
                }
            }
        });
    }

    int baseX = 0;
    int baseY = 0;
    double cellWidth = 0;

    private void base(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int imageHeight = imageView.getHeight();
                int imageWidth = imageView.getWidth();

                int boardTop = location[1];
                int boardLeft = location[0];


                centerX =  boardLeft + imageWidth/2;
                centerY =  boardTop + imageHeight/2;


                perpendicular = imageHeight * (12.5410/100);
                base = 2 * perpendicular * Math.tan(Math.toRadians(36));


                cellWidth = base/3 ;

                baseX = (int) (centerX - base/2);
                baseY = (int) (centerY + perpendicular);

                createView(baseX , baseY);


                int key = 1;
                        for(int i =0; i<3; i++) {

                            int x = (int) (baseX + (cellWidth*i) + cellWidth/2);

                            for(int j = 0; j<6;j++){

                                int y = (int) (baseY + (cellWidth*j) + cellWidth/2);
                                storeInHashMap(key++,x,y);
                            }
                        }

            }
        },2000);

    }

    int cell_2_X = 0,cell_2_Y = 0 ;

    private void change(){
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        double degrees = Double.parseDouble(degree.getText().toString());

                        if(angle.getText().toString().equals("sin")){

                            cell_2_X = (int) (baseX * Math.sin(Math.toRadians(degrees)));
                            cell_2_Y = (int) ((centerY + perpendicular) * Math.sin(Math.toRadians(degrees)));

                            System.out.println("Sin" + degrees);

                        }else if (angle.getText().toString().equals("tan")){

                            cell_2_X = (int) (baseX * Math.tan(Math.toRadians(degrees)));
                            cell_2_Y = (int) ((centerY + perpendicular) * Math.tan(Math.toRadians(degrees)));

                            System.out.println("tan" + degrees);


                        }else if (angle.getText().toString().equals("cos")){
                            cell_2_X = (int) (baseX * Math.cos(Math.toRadians(degrees)));
                            cell_2_Y = (int) ((centerY + perpendicular) * Math.cos(Math.toRadians(degrees)));
                            System.out.println("Cos" + degrees);

                        }

                        createView(cell_2_X, cell_2_Y);

//

                    }
                },1000
        );

    }

    private void storeInHashMap(int key, int x, int y){

        System.out.println("KEY:" + key + " X:"+ x + " Y:" + y);

        map.put(key,new int[]{x,y});
        createView(x,y);
    }

    private View createView(int x, int y){

        System.out.println("X:"+x);
        System.out.println("Y:"+y);


        View view = new View(this);
        view.setBackgroundColor(Color.DKGRAY);
        view.setLayoutParams(layoutParams);
        view.setX(x);
        view.setY(y);

        constraintLayout.addView(view);
        return view;
    }

    private void showLog(String msg){
        Log.i("MainActivity",msg);
    }
}