package hospital.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	ObservableList<String> appointments = null;

	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}

	public AppointmentCalendar(ObservableList<String> appointment) {
		doctorID = new SimpleStringProperty("doctorID");
		appointments = appointment;
	}

	public ObservableList<String> getAppointments() {
		return appointments;
	}

	public void setAppointments(ObservableList<String> appointments) {
		this.appointments = appointments;
	}
}