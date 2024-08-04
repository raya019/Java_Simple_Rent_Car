package repository.returnmobil;

import entity.ReturnMobil;

import java.sql.SQLException;
import java.util.List;

public interface ReturnMobilRepository {

    List<ReturnMobil> getAllCarReturn();

    ReturnMobil addReturnMobil(ReturnMobil returnMobil);

    void deleteAll();

    void deleteOne(Integer id);
}
