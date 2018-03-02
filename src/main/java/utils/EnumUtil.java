package utils;

import org.apache.commons.lang3.ArrayUtils;
import org.nutz.lang.Mirror;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author jerry.hu 补充规约
 */
public class EnumUtil {
	
	/**
	 * 把枚举结果转换为list
	 * 改方法依赖nutz，如果使用原生的方法，则需要改写getFields的逻辑
	 * 如：List<Map<String, Object>> list = enumToListMap(KyInquiry.FlagPrivateEnum.values());
	 * @param enums
	 * @return
	 */
	public static List<Map<String, Object>> enumToListMap(Enum... enums) {
	    if (ArrayUtils.isEmpty(enums)) {
	        return Collections.emptyList();
	    }

	    Mirror<?> mirror = Mirror.me(enums[0].getClass());
	    Field[] fields = mirror.getFields();

	    List<Map<String, Object>> result = new ArrayList<>();

	    for (Enum anEnum : enums) {
	        Map<String, Object> map = new HashMap<>();
	        for (Field field : fields) {
	            map.put("name", anEnum.name());
	            map.put(field.getName(), mirror.getValue(anEnum, field.getName()));
	        }
	        result.add(map);
	    }

	    return result;
	}

}
