package utils;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author dangdandan on 16/5/16.
 */
public class Pagination<E> implements Serializable {

    private static final long serialVersionUID = 992459402606753536L;

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final Pagination EMPTY_PAGE = new Builder(0).build();

    /**
     * 总行数
     */
    private int totalCount;

    /**
     * 每页行数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 上一页
     */
    private int previousPage;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    /**
     * 当前页开始行, 不是数据库ID，如果要数据库ID，可以自行设置
     */
    private int pageStartRow;

    /**
     * 当前页结束行, 不是数据库ID，如果要数据库ID，可以自行设置
     */
    private int pageEndRow;
    /**
     * 绑定的实体list
     */
    private List<E> list;

    private Map ext;

    private Pagination(Builder<E> eBuilder) {
        this.totalCount = eBuilder._totalCount;
        this.currentPage = eBuilder._currentPage;
        this.pageSize = eBuilder._pageSize;
        this.setList(eBuilder._list);

        this.setTotalRows(totalCount);
        this.setCurrentPage2(currentPage);
    }

    private Pagination() {
        super();
    }

    public static <E> Builder<E> builder(int totalCount) {
        return new Builder<E>(totalCount);
    }

    public static <E> Pagination<E> emptyPage() {
        return EMPTY_PAGE;
    }

    public Map getExt() {
        return ext;
    }

    public void setExt(Map ext) {
        this.ext = ext;
    }

    public <T> Pagination<T> transform(com.google.common.base.Function<E, T> function) {

        return new Builder<T>(this.getTotalCount(), this.getPageSize())
                .currentPage(this.getCurrentPage())
                .list(Lists.transform(this.getList(), function))
                .build();
    }

    public String description() {

        StringBuilder description = new StringBuilder().append("Pagination total_rows: ").append(this.getTotalCount())
                .append(" total_pages:").append(this.getTotalPages()).append(" current_page:").append(this.currentPage)
                .append(" previous：").append(this.hasPrevious).append(" next:").append(this.hasNext)
                .append(" start_row:").append(this.pageStartRow).append(" end_row:").append(this.pageEndRow);
        return description.toString();
    }

    /**
     * 设置总行数。
     *
     * @param rows 总行数。
     */
    private void setTotalRows(int rows) {

        if (rows < 0) {
            totalCount = 0;
        } else {
            totalCount = rows;
        }

        if (totalCount % pageSize == 0) {
            totalPages = totalCount / pageSize;
        } else {
            totalPages = totalCount / pageSize + 1;
        }
    }

    /**
     * 设置当前页数。
     *
     * @param curPage
     */
    private void setCurrentPage2(int curPage) {

        if (curPage <= 0) {
            currentPage = 1;
        } else {
            currentPage = curPage;
        }

        if (currentPage <= 1) {
            hasPrevious = false;
        } else {
            hasPrevious = true;
        }

        if (currentPage == totalPages || totalPages == 0) {
            hasNext = false;
        } else {
            hasNext = true;
        }

        nextPage = currentPage + 1;
        previousPage = currentPage - 1;
        // 记录索引从0开始
        pageStartRow = (currentPage - 1) * pageSize;
        pageEndRow = pageStartRow + pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public static final class Builder<E> {
        private int _totalCount;

        /**
         * 每页行数
         */
        private int _pageSize = 20;

        /**
         * 当前页码
         */
        private int _currentPage = 1;

        /**
         * 绑定的实体list
         */
        private List<E> _list = Collections.emptyList();

        public Builder(int totalCount) {
            this(totalCount, DEFAULT_PAGE_SIZE);
        }

        public Builder(Integer totalCount, Integer pageSize) {
            if (totalCount < 0) {
                throw new IllegalArgumentException("totalCount must be larger than 0 or equals 0");
            }
            if (pageSize < 0) {
                throw new IllegalArgumentException("totalCount must be larger than 0 or equals 0");
            }
            this._totalCount = totalCount;
            this._pageSize = pageSize;
        }

        public Builder<E> currentPage(int currentPage) {
            if (currentPage > 0) {
                this._currentPage = currentPage;
            }
            int totalPages = 0;
            if (_totalCount % _pageSize == 0) {
                totalPages = _totalCount / _pageSize;
            } else {
                totalPages = _totalCount / _pageSize + 1;
            }
            if (currentPage > totalPages) {
                this._currentPage = totalPages;
            }
            return this;
        }

        public Builder<E> list(List<E> data) {
            if (data != null) {
                this._list = data;
            }
            return this;
        }

        public Pagination<E> build() {
            return new Pagination<E>(this);
        }
    }

}
