package holik.hotel.servlet.web.dto;

import java.util.List;

import holik.hotel.servlet.repository.model.Room;

/**
 * Class that is used for storing data for rooms page.
 */
public class RoomsContent {
	public static final int NUMBER_OF_ROOMS_ON_PAGE = 3;
	private List<Room> rooms;
	private List<Page> pages;
	
	public static class Page {
		private String name;
		private String pageClass;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public String getPageClass() {
			return pageClass;
		}

		public void setPageClass(String pageClass) {
			this.pageClass = pageClass;
		}
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> roomsOnPage) {
		this.rooms = roomsOnPage;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
}
