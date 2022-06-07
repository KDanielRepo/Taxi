import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.math.BigDecimal.valueOf;

public class Calculator {
    private List<Company> companies;

    public void calculateIncomeAndProfit() {
        Map<BigDecimal, BigDecimal> startingCapitals = initStartingCapitals();
        List<Car> cars = initCars();
        List<Integer> numberOfEmployees = initNumberOfEmployees();
        companies = new ArrayList<>();
        startingCapitals.keySet().forEach(startingCapital -> {
            cars.forEach(car -> {
                numberOfEmployees.forEach(employee -> {
                    Company company = new Company();
                    company.setCar(car);
                    company.setBudged(startingCapital);
                    company.setLoan(startingCapitals.get(startingCapital));
                    company.setNumberOfEmployees(employee);

                    if (company.getNumberOfCars() != 0 && company.getNumberOfCars() * 2 >= employee) {
                        company.getWorkingDaysInTwoYears().keySet().forEach(month -> {
                            BigDecimal income = calculateMonthlyIncome(company, company.getWorkingDaysInTwoYears().get(month));
                            BigDecimal profit = calculateMonthlyProfit(company, company.getWorkingDaysInTwoYears().get(month));
                            income = income.subtract(calculateTaxForIncomeIfEligible(company, income));
                            income = income.subtract(CompanyUtils.getMonthlyExpenses(company, company.getWorkingDaysInTwoYears().get(month)));
                            company.getProfitPerMonth().put(month, profit);
                            company.getIncomePerMonth().put(month, income);
                        });
                    }
                    companies.add(company);
                });
            });
        });
    }

    public void typeAnswers(){
        companies.forEach(company -> {
            BigDecimal sumOfIncome = BigDecimal.ZERO;
            for (Map.Entry<LocalDate, BigDecimal> set : company.getIncomePerMonth().entrySet()){
                sumOfIncome = sumOfIncome.add(set.getValue());
                System.out.println(set.getKey()+" : "+sumOfIncome);
            }
        });
    }

    public BigDecimal calculateMonthlyIncome(Company company, Integer workingDays) {
        return (company.getConstants().getStartPrice()
                .multiply(valueOf(company.getMaxCourses())))
                .add(company.getConstants().getPricePerKilometer()
                        .multiply(valueOf(company.getMaxCourses())
                                .multiply(valueOf(company.getConstants().getAverageCourseLength())))).multiply(valueOf(workingDays));
    }

    public BigDecimal calculateMonthlyProfit(Company company, Integer workingDays) {
        return (company.getConstants().getStartPrice()
                .multiply(valueOf(company.getMaxCourses())))
                .add(company.getConstants().getPricePerKilometer()
                        .multiply(valueOf(company.getMaxCourses())
                                .multiply(valueOf(company.getConstants().getAverageCourseLength())))).multiply(valueOf(workingDays));
    }

    public BigDecimal calculateTaxForIncomeIfEligible(Company company, BigDecimal income) {
        if (income.compareTo(company.getConstants().getTaxFreeIncomeValue()) > 0) {
            return income.multiply(valueOf(0.18));
        }
        return BigDecimal.ZERO;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.calculateIncomeAndProfit();
        calculator.typeAnswers();
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
        sandero.setAverageFuelConsumption(6.9);
        cars.add(sandero);

        Car audi = new Car();
        audi.setCost(valueOf(40000));
        audi.setFuelType("Benzyna");
        audi.setAverageFuelConsumption(6.9);
        audi.setName("Audi a6");
        audi.setFuelCost(valueOf(7.2));
        cars.add(audi);

        Car toyota = new Car();
        toyota.setCost(valueOf(150000));
        toyota.setFuelType("Hybryda");
        toyota.setAverageFuelConsumption(5.5);
        toyota.setName("Toyota camry");
        toyota.setFuelCost(valueOf(7.2));
        cars.add(toyota);

        Car skoda = new Car();
        skoda.setCost(valueOf(20000));
        skoda.setFuelType("Benzyna");
        skoda.setAverageFuelConsumption(6.0);
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

}
