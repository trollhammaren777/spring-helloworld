package web.controller;

import web.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

@Controller
public class CarsController {

    @Autowired
    CarService carService;

    @GetMapping("/cars")
    public String printCars(ModelMap model, HttpServletRequest request) {
        Map<String, ArrayList<Object>> mapInstance;
        String number = request.getParameter("count");
        if (number == null) {
            mapInstance = carService.getMapOfCars(carService.getCarList());
        } else {
            mapInstance = carService.getMapOfCars(carService.getPassedNumberOfCars(Integer.parseInt(number)));
        }
        List<String> headers = Arrays.asList("ID", "Model", "Series");
        model.addAttribute("headers", headers);
        model.addAttribute("cars_list", mapInstance);
        return "cars";
    }
}
