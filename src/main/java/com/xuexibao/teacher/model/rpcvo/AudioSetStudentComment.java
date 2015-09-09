/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

import java.util.ArrayList;
import java.util.List;

public class AudioSetStudentComment extends RpcVO {
	public List<Item> data = new ArrayList<Item>();

	public static class Item {
		public boolean hasComment;
		public int type;
		public String comment;
		public String exercisesId;
		public long time;
	}
}
