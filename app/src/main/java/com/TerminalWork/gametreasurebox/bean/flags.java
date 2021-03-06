package com.TerminalWork.gametreasurebox.bean;

import android.graphics.Rect;

import com.TerminalWork.gametreasurebox.R;

/*
 * 作者：JiaTai Sun
 * 时间：20-1-24 下午3:30
 * 类名：flag
 * 功能：定义一些用于app过程的常量
 */

public class flags {
    //汉诺塔演示过程中盘子的移动方向
    public final static int direction_UP = 1;
    public final static int direction_LEFT = 2;
    public final static int direction_DOWN = 3;
    public final static int direction_RIGHT = 4;
    public static int direction = direction_UP;

    //2048游戏中的方块的缩放模式
    public final static int scaleScheme_composite = 5;
    public final static int scaleScheme_production = 7;

    //标志当前和之前华容道的类型
    public static int current_sort_hrd = 0;
    public static int last_sort_hrd = 0;

    //标志各个fragment
    public final static int _2048Fragment = 11;
    public final static int gameSelectFragment = 12;
    public final static int hanoiFragment = 13;
    public final static int hrdFragment = 14;
    public final static int selectHrdFragment = 15;

    //“请求”常量
    public final static int activityRequestCode_upLoadImage = 16;
    public final static int activityRequestCode_cameraUpLoad = 17;

    //单位化常量用于刻画大小
    public static int card_width;
    public static int gapHeight;
    public static int gapWidth;
    public static int unitWidth;
    public static int unitHeight;

    //标志用到的intentAction名称
    public final static String action_changeScore2048 = "com.TerminalWork.gametreasurebox.action.changeScore2048";
    public final static String action_changStepsKingdomHrd = "com.TerminalWork.gametreasurebox.action.changeStepsKingdomHrd";
    public final static String action_changStepsImageHrd = "com.TerminalWork.gametreasurebox.action.changeStepsImageHrd";
    public final static String action_changStepsNumberHrd = "com.TerminalWork.gametreasurebox.action.changeStepsNumberHrd";
    public final static String action_updateAccountImage = "com.TerminalWork.gametreasurebox.action.updateAccountImage";
    public final static String action_KingdomHrd_success = "com.TerminalWork.gametreasurebox.action.kingdomHrdSuccess";

    //用于记录三国华容道中的每个滑块的位置
    public static Rect[][] myView = new Rect[7][10];

    //三国华容道中的每个关卡各个位置的图片
    public static int[][] check_point_image = new int[][]{
            {

            },
            {
                    R.drawable.zhao_yun, R.drawable.cao_cao, R.drawable.guan_yu, R.drawable.huang_zhong, R.drawable.ma_chao,
                    R.drawable.zhang_fei, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.entry, R.drawable.stop,
                    R.drawable.stop
            },
            {
                    R.drawable.zhao_yun, R.drawable.cao_cao, R.drawable.guan_yu, R.drawable.huang_zhong, R.drawable.ma_chao,
                    R.drawable.zhang_fei, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.entry, R.drawable.stop,
                    R.drawable.stop
            },
            {
                    R.drawable.zhao_yun, R.drawable.cao_cao, R.drawable.guan_yu, R.drawable.huang_zhong, R.drawable.ma_chao,
                    R.drawable.zhang_fei, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.entry, R.drawable.stop,
                    R.drawable.stop
            },
            {
                    R.drawable.zhao_yun_y, R.drawable.cao_cao, R.drawable.guan_yu, R.drawable.huang_zhong, R.drawable.ma_chao_y,
                    R.drawable.zhang_fei, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.entry, R.drawable.stop,
                    R.drawable.stop
            },
            {
                    R.drawable.zhao_yun, R.drawable.cao_cao, R.drawable.guan_yu, R.drawable.huang_zhong, R.drawable.ma_chao,
                    R.drawable.zhang_fei, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.zu, R.drawable.entry, R.drawable.stop,
                    R.drawable.stop
            }
    };

    //记录三国华容道中各个滑块的长宽倍数
    public static int[][][] check_point = new int[][][]{
            {

            },//将标志位0空出去，舍弃掉
            {
                    {1, 2}, {2, 2}, {2, 1}, {1, 2}, {1, 2},
                    {1, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                    {2, 2}, {1, 2}, {1, 2}
            },
            {
                    {1, 2}, {2, 2}, {2, 1}, {1, 2}, {1, 2},
                    {1, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                    {2, 2}, {1, 2}, {1, 2}
            },
            {
                    {1, 2}, {2, 2}, {2, 1}, {1, 2}, {1, 2},
                    {1, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                    {2, 2}, {1, 2}, {1, 2}
            },
            {
                    {2, 1}, {2, 2}, {2, 1}, {1, 2}, {2, 1},
                    {1, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                    {2, 2}, {1, 2}, {1, 2}
            },
            {
                    {1, 2}, {2, 2}, {2, 1}, {1, 2}, {1, 2},
                    {1, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                    {2, 2}, {1, 2}, {1, 2}
            }
    };

    //记录三国华容道中各个滑块的左上角位置
    public static int[][][] check_point_location = new int[][][]{
            {

            },//将标志位0空出去，舍弃掉
            {
                    {3, 2}, {1, 0}, {1, 2}, {1, 3}, {2, 3}
                    , {0, 3}, {0, 0}, {0, 2}, {3, 0}, {3, 1}
                    , {1, 5}, {0, 5}, {3, 5}
            },
            {
                    {3, 2}, {1, 0}, {1, 2}, {0, 2}, {3, 0}
                    , {0, 0}, {1, 3}, {2, 3}, {0, 4}, {3, 4}
                    , {1, 5}, {0, 5}, {3, 5}
            },
            {
                    {0, 0}, {1, 0}, {1, 2}, {0, 3}, {1, 3}
                    , {2, 3}, {3, 0}, {3, 1}, {3, 2}, {3, 3}
                    , {1, 5}, {0, 5}, {3, 5}
            },
            {
                    {0, 1}, {2, 3}, {0, 0}, {0, 3}, {2, 1}
                    , {1, 3}, {2, 0}, {3, 0}, {1, 2}, {2, 2}
                    , {1, 5}, {0, 5}, {3, 5}
            },
            {
                    {3, 2}, {1, 2}, {1, 0}, {0, 2}, {3, 0}
                    , {0, 0}, {1, 1}, {2, 1}, {0, 4}, {3, 4}
                    , {1, 5}, {0, 5}, {3, 5}
            }
    };

    //在数字华容道和图像华容道中用到的内置顺序
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

    //当在图片与数字华容道为3*3时的各个合法序列
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

    //当在图片与数字华容道为5*5时的各个合法序列
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

    //按键间隔
    public static final int BACK_PRESSED_INTERVAL = 2000;

}