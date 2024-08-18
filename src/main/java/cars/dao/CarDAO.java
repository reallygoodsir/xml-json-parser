package cars.dao;

import cars.model.Car;
import cars.model.CarInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CarDAO {
    private static final String DB_URL = "jdbc:mysql://localhost/car_db";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String QUERY_ADD_CARS = "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?)";

    public void save(List<Car> cars) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement preparedStatementCars = connection.prepareStatement(QUERY_ADD_CARS);
        connection.setAutoCommit(false);
        try {
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                CarInfo carInfo = car.getCarInfo();
                preparedStatementCars.setInt(1, car.getCarId());
                preparedStatementCars.setString(2, carInfo.getName());
                preparedStatementCars.setString(3, carInfo.getMake());
                preparedStatementCars.setString(4, carInfo.getModel());
                preparedStatementCars.setInt(5, car.getCarPrice());
                preparedStatementCars.setString(6, car.getCarType());
                preparedStatementCars.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.commit();
            connection.close();
        }
    }
}
