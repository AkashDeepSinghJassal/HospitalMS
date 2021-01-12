package hospital.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.model.Appointment;
import hospital.util.DBUtil;
import hospital.util.DateUtil;

public class AppointmentSql {
	static Connection conn = DBUtil.getDBConnection();

	public static ArrayList<Appointment> getAppointments() {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		PreparedStatement statement = null;
		try {
			statement = conn
					.prepareStatement("select id, patient_id, doctor_id, date_scheduled from appointment");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointID(rs.getString(1));
				appointment.setPatientID(rs.getString(2));
				appointment.setDoctorID(rs.getString(3));
				appointment.setDate(DateUtil.parse("01.01.2020"));
				appointments.add(appointment);
			}
			return appointments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			statement = conn.prepareStatement("insert into appointment values(?,?,?,?)");
			statement.setString(1, appointment.getID());
			statement.setString(2, appointment.getPatientID());
			statement.setString(3, appointment.getDoctorID());

			statement.setString(4, "2010-04-30 07:27:39");
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
	public static String generateAppointID() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.prepareStatement("insert into appointment_seq values(null)");
			int ex = statement.executeUpdate();
			if (ex > 0) {
				PreparedStatement p = conn.prepareStatement("select last_insert_id()");
				resultSet = p.executeQuery();
				if (resultSet.next()) {
					int lastInsert = resultSet.getInt(1);
					String prefix = "AP";
					int repeatCount = 5 - (int) (Math.log10((double) lastInsert) + 1);
					prefix = prefix + "0".repeat(repeatCount) + lastInsert;
					return prefix;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}
		return null;
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
			statement.setString(1, appointment.getID());
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
					"update appointment set patient_id = ?, doctor_id = ?, date_scheduled = ? where appoint_id = ?");
			statement.setString(1, appointment.getPatientID());
			statement.setString(2, appointment.getDoctorID());
			statement.setString(3, "2020-01-01 16:00:00");
			statement.setString(4, appointment.getID());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(statement);
		}
		return 0;
	}
}
