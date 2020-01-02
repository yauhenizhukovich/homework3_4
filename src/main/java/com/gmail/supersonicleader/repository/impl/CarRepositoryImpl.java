package com.gmail.supersonicleader.repository.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.supersonicleader.repository.CarRepository;
import com.gmail.supersonicleader.repository.model.Car;
import com.gmail.supersonicleader.repository.model.CarModelEnum;
import com.gmail.supersonicleader.util.SQLPropertyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.supersonicleader.repository.constant.SQLConstant.SQL_CREATE_TABLE_CAR;

public class CarRepositoryImpl implements CarRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public boolean createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String command = SQLPropertyUtil.getProperty(SQL_CREATE_TABLE_CAR);
            return statement.execute(command);
        }
    }

    @Override
    public Car add(Connection connection, Car car) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO car(name, car_model, engine_capacity) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setString(2, car.getCarModel().toString());
            preparedStatement.setInt(3, car.getEngineCapacity());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            }
            return car;
        }

    }

    @Override
    public List<Car> findAllByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM car WHERE engine_capacity=?"
                )
        ) {
            preparedStatement.setInt(1, searchEngineCapacity);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Car> carList = new ArrayList<>();
                while (resultSet.next()) {
                    Car car = getCar(resultSet);
                    logger.info(car);
                    carList.add(car);
                }
                return carList;
            }
        }

    }

    @Override
    public boolean deleteAllByMinEngineCapacity(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            return statement.execute(
                    "DELETE FROM car WHERE engine_capacity = (SELECT * FROM (SELECT MIN(engine_capacity) FROM car) AS min)"
            );

        }
    }

    @Override
    public int getCountByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM car WHERE engine_capacity=?"
        )) {
            preparedStatement.setInt(1, searchEngineCapacity);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
                logger.info("Count of cars with engine capacity = " + searchEngineCapacity + " : " + count);
                return count;
            }

        }
    }

    @Override
    public int updateTitleByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE car SET name = 'Good Capacity' WHERE engine_capacity = ?"
                )
        ) {
            preparedStatement.setInt(1, searchEngineCapacity);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating car failed, no rows affected.");
            }

            return affectedRows;
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        CarModelEnum carModel = getCarModel(resultSet.getString("car_model"));
        Integer engineCapacity = resultSet.getInt("engine_capacity");
        return Car.newBuilder()
                .id(id)
                .name(name)
                .carModel(carModel)
                .engineCapacity(engineCapacity)
                .build();
    }

    private CarModelEnum getCarModel(String carModel) {
        switch (carModel) {
            case "HONDA":
                return CarModelEnum.HONDA;
            case "MAZDA":
                return CarModelEnum.MAZDA;
            case "KIA":
                return CarModelEnum.KIA;
            default:
                throw new IllegalArgumentException("Wrong car model!");
        }
    }

}
