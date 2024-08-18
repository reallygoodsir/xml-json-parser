package cars.app;

import cars.dao.CarDAO;
import cars.model.Car;
import cars.parser.CarParser;
import cars.parser.Parser;
import cars.utils.Resources;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws URISyntaxException, JAXBException, SQLException {
        Resources resources = new Resources();
        File xml = resources.read("cars.xml");
        Parser parser = new CarParser();
        List<Car> cars = parser.parse(xml);
        System.out.println(cars);
        CarDAO carDAO = new CarDAO();
        carDAO.save(cars);
    }
}
