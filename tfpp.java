import java.io.*;
import java.util.*;


 /*
  * BufferedReader in = new BufferedReader(new FileReader("input.txt"));
		while(in.ready())
		{
			System.out.println(in.readLine());
		}

		File file = new File("output.txt");

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		BufferedReader members = new BufferedReader(new FileReader("generalMembers.txt"));
		String input = members.readLine();
		String[] things = input.split("\t");
		for(int i=0; i< things.length; i++)
		{
			System.out.println(things[i]);
		}

		String[] responses1 = {};
		bw.close();
 */

public class tfpp
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader famHeads = new BufferedReader(new FileReader("familyHeads.txt"));
		String[][] rawData = new String[16][];
		for(int i=0; i < 16; i++)
		{
			rawData[i] = famHeads.readLine().split("\t");
		}
		famHeads.close();
		String[] familyHeadNames = new String[8];
		for(int i=0; i < 16; i+=2)
		{
			familyHeadNames[i/2] = rawData[i][0] + " and " + rawData[i+1][0];
		}

		int[][] familyScores = new int[8][53];
		for(int i=0; i < 16; i++)
		{
			for(int j=2; j < rawData[0].length; j++)
			{
				familyScores[i/2][j-2] += Integer.parseInt(rawData[i][j]);
			}
		}
		
		ArrayList<ArrayList<String>> familyMembers= new ArrayList<ArrayList<String>>();
		for(int i=0; i< 8; i++)
		{
			familyMembers.add(new ArrayList<String>());
		}
		
		BufferedReader members = new BufferedReader(new FileReader("generalMembers.txt"));
		File individualScores = new File("scores.txt");
		if(!individualScores.exists())
		{
			individualScores.createNewFile();
		}
		FileWriter scorefw = new FileWriter(individualScores.getAbsoluteFile());
		BufferedWriter scorebw = new BufferedWriter(scorefw);

		while(members.ready())
		{
			//System.out.println("Boop.");
			String[] info = members.readLine().split("\t");
			int[] scores = new int[8];
			for(int j = 0; j < 8; j++)
			{
				scores[j] += 5 - Math.abs(Integer.parseInt(info[2]) - Integer.parseInt(rawData[2*j][1]));
				scores[j] += 5 - Math.abs(Integer.parseInt(info[2]) - Integer.parseInt(rawData[2*j+1][1]));
				scores[j] *= 3;
				for(int i = 3; i < info.length; i++)
				{
					scores[j] += Integer.parseInt(info[i]) * familyScores[j][i];
				}
			}
			int max = 0;
			scorebw.write(info[0] + "\t\t");
			//System.out.println(info[0]);
			for(int i=0; i<8; i++)
			{
				scorebw.write(" " + scores[i]);
				if(scores[i] > scores[max])
				{
					max = i;
				}
			}
			scorebw.write("\n");
			familyMembers.get(max).add(info[0]);
		}
		scorebw.close();
		File families = new File("families.txt");
		FileWriter fw = new FileWriter(families.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i = 0; i < 8; i++)
		{
			bw.write(familyHeadNames[i] + ":\n");
			for(int j=0; j<familyMembers.get(i).size(); j++)
			{
				bw.write(familyMembers.get(i).get(j));
				bw.newLine();
			}
			bw.newLine();
		}
		bw.close();
	}
}
