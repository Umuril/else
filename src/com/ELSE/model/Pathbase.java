package com.ELSE.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pathbase {
	private static class PathNode {
		private Map<String, PathNode> children = new HashMap<>();
		private final String name;

		private PathNode(String name) {
			this.name = name;
		}

		public void clear() {
			for (PathNode child : children.values()) {
				if (!child.isLeaf()) {
					child.clear();
					child.children.clear();
				}
			}
		}

		public boolean isLeaf() {
			return children.size() == 0;
		}

		public boolean isRoot() {
			return name.isEmpty();
		}

		private void add(List<String> path, int i) {
			String childName = path.get(i);
			PathNode child = children.get(childName);
			if (child != null) {
				if (path.size() - i <= 1)
					child.children.clear();
				else
					child.add(path, i + 1);
			} else if (!isLeaf() || isRoot()) {
				PathNode node = this;
				for (; i < path.size(); i++) {
					String key = path.get(i);
					node.children.put(key, new PathNode(key));
					node = node.children.get(key);
				}
			}
		}

		private void listPaths(ArrayList<String> list, String prefix) {
			for (PathNode child : children.values()) {
				if (child.isLeaf())
					list.add(prefix + child.name);
				else
					child.listPaths(list, prefix + child.name + File.separator);
			}
		}

		private boolean remove(List<String> path, int i) {
			PathNode child = children.get(path.get(i));
			if (!child.isLeaf() && i + 1 < path.size()) {
				if (child.children.get(path.get(i + 1)).isLeaf()) {
					child.children.remove(path.get(i + 1));
					Utils.log(Utils.Debug.DEBUG, "true on remove on Pathbase.java");
					return true;
				} else {
					return child.remove(path, i + 1);
				}
			}
			return false;
		}
	}

	private Pathbase() {
		add("");
	}

	public static Pathbase newInstance(String filename) {
		Pathbase pathbase = new Pathbase();
		File file = new File(filename);
		if (file.exists() && file.isFile())
			try {
				Utils.log(Utils.Debug.DEBUG, "BEFORE READING FILE: " + pathbase.getPathsList());
				pathbase.loadFromFile(file);
				Utils.log(Utils.Debug.DEBUG, "AFTER READING FILE: " + pathbase.getPathsList());
			} catch (FileNotFoundException e) {
				// Intentionally unchecked exception
			} catch (IOException e) {
				// TODO need to check this
				// FileNotFoundException when db.txt is not present
				e.printStackTrace();
			}
		return pathbase;
	}

	private PathNode root = new PathNode("");

	public void add(String p) {
		root.add(Arrays.asList(p.split("\\\\|/")), 0);
	}

	public void clear() {
		root.clear();
	}

	public void createPathbaseFile(String filename) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)), Charset.defaultCharset()))) {
			for (String path : getPathsList()) {
				writer.append(path);
				writer.newLine();
			}
		}
	}

	public List<String> getPathsList() {
		ArrayList<String> list = new ArrayList<>();
		root.listPaths(list, "");
		return list;
	}

	public void loadFromFile(File file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					Path path = Paths.get(line).toRealPath();
					add(path.toString());
				}
			}
		}
	}

	public void remove(String p) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(p.split("\\\\|/")));
		while (root.remove(list, 0))
			list.remove(list.size() - 1);
	}

	public int size() {
		List<String> list = getPathsList();
		if (list.get(0).length() == 0)
			return 0;
		return list.size();
	}
}