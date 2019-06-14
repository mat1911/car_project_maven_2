package validators;

import model.Car;
import model.enums.CarBodyColor;
import model.enums.CarBodyType;
import model.enums.EngineType;
import model.enums.TyreType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CarValidator {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car){
        errors.clear();

        if (!isModelValid(car)) {
            errors.put("model", "not valid: " + car.getModel());
        }

        if(!isPriceValid(car)){
            errors.put("price", "not valid: " + car.getPrice());
        }

        if(!isMileageValid(car)){
            errors.put("mileage", "not valid: " + car.getMileage());
        }

        if(!isEngineTypeValid(car)){
            errors.put("engine type", "not valid: " + car.getEngine().getType());
        }

        if(!isEnginePowerValid(car)){
            errors.put("engine power", "not valid: " + car.getEngine().getPower());
        }

        if(!isCarBodyColorValid(car)){
            errors.put("car body color", "not valid: " + car.getCarBody().getColor());
        }

        if(!isCarBodyTypeValid(car)){
            errors.put("car body type", "not valid: " + car.getCarBody().getType());
        }

        if(!areCarBodyComponentsValid(car)){
            errors.put("car body components", "not valid: " +  car.getCarBody().getComponents().stream().collect(Collectors.joining(", ")));
        }

        if(!isWheelModelValid(car)){
            errors.put("wheel model", "not valid: " + car.getWheel().getModel());
        }

        if(!isWheelSizeValid(car)){
            errors.put("wheel size", "not valid: " + car.getWheel().getSize());
        }

        if(!isWheelTypeValid(car)){
            errors.put("wheel type", "not valid: " + car.getWheel().getType());
        }

        return errors;

    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car){
        return car.getModel().matches("[A-Z]+");
    }

    private boolean isPriceValid(Car car){
        if(car.getPrice().compareTo(BigDecimal.ZERO) < 0)
            return false;
        return true;
    }

    private boolean isMileageValid(Car car){
        if(car.getMileage() < 0)
            return false;
        return true;
    }

    private boolean isEngineTypeValid(Car car){
        EngineType[] types = EngineType.values();

        if(Arrays.stream(types).filter(type -> type == car.getEngine().getType()).count() <= 0)
            return false;
        return true;
    }

    private boolean isEnginePowerValid(Car car){
        if(car.getEngine().getPower().compareTo(BigDecimal.ZERO) < 0)
            return false;
        return true;
    }

    private boolean isCarBodyColorValid(Car car){
        CarBodyColor[] colors = CarBodyColor.values();

        if(Arrays.stream(colors).filter(color -> color == car.getCarBody().getColor()).count() <= 0)
            return false;
        return true;
    }

    private boolean isCarBodyTypeValid(Car car){
        CarBodyType[] types = CarBodyType.values();

        if(Arrays.stream(types).filter(color -> color == car.getCarBody().getType()).count() <= 0)
            return false;
        return true;
    }

    private boolean areCarBodyComponentsValid(Car car){
        return car.getCarBody().getComponents() != null && car.getCarBody().getComponents().stream().allMatch(comp -> comp.matches("[A-Z ]+"));
    }

    private boolean isWheelModelValid(Car car){
        return car.getWheel().getModel().matches("[A-Z\\s]+");
    }

    private boolean isWheelSizeValid(Car car){
        if(car.getWheel().getSize() <= 0)
            return false;
        return true;
    }

    private boolean isWheelTypeValid(Car car){
        TyreType[] types = TyreType.values();

        if(Arrays.stream(types).filter(type -> type == car.getWheel().getType()).count() <= 0)
            return false;
        return true;
    }

}