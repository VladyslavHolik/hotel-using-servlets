package holik.hotel.servlet.repository.impl;

import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.db.DBManager;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.RoomClass;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default realization of application repository.
 */
public class DefaultApplicationRepository implements ApplicationRepository {

    @Override
    public void saveApplication(Application application) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO Applications (user_id, space, class, room_id, arrival, leaving, booked, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = prepare(application, connection, sql);
            statement.execute();
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
    }

    private PreparedStatement prepare(Application application, Connection connection, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, application.getUserId());
        statement.setInt(2, application.getSpace());
        statement.setInt(3, application.getRoomClass().getId());
        statement.setInt(4, application.getRoomId());
        statement.setObject(5, application.getDatetimeOfArrival());
        statement.setObject(6, application.getDatetimeOfLeaving());
        statement.setObject(7, application.getDatetimeOfBooking());
        statement.setInt(8, application.getStatus().getId());
        return statement;
    }

    @Override
    public Optional<Application> getApplicationById(int id) {
        Application application = null;
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from applications where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                application = new Application();
                fillApplication(application, resultSet);
            }
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return Optional.ofNullable(application);
    }

    private void fillApplication(Application application, ResultSet resultSet) throws SQLException {
        application.setId(resultSet.getInt("id"));
        application.setUserId(resultSet.getInt("user_id"));
        application.setSpace(resultSet.getInt("space"));
        application.setRoomId(resultSet.getInt("room_id"));
        application.setDatetimeOfArrival(resultSet.getObject("arrival", LocalDateTime.class));
        application.setDatetimeOfLeaving(resultSet.getObject("leaving", LocalDateTime.class));
        application.setDatetimeOfBooking(resultSet.getObject("booked", LocalDateTime.class));
        application.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
        application.setStatus(ApplicationStatus.getStatusById(resultSet.getInt("status")));
    }

    @Override
    public List<Application> getAllApplications() {
        List<Application> list = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from applications";
            addApplications(list, connection, sql);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return list;
    }

    @Override
    public void updateApplication(Application application) {
        Optional<Application> storedApplication = getApplicationById(application.getId());
        if (storedApplication.isPresent()) {
            try (Connection connection = DBManager.getConnection()) {
                String sql = "UPDATE Applications SET user_id=?, space=?, class=?, room_id=?, arrival=?, leaving=?, booked=?, status=? WHERE id=?";
                PreparedStatement statement = prepare(application, connection, sql);
                statement.setInt(9, application.getId());
                statement.execute();
            } catch (SQLException exception) {
                String message = exception.getLocalizedMessage();
                throw new IllegalStateException(message);
            }
        }
    }

    @Override
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from applications where status=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, status.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                fillApplication(application, resultSet);
                applications.add(application);
            }
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return applications;
    }

    @Override
    public boolean canBeBooked(Application application) {
        boolean result = true;
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from applications where (status=5 or status=4) and (room_id=?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, application.getRoomId());
            ResultSet resultSet = statement.executeQuery();
            LocalDateTime arrival = application.getDatetimeOfArrival();
            LocalDateTime leaving = application.getDatetimeOfLeaving();

            while (resultSet.next()) {
                LocalDateTime originArrival = resultSet.getObject("arrival", LocalDateTime.class);
                LocalDateTime originLeaving = resultSet.getObject("leaving", LocalDateTime.class);
                if (isBetween(arrival, originArrival, originLeaving)
                        || isBetween(leaving, originArrival, originLeaving)
                        || isBetween(originArrival, arrival, leaving)) {
                    result = false;
                    break;
                }
            }
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Application> getBookedApplicationsByUserId(int userId) {
        List<Application> bookedApplications = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from applications where status=4 and (user_id=?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                fillApplication(application, resultSet);
                bookedApplications.add(application);
            }
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return bookedApplications;
    }

    private void addApplications(List<Application> list, Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Application application = new Application();
            fillApplication(application, resultSet);
            list.add(application);
        }
    }

    private boolean isBetween(LocalDateTime origin, LocalDateTime start, LocalDateTime end) {
        return origin.isAfter(start) && origin.isBefore(end);
    }
}
