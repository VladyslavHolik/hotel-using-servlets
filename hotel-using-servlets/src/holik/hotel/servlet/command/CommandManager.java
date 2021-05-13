package holik.hotel.servlet.command;

import java.util.Map;
import java.util.TreeMap;

public final class CommandManager {
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		commands.put("home", new HomeCommand());
		commands.put("getlogin", new GetLoginCommand());
		commands.put("login", new LoginCommand());
		commands.put("signup", new RegisterCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("getsignup", new GetSignUpCommand());
		commands.put("rooms", new RoomsCommand());
		commands.put("room", new RoomCommand());
		commands.put("language", new LanguageCommand());
		commands.put("sorting", new SortingCommand());
	}
	
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			return commands.get("unknownCommand"); 
		}
		
		return commands.get(commandName);
	}
}
