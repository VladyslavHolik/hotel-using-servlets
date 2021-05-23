package holik.hotel.servlet.web.context;

import holik.hotel.servlet.job.BookingRemover;
import holik.hotel.servlet.job.RoomStatusSetter;
import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.impl.DefaultApplicationRepository;
import holik.hotel.servlet.repository.impl.DefaultRoomRepository;
import holik.hotel.servlet.repository.impl.DefaultUserRepository;
import holik.hotel.servlet.service.*;
import holik.hotel.servlet.service.impl.*;
import holik.hotel.servlet.web.command.*;
import holik.hotel.servlet.web.converter.UserConverter;
import holik.hotel.servlet.web.validator.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that performs dependency injection.
 */
public class ApplicationContext {
    private static final Map<String, Command> commands = new HashMap<>();
    private static ApplicationService applicationService;
    private static RoomService roomService;
    private static UserService userService;
    private static BillService billService;
    private static ApplicationValidator applicationValidator;
    private static LanguageValidator languageValidator;
    private static LoginValidator loginValidator;
    private static ChoiceValidator choiceValidator;
    private static UserValidator userValidator;
    private static RoomValidator roomValidator;
    private static PageValidator pageValidator;
    private static UserConverter userConverter;
    private static BookingRemover bookingRemover;
    private static RoomStatusSetter roomStatusSetter;

    public static void initialise() {
        ApplicationRepository applicationRepository = new DefaultApplicationRepository();
        RoomRepository roomRepository = new DefaultRoomRepository();
        UserRepository userRepository = new DefaultUserRepository();
        EncoderService encoderService = new DefaultEncoderService();

        roomService = new DefaultRoomService(roomRepository);
        applicationService = new DefaultApplicationService(applicationRepository, roomService);
        billService = new DefaultBillService(applicationService, roomService);
        userService = new DefaultUserService(userRepository, encoderService);
        applicationValidator = new ApplicationValidator(applicationService, userService);
        languageValidator = new LanguageValidator();
        loginValidator = new LoginValidator();
        choiceValidator = new ChoiceValidator(roomService);
        userValidator = new UserValidator(userService);
        roomValidator = new RoomValidator(roomService);
        pageValidator = new PageValidator(roomService);
        userConverter = new UserConverter(encoderService);
        bookingRemover = new BookingRemover(applicationService);
        roomStatusSetter = new RoomStatusSetter(applicationService, roomService);

        initialiseCommands();
    }

    private static void initialiseCommands() {
        commands.put("/", new HomeCommand());
        commands.put("/signin", new GetLoginCommand());
        commands.put("signin", new LoginCommand(userService, loginValidator));
        commands.put("signup", new RegisterCommand(userService, userValidator, userConverter));
        commands.put("/logout", new LogoutCommand());
        commands.put("/signup", new GetSignUpCommand());
        commands.put("/rooms", new RoomsCommand(roomService, pageValidator));
        commands.put("/room", new RoomCommand(roomService, roomValidator));
        commands.put("/language", new LanguageCommand(languageValidator));
        commands.put("sorting", new SortingCommand());
        commands.put("/application", new GetApplicationCommand());
        commands.put("application", new ApplicationCommand(applicationService, applicationValidator));
        commands.put("/applications", new ApplicationsCommand(applicationService));
        commands.put("/form", new GetApplicationFormCommand(applicationService, userService, applicationValidator));
        commands.put("form", new ProcessApplicationCommand(applicationService, choiceValidator, applicationValidator));
        commands.put("/myapplications", new GetMyApplicationsCommand(applicationService));
        commands.put("/image", new GetImage());
        commands.put("book", new BookRoomCommand(applicationService, applicationValidator));
        commands.put("/bills", new GetBillsCommand(billService));
        commands.put("pay", new PayBillCommand(applicationService, applicationValidator));
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            throw new IllegalArgumentException("Unknown command" + commandName);
        }
        return commands.get(commandName);
    }

    public static BookingRemover getBookingRemover() {
        return bookingRemover;
    }

    public static RoomStatusSetter getRoomStatusSetter() {
        return roomStatusSetter;
    }
}
