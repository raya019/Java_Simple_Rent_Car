package service.returnmobil;

import entity.ReturnMobil;

import java.util.List;

public interface ReturnMobilService {

    List<ReturnMobil> getAllCarRent();

    ReturnMobil addReturnMobil(ReturnMobil returnMobil);
}
