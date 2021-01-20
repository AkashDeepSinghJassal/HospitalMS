package hospital.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	// String[][][] appointments = new String[30][12][4];
	ArrayList appointments = null;
	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}


	public AppointmentCalendar(ArrayList appointment) {
		doctorID = new SimpleStringProperty("doctorID");
		appointments = appointment;
	}

	public ArrayList<String> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<String> appointments) {
		this.appointments = appointments;
	}
}