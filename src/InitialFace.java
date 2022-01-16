package src;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class InitialFace{

    JFrame frame = new JFrame();
    
    JPanel panel = new JPanel();
    int FACE_WIDTH = 700;
    int FACE_HEIGHT = 700;
    public static String mode;
    public static String pattern;
    public static String path;
    public static BoardFace boardFace;

    public String getMode(){
        return mode;
    }
    public static String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public InitialFace(){

        ImageIcon othello = new ImageIcon("src/picture/heibaiqi1060 360.png");
        ImageIcon chooseMode = new ImageIcon("src/picture/xuanzemoshi1300 360.png");
        ImageIcon chooseCheat = new ImageIcon("src/picture/zuobimoshi 1300 360.png");
        ImageIcon chooseNormal = new ImageIcon("src/picture/duizhanmoshi1300 360.png");
        ImageIcon InitialGame = new ImageIcon("src/picture/新游戏.png");
        ImageIcon loadGame = new ImageIcon("src/picture/载入游戏.png");
        ImageIcon Man_Man = new ImageIcon("src/picture/renrenduizhan1300 360.png");
        ImageIcon Man_AI = new ImageIcon("src/picture/renjiduizhan 1300 360.png");

        JButton ot = new JButton();
        ot.setContentAreaFilled(false);
        ot.setFocusPainted(false);
        ot.setBorder(null);
        ot.setIcon(othello);

        JButton cM = new JButton();
        cM.setContentAreaFilled(false);
        cM.setFocusPainted(false);
        cM.setBorder(null);
        cM.setIcon(chooseMode);

        JButton cC = new JButton();
        cC.setContentAreaFilled(false);
        cC.setFocusPainted(false);
        cC.setBorder(null);
        cC.setIcon(chooseCheat);

        JButton cN = new JButton();
        cN.setContentAreaFilled(false);
        cN.setFocusPainted(false);
        cN.setBorder(null);
        cN.setIcon(chooseNormal);

        JButton mM = new JButton();
        mM.setContentAreaFilled(false);
        mM.setFocusPainted(false);
        mM.setBorder(null);
        mM.setIcon(Man_Man);

        JButton mA = new JButton();
        mA.setContentAreaFilled(false);
        mA.setFocusPainted(false);
        mA.setBorder(null);
        mA.setIcon(Man_AI);
        
        JButton iG = new JButton();
        iG.setContentAreaFilled(false);
        iG.setFocusPainted(false);
        iG.setBorder(null);
        iG.setIcon(InitialGame);

        JButton lG = new JButton();
        lG.setContentAreaFilled(false);
        lG.setFocusPainted(false);
        lG.setBorder(null);
        lG.setIcon(loadGame);

        panel.setLayout(new BorderLayout());
        panel.add(ot);
        panel.add(cM,BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                panel.remove(ot);
                panel.remove(cM);
                panel.add(cC);
                panel.add(cN,BorderLayout.SOUTH);
                panel.updateUI();
            }
        });

        cN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                mode = "N";
                panel.remove(cN);
                panel.remove(cC);
                panel.add(mM);
                panel.add(mA,BorderLayout.SOUTH);
                panel.updateUI();
            }
        });

        cC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                mode = "C";
                panel.remove(cN);
                panel.remove(cC);
                panel.add(mM);
                panel.add(mA,BorderLayout.SOUTH);
                panel.updateUI();
            }
        });

        mM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                pattern = "mM";
                panel.remove(mM);
                panel.remove(mA);
                panel.add(iG);
                panel.add(lG,BorderLayout.SOUTH);
                panel.updateUI();
            }
        });

        mA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                pattern = "mA";
                panel.remove(mM);
                panel.remove(mA);
                panel.add(iG);
                panel.add(lG,BorderLayout.SOUTH);
                panel.updateUI();
            }
        });

        iG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Clip bgm = AudioSystem.getClip();
                    InputStream is = BoardFace.class.getClassLoader().getResourceAsStream("src/MusicResource/09. 唱不了一首欢乐的歌.wav");
                    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
                    bgm.open(ais);
                    bgm.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (LineUnavailableException e2) {
                    
                    e2.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    
                    e1.printStackTrace();
                } catch (IOException e1) {
                    
                    e1.printStackTrace();
                }
                frame.dispose();
                try {
                    boardFace = new BoardFace();
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        lG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().getPath();
                    path = path.replace("\\", "/");
                    System.out.println(path);
                    

                    
                    try {
                        boardFace = new BoardFace(path);
                    } catch (IOException e1) {
                        
                        e1.printStackTrace();
                    }
                    
                    
                    
                    
                }
            }
        });
    }

}
