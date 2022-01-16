package src;

public class Controller {

    public static int[][] canPut(int[][] board, int currentColor){
        int[][] resultBoard = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (canPlay(board, currentColor, i, j)) {
                    if (currentColor == 1) {
                        resultBoard[i][j] = 2;
                    }
                    else if(currentColor == -1){
                        resultBoard[i][j] = -2;
                    }
                }
            }
        }
        
        return resultBoard;

    }
    public static boolean judge(int[][] board, int currentColor){
        boolean blackCanPut = true;
        boolean whiteCanPut = true;
        int blackCanPutCnt = 0;
        int whiteCanPutCnt = 0;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (canPlay(board, currentColor, i, j)) {
                    if (currentColor == 1) {
                        blackCanPutCnt++;
                    }
                    else if(currentColor == -1){
                        whiteCanPutCnt++;
                    }
                }
            }
        }
        
        if (currentColor == 1) {
            if (blackCanPutCnt == 0) {
                blackCanPut = false;
            }
            return blackCanPut;
        }
        else{
            if (whiteCanPutCnt == 0) {
                whiteCanPut = false;
            }
            return whiteCanPut;
        }
    

    }
    public static boolean canPlay(int[][] board, int currentColor, int row, int column){
        int[][] directions = new int[][]{
        {0,1},{1,0},{0,-1},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}
        };
        for (int i = 0; i < 8; i++) {
            if (canPlayDirection(board,currentColor,row,column,directions[i])) {
                return true;
            }
        }
        return false;
    }
    public static int abcd(int[][] board, int currentColor, int row, int column){
        int count = 0;
        int[][] directions = new int[][]{
        {0,1},{1,0},{0,-1},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}
        };
        for (int i = 0; i < 8; i++) {
            count +=abc(board, currentColor, row, column, directions[i]);
        }
        return count;
    }
    public static boolean canPlayDirection(int[][] board, int currentColor, int row, int column, int[] direction){
        if (board[row][column] != 0) {
            return false;
        }
        int count = 0;
        boolean hasEnd = false;
        int dx = direction[0];
        int dy = direction[1];
        while (row+dx < 8&&row+dx >= 0&&column+dy < 8&&column+dy >= 0) {
            row += dx;
            column += dy;
            if (board[row][column] == 0) {
                break;
            }else if (board[row][column] == currentColor) {
                hasEnd = true;
                break;
            }else{
                count++;
            }
        }
        if (count > 0&&hasEnd) {
            return true;
        }else{
            return false;
        }

    }
    public static int abc(int[][] board, int currentColor, int row, int column, int[] direction){
        if (board[row][column] != 0) {
            return 0;
        }
        int count = 0;
        boolean hasEnd = false;
        int dx = direction[0];
        int dy = direction[1];
        while (row+dx < 8&&row+dx >= 0&&column+dy < 8&&column+dy >= 0) {
            row += dx;
            column += dy;
            if (board[row][column] == 0) {
                break;
            }else if (board[row][column] == currentColor) {
                hasEnd = true;
                break;
            }else{
                count++;
            }
        }
        if (count > 0&&hasEnd) {
            return count;
        }else{
            return 0;
        }

    }
    public static int[][] getReversiBoard(int[][] board, int currentColor, int j, int k) {
        //j = y;
        //k = x;
        int ocurrentColor = - currentColor;
        int mj, mk, count;
        board[j][k] = currentColor;

        mj = j;
        mk = k - 1;
        count = 0;
        while((mk > 0) && (board[mj][mk] == ocurrentColor)){
            mk--;
            count++;
        }
        if((mk >= 0) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j][k - i] = currentColor;
            }
        }

        mj = j;
        mk = k + 1;
        count = 0;
        while((mk < 7) && (board[mj][mk] == ocurrentColor)){
            mk++;
            count++;
        }
        if((mk < 8) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j][k + i] = currentColor;
            }
        }

        mj = j - 1;
        mk = k;
        count = 0;
        while((mj > 0) && (board[mj][mk] == ocurrentColor)){
            mj--;
            count++;
        }
        if((mj >= 0) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k] = currentColor;
            }
        }

        mj = j + 1;
        mk = k;
        count = 0;
        while((mj < 7) && (board[mj][mk] == ocurrentColor)){
            mj++;
            count++;
        }
        if((mj < 8) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k] = currentColor;
            }
        }

        mj = j - 1;
        mk = k - 1;
        count = 0;
        while((mj > 0) && (mk > 0) &&(board[mj][mk] == ocurrentColor)){
            mj--;
            mk--;
            count++;
        }
        if((mj >= 0) && (mk >= 0) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k - i] = currentColor;
            }
        }

        mj = j + 1;
        mk = k + 1;
        count = 0;
        while((mj < 7) && (mk < 7) &&(board[mj][mk] == ocurrentColor)){
            mj++;
            mk++;
            count++;
        }
        if((mj < 8) && (mk < 8) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k + i] = currentColor;
            }
        }

        mj = j + 1;
        mk = k - 1;
        count = 0;
        while((mj < 7) && (mk > 0) &&(board[mj][mk] == ocurrentColor)){
            mj++;
            mk--;
            count++;
        }
        if((mj < 8) && (mk >= 0) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j + i][k - i] = currentColor;
            }
        }

        mj = j - 1;
        mk = k + 1;
        count = 0;
        while((mj > 0) && (mk < 7) &&(board[mj][mk] == ocurrentColor)){
            mj--;
            mk++;
            count++;
        }
        if((mj >= 0) && (mk < 8) && (board[mj][mk] == currentColor) && (count > 0)){
            for (int i = 1; i <= count; i++){
                board[j - i][k + i] = currentColor;
            }
        }

        return board;
    }

}
