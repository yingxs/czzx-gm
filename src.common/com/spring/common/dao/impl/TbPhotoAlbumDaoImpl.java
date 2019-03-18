package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbPhotoAlbumDao;
import com.spring.common.entity.TbPhotoAlbum;

@Repository("tbPhotoAlbumDao")
public class TbPhotoAlbumDaoImpl extends BaseDaoMysqlImpl<TbPhotoAlbum, Long> implements TbPhotoAlbumDao {
	
    public TbPhotoAlbumDaoImpl(){
    	 super(TbPhotoAlbum.class);
    }

}
