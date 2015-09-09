/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.iter;

public interface IAudioSetEvaluateCount {

	public String getSetId();

	void setGoodCounts(int goodCounts);

	void setMidCounts(int midCounts);

	void setBadCounts(int badCounts);
}
