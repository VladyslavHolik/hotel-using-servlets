package holik.hotel.servlet.web.command.access;

import holik.hotel.servlet.repository.model.Role;

import java.util.List;

public class AccessManager {
    private final List<String> managerUri;
    private final List<String> userUri;
    private final List<String> commonUri;

    public AccessManager(List<String> managerUri, List<String> userUri, List<String> commonUri) {
        this.managerUri = managerUri;
        this.userUri = userUri;
        this.commonUri = commonUri;
    }

    public boolean isAccessAllowed(String uri, Role role) {
        if (commonUri.contains(uri)) {
            return true;
        }
        if (role != null) {
            if (Role.USER.equals(role) && userUri.contains(uri)) {
                return true;
            }
            return Role.MANAGER.equals(role) && managerUri.contains(uri);
        }
        return false;
    }
}
