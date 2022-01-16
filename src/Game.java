package src;
import java.util.ArrayList;

public class Game{
    final int HWITE = -2;
    final int WHITE = -1;
    final int BLANK = 0;
    final int BLACK = 1;
    final int HBLACK = 2;
    final int BOARD_SIZE = 8;

    private int gid;
    private static int gameCnt = 1;
    private Player whitePlayer;
    private Player blackPlayer;
    private ArrayList<Step> stepList = new ArrayList<Step>();
    private int[][] board;

    public Game(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        board = new int[BOARD_SIZE][BOARD_SIZE];
        board[3][3] = WHITE;
        board[4][4] = WHITE;
        board[3][4] = BLANK;
        board[4][3] = BLANK;
        this.gid = Game.gameCnt;
        Game.gameCnt++;
    }

    public boolean checkStep(int sid) {
        boolean result = false;
        for (int i = 0; i < stepList.size(); i++) {
            if(sid == stepList.get(i).getSid()){
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean addStep(Step step) {
        if(checkStep(step.getSid())){
            return false;
        }
        else{
            stepList.add(step);
            return true;
        }
    }

    public String toString() {
        String stepListString = new String();
        stepListString = stepListString.concat("[");
        for (int i = 0; i < stepList.size(); i++){
            stepListString = stepListString.concat(stepList.get(i).toString());
        }
        stepListString = stepListString.concat("]");

        String boardString = new String();
        boardString = boardString.concat("[");
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardString = boardString.concat(board[i].toString());
        }
        boardString = boardString.concat("]");

        return String.format("gid: %d, whitePlayerId: %d, blackPlayerId: %d, stepList: %s, board: %s", gid, stepListString ,boardString);
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                this.board[i][j] = board[i][j];
            }
        }
    }

    public int getGid() {
        return gid;
    }

    public static int getGameCnt() {
        return gameCnt;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }

    public void setStepList(ArrayList<Step> stepList) {
        this.stepList = stepList;
    }

}
