package com.xuexibao.teacher.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xuexibao.teacher.model.Grade;

public class GradeUtil {
	public static String getGradeString(String gradeIds, String appendStr) {
		if (StringUtils.isEmpty(gradeIds)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();

		List<Integer> sorts = new ArrayList<Integer>();
		for (String gradeId : gradeIds.split(",")) {
			if (!StringUtils.isEmpty(gradeId)) {
				sorts.add(Integer.parseInt(gradeId.trim()));
			}
		}

		Collections.sort(sorts);

		for (int i = sorts.size() - 1; i >= 0; i--) {
			String value = Grade.GRADE_MAP.get(sorts.get(i).toString());
			if (!StringUtils.isEmpty(value)) {
				sb.append(value).append("、");
			}
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		sb.append(appendStr);

		return sb.toString();
	}

	public static String reverseGrade(String gradeIds) {
		if (StringUtils.isEmpty(gradeIds)) {
			return "";
		}

		List<String> list = Arrays.asList(gradeIds.split(","));
		
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String b1, String b2) {
				Integer i1 = Integer.parseInt(b1.trim());
				Integer i2 = Integer.parseInt(b2.trim());
				if (i1 < i2) {
					return 1;
				} else if (i1 == i2) {
					return 0;
				}
				return -1;
			}

		});
		return StringUtils.join(list, ",");
	}

}
