package utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.List;

/**
 * @author dangdandan on 17/7/11.
 */
public class ListUtils {

    /**
     * 均匀分割list
     *
     * @param totalSize 总长度
     * @param step      步长
     * @return rangeList
     */
    public static List<Range<Integer>> divideList(int totalSize, int step) {
        int curPos = 0;

        int tempRecord = 0;

        List<Range<Integer>> ranges = Lists.newArrayList();

        while (curPos < totalSize) {
            Range<Integer> range;
            tempRecord = curPos + step;
            if (tempRecord <= totalSize) {
                range = Range.closed(curPos, curPos + step);
                ranges.add(range);
            } else {
                range = Range.closed(curPos, totalSize);
                ranges.add(range);
                break;
            }
            curPos += step;

        }

        return ranges;
    }
}
