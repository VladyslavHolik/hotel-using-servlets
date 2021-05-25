package holik.hotel.servlet.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LanguageValidatorTest {
    @InjectMocks
    private LanguageValidator languageValidator;

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