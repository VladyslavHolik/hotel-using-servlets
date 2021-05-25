package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.validator.ApplicationValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

public class OrderCommand implements Command {
    private final RoomService roomService;
    private final ApplicationService applicationService;
    private final ApplicationValidator applicationValidator;

    public OrderCommand(RoomService roomService, ApplicationService applicationService, ApplicationValidator applicationValidator) {
        this.roomService = roomService;
        this.applicationService = applicationService;
        this.applicationValidator = applicationValidator;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        LocalDateTime arrival = LocalDateTime.parse(request.getParameter("arrival"));
        LocalDateTime leaving = LocalDateTime.parse(request.getParameter("leaving"));

        Room room = roomService.getRoomById(roomId).orElseThrow();
        Application application = getApplication(request, room, arrival, leaving);
        applicationValidator.validate(application);

        String forward = Pages.PAGE_ERROR_PAGE;

        if (!applicationService.canBeBooked(application)) {
            String errorMessage = "Sorry, that room is already booked for that time";
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }
        applicationService.saveApplication(application);
        return "redirect:/rooms";
    }

    private Application getApplication(HttpServletRequest request, Room room, LocalDateTime arrival, LocalDateTime leaving) {
        Application application = new Application();
        HttpSession session = request.getSession();
        application.setUserId((int) session.getAttribute("userId"));
        application.setSpace(room.getSpace());
        application.setRoomId(room.getId());
        application.setRoomClass(room.getRoomClass());
        application.setDatetimeOfArrival(arrival);
        application.setDatetimeOfLeaving(leaving);
        application.setStatus(ApplicationStatus.APPROVED);
        return application;
    }

    @Override
    public String toString() {
        return "OrderCommand";
    }
}
