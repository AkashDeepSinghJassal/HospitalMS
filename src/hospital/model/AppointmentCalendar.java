package hospital.model;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {
	SimpleStringProperty doctorID;
	String[][][] appointments = new String[30][12][4];

	public String getDoctorID() {
		return this.doctorID.getValue();
	}

	public void setDoctorID(String doctorID) {
		this.doctorID.setValue(doctorID);
	}

	public SimpleStringProperty doctorIDProperty() {
		return this.doctorID;
	}

	public AppointmentCalendar() {
		doctorID = new SimpleStringProperty("doctorID");
		appointments = new String[30][12][4];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 12; j++) {
				for (int k = 0; k < 4; k++) {
					appointments[i][j][k] = "abc";
				}
			}
		}
	}

	public String[][][] getAppointments() {
		return appointments;
	}

	public void setAppointments(String[][][] appointments) {
		this.appointments = appointments;
	}
}