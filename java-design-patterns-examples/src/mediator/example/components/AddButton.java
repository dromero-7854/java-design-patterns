package mediator.example.components;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import mediator.example.mediator.Mediator;
import mediator.example.mediator.Note;

/**
 * Concrete components don't talk with each other. They have only one
 * communication channel–sending requests to the mediator.
 */
@SuppressWarnings("serial")
public class AddButton extends JButton implements Component {

	private Mediator mediator;

	public AddButton() {
		super("Add");
	}

	@Override
	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected void fireActionPerformed(ActionEvent actionEvent) {
		mediator.addNewNote(new Note());
	}

	@Override
	public String getName() {
		return "AddButton";
	}

}
