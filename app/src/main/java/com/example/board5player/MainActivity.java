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
                8,8);


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

                System.out.println(centerX);
                System.out.println(centerY);

                createView(centerX,centerY);

//                perpendicular = imageHeight * (12.97/100);
//                base = (2 * perpendicular ) / Math.tan(Math.toRadians(54));

                perpendicular = imageHeight * .1;
                base =  (2 * perpendicular)/Math.tan(Math.toRadians(45));

                cellWidth = base/3 ;

                baseX = (int) (centerX - base/2);
                baseY = (int) (centerY + perpendicular);

                int x = (int) (baseX + cellWidth/2);
                int y = (int) (baseY + cellWidth/2);

                createView(x,y);
                double radius = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));

                int cell3X = centerX + (int)(radius * Math.cos(Math.toRadians(30)));
                int cell3Y = centerY + (int)(radius * Math.sin(Math.toRadians(30)));

                int key = 1;
                        for(int i =0; i<3; i++) {

                            int y1 = (int) (cell3Y - (cellWidth*i));

                            for(int j = 0; j<6;j++){
//
                                int x1 = (int) (cell3X + (cellWidth*j));
                                storeInHashMap(key++,x1,y1);
////
////                                createView(centerX + cell3X , centerY + cell3);
//////
////                                int cell4X = (int) (radius * Math.cos(Math.toRadians(60)));
////                                int cell4Y = (int) (radius * Math.sin(Math.toRadians(60)));
////
////
////                                createView(centerX + cell4X , centerY - cell4Y);
////
////
////                                int cell5X = (int) (radius * Math.cos(Math.toRadians(30)));
////                                int cell5Y = (int) (radius * Math.sin(Math.toRadians(30)));
////
////
////                                createView(centerX - cell5X , centerY - cell5Y);
//
//
//                                //createView(cell3X,cell3Y);
////                                createView(816, 528);
////                                createView(816, 912);
////                                createView(912, 816);
//
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


//                        findNextCoordinate(72,cell_2_X,cell_2_Y);
//

                    }
                },1000
        );

    }

    private void storeInHashMap(int key, int x, int y){
//
//        map.put(key,new int[]{x,y});
        createView(x,y);
    }

    private View createView(int x, int y){

        System.out.println("X:"+x);
        System.out.println("Y:"+y);


        View view = new View(this);
        view.setBackgroundColor(Color.DKGRAY);
        view.setLayoutParams(layoutParams);
        view.setX(x-4);
        view.setY(y-4);

        constraintLayout.addView(view);
        return view;
    }

    private void findNextCoordinate(int angle, int centerX, int centerY , int x1, int y1){

      int anglePerSide = (180 - angle)/2;

      double sideAB = Math.sqrt(Math.pow(x1-centerX,2) + Math.pow(y1-centerY,2));
      double sideAC = sideAB;

      double sideBC = 2*sideAB*Math.toRadians(Math.cos(anglePerSide));



    }

    private void showLog(String msg){
        Log.i("MainActivity",msg);
    }
}