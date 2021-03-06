package ro.kuberam.libs.java.ftclient.SFTP;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import ro.kuberam.libs.java.ftclient.Connect;
import ro.kuberam.libs.java.ftclient.Disconnect;
import ro.kuberam.libs.java.ftclient.FTClientAbstractTest;
import ro.kuberam.libs.java.ftclient.ListResources;
import ro.kuberam.libs.java.ftclient.StoreResource;

import com.jcraft.jsch.Session;

public class StoreBinaryResourceTest extends FTClientAbstractTest {

	@Test
	public void listResourcesFromSftpServer() throws Exception {

		Session remoteConnection = initializeSftpConnection(
				connectionProperties.getProperty("sftp-server-connection-url"),
				IOUtils.toString(getClass().getResourceAsStream("../sftp-private.key")));

		String remoteResourcePath = sftpTmpDirPath + "/image-with-rights" + System.currentTimeMillis()
				+ ".gif";

		InputStream resourceInputStream = getClass().getResourceAsStream("../image-with-rights.gif");

		Boolean stored = StoreResource.storeResource(remoteConnection, remoteResourcePath,
				resourceInputStream);

		Disconnect.disconnect(remoteConnection);

		Assert.assertTrue(stored);

	}
}
