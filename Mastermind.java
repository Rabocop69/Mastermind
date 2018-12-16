 import java.awt.*; 
    import java.io.*;
    import javax.swing.*; 
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;
    import javax.sound.sampled.AudioInputStream; 
    import javax.sound.sampled.AudioSystem; 
    import javax.sound.sampled.*;
 public class Mastermind extends JFrame
    {

    public static int found = 0;
    public static int guesses = 0;
    public final JTextField guess = new JTextField(20);
    public static JFormattedTextField nG = new JFormattedTextField();
public Mastermind()
{   

    final JFrame f1 = new JFrame("Mastermind: Can you win?");
    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

    //runs a song/midi upon start of the game
    int replys = JOptionPane.showConfirmDialog(f1, "Welcome to Mastermind! \n T he game to test your brain." +
                                                                      "\n\n\nWould you like to play?", "Mastermind: Test Your Brain!", JOptionPane.YES_NO_OPTION);
    if(replys == JOptionPane.YES_OPTION)
    {
        try
        {

            //AudioFormat audioFormat;
            //AudioInputStream audioInputStream;
            //SourceDataLine sourceDataLine;

            File soundFile = new File("swfinal.mid");
                            AudioInputStream ai =      AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(ai);
            clip.start();
        }
        catch (Exception f)
        {}
    }
    else
    {
        System.exit(0);
    }

    //makes the panel to display the computer's guess
    final JPanel gamePanel = new JPanel(new GridLayout(1,4,5,5));
    f1.add(gamePanel);
    gamePanel.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.GREEN));
    gamePanel.setBackground(Color.BLACK);   

    //makes the panel to display the heading
    final JPanel Gs = new JPanel();
    f1.add(Gs);
    Gs.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.GREEN));
    Gs.setBackground(Color.BLACK);

    //makes the panel where you control everything
    final JPanel p2 = new JPanel();
    f1.add(p2);
    p2.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.GREEN));
    p2.setBackground(Color.BLACK);

    final JLabel line = new JLabel("");
    p2.add(line);
    line.setForeground(Color.GREEN);

    //creates the submit button 
    final JButton submit = new JButton("Submit");
    p2.add(submit);

    //adds a text field to display the number of guesses
    final JFormattedTextField tf1 = new JFormattedTextField();
    p2.add(tf1);
    tf1.setColumns(2);
    tf1.setEditable(false);
    tf1.setForeground(Color.RED);
    tf1.setValue(new Integer(guesses));

    JLabel here = new JLabel("Take a guess: ");
            p2.add(here);
    here.setForeground(Color.GREEN);

    final JLabel master = new JLabel("MASTERMIND: TEST YOUR MIND!");
    master.setForeground(Color.green);
    Font newf = new Font("SansSerif", Font.BOLD + Font.ITALIC, 32);
    master.setFont(newf);
    Gs.add(master);

    //add and initializes the computers guess buttons
    //and its guesses
    final String[] colored = new String[4];
    colored[0] = "circle-red.png";
    colored[1] = "circle-blue.png";
    colored[2] = "circle-green.png";
    colored[3] = "circle-yellow.png";

    final JButton[] compGuess = new JButton[4];
    compGuess[0] = new JButton();
    compGuess[1] = new JButton();
    compGuess[2] = new JButton();
    compGuess[3] = new JButton();

    gamePanel.add(compGuess[0]);
    gamePanel.add(compGuess[1]);
    gamePanel.add(compGuess[2]);
    gamePanel.add(compGuess[3]);

    compGuess[0].setVisible(false);
    compGuess[1].setVisible(false);
    compGuess[2].setVisible(false);
    compGuess[3].setVisible(false);

    final String[] colored2 = new String[4];
    final int[] checkFilled = new int[4];
    for(int fill = 0; fill < 4; fill++)
    {
        checkFilled[fill] = 0;
    }
    int counter = 0;
    do
    {
        int Random = (int)(Math.random()*4);

        if(checkFilled[Random] == 0)
        {
            colored2[counter] = colored[Random];
            checkFilled[Random] = 1;
            counter++;
        }
    }
    while(counter < 4);

    final ImageIcon cell1 = new ImageIcon(colored2[0]);
    final ImageIcon cell2 = new ImageIcon(colored2[1]);
    final ImageIcon cell3 = new ImageIcon(colored2[2]);
    final ImageIcon cell4 = new ImageIcon(colored2[3]);

    compGuess[0].setIcon(cell1);
    compGuess[1].setIcon(cell2);
    compGuess[2].setIcon(cell3);
    compGuess[3].setIcon(cell4);

    //adding a label to tell user where their guessing at
    JLabel user = new JLabel("Your Guess: ");
    user.setForeground(Color.green);
    p2.add(user);

    //adding your guessing buttons
    final JButton[] uG = new JButton[4];
    uG[0] = new JButton();
    uG[1] = new JButton();
    uG[2] = new JButton();
    uG[3] = new JButton();

    p2.add(uG[0]);
    p2.add(uG[1]);
    p2.add(uG[2]);
    p2.add(uG[3]);

    //This is the mouse event handler that make it change colors
    //to the corresponding color that you want with the click of
    //the mouse on the button
    uG[0].addMouseListener(new MouseListener()
    {
        @Override
                    public void mouseClicked(MouseEvent e) 
        {
            if(e.getClickCount() == 1)
            {
                uG[0].setIcon(new ImageIcon("circle-red.png"));
            }
            else if(e.getClickCount() == 2)
            {
                uG[0].setIcon(new ImageIcon("circle-blue.png"));
            }
            else if(e.getClickCount() == 3)
            {
                uG[0].setIcon(new ImageIcon("circle-green.png"));
            }
            else if(e.getClickCount() == 4)
            {
                uG[0].setIcon(new ImageIcon("circle-yellow.png"));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });

    uG[1].addMouseListener(new MouseListener()
    {
        @Override
                    public void mouseClicked(MouseEvent e) 
        {
            if(e.getClickCount() == 1)
            {
                uG[1].setIcon(new ImageIcon("circle-red.png"));
            }
            else if(e.getClickCount() == 2)
            {
                uG[1].setIcon(new ImageIcon("circle-blue.png"));
            }
            else if(e.getClickCount() == 3)
            {
                uG[1].setIcon(new ImageIcon("circle-green.png"));
            }
            else if(e.getClickCount() == 4)
            {
                uG[1].setIcon(new ImageIcon("circle-yellow.png"));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });

    uG[2].addMouseListener(new MouseListener()
    {
        @Override
                    public void mouseClicked(MouseEvent e) 
        {
            if(e.getClickCount() == 1)
            {
                uG[2].setIcon(new ImageIcon("circle-red.png"));
            }
            else if(e.getClickCount() == 2)
            {
                uG[2].setIcon(new ImageIcon("circle-blue.png"));
            }
            else if(e.getClickCount() == 3)
            {
                uG[2].setIcon(new ImageIcon("circle-green.png"));
            }
            else if(e.getClickCount() == 4)
            {
                uG[2].setIcon(new ImageIcon("circle-yellow.png"));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });

    uG[3].addMouseListener(new MouseListener()
    {       
                    @Override
        public void mouseClicked(MouseEvent e) 
        {
            if(e.getClickCount() == 1)
            {
                uG[3].setIcon(new ImageIcon("circle-red.png"));
            }
            else if(e.getClickCount() == 2)
            {
                uG[3].setIcon(new ImageIcon("circle-blue.png"));
            }
            else if(e.getClickCount() == 3)
            {
                uG[3].setIcon(new ImageIcon("circle-green.png"));
            }
            else if(e.getClickCount() == 4)
            {
                uG[3].setIcon(new ImageIcon("circle-yellow.png"));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });

    submit.addMouseListener(new MouseListener()
    {

    @Override
public void mouseClicked(MouseEvent e)
{   
    if(e.getSource() == submit)
    {
        int s = 0;

        System.out.println(uG[0].getIcon());
        System.out.println(uG[1].getIcon());
        System.out.println(uG[2].getIcon());
        System.out.println(uG[3].getIcon());
        System.out.println(compGuess[0].getIcon());
        System.out.println(compGuess[1].getIcon());
        System.out.println(compGuess[2].getIcon());
        System.out.println(compGuess[3].getIcon());

        if(uG[0].getIcon() == compGuess[0].getIcon() && uG[1].getIcon() == compGuess[1].getIcon() && uG[2].getIcon() == compGuess[2].getIcon() && uG[3].getIcon() == compGuess[3].getIcon())
        {
            JOptionPane.showMessageDialog(f1, "You have guessed them all coorect!", "Mastermind: Test Your Mind", JOptionPane.INFORMATION_MESSAGE);
            submit.setEnabled(false);
            compGuess[0].setEnabled(false);
            compGuess[1].setEnabled(false);
            compGuess[2].setEnabled(false);
            compGuess[3].setEnabled(false);
            uG[0].setEnabled(false);
            uG[1].setEnabled(false);
            uG[2].setEnabled(false);
            uG[3].setEnabled(false);
        }
        else
        {   
            JOptionPane.showMessageDialog(f1, "Please try again!", "Mastermind: Test Your Mind", JOptionPane.INFORMATION_MESSAGE);
        }


        guesses++;
        tf1.setValue(new Integer(guesses));

        if(guesses == 10)
        {
            JOptionPane.showMessageDialog(f1, "Thank you for playing! \n\n\n Have a wonderful day.", "Mastermind: Test Your Mind", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        int x = 0;
        int y = 0;

        if(uG[0].getIcon() == compGuess[0].getIcon())
        {
            x = x + 1;
        }
        if(uG[1].getIcon() == compGuess[1].getIcon())
        {
            x = x + 1;
        }
        if(uG[2].getIcon() == compGuess[2].getIcon())
        {
            x = x + 1;
        }
        if(uG[3].getIcon() == compGuess[2].getIcon())
        {
            x = x + 1;
        }

        if(uG[0].getIcon() != compGuess[0].getIcon())
        {
            y = y + 1;
        }
        if(uG[1].getIcon() != compGuess[1].getIcon())
        {
            y = y + 1;
        }
        if(uG[2].getIcon() != compGuess[2].getIcon())
        {
            y = y + 1;
        }
        if(uG[3].getIcon()!= compGuess[2].getIcon())
        {
            y = y + 1;
        }

        JOptionPane.showMessageDialog(f1, "You have " + x + " colors correct and in the right position."+
                                                     "\nYou have " + y + " colors correct but in the wrong position.", "Mastermind: Test Your      Mind", JOptionPane.INFORMATION_MESSAGE);
    }
}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    });
    //set the component of the game on the f1
    //and also sets the size of the fame and makes
    // its visible to the user
    f1.add(Gs, BorderLayout.NORTH);
    f1.add(gamePanel, BorderLayout.CENTER);
    f1.add(p2, BorderLayout.SOUTH);
    f1.setSize(650, 350);
    f1.setVisible(true);
    f1.setResizable(false);
    f1.setLocationRelativeTo(null);
}

public static void main(String[] args)
{
    Mastermind mastermind = new Mastermind();
}   
    }

