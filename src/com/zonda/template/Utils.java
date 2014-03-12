package com.zonda.template;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	
	public static String md5(String sourceStr) {

		String encryptedStr = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte b[] = md.digest(sourceStr.getBytes());

			int i;

			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < b.length; offset++) {

				i = b[offset];

				if (i < 0) {

					i += 256;
				}
				if (i < 16) {

					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

			encryptedStr = buf.toString().substring(8, 24);

			System.out.println("32bit result: " + buf.toString());// 32位的加密

			System.out.println("16bit result: "
					+ buf.toString().substring(8, 24));// 16位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}// end of try-catch

		return encryptedStr;
	}// end of md5
}
