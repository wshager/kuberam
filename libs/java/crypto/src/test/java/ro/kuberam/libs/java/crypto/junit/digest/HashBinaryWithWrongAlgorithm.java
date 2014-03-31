package ro.kuberam.libs.java.crypto.junit.digest;

import java.io.InputStream;

import ro.kuberam.libs.java.crypto.ErrorMessages;
import ro.kuberam.libs.java.crypto.digest.Hash;
import org.junit.Assert;
import org.junit.Test;

import ro.kuberam.tests.junit.BaseTest;

public class HashBinaryWithWrongAlgorithm extends BaseTest {

	@Test
	public void hashBinaryWithWrongAlgorithm() throws Exception {
		InputStream input = getClass().getResourceAsStream("../../resources/keystore");
		
		try {
			String result = Hash.hashBinary(input, "SHA-17", "SUN");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getLocalizedMessage().equals(ErrorMessages.err_CX21));
		}
	}
}
