package web.dao;

import web.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CarDao {
    void add(Car car);
    List<Car> getCarList();
    List<Car> getPassedNumberOfCars(int number);
    Map<String, ArrayList<Object>> getMapOfCars(List<Car> cars, Integer... count);
    List<Map<String, ArrayList<Object>>> getListOfMapOfCars(Map<String, ArrayList<Object>> instanceMap);
}
