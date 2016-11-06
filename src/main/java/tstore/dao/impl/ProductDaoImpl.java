package tstore.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tstore.dao.CategoryDao;
import tstore.dao.ProductDao;
import tstore.model.CategoryEntity;
import tstore.model.ProductEntity;
import tstore.model.enums.BasketOrderState;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 28.09.2016.
 */
@Repository
public class ProductDaoImpl extends GenericDaoImpl<ProductEntity, Integer> implements ProductDao {
    final static Logger logger = Logger.getLogger(ProductDaoImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    public List<ProductEntity> getByCategoryId(int categoryId) {
        String hql = "from ProductEntity where category.id = :categoryId";
        Query query = getCurrentSession().createQuery(hql).setParameter("categoryId", categoryId);
        return (List<ProductEntity>)query.list();
    }

    public List<ProductEntity> findByCriteria(Map<String, String> searchParameters) {

        Criteria criteria = getCurrentSession().createCriteria(ProductEntity.class);
        if(!searchParameters.get("category").equals("0"))
        {
            int categoryId = Integer.parseInt(searchParameters.get("category"));
            CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, categoryId);
            criteria.add(Restrictions.eq("category",categoryEntity));
        }

        if (!searchParameters.get("name").equals("")){
            criteria.add(Restrictions.like("name", searchParameters.get("name"), MatchMode.ANYWHERE));
        }

        if (!searchParameters.get("priceMin").equals("")){
            BigDecimal priceMin= BigDecimal.valueOf(-1);
            try {
                priceMin = BigDecimal.valueOf(Double.parseDouble(searchParameters.get("priceMin")));
            }
            catch (NumberFormatException e)
            {
                logger.info(MessageFormat.format("Incorrect  value in min search field: {0} ", searchParameters.get("priceMin"), e));
            }
            if (priceMin.compareTo(BigDecimal.valueOf(-1))>0) {
                criteria.add(Restrictions.ge("price", priceMin));
            }
        }
        if (!searchParameters.get("priceMax").equals("")){
            BigDecimal priceMax = BigDecimal.valueOf(-1);
            try {
                priceMax = BigDecimal.valueOf(Double.parseDouble(searchParameters.get("priceMax")));
            }
            catch (NumberFormatException e)
            {
                logger.info(MessageFormat.format("Incorrect  value in max search field: {0} ", searchParameters.get("priceMax"), e));
            }
            if (priceMax.compareTo(BigDecimal.valueOf(-1))>0) {
                criteria.add(Restrictions.le("price", priceMax));
            }
        }

        if (!searchParameters.get("company").equals("")){
            criteria.add(Restrictions.like("company", searchParameters.get("company"), MatchMode.ANYWHERE));
        }

        if (!searchParameters.get("parameters").equals("")){
            criteria.add(Restrictions.like("parameters", searchParameters.get("parameters"), MatchMode.ANYWHERE));
        }

        List list = criteria.list();
        return list;
    }
}
