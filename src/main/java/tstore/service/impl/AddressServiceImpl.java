package tstore.service.impl;

import tstore.dao.AddressDao;
import tstore.dao.impl.AddressDaoImpl;
import tstore.model.AddressEntity;
import tstore.service.AddressService;

/**
 * Created by mipan on 02.10.2016.
 */
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao = new AddressDaoImpl();

    public void addAddress(AddressEntity address) {
        addressDao.beginTransaction();
        addressDao.persist(address);
        addressDao.closeTransaction();
    }

    public void updateAddress(AddressEntity address) {
        addressDao.beginTransaction();
        addressDao.update(address);
        addressDao.closeTransaction();
    }
}
