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

public class GetResourceMetadataFromFtpServer extends FTClientAbstractTest {

	@Test
	public void getResourceMetadataFromFtpServer() throws Exception {
		
		System.out.println(connectionProperties.getProperty("ftp-server-connection-url"));

		FTPClient connection = Connect.connect(new URI(connectionProperties.getProperty("ftp-server-connection-url")), "");

		String actualResult = getTextContent(RetrieveResource.retrieveResource(connection, "/dir-with-rights/test.txt"));
		
		System.out.println(actualResult);

		Disconnect.disconnect(connection);

		String expectedResult = getBinaryResourceAsBase64String("../resources/test.txt");
		
		System.out.println(expectedResult);

		Assert.assertTrue(expectedResult.equals(actualResult));
	}
}
