package holik.hotel.servlet.service;

import holik.hotel.servlet.repository.model.Bill;

import java.util.List;

public interface BillService {
    List<Bill> getUserBills(int userId);
}
