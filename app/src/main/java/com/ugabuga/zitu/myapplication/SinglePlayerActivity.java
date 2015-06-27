package com.ugabuga.zitu.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


/*

Single Player codes

 */

public class SinglePlayerActivity extends ActionBarActivity {

    private Board board;
    private boolean isGameOver = false;
    private int turn = 1;
    private boolean firstMove = true;

    private String player1symbol = "X";
    private String player2symbol = "O";

    private int player1, player2;

    static final int AI = 1;
    static final int USER = 2;

    int[][] idList = { {R.id.Cell11, R.id.Cell12, R.id.Cell13}, {R.id.Cell21, R.id.Cell22, R.id.Cell23}, {R.id.Cell31, R.id.Cell32, R.id.Cell33} };

    TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        Bundle extras = getIntent().getExtras();
        String move = extras.getString("firstMove");
        v = (TextView) findViewById(R.id.CurrentPlayer);
        board = new Board();
        if(move.equals("android")) {
            player1 = AI;
            player2 = USER;
            //Toast.makeText(getApplicationContext(), move, Toast.LENGTH_SHORT).show();
            aiMove();
        }
        else {
            v.setText("Your Move");
            player1 = USER;
            player2 = AI;
            turn = 0;
            firstMove = false;
        }

    }

    public void aiMove() {

        Point pai = null;
        if (!isGameOver && !firstMove) {
            pai = board.alphaBeta();
            //Toast.makeText(getApplicationContext(),pai.x + " " + pai.y, Toast.LENGTH_SHORT).show();


        }
        else {
            Random rand = new Random();
            pai = new Point(rand.nextInt(3), rand.nextInt(3));
            firstMove = false;

        }
        board.updateBoard(pai, AI);
        turn = 1-turn;
        v.setText("Your Move");
        TextView cell = getAIMove(pai);
        if (player1 == AI) cell.setText(player1symbol);
        else cell.setText(player2symbol);
        checkBoard();
    }
    public void resetClicked(View view) {
        turn = 1;
        isGameOver = false;
        board.clearBoard();
        this.clearGrid();
        firstMove = true;
        if (player1 == USER) {
            turn = 0;
            firstMove = false;
            v.setText("Your Move");
        }
        else aiMove();
    }

    public void cellClick(View view) {
        if (turn == 1) return;
        TextView cell = (TextView) findViewById(view.getId());
        String content = (String) cell.getText();
        if (content == "" && !isGameOver) {
            int x = 0,y = 0;
            switch (cell.getId()) {
                case R.id.Cell11:
                    x = 1; y = 1; break;
                case R.id.Cell12:
                    x = 1; y = 2; break;
                case R.id.Cell13:
                    x = 1; y = 3; break;
                case R.id.Cell21:
                    x = 2; y = 1; break;
                case R.id.Cell22:
                    x = 2; y = 2; break;
                case R.id.Cell23:
                    x = 2; y = 3; break;
                case R.id.Cell31:
                    x = 3; y = 1; break;
                case R.id.Cell32:
                    x = 3; y = 2; break;
                case R.id.Cell33:
                    x = 3; y = 3; break;
            }
            Point p = new Point(x-1,y-1);
            if (turn == 0) {
                if (board.updateBoard(p, USER)) {
                    turn = 1-turn;
                    if (player1 == USER) cell.setText(player1symbol);
                    else cell.setText(player2symbol);
                }
            }
            checkBoard();
            if (!isGameOver) {
                aiMove();
            }


        }

    }

    public void checkBoard() {
        if (board.isGameOver()) {
            if (board.XWon()) {
                v.setText("Match Over : Android Wins!!");
                ArrayList<Point> win = board.winningCombination();
                for (int i=0;i<win.size();i++) {
                    Point mark = win.get(i);
                    TextView markCell = (TextView) findViewById(idList[mark.x][mark.y]);
                    markCell.setTextColor(Color.parseColor("#ADE89B"));

                }
            }


            //Toast.makeText(getApplicationContext(), "Android Wins", Toast.LENGTH_LONG).show();
            else if (board.OWon()) {
                v.setText("Match Over : You Win!!");
                ArrayList<Point> win = board.winningCombination();
                for (int i=0;i<win.size();i++) {
                    Point mark = win.get(i);
                    TextView markCell = (TextView) findViewById(idList[mark.x][mark.y]);
                    markCell.setTextColor(Color.parseColor("#ADE89B"));

                }
            }
            //Toast.makeText(getApplicationContext(), "Player Wins", Toast.LENGTH_LONG).show();
            else v.setText("Match Over : Drawn!!");
            //Toast.makeText(getApplicationContext(), "Match Drawn", Toast.LENGTH_LONG).show();
            isGameOver = true;
            board.clearBoard();
        }
    }

    public TextView getAIMove(Point pai) {
        TextView cell = null;
        switch (pai.x+1) {
            case 1:
                switch (pai.y+1) {
                    case 1:
                        cell = (TextView) findViewById(R.id.Cell11); break;
                    case 2:
                        cell = (TextView) findViewById(R.id.Cell12); break;
                    case 3:
                        cell = (TextView) findViewById(R.id.Cell13); break;
                }
                break;
            case 2:
                switch (pai.y+1) {
                    case 1:
                        cell = (TextView) findViewById(R.id.Cell21); break;
                    case 2:
                        cell = (TextView) findViewById(R.id.Cell22); break;
                    case 3:
                        cell = (TextView) findViewById(R.id.Cell23); break;
                }
                break;
            case 3:
                switch (pai.y+1) {
                    case 1:
                        cell = (TextView) findViewById(R.id.Cell31); break;
                    case 2:
                        cell = (TextView) findViewById(R.id.Cell32); break;
                    case 3:
                        cell = (TextView) findViewById(R.id.Cell33); break;
                }
                break;
        }
        return cell;
    }

    public void clearGrid() {
        //Get the id list of all the Textview cells
        //int[] idList = { R.id.Cell11, R.id.Cell12, R.id.Cell13, R.id.Cell21, R.id.Cell22, R.id.Cell23, R.id.Cell31, R.id.Cell32, R.id.Cell33 };
        TextView cell;
        //For each cell clear the text with an empty string
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                cell = (TextView) findViewById(idList[i][j]);
                cell.setText("");
                cell.setTextColor(Color.WHITE);
            }

        }
        //Reset the game state and clear the virtual board

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   */
}
