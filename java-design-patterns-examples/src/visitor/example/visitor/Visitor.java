package visitor.example.visitor;

import visitor.example.shapes.Circle;
import visitor.example.shapes.CompoundShape;
import visitor.example.shapes.Dot;
import visitor.example.shapes.Rectangle;

public interface Visitor {

	String visitDot(Dot dot);

    String visitCircle(Circle circle);

    String visitRectangle(Rectangle rectangle);

    String visitCompoundGraphic(CompoundShape cg);
    
}
