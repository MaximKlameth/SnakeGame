import java.awt.Point;
import java.util.ArrayList;

public class SnakeModel {

	private ArrayList<Point> snakePoints;

	public SnakeModel() {
	}

	public ArrayList<Point> getSnakePoints() {
		return snakePoints;
	}

	public void setSnakePoints(ArrayList<Point> snakeLocation) {
		this.snakePoints = snakeLocation;
	}
}
