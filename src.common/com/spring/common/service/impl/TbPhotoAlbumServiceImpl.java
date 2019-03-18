package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.StringUtil;
import com.spring.common.dao.TbPhotoAlbumDao;
import com.spring.common.entity.TbColumn;
import com.spring.common.entity.TbPhotoAlbum;
import com.spring.common.service.TbPhotoAlbumService;

@Service("tbPhotoAlbumServiceImpl")
@Transactional
public class TbPhotoAlbumServiceImpl extends BaseServiceImpl<TbPhotoAlbum, Long> implements TbPhotoAlbumService {

	@Resource
	TbPhotoAlbumDao tbPhotoAlbumDao;

	@Override
	public BaseDao<TbPhotoAlbum, Long> getGenericDao() {
		return tbPhotoAlbumDao;
	}

	@Override
	public TbPhotoAlbumDao getTbPhotoAlbumDao() {
		return tbPhotoAlbumDao;
	}

	@Override
	public List<TbPhotoAlbum> getAlbumAll(String type) {
		String sql = "SELECT * FROM tb_photo_album WHERE tpa_status <> 0 ";
		if(!StringUtil.isEmptyNull(type)){
			sql += " and tpa_type = "+type;
		}
		return tbPhotoAlbumDao.search(sql, null);
	}

}
