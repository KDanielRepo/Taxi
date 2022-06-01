import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

public class Calc {
    private Constants constants;
    private BigDecimal averageKilometersPerDay;
    private Integer maxCourses;
    private BigDecimal dailyFuelConsumption;
    private Integer workTimePerDay;
    private BigDecimal dailyEarnings;
    private Integer carAmount;

    public void calculate() {
        Map<BigDecimal, BigDecimal> startingCapitals = initStartingCapitals();
        List<Car> cars = initCars();
        List<Integer> numberOfEmployees = initNumberOfEmployees();
        List<Integer> workingDaysIn2022 = initWorkingDaysIn2022();
        List<Integer> workingDaysIn2023 = initWorkingDaysIn2023();
        List<BigDecimal> monthlyEarnings = new ArrayList<>();
        constants = new Constants();

        startingCapitals.keySet().forEach(startingCapital -> {
            cars.forEach(car -> {
                numberOfEmployees.forEach(employee -> {
                    carAmount = startingCapital.subtract(constants.getEmployerCost().multiply(valueOf(employee))).divide(car.getCost(), 2, RoundingMode.DOWN).intValue();
                    carAmount = carAmount > employee ? employee : carAmount;
                    if (carAmount != 0 && carAmount * 2 >= employee) {
                        System.out.println("Założenia początkowe: Kapitał: " + startingCapital + " ; Ilość aut: " + carAmount + " ; Ilość pracowników: " + employee + " ; Typ auta : " + car.getName());
                        workTimePerDay = 8 * employee;
                        averageKilometersPerDay = valueOf(workTimePerDay)
                                .divide(valueOf(100), 2, RoundingMode.DOWN)
                                .multiply(valueOf(constants.getWorkPercentage()))
                                .multiply(valueOf(constants.getAverageSpeed()));
                        dailyFuelConsumption = (averageKilometersPerDay
                                .divide(valueOf(100), 2, RoundingMode.DOWN))
                                .multiply(car.getAverageFuelConsumption())
                                .multiply(car.getFuelCost());
                        maxCourses = averageKilometersPerDay.intValue() / (constants.getAverageCourseLength() + constants.getAverageDistanceToClient());
                        dailyEarnings = (constants.getStartPrice()
                                .multiply(valueOf(maxCourses)))
                                .add(constants.getPricePerKilometer()
                                        .multiply(valueOf(maxCourses)
                                                .multiply(valueOf(constants.getAverageCourseLength()))));
                        BigDecimal expenses = dailyEarnings.multiply(valueOf(0.18)).subtract(dailyFuelConsumption);
                        dailyEarnings = dailyEarnings.subtract(expenses);
                        BigDecimal installment = startingCapitals.get(startingCapital)
                                .add(startingCapitals.get(startingCapital)
                                        .multiply(valueOf(0.09)))
                                .divide(valueOf(24), ROUND_HALF_UP);
                        for (int i = 0; i < 12; i++) {
                            BigDecimal monthEarnings = dailyEarnings
                                    .multiply(valueOf(workingDaysIn2022.get(i)))
                                    .subtract(constants.getEmployerCost()
                                            .multiply(valueOf(employee)))
                                    .subtract(car.getCost().multiply(valueOf(0.004))
                                            .multiply(valueOf(carAmount)))
                                    .subtract(installment);
                            monthlyEarnings.add(monthEarnings);
                        }
                        for (int i = 0; i < 12; i++) {
                            BigDecimal monthEarnings = dailyEarnings
                                    .multiply(valueOf(workingDaysIn2023.get(i)))
                                    .subtract(constants.getEmployerCost()
                                            .multiply(valueOf(employee)))
                                    .subtract(car.getCost()
                                            .multiply(valueOf(0.004))
                                            .multiply(valueOf(carAmount)));
                            monthlyEarnings.add(monthEarnings);
                        }
                    }
                });
            });
        });
        BigDecimal temp = BigDecimal.ZERO;
        for (int i = 0; i < monthlyEarnings.size(); i++) {
            if (i % 24 == 0) {
                temp = BigDecimal.ZERO;
            }
            temp = temp.add(monthlyEarnings.get(i));
            System.out.print(temp.setScale(2, RoundingMode.DOWN) + " ");
        }
    }

    public static void main(String[] args) {
        Calc calc = new Calc();
        calc.calculate();
    }

    private Map<BigDecimal, BigDecimal> initStartingCapitals() {
        Map<BigDecimal, BigDecimal> startingCapitals = new HashMap<>();
        startingCapitals.put(valueOf(100000), valueOf(40000));
        startingCapitals.put(valueOf(250000), valueOf(190000));
        startingCapitals.put(valueOf(500000), valueOf(440000));
        startingCapitals.put(valueOf(1000000), valueOf(940000));
        return startingCapitals;
    }

    private List<Car> initCars() {
        List<Car> cars = new ArrayList<>();
        Car sandero = new Car();
        sandero.setCost(valueOf(59200));
        sandero.setName("Dacia Sandero");
        sandero.setFuelCost(valueOf(4));
        sandero.setFuelType("LPG");
        sandero.setAverageFuelConsumption(valueOf(6.9));
        cars.add(sandero);

        Car audi = new Car();
        audi.setCost(valueOf(40000));
        audi.setFuelType("Benzyna");
        audi.setAverageFuelConsumption(valueOf(6.9));
        audi.setName("Audi a6");
        audi.setFuelCost(valueOf(7.2));
        cars.add(audi);

        Car toyota = new Car();
        toyota.setCost(valueOf(150000));
        toyota.setFuelType("Hybryda");
        toyota.setAverageFuelConsumption(valueOf(5.5));
        toyota.setName("Toyota camry");
        toyota.setFuelCost(valueOf(7.2));
        cars.add(toyota);

        Car skoda = new Car();
        skoda.setCost(valueOf(20000));
        skoda.setFuelType("Benzyna");
        skoda.setAverageFuelConsumption(valueOf(6));
        skoda.setName("Skoda octavia");
        skoda.setFuelCost(valueOf(7.2));
        cars.add(skoda);
        return cars;
    }

    private List<Integer> initNumberOfEmployees() {
        List<Integer> numberOfEmployees = new ArrayList<>();
        numberOfEmployees.add(1);
        numberOfEmployees.add(2);
        numberOfEmployees.add(3);
        numberOfEmployees.add(4);
        numberOfEmployees.add(6);
        numberOfEmployees.add(8);
        return numberOfEmployees;
    }

    private List<Integer> initWorkingDaysIn2022() {
        List<Integer> workingDaysIn2022 = new ArrayList<>();
        workingDaysIn2022.add(19);
        workingDaysIn2022.add(20);
        workingDaysIn2022.add(23);
        workingDaysIn2022.add(20);
        workingDaysIn2022.add(21);
        workingDaysIn2022.add(21);
        workingDaysIn2022.add(21);
        workingDaysIn2022.add(22);
        workingDaysIn2022.add(22);
        workingDaysIn2022.add(21);
        workingDaysIn2022.add(20);
        workingDaysIn2022.add(21);
        return workingDaysIn2022;
    }

    private List<Integer> initWorkingDaysIn2023() {
        List<Integer> workingDaysIn2023 = new ArrayList<>();
        workingDaysIn2023.add(21);
        workingDaysIn2023.add(20);
        workingDaysIn2023.add(23);
        workingDaysIn2023.add(19);
        workingDaysIn2023.add(21);
        workingDaysIn2023.add(21);
        workingDaysIn2023.add(21);
        workingDaysIn2023.add(22);
        workingDaysIn2023.add(21);
        workingDaysIn2023.add(22);
        workingDaysIn2023.add(20);
        workingDaysIn2023.add(19);
        return workingDaysIn2023;
    }

}
