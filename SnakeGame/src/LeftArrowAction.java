import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class LeftArrowAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	SnakeController snakeController;
	
	 public LeftArrowAction(SnakeController snakeController) {
		this.snakeController=snakeController;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (ArrowDirection.RIGHT != snakeController.getDirection() && !snakeController.getPause()) {
			snakeController.setDirection(ArrowDirection.LEFT);
		}
	}
}
