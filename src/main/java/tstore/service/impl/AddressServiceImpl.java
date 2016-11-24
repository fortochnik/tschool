package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.AddressDao;
import tstore.dao.impl.AddressDaoImpl;
import tstore.model.AddressEntity;
import tstore.service.AddressService;

/**
 * Service for management {@link AddressEntity}
 *
 * Created by mipan on 02.10.2016.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    protected AddressDao addressDao;

    /**
     * create addressT
     * @param address Entity address for create
     * */
    public void addAddress(AddressEntity address) {
        addressDao.persist(address);
    }

    /**
     * update address
     * @param address Entity for update
     * */
    public void updateAddress(AddressEntity address) {
        addressDao.update(address);
    }
}
