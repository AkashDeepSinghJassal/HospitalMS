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

	public static ArrayList<Appointment> getAppointments(){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			PreparedStatement ps = conn.prepareStatement("select appoint_id, patient_id, doctor_id, date_scheduled from appointment");
			ResultSet rs = ps.executeQuery();
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
		try {
			PreparedStatement ps = conn.prepareStatement("insert into appointment values(?,?,?,?)");
			ps.setString(1, appointment.getAppointID());
			ps.setString(2, appointment.getPatientID());
			ps.setString(3, appointment.getDoctorID());

			ps.setString(4, "2010-04-30 07:27:39");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Returns the generated ID of appointment 
	 * 
	 * @return String  id of the appointment
	 */
	public static String generateAppointID() {
		try {
			PreparedStatement ps = conn.prepareStatement("insert into appointment_seq values(null)");
			int ex = ps.executeUpdate();
			if (ex > 0) {
				PreparedStatement p = conn.prepareStatement("select last_insert_id()");
				ResultSet rSet = p.executeQuery();
				if (rSet.next()) {
					int lastInsert = rSet.getInt(1);
					String prefix = "AP";
					int repeatCount = 5 - (int) (Math.log10((double) lastInsert) + 1);
					prefix = prefix + "0".repeat(repeatCount) + lastInsert;
					return prefix;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		try {
			PreparedStatement ps = conn.prepareStatement("delete from appointment where appoint_id = ?");
			ps.setString(1, appointment.getAppointID());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
		try {
			PreparedStatement ps = conn.prepareStatement("update appointment set patient_id = ?, doctor_id = ?, date_scheduled = ? where appoint_id = ?");
			ps.setString(1, appointment.getPatientID());
			ps.setString(2,  appointment.getDoctorID());
			ps.setString(3, "2020-01-01 16:00:00");
			ps.setString(4, appointment.getAppointID());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
