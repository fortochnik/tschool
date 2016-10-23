package tstore.service;

import tstore.model.ImageEntity;
import tstore.model.ProductEntity;

import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
public interface ImageService {
    void save(String url, Integer productId);

    ImageEntity getMain(int productId);
    List<ImageEntity> getAll(int productId);
}
