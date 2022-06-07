import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Constants {
    private BigDecimal startPrice = BigDecimal.valueOf(8);
    private BigDecimal pricePerKilometer = BigDecimal.valueOf(3);
    private BigDecimal employeeSalary = BigDecimal.valueOf(3000);
    private BigDecimal employerCost = BigDecimal.valueOf(4705.88);
    private Integer averageSpeed = 30;
    private Integer averageDistanceToClient = 2;
    private Integer averageCourseLength = 3;
    private Integer workPercentage = 70;
    private BigDecimal taxFreeIncomeValue = BigDecimal.valueOf(30000);
}
