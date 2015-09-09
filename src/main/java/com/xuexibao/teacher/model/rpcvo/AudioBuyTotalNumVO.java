package com.xuexibao.teacher.model.rpcvo;

import java.util.HashMap;
import java.util.Map;

import com.xuexibao.teacher.model.rpcvo.AudioSetEvaluateCount.Item;

public class AudioBuyTotalNumVO extends RpcVO{
	public Map<String, Item> data = new HashMap<String, Item>();
	public static class Item {
		private int price;
		private int totalNum;
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getTotalNum() {
			return totalNum;
		}
		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
		}
		
	}
	

}
