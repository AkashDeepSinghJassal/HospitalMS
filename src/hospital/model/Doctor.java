package hospital.model;

public class Doctor extends Person {
	private String id, speciality;
	
	public Doctor(){
		
	}
	
	public Doctor(String id, String name, int age, String address, String contact, String speciality) {
		super(name, age, address, contact);
		this.id = id;
		this.speciality = speciality;
	}

	public String getId() {
		return id;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Override
	public String toString() {
		return "Doctor [getId()=" + getId() + ", getSpeciality()=" + getSpeciality() + ", getName()=" + getName()
				+ ", getAge()=" + getAge() + ", getAddress()=" + getAddress() + ", getContact()=" + getContact() + "]";
	}
}
