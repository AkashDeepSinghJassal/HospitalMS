package hospital.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hospital.model.GENDER;
import hospital.model.GenerateGender;
import hospital.model.Patient;
import hospital.ui.main.Main;

public class PatientSql {

	/**
	 * Generate a new Patient from the given ResultSet
	 * 
	 * @param resultSet a single ResultSet
	 * @return the new Patient
	 */
	public static Patient generatePatient(ResultSet resultSet) {
		Patient patient = null;
		try {
			String id = resultSet.getString(1);
			String name = resultSet.getString(2);
			int age = resultSet.getInt(3);
			GENDER gender = GenerateGender.generateGender(resultSet.getString(4));
			String address = resultSet.getString(5);
			String contact = resultSet.getString(6);
			patient = new Patient(id, name, age, gender, contact, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	public static int addPatient(Patient patient) {
		try {
			PreparedStatement statement = Main.conn.prepareStatement("INSERT INTO patient VALUES (?,?,?,?,?,?)");
			statement.setString(1, "");
			statement.setString(2, patient.getName());
			statement.setInt(3, patient.getAge());
			statement.setString(4, patient.getGender().toString());
			statement.setString(5, patient.getContact());
			statement.setString(6, patient.getAddress());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int removePatient(String id) {
		try {
			PreparedStatement statement = Main.conn.prepareStatement("DELETE FROM patient WHERE id = ?");
			statement.setString(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getIdOfLastPatient() {
		String id = "";
		try {
			PreparedStatement statement = Main.conn
					.prepareStatement("SELECT id FROM patient ORDER BY id DESC LIMIT 1;");
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
				id = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
