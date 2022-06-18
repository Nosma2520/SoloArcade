import javax.swing.*;
import java.util.*;
public class Main{
	public static void main(String[] args)
	{
    Scanner input = new Scanner(System.in);
		System.out.println("Welcome, Do you want to play Sudoku (Enter S) or Tic-Tac-Toe (Enter T): ");
    String response = input.next();
    if(response.equals("S")){
      int N = 9, K = 20;
		JFrame window = new JFrame("Sudoku");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new Sudoku(N, K, window));
		window.setBounds(200,300,300, 300);
		window.setVisible(true);
    }
    else if (response.equals("T")){
      TicTacToe.main(args);
    }
    else{
      System.out.println("Goodbye");
    }
    input.close();
	}

}