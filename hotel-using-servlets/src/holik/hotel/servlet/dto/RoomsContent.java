package holik.hotel.servlet.dto;

import java.util.List;

import holik.hotel.servlet.model.Room;

public final class RoomsContent {
	private List<Room> roomsOnPage;
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
		return roomsOnPage;
	}

	public void setRooms(List<Room> roomsOnPage) {
		this.roomsOnPage = roomsOnPage;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
}
