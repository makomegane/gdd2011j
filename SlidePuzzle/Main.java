public class Main {
	public static void main(String[] args) {
		long miliSec = Long.parseLong(args[0]);
		int limitMax = Integer.parseInt(args[1]);
		int startIndex = Integer.parseInt(args[2]);
		int endIndex = Integer.parseInt(args[3]);
		SlidePuzzle sp = new SlidePuzzle(miliSec, limitMax, startIndex, endIndex);
		sp.main();
	}
}
