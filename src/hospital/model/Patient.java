package hospital.model;

import javafx.beans.property.SimpleStringProperty;

public class Patient extends Person {
	private SimpleStringProperty id = new SimpleStringProperty();

	public Patient() {

	}

	public Patient(String id, String name, int age, GENDER gender, String contact, String address) {
		super(name, age, gender, address, contact);
		this.id.set(id);
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

	@Override
	public String toString() {
		return "Patient [getId()=" + getId() + ", getName()=" + getName() + ", getAge()=" + getAge() + ", getAddress()="
				+ getAddress() + ", getContact()=" + getContact() + "]";
	}

}
