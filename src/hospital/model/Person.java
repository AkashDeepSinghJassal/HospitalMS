package hospital.model;

public class Person {
	private String name, address, contact;
	private GENDER gender;
	private int age;

	public Person() {
	}

	public Person(String name, int age,GENDER gender, String address, String contact) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.contact = contact;
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}
	
	public GENDER getGender() {
		return this.gender;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", address=" + address + ", age=" + age + ", contact=" + contact+ ", gender=" + gender + "]";
	}



}
