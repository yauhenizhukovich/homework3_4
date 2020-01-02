package com.gmail.supersonicleader.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.supersonicleader.repository.model.Car;

public interface CarRepository {

    boolean createTable(Connection connection) throws SQLException;

    Car add(Connection connection, Car car) throws SQLException;

    List<Car> findAllByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException;

    boolean deleteAllByMinEngineCapacity(Connection connection) throws SQLException;

    int getCountByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException;

    int updateTitleByEngineCapacity(Connection connection, int searchEngineCapacity) throws SQLException;

}
