package com.gmail.supersonicleader.service;

import java.util.List;

import com.gmail.supersonicleader.repository.model.Car;

public interface CarService {

    boolean createTable();

    Car add(Car person);

    List<Car> findAllByEngineCapacity(int searchEngineCapacity);

    boolean deleteAllByMinEngineCapacity();

    int getCountByEngineCapacity(int searchEngineCapacity);

    int updateTitleByEngineCapacity(int searchEngineCapacity, String updatedName);

}
