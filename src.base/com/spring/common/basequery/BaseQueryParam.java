package com.spring.common.basequery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.spring.base.utils.JsonUtils;
import com.spring.common.entity.PageBean;

/**
 * 
 * @author 1695446827@qq.com
 * 
 */
public class BaseQueryParam<T> extends PageBean<T> implements Serializable {

	private static final long serialVersionUID = 6114942909562277980L;

	protected String startDate;

	protected String endDate;

	protected Long userId;

	protected Integer rows = 10;

	protected Integer page = 1;

	protected String keywords;

	protected Long total;

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		super.setPageSize(rows);
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		super.setPageNo(page);
		this.page = page;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String resultJson(PageBean<T> page) {
		Map<String, Object> mMap = new HashMap<String, Object>();
		mMap.put("rows", page.getList());
		mMap.put("total", page.getRowCount());
		return JsonUtils.writeValueAsString(mMap);
	}

	@Override
	public String toString() {
		return "BaseQueryParam [userId=" + userId + ", rows=" + rows + ", page=" + page + ", keywords=" + keywords + ", total=" + total + ", getUserId()=" + getUserId() + ", getRows()=" + getRows() + ", getPage()=" + getPage() + ", getKeywords()=" + getKeywords() + ", getTotal()=" + getTotal() + "]";
	}

}
