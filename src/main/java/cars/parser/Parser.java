package cars.parser;

import cars.model.Car;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public interface Parser {
    List<Car> parse(File file) throws JAXBException;

}
