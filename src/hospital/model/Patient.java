package hospital.model;

public class Patient extends Person {
	private String id;
	
	public Patient(){
		
	}
	
	public Patient(String id, String name, int age,GENDER gender, String address, String contact) {
		super(name, age,gender, address, contact);
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Patient [getId()=" + getId() + ", getName()=" + getName() + ", getAge()=" + getAge() + ", getAddress()="
				+ getAddress() + ", getContact()=" + getContact() + "]";
	}
	
}
