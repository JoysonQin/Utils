package utils;

import com.lianjia.plats.store.link.utils.entity.DataException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 财务校验
 * @author nong 2717-10-23
 */
public class PaymenCheckUtil {

    /**
     * 获取付款需要校验的字段名
     * @return
     */
    public static final List<String> getPaymentPlanFieldNames() {
        return Arrays.asList(
                "paymentPlanCode",
                "amount",
                "payeeName",
                "bankAccount",
                "bankSubbranch",
                "type",
                "storeCode"
        );
    }

    /**
     * 获取供应商需要校验的字段名
     * @return
     */
    public static final List<String> getSupplierDTOFieldNames() {
        return Arrays.asList(
                "supplierNo",
                "supplierName",
                "supplierCategoryNo"
        );
    }

    /**
     * 获取银行信息需要校验的字段名
     * @return
     */
    public static final List<String> getBankBranchDetailDTOFieldNames() {
        return Arrays.asList(
                "cnapsNo",
                "bankNameBrief",
                "provinceStr",
                "cityStr"
        );
    }

    /**
     * 获取收款需要校验的字段名
     * @return
     */
    public static final List<String> getCollectionsPlanFieldNames() {
        return Arrays.asList(
                "paymentPlanCode",
                "regionCode",
                "amount",
                "bankAccountId",
                "payerSupplier",
                "payerName",
                "payPreTime",
                "type"
        );
    }

    /**
     * 获取公司账户信息需要校验的字段名
     * @return
     */
    public static final List<String> getCompanyEbsAccountDTOFieldNames() {
        return Arrays.asList(
                "bankAccountId",
                "bankAccountName",
                "bankAccountNo",
                "bankName",
                "bankBranchName"
        );
    }

    /**
     * 校验必要字段
     * @param reference
     * @param fieldNames
     * @param result
     * @param <T>
     * @return
     */
    public static <T> boolean checkFieldNames(T reference, List<String> fieldNames, StringBuilder result) {
        DataException checkResult = ExceptionCheckUtil.checkObjectPropertyNull(reference, fieldNames);
        if (Objects.equals(checkResult.getCode(), DataException.DataExceptionEnum.OK.getCode())) {
            return true;
        } else {
            result.append("  ").append(checkResult.getMessage());
            return false;
        }
    }
}
