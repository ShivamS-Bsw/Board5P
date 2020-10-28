package com.example.board5player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blacklightsw.ludooffline.game.Player;


/**
 * Created by BlackLight on 6/5/17.
 */

public class TokenImageHolder {

    private static TokenImageHolder instance;
    private Bitmap greenToken;
    private Bitmap redToken;
    private Bitmap yellowToken;
    private Bitmap blueToken;
    private int height;
    private int width;
    private int gameBoardType;

    private TokenImageHolder() {
        /*initialize with and height to default 1 so it prevents the
        crash while decoding the resource on zero width and height*/
        height = 1;
        width = 1;
    }

    public static TokenImageHolder getInstance() {
        return instance == null ? instance = new TokenImageHolder() : instance;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public void setGameBoardType(int gameBoardType) {
        this.gameBoardType = gameBoardType;
    }

    public Bitmap getTokenBitmap(Context context, Player.PlayerColor color, boolean isRecreateNewToken) {
        try {
//            return getNewTokenBitmap(context, color);
            switch (Storage.getInstance().getSelectedGameToken()){
                /*case MyConstants.GameRule.TOKEN_1:
                    return getNewTokenBitmap(context, color);

                case MyConstants.GameRule.TOKEN_4:
                    return getTokenBitmap4(context, color);*/
                case MyConstants.GameRule.TOKEN_5:
                    return getNewTokenBitmap(context, color, isRecreateNewToken);

                default:
                    return getTokenBitmap4(context, color, isRecreateNewToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getNewTokenBitmap(Context context, Player.PlayerColor color, boolean isRecreateNewToken) {
        Bitmap image;
        switch (color) {
            case RED:
                image = (redToken == null || isRecreateNewToken) ? redToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.red_token), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : redToken;
                break;
            case GREEN:
                image = (greenToken == null || isRecreateNewToken) ? greenToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.green_token), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : greenToken;
                break;
            case YELLOW:
                image = (yellowToken == null || isRecreateNewToken) ? yellowToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_token), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : yellowToken;
                break;
            case BLUE:
                image = (blueToken == null || isRecreateNewToken) ? blueToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_token), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : blueToken;
                break;
            default:
                image = (redToken == null || isRecreateNewToken) ? redToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.red_token), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : redToken;
                break;
        }
        return image;
    }
    private Bitmap getTokenBitmap4(Context context, Player.PlayerColor color, boolean isRecreateNewToken) {
        Bitmap image;
        switch (color) {
            case RED:
                image = (redToken == null || isRecreateNewToken) ? redToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.red_token_4), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : redToken;
                break;
            case GREEN:
                image = (greenToken == null || isRecreateNewToken) ? greenToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.green_token_4), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : greenToken;
                break;
            case YELLOW:
                image = (yellowToken == null || isRecreateNewToken) ? yellowToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_token_4), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : yellowToken;
                break;
            case BLUE:
                image = (blueToken == null || isRecreateNewToken) ? blueToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_token_4), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : blueToken;
                break;
            default:
                image = (redToken == null || isRecreateNewToken) ? redToken = Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.red_token_4), width, height, true)
                        .copy(Bitmap.Config.ARGB_8888, true) : redToken;
                break;
        }
        return image;
    }


    private BitmapFactory.Options getOpTion() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void dispose() {
        if (redToken != null) {
            redToken.recycle();
        }
        if (greenToken != null) {
            greenToken.recycle();
        }
        if (yellowToken != null) {
            yellowToken.recycle();
        }
        if (blueToken != null) {
            blueToken.recycle();
        }
        redToken = null;
        greenToken = null;
        yellowToken = null;
        blueToken = null;
        instance = null;
    }


    ////////////////////////////////////

    public int getTokenRes(Context context, Player.PlayerColor color) {
        if(Storage.getInstance().getSelectedGameToken() == MyConstants.GameRule.TOKEN_1)
            return getNewTokenRes1(context, color);
        else if(Storage.getInstance().getSelectedGameToken() == MyConstants.GameRule.TOKEN_2)
            return getNewTokenRes2(context, color);
        else if(Storage.getInstance().getSelectedGameToken() == MyConstants.GameRule.TOKEN_3)
            return getNewTokenRes3(context, color);
        else if(Storage.getInstance().getSelectedGameToken() == MyConstants.GameRule.TOKEN_4)
            return getNewTokenRes4(context, color);
        else
            return getNewTokenRes(context, color);
    }

    private int getNewTokenRes(Context context, Player.PlayerColor color) {
        switch (color) {
            case RED:
                return R.drawable.red_token;
            case GREEN:
                return R.drawable.green_token;
            case YELLOW:
                return R.drawable.yellow_token;
            case BLUE:
                return R.drawable.blue_token;
            case ORANGE:
                return R.drawable.yellow_token;
            default:
                return R.drawable.red_token;
        }
    }

    private int getNewTokenRes4(Context context, Player.PlayerColor color) {
        switch (color) {
            case RED:
                return R.drawable.red_token_4;

            case GREEN:
                return R.drawable.green_token_4;

            case YELLOW:
                return R.drawable.yellow_token_4;

            case BLUE:
                return R.drawable.blue_token_4;
            default:
                return R.drawable.red_token_4;
        }
    }

    private int getNewTokenRes1(Context context, Player.PlayerColor color) {
        switch (color) {
            case RED:
                return R.drawable.red_token_1;

            case GREEN:
                return R.drawable.green_token_1;

            case YELLOW:
                return R.drawable.yellow_token_1;

            case BLUE:
                return R.drawable.blue_token_1;
            default:
                return R.drawable.red_token_1;
        }
    }

    private int getNewTokenRes2(Context context, Player.PlayerColor color) {
        switch (color) {
            case RED:
                return R.drawable.red_token_2;

            case GREEN:
                return R.drawable.green_token_2;

            case YELLOW:
                return R.drawable.yellow_token_2;

            case BLUE:
                return R.drawable.blue_token_2;
            default:
                return R.drawable.red_token_2;
        }
    }


    private int getNewTokenRes3(Context context, Player.PlayerColor color) {
        switch (color) {
            case RED:
                return R.drawable.red_token_3;

            case GREEN:
                return R.drawable.green_token_3;

            case YELLOW:
                return R.drawable.yellow_token_3;

            case BLUE:
                return R.drawable.blue_token_3;
            default:
                return R.drawable.red_token_3;
        }
    }

}
