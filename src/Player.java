package src;
public class Player {
    final int HWITE = -2;
    final int WHITE = -1;
    final int BLANK = 0;
    final int BLACK = 1;
    final int HBLACK = 2;
    final int BOARD_SIZE = 8;

    private int pid;
    private String name;
    private static int playerCnt = 1;

    public Player(String name) {
        this.name = name;
        this.pid = playerCnt++;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getPlayerCnt() {
        return playerCnt;
    }
    
    public String toString() {
        return String.format("Player: %s, pid: %d", name, pid);
    }
}
