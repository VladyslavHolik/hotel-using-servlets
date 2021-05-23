package holik.hotel.servlet.web.command;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for getting image data.
 */
public class GetImage implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		String webappPath = request.getServletContext().getRealPath("/");
		String imagePath = getImagePath(type, id, webappPath);

		FileInputStream inputStream = new FileInputStream(imagePath);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
		int ch;
		while ((ch = bufferedInputStream.read()) != -1) {
			bufferedOutputStream.write(ch);
		}

		bufferedInputStream.close();
		inputStream.close();
		bufferedOutputStream.close();
		out.close();
		return null;
	}

	private String getImagePath(String type, String id, String webappPath) {
		String imagePath;
		if ("main".equals(type)) {
			imagePath = webappPath + "WEB-INF/images/" + type + "/" + id + ".jpg";
		} else {
			imagePath = webappPath + "WEB-INF/images/" + type + "/" + id + "/1.jpg";
		}
		return imagePath;
	}

	@Override
	public String toString() {
		return "Get image";
	}
}
