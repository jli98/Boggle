import java.io.*;
import java.util.*;

public class Boggle {
	public void solver(char[][] board, boolean[][] tracker, Trie lexicon, String word, int x, int y,
			Set<String> result) {
		if (lexicon.contains(word)) {
			result.add(word);
		}
		if (!lexicon.containsPrefix(word)) {
			return;
		}
		boolean[][] tmp = tracker;
		tmp[x][y] = true;
		if (0 <= x - 1 && 0 <= y - 1 && !tmp[x - 1][y - 1]) {
			solver(board, tmp, lexicon, word + board[x - 1][y - 1], x - 1, y - 1, result);
		}
		if (0 <= y - 1 && !tmp[x][y - 1]) {
			solver(board, tmp, lexicon, word + board[x][y - 1], x, y - 1, result);
		}
		if (x + 1 < board.length && 0 <= y - 1 && !tmp[x + 1][y - 1]) {
			solver(board, tmp, lexicon, word + board[x + 1][y - 1], x + 1, y - 1, result);
		}
		if (x + 1 < board.length && !tmp[x + 1][y]) {
			solver(board, tmp, lexicon, word + board[x + 1][y], x + 1, y, result);
		}
		if (x + 1 < board.length && y + 1 < board[0].length && !tmp[x + 1][y + 1]) {
			solver(board, tmp, lexicon, word + board[x + 1][y + 1], x + 1, y + 1, result);
		}
		if (y + 1 < board[0].length && !tmp[x][y + 1]) {
			solver(board, tmp, lexicon, word + board[x][y + 1], x, y + 1, result);
		}
		if (0 <= x - 1 && y + 1 < board[0].length && !tmp[x - 1][y + 1]) {
			solver(board, tmp, lexicon, word + board[x - 1][y + 1], x - 1, y + 1, result);
		}
		if (0 <= x - 1 && !tmp[x - 1][y]) {
			solver(board, tmp, lexicon, word + board[x - 1][y], x - 1, y, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Trie trie = new Trie();
		Scanner input = new Scanner(new FileReader("dictionary.txt"));
		while (input.hasNext()) {
			String s = input.next();
			trie.add(s);
		}
		input.close();

		Reader r = new FileReader("boggle.dat");
		int size = r.read();
		char[][] board = new char[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				board[i][j] = (char) (r.read());
			}

	}
}

class Trie {
	private int SIZE = 26;
	private TrieNode root;
	private boolean abc;

	Trie() {
		root = new TrieNode();
	}

	private class TrieNode {
		private TrieNode[] son;
		private boolean isEnd;
		private char val;

		TrieNode() {
			son = new TrieNode[SIZE];
			isEnd = false;
		}
	}

	public void add(String str) {
		if (str == null || str.length() == 0) {
			return;
		}
		TrieNode node = root;
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			int pos = letters[i] - 'a';
			if (node.son[pos] == null) {
				node.son[pos] = new TrieNode();
				node.son[pos].val = letters[i];
			}
			node = node.son[pos];
		}
		node.isEnd = true;
	}

	public boolean contains(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		TrieNode node = root;
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			int pos = letters[i] - 'a';
			if (node.son[pos] != null) {
				node = node.son[pos];
			} else {
				return false;
			}
		}
		return node.isEnd;
	}

	public boolean containsPrefix(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		TrieNode node = root;
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			int pos = letters[i] - 'a';
			if (node.son[pos] != null) {
				node = node.son[pos];
			} else {
				return false;
			}
		}
		return true;
	}
}
