import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

public class CompanyUtils {
    public static Double getWorkTimePerDay(Company company) {
        return (double) company.getNumberOfEmployees() * 8;
    }

    public static Double getAverageKilometersPerDay(Company company){
        return company.getWorkTimePerDay() / 100 * company.getConstants().getWorkPercentage() * company.getConstants().getAverageSpeed();
    }

    public static BigDecimal getDailyFuelConsumption(Company company){
        return (valueOf(company.getAverageKilometersPerDay())
                .divide(valueOf(100), 2, RoundingMode.DOWN))
                .multiply(valueOf(company.getCar().getAverageFuelConsumption()))
                .multiply(company.getCar().getFuelCost());
    }

    public static Double getMaxCourseAmount(Company company){
        return Math.floor(company.getAverageKilometersPerDay() / (company.getConstants().getAverageCourseLength() + company.getConstants().getAverageDistanceToClient()));
    }

    public static BigDecimal getMonthlyExpenses(Company company, Integer workingDays){
        return company.getConstants().getEmployerCost().multiply(valueOf(company.getNumberOfEmployees()))
                .add(company.getCar().getCost().multiply(valueOf(0.004).multiply(valueOf(company.getNumberOfCars()))))
                .add(company.getDailyFuelConsumption().multiply(valueOf(workingDays)))
                .add(company.getLoanInstallmentValue());
    }

    public static BigDecimal calculateMonthlyLoanInstallment(Company company){
        return company.getLoan().divide(valueOf(24), 3, RoundingMode.HALF_UP);
    }

    public static Integer getNumberOfCars(Company company){
        Integer numberOfCars = company.getBudged()
                .subtract(company.getConstants().getEmployerCost().multiply(valueOf(company.getNumberOfEmployees())).multiply(valueOf(3))) // 3 to ilość wypłat którą trzymamy w zapasie żeby móc zapłacić pracownikom
                .divide(company.getCar().getCost(), 2, RoundingMode.DOWN).intValue();
        return numberOfCars > company.getNumberOfEmployees() ? company.getNumberOfEmployees() : numberOfCars;
    }
}
