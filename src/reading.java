//Program by AMOL VAZE(Net_ID:-asv130130@utdallas.edu)

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

public class reading {
	// Training data examples and number of the features
	public static int Examples;

	public static int Features;

	// ArrayList for storing single row of the training data
	public static ArrayList<Integer> featureArray;

	// ArrayList for complete training data
	public static ArrayList<ArrayList<Integer>> featureArrayList;

	// Reading and saving the data of (Partition)-file into the HashTable
	public static Hashtable<String, String> Groups = new Hashtable<>();

	public reading(String file1, String file2) throws FileNotFoundException {
		String path = System.getProperty("user.dir")
				+ (reading.class.getPackage() == null ? "" : "\\"
						+ "\\src\\"
						+ reading.class.getPackage().getName()
								.replace('.', '\\'));

		Scanner S = new Scanner(new File(path + "\\" + file1 + ".txt"));

		String temp = S.nextLine();
		StringTokenizer token = new StringTokenizer(temp, " ");

		Examples = Integer.parseInt(token.nextToken());
		Features = Integer.parseInt(token.nextToken());

		featureArrayList = new ArrayList<>();

		for (int i = 0; i < Features; i++) {

			featureArray = new ArrayList<>();
			S = new Scanner(new File(path + "\\" + file1 + ".txt"));
			S.nextLine();

			while (S.hasNextLine()) {
				temp = S.nextLine();
				String[] inputs = temp.split(" ");
				featureArray.add(Integer.parseInt(inputs[i]));
			}

			featureArrayList.add(featureArray);
		}

		S = new Scanner(new File(path + "\\" + file2 + ".txt" + ""));

		while (S.hasNextLine()) {
			temp = S.nextLine();
			String[] tempArray = temp.split(" ");
			String str = "";
			for (int i = 1; i < tempArray.length; i++) {
				str += tempArray[i] + " ";
			}
			str = str.substring(0, str.length() - 1);
			Groups.put(tempArray[0], str);

		}
		S.close();

	}

	/*public static void readSourceFile(String filename1) {

		// TODO Auto-generated method stub

	}

	public static void readPartitionFile(String filename2) {
		// TODO Auto-generated method stub

	}
*/
	// Code for writing to output partition file
	public void WriteFile(String S, String file) {
		String path = System.getProperty("user.dir")
				+ (reading.class.getPackage() == null ? "" : "\\"
						+ "\\src\\"
						+ reading.class.getPackage().getName()
								.replace('.', '\\'));
		try {
			FileWriter fstream = new FileWriter(path + "\\" + file + ".txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(S);
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

}
