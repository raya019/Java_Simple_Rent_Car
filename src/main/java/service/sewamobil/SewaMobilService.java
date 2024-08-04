package service.sewamobil;


import entity.SewaMobil;

import java.util.List;

public interface SewaMobilService {

    List<SewaMobil> getAllSewaMobil();

    SewaMobil addSewaMobil(SewaMobil sewaMobil);

    SewaMobil findByNoKtp (String Noktp);

}
