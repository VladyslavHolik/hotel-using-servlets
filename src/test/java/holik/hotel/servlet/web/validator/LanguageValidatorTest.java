package holik.hotel.servlet.web.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageValidatorTest {
    private LanguageValidator languageValidator;

    @Before
    public void initialize() {
        languageValidator = new LanguageValidator();
    }

    @Test
    public void validateLanguageIfEnglish() {
        languageValidator.validateLanguage("en");
    }

    @Test
    public void validateLanguageIfRussian() {
        languageValidator.validateLanguage("ru");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLanguageIfNull() {
        languageValidator.validateLanguage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLanguageIfNotLanguage() {
        languageValidator.validateLanguage("Not a language");
    }
}