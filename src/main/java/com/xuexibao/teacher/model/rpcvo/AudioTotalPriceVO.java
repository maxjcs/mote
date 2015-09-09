/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

public class AudioTotalPriceVO extends RpcVO {
	public Item data=new Item();

	public static class Item {
		public int price;
		public String productId;
		public int totalNum;
	}
}
