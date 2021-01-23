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
					"SELECT doctor.id as doctor_id, group_concat(patient_id) patients, group_concat(date_scheduled) appointments from doctor left join appointment on doctor.id = appointment.doctor_id group by doctor.id");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AppointmentCalendar calendar = null;
				LinkedHashMap<LocalDateTime, String> hashMap = new LinkedHashMap<LocalDateTime, String>();
				String doctorID = resultSet.getString(1);
				String patientString = resultSet.getString(2);
				if (patientString != null) {
					String[] patients = patientString.split(",");
					String[] appointments = resultSet.getString(3).split(",");

					for (int i = 0; i < patients.length; i++) {
						hashMap.put(DateTimeUtil.parse(appointments[i]), patients[i]);
					}
				}
				calendar = new AppointmentCalendar(doctorID, hashMap);
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
