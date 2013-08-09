package App;

import Connect.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    String Host = "";
    MainServer MS = new MainServer();
    MainClient MC = new MainClient();
    int Pos[][] = new int[5][2];
    int Ar[][][] = new int[5][2][2];
    int H[] = {8, 5, 10, 3, 5};
    boolean Over = false, reset = false, ActiveChat = true, Ready = false;
    int Bombs = 10;
    int AtX, AtY;
    SoundFX FX = new SoundFX();

    public void resetAll() {
        while (!reset) {
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
        try {
            System.out.println("RUN");
            Runtime.getRuntime().exec("java -jar JuegoNaval.jar");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println(System.getProperty("user.dir") + "\\JuevoNaval.jar");

    }

    /*public void checkHosts(String subnet) {
     try {
     int timeout = 10;
     for (int i = 1; i < 254; i++) {
     String host = subnet + "." + i;
     System.out.println(host);
     if (InetAddress.getByName(host).isReachable(timeout)) {
     System.out.println(host + " is reachable");
     }
     }
     } catch (Exception e) {
     }
     }*/
    public int count() {
        int cant = 0;
        for (int i = 0; i < H.length; i++) {
            if (H[i] > 0) {
                cant++;
            }
        }
        return cant;
    }

    public void enemyAttack() {
        while (!Over) {
            try {
                if (MS.aX > -1 && MS.aY > -1) {
                    FX.play("crash.wav");
                    MS.aX = -1;
                    MS.aY = -1;
                }
                new Thread().sleep(500);
            } catch (Exception e) {
            }
        }
    }

    public void startAttack() {
        while (!Ready && !MS.Ready) {
            try {
                new Thread().sleep(500);
            } catch (Exception e) {
            }
        }
        Grid.setVisible(true);
        LblReady.setVisible(true);
        while (!Over) {
            Enemy.setText(MS.Enemy + "");
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public void getChat() {
        while (!Over) {
            if (MS.chat.length() > 0) {
                ChatWindow.append(MS.chat + "\n");
                MS.chat = "";
            }
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public void getAttack() {
        System.out.println("Getting Attacks...");
        while (!Over) {
            System.out.print("");
            if (MS.cX > -1 && MS.cY > -1) {
                System.out.println("Attack on " + MS.cX + "," + MS.cY);
                attack(MS.cX, MS.cY);
                MS.cX = -1;
                MS.cY = -1;
            }
        }
    }

    public void resetBombs() {
        while (!Over) {
            try {
                if (Bombs == 0) {
                    Grid.setVisible(false);
                    Timer.setForeground(Color.blue);
                    for (int i = 10; i > 0; i--) {
                        Timer.setText(i + "");
                        new Thread().sleep(1000);
                    }
                    Bombs = 10;
                    Timer.setForeground(Color.black);
                    Timer.setText("X");
                    Grid.setVisible(true);
                }
            } catch (Exception e) {
            }
        }
    }

    public void youWin() {
        while (!MS.win) {
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
        Over = true;
        JOptionPane.showMessageDialog(null, "¡Haz Ganado!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        reset = true;
    }

    public void gameOver() {
        while (H[0] > 0 || H[1] > 0 || H[2] > 0 || H[3] > 0 || H[4] > 0) {
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
        Over = true;
        MC.sendData("win");
        JOptionPane.showMessageDialog(null, "¡Haz Perdido!", "Game Over", JOptionPane.WARNING_MESSAGE);
        reset = true;

    }

    public void keepLocation() {
        while (!Over) {
            barco1.setLocation(Pos[0][0], Pos[0][1]);
            barco2.setLocation(Pos[1][0], Pos[1][1]);
            barco3.setLocation(Pos[2][0], Pos[2][1]);
            barco4.setLocation(Pos[3][0], Pos[3][1]);
            barco5.setLocation(Pos[4][0], Pos[4][1]);
            explosion.setLocation(AtX, AtY);
        }
    }

    public void fillHealt() {
        while (!Over) {
            B1.setText(H[0] + "");
            B2.setText(H[1] + "");
            B3.setText(H[2] + "");
            B4.setText(H[3] + "");
            B5.setText(H[4] + "");
            Bomb.setText(Bombs + "");
            try {
                new Thread().sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public void explode(JLabel LB, int time) {
        if (time == 800) {
            FX.play("exp1.wav");
        } else {
            FX.play("exp2.wav");
        }
        explosion.setVisible(true);
        LB.setForeground(Color.red);
        try {
            new Thread().sleep(time);
        } catch (Exception e) {
        }
        explosion.setVisible(false);
        LB.setForeground(Color.black);
    }

    public void attack(int X, int Y) {
        if (X > Ar[0][0][0] && X < Ar[0][0][1] && Y > Ar[0][1][0] && Y < Ar[0][1][1]) {
            MC.sendData("A" + X + "-" + Y);
            if (H[0] == 1) {
                H[0]--;
                AtX = X - 50;
                AtY = Y - 100;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        explode(B1, 2000);
                    }
                }).start();
                barco1.setVisible(false);
                StB1.setForeground(Color.red);
            } else {
                if (H[0] > 0) {
                    H[0]--;
                    AtX = X - 50;
                    AtY = Y - 80;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            explode(B1, 800);
                        }
                    }).start();
                }
            }
        }
        if (X > Ar[1][0][0] && X < Ar[1][0][1] && Y > Ar[1][1][0] && Y < Ar[1][1][1]) {
            MC.sendData("A" + X + "-" + Y);
            if (H[1] == 1) {
                H[1]--;
                AtX = X - 50;
                AtY = Y - 100;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        explode(B2, 2000);
                    }
                }).start();
                barco2.setVisible(false);
                StB2.setForeground(Color.red);
            } else {
                if (H[1] > 0) {
                    H[1]--;
                    AtX = X - 50;
                    AtY = Y - 80;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            explode(B2, 800);
                        }
                    }).start();
                }
            }
        }
        if (X > Ar[2][0][0] && X < Ar[2][0][1] && Y > Ar[2][1][0] && Y < Ar[2][1][1]) {
            MC.sendData("A" + X + "-" + Y);
            if (H[2] == 1) {
                H[2]--;
                AtX = X - 50;
                AtY = Y - 100;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        explode(B3, 2000);
                    }
                }).start();
                barco3.setVisible(false);
                StB3.setForeground(Color.red);
            } else {
                if (H[2] > 0) {
                    H[2]--;
                    AtX = X - 50;
                    AtY = Y - 80;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            explode(B3, 800);
                        }
                    }).start();
                }
            }
        }
        if (X > Ar[3][0][0] && X < Ar[3][0][1] && Y > Ar[3][1][0] && Y < Ar[3][1][1]) {
            MC.sendData("A" + X + "-" + Y);
            if (H[3] == 1) {
                H[3]--;
                AtX = X - 50;
                AtY = Y - 100;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        explode(B4, 2000);
                    }
                }).start();
                barco4.setVisible(false);
                StB4.setForeground(Color.red);
            } else {
                if (H[3] > 0) {
                    H[3]--;
                    AtX = X - 50;
                    AtY = Y - 80;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            explode(B4, 800);
                        }
                    }).start();
                }
            }
        }
        if (X > Ar[4][0][0] && X < Ar[4][0][1] && Y > Ar[4][1][0] && Y < Ar[4][1][1]) {
            MC.sendData("A" + X + "-" + Y);
            if (H[4] == 1) {
                H[4]--;
                AtX = X - 50;
                AtY = Y - 100;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        explode(B5, 2000);
                    }
                }).start();
                barco5.setVisible(false);
                StB5.setForeground(Color.red);
            } else {
                if (H[4] > 0) {
                    H[4]--;
                    AtX = X - 50;
                    AtY = Y - 80;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            explode(B5, 800);
                        }
                    }).start();
                }
            }
        }
        MC.sendData("E" + count());
    }

    public void getSizes() {
        //Barco 1
        Ar[0][0][0] = Pos[0][0];
        Ar[0][0][1] = Pos[0][0] + 100;
        Ar[0][1][0] = Pos[0][1];
        Ar[0][1][1] = Pos[0][1] + 100;
        //Barco 2
        Ar[1][0][0] = Pos[1][0];
        Ar[1][0][1] = Pos[1][0] + 80;
        Ar[1][1][0] = Pos[1][1];
        Ar[1][1][1] = Pos[1][1] + 100;
        //Barco 3
        Ar[2][0][0] = Pos[2][0];
        Ar[2][0][1] = Pos[2][0] + 100;
        Ar[2][1][0] = Pos[2][1];
        Ar[2][1][1] = Pos[2][1] + 120;
        //Barco 4
        Ar[3][0][0] = Pos[3][0];
        Ar[3][0][1] = Pos[3][0] + 50;
        Ar[3][1][0] = Pos[3][1];
        Ar[3][1][1] = Pos[3][1] + 50;
        //Barco 5
        Ar[4][0][0] = Pos[4][0];
        Ar[4][0][1] = Pos[4][0] + 80;
        Ar[4][1][0] = Pos[4][1];
        Ar[4][1][1] = Pos[4][1] + 100;
        /*System.out.println("------------------------------------------------------------------------------");
         for (int i = 0; i < 5; i++) {
         System.out.println("Barco " + (i + 1) + ", Actual: [" + Pos[i][0] + "," + Pos[i][1] + "], " + "Min: [" + Ar[i][0][0] + "," + Ar[i][1][0] + "]" + ", Max: [" + Ar[i][0][1] + "," + Ar[i][1][1] + "]");
         }
         System.out.println("------------------------------------------------------------------------------");*/
    }

    public void fillPos() {
        Pos[0][0] = barco1.getLocation().x;
        Pos[0][1] = barco1.getLocation().y;
        Pos[1][0] = barco2.getLocation().x;
        Pos[1][1] = barco2.getLocation().y;
        Pos[2][0] = barco3.getLocation().x;
        Pos[2][1] = barco3.getLocation().y;
        Pos[3][0] = barco4.getLocation().x;
        Pos[3][1] = barco4.getLocation().y;
        Pos[4][0] = barco5.getLocation().x;
        Pos[4][1] = barco5.getLocation().y;
        getSizes();
    }

    public void connect() {
        try {
            MS.start(StServer);
        } catch (Exception e) {
        }
    }

    public void getNetworks() {
        try {
            System.out.println("Getting Networks...");
            InetAddress address = InetAddress.getLocalHost();
            Host = address.getHostAddress();
            ActualIP.setText("Mi dirección IP: " + Host);
            /*String X[] = Host.split("\\.");
             String serverHostname = X[0] + "." + X[1] + "." + X[2];
             checkHosts(serverHostname);*/
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void start() {
        try {
            getNetworks();
            new Thread().sleep(1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    connect();
                }
            }).start();
            Status.setText("Iniciando Servidor...");
            new Thread().sleep(2000);
            Wall.setVisible(false);
            Networks.setVisible(true);
            TxtServer.requestFocus();
        } catch (Exception e) {
        }
    }

    public Main() {
        initComponents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FX.playSoundtrack();
            }
        }).start();
        Networks.setVisible(false);
        Game.setVisible(false);
        Options.setVisible(false);
        this.setLocationRelativeTo(null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Chat = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatWindow = new javax.swing.JTextArea();
        ChatText = new javax.swing.JTextField();
        Game = new org.edisoncor.gui.panel.PanelImage();
        Grid = new org.edisoncor.gui.panel.PanelImage();
        Game1 = new org.edisoncor.gui.panel.PanelImage();
        Game2 = new org.edisoncor.gui.panel.PanelImage();
        explosion = new javax.swing.JLabel();
        barco1 = new org.edisoncor.gui.panel.PanelImage();
        barco2 = new org.edisoncor.gui.panel.PanelImage();
        barco3 = new org.edisoncor.gui.panel.PanelImage();
        barco4 = new org.edisoncor.gui.panel.PanelImage();
        barco5 = new org.edisoncor.gui.panel.PanelImage();
        GameTitle1 = new javax.swing.JLabel();
        GameTitle2 = new javax.swing.JLabel();
        StClient = new javax.swing.JLabel();
        StServer = new javax.swing.JLabel();
        StB1 = new javax.swing.JLabel();
        StB2 = new javax.swing.JLabel();
        StB3 = new javax.swing.JLabel();
        StB4 = new javax.swing.JLabel();
        StB5 = new javax.swing.JLabel();
        B5 = new javax.swing.JLabel();
        B4 = new javax.swing.JLabel();
        B3 = new javax.swing.JLabel();
        B2 = new javax.swing.JLabel();
        B1 = new javax.swing.JLabel();
        StBom = new javax.swing.JLabel();
        Bomb = new javax.swing.JLabel();
        StTimer = new javax.swing.JLabel();
        Timer = new javax.swing.JLabel();
        ImReady = new javax.swing.JToggleButton();
        LblReady = new javax.swing.JLabel();
        StEnemy = new javax.swing.JLabel();
        Enemy = new javax.swing.JLabel();
        Wall = new org.edisoncor.gui.panel.PanelImage();
        Status = new javax.swing.JLabel();
        Title = new javax.swing.JLabel();
        Options = new org.edisoncor.gui.panel.PanelImage();
        BtnBack = new javax.swing.JButton();
        TittleOptions = new javax.swing.JLabel();
        Opc1 = new javax.swing.JLabel();
        FOpc1 = new javax.swing.JToggleButton();
        Opc2 = new javax.swing.JLabel();
        FOpc2 = new javax.swing.JToggleButton();
        Opc3 = new javax.swing.JLabel();
        FOpc3 = new javax.swing.JToggleButton();
        Networks = new org.edisoncor.gui.panel.PanelImage();
        TittleServer = new javax.swing.JLabel();
        TxtServer = new javax.swing.JTextField();
        BtnServer = new javax.swing.JButton();
        ActualIP = new javax.swing.JLabel();
        BtnOptions = new javax.swing.JButton();

        Chat.setTitle("Chat");
        Chat.setMaximumSize(new java.awt.Dimension(490, 225));
        Chat.setMinimumSize(new java.awt.Dimension(490, 225));
        Chat.setPreferredSize(new java.awt.Dimension(490, 225));
        Chat.setResizable(false);
        Chat.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setEnabled(false);

        ChatWindow.setColumns(20);
        ChatWindow.setRows(5);
        ChatWindow.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ChatWindow.setEnabled(false);
        jScrollPane1.setViewportView(ChatWindow);

        Chat.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 170));

        ChatText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ChatText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ChatTextKeyReleased(evt);
            }
        });
        Chat.getContentPane().add(ChatText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 490, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Batalla Naval");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Game.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/wallpaper2.jpg"))); // NOI18N
        Game.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Grid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/grid2.gif"))); // NOI18N
        Grid.setMaximumSize(new java.awt.Dimension(450, 400));
        Grid.setMinimumSize(new java.awt.Dimension(450, 400));
        Grid.setPreferredSize(new java.awt.Dimension(450, 400));
        Grid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GridMouseClicked(evt);
            }
        });
        Grid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Game.add(Grid, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 450, 400));

        Game1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/mar.jpg"))); // NOI18N
        Game1.setMaximumSize(new java.awt.Dimension(450, 400));
        Game1.setMinimumSize(new java.awt.Dimension(450, 400));
        Game1.setPreferredSize(new java.awt.Dimension(450, 400));
        Game1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Game.add(Game1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 450, 400));

        Game2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/mar.jpg"))); // NOI18N
        Game2.setMaximumSize(new java.awt.Dimension(450, 400));
        Game2.setMinimumSize(new java.awt.Dimension(450, 400));
        Game2.setPreferredSize(new java.awt.Dimension(450, 400));
        Game2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        explosion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/explosion.gif"))); // NOI18N
        Game2.add(explosion, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        barco1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/barco1.png"))); // NOI18N
        barco1.setMaximumSize(new java.awt.Dimension(100, 100));
        barco1.setMinimumSize(new java.awt.Dimension(100, 100));
        barco1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco1MouseDragged(evt);
            }
        });
        Game2.add(barco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 100));

        barco2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/barco2.png"))); // NOI18N
        barco2.setMaximumSize(new java.awt.Dimension(80, 100));
        barco2.setMinimumSize(new java.awt.Dimension(80, 100));
        barco2.setPreferredSize(new java.awt.Dimension(80, 100));
        barco2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco2MouseDragged(evt);
            }
        });
        Game2.add(barco2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 80, 100));

        barco3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/barco3.png"))); // NOI18N
        barco3.setMaximumSize(new java.awt.Dimension(100, 120));
        barco3.setMinimumSize(new java.awt.Dimension(100, 120));
        barco3.setPreferredSize(new java.awt.Dimension(100, 120));
        barco3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco3MouseDragged(evt);
            }
        });
        Game2.add(barco3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 100, 120));

        barco4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/barco1.png"))); // NOI18N
        barco4.setMaximumSize(new java.awt.Dimension(50, 50));
        barco4.setMinimumSize(new java.awt.Dimension(50, 50));
        barco4.setPreferredSize(new java.awt.Dimension(50, 50));
        barco4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco4MouseDragged(evt);
            }
        });
        Game2.add(barco4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 50, 50));

        barco5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/barco3.png"))); // NOI18N
        barco5.setMaximumSize(new java.awt.Dimension(80, 100));
        barco5.setMinimumSize(new java.awt.Dimension(80, 100));
        barco5.setPreferredSize(new java.awt.Dimension(80, 100));
        barco5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barco5MouseDragged(evt);
            }
        });
        Game2.add(barco5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 80, 100));

        Game.add(Game2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 450, 400));

        GameTitle1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        GameTitle1.setText("Área de Ataque");
        Game.add(GameTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        GameTitle2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        GameTitle2.setText("Mis Barcos");
        Game.add(GameTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        StClient.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StClient.setText("Se ha conectado el cliente: 0.0.0.0");
        Game.add(StClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, -1, -1));

        StServer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StServer.setText("Conectado con la IP: 0.0.0.0");
        Game.add(StServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        StB1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StB1.setText("Barco 1");
        Game.add(StB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 500, -1, -1));

        StB2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StB2.setText("Barco 2");
        Game.add(StB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 500, -1, -1));

        StB3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StB3.setText("Barco 3");
        Game.add(StB3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, -1, -1));

        StB4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StB4.setText("Barco 4");
        Game.add(StB4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 500, -1, -1));

        StB5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StB5.setText("Barco 5");
        Game.add(StB5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 500, -1, -1));

        B5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        B5.setText("5");
        Game.add(B5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 530, -1, -1));

        B4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        B4.setText("3");
        Game.add(B4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 530, -1, -1));

        B3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        B3.setText("10");
        Game.add(B3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 530, -1, -1));

        B2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        B2.setText("5");
        Game.add(B2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, -1, -1));

        B1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        B1.setText("8");
        Game.add(B1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, -1, -1));

        StBom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StBom.setForeground(new java.awt.Color(0, 102, 0));
        StBom.setText("Bombas");
        Game.add(StBom, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 500, -1, -1));

        Bomb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Bomb.setForeground(new java.awt.Color(0, 102, 0));
        Bomb.setText("X");
        Game.add(Bomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 500, -1, -1));

        StTimer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StTimer.setText("Tiempo");
        Game.add(StTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 530, -1, -1));

        Timer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Timer.setText("X");
        Game.add(Timer, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 530, -1, -1));

        ImReady.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ImReady.setText("Iniciar");
        ImReady.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImReadyActionPerformed(evt);
            }
        });
        Game.add(ImReady, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, 50));

        LblReady.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        LblReady.setForeground(new java.awt.Color(255, 0, 0));
        LblReady.setText("Inicia la Partida");
        Game.add(LblReady, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        StEnemy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StEnemy.setForeground(new java.awt.Color(255, 0, 0));
        StEnemy.setText("Enemigos");
        Game.add(StEnemy, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 560, -1, -1));

        Enemy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Enemy.setForeground(new java.awt.Color(255, 0, 0));
        Enemy.setText("5");
        Game.add(Enemy, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 560, -1, -1));

        getContentPane().add(Game, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        Wall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/wallpaper.jpg"))); // NOI18N
        Wall.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Status.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        Status.setForeground(new java.awt.Color(204, 204, 204));
        Status.setText("Cargando el Juego...");
        Wall.add(Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, -1, -1));

        Title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/title.png"))); // NOI18N
        Wall.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        getContentPane().add(Wall, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        Options.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/wallpaper1.jpg"))); // NOI18N
        Options.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtnBack.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        BtnBack.setText("Volver Atras");
        BtnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBackActionPerformed(evt);
            }
        });
        Options.add(BtnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 551, 150, 40));

        TittleOptions.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        TittleOptions.setForeground(new java.awt.Color(255, 255, 255));
        TittleOptions.setText("Opciones del Juego");
        Options.add(TittleOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        Opc1.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        Opc1.setForeground(new java.awt.Color(255, 255, 255));
        Opc1.setText("Música");
        Options.add(Opc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, -1, -1));

        FOpc1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        FOpc1.setSelected(true);
        FOpc1.setText("Encendido");
        FOpc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FOpc1ActionPerformed(evt);
            }
        });
        Options.add(FOpc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, -1, -1));

        Opc2.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        Opc2.setForeground(new java.awt.Color(255, 255, 255));
        Opc2.setText("Sonidos");
        Options.add(Opc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, -1, -1));

        FOpc2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        FOpc2.setSelected(true);
        FOpc2.setText("Encendido");
        FOpc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FOpc2ActionPerformed(evt);
            }
        });
        Options.add(FOpc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, -1, -1));

        Opc3.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        Opc3.setForeground(new java.awt.Color(255, 255, 255));
        Opc3.setText("Chat");
        Options.add(Opc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, -1, -1));

        FOpc3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        FOpc3.setSelected(true);
        FOpc3.setText("Encendido");
        FOpc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FOpc3ActionPerformed(evt);
            }
        });
        Options.add(FOpc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        getContentPane().add(Options, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        Networks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/wallpaper1.jpg"))); // NOI18N
        Networks.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TittleServer.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        TittleServer.setForeground(new java.awt.Color(255, 255, 255));
        TittleServer.setText("Selecciona el Servidor");
        Networks.add(TittleServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        TxtServer.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Networks.add(TxtServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 450, -1));

        BtnServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnServer.setText("Conectar");
        BtnServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnServerActionPerformed(evt);
            }
        });
        Networks.add(BtnServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, -1, 30));

        ActualIP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ActualIP.setForeground(new java.awt.Color(255, 255, 255));
        ActualIP.setText("IP Actual: ");
        Networks.add(ActualIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, -1, -1));

        BtnOptions.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        BtnOptions.setText("Opciones");
        BtnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOptionsActionPerformed(evt);
            }
        });
        Networks.add(BtnOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 551, 150, 40));

        getContentPane().add(Networks, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnServerActionPerformed
        MC.setServer(StClient, TxtServer.getText());
        Networks.setVisible(false);
        Game.setVisible(true);
        explosion.setVisible(false);
        Grid.setVisible(false);
        LblReady.setVisible(false);
        fillPos();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAttack();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                fillHealt();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                keepLocation();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameOver();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                youWin();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                resetBombs();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                resetAll();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getChat();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startAttack();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                enemyAttack();
            }
        }).start();
        Chat.setVisible(ActiveChat);
    }//GEN-LAST:event_BtnServerActionPerformed

    private void barco1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco1MouseDragged
        int X = evt.getXOnScreen() - 260;
        int Y = evt.getYOnScreen() - 220;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > 350) {
            X = 350;
        }
        if (Y > 300) {
            Y = 300;
        }
        barco1.setLocation(X, Y);
        fillPos();
    }//GEN-LAST:event_barco1MouseDragged

    private void barco2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco2MouseDragged
        int X = evt.getXOnScreen() - 260;
        int Y = evt.getYOnScreen() - 220;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > 370) {
            X = 370;
        }
        if (Y > 300) {
            Y = 300;
        }
        barco2.setLocation(X, Y);
        fillPos();
    }//GEN-LAST:event_barco2MouseDragged

    private void barco3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco3MouseDragged
        int X = evt.getXOnScreen() - 260;
        int Y = evt.getYOnScreen() - 220;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > 350) {
            X = 350;
        }
        if (Y > 280) {
            Y = 280;
        }
        barco3.setLocation(X, Y);
        fillPos();
    }//GEN-LAST:event_barco3MouseDragged

    private void barco4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco4MouseDragged
        int X = evt.getXOnScreen() - 230;
        int Y = evt.getYOnScreen() - 190;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > 400) {
            X = 400;
        }
        if (Y > 350) {
            Y = 350;
        }
        barco4.setLocation(X, Y);
        fillPos();
    }//GEN-LAST:event_barco4MouseDragged

    private void barco5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barco5MouseDragged
        int X = evt.getXOnScreen() - 240;
        int Y = evt.getYOnScreen() - 220;
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > 370) {
            X = 370;
        }
        if (Y > 300) {
            Y = 300;
        }
        barco5.setLocation(X, Y);
        fillPos();
    }//GEN-LAST:event_barco5MouseDragged

    private void GridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridMouseClicked
        if (Bombs > 0) {
            int X = evt.getX();
            int Y = evt.getY();
            MC.sendData("C" + X + "-" + Y);
            //attack(X, Y);
            Bombs--;
        } else {
            JOptionPane.showMessageDialog(null, "No puedes atacar, no tienes bombas", "Falta de Bombas", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_GridMouseClicked

    private void BtnOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOptionsActionPerformed
        Options.setVisible(true);
        Networks.setVisible(false);
    }//GEN-LAST:event_BtnOptionsActionPerformed

    private void BtnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBackActionPerformed
        Options.setVisible(false);
        Networks.setVisible(true);
    }//GEN-LAST:event_BtnBackActionPerformed

    private void FOpc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FOpc1ActionPerformed
        if (FOpc1.isSelected()) {
            FX.turnOff(false);
        } else {
            FX.turnOff(true);
        }
    }//GEN-LAST:event_FOpc1ActionPerformed

    private void FOpc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FOpc2ActionPerformed
        if (FOpc2.isSelected()) {
            FX.turnOffFX(false);
        } else {
            FX.turnOffFX(true);
        }
    }//GEN-LAST:event_FOpc2ActionPerformed

    private void FOpc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FOpc3ActionPerformed
        ActiveChat = FOpc3.isSelected();
    }//GEN-LAST:event_FOpc3ActionPerformed

    private void ImReadyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImReadyActionPerformed
        Ready = true;
        MC.sendData("R");
        ImReady.setVisible(false);
        FX.play("start.wav");
    }//GEN-LAST:event_ImReadyActionPerformed

    private void ChatTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChatTextKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ChatWindow.append("Local: " + ChatText.getText() + "\n");
            MC.sendData("X" + ChatText.getText());
            ChatText.setText("");
        }
    }//GEN-LAST:event_ChatTextKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActualIP;
    private javax.swing.JLabel B1;
    private javax.swing.JLabel B2;
    private javax.swing.JLabel B3;
    private javax.swing.JLabel B4;
    private javax.swing.JLabel B5;
    private javax.swing.JLabel Bomb;
    private javax.swing.JButton BtnBack;
    private javax.swing.JButton BtnOptions;
    private javax.swing.JButton BtnServer;
    private javax.swing.JFrame Chat;
    private javax.swing.JTextField ChatText;
    private javax.swing.JTextArea ChatWindow;
    private javax.swing.JLabel Enemy;
    private javax.swing.JToggleButton FOpc1;
    private javax.swing.JToggleButton FOpc2;
    private javax.swing.JToggleButton FOpc3;
    private org.edisoncor.gui.panel.PanelImage Game;
    private org.edisoncor.gui.panel.PanelImage Game1;
    private org.edisoncor.gui.panel.PanelImage Game2;
    private javax.swing.JLabel GameTitle1;
    private javax.swing.JLabel GameTitle2;
    private org.edisoncor.gui.panel.PanelImage Grid;
    private javax.swing.JToggleButton ImReady;
    private javax.swing.JLabel LblReady;
    private org.edisoncor.gui.panel.PanelImage Networks;
    private javax.swing.JLabel Opc1;
    private javax.swing.JLabel Opc2;
    private javax.swing.JLabel Opc3;
    private org.edisoncor.gui.panel.PanelImage Options;
    private javax.swing.JLabel StB1;
    private javax.swing.JLabel StB2;
    private javax.swing.JLabel StB3;
    private javax.swing.JLabel StB4;
    private javax.swing.JLabel StB5;
    private javax.swing.JLabel StBom;
    private javax.swing.JLabel StClient;
    private javax.swing.JLabel StEnemy;
    private javax.swing.JLabel StServer;
    private javax.swing.JLabel StTimer;
    private javax.swing.JLabel Status;
    private javax.swing.JLabel Timer;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel TittleOptions;
    private javax.swing.JLabel TittleServer;
    private javax.swing.JTextField TxtServer;
    private org.edisoncor.gui.panel.PanelImage Wall;
    private org.edisoncor.gui.panel.PanelImage barco1;
    private org.edisoncor.gui.panel.PanelImage barco2;
    private org.edisoncor.gui.panel.PanelImage barco3;
    private org.edisoncor.gui.panel.PanelImage barco4;
    private org.edisoncor.gui.panel.PanelImage barco5;
    private javax.swing.JLabel explosion;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
