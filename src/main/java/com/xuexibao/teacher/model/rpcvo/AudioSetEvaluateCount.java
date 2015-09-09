/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

import java.util.HashMap;
import java.util.Map;

public class AudioSetEvaluateCount extends RpcVO {
	public Map<String, Item> data = new HashMap<String, Item>();

	public static class Item {
		private int goodCounts;
		private int midCounts;
		private int badCounts;
		public int getGoodCounts() {
			return goodCounts;
		}
		public void setGoodCounts(int goodCounts) {
			this.goodCounts = goodCounts;
		}
		public int getMidCounts() {
			return midCounts;
		}
		public void setMidCounts(int midCounts) {
			this.midCounts = midCounts;
		}
		public int getBadCounts() {
			return badCounts;
		}
		public void setBadCounts(int badCounts) {
			this.badCounts = badCounts;
		}
	}
}
