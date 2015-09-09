package test;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.expression.ParseException;

import com.xuexibao.teacher.model.Teacher;

/**
 * @author oldlu
 */

public class Test {
	public static final String token = "5eba8abaf63e2dff94aee22556b9487a7347a5d110f427a1e9396c12ace99ae070400569e99351f7bd028d80dfa054fc18823a743afa88c3f96cc62fa5e093bfc38e5298b3a734629e5707e39a432cbe792a2daccc27a9b5677684f78657357c";
	public static final String user_agent = "{'app_name':'学习宝','app_ver':'2.3.5','dev_model':'HM NOTE 1LTETD','sys_name':'android_os','sys_ver':'4.4.2','version':'1'}";

	public static void main(String[] args) throws ParseException, IOException {

		/**
		 * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
		 */
		ObjectMapper mapper = new ObjectMapper();
		Teacher teacher=new Teacher();
		teacher.setCreateTime(new Date());
		String string=mapper.writeValueAsString(teacher);
		
		System.out.println(string);
	}
}
