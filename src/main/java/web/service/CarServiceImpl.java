package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.CarDao;
import web.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("carService")
public class CarServiceImpl implements CarService{
    @Autowired
    CarDao carDao;

    @Override
    public void add(Car car) {
        carDao.add(car);
    }

    @Override
    public List<Car> getCarList() {
        return carDao.getCarList();
    }

    @Override
    public List<Car> getPassedNumberOfCars(int number) {
        return carDao.getPassedNumberOfCars(number);
    }

    @Override
    public Map<String, ArrayList<Object>> getMapOfCars(List<Car> list, Integer... count) {
        return carDao.getMapOfCars(list, count);
    }

    @Override
    public List<Map<String, ArrayList<Object>>> getListOfMapOfCars(Map<String, ArrayList<Object>> instanceMap) {
        return carDao.getListOfMapOfCars(instanceMap);
    }
}
