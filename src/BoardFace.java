package src;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.*;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardFace {
    
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    BufferedImage BoardTable_1 = ImageIO.read(new File("src/picture/经典棋盘.png"));
    BufferedImage BoardTable_2 = ImageIO.read(new File("src/picture/绿色棋盘.png"));
    int boardSkin = 1;
    BufferedImage White_1 = ImageIO.read(new File("src/picture/white.gif"));
    BufferedImage Black_1 = ImageIO.read(new File("src/picture/black.gif"));
    BufferedImage canPutBlack = ImageIO.read(new File("src/picture/图片3.png"));
    BufferedImage canPutWhite = ImageIO.read(new File("src/picture/图片4.png"));
    BufferedImage Selected = ImageIO.read(new File("src/picture/selected.gif"));
    ImageIcon ProfilePhoto = new ImageIcon("src/picture/狗头.png");
    ImageIcon ProfilePhoto_1 = new ImageIcon("src/picture/twoTimescircle.png");

    ImageIcon battleMode = new ImageIcon("src/picture/renrenduizhan1300 360.png");

    ImageIcon changeSkin = new ImageIcon("src/picture/切换皮肤300 85.png");
    ImageIcon undo = new ImageIcon("src/picture/悔棋18085.png");
    // ImageIcon concede = new ImageIcon("认输17385.png");
    ImageIcon newGame = new ImageIcon("src/picture/开心游戏300 75.png");

    ImageIcon menuPicture = new ImageIcon("src/picture/菜单.png");
    ImageIcon cheatMode = new ImageIcon("src/picture/作弊模式.png");
    ImageIcon normalMode = new ImageIcon("src/picture/对战模式.png");
    ImageIcon loadGame = new ImageIcon("src/picture/载入游戏.png");
    ImageIcon reverse = new ImageIcon("src/picture/规则反转.png");


    final int BOARD_WIDTH = 510;
    final int BOARD_HEIGHT = 510;
    final int PANEL_WIDTH = 310;
    final int BOARD_SIZE = 8;
    final int GRID_SIZE = 64;

    final int offSet = 32;

    // int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    int[][] board = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,1,-1,0,0,0},
        {0,0,0,-1,1,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };

    int a = -1;
    //(x,y)
    //1黑 -1白 0无棋子 2能下的地方（黑） -2能下的地方（白）
    final int PHOTO_HEIGHT = 200;
    final int BUTTON_HEIGHT = 78;
    
    int Selected_X = -1;
    int Selected_Y = -1;
    int currentColor = 1;
    char battleMod;
    int[][] canPlayBoard = new int[8][8];
    String rule = "N";

    
    public JFrame getFrame() {
        return frame;
    }
    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }
    public void setCanPlayBoard(int[][] canPlayBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.canPlayBoard[i][j] = canPlayBoard[i][j];
            }
        }
    }
    public void clean(int[][] canPlayBoard){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.canPlayBoard[i][j] = 0;
            }
        }
    }

    public char getBattleMod() {
        return battleMod;
    }


    public class ChessBoard extends JPanel{
        
        @Override
        public void paint(Graphics g){
            if (boardSkin == 1) {
                g.drawImage(BoardTable_1, 0, 0, null);
            }else if(boardSkin == 2){
                g.drawImage(BoardTable_2, 0, 0, null);
            }
            if (Selected_X >= 0&&Selected_Y >= 0) {
                g.drawImage(Selected, Selected_X*GRID_SIZE, Selected_Y*GRID_SIZE, null);
            }
            
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == 1) {
                        g.drawImage(Black_1, j*GRID_SIZE, i*GRID_SIZE, null);
                    }
                    else if(board[i][j] == -1) {
                        g.drawImage(White_1, j*GRID_SIZE, i*GRID_SIZE, null);
                    }
                    
                    
                    if (InitialFace.mode.equals("N")) {
                        if(canPlayBoard[i][j] == 2){
                            g.drawImage(canPutBlack, j*GRID_SIZE, i*GRID_SIZE, null);
                        }
                        else if(canPlayBoard[i][j] == -2){
                            g.drawImage(canPutWhite, j*GRID_SIZE, i*GRID_SIZE, null);
                        }
                    }
                    if (InitialFace.mode.equals("C")) {
                        if(board[i][j] == 0&&currentColor == 1){
                            g.drawImage(canPutBlack, j*GRID_SIZE, i*GRID_SIZE, null);
                        }
                        else if(board[i][j] == 0&&currentColor == -1){
                            g.drawImage(canPutWhite, j*GRID_SIZE, i*GRID_SIZE, null);
                        }
                    }
                }
            }
        }
        
    }

    ChessBoard chessBoardTable = new ChessBoard();


    public BoardFace() throws IOException{
        
        
        Player whitePlayer = new Player("whitePlayer");
        Player blackPlayer = new Player("blackPlayer");
        Game game = new Game(whitePlayer, blackPlayer);
        BoardFile boardFile = new BoardFile(game);

        panel.setLayout(null);
        JButton pP = new JButton();
        pP.setContentAreaFilled(false);
        pP.setFocusPainted(false);
        pP.setBorder(null);
        pP.setIcon(ProfilePhoto);
        pP.setBounds(0, 0, 300, PHOTO_HEIGHT);
        panel.add(pP);
        JDialog scores = new JDialog();
        scores.setVisible(false);
        scores.setBounds(0, 0, 300, PHOTO_HEIGHT);
        
        JButton cS = new JButton();
        cS.setContentAreaFilled(false);
        cS.setFocusPainted(false);
        cS.setBorder(null);
        cS.setIcon(changeSkin);
        cS.setBounds(0, PHOTO_HEIGHT, 300, BUTTON_HEIGHT);
        panel.add(cS);
        JButton un = new JButton();
        un.setContentAreaFilled(false);
        un.setFocusPainted(false);
        un.setBorder(null);
        un.setIcon(undo);
        un.setBounds(0, PHOTO_HEIGHT+BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
        panel.add(un);
        JButton me = new JButton();
        me.setContentAreaFilled(false);
        me.setFocusPainted(false);
        me.setBorder(null);
        me.setIcon(menuPicture);
        me.setBounds(0, PHOTO_HEIGHT+2*BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
        panel.add(me);
        JButton nG = new JButton();
        nG.setContentAreaFilled(false);
        nG.setFocusPainted(false);
        nG.setBorder(null);
        nG.setIcon(newGame);
        nG.setBounds(0, PHOTO_HEIGHT+3*BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
        panel.add(nG);

        JButton cM = new JButton();
        cM.setContentAreaFilled(false);
        cM.setFocusPainted(false);
        cM.setBorder(null);
        cM.setIcon(cheatMode);
        JButton nM = new JButton();
        nM.setContentAreaFilled(false);
        nM.setFocusPainted(false);
        nM.setBorder(null);
        nM.setIcon(normalMode);
        JButton lG = new JButton();
        lG.setContentAreaFilled(false);
        lG.setFocusPainted(false);
        lG.setBorder(null);
        lG.setIcon(loadGame);
        JButton re = new JButton();
        re.setContentAreaFilled(false);
        re.setFocusPainted(false);
        re.setBorder(null);
        re.setIcon(reverse);
        JPanel scoresPanel = new JPanel();
        scoresPanel.setVisible(false);
        JDialog scoresDialog = new JDialog();
        scoresDialog.setVisible(false);
        scoresDialog.setBounds(0, 0, 300, PHOTO_HEIGHT);
        
        JButton bW = new JButton("黑方胜");
        bW.setVisible(false);
        JButton wW = new JButton("白方胜");
        wW.setVisible(false);
        JButton dr = new JButton("平局");
        wW.setVisible(false);
        JDialog blackWin = new JDialog();
        blackWin.add(bW);
        blackWin.setVisible(false);
        JDialog whiteWin = new JDialog();
        whiteWin.add(wW);
        whiteWin.setVisible(false);
        JDialog draw = new JDialog();
        draw.add(dr);
        draw.setVisible(false);
        JDialog menu = new JDialog();
        menu.setLayout(new GridLayout(4,1));
        menu.setVisible(false);
        menu.add(cM);
        menu.add(nM);
        menu.add(lG);
        menu.add(re);
        
        
        cS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                if (boardSkin == 1) {
                    boardSkin = 2;
                }
                else{
                    boardSkin = 1;
                }
                chessBoardTable.repaint();
            }
        });
        nG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                try {
                    frame.dispose();
                    new BoardFace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                
                    if (a == -1) {
                        pP.setIcon(ProfilePhoto_1);
                        a = 1;
                    }else if(a == 1){
                        pP.setIcon(ProfilePhoto);
                        a = -1;
                    }
                
                
            }
        });
        un.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //undo

                try {
                    board = boardFile.getFileNewBoard();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                currentColor = -currentColor;
                chessBoardTable.repaint();
                try {
                    boardFile.cleanNineLines();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        me.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                menu.pack();
                menu.setVisible(true);
            }
        });
        lG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    InitialFace.path = chooser.getSelectedFile().getPath();
                    InitialFace.path.replace("\\", "/");
                    System.out.println(InitialFace.path);
                    try {
                        InitialFace.boardFace = new BoardFace(InitialFace.path);
                    } catch (IOException e1) {
                        
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        cM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                InitialFace.mode = "C";
            }
        });
        nM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                InitialFace.mode = "N";
            }
        });
        re.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                rule = "R";
            }
        });



        setCanPlayBoard(Controller.canPut(board, currentColor));
        chessBoardTable.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        panel.setBounds(BOARD_WIDTH, 0, 300, BOARD_HEIGHT);
//鼠标移动事件
        chessBoardTable.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Selected_X = e.getX()/GRID_SIZE;
                Selected_Y = e.getY()/GRID_SIZE;
                chessBoardTable.repaint();
            }
        });
    //鼠标点击事件（按下）
        
    chessBoardTable.addMouseListener(new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){

                
                    if (InitialFace.pattern.equals("mM")) {
                        if (InitialFace.mode.equals("N")) {
                            if(currentColor == 1){
                                if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == 2) {
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                }
                                chessBoardTable.repaint();
                            }
                            if(currentColor == -1){ 
                                if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == -2) {
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    //todo writeboard
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
    
    
                                    currentColor = -currentColor;
                                }
                                chessBoardTable.repaint();
                            }
                        }
                        if (InitialFace.mode.equals("C")) {
                            if(currentColor == 1){  
                                Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                boardFile.writeBoard(board);
                                board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                currentColor = -currentColor;
                                chessBoardTable.repaint();
                            }
                            else{ 
                                Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                boardFile.writeBoard(board);
                                board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                currentColor = -currentColor;
                                chessBoardTable.repaint();
                            }
                        }
                    }
                    if (InitialFace.pattern.equals("mA")) {
                        if (InitialFace.mode.equals("N")) {
                            if(currentColor == 1){
                                
                                if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == 2) {
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                }
                                chessBoardTable.repaint();
                                setCanPlayBoard(Controller.canPut(board, currentColor));
                                
                            }
                            if(currentColor == -1){ 
                                boardFile.writeBoard(board);
                                int countY = -1;
                                int countX = -1;
                                int mostDisks = 0;

                                for (int i = 0; i < canPlayBoard.length; i++) {
                                    for (int j = 0; j < canPlayBoard[i].length; j++) {
                                        if ((canPlayBoard[i][j] == -2) && Controller.abcd(board, currentColor, i, j) >= mostDisks) {
                                            mostDisks = Controller.abcd(board, currentColor, i, j);
                                        }
                                    }
                                }
                                
                                outer:for (int i = 0; i < canPlayBoard.length; i++) {
                                    for (int j = 0; j < canPlayBoard[i].length; j++) {
                                        if ((canPlayBoard[i][j] == -2) && Controller.abcd(board, currentColor, i, j) == mostDisks) {
                                            board[i][j] = -1;
                                            countY = i;
                                            countX = j;
                                            break outer;
                                        }
                                    }
                                }
                                
                                if (countX != -1&&countY != -1) {
                                    board = Controller.getReversiBoard(board, currentColor, countY, countX);
                                    boardFile.writeData(InitialFace.mode, currentColor, countY, countX);
                                }
                                currentColor = -currentColor;
                                chessBoardTable.repaint();
                            }
                        }
                        if (InitialFace.mode.equals("C")) {
                            if(currentColor == 1){  
                                Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                boardFile.writeBoard(board);
                                board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                currentColor = -currentColor;
                                chessBoardTable.repaint();
                            }
                            else{ 
                                Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                boardFile.writeBoard(board);
                                board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                currentColor = -currentColor;
                                chessBoardTable.repaint();
                            }
                        }
                    }
        }
    });

//鼠标点击事件（松开）

    chessBoardTable.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseReleased(MouseEvent e){
            if (InitialFace.mode.equals("N")) {
                int blackCnt = 0;
                int whiteCnt = 0;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 1) {
                            blackCnt++;
                        }
                        if (board[i][j] == -1) {
                            whiteCnt++;
                        }
                    }
                }

               
                JLabel whiteScore = new JLabel("黑方得分为"+blackCnt+"\n"+"白方得分为"+whiteCnt);
                scoresPanel.removeAll();
                scoresPanel.add(whiteScore);
                scoresPanel.setVisible(true);
                scoresDialog.add(scoresPanel);
                scoresDialog.setVisible(true);
                setCanPlayBoard(Controller.canPut(board, currentColor));
                if (rule.equals("N")) {
                    if (Controller.judge(board, currentColor) == false) {
                        currentColor = -currentColor;
                        if(Controller.judge(board, currentColor) == false){
                            if (blackCnt > whiteCnt) {
                                blackWin.setVisible(true);
                                bW.setVisible(true);
                                blackWin.pack();
                            }else if(blackCnt < whiteCnt){
                                whiteWin.setVisible(true);
                                wW.setVisible(true);
                                whiteWin.pack();
                            }else if(blackCnt == whiteCnt){
                                draw.setVisible(true);
                                dr.setVisible(true);
                                draw.pack();
                            }
                        }
                    }
                }
                if (rule.equals("R")) {
                    if (Controller.judge(board, currentColor) == false) {
                        currentColor = -currentColor;
                        if(Controller.judge(board, currentColor) == false){
                            if (blackCnt < whiteCnt) {
                                blackWin.setVisible(true);
                                bW.setVisible(true);
                                blackWin.pack();
                            }else if(blackCnt > whiteCnt){
                                whiteWin.setVisible(true);
                                wW.setVisible(true);
                                whiteWin.pack();
                            }else if(blackCnt == whiteCnt){
                                draw.setVisible(true);
                                dr.setVisible(true);
                                draw.pack();
                            }
                        }
                    }
                }
                
                chessBoardTable.repaint();
            }
            if (InitialFace.mode.equals("C")) {
                int blackCnt = 0;
                int whiteCnt = 0;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 1) {
                            blackCnt++;
                        }
                        if (board[i][j] == -1) {
                            whiteCnt++;
                        }
                    }
                }
                JLabel whiteScore = new JLabel("黑方得分为"+blackCnt+"\n"+"白方得分为"+whiteCnt);
                scoresPanel.removeAll();
                scoresPanel.add(whiteScore);
                scoresPanel.setVisible(true);
                scoresDialog.add(scoresPanel);
                scoresDialog.setVisible(true);
                
                chessBoardTable.repaint();
            }
        }
    });
        frame.setLayout(null);
        frame.add(chessBoardTable);
        frame.add(panel);
        frame.setBounds(380, 120, PANEL_WIDTH+BOARD_WIDTH, BOARD_HEIGHT+40);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}



    public BoardFace(String path) throws IOException{
        
        Player whitePlayer = new Player("whitePlayer");
        Player blackPlayer = new Player("blackPlayer");
        Game game = new Game(whitePlayer, blackPlayer);
        
        BoardFile boardFile = new BoardFile(path);
        //改个位置

        if(boardFile.isValid() == 104){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("104:比如支持存储文件是txt，导入的是json");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }

        else if(boardFile.isValid() == 101){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("101:棋盘并非8*8");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }

        else if(boardFile.isValid() == 106){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("106:以上5种错误外其他的错误");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }

        else if(boardFile.isValid() == 102){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("102:棋盘内棋子并非包含黑方、白方、空白 3种");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }
        else if(boardFile.isValid() == 103){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("103:只有棋盘，没有下一步行棋的方的提示");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }

        else if(boardFile.isValid() == 105){
            JDialog dialog = new JDialog();
            JButton _101 = new JButton("105:先前步骤不合法");
            dialog.add(_101);
            dialog.setVisible(true);
            dialog.pack();
            
        }

        else{
            board = boardFile.getFileNewBoard();


            panel.setLayout(null);
            JButton pP = new JButton();
            pP.setContentAreaFilled(false);
            pP.setFocusPainted(false);
            pP.setBorder(null);
            pP.setIcon(ProfilePhoto);
            pP.setBounds(0, 0, 300, PHOTO_HEIGHT);
            panel.add(pP);
            
            JButton cS = new JButton();
            cS.setContentAreaFilled(false);
            cS.setFocusPainted(false);
            cS.setBorder(null);
            cS.setIcon(changeSkin);
            cS.setBounds(0, PHOTO_HEIGHT, 300, BUTTON_HEIGHT);
            panel.add(cS);
            JButton un = new JButton();
            un.setContentAreaFilled(false);
            un.setFocusPainted(false);
            un.setBorder(null);
            un.setIcon(undo);
            un.setBounds(0, PHOTO_HEIGHT+BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
            panel.add(un);
    
            // JLabel Cnt = new JLabel();
            // Cnt.setBorder(null);
            // Cnt.setBounds(0, PHOTO_HEIGHT+2*BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
            // Cnt.setVisible(true);
            // panel.add(Cnt);
            JButton me = new JButton();
            me.setContentAreaFilled(false);
            me.setFocusPainted(false);
            me.setBorder(null);
            me.setIcon(menuPicture);
            me.setBounds(0, PHOTO_HEIGHT+2*BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
            panel.add(me);
            JButton nG = new JButton();
            nG.setContentAreaFilled(false);
            nG.setFocusPainted(false);
            nG.setBorder(null);
            nG.setIcon(newGame);
            nG.setBounds(0, PHOTO_HEIGHT+3*BUTTON_HEIGHT, 300, BUTTON_HEIGHT);
            panel.add(nG);
    
            JButton cM = new JButton();
            cM.setContentAreaFilled(false);
            cM.setFocusPainted(false);
            cM.setBorder(null);
            cM.setIcon(cheatMode);
            JButton nM = new JButton();
            nM.setContentAreaFilled(false);
            nM.setFocusPainted(false);
            nM.setBorder(null);
            nM.setIcon(normalMode);
            JButton lG = new JButton();
            lG.setContentAreaFilled(false);
            lG.setFocusPainted(false);
            lG.setBorder(null);
            lG.setIcon(loadGame);
            JButton re = new JButton();
            re.setContentAreaFilled(false);
            re.setFocusPainted(false);
            re.setBorder(null);
            re.setIcon(reverse);

            JPanel scoresPanel = new JPanel();
            scoresPanel.setVisible(false);
            JDialog scoresDialog = new JDialog();
            scoresDialog.setVisible(false);
            scoresDialog.setBounds(0, 0, 300, PHOTO_HEIGHT);
    
            JButton bW = new JButton("黑方胜");
            bW.setVisible(false);
            JButton wW = new JButton("白方胜");
            wW.setVisible(false);
            JButton dr = new JButton("平局");
            dr.setVisible(false);
            JDialog blackWin = new JDialog();
            blackWin.add(bW);
            blackWin.setVisible(false);
            JDialog whiteWin = new JDialog();
            whiteWin.add(wW);
            whiteWin.setVisible(false);
            JDialog draw = new JDialog();
            draw.add(dr);
            draw.setVisible(false);
            JDialog menu = new JDialog();
            menu.setLayout(new GridLayout(4,1));
            menu.setVisible(false);
            menu.add(cM);
            menu.add(nM);
            menu.add(lG);
            menu.add(re);
    
    
            
            cS.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    if (boardSkin == 1) {
                        boardSkin = 2;
                    }
                    else{
                        boardSkin = 1;
                    }
                    chessBoardTable.repaint();
                }
            });
            nG.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    try {
                        frame.dispose();
                        new BoardFace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            un.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    //undo
                    try {
                        board = boardFile.getFileNewBoard();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    currentColor = -currentColor;
                    chessBoardTable.repaint();
                    try {
                        boardFile.cleanNineLines();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            me.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    menu.pack();
                    menu.setVisible(true);
                }
            });
            lG.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    JFileChooser chooser = new JFileChooser();
                    int returnVal = chooser.showOpenDialog(frame);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        InitialFace.path = chooser.getSelectedFile().getPath();
                        InitialFace.path = path.replace("\\", "/");
                        System.out.println(path);
                        try {
                            InitialFace.boardFace = new BoardFace(path);
                        } catch (IOException e1) {
                            
                            e1.printStackTrace();
                        }
                    }
                }
            });
            cM.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    InitialFace.mode = "C";
                }
            });
            nM.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    InitialFace.mode = "N";
                }
            });
            re.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    rule = "R";
                }
            });
    
    
    
            setCanPlayBoard(Controller.canPut(board, currentColor));
            chessBoardTable.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            panel.setBounds(BOARD_WIDTH, 0, 300, BOARD_HEIGHT);
        //鼠标移动事件
            chessBoardTable.addMouseMotionListener(new MouseAdapter(){
                @Override
                public void mouseMoved(MouseEvent e){
                    Selected_X = e.getX()/GRID_SIZE;
                    Selected_Y = e.getY()/GRID_SIZE;
                    chessBoardTable.repaint();
                }
            });
        //鼠标点击事件（按下）
            
                chessBoardTable.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e){
                            
                        if (InitialFace.pattern.equals("mM")) {
                            if (InitialFace.mode.equals("N")) {
                                if(currentColor == 1){
                                    if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == 2) {
                                        Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                        boardFile.writeBoard(board);
                                        board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                        board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                        boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                        currentColor = -currentColor;
                                    }
                                    chessBoardTable.repaint();
                                }
                                if(currentColor == -1){ 
                                    if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == -2) {
                                        Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                        boardFile.writeBoard(board);
                                        board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                        board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                        //todo writeboard
                                        boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
        
        
                                        currentColor = -currentColor;
                                    }
                                    chessBoardTable.repaint();
                                }
                            }
                            if (InitialFace.mode.equals("C")) {
                                if(currentColor == 1){  
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                    chessBoardTable.repaint();
                                }
                                else{ 
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                    chessBoardTable.repaint();
                                }
                            }
                        }
                        if (InitialFace.pattern.equals("mA")) {
                            if (InitialFace.mode.equals("N")) {
                                if(currentColor == 1){
                                    if (canPlayBoard[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] == 2) {
                                        Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                        boardFile.writeBoard(board);
                                        board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                        board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                        boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                        currentColor = -currentColor;
                                    }
                                    chessBoardTable.repaint();
                                    setCanPlayBoard(Controller.canPut(board, currentColor));
                                    
                                }
                                if(currentColor == -1){ 
                                    boardFile.writeBoard(board);
                                    int countY = -1;
                                    int countX = -1;
                                    int mostDisks = 0;
    
                                    for (int i = 0; i < canPlayBoard.length; i++) {
                                        for (int j = 0; j < canPlayBoard[i].length; j++) {
                                            if ((canPlayBoard[i][j] == -2) && Controller.abcd(board, currentColor, i, j) >= mostDisks) {
                                                mostDisks = Controller.abcd(board, currentColor, i, j);
                                            }
                                        }
                                    }
                                    
                                    outer:for (int i = 0; i < canPlayBoard.length; i++) {
                                        for (int j = 0; j < canPlayBoard[i].length; j++) {
                                            if ((canPlayBoard[i][j] == -2) && Controller.abcd(board, currentColor, i, j) == mostDisks) {
                                                board[i][j] = -1;
                                                countY = i;
                                                countX = j;
                                                break outer;
                                            }
                                        }
                                    }
                                    
                                    if (countX != -1&&countY != -1) {
                                        board = Controller.getReversiBoard(board, currentColor, countY, countX);
                                        boardFile.writeData(InitialFace.mode, currentColor, countY, countX);
                                    }
                                    currentColor = -currentColor;
                                    chessBoardTable.repaint();
                                }
                            }
                            if (InitialFace.mode.equals("C")) {
                                if(currentColor == 1){  
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                    chessBoardTable.repaint();
                                }
                                else{ 
                                    Clip music;
                                    try {
                                        music = AudioSystem.getClip();
                                        InputStream a = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/落子音效.wav");
                                        AudioInputStream b = AudioSystem.getAudioInputStream(a);
                                        music.open(b);
                                        music.start();
                                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                                        
                                        e1.printStackTrace();
                                    }
                                    boardFile.writeBoard(board);
                                    board[e.getY()/GRID_SIZE][e.getX()/GRID_SIZE] = currentColor;
                                    board = Controller.getReversiBoard(board, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    boardFile.writeData(InitialFace.mode, currentColor, e.getY()/GRID_SIZE, e.getX()/GRID_SIZE);
                                    currentColor = -currentColor;
                                    chessBoardTable.repaint();
                                }
                            }
                        }
                    }
                });
            
        //鼠标点击事件（松开）
            
                chessBoardTable.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseReleased(MouseEvent e){
                        if (InitialFace.mode.equals("N")) {
                            int blackCnt = 0;
                            int whiteCnt = 0;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (board[i][j] == 1) {
                                        blackCnt++;
                                    }
                                    if (board[i][j] == -1) {
                                        whiteCnt++;
                                    }
                                }
                            }
        
                            JLabel whiteScore = new JLabel("黑方得分为"+blackCnt+"\n"+"白方得分为"+whiteCnt);
                            scoresPanel.removeAll();
                            scoresPanel.add(whiteScore);
                            scoresPanel.setVisible(true);
                            scoresDialog.add(scoresPanel);
                            scoresDialog.setVisible(true);
                            
                            setCanPlayBoard(Controller.canPut(board, currentColor));
                            if (rule.equals("N")) {
                                if (Controller.judge(board, currentColor) == false) {
                                    currentColor = -currentColor;
                                    if(Controller.judge(board, currentColor) == false){
                                        if (blackCnt > whiteCnt) {
                                            blackWin.setVisible(true);
                                            bW.setVisible(true);
                                            blackWin.pack();
                                        }else if(blackCnt < whiteCnt){
                                            whiteWin.setVisible(true);
                                            wW.setVisible(true);
                                            whiteWin.pack();
                                        }else if(blackCnt == whiteCnt){
                                            draw.setVisible(true);
                                            dr.setVisible(true);
                                            draw.pack();
                                        }
                                    }
                                }
                            }
                            if (rule.equals("R")) {
                                if (Controller.judge(board, currentColor) == false) {
                                    currentColor = -currentColor;
                                    if(Controller.judge(board, currentColor) == false){
                                        if (blackCnt < whiteCnt) {
                                            blackWin.setVisible(true);
                                            bW.setVisible(true);
                                            blackWin.pack();
                                        }else if(blackCnt > whiteCnt){
                                            whiteWin.setVisible(true);
                                            wW.setVisible(true);
                                            whiteWin.pack();
                                        }else if(blackCnt == whiteCnt){
                                            draw.setVisible(true);
                                            dr.setVisible(true);
                                            draw.pack();
                                        }
                                    }
                                }
                            }
                            chessBoardTable.repaint();
                        }
                        if (InitialFace.mode.equals("C")) {
                            int blackCnt = 0;
                            int whiteCnt = 0;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (board[i][j] == 1) {
                                        blackCnt++;
                                    }
                                    if (board[i][j] == -1) {
                                        whiteCnt++;
                                    }
                                }
                            }
                            JLabel whiteScore = new JLabel("黑方得分为"+blackCnt+"\n"+"白方得分为"+whiteCnt);
                            scoresPanel.removeAll();
                            scoresPanel.add(whiteScore);
                            scoresPanel.setVisible(true);
                            scoresDialog.add(scoresPanel);
                            scoresDialog.setVisible(true);
                            
                            chessBoardTable.repaint();
                        }
                    }
                });
            
            frame.setLayout(null);
            frame.add(chessBoardTable);
            frame.add(panel);
            frame.setBounds(380, 120, PANEL_WIDTH+BOARD_WIDTH, BOARD_HEIGHT+40);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
