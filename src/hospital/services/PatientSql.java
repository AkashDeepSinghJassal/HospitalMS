package hospital.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.model.GENDER;
import hospital.model.GenerateGender;
import hospital.model.Patient;
import hospital.util.DBUtil;

public class PatientSql {

	/**
	 * Get all paients from the database.
	 * 
	 * @return a {@link ArrayList} of type {@link Patient} containing all patients
	 *         from the database
	 */
	public static ArrayList<Patient> getPatients() {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		try {
			PreparedStatement statement = DBUtil.getDBConnection()
					.prepareStatement("select id, name, age, gender, contact, address from patient");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				patients.add(generatePatient(resultSet));
			}
			return patients;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a new Patient from the given ResultSet
	 * 
	 * @param resultSet a single {@link ResultSet}
	 * @return the new Patient
	 */
	public static Patient generatePatient(ResultSet resultSet) {
		Patient patient = null;
		try {
			String id = resultSet.getString(1);
			String name = resultSet.getString(2);
			int age = resultSet.getInt(3);
			GENDER gender = GenerateGender.generateGender(resultSet.getString(4));
			String contact = resultSet.getString(5);
			String address = resultSet.getString(6);
			patient = new Patient(id, name, age, gender, contact, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	/**
	 * Adds the given patient to the database.
	 * 
	 * @param patient the patient to add to the database
	 * @return the number of rows affected. Should be equal to 1
	 */
	public static int addPatient(Patient patient) {
		try {
			PreparedStatement statement = DBUtil.getDBConnection()
					.prepareStatement("INSERT INTO patient VALUES (?,?,?,?,?,?)");
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

	/**
	 * Deletes the patient with the given id from the database.
	 * 
	 * @param id The id of the patient to be deleted
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int removePatient(String id) {
		try {
			PreparedStatement statement = DBUtil.getDBConnection().prepareStatement("DELETE FROM patient WHERE id = ?");
			statement.setString(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Updates the given {@link Patient} according to the id.
	 * 
	 * @param patient Edited {@link Patient}
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int updatePatient(Patient patient) {
		try {
			PreparedStatement statement = DBUtil.getDBConnection().prepareStatement(
					"UPDATE patient SET name = ?, age = ?, gender = ?, contact = ?, address = ? WHERE id = ?");
			statement.setString(1, patient.getName());
			statement.setInt(2, patient.getAge());
			statement.setString(3, patient.getGender().toString());
			statement.setString(4, patient.getContact());
			statement.setString(5, patient.getAddress());
			statement.setString(6, patient.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Returns the ID of the last inserted patient
	 * 
	 * @return String containing the id of the last inserted patient
	 */
	public static String getIdOfLastPatient() {
		String id = "";
		try {
			PreparedStatement statement = DBUtil.getDBConnection()
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
