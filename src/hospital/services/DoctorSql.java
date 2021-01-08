package hospital.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hospital.model.Doctor;
import hospital.model.GENDER;
import hospital.model.GenerateGender;
import hospital.ui.main.Main;

public class DoctorSql {

	/**
	 * Generate a new Doctor from the given ResultSet
	 * 
	 * @param resultSet a single ResultSet
	 * @return the new Doctor
	 */
	public static Doctor generateDoctor(ResultSet resultSet) {
		Doctor doctor = null;
		try {
			String id = resultSet.getString(1);
			String name = resultSet.getString(2);
			int age = resultSet.getInt(3);
			GENDER gender = GenerateGender.generateGender(resultSet.getString(4));
			String speciality = resultSet.getString(5);
			String contact = resultSet.getString(6);
			String address = resultSet.getString(7);
			doctor = new Doctor(id, name, age, gender, speciality, contact, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctor;
	}

	/**
	 * Adds the given doctor to the database.
	 * 
	 * @param doctor the doctor to add to the database
	 * @return the number of rows affected. Should be equal to 1
	 */
	public static int addDoctor(Doctor doctor) {
		try {
			PreparedStatement statement = Main.conn.prepareStatement("INSERT INTO doctor VALUES (?,?,?,?,?,?,?)");
			statement.setString(1, "");
			statement.setString(2, doctor.getName());
			statement.setInt(3, doctor.getAge());
			statement.setString(4, doctor.getGender().toString());
			statement.setString(5, doctor.getSpeciality());
			statement.setString(6, doctor.getContact());
			statement.setString(7, doctor.getAddress());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Deletes the doctor with the given id from the database.
	 * 
	 * @param id The id of the doctor to be deleted
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int removeDoctor(String id) {
		try {
			PreparedStatement statement = Main.conn.prepareStatement("DELETE FROM doctor WHERE id = ?");
			statement.setString(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Updates the given doctor according to the id.
	 * 
	 * @param doctor Edited doctor
	 * @return Returns the number of rows affected. Should be equal to 1
	 */
	public static int updateDoctor(Doctor doctor) {
		try {
			PreparedStatement statement = Main.conn.prepareStatement(
					"UPDATE doctor SET name = ?, age = ?, gender = ?, speciality = ?, contact = ?, address = ? WHERE id = ?");
			statement.setString(1, doctor.getName());
			statement.setInt(2, doctor.getAge());
			statement.setString(3, doctor.getGender().toString());
			statement.setString(4, doctor.getSpeciality());
			statement.setString(5, doctor.getContact());
			statement.setString(6, doctor.getAddress());
			statement.setString(7, doctor.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Returns the ID of the last inserted doctor
	 * 
	 * @return String containing the id of the last inserted doctor
	 */
	public static String getIdOfLastDoctor() {
		String id = "";
		try {
			PreparedStatement statement = Main.conn
					.prepareStatement("SELECT id FROM doctor ORDER BY id DESC LIMIT 1;");
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
				id = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}