package com.spring.common.service;

import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbPhotoDao;
import com.spring.common.entity.TbPhoto;

public interface TbPhotoService extends BaseService<TbPhoto, Long> {
	
	public TbPhotoDao getTbPhotoDao();

	public List<TbPhoto> searchp(String string);

	public List<TbPhoto> findByAlbumId(String id);

	public void setCoverPhoto(String photoId, String string);

}
