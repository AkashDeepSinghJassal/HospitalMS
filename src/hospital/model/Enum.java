package hospital.model;

enum GENDER {
	M, F, O;

	@Override
	public String toString() {
		String gender = "";
		switch (this) {
		case M:
			gender = "M";
			break;
		case F:
			gender = "F";
			break;
		case O:
			gender = "O";
			break;

		default:
			gender = null;
			break;
		}
		return gender;
	}
}

enum PAT_STATUS {
	
}
