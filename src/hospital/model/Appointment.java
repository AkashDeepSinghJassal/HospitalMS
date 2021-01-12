package hospital.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
	private SimpleStringProperty appointID = new SimpleStringProperty();
	private SimpleStringProperty patientID = new SimpleStringProperty();
	private SimpleStringProperty doctorID = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

	public String getID() {
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
	public SimpleStringProperty appointIDProperty() {
		return appointID;
	}

	public String getPatientID() {
		return patientID.get();
	}

	public void setPatientID(String id) {
		patientID.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code patientID}
	 * 
	 * @return SimpleStringProperty of {@code patientID}
	 */
	public SimpleStringProperty patientIDProperty() {
		return patientID;
	}

	public String getDoctorID() {
		return doctorID.get();
	}

	public void setDoctorID(String id) {
		doctorID.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code doctorID}
	 * 
	 * @return SimpleStringProperty of {@code doctorID}
	 */
	public SimpleStringProperty doctorIDProperty() {
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
	public SimpleObjectProperty<LocalDate> dateProperty() {
		return date;
	}
}
