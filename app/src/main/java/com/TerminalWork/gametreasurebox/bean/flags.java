package com.TerminalWork.gametreasurebox.bean;

import android.graphics.Rect;

public class flags {
    public final static int direction_UP = 1;
    public final static int direction_LEFT = 2;
    public final static int direction_DOWN = 3;
    public final static int direction_RIGHT = 4;
    public static int direction = direction_UP;

    public final static int scaleScheme_composite = 5;
    public final static int scaleScheme_production = 7;

    public final static int number_hrd = 8;
    public final static int image_hrd = 9;
    public final static int threeKingdom_hrd = 10;
    public static int current_sort_hrd = 0;
    public static int last_sort_hrd = 0;

    public final static int _2048Fragment = 11;
    public final static int gameSelectFragment = 12;
    public final static int hanoiFragment = 13;
    public final static int hrdFragment = 14;
    public final static int selectHrdFragment = 15;


    public static int card_width;

    public static Rect[][] myView = new Rect[6][10];

    public static int[][][] check_point = new int[][][]{
            {

            },//将标志位0空出去，舍弃掉
            {
                    {306, 320}, {106, 120}, {106, 320}, {106, 420}, {206, 420}
                        , {6, 420}, {6, 120}, {6, 320}, {306, 120}, {306, 220}
            },
            {
                    {306, 320}, {106, 120}, {106, 320}, {6, 320}, {306, 120}
                        , {6, 120}, {106, 420}, {206, 420}, {6, 520}, {306, 520}
            },
            {
                    {6, 120}, {106, 120}, {106, 320}, {6, 420}, {106, 420}
                    , {206, 420}, {306, 120}, {306, 220}, {306, 320}, {306, 420}
            },
            {
                    {6, 220}, {206, 420}, {6, 120}, {6, 420}, {206, 220}
                    , {106, 420}, {206, 120}, {306, 120}, {106, 320}, {206, 320}
            }
    };

    public static int[][] Order16 = new int [][]{
            {0, 12, 14, 13, 4, 8, 9, 11, 5, 1, 2, 6, 3, 7, 10},
            {5, 11, 6, 10, 14, 2, 3, 12, 13, 4, 7, 1, 0, 9, 8},
            {1, 10, 11, 0, 3, 14, 12, 7, 13, 2, 4, 5, 6, 8, 9},
            {6, 1, 12, 13, 14, 0, 9, 4, 7, 10, 2, 3, 5, 8, 11},
            {13, 9, 14, 3, 4, 0, 1, 8, 5, 2, 6, 10, 11, 12, 7},
            {1, 8, 4, 0, 12, 2, 3, 5, 10, 6, 13, 14, 9, 11, 7},
            {9, 10, 3, 11, 12, 14, 5, 6, 2, 7, 0, 4, 8, 13, 1},
            {2, 13, 0, 11, 4, 14, 1, 8, 5, 6, 9, 10, 3, 7, 12}
    };

    public static int[][] Order9 = new int[][]{
            {6, 4, 7, 0, 5, 3, 1, 2},
            {0, 1, 2, 3, 4, 5, 6, 7},
            {2, 3, 5, 4, 6, 0, 7, 1},
            {1, 7, 6, 5, 2, 3, 0, 4},
            {7, 6, 4, 0, 1, 2, 5, 3},
            {3, 4, 5, 6, 2, 7, 0, 1},
            {5, 6, 4, 7, 0, 3, 1, 2},
            {2, 4, 5, 3, 6, 7, 0, 1},
            {3, 7, 1, 4, 5, 6, 0, 2}
    };

    public static int[][] Order25 = new int[][]{
            {19, 15, 6, 2, 14, 16, 17, 20, 11, 12, 7, 13, 0, 1,
                    9, 4, 18, 3, 21, 5, 8, 10, 22, 23},
            {15, 20, 21, 13, 5, 22, 7, 6, 16, 9, 17, 12, 14, 3,
                    10, 8, 18, 23, 11, 0, 4, 1, 19, 2},
            {2, 22, 7, 9, 1, 20, 11, 17, 0, 4, 5, 12, 21, 23,
                    3, 6, 8, 10, 18, 13, 14, 15, 16, 19},
            {18, 23, 19, 20, 3, 15, 10, 21, 11, 7, 12, 1, 8, 22,
                    4, 5, 6, 9, 16, 0, 2, 13, 14, 17}
    };



}