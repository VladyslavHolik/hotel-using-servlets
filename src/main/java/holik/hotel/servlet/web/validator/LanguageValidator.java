package holik.hotel.servlet.web.validator;

public class LanguageValidator {
    public void validateLanguage(String language) {
        if (language == null || !(language.equals("ru") || language.equals("en"))) {
            throw new IllegalArgumentException("Invalid locale  " + language);
        }
    }
}
