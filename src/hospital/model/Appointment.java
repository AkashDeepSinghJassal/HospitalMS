package hospital.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
	private SimpleStringProperty appointID;
	private SimpleStringProperty patientID;
	private SimpleStringProperty doctorID;
	private SimpleObjectProperty<LocalDate> date;

	public String getAppointID() {
		return appointID.get();
	}

	public void setAppointID(String id) {
		appointID.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code appointID}
	 * 
	 * @return SimpleStringProperty of {@code appointID}
	 */
	public SimpleStringProperty getAppointIDProperty() {
		return appointID;
	}

	public String getPatientID() {
		return appointID.get();
	}

	public void setPatientID(String id) {
		appointID.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code patientID}
	 * 
	 * @return SimpleStringProperty of {@code patientID}
	 */
	public SimpleStringProperty getPatientIDProperty() {
		return patientID;
	}

	public String getDoctorID() {
		return appointID.get();
	}

	public void setDoctorID(String id) {
		appointID.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code doctorID}
	 * 
	 * @return SimpleStringProperty of {@code doctorID}
	 */
	public SimpleStringProperty getDoctorIDProperty() {
		return doctorID;
	}

	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate localDate) {
		date.set(localDate);
	}

	/**
	 * Function returns Simple Object Property of {@code localDate}
	 * 
	 * @return SimpleObjectProperty of {@code localDate}
	 */
	public SimpleObjectProperty<LocalDate> getDateProperty() {
		return date;
	}
}
