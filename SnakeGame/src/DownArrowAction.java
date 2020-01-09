import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class DownArrowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	SnakeController snakeController;

	public DownArrowAction(SnakeController snakeController) {
		this.snakeController = snakeController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ArrowDirection.UP != snakeController.getDirection() && !snakeController.getPause()) {
			snakeController.setDirection(ArrowDirection.DOWN);
		}
	}
}
