package scheduler.model;

import scheduler.db.ConnectionManager;
import scheduler.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class Reservation {
    private final Date time;
    private final String vaccineName;
    private final String username;



    private Reservation(Reservation.ReservationBuilder builder) {
        this.time = builder.time;
        this.vaccineName = builder.vaccineName;
        this.username = builder.username;

    }
    private Reservation(Reservation.ReservationGetter getter) {
        this.time = getter.time;
        this.vaccineName = getter.vaccineName;
        this.username = getter.username;
    }

    public Date getTime() {return this.time; }

    public String getVaccineName() { return this.vaccineName;}

    public String getUsername() { return this.username;}

    public void saveToDB() throws SQLException {
        ConnectionManager cm = new ConnectionManager();
        Connection con = cm.createConnection();

        String addReservation = "INSERT INTO Reservations VALUES (?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(addReservation);
            statement.setDate(1, (java.sql.Date) this.time);
            statement.setString(2, this.vaccineName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            cm.closeConnection();
        }
    }

    public void showAppointments() throws SQLException {
        ConnectionManager cm = new ConnectionManager();
        Connection con = cm.createConnection();

        String showAppointments = "SELECT * FROM Reservations;";
        try{
            PreparedStatement statement = con.prepareStatement(showAppointments);
        } catch(SQLException e) {
            throw new SQLException();
        } finally {
            cm.closeConnection();
        }
    }




    public static class ReservationBuilder {
        private final Date time;
        private final String vaccineName;
        private final String username;

        public ReservationBuilder(Date time, String vaccineName, String username) {
            this.time = time;
            this.vaccineName = vaccineName;
            this.username = username;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }

    public static class ReservationGetter {
        private Date time;
        private String vaccineName;
        private String username;

        public ReservationGetter(Date time, String vaccineName, String username) {
            this.time = time;
            this.vaccineName = vaccineName;
            this.username = username;
        }

        public Reservation get() throws SQLException {
            ConnectionManager cm = new ConnectionManager();
            Connection con = cm.createConnection();

            String getReservation = "SELECT Time, Name, Username FROM Reservations WHERE Time = ? AND Username = ?;";
            try {
                PreparedStatement statement = con.prepareStatement(getReservation);
                statement.setDate(1, (java.sql.Date) this.time);
                statement.setString(2, this.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    this.time = resultSet.getDate("Time");
                    this.vaccineName = resultSet.getString("Name");
                    this.username = resultSet.getString("Username");
                    cm.closeConnection();
                    return new Reservation(this);
                }
                return null;
            } catch (SQLException e) {
                throw new SQLException();
            } finally {
                cm.closeConnection();
            }
        }

    }
}
