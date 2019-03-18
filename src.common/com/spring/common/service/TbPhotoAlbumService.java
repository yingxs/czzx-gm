package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbPhotoAlbumDao;
import com.spring.common.entity.TbPhotoAlbum;

public interface TbPhotoAlbumService extends BaseService<TbPhotoAlbum, Long> {
	
	public TbPhotoAlbumDao getTbPhotoAlbumDao();
	
	public List<TbPhotoAlbum> getAlbumAll(String type);

}
