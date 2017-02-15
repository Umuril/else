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

/**
 * Classe contenente principalmente metodi statici di supporto
 * 
 * @author eddy
 */
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
	
	/**
	 * Metodo che dato un file ne calcola il checksum
	 * 
	 * @param path
	 *            percorso del file
	 * @return checksum
	 */
	public static String getMD5Checksum(final Path path) {
		final StringBuilder sb = new StringBuilder();
		for (final byte e : Utils.createChecksum(path))
			sb.append(Integer.toString((e & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	/**
	 * Metodo che restituisce le preferenze salvare, se presenti, altrimenti quelle di default
	 * 
	 * @param key
	 *            nome della preferenza da cercare
	 * @return stringa rappresentante la risorsa richiesta
	 */
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
	
	/**
	 * Metodo che stampa su console secondo certi parametri
	 * 
	 * @param debug
	 *            livello di richiesto per la stampa
	 * @param o
	 *            oggetto da stampare
	 */
	public static void log(final Debug debug, final Object o) {
		if ((Utils.debugmask >> debug.ordinal() & 1) != 0)
			System.out.println(o.toString());
	}
	
	/**
	 * Metodo che cancella le preferenze salvate finora
	 * 
	 * @throws BackingStoreException
	 *             errore nel lettura delle preferenze
	 */
	public static void resetPreferences() throws BackingStoreException {
		Utils.prefs.clear();
	}
	
	/**
	 * Metodo che aggiuge una nuova preferenza
	 * 
	 * @param key
	 *            nome della preferenza da salvare
	 * @param value
	 *            valore
	 */
	public static void setPreferences(final String key, final String value) {
		Utils.prefs.put(key, value);
	}
	
	/**
	 * Metodo che controlla che nessuna delle stringhe sia nulla o vuota
	 * 
	 * @param strings
	 *            stringhe da controllare
	 * @return vero solo se tutte le stringhe sono valide
	 */
	public static boolean validString(final String... strings) {
		for (final String string : strings)
			if (string == null || string.trim().isEmpty())
				return false;
		return true;
	}
	
	/**
	 * Metodo che controlla se un anno è valido (non nullo e diverso da zero)
	 * 
	 * @param year
	 *            anno da controllare
	 * @return boolean se l'anno è valido
	 */
	public static boolean validYear(final Year year) {
		return year != null && !year.equals(Year.of(0));
	}
	
	/**
	 * Insieme di valori validi per il debug
	 * 
	 * @author eddy
	 */
	public enum Debug {
		DEBUG, ERROR, INFO, WARNING
	}
}