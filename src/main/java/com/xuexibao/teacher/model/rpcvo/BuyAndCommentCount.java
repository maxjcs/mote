/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;


public class BuyAndCommentCount extends RpcVO {
	public Item data=new Item();

	public static class Item {
		public int buyCount;
		public int commentCount;
	}
}
