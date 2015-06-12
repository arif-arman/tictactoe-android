package com.ugabuga.zitu.myapplication;

/**
 * Created by user on 12-Jun-15.
 * X=1, O=2, Blank=0
 */
public class Board {
    private int[][] blocks = new int[3][3];

    public Board() {
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                blocks[i][j] = 0;
    }

    public int[][] getBoard() {
        return blocks;
    }

    public int evaluateBoard() {
        if(XWon()) return 1;
        else if(OWon()) return -1;
        return 0;
    }

    public boolean isGameOver() {
        return (XWon() || OWon() || boardFull());
    }

    public boolean XWon() {
        if((blocks[0][0] == blocks[1][1] && blocks[1][1] == blocks[2][2] && blocks[2][2] == 1) ||
                (blocks[0][2] == blocks[1][1] && blocks[1][1] == blocks[2][0] && blocks[2][0] == 1))
            return true;
        for(int i=0;i<3;i++) {
            if((blocks[i][0] == blocks[i][1] && blocks[i][1] == blocks[i][2] && blocks[i][2] == 1) ||
                    (blocks[0][i] == blocks[1][i] && blocks[1][i] == blocks[2][i] && blocks[2][i] == 1))
                return true;
        }
        return false;
    }

    public boolean OWon() {
        if((blocks[0][0] == blocks[1][1] && blocks[1][1] == blocks[2][2] && blocks[2][2] == 2) ||
                (blocks[0][2] == blocks[1][1] && blocks[1][1] == blocks[2][0] && blocks[2][0] == 2))
            return true;
        for(int i=0;i<3;i++) {
            if((blocks[i][0] == blocks[i][1] && blocks[i][1] == blocks[i][2] && blocks[i][2] == 2) ||
                    (blocks[0][i] == blocks[1][i] && blocks[1][i] == blocks[2][i] && blocks[2][i] == 2))
                return true;
        }
        return false;
    }

    public boolean boardFull() {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(blocks[i][j] == 0) return false;
        return true;
    }

    public boolean updateBoard(Point p, int player) {
        if(blocks[p.x][p.y] == 0) {
            blocks[p.x][p.y] = player;
            return true;
        }
        return false;

    }

    public void clearBoard() {
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                blocks[i][j] = 0;
    }

    public String toString() {
        String str = "";
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if(blocks[i][j] == 1) str +=  "X\t";
                else if(blocks[i][j] == 2) str += "O\t";
                else str += "-\t";

            }
            str += "\n";
        }
        return str;
    }
}
