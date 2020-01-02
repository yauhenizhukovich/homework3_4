package com.gmail.supersonicleader.service.impl;

import com.gmail.supersonicleader.repository.model.Car;
import com.gmail.supersonicleader.repository.model.CarModelEnum;
import com.gmail.supersonicleader.service.CarService;
import com.gmail.supersonicleader.service.SecondTaskService;
import com.gmail.supersonicleader.util.RandomUtil;

import static com.gmail.supersonicleader.repository.constant.CarConstant.MAX_ENGINE_CAPACITY;
import static com.gmail.supersonicleader.repository.constant.CarConstant.MIN_ENGINE_CAPACITY;

public class SecondTaskServiceImpl implements SecondTaskService {

    @Override
    public void runSecondTask() {
        CarService carService = new CarServiceImpl();
        carService.createTable();
        int countOfCars = 10;
        addCarsToTable(carService, countOfCars);
        carService.findAllByEngineCapacity(generateEngineCapacity());
        carService.deleteAllByMinEngineCapacity();
        carService.getCountByEngineCapacity(generateEngineCapacity());
        carService.updateTitleByEngineCapacity(generateEngineCapacity());

    }

    private void addCarsToTable(CarService carService, int countOfCars) {
        for (int i = 0; i < countOfCars; i++) {
            Car car = Car.newBuilder()
                    .name("Name" + i)
                    .carModel(generateCarModel())
                    .engineCapacity(generateEngineCapacity())
                    .build();
            carService.add(car);
        }
    }

    private CarModelEnum generateCarModel() {
        int index = RandomUtil.getElement(0, CarModelEnum.values().length - 1);
        return CarModelEnum.values()[index];
    }

    private int generateEngineCapacity() {
        return RandomUtil.getElement(MIN_ENGINE_CAPACITY, MAX_ENGINE_CAPACITY);
    }

}
