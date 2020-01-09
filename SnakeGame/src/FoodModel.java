import java.awt.Point;

public class FoodModel {

	private Point food;

	public FoodModel() {
		food = new Point(0, 0);
	}

	public Point getFood() {
		return food;
	}

	public void setFood(Point food) {
		this.food = food;
	}
}
