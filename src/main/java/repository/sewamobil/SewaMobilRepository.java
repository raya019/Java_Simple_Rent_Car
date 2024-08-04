package repository.sewamobil;

import entity.SewaMobil;
import java.util.List;
import java.util.Optional;

public interface SewaMobilRepository {

    List<SewaMobil> getAllSewaMobil();

    SewaMobil addSewaMobil(SewaMobil sewaMobil);

    void deleteOne(Integer id);

    void deleteAll();

    Optional <SewaMobil> findByNoKtp (String noKtp);
}
