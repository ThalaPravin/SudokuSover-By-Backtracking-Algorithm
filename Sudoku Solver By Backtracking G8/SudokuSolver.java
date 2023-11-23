import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolver extends JPanel {

    public int count;
    public static final int SIZE = 9;
    public static int[][] get = {
            { 0, 0, 1, 8, 7, 3, 0, 9, 0 },
            { 7, 0, 3, 2, 5, 0, 0, 0, 8 },
            { 9, 8, 0, 1, 0, 4, 3, 0, 7 },
            { 1, 0, 5, 0, 0, 0, 0, 0, 0 },
            { 4, 0, 0, 0, 0, 0, 0, 0, 2 },
            { 0, 0, 0, 0, 0, 0, 5, 0, 3 },
            { 5, 0, 8, 3, 0, 1, 0, 2, 6 },
            { 2, 0, 0, 0, 4, 0, 9, 0, 0 },
            { 0, 9, 0, 6, 2, 5, 0, 8, 1 }
    };
    JTextField[][] textField;
    JPanel panel;
    JButton button;
    Font font;

    // Use enums for color constants
    public enum CellColor {
        BLUE, ORANGE, RED, GREEN, WHITE, PINK, CYAN, YELLOW, LIGHT_GRAY
    }

    // Constructor
    SudokuSolver() {
        JFrame frame = new JFrame("Sudoku Solver By Backtracking Algorithm By G8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        font = new Font("SansSerif", Font.BOLD, 40);
        textField = new JTextField[9][9];
        button = new JButton("Solve");

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 600));
        GridLayout grid = new GridLayout(10, 9);
        panel.setLayout(grid);
        frame.setContentPane(panel);

        initializeBoard(); 

        gui(0, 1, CellColor.BLUE);
        gui(1, 3, CellColor.ORANGE);
        gui(1, 6, CellColor.RED);
        gui(4, 1, CellColor.GREEN);
        gui(4, 3, CellColor.WHITE);
        gui(4, 7, CellColor.PINK);
        gui(6, 1, CellColor.CYAN);
        gui(6, 3, CellColor.YELLOW);
        gui(6, 7, CellColor.LIGHT_GRAY);

        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (textField[i][j].getText().equals("")) {
                            get[i][j] = 0;
                        } else
                            get[i][j] = Integer.parseInt(textField[i][j].getText());
                    }
                }

                if (solveSudoku()) {
                    System.out.println("Number of Backtrackings:" + count);
                    printSudoku();
                } else
                    System.out.println("No solution");
            }

            public boolean solveSudoku() {
                int row = 0;
                int col = 0;
                int[] a = FindEmptyLoction(row, col);
                // if there is no empty locations then the sudoku is already solved
                if (a[0] == 0)
                    return true;
                row = a[1];// get the position of empty row
                col = a[2];// get the position of empty column
                for (int i = 1; i <= SIZE; i++)// attempt to fill number between 1 to 9
                {
                    if (Numberissafe(i, row, col))// check the number i can be filled or not into empty location
                    {
                        get[row][col] = i;// fill the number i
                        // backtracking
                        if (solveSudoku())
                            return true;
                        count++;

                        get[row][col] = 0;
                    }
                }

                return false;
            }

            public int[] FindEmptyLoction(int row, int col)
            {
                int flag= 0;
                for(int i=0;i<SIZE;i++){
                    for(int j=0;j<SIZE;j++){
                        if(get[i][j] == 0)//check cell is empty
                        {
                            //changing the values of row and col
                            row = i;
                            col = j;
                            //there is  empty cells
                            flag = 1;
                            int[] a = {flag, row, col};
                            return a;
                        }
                    }
                }
                int[] a = {flag, -1, -1};
                return a;
            }
        
            public boolean Numberissafe(int n, int r, int c){
                //checking in row
                for(int i=0;i<SIZE;i++){
                    if(get[r][i] == n)//there is a cell with same value
                        return false;
                } 
                //checking col
                for(int i=0;i<SIZE;i++){
                    if(get[i][c] == n)//there is a cell with the value equal to i
                        return false;
                }
                //checking sub matrix
                int row_start = (r/3)*3;
                int col_start = (c/3)*3;
                for(int i=row_start;i<row_start+3;i++){
                    for(int j=col_start;j<col_start+3;j++){
                        if(get[i][j]==n)
                            return false;
                    }
                }
                return true;
            }
            //printing the sudoku
            public void printSudoku(){
                for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    textField[i][j].setText(""+get[i][j]);//print the vaues on to board
                    textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
                    textField[i][j].setFont(font);
                    }
                }
            }
        

           
        });

        frame.pack();
        frame.setVisible(true);
    }

    // Extracted method to initialize the Sudoku board
    private void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                panel.add(textField[i][j] = new JTextField(5));
                if (get[i][j] == 0) {
                    textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
                    textField[i][j].setFont(font);
                    continue;
                } else {
                    textField[i][j].setText("" + get[i][j]);
                    textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
                    textField[i][j].setFont(font);
                }
            }
        }
    }

    // Updated gui method to use CellColor enum
    public final void gui(int r, int c, CellColor color) {
        int row_start = (r / 3) * 3;
        int col_start = (c / 3) * 3; //to make it divisible by 3
        for (int i = row_start; i < row_start + 3; i++) {
            for (int j = col_start; j < col_start + 3; j++) {
                setCellColor(i, j, color);
            }
        }
    }

    // Method to set cell color based on the CellColor enum
    private void setCellColor(int row, int col, CellColor color) {
        switch (color) {
            case BLUE:
                textField[row][col].setBackground(Color.blue);
                break;
            case ORANGE:
                textField[row][col].setBackground(Color.orange);
                break;
            case RED:
                textField[row][col].setBackground(Color.red);
                break;
            case GREEN:
                textField[row][col].setBackground(Color.green);
                break;
            case WHITE:
                textField[row][col].setBackground(Color.white);
                break;
            case PINK:
                textField[row][col].setBackground(Color.pink);
                break;
            case CYAN:
                textField[row][col].setBackground(Color.cyan);
                break;
            case YELLOW:
                textField[row][col].setBackground(Color.black);
                textField[row][col].setForeground(Color.yellow);
                break;
            case LIGHT_GRAY:
                textField[row][col].setBackground(Color.lightGray);
                break;
        }
    }


    public static void main(String[] args) {
        SudokuSolver sample = new SudokuSolver();
    }
}
