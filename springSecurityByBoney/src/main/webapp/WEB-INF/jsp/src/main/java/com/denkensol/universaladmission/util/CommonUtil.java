/*
 * 
 */
package com.denkensol.universaladmission.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.denkensol.universaladmission.constant.ApplicationConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonUtil.
 */
@SuppressWarnings("restriction")
public class CommonUtil {

	/**
	 * Generate salt.
	 *
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String generateSalt(final int length) {
		final Random rng = new Random();
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwzyz";
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	/**
	 * Salt password.
	 *
	 * @param password
	 *            the password
	 * @param salt
	 *            the salt
	 * @return the string
	 */
	public static String saltPassword(final String password, final String salt) {
		return salt.substring(0, 16) + password + salt.substring(16, 32);
	}

	/**
	 * Hash password.
	 *
	 * @param password
	 *            the password
	 * @return the string
	 */
	public static String hashPassword(final String password) {

		final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] input = null;
		try {
			input = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(input);
		final byte[] output = digest.digest();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < output.length; i++) {
			buffer.append(hexDigits[(0xf0 & output[i]) >> 4]);
			buffer.append(hexDigits[0x0f & output[i]]);
		}
		return buffer.toString();

	}

	/**
	 * Gets the current gmt timestamp.
	 *
	 * @return the current gmt timestamp
	 */
	public static Timestamp getCurrentGMTTimestamp() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIMESTAMP_FORMAT);
		final ZoneId inputZone = ZoneId.of(ApplicationConstants.ZONE_ID_UTC);
		final LocalDateTime date = LocalDateTime.now(inputZone);
		return Timestamp.valueOf(date.format(formatter).toString());
	}

	public static boolean isListNotNullAndNotEmpty(List<?> inputList) {
		if (inputList != null && !inputList.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isSetNotNullAndNotEmpty(Set<?> inputSet) {
		if (inputSet != null && !inputSet.isEmpty()) {
			return true;
		}
		return false;
	}

	public static Workbook getWorkBookFromFile(String inputFilePath) {
		InputStream fs = null;
		try {
			fs = new FileInputStream(inputFilePath);
		} catch (FileNotFoundException e1) {
		}
		Workbook workbook = null;
		if (FilenameUtils.getExtension(inputFilePath)
				.equalsIgnoreCase(ApplicationConstants.EXCEL_SHEET_TYPE_XLS_EXTENSION)) {
			try {
				workbook = new HSSFWorkbook(fs);
			} catch (IOException e) {
			}
		} else if (FilenameUtils.getExtension(inputFilePath)
				.equalsIgnoreCase(ApplicationConstants.EXCEL_SHEET_TYPE_XLSX_EXTENSION)) {
			try {
				workbook = new XSSFWorkbook(fs);
			} catch (IOException e) {

			}
		}
		return workbook;
	}

	public static String getExtension(String inputFilePath) {
		String fileExtension = "";
		if (inputFilePath != null && !inputFilePath.isEmpty()) {
			fileExtension = FilenameUtils.getExtension(inputFilePath);
		}
		return fileExtension;
	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		int len = searchStr.length();
		int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (str.regionMatches(true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}
	public static String getFileNameFromURL(String url) {
		String fileName = "";
		if (url != null && !url.isEmpty()) {

			String baseName = FilenameUtils.getBaseName(url);
			String extension = FilenameUtils.getExtension(url);

			fileName = baseName + "." + extension;
		}
		return fileName;
	}

	public static Workbook createWorkBook(String fileName) {

		Workbook workbook = null;
		if (FilenameUtils.getExtension(fileName).equalsIgnoreCase("xls")) {
			workbook = new HSSFWorkbook();
		} else if (FilenameUtils.getExtension(fileName).equalsIgnoreCase("xlsx")) {
			workbook = new XSSFWorkbook();

		}
		return workbook;
	}

}
