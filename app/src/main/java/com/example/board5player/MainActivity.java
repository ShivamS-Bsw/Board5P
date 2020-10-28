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
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    View view;
    int top;
    private ConstraintLayout constraintLayout;

    int left;
    int[] location;
    ConstraintLayout.LayoutParams layoutParams;
    private HashMap<Integer,int[]> redMap,blueMap,greenMap,yellowMap,orangeMap,purpleMap;
    boolean called = false;
    private double base;
    private double perpendicular;
    private int centerY;
    private int centerX;
    private Button button4,button5,button6;
    private ImageView token;
    int boardValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);


        constraintLayout = findViewById(R.id.constraint_layout);

        layoutParams = new ConstraintLayout.LayoutParams(
                8,8);


        location = new int[2];
        imageView.getLocationInWindow(location);

        base();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boardValue != 0)
                    base();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boardValue != 1)
                    base1();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(boardValue != 2)
                    base2();
            }
        });
    }

    int baseX = 0;
    int baseY = 0;
    double cellWidth = 0;

    // 4 Player
    private void base(){
    boardValue = 0;
    constraintLayout.removeAllViews();
        imageView.setBackgroundResource(R.drawable.game_board_4);
        constraintLayout.addView(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int imageHeight = imageView.getHeight();
                int imageWidth = imageView.getWidth();

                int boardTop = location[1];
                int boardLeft = location[0];


                centerX =  boardLeft + imageWidth/2;
                centerY =  boardTop + imageHeight/2;

                createView(centerX,centerY);

                perpendicular = imageHeight * .1;
                base =  (2 * perpendicular)/Math.tan(Math.toRadians(45));

                cellWidth = base/3 ;



                baseX = (int) (centerX - base/2);
                baseY = (int) (centerY + perpendicular);

                createView(baseX,baseY);

                int x = (int) (baseX + cellWidth/2);
                int y = (int) (baseY + cellWidth/2);

                createView(x,y);
                redMap = new HashMap<>();
                blueMap = new HashMap<>();
                greenMap = new HashMap<>();
                yellowMap = new HashMap<>();
                orangeMap = new HashMap<>();

                int key = 1;
                for(int i =0; i<3; i++) {

                    int x1 = (int) (x + (cellWidth*i));

                    for(int j = 0; j<6;j++){
//
                        int y1 = (int) (y + (cellWidth*j));
                        storeInHashMap(redMap,key,x1,y1);

                        int [] location2 = performRotation4P(x1,y1);
                        storeInHashMap(blueMap,key,location2[0],location2[1]);

                        int [] location3 = performRotation4P(location2[0],location2[1]);
                        storeInHashMap(orangeMap,key,location3[0],location3[1]);


                        int [] location4 = performRotation4P(location3[0],location3[1]);
                        storeInHashMap(yellowMap,key,location4[0],location4[1]);

                        key++;
                    }
                }

            }
        },2000); //  delay because it takes some time to render the image and get the height and width
    }

    int imageHeight = 0 ;
    int imageWidth = 0;
    //5 Player
    private void base1(){

        boardValue = 1;
        constraintLayout.removeAllViews();
        imageView.setBackgroundResource(R.drawable.game_board_5p);

        constraintLayout.addView(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                imageHeight = imageView.getHeight() - 13;
                imageWidth = imageView.getWidth();

                int boardTop = location[1];
                int boardLeft = location[0];


                centerX =  boardLeft + imageWidth/2;
                centerY =  boardTop + imageHeight/2;

                createView(centerX,centerY);

                perpendicular = imageHeight * (12.97/100);
                base = (2 * perpendicular ) / Math.tan(Math.toRadians(54));

//                perpendicular = imageHeight * .1;
//                base =  (2 * perpendicular)/Math.tan(Math.toRadians(45));

                cellWidth = base/3 ;


                baseX = (int) (centerX - base/2);
                baseY = (int) (centerY + perpendicular);

                createView(baseX,baseY);
                drawRedBase(baseX,baseY);

                int x = (int) (baseX + cellWidth/2);
                int y = (int) (baseY + cellWidth/2);

                createView(x,y);

                redMap = new HashMap<>();
                blueMap = new HashMap<>();
                greenMap = new HashMap<>();
                yellowMap = new HashMap<>();
                orangeMap = new HashMap<>();

                int key = 1;
                        for(int i =0; i<3; i++) {

                            int x1 = (int) (x + (cellWidth*i));

                            for(int j = 0; j<6;j++){
//
                                int y1 = (int) (y + (cellWidth*j));
                                storeInHashMap(redMap,key,x1,y1);

                                int [] location2 = performRotation5P(x1,y1);
                                storeInHashMap(blueMap,key,location2[0],location2[1]);


                                int [] location3 = performRotation5P(location2[0],location2[1]);
                                storeInHashMap(orangeMap,key,location3[0],location3[1]);


                                int [] location4 = performRotation5P(location3[0],location3[1]);
                                storeInHashMap(yellowMap,key,location4[0],location4[1]);


                                int [] location5 = performRotation5P(location4[0],location4[1]);
                                storeInHashMap(yellowMap,key,location5[0],location5[1]);

                                key++;


                            }
                        }

            }
        },2000);

    }

    //6 Player
    private void base2(){

        boardValue = 2;
        constraintLayout.removeAllViews();
        imageView.setBackgroundResource(R.drawable.game_board_6p);
        constraintLayout.addView(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                imageHeight = imageView.getHeight();
                imageWidth = imageView.getWidth();

                int boardTop = location[1];
                int boardLeft = location[0];

                centerX =  boardLeft + imageWidth/2;
                centerY =  boardTop + imageHeight/2;

                createView(centerX,centerY);

                perpendicular = imageHeight * (14.55/100);
                base = (2 * perpendicular ) / Math.tan(Math.toRadians(60));

                cellWidth = base/3 ;

                // First cell
                baseX = (int) (centerX - perpendicular);
                baseY = (int) (centerY - base/2 );

                int x = (int) (baseX - cellWidth/2);
                int y = (int) (baseY + cellWidth/2);

                redMap = new HashMap<>();
                blueMap = new HashMap<>();
                greenMap = new HashMap<>();
                yellowMap = new HashMap<>();
                orangeMap = new HashMap<>();
                purpleMap = new HashMap<>();

                int key = 1;
                for (int i=0;i<3;i++){

                    int y1 = (int) (y + (i*cellWidth));

                    for(int j=0;j<6;j++){

                        int x1 = (int) (x - (j*cellWidth));
                        storeInHashMap(purpleMap,key,x1,y1);

                        int[] location2 = performRotation6P(x1,y1);
                        storeInHashMap(yellowMap,key,location2[0],location2[1]);

                        int[] location3 = performRotation6P(location2[0],location2[1]);
                        storeInHashMap(yellowMap,key,location3[0],location3[1]);

                        int[] location4 = performRotation6P(location3[0],location3[1]);
                        storeInHashMap(yellowMap,key,location4[0],location4[1]);

                        int[] location5 = performRotation6P(location4[0],location4[1]);
                        storeInHashMap(yellowMap,key,location5[0],location5[1]);

                        int[] location6 = performRotation6P(location5[0],location5[1]);
                        storeInHashMap(yellowMap,key,location6[0],location6[1]);

                        key++;

                    }
                }

            }
        },2000);

    }

    private void drawBase6P(){

        for(Map.Entry<Integer,int[]> map: purpleMap.entrySet()){

            if(map.getKey()>6 && map.getKey()<13){

                int[] location = performRotation4P(map.getValue()[0],map.getValue()[1]);

                createView(location[0], (int) (location[1]+cellWidth/2));

            }
        }
    }

/*
https://math.stackexchange.com/questions/270194/how-to-find-the-vertices-angle-after-rotation
 */

    private int[] performRotation4P(int x, int y){

        int x1 = (int) (((x - centerX) * Math.cos(Math.toRadians(-90))) - ((y-centerY)*Math.sin(Math.toRadians(-90))));
        int y1 = (int) (((x- centerX )* Math.sin(Math.toRadians(-90))) + ((y - centerY)*Math.cos(Math.toRadians(-90))));

        return new int[]{centerX+ x1,centerY + y1};
    }
    private int[] performRotation5P(int x, int y){

        int x1 = (int) (((x - centerX) * Math.cos(Math.toRadians(-72))) - ((y-centerY)*Math.sin(Math.toRadians(-72))));
        int y1 = (int) (((x- centerX )* Math.sin(Math.toRadians(-72))) + ((y - centerY)*Math.cos(Math.toRadians(-72))));

        return new int[]{centerX+ x1,centerY + y1};
    }
    private int[] performRotation6P(int x, int y){

        int x1 = (int) (((x - centerX) * Math.cos(Math.toRadians(-60))) - ((y-centerY)*Math.sin(Math.toRadians(-60))));
        int y1 = (int) (((x- centerX )* Math.sin(Math.toRadians(-60))) + ((y - centerY)*Math.cos(Math.toRadians(-60))));

        return new int[]{centerX+ x1,centerY + y1};
    }


/*
width - 56 , 84,53,131
height - 77 , 116,158,99

 */
    private void drawRedBase(int baseX, int baseY){

        int firstX = (int) (baseX - imageWidth*.0722);
        int firstY = (int) (baseY + imageHeight*.111);
        int secondX = (int) (baseX - imageWidth*.122);
        int secondY = (int) (baseY + imageHeight*.1611);
        int thirdX = (int) (baseX - imageWidth*.0680);
        int thirdY = (int) (baseY + imageHeight*.2194);
        int fourthX = (int) (baseX - imageWidth*.181);
        int fourthY = (int) (baseY + imageHeight*.1375);

        createView(firstX,firstY);
        createView(secondX,secondY);
        createView(thirdX,thirdY);
        createView(fourthX,fourthY);


        drawBlueBase(firstX,firstY);
        drawBlueBase(secondX,secondY);
        drawBlueBase(thirdX,thirdY);
        drawBlueBase(fourthX,fourthY);

    }
    private void drawBlueBase(int x, int y){
        int[] location = performRotation5P(x,y);
        createView(location[0],location[1]);
        drawOrangeBase(location[0],location[1]);
    }
    private void drawOrangeBase(int x, int y){
        int[] location = performRotation5P(x,y);
        createView(location[0],location[1]);
        drawYellowBase(location[0],location[1]);
    }
    private void drawYellowBase(int x, int y){
        int[] location = performRotation5P(x,y);
        createView(location[0],location[1]);
        drawGreenBase(location[0],location[1]);
    }
    private void drawGreenBase(int x, int y){
        int[] location = performRotation5P(x,y);
        createView(location[0],location[1]);
    }

    private void storeInHashMap(HashMap<Integer,int[]> map,int key, int x, int y){
//
        map.put(key,new int[]{x,y});
        createView(x,y);
    }

    private View createView(int x, int y){


        System.out.println("X: " +x + " Y: " + y);
        View view = new View(this);
        view.setBackgroundColor(Color.DKGRAY);
        view.setLayoutParams(layoutParams);
        view.setX(x-4);
        view.setY(y-4);

        constraintLayout.addView(view);
        return view;
    }
    private void showLog(String msg){
        Log.i("MainActivity",msg);
    }
}