package hospital.model;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
	private SimpleStringProperty id = new SimpleStringProperty();
	private SimpleStringProperty patientID = new SimpleStringProperty();
	private SimpleStringProperty doctorID = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDateTime> date = new SimpleObjectProperty<LocalDateTime>();

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	/**
	 * Function returns Simple String Property of {@code id}
	 * 
	 * @return SimpleStringProperty of {@code id}
	 */
	public SimpleStringProperty idProperty() {
		return id;
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

	public LocalDateTime getDate() {
		return date.get();
	}

	public void setDate(LocalDateTime localDateTime) {
		date.set(localDateTime);
	}

	/**
	 * Function returns Simple Object Property of {@code LocalDateTime}
	 * 
	 * @return SimpleObjectProperty of {@code LocalDateTime}
	 */
	public SimpleObjectProperty<LocalDateTime> dateProperty() {
		return date;
	}
}
