package repository.mobil;

import entity.Mobil;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MobilRepository {

    List<Mobil> getAll();

    Mobil add(Mobil car);

    Mobil update(Mobil car);

    void deleteOne(Integer id);

    void deleteAll();

    Optional <Mobil> findById(Integer id);

//    boolean isExistByid(Integer id);

    boolean isExistByNoKendaraan(String noKendaraan);

}
