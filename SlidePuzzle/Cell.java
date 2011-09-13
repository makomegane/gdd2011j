import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {
	private static final String[] ALL =
		{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
		 "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private static final String WALL = "=";
	private static final String ZERO = "0";
	private static final String U = "U";
	private static final String D = "D";
	private static final String L = "L";
	private static final String R = "R";
	private StringBuilder answer;
	private String[][] cells;
	private int wi;
	private int hi;
	private String bi;
	private int slideCnt;
	private int zeroWi;
	private int zeroHi;
	private int beforeWi;
	private int beforeHi;
	private int[] endHash;
	private Map<String, Integer> distance;
	private int allDistance;
	private Map<String, int[]> endPos;
	private boolean hasWall;
	public Cell() {
		//Nothing
	}
	public Cell(int wi, int hi, String bi) {
		this.answer = new StringBuilder();
		this.slideCnt = 0;
		this.wi = wi;
		this.hi = hi;
		this.bi = bi;
		this.cells = new String[hi][wi];
		int start = 0;
		this.hasWall = false;
		for (int i = 0; i < hi; i++) {
			for (int j = 0; j < wi; j++) {
				cells[i][j] = bi.substring(start, (start + 1));
				start++;
				if (ZERO.equals(cells[i][j])) {
					this.zeroHi = i;
					this.zeroWi = j;
				}
				if (WALL.equals(cells[i][j])) {
					this.hasWall = true;
				}
			}
		}
		this.beforeHi = -1;
		this.beforeWi = -1;
		String[][] endStyle = this.endStyle();
		this.distance = new HashMap<String, Integer>();
		this.allDistance = 0;
		for (int i = 0; i < endStyle.length; i++) {
			for (int j = 0; j < endStyle[0].length; j++) {
				if ("=".equals(endStyle[i][j]) || ZERO.equals(endStyle[i][j])) {
					continue;
				}
				int dis = this.getDistance(i, j, endStyle[i][j], this.cells);
				this.distance.put(endStyle[i][j], Integer.valueOf(dis));
				this.allDistance += dis;
			}
		}
		this.endHash = new int[endStyle.length];
		for (int i = 0; i < endStyle.length; i++) {
			this.endHash[i] = Arrays.deepHashCode(endStyle[i]);
		}
	}
	public StringBuilder getAnswer() {
		return answer;
	}
	public void setAnswer(StringBuilder answer) {
		this.answer = answer;
	}
	public void addAnswer(String val) {
		this.answer.append(val);
	}
	public String[][] getCells() {
		return cells;
	}
	public void setCells(String[][] cells) {
		this.cells = cells;
	}
	public int getWi() {
		return wi;
	}
	public void setWi(int wi) {
		this.wi = wi;
	}
	public int getHi() {
		return hi;
	}
	public void setHi(int hi) {
		this.hi = hi;
	}
	public String getBi() {
		return bi;
	}
	public void setBi(String bi) {
		this.bi = bi;
	}
	public int getSlideCnt() {
		return slideCnt;
	}
	public void setSlideCnt(int slideCnt) {
		this.slideCnt = slideCnt;
	}
	public int getZeroWi() {
		return zeroWi;
	}
	public void setZeroWi(int zeroWi) {
		this.zeroWi = zeroWi;
	}
	public int getZeroHi() {
		return zeroHi;
	}
	public void setZeroHi(int zeroHi) {
		this.zeroHi = zeroHi;
	}
	public int getBeforeWi() {
		return beforeWi;
	}
	public void setBeforeWi(int beforeWi) {
		this.beforeWi = beforeWi;
	}
	public int getBeforeHi() {
		return beforeHi;
	}
	public void setBeforeHi(int beforeHi) {
		this.beforeHi = beforeHi;
	}
	public String[][] endStyle() {
		String[][] endStyle = new String[hi][wi];
		List<String> chars = new ArrayList<String>();
		for (int i = 0; i < ALL.length; i++) {
			if (bi.indexOf(ALL[i]) >= 0) {
				chars.add(ALL[i]);
			} else {
				chars.add(WALL);
			}
		}
		int cnt = 0;
		this.endPos = new HashMap<String, int[]>();
		for (int i = 0; i < endStyle.length; i++) {
			for (int j = 0; j < endStyle[i].length; j++) {
				if (i == endStyle.length - 1 && j == endStyle[i].length - 1) {
					endStyle[i][j] = ZERO;
				} else {
					endStyle[i][j] = chars.get(cnt);
					cnt++;
					int[] pos = {i, j};
					this.endPos.put(endStyle[i][j], pos);
				}
			}
		}
		return endStyle;
	}
	public static void printArray(String[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j == array[i].length - 1) {
					System.out.println(array[i][j]);
				} else {
					System.out.print(array[i][j]);
				}
			}
		}
	}
	public boolean canUp() {
		if (this.zeroHi == 0) {
			return false;
		}
		if (this.beforeWi == this.zeroWi &&
				this.beforeHi == this.zeroHi - 1) {
			return false;
		}
		if (WALL.equals(this.cells[this.zeroHi - 1][this.zeroWi])) {
			return false;
		}
		return true;
	}
	public boolean canDown() {
		if (this.zeroHi == this.cells.length - 1) {
			return false;
		}
		if (this.beforeWi == this.zeroWi &&
				this.beforeHi == this.zeroHi + 1) {
			return false;
		}
		if (WALL.equals(this.cells[this.zeroHi + 1][this.zeroWi])) {
			return false;
		}
		return true;
	}
	public boolean canLeft() {
		if (this.zeroWi == 0) {
			return false;
		}
		if (this.beforeHi == this.zeroHi &&
				this.beforeWi == this.zeroWi - 1) {
			return false;
		}
		if (WALL.equals(this.cells[this.zeroHi][this.zeroWi - 1])) {
			return false;
		}
		return true;
	}
	public boolean canRight() {
		if (this.zeroWi == this.cells[0].length - 1) {
			return false;
		}
		if (this.beforeHi == this.zeroHi &&
				this.beforeWi == this.zeroWi + 1) {
			return false;
		}
		if (WALL.equals(this.cells[this.zeroHi][this.zeroWi + 1])) {
			return false;
		}
		return true;
	}
	public void actionUp() {
		String save = this.cells[zeroHi - 1][zeroWi];
		this.cells[zeroHi - 1][zeroWi] = this.cells[zeroHi][zeroWi];
		this.cells[zeroHi][zeroWi] = save;
		this.slideCnt++;
		this.beforeHi = zeroHi;
		this.zeroHi = zeroHi - 1;
		this.beforeWi = zeroWi;
		this.answer.append(U);
		int newDistance = this.getDistance(beforeHi, beforeWi, save);
		int oldDistance = this.distance.get(save).intValue();
		this.allDistance -= (oldDistance - newDistance);
		this.distance.put(save, newDistance);
	}
	public void actionDown() {
		String save = this.cells[zeroHi + 1][zeroWi];
		this.cells[zeroHi + 1][zeroWi] = this.cells[zeroHi][zeroWi];
		this.cells[zeroHi][zeroWi] = save;
		this.slideCnt++;
		this.beforeHi = zeroHi;
		this.zeroHi = zeroHi + 1;
		this.beforeWi = zeroWi;
		this.answer.append(D);
		int newDistance = this.getDistance(beforeHi, beforeWi, save);
		int oldDistance = this.distance.get(save).intValue();
		this.allDistance -= (oldDistance - newDistance);
		this.distance.put(save, newDistance);
	}
	public void actionLeft() {
		String save = this.cells[zeroHi][zeroWi - 1];
		this.cells[zeroHi][zeroWi - 1] = this.cells[zeroHi][zeroWi];
		this.cells[zeroHi][zeroWi] = save;
		this.slideCnt++;
		this.beforeWi = zeroWi;
		this.zeroWi = zeroWi - 1;
		this.beforeHi = zeroHi;
		this.answer.append(L);
		int newDistance = this.getDistance(beforeHi, beforeWi, save);
		int oldDistance = this.distance.get(save).intValue();
		this.allDistance -= (oldDistance - newDistance);
		this.distance.put(save, newDistance);
	}
	public void actionRight() {
		String save = this.cells[zeroHi][zeroWi + 1];
		this.cells[zeroHi][zeroWi + 1] = this.cells[zeroHi][zeroWi];
		this.cells[zeroHi][zeroWi] = save;
		this.slideCnt++;
		this.beforeWi = zeroWi;
		this.zeroWi = zeroWi + 1;
		this.beforeHi = zeroHi;
		this.answer.append(R);
		int newDistance = this.getDistance(beforeHi, beforeWi, save);
		int oldDistance = this.distance.get(save).intValue();
		this.allDistance -= (oldDistance - newDistance);
		this.distance.put(save, newDistance);
	}
	public Cell cln() {
		Cell cell = new Cell();
		cell.setAnswer(new StringBuilder(answer.toString()));
		cell.setCells(copyArray(cells));
		cell.setWi(wi);
		cell.setHi(hi);
		cell.setBi(bi);
		cell.setSlideCnt(slideCnt);
		cell.setZeroWi(zeroWi);
		cell.setZeroHi(zeroHi);
		cell.setBeforeWi(beforeWi);
		cell.setBeforeHi(beforeHi);
		cell.setEndHash(this.endHash);
		cell.setDistance(new HashMap<String, Integer>(distance));
		cell.setAllDistance(this.allDistance);
		cell.setEndPos(endPos);
		cell.setHasWall(this.hasWall);
		return cell;
	}
	private String[][] copyArray(String[][] arg) {
		String[][] ret = new String[arg.length][arg[0].length];
		for (int i = 0; i < arg.length; i++) {
			ret[i] = Arrays.copyOf(arg[i], arg[i].length);
		}
		return ret;
	}
	private int getDistance(int hi, int wi, String str, String[][] cell) {
		int distance = 0;
		int chkHi = 0;
		int chkWi = 0;
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[0].length; j++) {
				if (str.equals(cell[i][j])) {
					chkHi = i;
					chkWi = j;
					break;
				}
			}
		}
		if (hi != chkHi) {
			if (hi > chkHi) {
				distance += (hi - chkHi);
			} else {
				distance += (chkHi - hi);
			}
		}
		if (wi != chkWi) {
			if (wi > chkWi) {
				distance += (wi - chkWi);
			} else {
				distance += (chkWi - wi);
			}
		}
		if (this.hasWall && distance != 0 && distance >= 2) {
			if (hi == chkHi) {
				for (int i = (wi < chkWi ? wi : chkWi) + 1; i < (wi < chkWi ? chkWi : wi); i++) {
					if (WALL.equals(cell[hi][i])) {
						distance += 2;
						break;
					}
				}
			}
			if (wi == chkWi) {
				for (int i = (hi < chkHi ? hi : chkHi) + 1; i < (hi < chkHi ? chkHi : hi); i++) {
					if (WALL.equals(cell[i][wi])) {
						distance += 2;
						break;
					}
				}
			}
		}
		return distance;
	}
	private int getDistance(int hi, int wi, String str) {
		int distance = 0;
		int chkHi = 0;
		int chkWi = 0;
		int[] pos = this.endPos.get(str);
		chkHi = pos[0];
		chkWi = pos[1];
		if (hi != chkHi) {
			if (hi > chkHi) {
				distance += (hi - chkHi);
			} else {
				distance += (chkHi - hi);
			}
		}
		if (wi != chkWi) {
			if (wi > chkWi) {
				distance += (wi - chkWi);
			} else {
				distance += (chkWi - wi);
			}
		}
		if (this.hasWall && distance != 0 && distance >= 2) {
			if (hi == chkHi) {
				for (int i = (wi < chkWi ? wi : chkWi) + 1; i < (wi < chkWi ? chkWi : wi); i++) {
					if (WALL.equals(this.cells[hi][i])) {
						distance += 2;
						break;
					}
				}
			}
			if (wi == chkWi) {
				for (int i = (hi < chkHi ? hi : chkHi) + 1; i < (hi < chkHi ? chkHi : hi); i++) {
					if (WALL.equals(this.cells[i][wi])) {
						distance += 2;
						break;
					}
				}
			}
		}
		return distance;
	}
	public int[] getEndHash() {
		return endHash;
	}
	public void setEndHash(int endHash[]) {
		this.endHash = endHash;
	}
	public int getAllDistance() {
		return allDistance;
	}
	public void setAllDistance(int allDistance) {
		this.allDistance = allDistance;
	}
	public Map<String, Integer> getDistance() {
		return distance;
	}
	public void setDistance(Map<String, Integer> distance) {
		this.distance = distance;
	}
	public Map<String, int[]> getEndPos() {
		return endPos;
	}
	public void setEndPos(Map<String, int[]> endPos) {
		this.endPos = endPos;
	}
	public void setHasWall(boolean hasWall) {
		this.hasWall = hasWall;
	}
}
