package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.CarBodyColor;
import model.enums.CarBodyType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarBody {
    private CarBodyColor color;
    private CarBodyType type;
    private List<String> components;
}
