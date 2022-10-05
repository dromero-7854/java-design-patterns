package mediator.example;

import javax.swing.DefaultListModel;

import mediator.example.components.AddButton;
import mediator.example.components.DeleteButton;
import mediator.example.components.Filter;
import mediator.example.components.List;
import mediator.example.components.SaveButton;
import mediator.example.components.TextBox;
import mediator.example.components.Title;
import mediator.example.mediator.Editor;
import mediator.example.mediator.Mediator;

/**
 * Demo class. Everything comes together here.
 */
public class Demo {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Mediator mediator = new Editor();

		mediator.registerComponent(new Title());
		mediator.registerComponent(new TextBox());
		mediator.registerComponent(new AddButton());
		mediator.registerComponent(new DeleteButton());
		mediator.registerComponent(new SaveButton());
		mediator.registerComponent(new List(new DefaultListModel()));
		mediator.registerComponent(new Filter());

		mediator.createGUI();
	}

}
