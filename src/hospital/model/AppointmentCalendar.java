package hospital.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	LinkedHashMap<LocalDateTime, String> appointments = null;

	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}

	public AppointmentCalendar(String doctorID, LinkedHashMap<LocalDateTime, String> appointment) {
		this.doctorID = new SimpleStringProperty(doctorID);
		this.appointments = appointment;
	}

	public LinkedHashMap<LocalDateTime, String> getAppointments() {
		return appointments;
	}

	public void setAppointments(LinkedHashMap<LocalDateTime, String> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return "{" + " doctorID='" + getDoctorID() + "'" + ", appointments='" + getAppointments() + "'" + "}";
	}

}