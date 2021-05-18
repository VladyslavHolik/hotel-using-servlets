package holik.hotel.servlet.path;

/**
 * Class that is used to get command from URI.
 * TODO delete
 */
public final class PathParser {

	public static String getCommand(String uri) {
		String result;
		int indexOfSlash = uri.lastIndexOf('/');
		int indexOfQuestion = uri.indexOf('?');
		if (indexOfSlash != uri.length() - 1) {
			if (indexOfQuestion == -1) {
				result = uri.substring(indexOfSlash + 1);
			} else {
				result = uri.substring(indexOfSlash + 1, indexOfQuestion);
			}
		} else {
			result = "home";
		}
		return result;
	}
}
