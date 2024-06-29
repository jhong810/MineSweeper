package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table;
        table = (TableLayout)findViewById(R.id.tableLayout);

        int mines = 0;

        TextView mineText = new TextView(this);
        table.addView(mineText);

        ToggleButton toggleButton = new ToggleButton(this);

        BlockButton[][] buttons = new BlockButton[9][9];

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);

                int a = i;
                int b = j;

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggleButton.isChecked()){
                            buttons[a][b].toggleFlag(mineText);
                        }
                        else ((BlockButton) view).breakBlock(buttons, a, b);
                    }
                });
            }
        }

        while (mines!=10){
            Random random = new Random();

            int mineX = random.nextInt(9);
            int mineY = random.nextInt(9);

            if(buttons[mineX][mineY].mine==false){
                buttons[mineX][mineY].mine=true;
                mines++;
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                buttons[x][y].setNeighborMines(buttons);
            }
        }

        mineText.setText("Mines : "+ mines);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    toggleButton.setTextOff("flag");
                    Toast.makeText(MainActivity.this, "🚩Flag Mode🚩", Toast.LENGTH_SHORT).show();
                }

                else if(b==false){
                    toggleButton.setTextOn("Break");
                    Toast.makeText(MainActivity.this, "💣Break Mode💣", Toast.LENGTH_SHORT).show();
                }
            }
        });

        table.addView(toggleButton);


    }
}