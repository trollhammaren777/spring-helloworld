package web.dao;

import web.model.Car;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarDaoImpl implements CarDao {
    List<Car> cars = new ArrayList<>();

    public CarDaoImpl() {
        //потому что нет hibernate
        Car car1 = new Car("model1", 1);
        car1.setId(1);
        Car car2 = new Car("model2", 2);
        car2.setId(2);
        Car car3 = new Car("model3", 3);
        car3.setId(3);
        Car car4 = new Car("model4", 4);
        car4.setId(4);
        Car car5 = new Car("model5", 5);
        car5.setId(5);
        Car car6 = new Car("model6", 6);
        car6.setId(6);
        Car car7 = new Car("model7", 7);
        car7.setId(7);
        Car car8 = new Car("model8", 8);
        car8.setId(8);
        Car car9 = new Car("model9", 9);
        car9.setId(9);
        Car car10 = new Car("model10", 10);
        car10.setId(10);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);
        cars.add(car7);
        cars.add(car8);
        cars.add(car9);
        cars.add(car10);
    }

    @Override
    public void add(Car car) {
        cars.add(car);
    }

    @Override
    public List<Car> getCarList() {
        return cars;
    }

    @Override
    public List<Car> getPassedNumberOfCars(int number) {
        if (number <= 0 || number >= 5) {
            return cars;
        }
        return new ArrayList<>(cars.subList(0, number));
    }

    @Override
    public Map<String, ArrayList<Object>> getMapOfCars(List<Car> cars, Integer... count) {
        int counter;
        Map<String, ArrayList<Object>> instanceMap = new HashMap<>();
        if (count.length == 0) {
            counter = cars.size();
        } else {
            counter = count[0];
        }
        instanceMap.put("ID", new ArrayList<>());
        instanceMap.put("Model", new ArrayList<>());
        instanceMap.put("Series", new ArrayList<>());
        for (int i = 0; i < counter; i++) {
            instanceMap.get("ID").add(cars.get(i).getId());
            instanceMap.get("Model").add(cars.get(i).getModel());
            instanceMap.get("Series").add(cars.get(i).getSeries());
        }
        return instanceMap;
    }

    @Override
    public List<Map<String, ArrayList<Object>>> getListOfMapOfCars(Map<String, ArrayList<Object>> instanceMap) {
        List<Map<String, ArrayList<Object>>> carsListRows = new ArrayList<>();
        carsListRows.add(instanceMap);
        return carsListRows;
    }
}
