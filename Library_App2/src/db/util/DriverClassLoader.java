package db.util;

public class DriverClassLoader {
	public static boolean load(String driver) {
		boolean r = false;
//		try {
//			Class.forName(driver);
//		} catch (Exception e) {
//			System.out.printf("'%s' 로딩 실패sdf\n", driver);
//		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}
}
 