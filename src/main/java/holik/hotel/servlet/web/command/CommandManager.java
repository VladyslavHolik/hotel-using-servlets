package holik.hotel.servlet.web.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that is responsible for getting command realizations.
 */
public class CommandManager {
	private static final Map<String, Command> commands = new HashMap<>();
	
	static {
		commands.put("/home", new HomeCommand());
		commands.put("/signin", new GetLoginCommand());
		commands.put("signin", new LoginCommand());
		commands.put("signup", new RegisterCommand());
		commands.put("/logout", new LogoutCommand());
		commands.put("/signup", new GetSignUpCommand());
		commands.put("/rooms", new RoomsCommand());
		commands.put("/room", new RoomCommand());
		commands.put("/language", new LanguageCommand());
		commands.put("sorting", new SortingCommand());
		commands.put("/application", new GetApplicationCommand());
		commands.put("application", new ApplicationCommand());
		commands.put("/applications", new ApplicationsCommand());
		commands.put("/form", new GetApplicationFormCommand());
		commands.put("form", new ProcessApplicationCommand());
		commands.put("/myapplications", new GetMyApplicationsCommand());
		commands.put("/image", new GetImage());
		commands.put("book", new BookRoomCommand());
		commands.put("/bills", new GetBillsCommand());
		commands.put("pay", new PayBillCommand());
	}
	
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			throw new IllegalArgumentException("Unknown command" + commandName);
		}
		
		return commands.get(commandName);
	}
}
