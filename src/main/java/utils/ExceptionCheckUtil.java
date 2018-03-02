package utils;

import com.lianjia.plats.store.link.utils.entity.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author fengchen
 * @date 2017/5/31
 * @project
 * @description
 */
public class ExceptionCheckUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCheckUtil.class);

    /**
     * @param expression
     * @param code
     * @param msg
     * @throws DataException 检查表达式
     */
    public static void check(boolean expression, Integer code, String msg) throws DataException {
        if (!expression) {
            throw new DataException(code, msg);
        }
    }

    /**
     * @param num
     * @param code
     * @param msg
     * @throws DataException 检查Integer
     */
    public static void checkNumber(Integer num, Integer code, String msg) throws DataException {
        if (num == null || num < 0) {
            throw new DataException(code, msg);
        }
    }

    /**
     * @param num
     * @param msg
     * @throws DataException 检查Integer
     */
    public static void checkNumber(Long num, String msg) throws DataException {
        if (num == null || num < 0) {
            throw new DataException(DataException.DataExceptionEnum.number_error_exc.getCode(), msg);
        }
    }

    /**
     * @param num
     * @param msg
     * @throws DataException 检查Float
     */
    public static void checkNumber(Float num, String msg) throws DataException {
        if (num == null || num < 0) {
            throw new DataException(DataException.DataExceptionEnum.number_error_exc.getCode(), msg);
        }
    }

    /**
     * @param num
     * @param msg
     * @throws DataException 检查Double
     */
    public static void checkNumber(Double num, String msg) throws DataException {
        if (num == null || num < 0) {
            throw new DataException(DataException.DataExceptionEnum.number_error_exc.getCode(), msg);
        }
    }



    /**
     * @param num
     * @param code
     * @param msg
     * @throws DataException 检查Long
     */
    public static void checkNumber(Long num, Integer code, String msg) throws DataException {
        if (num == null || num < 0) {
            throw new DataException(code, msg);
        }
    }

    /**
     * @param str
     * @param code
     * @param msg
     * @throws DataException 检查String
     */
    public static void checkString(String str, Integer code, String msg) throws DataException {
        if (StringUtils.isEmpty(str)) {
            throw new DataException(code, msg);
        }
    }

    /**
     * @param list
     * @throws DataException 检查List是否为空
     */
    public static void checkList(List list) throws DataException {
        if (list == null || list.size() == 0) {
            throw new DataException(DataException.DataExceptionEnum.list_null_exc);
        }
    }

    /**
     * @param list
     * @throws DataException 检查List是否为空
     */
    public static void checkList(List list, String msg) throws DataException {
        if (list == null || list.size() == 0) {
            throw new DataException(DataException.DataExceptionEnum.list_null_exc.getCode(), msg);
        }
    }

    /**
     * @param reference
     * @return
     * @throws DataException 检查List是否为空
     */
    public static <T> T checkObjectNull(T reference) throws DataException {
        if (reference == null) {
            throw new DataException(DataException.DataExceptionEnum.object_null_exc);
        } else {
            return reference;
        }
    }

    /**
     * @param reference
     * @param msg
     * @return
     * @throws DataException 检查List是否为空
     */
    public static <T> T checkObjectNull(T reference, String msg) throws DataException {
        if (reference == null) {
            throw new DataException(DataException.DataExceptionEnum.object_null_exc.getCode(), msg);
        } else {
            return reference;
        }
    }

    /**
     * @param reference
     * @param properties 属性
     * @return
     * @throws DataException 检查对象的哪些属性是否为空
     */
    public static <T> void checkObjectPropertyNull(T reference, String... properties) throws DataException {
        if (properties == null || properties.length <= 0) {
            throw new DataException(DataException.DataExceptionEnum.object_null_exc);
        }
        for (String str : properties) {
            doCheckObjectPropertyNull(reference, str);
        }
    }

    /**
     * @param reference
     * @param properties 属性
     * @return
     * @throws DataException 检查对象的哪些属性是否为空
     */
    public static <T> DataException checkObjectPropertyNull(T reference, List<String> properties) throws DataException {
        if(Objects.isNull(reference)){
            return new DataException(DataException.DataExceptionEnum.object_null_exc.getCode(), "对象不能为空");
        }
        if (CollectionUtils.isEmpty(properties)) {
            LOGGER.error(DataException.DataExceptionEnum.object_null_exc.getMessage());
            return new DataException(DataException.DataExceptionEnum.object_null_exc);
        }
        try {
            properties.forEach(str -> doCheckObjectPropertyNull(reference, str));
            return new DataException(DataException.DataExceptionEnum.OK);
        } catch (DataException d) {
            LOGGER.error(d.getMessage());
            return d;
        }
    }

    private static <T> void doCheckObjectPropertyNull(T reference, String str) {
        try {
            Field f = getDeclaredField(reference, str);
            if (f == null) {
                throw new DataException(DataException.DataExceptionEnum.object_null_exc.getCode(), "对象:" + reference.getClass().getSimpleName() + "的属性:" + str + "不存在");
            }
            f.setAccessible(true);
            if (f.get(reference) == null) {
                throw new DataException(DataException.DataExceptionEnum.object_null_exc.getCode(), "参数:" + str + "不能为空");
            }
        } catch (IllegalAccessException e) {
            throw new DataException(DataException.DataExceptionEnum.object_null_exc.getCode(), "对象:" + reference.getClass().getSimpleName() + "的属性:" + str + "不存在");
        }
    }

    /**
     * @param object
     * @param fieldName
     * @return 从自己和父类里查找字段
     */
    private static Field getDeclaredField(Object object, String fieldName) {
        Field field;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return null;
    }
}
