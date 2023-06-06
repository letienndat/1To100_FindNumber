package com.game.findnumber.code;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class PanelGame extends JFrame implements WindowListener {
    private static final int WIDTH = 850;
    private static final int HEIGHT = 520;
    private JLayeredPane layeredPaneButtonRemuse;
    private JLabel labelRemuse;
    private JLayeredPane layeredPaneButtonStart;
    private JLabel labelHightScoreStart;
    private JLabel labelStart;
    private JLabel labelQuitStart;
    private JLayeredPane layeredPaneGame;
    private JLayeredPane layeredPaneHead;
    private JLabel labelBackGame;
    private JLabel scoreHigh;
    private Integer Number;
    private JLayeredPane layeredPaneNumber;
    private JLabel labelNumber;
    private JLabel labelTime;
    private JLayeredPane layeredPaneBody;
    private JLabel[] labelsNumber;
    private JLabel labelBackground_Body;
    private Integer[][] colorNumber = {{167, 210, 203}, {242, 211, 136}, {201, 132, 116}, {135, 76, 98}};
    private JLabel[] labelsRange;
    private JLabel labelAudio;
    String sourceImage = "/com/game/findnumber/image/";
    String sourceScore = "/com/game/findnumber/score/";
    String sourceAudio = "/com/game/findnumber/audio/";
    private String[] colorRange = {sourceImage + "round_1.png", sourceImage + "round_2.png", sourceImage + "round_3.png", sourceImage + "round_4.png", sourceImage + "round_5.png", sourceImage + "round_6.png", sourceImage + "round_7.png"};
    private boolean complete = false;
    private boolean start = false;
    private boolean remuse = false;
    private boolean pause = true;
    private boolean quit = false;
    private boolean audio = true;
    private Set<Integer> hashSetNumber;
    private AudioInputStream audioInputStream;
    private Clip clip;

    public PanelGame() {
        super("1to100 - FindTheNumber [letienndat]");
        setSize(WIDTH, HEIGHT);
        setIconImage(new ImageIcon(PanelGame.class.getResource(sourceImage + "icon_game.png")).getImage());

        initStart();

        getContentPane().setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addWindowListener(this);
    }

    public void init() {
        initHead();
        initBody();
    }

    public void initStart() {
        layeredPaneGame = new JLayeredPane();
        layeredPaneGame.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPaneGame.setVisible(true);
        JLabel label = new JLabel("", new ImageIcon(PanelGame.class.getResource(sourceImage + "background.png")), JLabel.CENTER);
        label.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPaneGame.add(label, 0);

        layeredPaneButtonRemuse = new JLayeredPane();
        layeredPaneButtonRemuse.setOpaque(true);
        layeredPaneButtonRemuse.setBounds(WIDTH / 2 - 120 / 2, 145, 120, 40);
        layeredPaneButtonRemuse.setBackground(new Color(230, 232, 232));
        layeredPaneButtonRemuse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layeredPaneButtonRemuse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == layeredPaneButtonRemuse) {
                    start = false;
                    pause = false;
                    remuse = true;
                    complete = false;
                    layeredPaneButtonStart.setVisible(false);
                    layeredPaneButtonRemuse.setVisible(false);
                    labelQuitStart.setVisible(false);
                    layeredPaneHead.setVisible(true);
                    layeredPaneBody.setVisible(true);
                }
            }
        });
        layeredPaneButtonRemuse.setVisible(false);

        labelRemuse = new JLabel("REMUSE");
        labelRemuse.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelRemuse.setForeground(new Color(201, 75, 215));
        labelRemuse.setHorizontalAlignment(JLabel.CENTER);
        labelRemuse.setBounds(0, 5, 120, 30);
        layeredPaneButtonRemuse.add(labelRemuse, 0);

        layeredPaneGame.add(layeredPaneButtonRemuse, 0);

        layeredPaneButtonStart = new JLayeredPane();
        layeredPaneButtonStart.setOpaque(true);
        layeredPaneButtonStart.setBounds(WIDTH / 2 - 120 / 2, 195, 120, 60);
        layeredPaneButtonStart.setBackground(new Color(230, 232, 232));
        layeredPaneButtonStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layeredPaneButtonStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == layeredPaneButtonStart) {
                    init();
                    start = true;
                    remuse = false;
                    pause = false;
                    complete = false;
                    layeredPaneButtonStart.setVisible(false);
                    labelQuitStart.setVisible(false);
                }
            }
        });

        try {
            labelHightScoreStart = new JLabel("High Score: " + readFile());
            labelHightScoreStart.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
            labelHightScoreStart.setBounds(0, 5, 120, 15);
            labelHightScoreStart.setHorizontalAlignment(JLabel.CENTER);
            labelHightScoreStart.setForeground(new Color(255, 104, 104));
            layeredPaneButtonStart.add(labelHightScoreStart, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        labelStart = new JLabel("NEW GAME");
        labelStart.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        labelStart.setForeground(new Color(75, 215, 116));
        labelStart.setHorizontalAlignment(JLabel.CENTER);
        labelStart.setBounds(0, 25, 120, 30);
        layeredPaneButtonStart.add(labelStart, 0);

        layeredPaneGame.add(layeredPaneButtonStart, 0);

        labelQuitStart = new JLabel("QUIT");
        labelQuitStart.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        labelQuitStart.setForeground(new Color(56, 211, 232));
        labelQuitStart.setHorizontalAlignment(JLabel.CENTER);
        labelQuitStart.setBounds(WIDTH / 2 - 50 / 2, 265, 50, 16);
        labelQuitStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelQuitStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == labelQuitStart) {
                    quit = true;
                    clip.close();
                }
            }
        });
        layeredPaneGame.add(labelQuitStart, 0);

        labelAudio = new JLabel("", new ImageIcon(PanelGame.class.getResource(sourceImage + "play_audio.png")), JLabel.CENTER);
        labelAudio.setBounds(WIDTH - 70, 15, 30, 30);
        labelAudio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelAudio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (audio) {
                    clip.stop();
                    labelAudio.setIcon(new ImageIcon(PanelGame.class.getResource(sourceImage + "mute_audio.png")));
                    audio = false;
                } else {
                    clip.start();
                    labelAudio.setIcon(new ImageIcon(PanelGame.class.getResource(sourceImage + "play_audio.png")));
                    audio = true;
                }
            }
        });
        layeredPaneGame.add(labelAudio, 0);

        initAudio();

        add(layeredPaneGame);
    }

    public void initHead() {
        layeredPaneHead = new JLayeredPane();
        layeredPaneHead.setOpaque(true);
        layeredPaneHead.setBackground(new Color(255, 247, 247));
        layeredPaneHead.setBounds(0, 0, WIDTH, 40);

        labelBackGame = new JLabel("", new ImageIcon(PanelGame.class.getResource(sourceImage + "backgame.png")), JLabel.CENTER);
        labelBackGame.setBounds(20, 4, 32, 32);
        labelBackGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelBackGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == labelBackGame) {
                    layeredPaneHead.setVisible(false);
                    layeredPaneBody.setVisible(false);
                    layeredPaneButtonStart.setVisible(true);
                    if (labelNumber.getText().equals("Complete") || labelNumber.getText().equals("Win")) {
                        labelHightScoreStart.setText("High Score: " + scoreHigh.getText().substring(scoreHigh.getText().length() - 5));
                        layeredPaneButtonRemuse.setVisible(false);
                    } else {
                        layeredPaneButtonRemuse.setVisible(true);
                    }
                    labelQuitStart.setVisible(true);
                    pause = true;
                    remuse = false;
                    start = false;
                    complete = false;
                }
            }
        });
        layeredPaneHead.add(labelBackGame);

        try {
            scoreHigh = new JLabel("High Score: " + readFile());
            scoreHigh.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            scoreHigh.setBounds(70, 8, 130, 25);
            layeredPaneHead.add(scoreHigh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        layeredPaneNumber = new JLayeredPane();
        layeredPaneNumber.setOpaque(true);
        layeredPaneNumber.setBackground(new Color(201, 197, 197));
        layeredPaneNumber.setBounds(WIDTH / 2 - 60 / 2, 10, 80, 20);

        Number = 1;
        labelNumber = new JLabel(Integer.toString(Number));
        labelNumber.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        labelNumber.setForeground(new Color(109, 108, 108));
        labelNumber.setBounds(0, 3, 80, 15);
        labelNumber.setHorizontalAlignment(JLabel.CENTER);
        layeredPaneNumber.add(labelNumber);

        layeredPaneHead.add(layeredPaneNumber);

        labelTime = new JLabel("00:00");
        labelTime.setIcon(new ImageIcon(PanelGame.class.getResource(sourceImage + "clock.png")));
        labelTime.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        labelTime.setForeground(new Color(109, 108, 108));
        labelTime.setBounds(WIDTH - 150, 8, 70, 25);
        labelTime.setHorizontalAlignment(JLabel.CENTER);
        layeredPaneHead.add(labelTime);

        layeredPaneGame.add(layeredPaneHead, 0);
    }

    public void initBody() {
        layeredPaneBody = new JLayeredPane();
        layeredPaneBody.setOpaque(true);
        layeredPaneBody.setBounds(0, 40, WIDTH, HEIGHT - 40);

        labelBackground_Body = new JLabel("", new ImageIcon(PanelGame.class.getResource(sourceImage + "background_body.png")), JLabel.CENTER);
        labelBackground_Body.setBounds(0, 0, WIDTH, HEIGHT - 40);

        layeredPaneBody.add(labelBackground_Body, 0);
        labelsNumber = new JLabel[100];
        labelsRange = new JLabel[100];

        int widthNumber = 100;
        int heightNumber = 25;

        hashSetNumber = new LinkedHashSet<>();
        while (hashSetNumber.size() != 100) {
            hashSetNumber.add((int) (Math.random() * (101 - 1) + 1));
        }
        Iterator iterator = hashSetNumber.iterator();

        for (int i = 0; i < labelsNumber.length; i++) {
            labelsNumber[i] = new JLabel(String.valueOf(iterator.next()));
            labelsNumber[i].setForeground(new Color(colorNumber[(int) (Math.random() * (colorNumber.length - 0) + 0)][0], colorNumber[(int) (Math.random() * (colorNumber.length - 0) + 0)][1], colorNumber[(int) (Math.random() * (colorNumber.length - 0) + 0)][2]));
            labelsNumber[i].setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            labelsNumber[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            int finalI = i;
            labelsNumber[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource() == labelsNumber[finalI]) {
                        if (labelsNumber[finalI].getText().equals(Integer.toString(Number))) {
                            if (Number != 100) {
                                labelNumber.setText(Integer.toString(++Number));
                            } else {
                                complete = true;
                                try {
                                    if (compareTime()) {
                                        labelNumber.setText("Win");
                                        scoreHigh.setText("High Score: " + labelTime.getText());
                                        writeFile(labelTime.getText());
                                    } else {
                                        labelNumber.setText("Complete");
                                    }
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            }
                            layeredPaneBody.add(labelsRange[finalI], 0);
                        }
                    }
                }
            });
            int x = (int) (Math.random() * (widthNumber + 32 - widthNumber) + widthNumber);
            int y = (int) (Math.random() * (heightNumber + 30 - heightNumber) + heightNumber);
            labelsNumber[i].setBounds(x, y, labelsNumber[i].getText().length() == 1 ? 9 : (labelsNumber[i].getText().length() == 2 ? 18 : 27), 13);
            labelsNumber[i].setVisible(false);
            layeredPaneBody.add(labelsNumber[i], 0);

            labelsRange[i] = new JLabel("", new ImageIcon(PanelGame.class.getResource(colorRange[(int) (Math.random() * (colorRange.length - 0) + 0)])), JLabel.CENTER);
            labelsRange[i].setBounds(x - (labelsNumber[i].getText().length() == 1 ? 15 : (labelsNumber[i].getText().length() == 2 ? 12 : 6)), y - 13, 40, 40);

            if (i == 10) {
                heightNumber += 50;
                widthNumber = 42;
                continue;
            } else if (i == 100 - 11 - 1) {
                widthNumber = 100;
                heightNumber += 50;
                continue;
            }
            heightNumber = widthNumber > 710 ? heightNumber + 50 : heightNumber;
            widthNumber = widthNumber > 710 ? 42 : widthNumber + 58;
        }

        layeredPaneGame.add(layeredPaneBody, 0);
    }

    public void setLabelTime(String s) {
        if (labelTime != null) {
            labelTime.setText(s);
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isRemuse() {
        return remuse;
    }

    public boolean isPause() {
        return pause;
    }

    public boolean isQuit() {
        return quit;
    }

    public String readFile() throws IOException {
        String res = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(PanelGame.class.getResourceAsStream(sourceScore + "score.txt")));
            res = br.readLine();
            return res == null ? "None" : res;
        } catch (IOException e) {
            new File(sourceScore + "score.txt").createNewFile();
            return "None";
        }
    }

    public void writeFile(String s) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(sourceScore + "score.txt"));
        bw.write(s);
        bw.close();
    }

    public boolean compareTime() throws IOException {
        if (scoreHigh != null) {
            if (scoreHigh.getText().endsWith("None")) {
                return true;
            }
            String[] scoreFile = scoreHigh.getText().substring(scoreHigh.getText().length() - 5).split(":");
            int minuteFile = Integer.parseInt(scoreFile[0]);
            int secondFile = Integer.parseInt(scoreFile[1]);
            String[] scoreThis = labelTime.getText().split(":");
            int minuteThis = Integer.parseInt(scoreThis[0]);
            int secondThis = Integer.parseInt(scoreThis[1]);
            if (minuteThis < minuteFile) {
                return true;
            } else if (minuteThis == minuteFile) {
                if (secondThis < secondFile) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showNumberEffect() {
        Set<Integer> hashSetNumberRandom = new LinkedHashSet<>();
        while (hashSetNumberRandom.size() != 100) {
            hashSetNumberRandom.add((int) (Math.random() * (100 - 0) + 0));
        }
        Iterator iterator = hashSetNumberRandom.iterator();
        try {
            while (iterator.hasNext()) {
                labelsNumber[(int) iterator.next()].setVisible(true);
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCreateNumbers() {
        if (labelsNumber == null) return false;
        return true;
    }

    public void initAudio() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(PanelGame.class.getResource(sourceAudio + "music_background.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        clip.close();
        quit = true;
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
