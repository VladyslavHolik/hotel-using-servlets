package holik.hotel.servlet.web.command.access;

import holik.hotel.servlet.repository.model.Role;

import java.util.List;

public class AccessManager {
    private final List<String> managerCommands;
    private final List<String> userCommands;
    private final List<String> commonCommands;

    public AccessManager(List<String> managerCommands, List<String> userCommands, List<String> commonCommands) {
        this.managerCommands = managerCommands;
        this.userCommands = userCommands;
        this.commonCommands = commonCommands;
    }

    public boolean isAccessAllowed(String commandName, Role role) {
        if (commandName != null && !commandName.isEmpty()) {
            if (commonCommands.contains(commandName)) {
                return true;
            }
            if (role != null) {
                if (Role.USER.equals(role) && userCommands.contains(commandName)) {
                    return true;
                }
                return Role.MANAGER.equals(role) && managerCommands.contains(commandName);
            }
        }
        return false;
    }
}
