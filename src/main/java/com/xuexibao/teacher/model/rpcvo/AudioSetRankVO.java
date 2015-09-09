/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

import java.util.ArrayList;
import java.util.List;

public class AudioSetRankVO extends RpcVO {
	public Item data = new Item();

	public static class Item {
		public List<String> data = new ArrayList<String>();
		public Integer pageno;
		public Integer totalcount;
	}
}
