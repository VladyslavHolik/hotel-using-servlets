package holik.hotel.servlet.web.context;

import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.impl.DefaultApplicationRepository;
import holik.hotel.servlet.repository.impl.DefaultRoomRepository;
import holik.hotel.servlet.repository.impl.DefaultUserRepository;
import holik.hotel.servlet.service.*;
import holik.hotel.servlet.service.impl.*;
import holik.hotel.servlet.web.converter.UserConverter;
import holik.hotel.servlet.web.validator.*;

public class ApplicationContext {
    private static final ApplicationService applicationService;
    private static final EncoderService encoderService;
    private static final RoomService roomService;
    private static final UserService userService;
    private static final BillService billService;
    private static final ApplicationValidator applicationValidator;
    private static final LanguageValidator languageValidator;
    private static final LoginValidator loginValidator;
    private static final ChoiceValidator choiceValidator;
    private static final UserValidator userValidator;
    private static final RoomValidator roomValidator;
    private static final PageValidator pageValidator;
    private static final UserConverter userConverter;

    static {
        ApplicationRepository applicationRepository = new DefaultApplicationRepository();
        RoomRepository roomRepository = new DefaultRoomRepository();
        UserRepository userRepository = new DefaultUserRepository();

        roomService = new DefaultRoomService(roomRepository);
        applicationService = new DefaultApplicationService(applicationRepository, roomService);
        billService = new DefaultBillService(applicationService, roomService);
        encoderService = new DefaultEncoderService();
        userService = new DefaultUserService(userRepository, encoderService);
        applicationValidator = new ApplicationValidator(applicationService, userService);
        languageValidator = new LanguageValidator();
        loginValidator = new LoginValidator();
        choiceValidator = new ChoiceValidator(roomService);
        userValidator = new UserValidator(userService);
        roomValidator = new RoomValidator(roomService);
        pageValidator = new PageValidator(roomService);
        userConverter = new UserConverter(encoderService);
    }

    public static ApplicationService getApplicationService() {
        return applicationService;
    }

    public static RoomService getRoomService() {
        return roomService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ApplicationValidator getApplicationValidator() {
        return applicationValidator;
    }

    public static BillService getBillService() {
        return billService;
    }

    public static LanguageValidator getLanguageValidator() {
        return languageValidator;
    }

    public static LoginValidator getLoginValidator() {
        return loginValidator;
    }

    public static ChoiceValidator getChoiceValidator() {
        return choiceValidator;
    }

    public static UserValidator getUserValidator() {
        return userValidator;
    }

    public static RoomValidator getRoomValidator() {
        return roomValidator;
    }

    public static PageValidator getPageValidator() {
        return pageValidator;
    }

    public static UserConverter getUserConvertor() {
        return userConverter;
    }
}
