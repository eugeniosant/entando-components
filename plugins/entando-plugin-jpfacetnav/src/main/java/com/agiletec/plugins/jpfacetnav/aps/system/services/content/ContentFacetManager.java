/*
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.agiletec.plugins.jpfacetnav.aps.system.services.content;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.searchengine.ICmsSearchEngineManager;
import com.agiletec.plugins.jacms.aps.system.services.searchengine.IIndexerDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.entando.entando.aps.system.services.searchengine.FacetedContentsResult;
import org.entando.entando.aps.system.services.searchengine.SearchEngineFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author E.Santoboni
 */
public class ContentFacetManager extends AbstractService implements IContentFacetManager {

	private static final Logger _logger = LoggerFactory.getLogger(ContentFacetManager.class);
	
	@Override
	public void init() throws Exception {
		_logger.debug("{} ready", this.getName());
	}
	
	@Override
	@Deprecated
	public List<String> loadContentsId(List<String> contentTypeCodes, List<String> facetNodeCodes, List<String> groupCodes) throws ApsSystemException {
		FacetedContentsResult result = this.getFacetResult(contentTypeCodes, facetNodeCodes, groupCodes);
		return result.getContentsId();
	}
	
	@Override
	@Deprecated
	public Map<String, Integer> getOccurrences(List<String> contentTypeCodes, List<String> facetNodeCodes, List<String> groupCodes) throws ApsSystemException {
		FacetedContentsResult result = this.getFacetResult(contentTypeCodes, facetNodeCodes, groupCodes);
		return result.getOccurrences();
	}
	
	@Override
	public FacetedContentsResult getFacetResult(List<String> contentTypeCodes, List<String> facetNodeCodes, List<String> groupCodes) throws ApsSystemException {
		try {
			ICmsSearchEngineManager searchEngineManager = (ICmsSearchEngineManager) this.getBeanFactory().getBean(JacmsSystemConstants.SEARCH_ENGINE_MANAGER);
			ICategoryManager categoryManager = (ICategoryManager) this.getBeanFactory().getBean(SystemConstants.CATEGORY_MANAGER);
			SearchEngineFilter[] filters = new SearchEngineFilter[0];
			if (null != contentTypeCodes && !contentTypeCodes.isEmpty()) {
				for (int i = 0; i < contentTypeCodes.size(); i++) {
					String contentType = contentTypeCodes.get(i);
					SearchEngineFilter newFilter = new SearchEngineFilter(IIndexerDAO.CONTENT_TYPE_FIELD_NAME, contentType);
					filters = this.addFilter(filters, newFilter);
				}
			}
			List<Category> categories = new ArrayList<Category>();
			if (null != facetNodeCodes && !facetNodeCodes.isEmpty()) {
				for (int i = 0; i < facetNodeCodes.size(); i++) {
					String categoryCode = facetNodeCodes.get(i);
					Category category = categoryManager.getCategory(categoryCode);
					if (null != category) {
						categories.add(category);
					}
				}
			}
			return searchEngineManager.searchFacetedEntities(filters, categories, groupCodes);
		} catch (Throwable t) {
			_logger.error("Error loading facet result", t);
			throw new ApsSystemException("Error loading facet result", t);
		}
	}
	
	private SearchEngineFilter[] addFilter(SearchEngineFilter[] filters, SearchEngineFilter filterToAdd) {
		int len = filters.length;
		SearchEngineFilter[] newFilters = new SearchEngineFilter[len + 1];
		for(int i=0; i < len; i++){
			newFilters[i] = filters[i];
		}
		newFilters[len] = filterToAdd;
		return newFilters;
	}
	
}
