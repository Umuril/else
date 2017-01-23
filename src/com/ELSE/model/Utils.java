package com.ELSE.model;

import java.awt.Color;
import java.io.File;
import java.util.prefs.Preferences;

public class Utils {
	private static int debugmask = 3;

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
		File theDir = new File(getPreferences("Folder"));
		if (!theDir.exists()) {
			try {
				theDir.mkdir();
			} catch (SecurityException se) {
				// TODO
			}
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
				return prefs.get("Pathbase", getPreferences("Folder") + File.separator + "db.txt");
			case "Preview":
				return prefs.get("Preview", "True");
			case "Folder":
				return prefs.get("Folder", System.getProperty("user.home") + File.separator + ".else");
			case "Save":
				return prefs.get("Save", "True");
			default:
				return "";
		}
	}

	public static void setPreferences(String key, String value) {
		prefs.put(key, value);
	}
}
