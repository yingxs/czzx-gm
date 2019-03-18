/**    
* @{#} TbInformationCataDaoImpl.java Create on 2015��8��17�� ����3:33:49    
* Copyright (c) 2015.    
*/
package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbVersionDao;
import com.spring.common.entity.TbVersion;

/**
 * @author <a href="mailto:liwei.flyf@gmail.com">author</a>
 * @version 1.0
 * @description
 */
@Repository("tbVersionDao")
public class TbVersionDaoImpl extends BaseDaoMysqlImpl<TbVersion, Integer>implements TbVersionDao {

	public TbVersionDaoImpl() {
		super(TbVersion.class);
	}
}
