import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class SnakeController {
// die ganzen Attributen werden deklariert 
	private final SnakeModel snakeModel;
	private final FoodModel foodModel;
	private SnakeView snakeView;
	private ButtonBarView buttonBarView;
	private ArrowDirection direction = ArrowDirection.RIGHT;
	private Timer timer;
	private Integer score = 0;
	private int speed = Schwierigkeit.LEICHT;
	private Boolean isGameOver = true;
	private Boolean isPause = false;
	private ActionListener performAction;

// konstruktor der klasse 

// objekte werden erstellt: 
	public SnakeController() {
		snakeModel = new SnakeModel();
		foodModel = new FoodModel();
		snakeView = new SnakeView(this);
		buttonBarView = new ButtonBarView(this);
		initStart(); // Methode wird aufgerufen
		snakeView.addButtonBar(buttonBarView);
		// buttonBarview wird der SnakeView hinzugefügt
	}

// Methode zum initilisieren des Starts 
	private void initStart() {
		snakeModel.setSnakePoints(new ArrayList<>());// koordinaten der Schlange
		snakeModel.getSnakePoints().add(new Point(0, 0)); // default wert für die schlange wird erstellt
		snakeView.createView(); // Die view wird erstellt
	}
// Startet das spiel

	private void startGame() {
		performAction = new ActionListener() { // ActionListener wird erstellt sobald das spiel gestartet werden soll
												
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isPause && !isGameOver) { //
					move(); // macht ein Schritt in dem sie die koordinaten der Schlange anpasst
					checkPosition(); 
					snakeView.repaint(); // Die View wird neu gezeichnet, damit sich die Schlange bewegt (mit den neuen
											// Koordianten)

				} else if (isGameOver) { // Wenn das spiel zuende, dann wird der Timer gestoppt, der Timer führt die
											// perfomAction immer wieder aus
					timer.stop(); //
					buttonBarView.setStartButton(); // In der ButtonBar soll jetzt Start statt Pause stehen
				}
			}
		};
		timer = new Timer(speed, performAction); 

		score = 0;// beim start ist der Score natürlich 0
		direction = ArrowDirection.RIGHT; // Die Default richtung also die Richtung die die Schlange am anfang hat ist
											// am anfang immer rechts
		snakeModel.getSnakePoints().clear(); // Wir löschen die SnakePositions (Snake Länge) damit wir bei 0 anfangen

		for (int i = 0; i < 6; i++) {
			snakeModel.getSnakePoints()
					.add(new Point(Math.round((SnakeUtils.BREITE + SnakeUtils.offsetWidth) / (2 * 10)) * 10 - i * 10,
							Math.round((SnakeUtils.HOEHE + SnakeUtils.offsetHeight) / (2 * 10)) * 10));
		}
		// Da eine Schlange immer schon eine bestimmte größe hat, haben wir uns für eine
		// größe von 6 entschieden.


		newFood();// Erstellt einen neuen Food
		buttonBarView.setPauseButton(); // Start wird auf Pause geändert
		isGameOver = false; // gameOver ist natürlich falsch wenn das spiel gestartet wird
		isPause = false; 
		timer.start(); 
	}

	public Point getFoodLocation() {
		return foodModel.getFood();
	}

	public Boolean getIsGameOver() {
		return isGameOver;
	}

	public void setDirection(ArrowDirection direction) {
		this.direction = direction;
	}

	public ArrowDirection getDirection() {
		return direction;
	}


	public void tryStartGame() {

		if (isGameOver) {
			startGame();
		} else {
			if (isPause) {
				isPause = false;
				snakeView.repaint();
			} else {
				isPause = true;
				snakeView.repaint();
			}
		}
	}

	public Boolean getPause() {
		return isPause;
	}

	
	public void move() {
		if (ArrowDirection.RIGHT == direction) {
			snakeModel.getSnakePoints().add(0,
					new Point(snakeModel.getSnakePoints().get(0).x + 10, snakeModel.getSnakePoints().get(0).y + 0));
		} else if (ArrowDirection.LEFT == direction) {
			snakeModel.getSnakePoints().add(0,
					new Point(snakeModel.getSnakePoints().get(0).x - 10, snakeModel.getSnakePoints().get(0).y + 0));
		} else if (ArrowDirection.UP == direction) {
			snakeModel.getSnakePoints().add(0,
					new Point(snakeModel.getSnakePoints().get(0).x, snakeModel.getSnakePoints().get(0).y - 10));
		} else if (ArrowDirection.DOWN == direction) {
			snakeModel.getSnakePoints().add(0,
					new Point(snakeModel.getSnakePoints().get(0).x, snakeModel.getSnakePoints().get(0).y + 10));
		}
	}

//Ein neuer Food wird erstellt. Wir erstellen ihn zufällig.
	public void newFood() {

		Random random = new Random();
		Point point;
	
		point = new Point(
				random.nextInt(SnakeUtils.BREITE / SnakeUtils.RAHMEN) * SnakeUtils.RAHMEN + SnakeUtils.offsetWidth,
				random.nextInt(SnakeUtils.HOEHE / SnakeUtils.RAHMEN) * SnakeUtils.RAHMEN + SnakeUtils.offsetHeight);

	
		while (Arrays.asList(snakeModel.getSnakePoints()).contains(point)) {
			point = new Point(
					random.nextInt(SnakeUtils.BREITE / SnakeUtils.RAHMEN) * SnakeUtils.RAHMEN + SnakeUtils.offsetWidth,
					random.nextInt(SnakeUtils.HOEHE / SnakeUtils.RAHMEN) * SnakeUtils.RAHMEN + SnakeUtils.offsetHeight);
		}
		foodModel.setFood(point);
	}

// 
	public void setScore() {
		switch (speed) {
		case Schwierigkeit.LEICHT:
			score += 5;
			break;
		case Schwierigkeit.MITTEL:
			score += 6;
			break;
		case Schwierigkeit.SCHWER:
			score += 7;
			break;
		}
	}

//
	public int getScore() {
		return score;
	}

// Alaa: Hier wird die Schwierigkeit erhöht. Switch Case.
	// Eigene Klasse damit übersichtlicher, wenn leicht dann verzögerung von 100ms
	// ....
	public void schwierigkeitErhoehen() {
		switch (speed) {
		case Schwierigkeit.LEICHT:
			speed = Schwierigkeit.MITTEL;
			break;
		case Schwierigkeit.MITTEL:
			speed = Schwierigkeit.SCHWER;
			break;
		case Schwierigkeit.SCHWER:
			break;
		}
		snakeView.repaint();
	}

	public void schwierigkeitVerringern() {
		switch (speed) {
		case Schwierigkeit.LEICHT:
			// nicht da es nicht leichter geht
			break;
		case Schwierigkeit.MITTEL:
			speed = Schwierigkeit.LEICHT;
			break;
		case Schwierigkeit.SCHWER:
			speed = Schwierigkeit.MITTEL;
			break;
		}
		snakeView.repaint();
	}

	// Schwierigkeit als Text
	public String getSchwierigkeit() {
		switch (speed) {
		case Schwierigkeit.LEICHT:
			return "LEICHT";
		case Schwierigkeit.MITTEL:
			return "MITTEL";
		case Schwierigkeit.SCHWER:
			return "SCHWER";
		}
		return "";
	}

//  Das prüft die Pos und checkt ob das spiel beendet ist.
	public void checkPosition() {
		// Das Spiel kann auf 2 weisen beendet werden. Einmal Schlange beißt sich selbst
		// oder geht in den Rand
		// Wir gehen jedes einzelne Feld durch und gucken ob sich die Schlange kreuzt
		for (int j = 1; j < snakeModel.getSnakePoints().size() - 1; j++) {
			if (snakeModel.getSnakePoints().get(0).equals(snakeModel.getSnakePoints().get(j))) {
				isGameOver = true;
			}
		}
		// Schlange knallt gegen den Rand
		if (snakeModel.getSnakePoints().get(0).x == SnakeUtils.offsetWidth - SnakeUtils.RAHMEN
				|| snakeModel.getSnakePoints().get(0).x == SnakeUtils.BREITE + SnakeUtils.offsetWidth
				|| snakeModel.getSnakePoints().get(0).y == SnakeUtils.offsetHeight - SnakeUtils.RAHMEN
				|| snakeModel.getSnakePoints().get(0).y == SnakeUtils.HOEHE + SnakeUtils.offsetHeight) {
			isGameOver = true;
		}
//Schlange isst einen Food ()natürlich Schlangen kopf daher .get(0)
		// Wenn der Kopf selbe Koordinate hat dann hat er natürlich gegegsen
		if (snakeModel.getSnakePoints().get(0).equals(foodModel.getFood())) {
			// neuer Food erstellt
			newFood();
			// Score je nach level erhöht
			setScore();
		} else {
			// Der letzte Teil der Schlange wird gelöscht, damit die Scdhlange sich bewegt
			snakeModel.getSnakePoints().remove(snakeModel.getSnakePoints().size() - 1);
		}
	}

	public ActionListener getSchwierigkeitErhoehenListener() {
		ActionListener speedUpListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				schwierigkeitErhoehen();
			}
		};
		return speedUpListener;
	}

	public ActionListener getStartGameListener() {
		ActionListener startGameListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryStartGame();
			}
		};
		return startGameListener;
	}

	public ActionListener getSchwierigkeitVerringernListener() {
		ActionListener speedDownListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				schwierigkeitVerringern();
			}
		};
		return speedDownListener;
	}

	public ActionListener getExitGameListener() {
		ActionListener exitGameListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		return exitGameListener;
	}

	// Versucht mit hilfe eines Bufferedreaders die Highscore.dat auszulesen und den
	// Highsore abzuholen
	public String getHighscore() {
		BufferedReader reader = null;
		try {
			FileReader readFile1 = new FileReader("highscore.dat"); //
			BufferedReader reader1 = new BufferedReader(readFile1);
			String highScore = reader1.readLine(); //
			reader1.close();
			// vergleich zwischen den werten 
			if (highScore == null) {
				return "0";
			}

			return highScore;
		} catch (Exception e) {
			return "0";
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Point> getSnakePoints() {
		return snakeModel.getSnakePoints();
	}
}