import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGame extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;
    private int moveCount = 0;

    public TicTacToeGame() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font buttonFont = new Font("Arial", Font.BOLD, 48);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Player X's Turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;

        clicked.setText(String.valueOf(currentPlayer));
        clicked.setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);
        moveCount++;

        if (checkWinner()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            askReplay();
        } else if (moveCount == 9) {
            JOptionPane.showMessageDialog(this, "It's a Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            askReplay();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (same(buttons[i][0], buttons[i][1], buttons[i][2])) return true;
            if (same(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }
        // Diagonals
        if (same(buttons[0][0], buttons[1][1], buttons[2][2])) return true;
        if (same(buttons[0][2], buttons[1][1], buttons[2][0])) return true;

        return false;
    }

    private boolean same(JButton b1, JButton b2, JButton b3) {
        return !b1.getText().equals("") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText());
    }

    private void askReplay() {
        int choice = JOptionPane.showConfirmDialog(this, "Play again?", "Restart", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void resetBoard() {
        currentPlayer = 'X';
        moveCount = 0;
        statusLabel.setText("Player X's Turn");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGame game = new TicTacToeGame();
            game.setVisible(true);
        });
    }
}
