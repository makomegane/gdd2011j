import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlidePuzzle {
	private static final String X = "X";
	private int limit = 0;
	private String answer;
	private Map<String, Integer> save;
	private long timeout;
	private int limitMax;
	private long startMiliSec;
	private boolean isTimeOut;
	private int strange;
	private int startIndex;
	private int endIndex;
	public SlidePuzzle(long miliSec, int lm, int startIndex, int endIndex) {
		this.timeout = miliSec;
		this.limitMax = lm;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	public void main() {
		System.out.println("<><><><><><>START<><><><><>");
		System.out.println(Calendar.getInstance().getTime());
		List<String> doneList = new ArrayList<String>();
		try {
			FileReader in = new FileReader("done.txt");
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				doneList.add(line);
			}
			br.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			FileReader in = new FileReader("problems.txt");
			BufferedReader br = new BufferedReader(in);
			String line;
			int cnt = 0;
			List<String> list = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				cnt++;
				if (cnt == 1 || cnt == 2) {
					continue;
				}
				list.add(line);
			}
			br.close();
			in.close();
			
			System.out.println("list.size()="+list.size());
			System.out.println("doneList.size()="+doneList.size());
			File file = new File("answer.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			int ansCntUp = 0;
			int ansCntLeft = 0;
			int ansCntDown = 0;
			int ansCntRight = 0;
			int timeOutCnt = 0;
			int limitMaxCnt = 0;
			int strangeCnt = 0;
			for (int i = 0; i < list.size(); i++) {
				if (doneList.get(i).length() != 0) {
					pw.println(doneList.get(i));
				} else if (list.get(i).length() == 0) {
					pw.println("");
				} else {
					String[] param = list.get(i).split(",");
					int wi = Integer.parseInt(param[0]);
					int hi = Integer.parseInt(param[1]);
					Cell cell = new Cell(wi,
										hi,
										param[2]);
					limit = cell.getAllDistance();
					if (i >= this.startIndex && i <= this.endIndex) {
						this.answer = null;
						this.save = null;
						System.gc();
						String answer = "";
						int repeat = 0;
						this.answer = "X";
						this.save = new HashMap<String, Integer>();
						this.strange = 0;
						while (true) {
							answer = null;
							save = null;
							System.gc();
							this.save = new HashMap<String, Integer>();
							repeat++;
							System.out.println((i + 1) + " solving! limit="+limit + " " + repeat + "times");
							boolean isTimeOutUp = false;
							boolean isTimeOutLeft = false;
							boolean isTimeOutDown = false;
							boolean isTimeOutRight = false;
							this.isTimeOut = false;
							this.startMiliSec = Calendar.getInstance().getTimeInMillis();
							answer = this.getAns(cell, 1);
							if (!X.equals(answer)) {
								ansCntUp++;
								break;
							}
							if (this.isTimeOut) {
								isTimeOutUp = true;
							}
							this.isTimeOut = false;
							this.startMiliSec = Calendar.getInstance().getTimeInMillis();
							answer = this.getAns(cell, 2);
							if (!X.equals(answer)) {
								ansCntLeft++;
								break;
							}
							if (this.isTimeOut) {
								isTimeOutLeft = true;
							}
							this.isTimeOut = false;
							this.startMiliSec = Calendar.getInstance().getTimeInMillis();
							answer = this.getAns(cell, 3);
							if (!X.equals(answer)) {
								ansCntDown++;
								break;
							}
							if (this.isTimeOut) {
								isTimeOutDown = true;
							}
							this.isTimeOut = false;
							this.startMiliSec = Calendar.getInstance().getTimeInMillis();
							answer = this.getAns(cell, 4);
							if (!X.equals(answer)) {
								ansCntRight++;
								break;
							}
							if (this.isTimeOut) {
								isTimeOutRight = true;
							}
							if (isTimeOutUp && isTimeOutLeft && isTimeOutDown && isTimeOutRight) {
								answer = "";
								timeOutCnt++;
								break;
							}
							if (isTimeOutUp && isTimeOutLeft) {
								this.strange++;
								if (this.strange > 4) {
									this.save = null;
									System.gc();
									this.save = new HashMap<String, Integer>();
									this.isTimeOut = false;
									this.startMiliSec = Calendar.getInstance().getTimeInMillis();
									answer = this.getAns(cell, 3);
									if (!X.equals(answer)) {
										ansCntDown++;
										break;
									}
									if (this.isTimeOut) {
										isTimeOutDown = true;
									}
									this.isTimeOut = false;
									this.startMiliSec = Calendar.getInstance().getTimeInMillis();
									answer = this.getAns(cell, 4);
									if (!X.equals(answer)) {
										ansCntRight++;
										break;
									}
									if (this.isTimeOut) {
										isTimeOutRight = true;
									}
									this.isTimeOut = false;
									this.startMiliSec = Calendar.getInstance().getTimeInMillis();
									answer = this.getAns(cell, 1);
									if (!X.equals(answer)) {
										ansCntUp++;
										break;
									}
									if (this.isTimeOut) {
										isTimeOutUp = true;
									}
									this.isTimeOut = false;
									this.startMiliSec = Calendar.getInstance().getTimeInMillis();
									answer = this.getAns(cell, 2);
									if (!X.equals(answer)) {
										ansCntLeft++;
										break;
									}
									if (this.isTimeOut) {
										isTimeOutLeft = true;
									}
									if (isTimeOutUp && isTimeOutLeft && isTimeOutDown && isTimeOutRight) {
										answer = "";
										timeOutCnt++;
										break;
									}
									answer = "";
									strangeCnt++;
									break;
								}
							}
							limit += 2;
							if (limit > this.limitMax) {
								answer = "";
								limitMaxCnt++;
								break;
							}
							System.out.println("isTimeOutUp="+isTimeOutUp);
							System.out.println("isTimeOutLeft="+isTimeOutLeft);
							System.out.println("isTimeOutDown="+isTimeOutDown);
							System.out.println("isTimeOutRight="+isTimeOutRight);
						}
						System.out.println("answer="+answer);
						System.out.println("up total="+ansCntUp);
						System.out.println("left total="+ansCntLeft);
						System.out.println("down total="+ansCntDown);
						System.out.println("right total="+ansCntRight);
						System.out.println("timeout total="+timeOutCnt);
						System.out.println("strange total="+strangeCnt);
						System.out.println("limit max total="+limitMaxCnt);
						pw.println(answer);
					} else {
						pw.println("");
					}
				}
			}
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println(Calendar.getInstance().getTime());
		System.out.println("<><><><><><>END<><><><><>");
	}
	private String getAns(Cell cell, int direct) {
		try {
			this.ans(cell, direct, false);
		} catch (CloneNotSupportedException e) {
			System.out.println(e);
		}
		cell = null;
		return answer;
	}
	private void ans(Cell cell, int direct, boolean canChk) throws CloneNotSupportedException {
		if (!X.equals(answer)) {
			cell = null;
			return;
		}
		if (isSameHash(cell.getCells(), cell.getEndHash())) {
			if (X.equals(answer) || answer.length() > cell.getAnswer().length()) {
				answer = cell.getAnswer().toString();
			}
			cell = null;
			return;
		}
		if (Calendar.getInstance().getTimeInMillis() - this.startMiliSec > this.timeout) {
			cell = null;
			this.isTimeOut = true;
			return;
		}
		int allDistance = cell.getAllDistance();
		if (allDistance < 0) {
			System.out.println("Minus answer= "+cell.getAnswer().toString());
			System.out.println(allDistance);
			this.answer ="Z";
			cell = null;
			return;
		}
		if ((cell.getSlideCnt() + cell.getAllDistance()) > limit) {
			cell = null;
			return;
		}
		if (canChk) {
			if (tried(cell.getCells(), cell.getSlideCnt())) {
				cell = null;
				return;
			}
		}
		if (direct == 1) {
			if (cell.canUp()) {
				//Up
				Cell cellUp = cell.cln();
				cellUp.actionUp();
				ans(cellUp, 0, true);
			}
			if (cell.canLeft()) {
				//Left
				Cell cellLeft = cell.cln();
				cellLeft.actionLeft();
				ans(cellLeft, 0, true);
			}
			if (cell.canDown()) {
				//Down
				Cell cellDown = cell.cln();
				cellDown.actionDown();
				ans(cellDown, 0, true);
			}
			if (cell.canRight()) {
				//Right
				Cell cellRight = cell.cln();
				cellRight.actionRight();
				ans(cellRight, 0, true);
			}
		} else if (direct == 2) {
			if (cell.canDown()) {
				//Down
				Cell cellDown = cell.cln();
				cellDown.actionDown();
				ans(cellDown, 0, true);
			}
			if (cell.canRight()) {
				//Right
				Cell cellRight = cell.cln();
				cellRight.actionRight();
				ans(cellRight, 0, true);
			}
			if (cell.canUp()) {
				//Up
				Cell cellUp = cell.cln();
				cellUp.actionUp();
				ans(cellUp, 0, true);
			}
			if (cell.canLeft()) {
				//Left
				Cell cellLeft = cell.cln();
				cellLeft.actionLeft();
				ans(cellLeft, 0, true);
			}
		} else if (direct == 3) {
			if (cell.canLeft()) {
				//Left
				Cell cellLeft = cell.cln();
				cellLeft.actionLeft();
				ans(cellLeft, 0, true);
			}
			if (cell.canUp()) {
				//Up
				Cell cellUp = cell.cln();
				cellUp.actionUp();
				ans(cellUp, 0, true);
			}
			if (cell.canRight()) {
				//Right
				Cell cellRight = cell.cln();
				cellRight.actionRight();
				ans(cellRight, 0, true);
			}
			if (cell.canDown()) {
				//Down
				Cell cellDown = cell.cln();
				cellDown.actionDown();
				ans(cellDown, 0, true);
			}
		} else if (direct == 4) {
			if (cell.canRight()) {
				//Right
				Cell cellRight = cell.cln();
				cellRight.actionRight();
				ans(cellRight, 0, true);
			}
			if (cell.canDown()) {
				//Down
				Cell cellDown = cell.cln();
				cellDown.actionDown();
				ans(cellDown, 0, true);
			}
			if (cell.canLeft()) {
				//Left
				Cell cellLeft = cell.cln();
				cellLeft.actionLeft();
				ans(cellLeft, 0, true);
			}
			if (cell.canUp()) {
				//Up
				Cell cellUp = cell.cln();
				cellUp.actionUp();
				ans(cellUp, 0, true);
			}
		} else {
			if (cell.canUp()) {
				//Up
				Cell cellUp = cell.cln();
				cellUp.actionUp();
				ans(cellUp, 0, true);
			}
			if (cell.canLeft()) {
				//Left
				Cell cellLeft = cell.cln();
				cellLeft.actionLeft();
				ans(cellLeft, 0, true);
			}
			if (cell.canDown()) {
				//Down
				Cell cellDown = cell.cln();
				cellDown.actionDown();
				ans(cellDown, 0, true);
			}
			if (cell.canRight()) {
				//Right
				Cell cellRight = cell.cln();
				cellRight.actionRight();
				ans(cellRight, 0, true);
			}
		}
	}
	private boolean tried(String[][] array, int slideCnt) {
		boolean ret = false;
		boolean isSave = true;
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			key.append(String.valueOf(Arrays.deepHashCode(array[i])));
		}
		String keyStr = key.toString();
		if (save.containsKey(keyStr)) {
			if (save.get(keyStr).intValue() <= slideCnt) {
				ret = true;
			} else {
				save.put(keyStr, Integer.valueOf(slideCnt));
			}
			isSave = false;
		}
		if (isSave) {
			save.put(keyStr, Integer.valueOf(slideCnt));
		}
		return ret;
	}
	private boolean isSameHash(String[][] array, int endHash[]) {
		boolean ret = true;
		for (int i = 0; i < endHash.length; i++) {
			if (endHash[i] != Arrays.deepHashCode(array[i])) {
				ret = false;
				break;
			}
		}
		return ret;
	}
}
