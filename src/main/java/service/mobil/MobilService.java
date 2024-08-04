package service.mobil;

import entity.Mobil;

import java.util.List;

public interface MobilService {

    List<Mobil> showAllMobil();

    Mobil save(Mobil mobil);

    void delete(Integer id);

    Mobil findById(Integer id);

}
