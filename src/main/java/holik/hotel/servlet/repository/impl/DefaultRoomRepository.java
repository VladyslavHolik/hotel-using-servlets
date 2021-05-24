package holik.hotel.servlet.repository.impl;

import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.db.DBManager;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.repository.model.RoomStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default realization of room repository.
 */
public class DefaultRoomRepository implements RoomRepository {
    private static final Logger LOG = Logger.getLogger(DefaultRoomRepository.class);

    @Override
    public List<Room> getAllRooms() {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    private void addRooms(List<Room> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Room room = new Room();
            room.setId(resultSet.getInt("id"));
            room.setNumber(resultSet.getString("number"));
            room.setPrice(resultSet.getInt("price"));
            room.setSpace(resultSet.getInt("space"));
            room.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
            room.setRoomStatus(RoomStatus.getStatusById(resultSet.getInt("status")));
            result.add(room);
        }
    }

    @Override
    public Optional<Room> getRoomById(int id) {
        Room room = null;
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setNumber(resultSet.getString("number"));
                room.setPrice(resultSet.getInt("price"));
                room.setSpace(resultSet.getInt("space"));
                room.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
            }
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return Optional.ofNullable(room);
    }

    @Override
    public List<Room> getSpecificRooms(int classId, int space, int status) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms where class=? and space=? and status=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, classId);
            statement.setInt(2, space);
            statement.setInt(3, status);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public boolean updateRoom(Room room) {
        boolean result = false;
        Optional<Room> storedRoom = getRoomById(room.getId());
        if (storedRoom.isPresent()) {
            try (Connection connection = DBManager.getConnection()) {
                String sql = "UPDATE Rooms SET number=?, price=?, space=?, class=?, status=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, room.getNumber());
                statement.setInt(2, room.getPrice());
                statement.setInt(3, room.getSpace());
                statement.setInt(4, room.getRoomClass().getId());
                statement.setObject(5, room.getRoomStatus().getId());
                statement.setInt(6, room.getId());
                result = statement.execute();
            } catch (SQLException exception) {
                String message = exception.getLocalizedMessage();
                throw new IllegalStateException(message);
            }
        }
        return result;
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms where status!=4";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Room> getRoomsOrderedByPrice(int limit, int offset) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms order by price limit ? offset ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Room> getRoomsOrderedBySpace(int limit, int offset) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms order by space limit ? offset ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Room> getRoomsOrderedByClass(int limit, int offset) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms order by class limit ? offset ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Room> getRoomsOrderedByStatus(int limit, int offset) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms order by status limit ? offset ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }

    @Override
    public List<Room> getAvailableRooms(int classId, int space) {
        List<Room> result = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "select * from rooms where class=? and space=? and status!=4";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, classId);
            statement.setInt(2, space);
            ResultSet resultSet = statement.executeQuery();
            addRooms(result, resultSet);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
        return result;
    }
}
