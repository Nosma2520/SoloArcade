import javax.swing.*;
public class Main{
  // Driver code
	public static void main(String[] args)
	{
		int N = 9, K = 20;
		JFrame window = new JFrame("Sudoku");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new Sudoku(N, K, window));
		window.setBounds(900,600,900,900);
		window.setVisible(true);
	}

}