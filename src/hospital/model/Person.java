package hospital.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
	private SimpleObjectProperty<GENDER> gender = new SimpleObjectProperty<GENDER>();
	private SimpleStringProperty name = new SimpleStringProperty(), address = new SimpleStringProperty(),
			contact = new SimpleStringProperty();
	private SimpleIntegerProperty age = new SimpleIntegerProperty();

	public Person() {
	}

	public Person(String name, int age, GENDER gender, String address, String contact) {
		this.name.set(name);
		this.age.set(age);
		this.gender.set(gender);
		;
		this.address.set(address);
		this.contact.set(contact);
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public SimpleStringProperty nameProperty() {
		return this.name;
	}

	public int getAge() {
		return this.age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public SimpleIntegerProperty ageProperty() {
		return this.age;
	}

	public GENDER getGender() {
		return this.gender.get();
	}

	public void setGender(GENDER gender) {
		this.gender.set(gender);
		;
	}

	public SimpleObjectProperty<GENDER> genderProperty() {
		return this.gender;
	}

	public String getAddress() {
		return this.address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public SimpleStringProperty addressProperty() {
		return this.address;
	}

	public String getContact() {
		return this.contact.get();
	}

	public void setContact(String contact) {
		this.contact.set(contact);
	}

	public SimpleStringProperty contactProperty() {
		return this.contact;
	}

	@Override
	public String toString() {
		return "Person [name=" + getName() + ", address=" + getAddress() + ", age=" + getAge() + ", contact="
				+ getContact() + ", gender=" + gender + "]";
	}

}
