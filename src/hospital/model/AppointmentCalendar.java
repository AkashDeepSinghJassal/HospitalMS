package hospital.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	LinkedHashMap<LocalDateTime, String> appointments = null;
	LinkedHashMap<LocalDateTime, String> ids = null;

	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}

	public LinkedHashMap<LocalDateTime, String> getAppointments() {
		return appointments;
	}

	public void setAppointments(LinkedHashMap<LocalDateTime, String> appointments) {
		this.appointments = appointments;
	}

	public LinkedHashMap<LocalDateTime, String> getIds() {
		return this.ids;
	}

	public void setIds(LinkedHashMap<LocalDateTime, String> ids) {
		this.ids = ids;
	}

	public AppointmentCalendar(String doctorID, LinkedHashMap<LocalDateTime, String> appointments,
			LinkedHashMap<LocalDateTime, String> ids) {
		this.doctorID = new SimpleStringProperty(doctorID);
		this.appointments = appointments;
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "{" + " doctorID='" + getDoctorID() + "'" + ", appointments='" + getAppointments() + "'" + ", ids='"
				+ getIds() + "'" + "}";
	}

}