package com.example.board5player;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blacklightsw.ludooffline.customView.DrawingView;
import com.blacklightsw.ludooffline.dialog.GameExitDialog;
import com.blacklightsw.ludooffline.dialog.GameOverDialog;
import com.blacklightsw.ludooffline.dialog.GameSettingsDialog;
import com.blacklightsw.ludooffline.dialog.LocaleDialog;
import com.blacklightsw.ludooffline.dialog.TwoButtonDialog;
import com.blacklightsw.ludooffline.game.BoardField;
import com.blacklightsw.ludooffline.game.Game5P;
import com.blacklightsw.ludooffline.game.Move;
import com.blacklightsw.ludooffline.game.Player;
import com.blacklightsw.ludooffline.game.Token;
import com.blacklightsw.ludooffline.util.CommonUtil;
import com.blacklightsw.ludooffline.util.MyConstants;
import com.blacklightsw.ludooffline.util.SoundHandling;
import com.blacklightsw.ludooffline.util.Storage;
import com.blacklightsw.ludooffline.util.TokenImageHolder;
import com.google.gson.Gson;
import com.plattysoft.leonids.ParticleSystem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.blacklightsw.ludooffline.game.Player.PlayerColor.BLUE;
import static com.blacklightsw.ludooffline.game.Player.PlayerColor.GREEN;
import static com.blacklightsw.ludooffline.game.Player.PlayerColor.ORANGE;
import static com.blacklightsw.ludooffline.game.Player.PlayerColor.RED;
import static com.blacklightsw.ludooffline.game.Player.PlayerColor.YELLOW;

public class GameFragment_5P extends Fragment {

    Bundle savedInstanceState = null;
    private int yPosOfBoard;

    GameFragment_5P(){

    }
    private ImageView back_btn;
    private ImageView settings;
    private ImageView add_remove_player;

    private Game5P game;
    private DrawingView drawingView;
    private View bottomLeftAddRemoveParent;
    private View bottomRightAddRemoveParent;
    private View topLeftAddRemoveParent;
    private View topRightAddRemoveParent;
    private View bottomAddRemoveParent;


    private TextView bottomLeftAddRemoveText;
    private TextView bottomRightAddRemoveText;
    private TextView topLeftAddRemoveText;
    private TextView topRightAddRemoveText;
    private TextView bottomAddRemoveText;

    private TextView topLeftPlayerName;
    private TextView bottomLeftPlayerName;
    private TextView topRightPlayerName;
    private TextView bottomRightPlayerName;
    private TextView bottomPlayerName;

    private View bottomLeftHelper;
    private View bottomRightHelper;
    private View topLeftHelper;
    private View topRightHelper;
    private View bottomHelper;

    private Player.PlayerColor bottomLeftColor;
    private Player.PlayerColor bottomRightColor;
    private Player.PlayerColor middleLeftColor;
    private Player.PlayerColor middleRightColor;
    private Player.PlayerColor topColor;
    private Player.PlayerColor currentPlayerColor;

    private TwoButtonDialog addPlayerDialog;
    private TwoButtonDialog removePlayerDialog;
    private GameExitDialog gameExitDialog;
    private GameOverDialog gameOverDialog;
    private LocaleDialog localeDialog;
    private Player[] players;
    private boolean isFromSavedGame = false;
    private boolean isReplayAction = false;
    private boolean isDiceAnimating;

    private ParticleSystem ps;
    private ParticleSystem ps1;
    private ParticleSystem ps2;
    private ParticleSystem ps3;
    private ParticleSystem ps4;


    private volatile Token movingToken;
    private Move killerMove;
    private volatile Token movingKilledToken;
    private boolean pause;

    private FrameLayout drawingArea_token;
    private FrameLayout drawingArea;
    private ImageView gameBoardView;
    private FrameLayout gameAnimationParent;
    private boolean gameBoardRotating;
    private View gameBoardParentClone;
    private boolean isReCreateNewToken;

    public static HashMap<Integer, int[]> boardFieldMap;
    public static HashMap<Integer, int[]> fieldLocationMap;

    private Button doneAddRemove;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getContext() != null) {
                if (view.getVisibility() == View.VISIBLE) {
                    handleClick(view);
                }
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            return getContext() != null && handleClick(view);
        }
    };

    private DrawingView.DrawingViewCallBacks drawingViewCallBacks  = new DrawingView.DrawingViewCallBacks() {
        @Override
        public void onDraw(Canvas canvas, Paint paint) {

        }

        @Override
        public void onTouchUp(float x, float y) {

        }

        @Override
        public void onSizeChanged() {

        }
    };
    private Game5P.GameCallBacks gameCallBacks = new Game5P.GameCallBacks() {
        @Override
        public void onKill(Move killerMove) {
            if (getContext() != null) {
//                setKillerMove(killerMove);
            }
        }

        @Override
        public void onTurnChanged(Player player, boolean timeOutMove) {
            if (getContext() != null) {
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_5_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        bottomLeftAddRemoveParent = view.findViewById(R.id.bottomLeft_addRemove_parent);
        bottomLeftAddRemoveParent.setOnClickListener(onClickListener);
        bottomRightAddRemoveParent = view.findViewById(R.id.bottomRight_addRemove_parent);
        bottomRightAddRemoveParent.setOnClickListener(onClickListener);
        topLeftAddRemoveParent = view.findViewById(R.id.topLeft_addRemove_parent);
        topLeftAddRemoveParent.setOnClickListener(onClickListener);
        topRightAddRemoveParent = view.findViewById(R.id.topRight_addRemove_parent);
        topRightAddRemoveParent.setOnClickListener(onClickListener);

        //PlayerName
        topLeftPlayerName = view.findViewById(R.id.topLeft_playerName);
        topRightPlayerName = view.findViewById(R.id.topRight_playerName);
        bottomRightPlayerName = view.findViewById(R.id.bottomRight_playerName);
        bottomLeftPlayerName = view.findViewById(R.id.bottomLeft_playerName);
        bottomPlayerName = view.findViewById(R.id.bottom_playerName);

        //bottom
        bottomAddRemoveParent = view.findViewById(R.id.bottom_addRemove_parent);
        bottomAddRemoveParent.setOnClickListener(onClickListener);

        bottomLeftAddRemoveText = view.findViewById(R.id.bl_addRemove_txt);
        bottomRightAddRemoveText = view.findViewById(R.id.br_addRemove_txt);
        topLeftAddRemoveText = view.findViewById(R.id.tl_addRemove_txt);
        topRightAddRemoveText = view.findViewById(R.id.tr_addRemove_txt);

        //bottom
        bottomAddRemoveText = view.findViewById(R.id.bottom_addRemove_txt);
        bottomAddRemoveText = view.findViewById(R.id.bottom_addRemove_txt);

        bottomLeftHelper = view.findViewById(R.id.bottomLeftHelper);
        bottomRightHelper = view.findViewById(R.id.bottomRightHelper);
        topLeftHelper = view.findViewById(R.id.topLeftHelper);
        topRightHelper = view.findViewById(R.id.topRightHelper);

        back_btn = view.findViewById(R.id.back_btn);
        settings = view.findViewById(R.id.settings);
        add_remove_player = view.findViewById(R.id.add_remove_player);

        drawingArea = view.findViewById(R.id.drawingArea);
        gameBoardParentClone = view.findViewById(R.id.gameBoardParentClone);
        gameBoardView = view.findViewById(R.id.gameBoardImageView_game);

        gameAnimationParent = view.findViewById(R.id.animatingArea);
        drawingArea_token = view.findViewById(R.id.drawingArea_token);

        drawingView = new DrawingView(getContext());
        drawingView.setCallBacks(drawingViewCallBacks);
        drawingArea.addView(drawingView);

        initGame();
    }

    public void setReplayAction(boolean isFromGameOver) {
        this.isReplayAction = isFromGameOver;
    }

    private boolean isFirstTurn;
    private boolean isMultiPlayerGame;

    private void initGame() {

        JSONObject object1 = null;
        try {
            object1 = new JSONObject(getArguments().getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        isFromSavedGame = object1.optBoolean(MyConstants.JSonKey.IS_FROM_SAVED_GAME, false);

        //Not from saved Game
        if (!isFromSavedGame) {
            try {
                JSONObject object = new JSONObject(getArguments().getString("data"));

                int localGameMode = object.optInt(MyConstants.JSonKey.LOCAL_GAME_MODE, MyConstants.LocalGameMode.CLASSIC);

                players = new Gson().fromJson(object.getString(MyConstants.JSonKey.PLAYERS), Player[].class);

                Storage.getInstance().setPlayers(new Gson().toJson(players));

                movingToken = null;

                movingKilledToken = null;

                killerMove = null;

                isFirstTurn = true;

                if (!isMultiPlayerGame) {
                    if (players != null && players.length > 0) {
                        currentPlayerColor = players[0].getPlayerColor();
                    }
                }

//                initPlayerRelatedViews();
               // rotateGameBoard(currentPlayerColor);

                game = new Game5P(isMultiPlayerGame, fieldSize, localGameMode);

                game.setTimeLeft(0);

                if (players != null) {
                    for (Player player : players) {
                        player.setRoundStanding(players.length);
                        addPlayerToGame(player);

                        Log.i(TAG,"Player Color:" + player.getPlayerColor());
                        Log.i(TAG,"Player Token Count:" + player.getTokens().length);
                    }
                }

                game.setGameMode(MyConstants.GameMode.LOCAL);

//                logGameStartEvent();
//                logUserGameCount();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayerToGame(Player player) {
        game.addPlayer(player);
    }


    int fieldSize = 0;
    private int boardHeight;
    private int boardWidth;
    private int boardCenterX,boardCenterY;
    private HashMap<Integer, int[]> baseMap;
    private int key = 96;
    private static final String TAG = GameFragment_5P.class.getSimpleName();

    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(8,8);
    int top,left;
    private void initFieldLocation(){

        if (fieldLocationMap != null) {
            if (!fieldLocationMap.isEmpty()) {
                fieldLocationMap.clear();
            }
            fieldLocationMap = null;
        }

        boardHeight = gameBoardView.getHeight();
        boardWidth = gameBoardView.getWidth();

        int[] boardPos = new int[2];
        gameBoardView.getLocationInWindow(boardPos);

        top = boardPos[1];
        left = boardPos[0];


        getFieldLocation(top, left , boardHeight,boardWidth);
        drawBase();
        mapFieldLocation();

        if (game != null) {
            game.resetTokensPosition();
            getSizeAndPositions(getContext(), angle);
            drawingView.invalidate();
        }
//
         createTokens();
    }

    private void drawView(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(16,16);

            View view = new View(getContext());
            view.setBackgroundColor(Color.BLACK);
            view.setLayoutParams(layoutParams);
            view.setX(top - 8);
            view.setY(left - 8);

        drawingArea.addView(view);


//        for(int i=1;i<110;i++){
//
//            int[] loc = boardFieldMap.get(i);
//
//            Log.i(TAG,""+loc[0]);
//            Log.i(TAG,""+loc[1]);
//
//            View view = new View(getContext());
//            view.setBackgroundColor(Color.BLACK);
//            view.setLayoutParams(layoutParams);
//            view.bringToFront();
//            view.setX(boardCenterX - 4);
//            view.setY(boardCenterY - 4);
//
//            viewGroup.addView(view);
//        }
    }

    private void mapFieldLocation(){

        boardFieldMap = new HashMap<>();

        if(fieldLocationMap != null && !fieldLocationMap.isEmpty()){

            for(Map.Entry<Integer,int[]> map : fieldLocationMap.entrySet()){

                switch (map.getKey()){

                    case 1:
                        boardFieldMap.put(5,map.getValue());
                        break;
                    case 2:
                        boardFieldMap.put(57,map.getValue());
                        break;
                    case 3:
                        boardFieldMap.put(44,map.getValue());
                        break;
                    case 4:
                        boardFieldMap.put(31,map.getValue());
                        break;
                    case 5:
                        boardFieldMap.put(18,map.getValue());
                        break;
                    case 6:
                        boardFieldMap.put(4,map.getValue());
                        break;
                    case 7:
                        boardFieldMap.put(56,map.getValue());
                        break;
                    case 8:
                        boardFieldMap.put(43,map.getValue());
                        break;
                    case 9:
                        boardFieldMap.put(30,map.getValue());
                        break;
                    case 10:
                        boardFieldMap.put(17,map.getValue());
                        break;
                    case 11:
                        boardFieldMap.put(3,map.getValue());
                        break;
                    case 12:
                        boardFieldMap.put(55,map.getValue());
                        break;
                    case 13:
                        boardFieldMap.put(42,map.getValue());
                        break;
                    case 14:
                        boardFieldMap.put(29,map.getValue());
                        break;
                    case 15:
                        boardFieldMap.put(16,map.getValue());
                        break;
                    case 16:
                        boardFieldMap.put(2,map.getValue());
                        break;
                    case 17:
                        boardFieldMap.put(54,map.getValue());
                        break;
                    case 18:
                        boardFieldMap.put(41,map.getValue());
                        break;
                    case 19:
                        boardFieldMap.put(28,map.getValue());
                        break;
                    case 20:
                        boardFieldMap.put(15,map.getValue());
                        break;
                    case 21:
                        boardFieldMap.put(1,map.getValue());
                        break;
                    case 22:
                        boardFieldMap.put(53,map.getValue());
                        break;
                    case 23:
                        boardFieldMap.put(40,map.getValue());
                        break;
                    case 24:
                        boardFieldMap.put(27,map.getValue());
                        break;
                    case 25:
                        boardFieldMap.put(14,map.getValue());
                        break;
                    case 26:
                        boardFieldMap.put(71,map.getValue());
                        break;
                    case 27:
                        boardFieldMap.put(52,map.getValue());
                        break;
                    case 28:
                        boardFieldMap.put(39,map.getValue());
                        break;
                    case 29:
                        boardFieldMap.put(26,map.getValue());
                        break;
                    case 30:
                        boardFieldMap.put(13,map.getValue());
                        break;
                    case 31:
                        boardFieldMap.put(69,map.getValue());
                        break;
                    case 32:
                        boardFieldMap.put(94,map.getValue());
                        break;
                    case 33:
                        boardFieldMap.put(88,map.getValue());
                        break;
                    case 34:
                        boardFieldMap.put(82,map.getValue());
                        break;
                    case 35:
                        boardFieldMap.put(77,map.getValue());
                        break;
                    case 36:
                        boardFieldMap.put(68,map.getValue());
                        break;
                    case 37:
                        boardFieldMap.put(93,map.getValue());
                        break;
                    case 38:
                        boardFieldMap.put(87,map.getValue());
                        break;
                    case 39:
                        boardFieldMap.put(81,map.getValue());
                        break;
                    case 40:
                        boardFieldMap.put(75,map.getValue());
                        break;
                    case 41:
                        boardFieldMap.put(67,map.getValue());
                        break;
                    case 42:
                        boardFieldMap.put(92, map.getValue());
                        break;
                    case 43:
                        boardFieldMap.put(86, map.getValue());
                        break;
                    case 44:
                        boardFieldMap.put(80, map.getValue());
                        break;
                    case 45:
                        boardFieldMap.put(74, map.getValue());
                        break;
                    case 46:
                        boardFieldMap.put(66, map.getValue());
                        break;
                    case 47:
                        boardFieldMap.put(91, map.getValue());
                        break;
                    case 48:
                        boardFieldMap.put(85, map.getValue());
                        break;
                    case 49:
                        boardFieldMap.put(79, map.getValue());
                        break;
                    case 50:
                        boardFieldMap.put(73, map.getValue());
                        break;
                    case 51:
                        boardFieldMap.put(65, map.getValue());
                        break;
                    case 52:
                        boardFieldMap.put(90, map.getValue());
                        break;
                    case 53:
                        boardFieldMap.put(84, map.getValue());
                        break;
                    case 54:
                        boardFieldMap.put(78, map.getValue());
                        break;
                    case 55:
                        boardFieldMap.put(72, map.getValue());
                        break;
                    case 56:
                        boardFieldMap.put(64, map.getValue());
                        break;
                    case 57:
                        boardFieldMap.put(51, map.getValue());
                        break;
                    case 58:
                        boardFieldMap.put(38, map.getValue());
                        break;
                    case 59:
                        boardFieldMap.put(25, map.getValue());
                        break;
                    case 60:
                        boardFieldMap.put(12, map.getValue());
                        break;
                    case 61:
                        boardFieldMap.put(58, map.getValue());
                        break;
                    case 62:
                        boardFieldMap.put(45, map.getValue());
                        break;
                    case 63:
                        boardFieldMap.put(32, map.getValue());
                        break;
                    case 64:
                        boardFieldMap.put(19, map.getValue());
                        break;
                    case 65:
                        boardFieldMap.put(6, map.getValue());
                        break;
                    case 66:
                        boardFieldMap.put(59, map.getValue());
                        break;
                    case 67:
                        boardFieldMap.put(46, map.getValue());
                        break;
                    case 68:
                        boardFieldMap.put(33, map.getValue());
                        break;
                    case 69:
                        boardFieldMap.put(20, map.getValue());
                        break;
                    case 70:
                        boardFieldMap.put(7, map.getValue());
                        break;
                    case 71:
                        boardFieldMap.put(60, map.getValue());
                        break;
                    case 72:
                        boardFieldMap.put(47, map.getValue());
                        break;
                    case 73:
                        boardFieldMap.put(34, map.getValue());
                        break;
                    case 74:
                        boardFieldMap.put(21, map.getValue());
                        break;
                    case 75:
                        boardFieldMap.put(8, map.getValue());
                        break;
                    case 76:
                        boardFieldMap.put(61, map.getValue());
                        break;
                    case 77:
                        boardFieldMap.put(48, map.getValue());
                        break;
                    case 78:
                        boardFieldMap.put(35, map.getValue());
                        break;
                    case 79:
                        boardFieldMap.put(22, map.getValue());
                        break;
                    case 80:
                        boardFieldMap.put(9, map.getValue());
                        break;
                    case 81:
                        boardFieldMap.put(62, map.getValue());
                        break;
                    case 82:
                        boardFieldMap.put(49, map.getValue());
                        break;
                    case 83:
                        boardFieldMap.put(36, map.getValue());
                        break;
                    case 84:
                        boardFieldMap.put(23, map.getValue());
                        break;
                    case 85:
                        boardFieldMap.put(10, map.getValue());
                        break;
                    case 86:
                        boardFieldMap.put(63, map.getValue());
                        break;
                    case 87:
                        boardFieldMap.put(50, map.getValue());
                        break;
                    case 88:
                        boardFieldMap.put(37, map.getValue());
                        break;
                    case 89:
                        boardFieldMap.put(24, map.getValue());
                        break;
                    case 90:
                        boardFieldMap.put(11, map.getValue());
                        break;
                    case 91:
                        boardFieldMap.put(70, map.getValue());
                        break;
                    case 92:
                        boardFieldMap.put(95, map.getValue());
                        break;
                    case 93:
                        boardFieldMap.put(89, map.getValue());
                        break;
                    case 94:
                        boardFieldMap.put(83, map.getValue());
                        break;
                    case 95:
                        boardFieldMap.put(77, map.getValue());
                        break;
                }

            }
        }

        if(baseMap != null && !baseMap.isEmpty())
            for(int i = 96;i<=115;i++){
                boardFieldMap.put(i,baseMap.get(i));
            }
    }

    private void getFieldLocation(int top, int left ,int imageHeight, int imageWidth){

        fieldLocationMap = new HashMap<>();

        boardCenterX = imageWidth/2;
        boardCenterY = imageHeight/2;

        float height = (float) (imageHeight * (12.638888/100));
        float base = (float) ((2 * height ) / Math.tan(Math.toRadians(54)));

        fieldSize = (int) (base/3);

        int firstCellX = (int) ((boardCenterX - base/2) + fieldSize /2);
        int firstCellY = (int) ((boardCenterY + height) + fieldSize /2);

        int key = 1;

        for (int i = 0; i < 3; i++) {

            int x1 = (int) (firstCellX + fieldSize * i);

            for (int j = 0; j < 6; j++) {

                int y1 = (int) (firstCellY + fieldSize * j);
                fieldLocationMap.put(key++, new int[]{x1, y1});

                int [] location2 = performRotation5P(x1,y1);
                fieldLocationMap.put(key++,new int[]{location2[0],location2[1]});

                int [] location3 = performRotation5P(location2[0],location2[1]);
                fieldLocationMap.put(key++,new int[]{location3[0],location3[1]});


                int [] location4 = performRotation5P(location3[0],location3[1]);
                fieldLocationMap.put(key++,new int[]{location4[0],location4[1]});

                int[] location5 = performRotation5P(location4[0],location4[1]);
                fieldLocationMap.put(key++,new int[]{location5[0],location5[1]});

            }
        }
    }
    private void drawBase(){

        baseMap = new HashMap<>();

        int firstX = (int) (boardCenterX - boardWidth*.166) ;
        int firstY = (int) (boardCenterY + boardHeight*.2282);
        int secondX = (int) (boardCenterX - boardWidth*.2069);
        int secondY = (int) (boardCenterY + boardHeight*.2820);
        int thirdX = (int) (boardCenterX - boardWidth*.275);
        int thirdY = (int) (boardCenterY + boardHeight*.260);
        int fourthX = (int) (boardCenterX - boardWidth*.1654);
        int fourthY = (int) (boardCenterY + boardHeight*.3416);

        baseMap.put(key++, new int[]{firstX,firstY});
        baseMap.put(key++, new int[]{secondX,secondY});
        baseMap.put(key++, new int[]{thirdX,thirdY});
        baseMap.put(key++, new int[]{fourthX,fourthY});

        drawBlueBase(firstX,firstY , secondX, secondY, thirdX, thirdY , fourthX ,fourthY);

    }
    private void drawBlueBase(int x1, int y1,int x2, int y2, int x3, int y3, int x4, int y4){

//        int[][] arr = new int[4][2];
//
//        arr[0][0] = x1;
//        arr[0][1] = y1;
//        arr[1][0] = x2;
//        arr[1][1] = y2;
//        arr[2][0] = x3;
//        arr[2][1] = y3;
//        arr[3][0] = x4;
//        arr[3][1] = y4;
//
//        for(int i=0;i<4;i++) {
//
//            int[] location = performRotation5P(arr[i][0],arr[i][1]);
//            baseMap.put(key++,new int[]{location[0],location[1]});
//        }

        int[] loc1 = performRotation5P(x1,y1);
        baseMap.put(key++,new int[]{loc1[0],loc1[1]});
        int[] loc2 = performRotation5P(x2,y2);
        baseMap.put(key++,new int[]{loc2[0],loc2[1]});
        int[] loc3 = performRotation5P(x3,y3);
        baseMap.put(key++,new int[]{loc3[0],loc3[1]});
        int[] loc4 = performRotation5P(x4,y4);
        baseMap.put(key++,new int[]{loc4[0],loc4[1]});

        drawOrangeBase(loc1[0],loc1[1], loc2[0],loc2[1],loc3[0],loc3[1] ,loc4[0],loc4[1]  );

    }
    private void drawOrangeBase(int x1, int y1,int x2, int y2, int x3, int y3, int x4, int y4){

        int[] loc1 = performRotation5P(x1,y1);
        baseMap.put(key++,new int[]{loc1[0],loc1[1]});
        int[] loc2 = performRotation5P(x2,y2);
        baseMap.put(key++,new int[]{loc2[0],loc2[1]});
        int[] loc3 = performRotation5P(x3,y3);
        baseMap.put(key++,new int[]{loc3[0],loc3[1]});
        int[] loc4 = performRotation5P(x4,y4);
        baseMap.put(key++,new int[]{loc4[0],loc4[1]});

        drawYellowBase(loc1[0],loc1[1], loc2[0],loc2[1],loc3[0],loc3[1] ,loc4[0],loc4[1]  );

    }
    private void drawYellowBase(int x1, int y1,int x2, int y2, int x3, int y3, int x4, int y4){

        int[] loc1 = performRotation5P(x1,y1);
        baseMap.put(key++,new int[]{loc1[0],loc1[1]});
        int[] loc2 = performRotation5P(x2,y2);
        baseMap.put(key++,new int[]{loc2[0],loc2[1]});
        int[] loc3 = performRotation5P(x3,y3);
        baseMap.put(key++,new int[]{loc3[0],loc3[1]});
        int[] loc4 = performRotation5P(x4,y4);
        baseMap.put(key++,new int[]{loc4[0],loc4[1]});

        drawGreenBase(loc1[0],loc1[1], loc2[0],loc2[1],loc3[0],loc3[1] ,loc4[0],loc4[1]);

    }
    private void drawGreenBase(int x1, int y1,int x2, int y2, int x3, int y3, int x4, int y4){
        int[] loc1 = performRotation5P(x1,y1);
        baseMap.put(key++,new int[]{loc1[0],loc1[1]});
        int[] loc2 = performRotation5P(x2,y2);
        baseMap.put(key++,new int[]{loc2[0],loc2[1]});
        int[] loc3 = performRotation5P(x3,y3);
        baseMap.put(key++,new int[]{loc3[0],loc3[1]});
        int[] loc4 = performRotation5P(x4,y4);
        baseMap.put(key++,new int[]{loc4[0],loc4[1]});
    }
    private int[] performRotation5P(int x, int y){

        int x1 = (int) (((x - boardCenterX) * Math.cos(Math.toRadians(-72))) - ((y-boardCenterY)*Math.sin(Math.toRadians(-72))));
        int y1 = (int) (((x- boardCenterY )* Math.sin(Math.toRadians(-72))) + ((y - boardCenterY)*Math.cos(Math.toRadians(-72))));

        return new int[]{boardCenterX+ x1,boardCenterY + y1};
    }

    private float angle;
    private void setBoardColor() {
        if (angle == MyConstants.ScreenRotation5P.ANGLE_GREEN) {
            bottomLeftColor = GREEN;
            bottomRightColor = RED;
            middleLeftColor = YELLOW;
            middleRightColor = BLUE;
            topColor = ORANGE;
        } else if (angle == MyConstants.ScreenRotation5P.ANGLE_YELLOW) {
            bottomLeftColor = YELLOW;
            bottomRightColor = GREEN;
            middleLeftColor = GREEN;
            middleRightColor = RED;
            topColor = BLUE;
        } else if (angle == MyConstants.ScreenRotation5P.ANGLE_BLUE) {
            bottomLeftColor = BLUE;
            bottomRightColor = ORANGE;
            middleLeftColor = RED;
            middleRightColor = YELLOW;
            topColor = GREEN;
        }else if (angle == MyConstants.ScreenRotation5P.ANGLE_ORANGE) {
            bottomLeftColor = ORANGE;
            bottomRightColor = YELLOW;
            middleLeftColor = BLUE;
            middleRightColor = GREEN;
            topColor = RED;
        }
        else {
            bottomLeftColor = RED;
            bottomRightColor = BLUE;
            middleLeftColor = GREEN;
            middleRightColor = ORANGE;
            topColor = YELLOW;
        }
    }
    private void rotateGameBoard(Player.PlayerColor currPlayerColor) {
        switch (currPlayerColor) {
            case GREEN:
                rotateGameBoard(MyConstants.ScreenRotation5P.ANGLE_GREEN);
                break;
            case YELLOW:
                rotateGameBoard(MyConstants.ScreenRotation5P.ANGLE_YELLOW);
                break;
            case BLUE:
                rotateGameBoard(MyConstants.ScreenRotation5P.ANGLE_BLUE);
                break;
            case ORANGE:
                rotateGameBoard(MyConstants.ScreenRotation5P.ANGLE_ORANGE);
                break;
        }
        setBoardColor();
    }
    private void rotateGameBoard(float angle) {
        this.angle = angle;
        ObjectAnimator animation = ObjectAnimator.ofFloat(gameBoardView, ImageView.ROTATION, 0, angle);
        animation.setDuration(1);
//        animation.addListener(rotateListener);
        gameBoardRotating = true;
        animation.start();
    }

    private boolean handleClick(View view){

        switch (view.getId()) {

            case R.id.done_add_remove:
//                hideAddRemoveView();
                return true;

            case R.id.bottomLeft_addRemove_parent:
                onAddRemoveClick(view, bottomLeftColor);
                return true;
            case R.id.bottomRight_addRemove_parent:
                onAddRemoveClick(view, bottomRightColor);
                return true;
            case R.id.topLeft_addRemove_parent:
                onAddRemoveClick(view, middleLeftColor);
                return true;
            case R.id.topRight_addRemove_parent:
                onAddRemoveClick(view, middleRightColor);
                return true;
            case R.id.bottom_addRemove_parent:
                onAddRemoveClick(view, topColor);
                return true;


            case R.id.settings:
                try {
                    SoundHandling.getInstance().playButtonTapSound(getContext());
                    dismissGameSettingDialog();
                    showGameSettingDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.back_btn:
                SoundHandling.getInstance().playButtonTapSound(getContext());
                onBackPressAction();
                return true;
            case R.id.add_remove_player:
                SoundHandling.getInstance().playButtonTapSound(getContext());
//                if( removePlayerDialog == null && addPlayerDialog == null )
//                    showAddRemovePlayerDialog();
//                showAddRemoveView();
                return true;
            default:
                return false;
        }
    }

    public void onBackPressAction() {
        if (game != null && game.isRunning()) {
            showGameExitDialog();
        } else {
//            goBack();
        }
    }
    private GameExitDialog.ExitDialogListener exitDialogListener = new GameExitDialog.ExitDialogListener() {
        @Override
        public void onYes() {
            if (getContext() != null) {

                dismissGameExitDialog();
                //TODO:
//                showSaveGameDialog();
//                logUserChancesPlayed();
                //goBack();
            }
        }

        @Override
        public void onNo() {
            if (getContext() != null) {
                dismissGameExitDialog();
            }
        }
    };
    private void resumeMove() {
        if (isMovePaused) {
            isMovePaused = false;
            ;
//            onMoveAvailable();
        }
    }
    private boolean isMovePaused = false;

    @Override
    public void onStart() {
        super.onStart();

        gameBoardView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    initFieldLocation();
                    if (game != null) {
                        game.resetTokensPosition();
                        if (drawingView != null) {
                            drawingView.invalidate();
                        }

                        getSizeAndPositions(getContext(), angle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showGameExitDialog() {
        try {
            dismissGameExitDialog();
            gameExitDialog = new GameExitDialog(getContext(), getActivity(), MyConstants.LudoOrSnakeLadderMode.LUDO);
            gameExitDialog.setExitDialogListener(exitDialogListener);
            gameExitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (gameExitDialog != null) {
                        gameExitDialog.clearActivity();
                    }
                    if (exitDialogListener != null) {
                        exitDialogListener.onNo();
                    }
                }
            });
            gameExitDialog.show();
            pause = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissGameExitDialog() {
        if (gameExitDialog != null) {
            if (gameExitDialog.isShowing()) {
                gameExitDialog.dismiss();
            }
            pause = false;
            resumeMove();
            gameExitDialog = null;
        }
    }
    private int previousSelectedToken;
    private GameSettingsDialog gameSettingsDialog;
    private void showGameSettingDialog() {
        gameSettingsDialog = new GameSettingsDialog(getContext(), MyConstants.LudoOrSnakeLadderMode.LUDO);
        previousSelectedToken = Storage.getInstance().getSelectedGameToken();

        gameSettingsDialog.setGameSettingsCallback(new GameSettingsDialog.GameSettingsCallback() {
            @Override
            public void onShakeChange() {
//                if (getContext() != null) {
//                    Storage.getInstance().setCanShakeDevice(!Storage.getInstance().canShakeDevice());
//                    gameSettingsDialog.setShakeToggle();
//                    if(Storage.getInstance().canShakeDevice()){
//                        sd.start(sm);
//                    } else {
//                        sd.stop();
//                    }
//                }
            }

            @Override
            public void onSoundChange() {
                if (getContext() != null) {
                    Storage.getInstance().setSound(getContext(),
                            !Storage.getInstance().isSound(getContext()));
                    if (gameSettingsDialog != null) {
                        gameSettingsDialog.setSoundToggle();
                    }
                }
            }

            @Override
            public void onMusicChange() {
//                if (getContext() != null) {
//                    Storage.getInstance().setMusic(getContext(),
//                            !Storage.getInstance().isMusic(getContext()));
//                    if (gameSettingsDialog != null) {
//                        gameSettingsDialog.setMusicToggle();
//                    }
//                }
            }

            @Override
            public void onLocaleSelected() {
                if (getContext() != null) {
                    gameSettingsDialog.setOnDismissEvent(false);
                    gameSettingsDialog.dismiss();
                    dismissLocaleDialog();
                    showLocaleDialog();
                }
            }

            /*@Override
            public void onRateUs() {
                if (getActivity() != null){
                    try {
                        CommonUtil.openRateUs(getContext());
                    }catch (Exception ignored){

                    }
                }
            }*/

            @Override
            public void onFeedback() {
                gameSettingsDialog.dismiss();
                handlerWriteTousRowClick();
            }

            @Override
            public void onDismissed() {
                pause = false;
                resumeMove();
            }

            @Override
            public void onHapticFeedbackParent() {
                Storage.getInstance().setHapticFeedBackEnabled(
                        !Storage.getInstance().isHapticFeedBackEnabled());
                if (gameSettingsDialog != null) {
                    gameSettingsDialog.setHapticFeedBackToggle();
                }
            }

            @Override
            public void onBoardsAndTokenChange() {
            }
        });
        pause = true;
        gameSettingsDialog.show();
    }

    private void handlerWriteTousRowClick() {
        CommonUtil.onWriteUs(getContext(), null);
    }

    private void showLocaleDialog() {
        if (getContext() != null) {
            dismissLocaleDialog();
            localeDialog = new LocaleDialog(getContext(), R.style.SlidingDialog, MyConstants.LudoOrSnakeLadderMode.LUDO);
            localeDialog.setCallback(dialog -> dismissLocaleDialog());
            localeDialog.show();
            pause = true;
        }
    }

    private void dismissLocaleDialog() {
        if (localeDialog != null) {
            if (localeDialog.isShowing()) {
                localeDialog.dismiss();
            }
            pause = false;
            resumeMove();
            localeDialog = null;
        }
    }

    private void dismissGameSettingDialog() {

        if (gameSettingsDialog != null) {

            if (gameSettingsDialog.isShowing())
                gameSettingsDialog.dismiss();

            gameSettingsDialog = null;
        }
    }

    private void onAddRemoveClick(View view, Player.PlayerColor color) {
        if (view.getTag().equals(addTag)) {
//            showAddPlayerDialog(color);
        } else if (view.getTag().equals(removeTag)) {
            Player player = game.getPlayer(color);
//            if (player != null)
//                showRemovePlayerDialog(player);
        }
    }


    private String addTag = "add";
    private String removeTag = "remove";


    private int getTokenTranslateSpeed() {

        int normalSpeed = 170;
        int currentSpeed = Storage.getInstance().getTokenSpeed();

        if (currentSpeed == MyConstants.TokenSpeed.NORMAL)
            return normalSpeed;
        else if (currentSpeed == MyConstants.TokenSpeed.SLOW) {
            return normalSpeed + (int) (normalSpeed * 0.11); // time increased by 11%
        } else {
            return normalSpeed - (int) (normalSpeed * 0.23); //time decreased by 23%
        }
    }

    private void dismissGameOverDialog() {
        if (gameOverDialog != null) {
            if (gameOverDialog.isShowing()) {
                gameOverDialog.dismiss();
            }
            gameOverDialog = null;
        }
        pause = false;
    }


    private HashMap<String, ImageView> tokensImage;
    private void createTokens() {

        TokenImageHolder.getInstance().setHeight(fieldSize - 5);
        TokenImageHolder.getInstance().setWidth(fieldSize -5 );

        tokensImage = new HashMap<>();

        int tokenH = TokenImageHolder.getInstance().getHeight();
        int tokenW = TokenImageHolder.getInstance().getWidth();


        List<Player> players = game.getPlayers();

        if (drawingArea_token != null) {
            drawingArea_token.removeAllViews();
        }

//        for (int i = 0; i < players.size(); i++) {
//            Player player = players.get(i);
//
//
//            for (int j = 0; j < player.getTokens().length; j++) {
//
//                Log.i(TAG,"Color " + player.getTokens().length);
//                int drawable = TokenImageHolder.getInstance().getTokenRes(getContext(), player.getPlayerColor());
//                ImageView imageView = new ImageView(getContext());
//                imageView.setBackgroundResource(drawable);
//                imageView.setLayoutParams(new FrameLayout.LayoutParams(tokenW, tokenH));
//                imageView.setId(MyConstants.tokenIds[i][j]);
//
//                imageView.setX(left + boardFieldMap.get( 96 + (i*4) + j)[0] - tokenW/2);
//                imageView.setY(top + boardFieldMap.get( 96 + (i*4 ) + j)[1] - tokenH/2);
//
//                drawingArea_token.addView(imageView);
//                tokensImage.put(player.getPlayerColor().ordinal() + "_" + player.getTokens()[j].getId(), imageView);
//
//            }
//        }
//        int drawable1 = TokenImageHolder.getInstance().getTokenRes(getContext(),YELLOW);
//        ImageView imageView1 = new ImageView(getContext());
//        imageView1.setBackgroundResource(drawable1);
//        imageView1.setLayoutParams(new FrameLayout.LayoutParams(tokenW, tokenH));
//
//        for(int i=1;i< 90; i++){
//
//            int drawable = TokenImageHolder.getInstance().getTokenRes(getContext(),RED);
//            ImageView imageView = new ImageView(getContext());
//            imageView.setBackgroundResource(drawable);
//            imageView.setLayoutParams(new FrameLayout.LayoutParams(tokenW, tokenH));
//
//            if(i==1) {
//                imageView1.setX(left + boardCenterX - tokenW / 2);
//                imageView1.setY(top + boardCenterY - tokenH / 2);
//                drawingArea_token.addView(imageView1);
//            }
//
//            imageView.setX(left+ fieldLocationMap.get(i)[0] - tokenW/2);
//            imageView.setY( top + fieldLocationMap.get(i)[1] - tokenH/2);
//
//            drawingArea_token.addView(imageView);
//        }
        for(int i=1;i<=90; i++){
            int drawable = TokenImageHolder.getInstance().getTokenRes(getContext(),RED);
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(drawable);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(tokenW, tokenH));

            imageView.setX(left+ fieldLocationMap.get(i)[0] - tokenW/2);
            imageView.setY( top + fieldLocationMap.get(i)[1] - tokenH/2);

            drawingArea_token.addView(imageView);
        }
        for(int i=96;i<=115; i++){
            int drawable = TokenImageHolder.getInstance().getTokenRes(getContext(),GREEN);
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(drawable);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(tokenW, tokenH));

            imageView.setX(left+ baseMap.get(i)[0] - tokenW/2);
            imageView.setY( top + baseMap.get(i)[1] - tokenH/2);

            drawingArea_token.addView(imageView);
        }

//        getSizeAndPositions(getContext(), angle);
    }

    public void getSizeAndPositions(Context context, final float angle) {

        Set<BoardField> filled = new HashSet<>();
        for (Player player : game.getPlayers()) {
            for (Token token : player.getTokens()) {
                BoardField field = game.getFieldWithId(token.getCurrentPositionIndex());
                if (field != null) {
                    filled.add(field);
                }
            }
        }
        List<BoardField> filledFields = new ArrayList<>(filled);
        Collections.sort(filledFields, new Comparator<BoardField>() {
            @Override
            public int compare(BoardField b1, BoardField b2) {
                if (b1.getRenderLevel((int) angle) > b2.getRenderLevel((int) angle)) {
                    return -1;
                } else if (b1.getRenderLevel((int) angle) < b2.getRenderLevel((int) angle)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        boolean insideField;
        int top;
        int left;
        int centerX;
        int centerY;
        int weight;
        int centerDisposition;
        int tokenWidth;
        int tokenHeight;
        Token token;

        for (BoardField field : filledFields) {

            for (int i = 0; i < field.tokenSize(); i++) {
                token = field.getToken(i);
                if (token != null) {
                    token = game.getToken(token.getColor(), token.getId());
                }
                if (token != null && GameFragment.boardFieldMap != null && !GameFragment.boardFieldMap.isEmpty()) {
//                    LinearLayout boardField = GameFragment.boardFieldMap.get(token.getCurrentPositionIndex());
                    Bitmap tokenImage = null;
                    if (context != null) {
                        tokenImage = TokenImageHolder.getInstance().getTokenBitmap(context, token.getColor(), isReCreateNewToken);
                    }
                    if (tokenImage != null) {
                        int[] locations = GameFragment.boardFieldMap.get(token.getCurrentPositionIndex());
//                        boardField.getLocationInWindow(locations);

                        if (locations != null/* && (token.getCurrentX() == 0 || token.getCurrentY() == 0)*/) {

                            token.setCurrentX(locations[0]);
                            token.setCurrentY(locations[1]);
                        }

                        insideField = game.isInsideBoardField(field.getId(), angle, token.getCurrentX(), token.getCurrentY());

                        int currentTokenSize;
                        if (insideField) {
                            currentTokenSize = field.tokenSize();
                        } else {
                            currentTokenSize = field.tokenSize() > 1 ? field.tokenSize() - 1 : field.tokenSize();
                        }

                        Log.i(TAG,"Field Size " + fieldSize);

                        if (insideField) {
                            weight = (fieldSize / (currentTokenSize + 1));
                        } else {
                            weight = (fieldSize / (2));
                        }

                        if (insideField) {
                            centerDisposition = (weight + (weight * (i)));
                        } else {
                            centerDisposition = weight;
                        }


                        if (insideField && !token.canMove()) {
//                            tokenWidth = tokenImage.getWidth() / currentTokenSize;
                            if (currentTokenSize > 1) {
                                /*tokenWidth = (tokenImage.getWidth()*2) / (currentTokenSize+1);*/
                                tokenWidth = (fieldSize * 2) / (currentTokenSize + 1);
                            } else {
                                tokenWidth = tokenImage.getWidth();
                            }
                        } else {
                            tokenWidth = tokenImage.getWidth();
                        }

                        /*if (gameBoardType == MyConstants.GameRule.BOARD_1) {
//                            tokenHeight = (tokenWidth * 73) / 47;
                            tokenHeight = (tokenWidth * 219) / 116;
                        } else {
                            tokenHeight = *//*(tokenWidth * 42) / 35*//*tokenWidth;
                        }*/

                        if (Storage.getInstance().getSelectedGameToken() == MyConstants.GameRule.TOKEN_5) {
                            tokenHeight = tokenWidth;
                        } else {
                            tokenHeight = (tokenWidth * 73) / 47;
                        }

//                        tokenHeight = (tokenWidth * 73) / 47;

                        centerX = token.getCurrentX() + centerDisposition;
                        centerY = token.getCurrentY() + (fieldSize / 2);
                        /*switch (gameBoardType) {
                         *//*case MyConstants.GameRule.BOARD_1:
                                top = centerY - (tokenHeight) + (9 * tokenHeight) / 73;
                                break;*//*
                            default:
                                top = centerY - (tokenHeight / 2);
                                break;
                        }*/

                        switch (Storage.getInstance().getSelectedGameToken()) {
                            case MyConstants.GameRule.TOKEN_5:
                                top = centerY - (tokenHeight / 2);
                                break;
                            default:
                                top = centerY - tokenHeight + (fieldSize / 2);
                                break;
                        }

//                        top = centerY - (tokenHeight) + (9 * tokenHeight) / 73;
//                        top = centerY - tokenHeight + (fieldSize / 2);

                        left = centerX - (tokenWidth / 2);
                        token.setRect(new Rect(left, top, left + tokenWidth, top + tokenHeight));
                        if (tokensImage != null) {
                            ImageView view = getImageView(token)/*tokensImage.get(token.getColor().ordinal() + "_" + token.getId())*/;

                            if (view != null) {
                                view.getLayoutParams().width = tokenWidth;
                                view.getLayoutParams().height = tokenHeight;
                                view.bringToFront();
                                view.setX(left);
                                view.setY(top);

                                view.requestLayout();
                            }
                        }
                    }
                }
            }
        }
        isReCreateNewToken = false;
    }

    private ImageView getImageView(Token token) {

        if (tokensImage != null) {
            ImageView view = tokensImage.get(token.getColor().ordinal() + "_" + token.getId());
            return view;
        }

        return null;
    }

}
