import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RightArrowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	SnakeController snakeController;
	
	 public RightArrowAction(SnakeController snakeController) {
		this.snakeController=snakeController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ArrowDirection.LEFT != snakeController.getDirection() && !snakeController.getPause()) {
			snakeController.setDirection(ArrowDirection.RIGHT);
		}
	}
}
