package com.sys.core.query;

import java.io.Serializable;

/**
 * 分页定义
 * 
 * @author kain
 *
 */
public class Pagenation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6583010576690242956L;

	/**
	 * 每页数量
	 */
	private int pageSize = 10;

	/**
	 * 当前页码
	 */
	private int pageIndex = 1;

	/**
	 * 记录总数
	 */
	private long total;

	/**
	 * 页总数
	 */
	private int pageCount;

	public Pagenation() {

	}
	
	/**
	 * 
	 * @param pageIndex default 1
	 * @param pageSize
	 */
	public Pagenation(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int rows) {
		this.pageSize = rows;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public boolean next() {
		Integer val = this.pageIndex + 1;
		if (val > pageCount)
			return false;
		this.pageIndex = val;
		return true;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		this.pageCount = (int) Math.ceil(total / pageSize);
	}

	public int getPageCount() {
		return pageCount;
	}

	public long getStartRowNum() {
		return (pageIndex - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "pageSize：" + this.pageSize + " pageIndex:" + this.pageIndex + " total:" + this.total + " pageCount" + this.pageCount;
	}
}
