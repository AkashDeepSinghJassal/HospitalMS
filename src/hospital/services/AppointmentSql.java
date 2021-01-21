package hospital.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.model.Appointment;
import hospital.util.DBUtil;
import hospital.util.DateTimeUtil;

public class AppointmentSql {
	static Connection conn = DBUtil.getDBConnection();

	public static ArrayList<Appointment> getAppointments() {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.prepareStatement("select id, patient_id, doctor_id, date_scheduled from appointment");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Appointment appointment = new Appointment();
				appointment.setId(resultSet.getString(1));
				appointment.setPatientID(resultSet.getString(2));
				appointment.setDoctorID(resultSet.getString(3));
				appointment.setDate(DateTimeUtil.parse(resultSet.getString(4)));
				appointments.add(appointment);
			}
			return appointments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}

		return null;
	}

	/**
	 * Adds the given appointment to the database.
	 * 
	 * @param appointment the appointment to add to the database
	 * @return the number of rows affected. Should be equal to 1
	 */
	public static int addAppointment(Appointment appointment) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"insert into appointment (id, patient_id, doctor_id, date_scheduled) values(?,?,?,?)");
			statement.setString(1, appointment.getId());
			statement.setString(2, appointment.getPatientID());
			statement.setString(3, appointment.getDoctorID());
			statement.setString(4, DateTimeUtil.format(appointment.getDate()));
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(statement);
		}
		return -1;
	}

	/**
	 * Returns the generated ID of appointment
	 * 
	 * @return String id of the appointment
	 */
	public static String getIdOfLastAppointment() {
		String id = "";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBUtil.getDBConnection()
					.prepareStatement("SELECT id FROM appointment ORDER BY id DESC LIMIT 1;");
			resultSet = statement.executeQuery();
			if (resultSet.next())
				id = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}
		return id;
	}

	/**
	 * Deletes the appointment with the given id from the database.
	 * 
	 * @param id The id of the appointment to be deleted
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int removeAppointment(Appointment appointment) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("delete from appointment where id = ?");
			statement.setString(1, appointment.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(statement);
		}

		return 0;
	}

	/**
	 * Updates the given appointment according to the id.
	 * 
	 * @param appointment Edited appointment
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int updateAppointment(Appointment appointment) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(
					"update appointment set patient_id = ?, doctor_id = ?, date_scheduled = ? where id = ?");
			statement.setString(1, appointment.getPatientID());
			statement.setString(2, appointment.getDoctorID());
			statement.setString(3, DateTimeUtil.format(appointment.getDate()));
			statement.setString(4, appointment.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(statement);
		}
		return 0;
	}
}
