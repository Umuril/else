package com.ELSE.model;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Utils {
	private static int debugmask = 0;

	public enum Debug {
		ERROR, WARNING, INFO, DEBUG
	};

	public static boolean checkString(final String string) {
		return string != null && !string.isEmpty() && !string.trim().isEmpty();
	}

	public static void log(Debug debug, Object o) {
		if (((debugmask >> debug.ordinal()) & 1) != 0)
			System.out.println(o.toString());
	}

	private static Preferences prefs;
	// Debug purposes
	/*
	 * static { try { prefs.clear(); } catch (BackingStoreException e) { } }
	 */
	static {
		prefs = Preferences.userRoot().node("ELSE");
		Path theDir = Paths.get(getPreferences("Folder"));
		if (Files.notExists(theDir))
			try {
				Files.createDirectory(theDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static String getPreferences(String key) {
		switch (key) {
			case "Color1":
				return prefs.get("Color1", Integer.toString(Color.decode("#e2dcc5").getRGB()));
			case "Color2":
				return prefs.get("Color2", Integer.toString(Color.decode("#cbc4a7").getRGB()));
			case "BackColor":
				return prefs.get("BackColor", Integer.toString(Color.WHITE.getRGB()));
			case "Pathbase":
				return prefs.get("Pathbase", getPreferences("Folder") + FileSystems.getDefault().getSeparator() + "db.txt");
			case "Preview":
				return prefs.get("Preview", "True");
			case "Folder":
				return prefs.get("Folder", System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + ".else");
			case "Save":
				return prefs.get("Save", "True");
			default:
				return "";
		}
	}

	public static void setPreferences(String key, String value) {
		prefs.put(key, value);
	}

	public static void resetPreferences() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			// TODO Catch vs throws
			e.printStackTrace();
		}
	}

	public static String getMD5Checksum(String filename) {
		byte[] b = createChecksum(filename);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	private static byte[] createChecksum(String filename) {
		MessageDigest complete = null;
		try (InputStream fis = new FileInputStream(filename)) {
			byte[] buffer = new byte[1024];
			complete = MessageDigest.getInstance("MD5");
			int numRead;
			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (complete != null)
			return complete.digest();
		return new byte[0];
	}
}
