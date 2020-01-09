import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class UpArrowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	SnakeController snakeController;
	
	 public UpArrowAction(SnakeController snakeController) {
		this.snakeController=snakeController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ArrowDirection.DOWN != snakeController.getDirection() && !snakeController.getPause()) {
			snakeController.setDirection(ArrowDirection.UP);
		}
	}
}
