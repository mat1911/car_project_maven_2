package menu;

import exceptions.AppException;
import model.enums.CarBodyType;
import model.enums.EngineType;
import service.CarService;
import service.UserDataService;
import service.enums.SortingOptions;

import java.math.BigDecimal;
import java.util.List;

public class MenuService {

    private final CarService carService;
    private final UserDataService userDataService;

    public MenuService(String... jsonFileNames) {
        this.carService = new CarService(jsonFileNames);
        this.userDataService = new UserDataService();
    }

    public void mainMenu(){
        while (true) {
            try {

                showMenu();
                int option = userDataService.getInt("Enter option number:");

                switch (option) {
                    case 1 -> option1();

                    case 2 -> option2();

                    case 3 -> option3();

                    case 4 -> option4();

                    case 5 -> option5();

                    case 6 -> option6();

                    case 7 -> option7();

                    case 8 -> option8();

                    case 9 -> {
                        System.out.println("Have a nice day!");
                        return;
                    }
                }

            } catch (AppException e) {
                System.out.println("\n--------------------- EXCEPTION ----------------------");
                System.out.println(e.getMessage());
            }
        }
    }

    private void showMenu() {

        System.out.println("=================MENU=================");
        System.out.println("1 - all cars");
        System.out.println("2 - sort cars");
        System.out.println("3 - cars with car body type and price between");
        System.out.println("4 - show cars with the specified engine type");
        System.out.println("5 - cars statistics");
        System.out.println("6 - show cars with sorted mileage");
        System.out.println("7 - cars with the type of tyre");
        System.out.println("8 - cars with given components");
        System.out.println("9 - end of app");
    }

    private void option1(){
        System.out.println(carService);
    }

    private void option2(){
        SortingOptions sortingOptions = userDataService.getSortingOptions();
        boolean descending = userDataService.getBoolean("Descending ");

        carService.sortCarsBy(sortingOptions, descending).forEach(System.out::println);
    }

    private void option3(){
        CarBodyType carBodyType = userDataService.getCarBodyType();
        BigDecimal priceFrom = userDataService.getBigDecimal("Price from: ");
        BigDecimal priceTo = userDataService.getBigDecimal("Price to: ");

        carService.getCarsOfTheTypeAndPrice(carBodyType, priceFrom, priceTo).forEach(System.out::println);
    }

    private void option4(){
        EngineType engineType = userDataService.getEngineType();

        carService.getCarsWithEngineType(engineType).forEach(System.out::println);
    }

    private void option5(){
        carService.showCarsStatistics();
    }

    private void option6(){
        carService.getCarsWithMileage().forEach((car, mileage) -> System.out.println(car + " " + mileage));
    }

    private void option7(){
        carService.getCarsWithTyreType().forEach((tyre, cars) -> System.out.println(tyre + " " + cars));
    }

    private void option8(){
        int numberOfComponents = userDataService.getInt("Enter number of components: ");
        List<String> components = userDataService.getComponenest(numberOfComponents);

        carService.getCarsWithGivenCompnents(components).forEach(System.out::println);
    }
}
