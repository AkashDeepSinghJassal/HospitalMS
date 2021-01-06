package hospital.model;

import javafx.beans.property.SimpleStringProperty;

public class Doctor extends Person {
	private SimpleStringProperty id = new SimpleStringProperty(), speciality = new SimpleStringProperty();

	public Doctor() {

	}

	public Doctor(String id, String name, int age, GENDER gender, String address, String contact, String speciality) {
		super(name, age, gender, address, contact);
		this.id.set(id);
		this.speciality.set(speciality);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public SimpleStringProperty idProperty() {
		return id;
	}

	public String getSpeciality() {
		return speciality.get();
	}

	public void setSpeciality(String speciality) {
		this.speciality.set(speciality);
	}

	public SimpleStringProperty specialityProperty() {
		return speciality;
	}

	@Override
	public String toString() {
		return "Doctor [getId()=" + getId() + ", getSpeciality()=" + getSpeciality() + ", getName()=" + getName()
				+ ", getAge()=" + getAge() + ", getAddress()=" + getAddress() + ", getContact()=" + getContact() + "]";
	}
}
