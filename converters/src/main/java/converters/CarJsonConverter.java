package converters;

import model.Car;

public class CarJsonConverter extends JsonConverter<Car>{
    public CarJsonConverter(String jsonFileName) {
        super(jsonFileName);
    }
}
