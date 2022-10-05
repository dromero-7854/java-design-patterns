package mediator.example.components;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import mediator.example.mediator.Mediator;
import mediator.example.mediator.Note;

/**
 * Concrete components don't talk with each other. They have only one
 * communication channel–sending requests to the mediator.
 */
@SuppressWarnings("serial")
public class Filter extends JTextField implements Component {

	private Mediator mediator;

	@SuppressWarnings("rawtypes")
	private ListModel listModel;

	public Filter() {}

	@Override
	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected void processComponentKeyEvent(KeyEvent keyEvent) {
		String start = getText();
		searchElements(start);
	}

	@SuppressWarnings("rawtypes")
	public void setList(ListModel listModel) {
		this.listModel = listModel;
	}

	private void searchElements(String s) {
		if (listModel == null) {
			return;
		}

		if (s.equals("")) {
			mediator.setElementsList(listModel);
			return;
		}

		ArrayList<Note> notes = new ArrayList<>();
		for (int i = 0; i < listModel.getSize(); i++) {
			notes.add((Note) listModel.getElementAt(i));
		}
		DefaultListModel<Note> listModel = new DefaultListModel<>();
		for (Note note : notes) {
			if (note.getName().contains(s)) {
				listModel.addElement(note);
			}
		}
		mediator.setElementsList(listModel);
	}

	@Override
	public String getName() {
		return "Filter";
	}

}
