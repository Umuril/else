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
import java.time.Year;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Utils {
	private static int debugmask = 0;
	private static Preferences prefs;
	// Debug purposes
	/*
	 * static { try { prefs.clear(); } catch (BackingStoreException ex) { } }
	 */
	static {
		Utils.prefs = Preferences.userRoot().node("ELSE");
		final Path folder = Paths.get(Utils.getPreferences("Folder"));
		if (Files.notExists(folder))
			try {
				Files.createDirectory(folder);
			} catch (final IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
	}
	
	private static byte[] createChecksum(final Path path) {
		MessageDigest digest = null;
		try (InputStream is = new FileInputStream(path.toFile())) {
			final byte[] buffer = new byte[1024];
			digest = MessageDigest.getInstance("MD5");
			int numRead;
			do {
				numRead = is.read(buffer);
				if (numRead > 0)
					digest.update(buffer, 0, numRead);
			} while (numRead != -1);
		} catch (final FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (final NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		if (digest != null)
			return digest.digest();
		return new byte[0];
	}
	
	public static String getMD5Checksum(final Path path) {
		final StringBuilder sb = new StringBuilder();
		for (final byte e : Utils.createChecksum(path))
			sb.append(Integer.toString((e & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	public static String getPreferences(final String key) {
		switch (key) {
			case "Color1":
				return Utils.prefs.get("Color1", Integer.toString(Color.decode("#e2dcc5").getRGB()));
			case "Color2":
				return Utils.prefs.get("Color2", Integer.toString(Color.decode("#cbc4a7").getRGB()));
			case "BackColor":
				return Utils.prefs.get("BackColor", Integer.toString(Color.WHITE.getRGB()));
			case "PathTree":
				return Utils.prefs.get("PathTree", Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + "db.txt");
			case "Preview":
				return Utils.prefs.get("Preview", "True");
			case "Folder":
				return Utils.prefs.get("Folder", System.getProperty("user.home") + FileSystems.getDefault().getSeparator() + ".else");
			case "Save":
				return Utils.prefs.get("Save", "True");
			default:
				return "";
		}
	}
	
	public static void log(final Debug debug, final Object o) {
		if ((Utils.debugmask >> debug.ordinal() & 1) != 0)
			System.out.println(o.toString());
	}
	
	public static void resetPreferences() throws BackingStoreException {
		Utils.prefs.clear();
	}
	
	public static void setPreferences(final String key, final String value) {
		Utils.prefs.put(key, value);
	}
	
	public static boolean validString(final String... strings) {
		for (final String string : strings)
			if (string == null || string.trim().isEmpty())
				return false;
		return true;
	}
	
	public static boolean validYear(final Year year) {
		return year != null && !year.equals(Year.of(0));
	}
	
	public enum Debug {
		DEBUG, ERROR, INFO, WARNING
	}
}