package service;

import exceptions.AppException;
import model.enums.CarBodyType;
import model.enums.EngineType;
import model.enums.TyreType;
import service.enums.SortingOptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDataService {

    private Scanner scanner = new Scanner(System.in);

    public int getInt(String message) {

        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new AppException("INT VALUE IS NOT CORRECT: " + text);
        }

        return Integer.parseInt(text);

    }

    public double getDouble(String message) {

        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("[0-9]+.*[0-9]+]")) {
            throw new AppException("DOUBLE VALUE IS NOT CORRECT: " + text);
        }

        return Double.parseDouble(text);

    }

    public SortingOptions getSortingOptions() {
        SortingOptions[] sortingOptions = SortingOptions.values();

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(sortingOptions)
                .forEach(carOption -> System.out.println(counter.getAndIncrement() + ". " + carOption));
        System.out.println("Enter sorting option number:");
        String text = scanner.nextLine();

        if (!text.matches("[1-" + sortingOptions.length + "]")) {
            throw new AppException("Sorting option value is not correct: " + text);
        }

        return sortingOptions[Integer.parseInt(text) - 1];
    }

    public CarBodyType getCarBodyType() {
        CarBodyType[] carBodyTypes = CarBodyType.values();

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(carBodyTypes)
                .forEach(bodyType -> System.out.println(counter.getAndIncrement() + ". " + bodyType));
        System.out.println("Enter body type number:");
        String text = scanner.nextLine();

        if (!text.matches("[1-" + carBodyTypes.length + "]")) {
            throw new AppException("Body type value is not correct: " + text);
        }

        return carBodyTypes[Integer.parseInt(text) - 1];
    }

    public EngineType getEngineType() {
        EngineType[] engineType = EngineType.values();

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(engineType)
                .forEach(bodyType -> System.out.println(counter.getAndIncrement() + ". " + bodyType));
        System.out.println("Enter engine type number:");
        String text = scanner.nextLine();

        if (!text.matches("[1-" + engineType.length + "]")) {
            throw new AppException("Engine type value is not correct: " + text);
        }

        return engineType[Integer.parseInt(text) - 1];
    }

    public TyreType getTyreType() {
        TyreType[] tyreType = TyreType.values();

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(tyreType)
                .forEach(bodyType -> System.out.println(counter.getAndIncrement() + ". " + bodyType));
        System.out.println("Enter tyre type number:");
        String text = scanner.nextLine();

        if (!text.matches("[1-" + tyreType.length + "]")) {
            throw new AppException("Tyre type value is not correct: " + text);
        }

        return tyreType[Integer.parseInt(text) - 1];
    }

    public List<String> getComponenest(int numberOfComponents){
        List<String> components = new ArrayList<>();

        for (int i = 0; i < numberOfComponents; i++) {
            components.add(getStringOfLetters("Enter component: ", true));
        }
        return components;
    }

    public boolean getBoolean(String message) {
        System.out.println(message + "[y / n]?");
        return Character.toLowerCase(scanner.nextLine().charAt(0)) == 'y';
    }

    public BigDecimal getBigDecimal(String message){
        System.out.println(message);
        String text = scanner.nextLine();

        if(!text.matches("([0-9]+)|([0-9]+.[0-9]+)")){
            throw new AppException("BIGDECIMAL VALUE IS NOT CORRECT: " + text);
        }

        return new BigDecimal(text);
    }

    public String getStringOfLetters(String message, boolean upperCase){
        System.out.println(message);

        String text = scanner.nextLine();

        if(upperCase && !text.matches("[A-Z]+")){
            throw new AppException("STRING IS NOT CORRECT: " + text);
        }
        else if(!upperCase && !text.matches("[a-z]+")){
            throw new AppException("STRING IS NOT CORRECT: " + text);
        }

        return text;
    }

    public void close() {

        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

}
