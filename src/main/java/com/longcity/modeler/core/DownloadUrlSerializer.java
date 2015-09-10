/**
 * @author oldlu
 */
package com.longcity.modeler.core;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.longcity.modeler.util.PropertyUtil;

@Deprecated
public class DownloadUrlSerializer extends JsonSerializer<String> {

	public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(!StringUtils.isEmpty(value)){
			jgen.writeString(PropertyUtil.getDownloadUrl() + value);
		}else{
			jgen.writeString("");
		}
	}
}