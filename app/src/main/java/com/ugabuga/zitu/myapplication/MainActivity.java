package com.ugabuga.zitu.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Board board;
    private boolean isGameOver = false;
    private int turn = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new Board();
    }

    public void resetClicked(View view) {
        turn = 0;
        isGameOver = false;
        board.clearBoard();
        this.clearGrid();
    }

    public void cellClick(View view) {
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
                if (board.updateBoard(p,1)) {
                    turn = 1-turn;
                    cell.setText("X");
                }
            }
            else {
                if (board.updateBoard(p,2)) {
                    turn = 1-turn;
                    cell.setText("O");
                }
            }
            if (board.isGameOver()) {
                if (board.XWon()) Toast.makeText(getApplicationContext(), "Player 1 Wins", Toast.LENGTH_LONG).show();
                else if (board.OWon()) Toast.makeText(getApplicationContext(), "Player 2 Wins", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(), "Match Drawn", Toast.LENGTH_LONG).show();
                isGameOver = true;
                board.clearBoard();



            }
        }

    }

    public void clearGrid() {
        //Get the id list of all the Textview cells
        int[] idList = { R.id.Cell11, R.id.Cell12, R.id.Cell13, R.id.Cell21, R.id.Cell22, R.id.Cell23, R.id.Cell31, R.id.Cell32, R.id.Cell33 };
        TextView cell;
    //For each cell clear the text with an empty string
        for (int item : idList) {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }
        //Reset the game state and clear the virtual board

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
