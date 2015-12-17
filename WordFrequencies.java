import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class WordFrequencies {

// This program takes a file from standard input and produces an inventory of its words, with their frequencies, 
// on standard output. 

// (1) When forming words, only letters and digits count- ignore all commas, semi-colons and colons, 
// hyphens, apostrophes, etc. 

// (2) By default, all letters should be converted to upper-case; however, if case SENSITIVITY is desired, 
// then the program should be run with the -s option.

// (3) Hyphens should be ignored. For example, all of the following will be counted as CATS: CATS, cats, cAtS, cat-s, C-at--s-, -c-AT----S. 

// (4) All characters OTHER THAN STATNDARD PUNCTUATION (see 1), and, of course, spaces, are presumed to be delimiters. 

// (5) Frequencies should outputted along with the strings. If CLEAN output (i.e., without frequencies) is desired, 
// then the program should be run with the -c option.

	public static void main(String[] args) {

		boolean caseSen = false;
		boolean clean = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-s")) {
				caseSen = true;
			}

			if (args[i].equals("-c")) {
				clean = true;
			}

			if (args[i].equals("-sc")) {
				caseSen = true;
				clean = true;
			}
		}
		// int x = 8;
		// x += map.get(y); * assures the compiler that its a pair of strings and integers (x = string, y = int) *
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashSet<String> set = new HashSet<String>(); //string bc keys, sets are just the keys, maps are the keys and values
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			String str = scan.next();
			str = str.replaceAll("-", "");
			str = str.replaceAll("'", "");
			str = str.replaceAll("[^a-zA-Z0-9]", " "); //regex
			String[] array = str.split(" ");
			for (int j = 0; j < array.length; j ++) {
				if (!(array[j].equals(""))) {
					array[j] = (!caseSen ? array[j].toUpperCase() : array[j]);
					Integer value = map.get(array[j]);
					map.put(array[j], (value == null ? 1 : value + 1));
					set.add(array[j]);
				}
			}
		}
		ArrayList<String> sortedList = new ArrayList<String>(set);
		Collections.sort(sortedList);
		for (String word : sortedList) { 
			System.out.println(word + (clean ? "" : " " + map.get(word)));
		}
	}
}
