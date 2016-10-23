package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.ImageDao;
import tstore.model.ImageEntity;
import tstore.model.OrderEntity;
import tstore.model.enums.PaymentStatus;

import javax.persistence.NoResultException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public class ImageDaoImpl extends GenericDaoImpl<ImageEntity, Integer> implements ImageDao {

    public ImageEntity getImage(int productId, int imageIndex) {
        ImageEntity imageEntity = null;

        String hql = "from ImageEntity where product.id = :productId and image = :imageUrl order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("productId", productId).setParameter("imageUrl", MessageFormat.format("{0}-image{1}.jpg", productId, imageIndex));
        try {
            imageEntity = (ImageEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
//            todo Change exception (?)
//            todo add logging
        }

        return imageEntity;
    }

    public List<ImageEntity> getImage(int productId) {
        ImageEntity imageEntity = null;

        String hql = "from ImageEntity where product.id = :productId order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("productId", productId);
        return query.list();
    }
}
