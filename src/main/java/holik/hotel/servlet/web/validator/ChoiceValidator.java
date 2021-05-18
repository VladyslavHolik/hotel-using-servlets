package holik.hotel.servlet.web.validator;

public class ChoiceValidator {
    public void validateChoice(String choice) {
        if (choice == null || choice.isEmpty()) {
            throw new IllegalArgumentException("Choice cannot be empty");
        }
    }
}
