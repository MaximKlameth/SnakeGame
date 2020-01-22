import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonBarView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton leichtButton;
	private JButton schwerButton;
	private JButton spielBeendenButton;
	private final SnakeController snakeController;

	public ButtonBarView(SnakeController snakeController) {
		this.snakeController = snakeController;
		createButtons();
	}

	public void createButtons() {
		this.setPreferredSize(new Dimension(SnakeUtils.BREITE, 30));

		startButton = new JButton("Spiel Starten");
		startButton.addActionListener(snakeController.getStartGameListener());
		add(startButton);
		
		leichtButton = new JButton("Schwierigkeit verringern");
		leichtButton.addActionListener(snakeController.getSchwierigkeitVerringernListener());
		add(leichtButton);
		
		schwerButton = new JButton("Schwierigkeit erhöhen");
		schwerButton.addActionListener(snakeController.getSchwierigkeitErhoehenListener());
		add(schwerButton);
		
		
		
		
		
		
		
		setLayout(new GridLayout());
		
	}

	public void setPauseButton() {
		startButton.setText("PAUSE");
	}

	public void setStartButton() {
		startButton.setText("START!");
	}
}
