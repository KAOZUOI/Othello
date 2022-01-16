package src;
import java.util.ArrayList;

public class GameSystem {
    final int HWITE = -2;
    final int WHITE = -1;
    final int BLANK = 0;
    final int BLACK = 1;
    final int HBLACK = 2;
    final int BOARD_SIZE = 8;

    private ArrayList<Player> playerList;
    private ArrayList<Game> gameList;

    public GameSystem() {
        playerList = new ArrayList<Player>();
        gameList = new ArrayList<Game>();
    }

    public boolean checkPlayer(int pid) {
        boolean result = false;
        for(int i = 0; i < playerList.size(); i++) {
            if(pid == playerList.get(i).getPid()){
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean checkGame(int gid) {
        boolean result = false;
        for(int i = 0; i < gameList.size(); i++){
            if(gid == gameList.get(i).getGid()){
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean addPlayer(Player player) {
        if(checkPlayer(player.getPid())){
            return false;
        }
        else{
            playerList.add(player);
            return true;
        }
    }

    public boolean addGame(Game game) {
        if(checkGame(game.getGid())){
            return false;
        }
        else if((checkPlayer(game.getBlackPlayer().getPid()) == false) || (checkPlayer(game.getWhitePlayer().getPid()) == false)){
            return false;
        }
        else{
            gameList.add(game);
            return true;
        }
    }
    
    public ArrayList<Game> listPlayerGame(int pid) {
        ArrayList<Game> hisGameList = new ArrayList<Game>();
        for(int i = 0; i < gameList.size(); i++){
            if((pid == gameList.get(i).getWhitePlayer().getPid()) || (pid == gameList.get(i).getBlackPlayer().getPid())){
                hisGameList.add(gameList.get(i));
            }
        }
        return hisGameList;
    }

    public float calculatePlayerWinRate(int pid) {
        float rate = 0.0f;
        ArrayList<Game> hisGameList = new ArrayList<Game>();
        hisGameList = listPlayerGame(pid);
        if(hisGameList.size() == 0){
            return rate;
        }
        else{
            rate = (float)(getPlayerWinGameCnt(pid)) / (float)(hisGameList.size());
            return rate;
        }
    }

    public int getPlayerWinGameCnt(int pid) {
        int count = 0;
        ArrayList<Game> hisGameList = new ArrayList<Game>();
        hisGameList = listPlayerGame(pid);
        for(int i = 0; i < hisGameList.size(); i++){
            int color;
            if(hisGameList.get(i).getWhitePlayer().getPid() == pid){
                color = WHITE;
            }
            else{
                color = BLACK;
            }
            if(getWhoWin(hisGameList.get(i).getBoard()) == color){
                count++;
            }
        }
        return count;
    }

    
    public int getWhoWin(int[][] board) {
        int countWhite = 0;
        int countBlack = 0;
        for (int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++){
                if(board[i][j] == BLACK){
                    countBlack++;
                }
                else if(board[i][j] == WHITE){
                    countWhite++;
                }
            }
        }
        if(countWhite > countBlack){
            return WHITE;
        }
        else if(countWhite < countBlack){
            return BLACK;
        }
        else{
            return BLANK;
        }
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
