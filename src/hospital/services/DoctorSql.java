package hospital.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.model.Doctor;
import hospital.model.GENDER;
import hospital.model.GenerateGender;
import hospital.util.DBUtil;

public class DoctorSql {

	/**
	 * Get the {@link Doctor} from the given {@link Doctor#id}
	 * 
	 * @param id The id of the doctor you want to get
	 * @return The {@link Doctor} associated with the given id from the database.
	 */
	public static Doctor getDoctor(String id) {
		Doctor doctor = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		if (id != null && !id.equals("")) {
			try {
				statement = DBUtil.getDBConnection().prepareStatement(
						"select id, name, age, gender, speciality, contact, address from doctor where id = ?");
				statement.setString(1, id);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					doctor = generateDoctor(resultSet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(statement);
			}
		}
		return doctor;
	}

	/**
	 * Get all paients from the database.
	 * 
	 * @return a {@link ArrayList} of type {@link Doctor} containing all doctors
	 *         from the database
	 */
	public static ArrayList<Doctor> getDoctors() {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBUtil.getDBConnection()
					.prepareStatement("select id, name, age, gender, speciality, contact, address from doctor");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				doctors.add(generateDoctor(resultSet));
			}
			return doctors;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}

		return null;
	}

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
		PreparedStatement statement = null;
		try {
			statement = DBUtil.getDBConnection().prepareStatement("INSERT INTO doctor VALUES (?,?,?,?,?,?,?)");
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
		} finally {
			DBUtil.closeQuietly(statement);
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
		PreparedStatement statement = null;
		try {
			statement = DBUtil.getDBConnection().prepareStatement("DELETE FROM doctor WHERE id = ?");
			statement.setString(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(statement);
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
		PreparedStatement statement = null;
		try {
			statement = DBUtil.getDBConnection().prepareStatement(
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
		} finally {
			DBUtil.closeQuietly(statement);
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
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBUtil.getDBConnection().prepareStatement("SELECT id FROM doctor ORDER BY id DESC LIMIT 1;");
			resultSet = statement.executeQuery();
			if (resultSet.next())
				id = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeQuietly(resultSet);
			DBUtil.closeQuietly(statement);
		}
		return id;
	}
}
