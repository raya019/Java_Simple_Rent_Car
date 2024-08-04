package apps;

import com.zaxxer.hikari.HikariDataSource;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import repository.returnmobil.ReturnMobilRepository;
import repository.returnmobil.ReturnMobilRepositoryImpl;
import repository.sewamobil.SewaMobilRepository;
import repository.sewamobil.SewaMobilRepositoryImpl;
import service.mobil.MobilService;
import service.mobil.MobilServiceImpl;
import service.returnmobil.ReturnMobilService;
import service.returnmobil.ReturnMobilServiceImpl;
import service.sewamobil.SewaMobilService;
import service.sewamobil.SewaMobilServiceImpl;
import util.DatabaseUtil;
import view.CarRentalView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        HikariDataSource dataSource = DatabaseUtil.getDataSource();
        MobilRepository mobilRepository = new MobilRepositoryImpl(dataSource);
        MobilService mobilService = new MobilServiceImpl(mobilRepository);
        SewaMobilRepository sewaMobilRepository = new SewaMobilRepositoryImpl(dataSource);
        SewaMobilService sewaMobilService = new SewaMobilServiceImpl(sewaMobilRepository);
        ReturnMobilRepository returnMobilRepository = new ReturnMobilRepositoryImpl(dataSource);
        ReturnMobilService returnMobilService = new ReturnMobilServiceImpl(returnMobilRepository,sewaMobilRepository,mobilRepository);
        CarRentalView carRentalView = new CarRentalView(mobilService,sewaMobilService,returnMobilService);

        carRentalView.homeView();
    }
}
