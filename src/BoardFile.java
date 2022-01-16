package src;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class BoardFile {
    final int HWITE = -2;
    final int WHITE = -1;
    final int BLANK = 0;
    final int BLACK = 1;
    final int HBLACK = 2;
    final int BOARD_SIZE = 8;
    private File file;
    String path;

    public BoardFile(String path) {
        file = new File(path);
        this.path = path;
    }

    public BoardFile(Game game) throws IOException {
        String name = game.getGid() + ".txt";
        path = "D:/ReversiData/" + name;
        file = new File(path);
        file.createNewFile();
    }

    public int isValid(){
        if(!isFileError()){
            return 104;
        }
        else if(!isBoardError()){
            return 101;
        }
        else if(!isOtherError()){
            return 106;
        }
        else if(!isDiskError()){
            return 102;
        }
        else if(!isPlayerError()){
            return 103;
        }
        else if(!isModelError()){
            return 105;
        }
        else{
            return 100;
        }
    }
    
    private boolean isBoardError() {
        for(int i = 1; i <= BOARD_SIZE; i++){
            try {
                String tempData = getLineData(i);
                String [] temps = tempData.split(" ");
                if(temps.length == BOARD_SIZE){
                    return true;
                }
                else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isDiskError() {
        try {
            int [][] board = getFileOldBoard();
            int [] count = new int[3];
            for(int i = 0; i < BOARD_SIZE; i++){
                for(int j = 0; j < BOARD_SIZE; j++){
                    if(board[i][j] == BLACK){
                        count[0]++;
                    }
                    else if(board[i][j] == WHITE){
                        count[1]++;
                    }
                    else if(board[i][j] == BLANK){
                        count[2]++;
                    }
                    else {
                        return false;
                    }
                }
            }
            if((count[0] == 0) || (count[1] == 0) || (count[2] == 0)){
                return false;
            }
            else if(count[0] + count[1] + count[2] != 64){
                return false;
            }
            else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isPlayerError() {
        try {
            if((getPlayer(BOARD_SIZE + 1) == 1) || (getPlayer(BOARD_SIZE + 1) == -1)){
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isFileError() {
        String temp = path.substring(path.length() -3);
        if(temp.equals("txt")){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isModelError() {
        boolean result = true;
        try {
            for(int i = 9; i <= getFileLine(); i += 9){
                if(getModel(i).equals("N")){
                    if(!isValidStep(i)){
                        result = false;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean isOtherError() {
        boolean result = true;
        for(int i = 1; i <= BOARD_SIZE; i++){
                String tempData;
                try {
                    tempData = getLineData(i);
                    String [] temps = tempData.split(" ");
                    for(int j = 0; j < temps.length; j++){
                        if((!temps[j].equals("0")) && (!temps[j].equals("1")) && (!temps[j].equals("-1"))){
                            result = false;
                            break;
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }

    public boolean isValidStep(int lineNumber) throws IOException{
        int [][]board = getFileBoard(lineNumber);
        if(Controller.canPlay(board, getPlayer(lineNumber), getCoordinate(lineNumber)[0], getCoordinate(lineNumber)[1])){
            return true;
        }
        else{
            return false;
        }
    }

    public int getFileLine() throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
        int lineNumber = 0;
        while (lineNumberReader.readLine() != null){
            lineNumber++;
        }
        return lineNumber;
    }

    public String getLineData(int lineNumber) throws IOException{
        FileReader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        String txt = "";
        String result = "";
        int lines = 0;
        while (txt != null) {
            lines++;
            txt = reader.readLine();
            if (lines == lineNumber) {
                result = txt;
                long timeEnd = System.currentTimeMillis();
                break;
            }
        }
        reader.close();
        fileReader.close();
        return result;
    }

    public String getModel(int lineNumber) throws IOException{
        String model;
        String data = getLineData(lineNumber);
        String [] temp = data.split(" ");
        model = temp[0];
        return model;
    }

    public int getPlayer(int lineNumber) throws IOException{
        int player;
        String data = getLineData(lineNumber);
        String [] temp = data.split(" ");
        player = Integer.parseInt(temp[1]);
        return player;
    }

    public int[] getCoordinate(int lineNumber) throws IOException{
        int [] coordinate = new int[2];
        String data = getLineData(lineNumber);
        String [] temp = data.split(" ");
        coordinate[0] = Integer.parseInt(temp[2]);
        coordinate[1] = Integer.parseInt(temp[3]);
        return coordinate;
    }

    public void setStepList(Game game) throws IOException{
        int fileLine = getFileLine();
        for (int i = BOARD_SIZE + 1; i <= fileLine; i += BOARD_SIZE){
            int [] coordinate = getCoordinate(i);
            int color = getPlayer(i);
            Step tempStep = new Step(coordinate[0], coordinate[1], color);
            game.addStep(tempStep);
        }
    }

    public int[][] getFileOldBoard() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int [][] board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            String lineNumber = br.readLine();
            String[] tempS = lineNumber.split(" ");
            for (int j = 0; j < BOARD_SIZE; j++){
                board[i][j] = Integer.parseInt(tempS[j]);
            }
        }
        return board;
    }

    public int[][] getFileNewBoard() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int [][] board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.println(getLineData(1));
            String lineNumber = getLineData(getFileLine() - 8 + i);
            System.out.println(lineNumber);
            String[] tempS = lineNumber.split(" ");
            for (int j = 0; j < BOARD_SIZE; j++){
                board[i][j] = Integer.parseInt(tempS[j]);
            }
        }
        return board;
    }

    public int[][] getFileBoard(int line) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int [][] board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = BOARD_SIZE; i > 0; i--) {
            String lineNumber = getLineData(line - i);
            String[] tempS = lineNumber.split(" ");
            for (int j = 0; j < BOARD_SIZE; j++){
                board[BOARD_SIZE - i][j] = Integer.parseInt(tempS[j]);
            }
        }
        return board;
    }

    public void cleanNineLines() throws IOException{
        ArrayList<String> dataList = new ArrayList();
        for (int i = 1; i <= getFileLine() - 9; i++){
            dataList.add(getLineData(i));
        }
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int j = 0; j < dataList.size(); j++){
            String temp = dataList.get(j) + "\n";
            writeMethod(temp);
        }
    }
    
    public void writeBoard(int [][]board){
        BufferedWriter out = null;
        String conent = null;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.setLength(sb.length());
        conent = String.valueOf(sb);
        writeMethod(conent);
    }

    public void writeData(String model, int player, int x, int y){
        String conent = null;
        StringBuilder sb = new StringBuilder();
        sb.append(model).append(" ").append(player).append(" ").append(x).append(" ").append(y).append("\n");
        conent = String.valueOf(sb);
        writeMethod(conent);
    }
    public void writeMethod(String conent) {
        BufferedWriter out = null ;
        try  {
            out = new  BufferedWriter( new  OutputStreamWriter(new  FileOutputStream(path,  true )));
            out.write(conent);
        } catch  (Exception e) {
            e.printStackTrace();
        } finally  {
            try  {
                out.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
