import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Sudoku extends JPanel
{
    private int[] mat[];
    private int[] answer[];
    private JButton[] buttons;
    private int N; // number of columns/rows.
    private int SRN; // square root of N
    private int K; // No. Of missing digits
    private JFrame frame;

    // Constructor
    public Sudoku(int N, int K, JFrame frame)
    {
        this.N = N;
        this.K = K;
        this.frame = frame;
        buttons = new JButton[N*N];
        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];
        answer = new int[N][N];
        fillValues();
        setLayout(new GridLayout(N,N));
        for(int i = 0; i <= N*N-1; i++)
        {
            buttons[i] = new JButton();
            if(mat[i/N][i%N] != 0){
                buttons[i].setText(""+mat[i/N][i%N]);
            }
            else{
                buttons[i].setText("");
                buttons[i].addActionListener(new buttonListener());
            }
          buttons[i].putClientProperty("column", i%N);
          buttons[i].putClientProperty("row", i/N);
          if(i/N < 3 || i/N >5){
            if(i%N<3 || i %N > 5){
              buttons[i].setBackground(Color.gray);
            }
          }
          else{
            if(!(i%N<3 || i %N > 5)){
              buttons[i].setBackground(Color.gray);
            }
          }



            add(buttons[i]); //adds this button to JPanel (note: no need for JPanel.add(...)
            //because this whole class is a JPanel already

         

        }

        System.out.println(strSudoku(mat));

    }
    

    // Sudoku Generator
    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);
        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    public void fillDiagonal()
    {

        for (int i = 0; i<N; i=i+SRN)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
    public boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    public void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                num = randomGenerator(N);
                while (!unUsedInBox(row, col, num))
                {
                    num = randomGenerator(N);
                }
                answer[row+i][col+j]=num;
                mat[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    public int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    public boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    public boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    public boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    public boolean fillRemaining(int i, int j)
    {
        // System.out.println(i+" "+j);
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j = j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                answer[i][j] = num;
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;

            // System.out.println(cellId);
            // extract coordinates i and j
            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (mat[i][j] != 0)
            {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    // Print sudoku
    public String strSudoku(int[][] arr)
    {
        String output = "";
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++){
                output+=(arr[i][j] + " ");
                if(j%3 == 2 && j>0 && j<N-1){
                    output+=("|");
                }
            }
            if(i%3 == 2 && i>0 && i<N-1){
                output+=("\n-------------------");
            }
            output+="\n";
        }
        return output;
    }

    private class buttonListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {

            JButton buttonClicked = (JButton)e.getSource(); //get the particular button that was clicked
          
            String textToShow;
        textToShow = JOptionPane.showInputDialog(
                frame,
                "Enter your guess for this square:",
                "Input Dialog",
                JOptionPane.QUESTION_MESSAGE);
        if (textToShow != null && Integer.parseInt(textToShow) != answer[(int)buttonClicked.getClientProperty("row")][(int)buttonClicked.getClientProperty("column")] )
        {
            buttonClicked.setForeground(Color.red);
            mat[(int)buttonClicked.getClientProperty("row")][(int)buttonClicked.getClientProperty("column")] = Integer.parseInt(textToShow);
            buttonClicked.setText(textToShow);
        }
          else{
            buttonClicked.setForeground(Color.blue);
            mat[(int)buttonClicked.getClientProperty("row")][(int)buttonClicked.getClientProperty("column")] = Integer.parseInt(textToShow);
            buttonClicked.setText(textToShow);
          }
          if(checkForWin()){
            int code = JOptionPane.showConfirmDialog(null, "Game Over.");
                if(code!=2){
                  System.exit(0);
                }
          }

        }

        public boolean checkForWin()
        {
          for(int[] row: mat){
            for(int cell: row){
              if(cell == 0){
                return false;
              }
            }
          }
          for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++){
                if(mat[i][j] != answer[i][j]){
                  return false;
                }
              }
          }
          return true;

        }

    }

}


