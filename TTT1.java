import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.*;

class TTT1 extends JFrame implements ItemListener, ActionListener{
    int i,j,ii,jj,x,y,yesnull;
    int a[][]={{10,1,2,3,11},{10,1,4,7,11},{10,1,5,9,11},{10,2,5,8,11},
            {10,3,5,7,11},{10,3,6,9,11},{10,4,5,6,11},{10,7,8,9,11} };
    int a1[][]={{10,1,2,3,11},{10,1,4,7,11},{10,1,5,9,11},{10,2,5,8,11},
            {10,3,5,7,11},{10,3,6,9,11},{10,4,5,6,11},{10,7,8,9,11} };

    Clip clip;
    AudioInputStream audioInputStream;

    public void playSound(String soundFilePath) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    boolean state,type,set;
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;
    ImageIcon playerIcon, computerIcon;
    Timer playerTimer;
    int turnTimer = 10;
    JLabel timerLabel;
    boolean gameInProgress = false;
    Icon ic1,ic2,icon,ic11,ic22;
    Checkbox c1,c2;
    JLabel l1,l2;
    JButton b[]=new JButton[9];
    JButton reset;
    private JLabel additionalTextLabel;
    private JSeparator separator;
    private JLabel titleLabel1;
    private JLabel titleLabel2;
    JLabel backgroundLabel;


    public void showButton(){

        x=10; y=10;j=0;
        for(i=0;i<=8;i++,x+=100,j++){
            b[i]=new JButton();
            if(j==3)
            {j=0; y+=100; x=10;}
            b[i].setBounds(x,y,100,100);
            add(b[i]);
            b[i].addActionListener(this);
        }//eof for

        reset=new JButton("RESET");
        reset.setBounds(100,385,100,50);
        repaint();
        add(reset);
        reset.addActionListener(this);

    }//eof showButton

    /*********************************************************/
    public  void check(int num1){
        for(ii=0;ii<=7;ii++){
            for(jj=1;jj<=3;jj++){
                if(a[ii][jj]==num1){ a[ii][4]=11;  }

            }//eof for jj

        }//eof for ii
    }//eof check

    /*********************************************************/

    public void complogic(int num){

        for(i=0;i<=7;i++){
            for(j=1;j<=3;j++){
                if(a[i][j]==num){  a[i][0]=11; a[i][4]=10;    }
            }
        }
        for(i=0;i<=7;i++){
            set=true;
            if(a[i][4]==10){
                int count=0;
                for(j=1;j<=3;j++){
                    if(b[(a[i][j]-1)].getIcon()!=null){
                        count++;
                    }
                    else{ yesnull=a[i][j]; }
                }
                if(count==2){
                    b[yesnull-1].setIcon(ic2);
                    this.check(yesnull); set=false;break;
                }
            }
            else
            if(a[i][0]==10){
                for(j=1;j<=3;j++){
                    if(b[(a[i][j]-1)].getIcon()==null){
                        b[(a[i][j]-1)].setIcon(ic2);
                        this.check(a[i][j]);
                        set=false;
                        break;
                    }
                }
                if(set==false)
                    break;
            }//eof elseif

            if(set==false)
                break;
        }//eof for 1
    }//eof complogic
    /*********************************************************/

    TTT1(){
        super("tic tac toe modified by felia");

        backgroundLabel = new JLabel(new ImageIcon("logo.png")); // Sesuaikan path dengan lokasi gambar Anda
        backgroundLabel.setBounds(0, 0, 330, 690);
        add(backgroundLabel);
        getContentPane().setBackground(new Color(160, 82, 45));

        titleLabel1 = new JLabel("<html><font color='#5F9EA0'>T</font><font color='#EEE8AA'>i</font><font color='#5F9EA0'>c</font> <font color='#EEE8AA'>T</font><font color='#5F9EA0'>a</font><font color='#EEE8AA'>c</font></html>");
        titleLabel1.setFont(new Font("Impact", Font.PLAIN, 40));
        titleLabel1.setBounds(80, 45, 250, 40);
        add(titleLabel1);

        // Tambahkan judul "Toe" pada baris berikutnya dengan efek teks
        titleLabel2 = new JLabel("<html><font color='#5F9EA0'>T</font><font color='#EEE8AA'>o</font><font color='#5F9EA0'>e</font></html>");
        titleLabel2.setFont(new Font("Impact", Font.PLAIN, 40));
        titleLabel2.setBounds(130, 90, 150, 40);
        add(titleLabel2);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        startGameButton.setBounds(90, 210, 150, 40);
        startGameButton.setBackground(new Color(238, 232, 170)); // Warna latar belakang tombol
        startGameButton.setForeground(new Color(46, 64, 83)); // Warna teks tombol
        add(startGameButton);


        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                remove(backgroundLabel);
                remove(startGameButton);
                revalidate();
                repaint();

                additionalTextLabel = new JLabel("          Pilih mode permainan");
                additionalTextLabel.setForeground(new Color(238, 232, 170));
                additionalTextLabel.setFont(new Font("Impact", Font.PLAIN, 16));

                int labelWidth = 200;
                int labelHeight = 30;
                int x = (getWidth() - labelWidth) / 2;
                int y = (getHeight() - labelHeight) / 4;

                additionalTextLabel.setBounds(x, y, labelWidth, labelHeight);
                add(additionalTextLabel);

                separator = new JSeparator();
                separator.setBounds(x, y + labelHeight + 5, labelWidth, 1);
                separator.setForeground(new Color(245, 245, 245));
                add(separator);


                CheckboxGroup cbg = new CheckboxGroup();
                c1 = new Checkbox("vs computer", cbg, false);
                c2 = new Checkbox("vs friend", cbg, false);
                Color textColor = new Color(112, 128, 145);
                Font font = new Font("Arial", Font.BOLD, 14);
                Color bgColor = new Color(238, 232, 170);

                c1.setForeground(textColor);
                c1.setFont(font);
                c1.setBackground(bgColor);

                c2.setForeground(textColor);
                c2.setFont(font);
                c2.setBackground(bgColor);

                c1.setBounds(120, y + labelHeight + 30, 100, 40);
                c2.setBounds(120, y + labelHeight + 100, 100, 40);

                add(c1);
                add(c2);

                c1.addItemListener(TTT1.this);
                c2.addItemListener(TTT1.this);

                revalidate();
                repaint();
            }
        });

        state=true;type=true;set=true;
        ic1=new ImageIcon("ic1.jpg");
        ic2=new ImageIcon("ic2.jpg");
        ic11=new ImageIcon("ic11.jpg");
        ic22=new ImageIcon("ic22.jpg");

        setLayout(null);
        setSize(330,550);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }//eof constructor
    public void displayScores() {
        l1.setText("Player 1: " + scorePlayer1);
        l2.setText("Player 2: " + scorePlayer2);
    }

    /*************************************************************/
    public void itemStateChanged(ItemEvent e) {
        int labelHeight = 30;
        String playerName1 = ""; // Tambahkan variabel untuk menyimpan nama pemain 1
        String playerName2 = ""; // Tambahkan variabel untuk menyimpan nama pemain 2

        if (c1.getState()) { // Memilih melawan komputer
            type = false;

            // Tampilkan dialog pilihan simbol saat memilih melawan komputer
            int symbolChoice = JOptionPane.showOptionDialog(this,
                    "Pilih simbol Anda:",
                    "Pemilihan Simbol",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"X", "O"},
                    "X");


            JLabel playerNameLabel1 = new JLabel("Player 1: ");
            playerName1 = JOptionPane.showInputDialog(this,
                    "Masukkan nama pemain :",
                    "Input Nama Pemain ",
                    JOptionPane.PLAIN_MESSAGE);

            if (playerName1 == null) {
                JOptionPane.showMessageDialog(this, "Harap isi nama agar bisa memulai permainan.");
            }

            String displayedName1 = playerName1.isEmpty() ? "Player 1" : playerName1;
            playerNameLabel1.setText("Player 1: " + displayedName1);
            add(playerNameLabel1);
            playerNameLabel1.setBounds(120, 350, 150, 30);

            JPanel infoPanel = new JPanel();
            infoPanel.setBounds(13, 320, 290, 30);
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setLayout(new GridLayout(1, 3, 10, 0));

            l1 = new JLabel("   Player 1: 0");
            l1.setForeground(new Color(112, 128, 145));
            l1.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(l1);

            playerTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    turnTimer--;
                    timerLabel.setText("Timer: " + turnTimer);
                    if (turnTimer == 0) {
                        switchPlayer();
                    }
                }
            });

            timerLabel = new JLabel("    Timer: " + turnTimer);
            timerLabel.setForeground(new Color(112, 128, 145));
            timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(timerLabel);

            l2 = new JLabel(" Player 2: 0");
            l2.setForeground(new Color(112, 128, 145));
            l2.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(l2);

            add(infoPanel);

            // Atur simbol pemain dan komputer berdasarkan pilihan
            if (symbolChoice == JOptionPane.YES_OPTION) {
                playerIcon = new ImageIcon("ic1.jpg");
                computerIcon = new ImageIcon("ic2.jpg");
            } else {
                playerIcon = new ImageIcon("ic2.jpg");
                computerIcon = new ImageIcon("ic1.jpg");
            }

            // Atur ikon untuk pemain dan komputer
            ic1 = playerIcon;
            ic2 = computerIcon;

            if (ic1.toString().equals("ic1.jpg")) {
                ic11 = new ImageIcon("ic11.JPG");
                ic22 = new ImageIcon("ic22.JPG");
            } else {
                ic11 = new ImageIcon("ic22.JPG");
                ic22 = new ImageIcon("ic11.JPG");
            }
        } else if (c2.getState()) { // Memilih melawan teman
            type = true;


            // Tampilkan dialog pilihan simbol saat memilih melawan teman
            int symbolChoice = JOptionPane.showOptionDialog(this,
                    "Pilih simbol Anda:",
                    "Pemilihan Simbol",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"X", "O"},
                    "X");

            JLabel playerNameLabel1 = new JLabel("Player 1: ");
            JLabel playerNameLabel2 = new JLabel("Player 2: ");
            playerName1 = JOptionPane.showInputDialog(this,
                    "Masukkan nama pemain 1:",
                    "Input Nama Pemain 1",
                    JOptionPane.PLAIN_MESSAGE);

            if (playerName1 == null) {
                JOptionPane.showMessageDialog(this, "Harap isi nama agar bisa memulai permainan.");
            }
            playerName2 = JOptionPane.showInputDialog(this,
                    "Masukkan nama pemain 2:",
                    "Input Nama Pemain 2",
                    JOptionPane.PLAIN_MESSAGE);

            if (playerName2 == null) {
                JOptionPane.showMessageDialog(this, "Harap isi nama agar bisa memulai permainan.");
            }
            // Menampilkan nama pemain di antarmuka
            String displayedName1 = playerName1.isEmpty() ? "Player 1" : playerName1;
            String displayedName2 = playerName2.isEmpty() ? "Player 2" : playerName2;
            playerNameLabel1.setText("Player 1: " + displayedName1);
            playerNameLabel2.setText("Player 2: " + displayedName2);
            add(playerNameLabel1);
            playerNameLabel1.setBounds(120, 350, 150, 30);

            add(playerNameLabel2);
            playerNameLabel2.setBounds(120, 370, 150, 30);


            JPanel infoPanel = new JPanel();
            infoPanel.setBounds(13, 320, 290, 30);
            infoPanel.setBackground(new Color(245, 245, 245));
            infoPanel.setLayout(new GridLayout(1, 3, 10, 0));

            l1 = new JLabel("   Player 1: 0");
            l1.setForeground(new Color(112, 128, 145));
            l1.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(l1);

            playerTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    turnTimer--;
                    timerLabel.setText("Timer: " + turnTimer);
                    if (turnTimer == 0) {
                        switchPlayer();
                    }
                }
            });

            timerLabel = new JLabel("    Timer: " + turnTimer);
            timerLabel.setForeground(new Color(112, 128, 145));
            timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(timerLabel);

            l2 = new JLabel(" Player 2: 0");
            l2.setForeground(new Color(112, 128, 145));
            l2.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(l2);

            add(infoPanel);

            // Atur simbol untuk pemain 1 dan pemain 2 berdasarkan pilihan
            if (symbolChoice == JOptionPane.YES_OPTION) {
                ic1 = new ImageIcon("ic2.jpg");
                ic2 = new ImageIcon("ic1.jpg");
            } else {
                ic1 = new ImageIcon("ic1.jpg");
                ic2 = new ImageIcon("ic2.jpg");
            }
            // Set gambar yang akan ditampilkan ketika ada pemenang
            if (ic1.toString().equals("ic1.jpg")) {
                ic11 = new ImageIcon("ic11.JPG");
                ic22 = new ImageIcon("ic22.JPG");
            } else {
                ic11 = new ImageIcon("ic22.JPG");
                ic22 = new ImageIcon("ic11.JPG");
            }

        }
        // Tampilkan atau reset tampilan jika diperlukan
        additionalTextLabel.setVisible(!c1.getState() && !c2.getState());
        separator.setVisible(!c1.getState() && !c2.getState());
        titleLabel1.setVisible(!c1.getState() && !c2.getState());
        titleLabel2.setVisible(!c1.getState() && !c2.getState());
        revalidate();
        repaint();
        remove(c1);
        remove(c2);
        repaint(0, 0, 330, 450);
        showButton(); // Tampilkan tombol setelah memilih simbol atau mode permainan
        reset.setBounds(110, 400, 100, 50);
    }
    /************************************************************/

    public void actionPerformed(ActionEvent e){
/********************************/
        if(type==true)//logicfriend
        {
            if(e.getSource()==reset){
                for(i=0;i<=8;i++){
                    b[i].setIcon(null);
                }//eof for
                gameInProgress = false; // set gameInProgress ke false saat reset
                stopTimer();
            }
            else{
                for(i=0;i<=8;i++){
                    if(e.getSource()==b[i]){
                        if(b[i].getIcon()==null){
                            if(state==true){
                                icon=ic2;
                                state=false;
                            } else{
                                icon=ic1;
                                state=true;
                            }
                            b[i].setIcon(icon);
                            gameInProgress = true;
                        }
                    }
                }//eof for
                startTimer();
                checkWinner();
            }//eof else
        }//eof logicfriend
        else if(type==false){                                     //  complogic
            if(e.getSource()==reset){
                for(i=0;i<=8;i++){
                    b[i].setIcon(null);
                }//eof for
                gameInProgress = false; // set gameInProgress ke false saat reset
                stopTimer();
                for(i=0;i<=7;i++)
                    for(j=0;j<=4;j++)
                        a[i][j]=a1[i][j];   //again initialsing array
            }
            else{  //complogic
                for(i=0;i<=8;i++){
                    if(e.getSource()==b[i]){
                        if(b[i].getIcon()==null){
                            b[i].setIcon(ic1);
                            if(b[4].getIcon()==null){
                                b[4].setIcon(ic2);
                                this.check(5);
                            } else{
                                this.complogic(i);
                            }
                            gameInProgress = true;
                        }
                    }
                }//eof for
                startTimer();
                checkWinner();
            }
        }//eof complogic
    }//eof actionperformed
    /************************************************************/
    public void checkWinner() {
        boolean gameWon = false; // Tambahkan variabel gameWon
        for (i = 0; i <= 7; i++) {
            Icon icon1 = b[(a[i][1] - 1)].getIcon();
            Icon icon2 = b[(a[i][2] - 1)].getIcon();
            Icon icon3 = b[(a[i][3] - 1)].getIcon();

            if ((icon1 == icon2) && (icon2 == icon3) && (icon1 != null)) {
                gameWon = true; // Setel gameWon ke true saat ada pemenang
                if (icon1 == ic1) { // Jika simbol pemain yang menang
                    b[(a[i][1] - 1)].setIcon(ic11);
                    b[(a[i][2] - 1)].setIcon(ic11);
                    b[(a[i][3] - 1)].setIcon(ic11);
                    scorePlayer1++;
                    displayScores();

                    if (!type) {
                        playSound("kamu_menang.wav"); // Add this line to play sound on winning
                        JOptionPane.showMessageDialog(TTT1.this, "Kamu menang!! Skor kamu : " + scorePlayer1 + " click reset");
                    } else {
                        playSound("komputer_menang.wav"); // Add this line to play sound on computer winning
                        JOptionPane.showMessageDialog(TTT1.this, "Pemain 1 menang!! Skor Pemain 1 : " + scorePlayer1 + " click reset");
                    }
                } else if (icon1 == ic2) { // Jika komputer atau pemain kedua yang menang
                    b[(a[i][1] - 1)].setIcon(ic22);
                    b[(a[i][2] - 1)].setIcon(ic22);
                    b[(a[i][3] - 1)].setIcon(ic22);
                    scorePlayer2++;
                    displayScores();

                    if (!type) {
                        playSound("kamu_menang.wav"); // Add this line to play sound on winning
                        JOptionPane.showMessageDialog(TTT1.this, "Haha komputer menang!! Skor komputer : " + scorePlayer2 + " click reset");
                    } else {
                        playSound("komputer_menang.wav"); // Add this line to play sound on computer winning
                        JOptionPane.showMessageDialog(TTT1.this, "Pemain 2 menang!! Skor Pemain 2 : " + scorePlayer2 + " click reset");
                    }
                }
                break;
            }
        }

        if (gameWon) {
            stopTimer(); // Hentikan timer saat ada pemenang
        }
    }
    public void switchPlayer() {
        // Hentikan timer
        stopTimer();

        // Gantian pemain
        if (state == true) {
            icon = ic2;
            state = false;
        } else {
            icon = ic1;
            state = true;
        }

        // Jika permainan melawan teman, atau giliran pemain saat ini yang seharusnya bermain
        if (type || (state == true)) {
            // Mulai timer kembali
            startTimer();
        } else {
            // Jika melawan komputer, panggil metode complogic
            complogic(getEmptyCell()); // Ganti dengan cara memperoleh sel kosong yang belum diisi
        }
    }
    private int getEmptyCell() {
        for (int i = 0; i < 9; i++) {
            if (b[i].getIcon() == null) {
                return i;
            }
        }
        return -1; // Jika tidak ada sel kosong (seharusnya tidak terjadi)
    }
    public void startTimer() {
        turnTimer = 10; // Reset timer
        playerTimer.start();
    }
    public void stopTimer() {
        playerTimer.stop();
    }

    public static void main(String []args){
        new TTT1();
    }//eof main
}//eof class