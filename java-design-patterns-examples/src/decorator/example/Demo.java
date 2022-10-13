package decorator.example;

import decorator.example.decorators.CompressionDecorator;
import decorator.example.decorators.DataSource;
import decorator.example.decorators.DataSourceDecorator;
import decorator.example.decorators.EncryptionDecorator;
import decorator.example.decorators.FileDataSource;

public class Demo {
	
	public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSourceDecorator encoded = new CompressionDecorator(
                                         new EncryptionDecorator(
                                             new FileDataSource("/home/dante.romero/Documentos/taller/software-engineering/out/OutputDemo.txt")));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource("/home/dante.romero/Documentos/taller/software-engineering/out/OutputDemo.txt");

        System.out.println("- Input ----------------");
        System.out.println(salaryRecords);
        System.out.println("- Encoded --------------");
        System.out.println(plain.readData());
        System.out.println("- Decoded --------------");
        System.out.println(encoded.readData());
    }

}
