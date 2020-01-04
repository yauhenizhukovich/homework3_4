package com.gmail.supersonicleader.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.gmail.supersonicleader.repository.CarRepository;
import com.gmail.supersonicleader.repository.ConnectionRepository;
import com.gmail.supersonicleader.repository.impl.CarRepositoryImpl;
import com.gmail.supersonicleader.repository.impl.ConnectionRepositoryImpl;
import com.gmail.supersonicleader.repository.model.Car;
import com.gmail.supersonicleader.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarServiceImpl implements CarService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository = new ConnectionRepositoryImpl();
    private CarRepository carRepository = new CarRepositoryImpl();

    @Override
    public boolean createTable() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boolean isTableCreated = carRepository.createTable(connection);
                connection.commit();
                return isTableCreated;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Car add(Car car) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                car = carRepository.add(connection, car);
                connection.commit();
                return car;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Car> findAllByEngineCapacity(int searchEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Car> carList = carRepository.findAllByEngineCapacity(connection, searchEngineCapacity);
                connection.commit();
                return carList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteAllByMinEngineCapacity() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                boolean isDeleted = carRepository.deleteAllByMinEngineCapacity(connection);
                connection.commit();
                return isDeleted;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public int getCountByEngineCapacity(int searchEngineCapacity) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int countByEngineCapacity = carRepository.getCountByEngineCapacity(connection, searchEngineCapacity);
                connection.commit();
                return countByEngineCapacity;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int updateTitleByEngineCapacity(int searchEngineCapacity, String updatedName) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = carRepository.updateTitleByEngineCapacity(connection, searchEngineCapacity, updatedName);
                carRepository.findAllByEngineCapacity(connection, searchEngineCapacity);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

}