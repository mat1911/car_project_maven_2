package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.TyreType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wheel {
    private String model;
    private int size;
    private TyreType type;
}
