
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainmenu extends JFrame{
    private JRadioButton GeneticRadio;
    private JTextField textField1;
    private JButton appliquerButton;
    private JLabel welcome;
    private JLabel chose;
    private JLabel N;
    private JPanel MainPage;
    private JRadioButton Pso;
    private JTextField textField2;

    public Mainmenu(){
        setTitle("Chess Board");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ButtonGroup group = new ButtonGroup();
        group.add(GeneticRadio);
        group.add(Pso);
        add(MainPage);
        Font font = new Font("Arial", Font.PLAIN, 24);
        welcome.setFont(font);
        setVisible(true);
        appliquerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean sel = ((GeneticRadio.isSelected() || Pso.isSelected()) && !textField1.getText().isEmpty() && !textField2.getText().isEmpty());
                if (sel){
                    long Max_generations = Integer.parseInt(textField2.getText());
                    int n = Integer.parseInt(textField1.getText());
                    if(GeneticRadio.isSelected()){
                        int popu_size = 100;
                        double mutation_proba = 0.6;
                        //long Max_generations = 2147483647;
                        int Geno_size = n;
                        GenSolution sol = Genetic.Genetic_NQueen(popu_size, Geno_size, mutation_proba, Max_generations);
                        createChessBoard(n, sol);
                    }
                    if(Pso.isSelected()){
                        swarm a = new swarm(100, n);
                        float w = 0.7f;// Inertia
                        float c1 = 1.49445f;// Personal Influence
                        float c2 = 1.49445f;// Social Influence
                        PsoSolution sol =  a.runPSO(Max_generations,w, c1,c2);
                        createChessBoard(n, sol);
                    }

                }

            }
        });
    }
    private void createChessBoard(int n, PsoSolution sol) {
        // Remove the main menu and create the chess board
        getContentPane().removeAll();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        int [] Solution = sol.parti.position;
        // Create the chess board
        JPanel chessBoardPanel = new JPanel(new GridLayout(n, n));
        for (int row = 0; row < n; row++) {
            int k = Solution[row];
            for (int col = 0; col < n; col++) {
                JPanel square = new JPanel();
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.lightGray);
                } else {
                    square.setBackground(Color.darkGray);
                }
                if(k == col+1){
                    JLabel chessLabel = new JLabel();

                    ImageIcon icon = new ImageIcon("src/queen.png");
                    Image img = icon.getImage();
                    Image imgScale = img.getScaledInstance((this.getWidth()/(n+1)), (this.getHeight()/(n+1)), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(imgScale);

                    chessLabel.setIcon(scaledIcon);
                    square.add(chessLabel);
                }
                chessBoardPanel.add(square);
            }
        }
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel textFieldsPanel = new JPanel(new GridLayout(2, 1));
        JTextField genere;
        if(sol.optimal == true){
            genere = new JTextField("La solution est optimal et la fitness est: "+ sol.parti.fitness()+"/"+ sol.parti.maxFitness);
        }else{
            genere = new JTextField("La solution n'est pas optimal et la fitness est: "+ sol.parti.fitness()+"/"+ sol.parti.maxFitness);
        }
        JTextField developpes = new JTextField("Le nombre d'iterations est: "+sol.numiter);
        textFieldsPanel.add(genere);
        textFieldsPanel.add(developpes);
        JButton revenir = new JButton("revenir au menu");

        revenir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                add(MainPage);
                setSize(500, 300);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                MainPage.setPreferredSize(new Dimension(getWidth(), getHeight()));
                revalidate();
                repaint();
            }
        });

        bottomPanel.add(textFieldsPanel, BorderLayout.CENTER);
        bottomPanel.add(revenir, BorderLayout.EAST);


        add(chessBoardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        revalidate();
        repaint();
    }
    private void createChessBoard(int n, GenSolution sol) {
        // Remove the main menu and create the chess board
        getContentPane().removeAll();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        int [] Solution = sol.geno.board;
        // Create the chess board
        JPanel chessBoardPanel = new JPanel(new GridLayout(n, n));
        for (int row = 0; row < n; row++) {
            int k = Solution[row];
            for (int col = 0; col < n; col++) {
                JPanel square = new JPanel();
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.lightGray);
                } else {
                    square.setBackground(Color.darkGray);
                }
                if(k == col+1){
                    JLabel chessLabel = new JLabel();

                    ImageIcon icon = new ImageIcon("src/queen.png");
                    Image img = icon.getImage();
                    Image imgScale = img.getScaledInstance((this.getWidth()/(n+1)), (this.getHeight()/(n+1)), Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(imgScale);

                    chessLabel.setIcon(scaledIcon);
                    square.add(chessLabel);
                }
                chessBoardPanel.add(square);
            }
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel textFieldsPanel = new JPanel(new GridLayout(2, 1));
        JTextField genere;
        if(sol.optimal == true){
            genere = new JTextField("La solution est optimal et la fitness est: "+ sol.geno.fitness()+"/"+ sol.geno.maxFitness);
        }else{
            genere = new JTextField("La solution n'est pas optimal et la fitness est: "+ sol.geno.fitness()+"/"+ sol.geno.maxFitness);
        }
        JTextField developpes = new JTextField("La derniere generation est: "+sol.Generation);
        textFieldsPanel.add(genere);
        textFieldsPanel.add(developpes);
        JButton revenir = new JButton("revenir au menu");

        revenir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                setLayout(new FlowLayout());
                add(MainPage);
                setSize(500, 335);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                MainPage.setPreferredSize(new Dimension(500, 300));
                revalidate();
                repaint();
            }
        });

        bottomPanel.add(textFieldsPanel, BorderLayout.CENTER);
        bottomPanel.add(revenir, BorderLayout.EAST);


        add(chessBoardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        revalidate();
        repaint();
    }

    public static void main(String[] args) {

        Mainmenu a = new Mainmenu();

    }

}
