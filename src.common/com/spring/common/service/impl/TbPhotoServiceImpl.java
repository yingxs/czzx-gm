package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.StringUtil;
import com.spring.common.dao.TbPhotoDao;
import com.spring.common.dao.impl.TbAdvertiseCatalogDaoImpl;
import com.spring.common.entity.TbPhoto;
import com.spring.common.entity.TbPhotoAlbum;
import com.spring.common.service.TbPhotoAlbumService;
import com.spring.common.service.TbPhotoService;

@Service("tbPhotoService")
@Transactional
public class TbPhotoServiceImpl extends BaseServiceImpl<TbPhoto, Long> implements TbPhotoService {

	@Resource
	TbPhotoDao tbPhotoDao;
	@Resource
	TbPhotoAlbumService tbPhotoAlbumService;
	
	@Override
	public BaseDao<TbPhoto, Long> getGenericDao() {
		return tbPhotoDao;
	}

	@Override
	public TbPhotoDao getTbPhotoDao() {
		return tbPhotoDao;
	}

	@Override
	public List<TbPhoto> searchp(String id) {
		if(id!=null && id.trim().length()!=0){
			StringBuffer sbSql = new StringBuffer();
			sbSql.append("SELECT * FROM tb_photo ");
			sbSql.append("WHERE tp_album_id="+id+" and tp_status != 0 ORDER BY tp_id DESC");
			return tbPhotoDao.search(sbSql.toString(), null);
		}
		return null;
	}

	@Override
	public List<TbPhoto> findByAlbumId(String id) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" SELECT t.* , p.tpa_type as type FROM tb_photo as t ");
		sbSql.append(" LEFT JOIN tb_photo_album as p ON tpa_id = tp_album_id ");
		sbSql.append(" WHERE tp_album_id="+id+" and tp_status != 0 ORDER BY tp_addtime DESC ");
		return tbPhotoDao.search(sbSql.toString(), null);
	}

	@Override
	@Transactional
	public void setCoverPhoto(String albumId, String strId) {
		try {
			TbPhoto tbPhoto = tbPhotoDao.get(Long.valueOf(strId));
			TbPhotoAlbum tbPhotoAlbum = tbPhotoAlbumService.findById(tbPhoto.getTpAlbumId());
			//先将该文件夹下所有封面图片清零
			StringBuffer sbSql = new StringBuffer();
			sbSql.append(" UPDATE tb_photo SET tp_coverPhoto = 0 WHERE tp_status <> 0 and tp_album_id in ( SELECT tpa_id from tb_photo_album WHERE tpa_type = "+tbPhotoAlbum.getTpaType()+" AND tpa_status = 1) ");
			tbPhotoDao.update(sbSql.toString(), null);
			//设置封面图片
			tbPhoto.setTpCoverPhoto(1);
			tbPhotoDao.update(tbPhoto);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
		}
	}

}
