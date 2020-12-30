package hospital.model;

public class User {
	private String name, password, age, address, contactNO;

	public User() {
	}

	public User(String name, String password, String age, String address, String contactNO) {
		this.name = name;
		this.password = password;
		this.age = age;
		this.address = address;
		this.contactNO = contactNO;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNO() {
		return this.contactNO;
	}

	public void setContactNO(String contactNO) {
		this.contactNO = contactNO;
	}

	@Override
	public String toString() {
		return "{" +
			" name='" + getName() + "'" +
			", password='" + getPassword() + "'" +
			", age='" + getAge() + "'" +
			", address='" + getAddress() + "'" +
			", contactNO='" + getContactNO() + "'" +
			"}";
	}

}
