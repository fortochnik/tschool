package tstore.dao.impl;

import org.springframework.stereotype.Repository;
import tstore.dao.AddressDao;
import tstore.model.AddressEntity;

import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
@Repository
public class AddressDaoImpl extends GenericDaoImpl<AddressEntity, Integer> implements AddressDao {

}
