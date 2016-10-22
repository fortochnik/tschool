package tstore.dao.transaction;

/**
 * Created by mipan on 02.10.2016.
 */
public interface Transaction {

    void beginTransaction();

    void closeTransaction();

    void rollbackTransaction();
}
