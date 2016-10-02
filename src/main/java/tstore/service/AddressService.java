package tstore.service;

import tstore.model.AddressEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public interface AddressService {

    void addAddress(AddressEntity address);

    void updateAddress(AddressEntity address);
}
