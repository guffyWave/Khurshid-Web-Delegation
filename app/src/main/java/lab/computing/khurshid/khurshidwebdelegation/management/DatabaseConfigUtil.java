package lab.computing.khurshid.khurshidwebdelegation.management;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	public static void main(String[] arg) throws SQLException, IOException {

		Class<WebRequest>[] classes = new Class[] { WebRequest.class };
		writeConfigFile("ormlite_config.txt");

	}
}
