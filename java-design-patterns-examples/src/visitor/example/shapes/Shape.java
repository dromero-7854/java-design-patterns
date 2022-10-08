package visitor.example.shapes;

import visitor.example.visitor.Visitor;

public interface Shape {

	void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
    
}
