import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Data
public class Company {
    private Integer numberOfEmployees;
    private Integer numberOfCars;
    private Car car;
    private Map<LocalDate, BigDecimal> incomePerMonth;
    private Map<LocalDate, BigDecimal> profitPerMonth;
    private Map<LocalDate, Integer> workingDaysInTwoYears;
    private Constants constants;
    private Double maxCourses;
    private BigDecimal dailyFuelConsumption;
    private Double workTimePerDay;
    private Double averageKilometersPerDay;
    private BigDecimal budged;
    private BigDecimal loan;
    private BigDecimal loanInstallmentValue;

    public Company() {
         initWorkingDaysInTwoYears();
         incomePerMonth = new TreeMap<>();
         profitPerMonth = new TreeMap<>();
         constants = new Constants();
    }

    private void initWorkingDaysInTwoYears() {
        workingDaysInTwoYears = new HashMap<>();
        LocalDate start = LocalDate.ofYearDay(2022,1);
        workingDaysInTwoYears.put(start, 19);
        workingDaysInTwoYears.put(start.plusMonths(1), 20);
        workingDaysInTwoYears.put(start.plusMonths(2), 23);
        workingDaysInTwoYears.put(start.plusMonths(3), 20);
        workingDaysInTwoYears.put(start.plusMonths(4), 21);
        workingDaysInTwoYears.put(start.plusMonths(5), 21);
        workingDaysInTwoYears.put(start.plusMonths(6), 21);
        workingDaysInTwoYears.put(start.plusMonths(7), 22);
        workingDaysInTwoYears.put(start.plusMonths(8), 22);
        workingDaysInTwoYears.put(start.plusMonths(9), 21);
        workingDaysInTwoYears.put(start.plusMonths(10), 20);
        workingDaysInTwoYears.put(start.plusMonths(11), 21);//

        workingDaysInTwoYears.put(start.plusMonths(12), 21);
        workingDaysInTwoYears.put(start.plusMonths(13), 20);
        workingDaysInTwoYears.put(start.plusMonths(14), 23);
        workingDaysInTwoYears.put(start.plusMonths(15), 19);
        workingDaysInTwoYears.put(start.plusMonths(16), 21);
        workingDaysInTwoYears.put(start.plusMonths(17), 21);
        workingDaysInTwoYears.put(start.plusMonths(18), 21);
        workingDaysInTwoYears.put(start.plusMonths(19), 22);
        workingDaysInTwoYears.put(start.plusMonths(20), 21);
        workingDaysInTwoYears.put(start.plusMonths(21), 20);
        workingDaysInTwoYears.put(start.plusMonths(22), 19);
        workingDaysInTwoYears.put(start.plusMonths(23), 19);
    }

    public Double getMaxCourses() {
        if(Objects.isNull(maxCourses)){
            maxCourses = CompanyUtils.getMaxCourseAmount(this);
        }
        return maxCourses;
    }

    public BigDecimal getDailyFuelConsumption() {
        if(Objects.isNull(dailyFuelConsumption)){
            dailyFuelConsumption = CompanyUtils.getDailyFuelConsumption(this);
        }
        return dailyFuelConsumption;
    }

    public Double getWorkTimePerDay() {
        if(Objects.isNull(workTimePerDay)){
            workTimePerDay = CompanyUtils.getWorkTimePerDay(this);
        }
        return workTimePerDay;
    }

    public Double getAverageKilometersPerDay() {
        if(Objects.isNull(averageKilometersPerDay)){
            averageKilometersPerDay = CompanyUtils.getAverageKilometersPerDay(this);
        }
        return averageKilometersPerDay;
    }

    public BigDecimal getLoanInstallmentValue() {
        if(Objects.isNull(loanInstallmentValue)){
            loanInstallmentValue = CompanyUtils.calculateMonthlyLoanInstallment(this);
        }
        return loanInstallmentValue;
    }

    public Integer getNumberOfCars() {
        if(Objects.isNull(numberOfCars)){
            numberOfCars = CompanyUtils.getNumberOfCars(this);
        }
        return numberOfCars;
    }
}
