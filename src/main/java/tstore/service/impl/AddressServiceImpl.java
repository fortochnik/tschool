package tstore.service.impl;

import tstore.dao.AddressDao;
import tstore.dao.impl.AddressDaoImpl;
import tstore.model.AddressEntity;
import tstore.service.AddressService;

/**
 * Service for management {@link AddressEntity}
 *
 * Created by mipan on 02.10.2016.
 */
public class AddressServiceImpl implements AddressService {

    protected AddressDao addressDao = new AddressDaoImpl();

    /**
     * create addressT
     * @param address Entity address for create
     * */
    public void addAddress(AddressEntity address) {
        addressDao.beginTransaction();
        addressDao.persist(address);
        addressDao.closeTransaction();
    }

    /**
     * update address
     * @param address Entity for update
     * */
    public void updateAddress(AddressEntity address) {
        addressDao.beginTransaction();
        addressDao.update(address);
        addressDao.closeTransaction();
    }
}
