import lombok.Data;

import java.math.BigDecimal;

@Data
public class Car {
    private String name;
    private BigDecimal cost;
    private String fuelType;
    private BigDecimal fuelCost;
    private Double averageFuelConsumption;
}
