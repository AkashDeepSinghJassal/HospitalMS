package hospital.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class RDSUtil {
	static String getRdsCredential() {
		RDSCredential rdsCredential;
		try {
			FileInputStream fis = new FileInputStream("rdscredential.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			rdsCredential = (RDSCredential) ois.readObject();
			fis.close();
			ois.close();
			return rdsCredential.getCredentials();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
