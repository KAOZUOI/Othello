package src;

import java.util.ArrayList;

public class Step{
    final int HWITE = -2;
    final int WHITE = -1;
    final int BLANK = 0;
    final int BLACK = 1;
    final int HBLACK = 2;
    final int BOARD_SIZE = 8;

    private int sid;
    private int rowIndex;
    private int columnIndex;
    private int color;
    private static int stepCnt = 1;

    public Step(int rowIndex, int columnIndex, int color) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.color = color;
        this.sid = Step.stepCnt;
        Step.stepCnt++;
    }

    public int getSid() {
        return sid;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static int getStepCnt() {
        return stepCnt;
    }

    public String toString() {
        return String.format("sid: %d, rowIndex: %d, columnIndex: %d, color: %d", this.sid, this.rowIndex, this.columnIndex, this.color);
    }

    public static boolean canPlay(int[][] board, int player, int j, int k) {
        if(board[j][k] != 0) return false;

        int mj, mk, count;
        int oplayer = - player;

        mj = j;
        mk = k - 1;
        count = 0;
        while((mk > 0) && (board[mj][mk] == oplayer)){
            mk--;
            count++;
        }
        if((mk >= 0) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j;
        mk = k + 1;
        count = 0;
        while((mk < 7) && (board[mj][mk] == oplayer)){
            mk++;
            count++;
        }
        if((mk < 8) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j - 1;
        mk = k;
        count = 0;
        while((mj > 0) && (board[mj][mk] == oplayer)){
            mj--;
            count++;
        }
        if((mj >= 0) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j + 1;
        mk = k;
        count = 0;
        while((mj < 7) && (board[mj][mk] == oplayer)){
            mj++;
            count++;
        }
        if((mj < 8) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j - 1;
        mk = k - 1;
        count = 0;
        while((mj > 0) && (mk > 0) &&(board[mj][mk] == oplayer)){
            mj--;
            mk--;
            count++;
        }
        if((mj >= 0) && (mk >= 0) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j + 1;
        mk = k + 1;
        count = 0;
        while((mj < 7) && (mk < 7) &&(board[mj][mk] == oplayer)){
            mj++;
            mk++;
            count++;
        }
        if((mj < 8) && (mk < 8) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j + 1;
        mk = k - 1;
        count = 0;
        while((mj < 7) && (mk > 0) &&(board[mj][mk] == oplayer)){
            mj++;
            mk--;
            count++;
        }
        if((mj < 8) && (mk >= 0) && (board[mj][mk] == player) && (count > 0)) return true;

        mj = j - 1;
        mk = k + 1;
        count = 0;
        while((mj > 0) && (mk < 7) &&(board[mj][mk] == oplayer)){
            mj--;
            mk++;
            count++;
        }
        if((mj >= 0) && (mk < 8) && (board[mj][mk] == player) && (count > 0)) return true;

        return false;
    }

    public static int[][] getReversiBoard(int[][] board, int player, int j, int k) {
        int oplayer = - player;
        int mj, mk, count;
        board[j][k] = player;

        mj = j;
        mk = k - 1;
        count = 0;
        while((mk > 0) && (board[mj][mk] == oplayer)){
            mk--;
            count++;
        }
        if((mk >= 0) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j][k - i] = player;
            }
        }

        mj = j;
        mk = k + 1;
        count = 0;
        while((mk < 7) && (board[mj][mk] == oplayer)){
            mk++;
            count++;
        }
        if((mk < 8) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j][k + i] = player;
            }
        }

        mj = j - 1;
        mk = k;
        count = 0;
        while((mj > 0) && (board[mj][mk] == oplayer)){
            mj--;
            count++;
        }
        if((mj >= 0) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k] = player;
            }
        }

        mj = j + 1;
        mk = k;
        count = 0;
        while((mj < 7) && (board[mj][mk] == oplayer)){
            mj++;
            count++;
        }
        if((mj < 8) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k] = player;
            }
        }

        mj = j - 1;
        mk = k - 1;
        count = 0;
        while((mj > 0) && (mk > 0) &&(board[mj][mk] == oplayer)){
            mj--;
            mk--;
            count++;
        }
        if((mj >= 0) && (mk >= 0) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k - i] = player;
            }
        }

        mj = j + 1;
        mk = k + 1;
        count = 0;
        while((mj < 7) && (mk < 7) &&(board[mj][mk] == oplayer)){
            mj++;
            mk++;
            count++;
        }
        if((mj < 8) && (mk < 8) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k + i] = player;
            }
        }

        mj = j + 1;
        mk = k - 1;
        count = 0;
        while((mj < 7) && (mk > 0) &&(board[mj][mk] == oplayer)){
            mj++;
            mk--;
            count++;
        }
        if((mj < 8) && (mk >= 0) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k - i] = player;
            }
        }

        mj = j - 1;
        mk = k + 1;
        count = 0;
        while((mj > 0) && (mk < 7) &&(board[mj][mk] == oplayer)){
            mj--;
            mk++;
            count++;
        }
        if((mj >= 0) && (mk < 8) && (board[mj][mk] == player) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k + i] = player;
            }
        }

        return board;
    }

}
