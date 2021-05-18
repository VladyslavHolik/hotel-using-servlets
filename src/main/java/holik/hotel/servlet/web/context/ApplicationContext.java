package holik.hotel.servlet.web.context;

import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.impl.DefaultApplicationRepository;
import holik.hotel.servlet.repository.impl.DefaultRoomRepository;
import holik.hotel.servlet.repository.impl.DefaultUserRepository;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.EncoderService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultEncoderService;
import holik.hotel.servlet.service.impl.DefaultRoomService;
import holik.hotel.servlet.service.impl.DefaultUserService;
import holik.hotel.servlet.web.validator.ApplicationValidator;

public class ApplicationContext {
    private static final ApplicationService applicationService;
    private static final EncoderService encoderService;
    private static final RoomService roomService;
    private static final UserService userService;
    private static final ApplicationValidator applicationValidator;

    static {
        ApplicationRepository applicationRepository = new DefaultApplicationRepository();
        RoomRepository roomRepository = new DefaultRoomRepository();
        UserRepository userRepository = new DefaultUserRepository();

        roomService = new DefaultRoomService(roomRepository);
        applicationService = new DefaultApplicationService(applicationRepository, roomService);
        userService = new DefaultUserService(userRepository);
        encoderService = new DefaultEncoderService();
        applicationValidator = new ApplicationValidator(applicationService, userService);
    }

    public static ApplicationService getApplicationService() {
        return applicationService;
    }

    public static EncoderService getEncoderService() {
        return encoderService;
    }

    public static RoomService getRoomService() {
        return roomService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ApplicationValidator getApplicationValidator() {
        return applicationValidator;
    };
}
