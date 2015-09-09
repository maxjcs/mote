/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

import java.util.ArrayList;
import java.util.List;

public class AudioEvaluateCount extends RpcVO {
	public List<Item> data = new ArrayList<Item>();

	public static class Item {
		public String audioId;
		public int goodCounts;
		public int midCounts;
		public int badCounts;
	}
}
