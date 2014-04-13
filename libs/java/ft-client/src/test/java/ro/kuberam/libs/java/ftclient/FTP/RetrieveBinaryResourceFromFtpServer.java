package ro.kuberam.libs.java.ftclient.FTP;

import java.net.URI;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import ro.kuberam.libs.java.ftclient.Connect;
import ro.kuberam.libs.java.ftclient.Disconnect;
import ro.kuberam.libs.java.ftclient.FTClientAbstractTest;
import ro.kuberam.libs.java.ftclient.RetrieveResource;
import org.junit.Assert;
import org.junit.Test;

public class RetrieveBinaryResourceFromFtpServer extends FTClientAbstractTest {

	@Test
	public void retrieveBinaryResourceFromFtpServer() throws Exception {
		Properties connectionProperties = new Properties();
		connectionProperties.load(this.getClass().getResourceAsStream("../connection.properties"));

		FTPClient connection = Connect.connect(new URI(connectionProperties.getProperty("ftp-server-connection-url")), "");

		String actualResult = getTextContent(RetrieveResource.retrieveResource(connection, "/dir-with-rights/image-with-rights.gif"));

		Disconnect.disconnect(connection);

		String expectedResult = getBinaryResourceAsBase64String("../image-with-rights.gif");

		Assert.assertTrue(expectedResult.equals(actualResult));
	}
}