package hospital.model;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	ObservableList<LocalDateTime> appointments = null;

	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}

	public AppointmentCalendar(String doctorID, ObservableList<LocalDateTime> appointment) {
		this.doctorID = new SimpleStringProperty(doctorID);
		appointments = appointment;
	}

	public ObservableList<LocalDateTime> getAppointments() {
		return appointments;
	}

	public void setAppointments(ObservableList<LocalDateTime> appointments) {
		this.appointments = appointments;
	}
}