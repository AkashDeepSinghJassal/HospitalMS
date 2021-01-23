package hospital.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import hospital.model.AppointmentCalendar;
import hospital.util.DBUtil;
import hospital.util.DateTimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentCalendarSql {
	public static ObservableList<AppointmentCalendar> getAppointmentCalendars() {
		ObservableList<AppointmentCalendar> data = FXCollections.observableArrayList();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBUtil.getDBConnection().prepareStatement(
					"select doctor_id, patients, appointments, appointment_ids from appointment_calendar");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AppointmentCalendar calendar = null;
				LinkedHashMap<LocalDateTime, String> appointmentHashMap = new LinkedHashMap<LocalDateTime, String>();
				LinkedHashMap<LocalDateTime, String> idHashMap = new LinkedHashMap<LocalDateTime, String>();

				String doctorID = resultSet.getString(1);
				String patientString = resultSet.getString(2);
				if (patientString != null) {
					String[] patients = patientString.split(",");
					String[] appointments = resultSet.getString(3).split(",");
					String[] ids = resultSet.getString(4).split(",");

					for (int i = 0; i < patients.length; i++) {
						appointmentHashMap.put(DateTimeUtil.parse(appointments[i]), patients[i]);
						idHashMap.put(DateTimeUtil.parse(appointments[i]), ids[i]);
					}
				}
				calendar = new AppointmentCalendar(doctorID, appointmentHashMap, idHashMap);
				data.add(calendar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}
		return data;
	}
}
