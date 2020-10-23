package com.example.board5player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    View view;
    int top;
    private ConstraintLayout constraintLayout;

    int left;
    int[] location;
    ConstraintLayout.LayoutParams layoutParams;
    private HashMap<Integer,int[]> map;
    boolean called = false;
    private double base;
    private double perpendicular;
    private int centerY;
    private int centerX;
    private double radius;
    private int boardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int centerX = metrics.widthPixels;
        int centerY = metrics.heightPixels;



        map = new HashMap<>();
        constraintLayout = findViewById(R.id.constraint_layout);

        layoutParams = new ConstraintLayout.LayoutParams(
                8,8);


        location = new int[2];
        imageView.getLocationInWindow(location);

        base();

    }

    int baseX = 0;
    int baseY = 0;
    double cellWidth = 0;
    int originX;
    int originY;

    private void base(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int imageHeight = imageView.getHeight();
                int imageWidth = imageView.getWidth();
                boardHeight = imageView.getWidth();

                int boardTop = location[1];
                int boardLeft = location[0];


                originX =  boardLeft + imageWidth/2;
                originY =  boardTop + imageHeight/2;

                int x = (int) (originX - getRadiusFromPercentage(boardHeight)*Math.sin(Math.toRadians(45)) + getCellWidth()/2);
                int y = (int) (originY + getRadiusFromPercentage(boardHeight)*Math.cos(Math.toRadians(45)) + getCellWidth()/2);

//                createView(x,y);
                drawRedCoordinates(x,y);

//
//                createView(centerX,centerY);
//
////                perpendicular = imageHeight * (12.97/100);
////                base = (2 * perpendicular ) / Math.tan(Math.toRadians(54));
//
//                perpendicular = imageHeight * 12.54/100;
//                base =  (2 * perpendicular) * Math.tan(Math.toRadians(36));
//
//                cellWidth = base/3 ;
//
//                baseX = (int) (centerX - base/2);
//                baseY = (int) (centerY + perpendicular);
//
//                int x = (int) (baseX + cellWidth/2);
//                int y = (int) (baseY + cellWidth/2);
//
////                createBoardMappingRed(x,y);
//
//
//                radius = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
////
//                int cell2X = centerX + (int)(radius * Math.cos(Math.toRadians(40)));
//                int cell2Y = centerY + (int)(radius * Math.sin(Math.toRadians(40)));
//
////                createView(cell2X,cell2Y);
//                createBoardMappingBlue(cell2X,cell2Y);
//
//                int cell3X = centerX + (int)(radius * Math.cos(Math.toRadians(34)));
//                int cell3Y = centerY - (int)(radius * Math.sin(Math.toRadians(34)));
//
//                createView(cell3X,cell3Y);
//
//                int cell4X = centerX - (int)(radius * Math.sin(Math.toRadians(14)));
//                int cell4Y = centerY - (int)(radius * Math.cos(Math.toRadians(12)));
//
//                createView(cell4X,cell4Y);
//
//                int cell5X = centerX - (int)(radius * Math.cos(Math.toRadians(4)));
//                int cell5Y = centerY - (int)(radius * Math.sin(Math.toRadians(4)));
//
//                createView(cell5X,cell5Y);

//                int key = 1;
//                        for(int i =0; i<3; i++) {
//
//                            int y1 = (int) (cell3Y - (cellWidth*i));
//
//                            for(int j = 0; j<6;j++){
////
//                                int x1 = (int) (cell3X + (cellWidth*j));
//                                storeInHashMap(key++,x1,y1);
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
//                            }
//                        }

            }
        },2000);

    }

    private HashMap<Integer, int[]> redMapping;
    private HashMap<Integer, int[]> blueMapping;
    private HashMap<Integer, int[]> yellowMapping;
    private HashMap<Integer, int[]> greenMapping;


    private void drawRedCoordinates( int startX, int startY){

        redMapping = new HashMap<>();

        int key = 1;

        for(int i=0;i<3;i++){

            int x = (int) (startX + (i*getCellWidth()));
            for(int j = 0; j<6;j++){
                int y = (int) (startY + (j*getCellWidth()));
                createView(x,y);
                System.out.println("Key:"+key + " X:"+ x + " Y:"+y);
                redMapping.put(key++ , new int[]{x,y});
            }
        }

        drawBlueCoordinates();
    }

    private double getRadiusFromCoordinates(int x, int y){

        return Math.sqrt(Math.pow(x-originX,2) + Math.pow(y-originY,2));
    }

    private int[] calculateNextCoordinate(int x, int y){
        double radius = getRadiusFromCoordinates(x,y);

//        System.out.println(radius);
//
        double v = (Math.atan(Math.toDegrees(y/(float)x)) + 90);

        int x1 = (int) (radius*(Math.cos(Math.toDegrees(v))));
        int y1 = (int) (radius*(Math.sin(Math.toDegrees(v))));

//        System.out.println(v);

        int[] coordinates = new int[2];

        coordinates[0] = x1;
        coordinates[1] = y1;

        return coordinates;
    }

    private void drawBlueCoordinates(){


        for (Map.Entry<Integer,int[]> entry : redMapping.entrySet()){

            int key = entry.getKey();
            int[] location = entry.getValue();

            int x = location[0];
            int y = location[1];

            int[] result = calculateNextCoordinate(x,y);
            System.out.println("Key:"+key + " Blue X:"+ result[0] + " Blue Y:"+result[1]);
            createView(result[0] + originX,result[1] + originY);
        }
    }

    // 13percent of board size
    private double getRadiusFromPercentage(int imageHeight) {
        return (imageHeight * 13.94) / 100;
    }

    private double getCellWidth(){
        return (getRadiusFromPercentage(boardHeight)*Math.cos(Math.toRadians(45)))/1.5;
    }

    private void createBoardMappingRed(int x, int y){

        for(int i =0; i<3; i++) {

            int x1 = (int) ((int)(x + (cellWidth * i)));

            for(int j = 0; j<6;j++) {

                int y1 = (int) ((int) (y + (cellWidth*j)));
                createView(x1,y1);

            }
        }
    }


    private void createBoardMappingBlue(int x, int y){

        createView(x,y);

        int nextX = (int) (x + cellWidth); // not changed
        int nextY = (int) (y - cellWidth);

        createView(nextX,nextY);
//

        int p = (int) (cellWidth * Math.tan(Math.toRadians(20)));

//        int nextZ = (int) (nextY + p);
//        createView(nextX ,nextZ);

        for(int i =0; i<3; i++) {

            int y1 = (int) ((int)(y - (cellWidth * i)));

            for(int j = 0; j<6;j++) {

                int x1 = (int) ((int) (x + (cellWidth*j)));
//                int y1 = y + p*j;

                createView(x1,y1);

            }
        }
    }


    int cell_2_X = 0,cell_2_Y = 0 ;

    private void storeInHashMap(int key, int x, int y){
//
//        map.put(key,new int[]{x,y});
        createView(x,y);
    }

    private View createView(int x, int y){

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

    private void for4Player(){

//        perpendicular = imageHeight * .1;
//        base =  (2 * perpendicular)/Math.tan(Math.toRadians(45));
//
//        cellWidth = base/3 ;
//
//        baseX = (int) (centerX - base/2);
//        baseY = (int) (centerY + perpendicular);
//
//        int x = (int) (baseX + cellWidth/2);
//        int y = (int) (baseY + cellWidth/2);
//
//        createView(x,y);
//        double radius = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
//
//        int cell3X = centerX + (int)(radius * Math.cos(Math.toRadians(30)));
//        int cell3Y = centerY + (int)(radius * Math.sin(Math.toRadians(30)));
//
//        int key = 1;
//        for(int i =0; i<3; i++) {
//
//            int y1 = (int) (cell3Y - (cellWidth*i));
//
//            for(int j = 0; j<6;j++){
////
//                int x1 = (int) (cell3X + (cellWidth*j));
//                storeInHashMap(key++,x1,y1);
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

    }

    private void showLog(String msg){
        Log.i("MainActivity",msg);
    }
}