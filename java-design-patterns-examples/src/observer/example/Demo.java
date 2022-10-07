package observer.example;

import observer.example.editor.Editor;
import observer.example.listeners.EmailNotificationListener;
import observer.example.listeners.LogOpenListener;

public class Demo {

	public static void main(String[] args) {
		Editor editor = new Editor();
		editor.events.subscribe("open", new LogOpenListener("/home/dante.romero/Documentos/taller/java-design-patterns/file.txt"));
		editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));
		try {
			editor.openFile("test.txt");
			editor.saveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
