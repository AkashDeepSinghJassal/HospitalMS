package hospital.model;

public class GenerateGender {
	public static GENDER generateGender(String gender) {
		if (gender.toUpperCase().equals("M")) 
			return GENDER.M;
		if (gender.toUpperCase().equals("F")) 
			return GENDER.F;
		return GENDER.O;
	}
}