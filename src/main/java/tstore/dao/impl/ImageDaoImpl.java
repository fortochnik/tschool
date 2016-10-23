package tstore.dao.impl;

import org.apache.log4j.Logger;
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
    final static Logger logger = Logger.getLogger(ImageDaoImpl.class);

    public ImageEntity getImage(int productId, int imageIndex) {
        ImageEntity imageEntity = null;

        String hql = "from ImageEntity where product.id = :productId and image = :imageUrl order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("productId", productId).setParameter("imageUrl", MessageFormat.format("{0}-image{1}.jpg", productId, imageIndex));
        try {
            imageEntity = (ImageEntity) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(MessageFormat.format("No result Image by : {0}  productId and {1} image index", productId, imageIndex), e);
            return null;
        } catch (NonUniqueResultException e) {
            logger.error(MessageFormat.format("Non unique Image by : {0} productId and {1} image index", productId, imageIndex), e);
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
