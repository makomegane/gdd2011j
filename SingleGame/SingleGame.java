import java.io.*;
import java.util.*;
class SingleGame {
	private static int count;
	public static void main(String[] args) {
		try {
			//File to List
			FileReader in = new FileReader("question.txt");
			BufferedReader br = new BufferedReader(in);
			String line;
			List<String> l = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				l.add(line);
			}
			br.close();
			in.close();

			//List to Detail
			int cnt = Integer.parseInt(l.get(0));
			List<String> num = new ArrayList<String>();
			List<String[]> que = new ArrayList<String[]>();
			for (int i = 1; i < l.size(); i++) {
				num.add(l.get(i));
				i++;
				que.add(l.get(i).split(" "));
			}

			//Answer
			File file = new File("answer.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (int i = 0; i < num.size(); i++) {
				count = 999;
				ans(que.get(i), 0);
				pw.println(String.valueOf(count));
			}
			pw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static String[] half(String[] que) {
		String[] in = new String[que.length];
		for (int i = 0; i < que.length; i++) {
			in[i] = que[i];
		}
		for (int i = 0; i < in.length; i++) {
			if ("".equals(in[i])) {
				continue;
			}
			int num = Integer.parseInt(in[i]);
			if (num != 0) {
				num = num / 2;
			}
			in[i] = String.valueOf(num);
		}
		return in;
	}

	private static String[] five(String[] que) {
		String[] in = new String[que.length];
		for (int i = 0; i < que.length; i++) {
			in[i] = que[i];
		}
		for (int i = 0; i < in.length; i++) {
			if ("".equals(in[i])) {
				continue;
			}
			int num = Integer.parseInt(in[i]);
			in[i] = num % 5 == 0 ? "" : String.valueOf(num);
		}
		return in;
	}

	private static boolean canFive(String[] que) {
		boolean ret = false;
		for (int i = 0; i < que.length; i++) {
			if ("".equals(que[i])) {
				continue;
			}
			int num = Integer.parseInt(que[i]);
			if (num % 5 == 0) {
				ret = true;
			}
		}
		return ret;
	}

	private static boolean isEnd(String[] que) {
		boolean isNothing = true;
		for (int i = 0; i < que.length; i++) {
			if (!"".equals(que[i])) {
				isNothing = false;
				break;
			}
		}
		return isNothing;
	}

	private static void ans(String[] que, int temp) {
		//End?
		if (isEnd(que)) {
			if (temp < count) {
				count = temp;
			}
			return;
		}
		String[] in1 = new String[que.length];
		String[] in2 = new String[que.length];
		for (int i = 0; i < que.length; i++) {
			in1[i] = que[i];
			in2[i] = que[i];
		}
		//Five
		if (canFive(in1)) {
			String[] in3 = five(in1);
			ans(in3, (temp + 1));
		}
		//Half
		String[] in4 = half(in2);
		if (!isSameArray(in2, in4)) {
			ans(in4, (temp + 1));
		}
	}

	private static boolean isSameArray(String[] arg1, String[] arg2) {
		boolean ret = true;
		for (int i = 0; i < arg1.length; i++) {
			if (!arg1[i].equals(arg2[i])) {
				ret = false;
				break;
			}
		}
		return ret;
	}

}
