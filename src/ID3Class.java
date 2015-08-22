//Program by AMOL VAZE(Net_ID:-asv130130@utdallas.edu)

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ID3Class {
	public static ArrayList<ArrayList<Double>> GroupGains = new ArrayList<>();
	public static ArrayList<String> Feature_F_List;
	public static String NewPartitions = "";
	private static reading r;
	static String keynode;
	static int feaure_to_split;
	static String FileNewText = "";
	static String pathName;
	static String filename1, filename2, filename3;

	// Main Function
	public static void main(String[] args) throws FileNotFoundException {

		pathName = System.getProperty("user.dir")
				+ (ID3Class.class.getPackage() == null ? "" : "\\"
						+ "\\src\\"
						+ ID3Class.class.getPackage().getName()
								.replace('.', '\\'));
		// @SuppressWarnings("resource")
		// Asking user for entering data-set file
		Scanner s1 = new Scanner(System.in);
		System.out.print("Enter the name of the dataset file: ");
		filename1 = s1.nextLine();

		// Asking for entering partition file
		@SuppressWarnings({ "unused", "resource" })
		Scanner s2 = new Scanner(System.in);
		System.out.print("Enter the name of the partition file: ");
		filename2 = s1.nextLine();

		// Asking for entering output file
		@SuppressWarnings({ "unused", "resource" })
		Scanner s3 = new Scanner(System.in);
		System.out.print("Enter the name of the output file: ");
		filename3 = s1.nextLine();

		r = new reading(filename1, filename2);
		MaxF();
		FileNewText = partitionNode(keynode, feaure_to_split);
		// Writing the result to a file
		r.WriteFile(FileNewText, filename3);
		s1.close();
	}

	private static void MaxF() {
		// To keep a track of rows in a group
		int r;
		double max_feature_value = 0;
		double feature_value = 0;
		// int max_feature_of_node = 0;
		// First entropy value
		double entropy;
		double entropy_by_feature_0 = 0;
		double entropy_by_feature_1 = 0;
		double entropy_by_feature_2 = 0;
		// Gain values
		double gain_by_feature = 0;
		// Max gain for node
		// double max_gain = 0;
		// Feature of partition value
		double entropy_by_feature = 0;
		// probability
		double prob_value = 0;

		// Entropy calculations variables
		int total_zero = 0, total_one = 0, total_two = 0;
		int zero_zero = 0, zero_one = 0, one_zero = 0, one_one = 0, two_zero = 0, two_one = 0;
		// TODO Auto-generated method stub
		for (Map.Entry<String, String> entry : reading.Groups.entrySet()) {
			String[] temp = entry.getValue().split(" ");
			r = temp.length;
			// No of zeros and no of ones in column
			int count_zero = 0, count_one = 0;
			for (int i = 0; i < temp.length; i++) {

				if (reading.featureArrayList.get(reading.Features - 1).get(
						Integer.parseInt(temp[i]) - 1) == 1) {
					count_one++;
				} else if (reading.featureArrayList.get(reading.Features - 1)
						.get(Integer.parseInt(temp[i]) - 1) == 0) {
					count_zero++;
				}
			}
			if (count_one == 0 || count_zero == 0) {

				entropy = 0;
				total_one = 0;
				total_two = 0;
				total_zero = 0;
				/*
				 * NewPartitions = NewPartitions + entry.getKey().toString() +
				 * " " + entry.getValue().toString()+ "\n";
				 * System.out.println(NewPartitions);
				 */
			} else {

				entropy = ((double) (((double) count_zero / (double) r) * (Math
						.log10(r / count_zero) / Math.log10(2))))
						+ ((double) (((double) count_one / (double) r) * (Math
								.log10(r / count_one) / Math.log10(2))));

				for (int j = 0; j < reading.Features - 1; j++) {

					for (int i = 0; i < temp.length; i++) {
						if (reading.featureArrayList.get(j).get(
								Integer.parseInt(temp[i]) - 1) == 1) {
							total_one++;
							if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 0) {
								one_zero++;
							} else if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 1) {
								one_one++;
							}
						} else if (reading.featureArrayList.get(j).get(
								Integer.parseInt(temp[i]) - 1) == 0) {
							total_zero++;
							if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 0) {
								zero_zero++;
							} else if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 1) {
								zero_one++;
							}
						} else if (reading.featureArrayList.get(j).get(
								Integer.parseInt(temp[i]) - 1) == 2) {
							total_two++;
							if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 0) {
								two_zero++;
							} else if (reading.featureArrayList.get(
									reading.Features - 1).get(
									Integer.parseInt(temp[i]) - 1) == 1) {
								two_one++;
							}

						}
					}
					/*
					 * System.out.println("feature_one: " + total_one + "\n " +
					 * "1-1: " + one_one + " 1-0: " + one_zero + "\n" +
					 * "feature_zero: " + total_zero + "\n " + "0-1: " +
					 * zero_one + " 0-0: " + zero_zero + "\n" + "feature_two: "
					 * + total_two + "\n " + "2-1: " + two_one + " 2-0: " +
					 * two_zero + "\n");
					 */

					if (total_zero == 0 || zero_one == 0 || zero_zero == 0) {
						entropy_by_feature_0 = 0;
					} else {
						entropy_by_feature_0 = entropyCalculation(total_zero,
								zero_one, zero_zero, r);
					}

					if (total_one == 0 || one_one == 0 || one_zero == 0) {
						entropy_by_feature_1 = 0;
					} else {
						entropy_by_feature_1 = entropyCalculation(total_one,
								one_one, one_zero, r);
					}

					if (total_two == 0 || two_one == 0 || two_zero == 0) {
						entropy_by_feature_2 = 0;
					} else {
						entropy_by_feature_2 = entropyCalculation(total_two,
								two_one, two_zero, r);
					}
					// Calculating entropy by feature and gain by feature

					entropy_by_feature = entropy_by_feature_0
							+ entropy_by_feature_1 + entropy_by_feature_2;
					gain_by_feature = entropy - entropy_by_feature;

					// probability & feature value calculations
					prob_value = (double) (r) / (double) reading.Examples;
					feature_value = prob_value * gain_by_feature;
					if (feature_value > max_feature_value) {
						max_feature_value = feature_value;
						keynode = entry.getKey();
						feaure_to_split = j;
					}

					System.out.println(entropy_by_feature);
					entropy_by_feature_0 = 0;
					entropy_by_feature_1 = 0;
					entropy_by_feature_2 = 0;
					zero_one = 0;
					zero_zero = 0;
					one_zero = 0;
					one_one = 0;
					two_zero = 0;
					two_one = 0;
					total_one = 0;
					total_two = 0;
					total_zero = 0;

				}
			}
		}// End of for loop that runs for File-2

		// End of MaxF function

	}

	// To find maximum
	/*public int max_no(int a, int b, int c) {
		int max = 0;
		int[] a1 = { a, b, c };
		for (int i = 0; i < 3; i++) {
			if (a1[i] > max) {
				max = a1[i];
			}

		}
		return max;

	}
*/
	// Method for calculating conditional entropy
	public static double entropyCalculation(double feature_number,
			double zero_one, double zero_zero, double r) {
		double entropy1;
		entropy1 = (double) ((double) feature_number / (double) r)
				* ((((double) zero_zero / (double) feature_number) * (Math
						.log10((feature_number) / (double) zero_zero) / Math
						.log10(2))) + (((double) zero_one / (double) feature_number) * (Math
						.log10((feature_number) / (double) zero_one) / Math
						.log10(2))));
		return entropy1;
	}

	public static String partitionNode(String node, int feature_number) {
		String partitionText = "";

		for (Map.Entry<String, String> entry : reading.Groups.entrySet()) {
			if (entry.getKey() == node) {
				String[] temp = entry.getValue().split(" ");
				StringBuffer zero_node = new StringBuffer(""), one_node = new StringBuffer(
						""), two_node = new StringBuffer("");

				// ///////////////////////////////////////////////////////////////////////////////////////////////////////
				for (int i = 0; i < temp.length; i++) {
					if (reading.featureArrayList.get(feature_number).get(
							Integer.parseInt(temp[i]) - 1) == 1) {
						one_node.append(String.valueOf(temp[i]));

					} else if (reading.featureArrayList.get(feature_number)
							.get(Integer.parseInt(temp[i]) - 1) == 0) {
						zero_node.append(String.valueOf(temp[i]));

					} else if (reading.featureArrayList.get(feature_number)
							.get(Integer.parseInt(temp[i]) - 1) == 2) {
						two_node.append(String.valueOf(temp[i]));

					}
				}
				// ///////////////////////////////////////////////////////////////////////////////////////////////////////
				partitionText = partitionText + "\n" + entry.getKey() + "0 : "
						+ zero_node + "\n" + entry.getKey() + "1 : " + one_node
						+ "\n" + entry.getKey() + "2 : " + two_node + "\n";
			} else {
				partitionText = partitionText + entry.getKey() + ": "
						+ entry.getValue() + "\n";
			}

		}
		System.out.println("\n" + partitionText);

		return partitionText;
	}

}
