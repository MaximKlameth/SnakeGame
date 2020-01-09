import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class SpaceAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	SnakeController snakeController;
	
	 public SpaceAction(SnakeController snakeController) {
		this.snakeController=snakeController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		snakeController.tryStartGame();
	}
}
