package service;

import converters.CarJsonConverter;
import exceptions.AppException;
import model.Car;
import model.enums.CarBodyType;
import model.enums.EngineType;
import model.enums.TyreType;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import service.enums.SortingOptions;
import validators.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarService {
    private final Set<Car> cars;

    public CarService(String... jsonFileNames){
        cars = getCarsFromFiles(jsonFileNames);
    }

    private Set<Car> getCarsFromFiles(String... jsonFileNames) {

        CarValidator carValidator = new CarValidator();
        AtomicInteger counter = new AtomicInteger(1);

        return Arrays.stream(jsonFileNames)
                .map(this::createCar)
                .filter(car -> {
                    Map<String, String> errors = carValidator.validate(car);

                    if(carValidator.hasErrors()){
                        System.out.println("-------------------- CAR NO. " + counter.getAndIncrement() + " ---------------");
                        errors.forEach((k, v) -> System.out.println(k + " " + v));
                    }

                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toSet());
    }

    private Car createCar(String jsonFileName){
        CarJsonConverter carConverter = new CarJsonConverter(jsonFileName);

        return carConverter
                .fromJsonFile()
                .orElseThrow(() -> new AppException("CAR SERVICE - GET CAR FROM JSON FILE"));
    }


    public Set<Car> sortCarsBy(SortingOptions sortingOptions, boolean descending){

        Set<Car> sortedCars = switch (sortingOptions){

            case ENGINE_POWER -> cars.stream().sorted(Comparator.comparing(car -> car.getEngine().getPower())).collect(Collectors.toCollection(LinkedHashSet::new));

            case SIZE_OF_THE_WHEEL -> cars.stream().sorted(Comparator.comparing(car -> car.getWheel().getSize())).collect(Collectors.toCollection(LinkedHashSet::new));

            case NUMBER_OF_COMPONENTS -> cars.stream().sorted(Comparator.comparing(car -> car.getCarBody().getComponents().size())).collect(Collectors.toCollection(LinkedHashSet::new));
        };

        if(descending){
            ArrayList<Car> tmp = new ArrayList<>(sortedCars);
            Collections.reverse(tmp);
            sortedCars = new LinkedHashSet<>(tmp);
        }

        return sortedCars;
    }

    public List<Car> getCarsOfTheTypeAndPrice(CarBodyType type, BigDecimal priceFrom, BigDecimal priceTo){
        return cars.stream()
                .filter(car -> car.getCarBody().getType() == type)
                .filter(car -> car.getPrice().compareTo(priceFrom) >= 0 && car.getPrice().compareTo(priceTo) <= 0)
                .collect(Collectors.toList());
    }

    public List<Car> getCarsWithEngineType(EngineType type){
        return cars.stream()
                .filter(car -> car.getEngine().getType() == type)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void showCarsStatistics(){
        BigDecimalSummaryStatistics priceStatistics = cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice));
        IntSummaryStatistics mileageStatistics = cars.stream().collect(Collectors.summarizingInt(Car::getMileage));
        BigDecimalSummaryStatistics enginePowerStatistisc = cars.stream().collect(Collectors2.summarizingBigDecimal(car -> car.getEngine().getPower()));

        System.out.println("PRICE");
        System.out.println("MAX PRICE: " + priceStatistics.getMax());
        System.out.println("MIN PRICE: " + priceStatistics.getMin());
        System.out.println("AVERAGE PRICE: " + priceStatistics.getAverage());

        System.out.println("\nMILEAGE");
        System.out.println("MAX MILEAGE: " + mileageStatistics.getMax());
        System.out.println("MIN MILEAGE: " + mileageStatistics.getMin());
        System.out.println("AVERAGE MILEAGE: " + mileageStatistics.getAverage());

        System.out.println("\nENGINE POWER");
        System.out.println("MAX ENGINE POWER: " + enginePowerStatistisc.getMax());
        System.out.println("MIN ENGINE POWER: " + enginePowerStatistisc.getMin());
        System.out.println("AVERAGE ENGINE POWER: " + enginePowerStatistisc.getAverage());
    }

    public Map<Car, Integer> getCarsWithMileage(){
        return cars.stream()
                .collect(Collectors.toMap(Function.identity(), Car::getMileage))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
    }

    public Map<TyreType, List<Car>> getCarsWithTyreType(){
        return cars.stream()
                .collect(Collectors.groupingBy(car -> car.getWheel().getType()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(x -> x.getValue().size(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
    }

    public List<Car> getCarsWithGivenCompnents(List<String> components){
        return  cars.stream()
                .filter(car -> car.getCarBody().getComponents().containsAll(components))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String toString() {
        return cars.stream().map(this::carStr).collect(Collectors.joining("\n"));
    }

    private String carStr(Car car){
        return "MODEL: " + car.getModel() + "\n" +
                "PRICE: " + car.getPrice() + "\n" +
                "MILEAGE: " + car.getMileage() + "\n" +
                "ENGINE TYPE: " + car.getEngine().getType() + "\n" +
                "ENGINE POWER: " + car.getEngine().getPower() + "\n" +
                "COLOR: " + car.getCarBody().getColor() + "\n" +
                "CAE BODY TYPE: " + car.getCarBody().getType() + "\n" +
                "COMPONENTS: " + car.getCarBody().getComponents().stream().collect(Collectors.joining(" ,")) + "\n" +
                "WHEEL MODEL: " + car.getWheel().getModel() + "\n" +
                "WHEEL SIZE: " + car.getWheel().getSize() + "\n" +
                "WHEEL TYPE: " + car.getWheel().getType() + "\n";

    }
}
