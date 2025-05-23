/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * Gebo.ai comment agent
 * A utility class to manage pagination information and convert it to a Spring Pageable object.
 */
public class DataPage implements Serializable {
	
	/**
	 * Serialization identifier for the DataPage class.
	 */
	private static final long serialVersionUID = 2713732311188615031L;
	
	// Current page number, initialized to null indicating an unspecified page number.
	private Integer page = null;
	
	// Size of the page, defining how many records it contains, initialized to null.
	private Integer pageSize = null;
	
	// Total number of records available.
	private Integer numrecords = null;
	
	// List of sorting orders applied to the data.
	private List<Order> sort = new ArrayList<Sort.Order>();

	/**
	 * Default constructor initializing the DataPage object.
	 */
	public DataPage() {

	}

	/**
	 * @return the current page number.
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * Sets the current page number.
	 * 
	 * @param page the page number to set.
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the size of the page.
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the size of the page.
	 * 
	 * @param pageSize the size of the page to set.
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the total number of records available.
	 */
	public Integer getNumrecords() {
		return numrecords;
	}

	/**
	 * Sets the total number of records available.
	 * 
	 * @param numrecords the total number of records to set.
	 */
	public void setNumrecords(Integer numrecords) {
		this.numrecords = numrecords;
	}

	/**
	 * Converts the current pagination and sorting information into a Pageable object.
	 * 
	 * @return a Pageable object representing the pagination state.
	 */
	public Pageable toPageable() {
		if (page == null && pageSize == null && sort.isEmpty())
			return Pageable.unpaged();
		if (page == null && pageSize == null && !sort.isEmpty())
			return Pageable.unpaged(Sort.by(sort));
		if (pageSize == null)
			pageSize = 20;
		if (page == null)
			page = 0;

		return sort != null && sort.isEmpty() ? Pageable.ofSize(pageSize).withPage(page)
				: PageRequest.of(pageSize, page, Sort.by(sort));

	}

	/**
	 * @return the list of sorting orders.
	 */
	public List<Order> getSort() {
		return sort;
	}

	/**
	 * Sets the list of sorting orders.
	 * 
	 * @param sort the list of sorting orders to set.
	 */
	public void setSort(List<Order> sort) {
		this.sort = sort;
	}

}