import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SnakeView extends JPanel {

	private static final long serialVersionUID = 1L;
	private String highScore = "";
	private JFrame frame;
	private final SnakeController snakeController;

	public SnakeView(SnakeController snakeController) {
		this.snakeController = snakeController;
	}

	// Die View wird hier sichtbar
	public void createView() {

		frame = new JFrame("Snake Game®");
		frame.getContentPane().add(BorderLayout.CENTER, this);
		// legen hier fest wie groß das Fenster sein soll
		frame.setPreferredSize(new Dimension(SnakeUtils.BREITE + 2 * SnakeUtils.offsetWidth,
				SnakeUtils.HOEHE + 2 * SnakeUtils.offsetHeight + 50));

		// Das Fenster machen wir hier sichtbar
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		// die ganzen Listener werden hier geaddet und damit aktiviert
		addActionListener();
	}

	public void paint(Graphics graphic) {

		// Farben für Hintergrund

		graphic.setColor(Color.BLACK);
		graphic.fillRect(SnakeUtils.offsetWidth, SnakeUtils.offsetHeight, SnakeUtils.BREITE, SnakeUtils.HOEHE);

// Die Punkte SnakeLocation sollen gleich dem geholten SnakePoints vom Controller sein		
		ArrayList<Point> snakeLocations = snakeController.getSnakePoints();
		for (int i = 0; i < snakeLocations.size(); i++) {
			//FARBE DER SCHLANGE
			
			
			
			graphic.fillRect(snakeLocations.get(i).x, snakeLocations.get(i).y, SnakeUtils.RAHMEN, SnakeUtils.RAHMEN);
		}

		// FARBE VOM PUNKT
		graphic.setColor(Color.RED);
		graphic.fillRect(snakeController.getFoodLocation().x, snakeController.getFoodLocation().y, SnakeUtils.RAHMEN,
				SnakeUtils.RAHMEN);

		// SCHRIFT FÜR SCORE UND SCHWIERIGKEIT OBEN
		graphic.setColor(Color.RED);
		Font font = new Font("Verdana", Font.BOLD, 12);

		graphic.setFont(font);
		FontMetrics fm = graphic.getFontMetrics();

		// Score Anzeige und der Platz
		String score = "Score: " + snakeController.getScore();
		graphic.drawString(score, 15, 15);

		// Schwierigkeit Anzeige und Platz
		String schwierigkeit = "Schwierigkeit: " + snakeController.getSchwierigkeit();
		graphic.drawString(schwierigkeit, SnakeUtils.BREITE - 100, 15);

		if (highScore.equals("")) {
			highScore = snakeController.getHighscore();
		}
		graphic.drawString("Highscore: " + highScore, 300, 15);

		// wenn es gameover ist dann soll er den Startbildschirm hinzufügen
		if (snakeController.getIsGameOver()) {

			font = new Font("Times New Roman", Font.BOLD, 14);
			graphic.setFont(font);
			checkNewHighscore();

			// das Bild wird hier erstellt und bei Fehler soll, der Fehler abgefangen werden
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("src/snake_titelbild.jpg"));
			} catch (IOException exc) {
				System.out.println(exc.getStackTrace());
			}

			// wird bestimmt wie das Bild und wo erscheinen soll
			graphic.drawImage(img, SnakeUtils.offsetWidth - SnakeUtils.RAHMEN,
					SnakeUtils.offsetHeight - SnakeUtils.RAHMEN, SnakeUtils.BREITE + 2 * SnakeUtils.RAHMEN,
					SnakeUtils.HOEHE + 2 * SnakeUtils.RAHMEN, this);
		}

		// wenn nicht gameover ist und Pause ist, soll Pause ausgegeben werden
		if (!snakeController.getIsGameOver() && snakeController.getPause()) {
			String gameOver1 = "Pause";
			String schwierigkeit2 = "Dein aktueller Score " + snakeController.getScore();
			System.out.println("Pause");

			// ?
			fm = graphic.getFontMetrics();

			// Für die Anzeige wenn pause gedrückt wird, wo der Text "Pause" landen soll
			graphic.setColor(Color.GREEN);
			graphic.drawString(schwierigkeit2,
					(SnakeUtils.offsetWidth * 2 + SnakeUtils.BREITE - 100) / 2 - fm.stringWidth(gameOver1) / 2,
					(SnakeUtils.offsetHeight + SnakeUtils.HOEHE) / 2);
			graphic.drawString(gameOver1,
					(SnakeUtils.offsetWidth * 2 + SnakeUtils.BREITE - 100) / 2 - fm.stringWidth(gameOver1) / 2,
					(SnakeUtils.offsetHeight + SnakeUtils.HOEHE - 20) / 2);
		}
	}

	public void checkNewHighscore() {
		if (highScore.equals("")) {
			return;
		}

		if (snakeController.getScore() > Integer.parseInt(highScore.split(":")[1])) {
			String name = JOptionPane
					.showInputDialog("WOW, du hast einen neuen Highscore erreicht. Wie ist dein Name?");
			highScore = name + ":" + snakeController.getScore();

			File scoreFile = new File("highscore.dat");
			if (!scoreFile.exists())
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}

			FileWriter writeFile = null;
			BufferedWriter writer = null;

			try {
				writeFile = new FileWriter(scoreFile, true);
				writer = new BufferedWriter(writeFile);
				writer.write("\n");
				writer.write(highScore);
				Thread.sleep(100);
				repaint();
			} catch (Exception e) {
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (Exception e) {
				}
			}

		}

	}

	// Tasten werden hier aktiviert
	private void addActionListener() {
		InputMap inputMap = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");

		ActionMap actionMap = getActionMap();
		actionMap.put("RightArrow", new RightArrowAction(snakeController));
		actionMap.put("LeftArrow", new LeftArrowAction(snakeController));
		actionMap.put("UpArrow", new UpArrowAction(snakeController));
		actionMap.put("DownArrow", new DownArrowAction(snakeController));
		actionMap.put("Space", new SpaceAction(snakeController));
		actionMap.put("Enter", new EnterAction(snakeController));

	}

	// wo die ButtonBar platziert werden soll
	public void addButtonBar(ButtonBarView buttonBar) {
		add(buttonBar);
		frame.getContentPane().add(BorderLayout.PAGE_END, buttonBar);
	}
}
