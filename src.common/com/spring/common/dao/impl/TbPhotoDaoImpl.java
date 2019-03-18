package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbPhotoDao;
import com.spring.common.entity.TbPhoto;

@Repository("tbPhotoDao")
public class TbPhotoDaoImpl extends BaseDaoMysqlImpl<TbPhoto, Long> implements TbPhotoDao {

	public TbPhotoDaoImpl() {
		super(TbPhoto.class);
	}

}
