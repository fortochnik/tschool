package tstore.dao;

import tstore.model.ImageEntity;

import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public interface ImageDao extends GenericDao<ImageEntity, Integer> {
    ImageEntity getImage(int productId, int imageIndex);

    List<ImageEntity> getImage(int productId);
}
