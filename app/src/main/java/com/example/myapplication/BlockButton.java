package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("AppCompatCustomView")
public class BlockButton extends Button {
    int x;
    int y;
    int neighborX;
    int neighborY;
    boolean mine = false;
    boolean flag = false;
    int neighborMines = 0;
    static int flags;
    static int blocks = 71;
    static int mines = 10;

    int[][] neighbor = {{-1, -1}, {-1, 0}, {-1, 1}
            , {0, -1}, {0, 1}
            , {1, -1}, {1, 0}, {1, 1}};

    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
    }

    public void toggleFlag(TextView mineText) {
        flag = !flag;
        if (flag) {
            setText("🚩");
            mines--;
            mineText.setText("Mines : " + mines);
            flags++;
        }
        else {
            setText("");
            mines++;
            mineText.setText("Mines : "+mines);
            flags--;
        }
    }

    public void setNeighborMines(BlockButton[][] buttons) {

        for (int i = 0; i < neighbor.length; i++) {
            neighborX = x + neighbor[i][0];
            neighborY = y + neighbor[i][1];

            if (neighborX >= 0 && neighborX < 9 && neighborY >= 0 && neighborY < 9) {
                if (buttons[neighborX][neighborY].mine == true)
                    neighborMines++;
            }
        }
    }

    public void breakBlock(BlockButton[][] buttons, int x, int y) {
        if(!flag) {
            if (buttons[x][y].mine == true) {
                setText("💣");

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        buttons[i][j].setClickable(false);
                    }
                }

                Toast.makeText(getContext(), "GAME OVER😭", Toast.LENGTH_LONG).show();
            } else {
                setText(String.valueOf(neighborMines));
                buttons[x][y].setClickable(false);
                blocks--;

                if (blocks == 0) {
                    Toast.makeText(getContext(), "🎉🎉 WIN🥳 🎉🎉", Toast.LENGTH_LONG).show();

                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            buttons[i][j].setClickable(false);
                        }
                    }
                }

                if (neighborMines == 0) {
                    for (int i = 0; i < neighbor.length; i++) {
                        neighborX = x + neighbor[i][0];
                        neighborY = y + neighbor[i][1];

                        if (neighborX >= 0 && neighborX < 9 && neighborY >= 0 && neighborY < 9
                                && buttons[neighborX][neighborY].isClickable() == true) {
                            buttons[neighborX][neighborY].breakBlock(buttons, neighborX, neighborY);
                        }
                    }
                }
            }
        }
    }
}
