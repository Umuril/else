package com.ELSE.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathTree {
	static PathTree newInstance(final Path path) {
		final PathTree pathtree = new PathTree();
		if (Files.isRegularFile(path))
			try {
				Utils.log(Utils.Debug.DEBUG, "BEFORE READING FILE: " + pathtree.getPathList());
				pathtree.loadFromFile(path);
				Utils.log(Utils.Debug.DEBUG, "AFTER READING FILE: " + pathtree.getPathList());
			} catch (final FileNotFoundException ex) {
				// Intentionally unchecked exception
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		return pathtree;
	}
	
	private final PathNode root = new PathNode("");
	
	private PathTree() {
		add("");
	}
	
	public void add(final String path) {
		root.add(Arrays.asList(path.split("\\\\|/")), 0);
	}
	
	public void clear() {
		root.clear();
	}
	
	public void createPathTreeFile(final Path path) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toFile()), Charset.defaultCharset()))) {
			for (final String pathlist : getPathList()) {
				writer.append(pathlist);
				writer.newLine();
			}
		}
	}
	
	public List<String> getPathList() {
		final ArrayList<String> list = new ArrayList<>();
		root.listPaths(list, "");
		return list;
	}
	
	public void loadFromFile(final Path path) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null)
				if (Utils.validString(line))
					add(Paths.get(line).toRealPath().toString());
		}
	}
	
	public void remove(final String path) {
		final ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(path.split("\\\\|/")));
		while (root.remove(list, 0))
			list.remove(list.size() - 1);
	}
	
	public int size() {
		final List<String> list = getPathList();
		if (list.get(0).length() == 0)
			return 0;
		return list.size();
	}
	
	private static class PathNode {
		private final Map<String, PathNode> children = new HashMap<>();
		private final String name;
		
		private PathNode(final String name) {
			this.name = name;
		}
		
		private void add(final List<String> path, int i) {
			final PathNode child = children.get(path.get(i));
			if (child != null) {
				if (path.size() - i <= 1)
					child.children.clear();
				else
					child.add(path, i + 1);
			} else if (!isLeaf() || isRoot()) {
				PathNode node = this;
				for (; i < path.size(); i++) {
					final String key = path.get(i);
					node.children.put(key, new PathNode(key));
					node = node.children.get(key);
				}
			}
		}
		
		public void clear() {
			for (final PathNode child : children.values())
				if (!child.isLeaf()) {
					child.clear();
					child.children.clear();
				}
		}
		
		public boolean isLeaf() {
			return children.size() == 0;
		}
		
		public boolean isRoot() {
			return name.isEmpty();
		}
		
		private void listPaths(final ArrayList<String> list, final String prefix) {
			for (final PathNode child : children.values())
				if (child.isLeaf())
					list.add(prefix + child.name);
				else
					child.listPaths(list, prefix + child.name + FileSystems.getDefault().getSeparator());
		}
		
		private boolean remove(final List<String> path, final int i) {
			final PathNode child = children.get(path.get(i));
			if (!child.isLeaf() && i + 1 < path.size())
				if (child.children.get(path.get(i + 1)).isLeaf()) {
					child.children.remove(path.get(i + 1));
					Utils.log(Utils.Debug.DEBUG, "true on remove on PathTree.java");
					return true;
				} else
					return child.remove(path, i + 1);
			return false;
		}
	}
}