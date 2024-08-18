package cars.parser;

import cars.model.Car;
import cars.model.Cars;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class CarParser implements Parser {
    @Override
    public List<Car> parse(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Cars.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Cars cars = (Cars) unmarshaller.unmarshal(file);
        return cars.getCars();
    }
}
