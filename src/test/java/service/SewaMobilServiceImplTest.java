package service;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import entity.SewaMobil;
import org.junit.jupiter.api.*;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import repository.sewamobil.SewaMobilRepository;
import repository.sewamobil.SewaMobilRepositoryImpl;
import service.sewamobil.SewaMobilService;
import service.sewamobil.SewaMobilServiceImpl;
import util.DatabaseUtil;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SewaMobilServiceImplTest {

    private HikariDataSource dataSource;

    private SewaMobilRepository sewaMobilRepository;

    private SewaMobilService sewaMobilService;

    private MobilRepository mobilRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        sewaMobilRepository = new SewaMobilRepositoryImpl(dataSource);
        mobilRepository = new MobilRepositoryImpl(dataSource);
        sewaMobilService = new SewaMobilServiceImpl(sewaMobilRepository,mobilRepository);
    }

    @Test
    @DisplayName("Delete All")
    @Order(1)
    void testDeleteAll() {
        sewaMobilRepository.deleteAll();
        mobilRepository.deleteAll();
    }

    @Test
    @DisplayName("Show Sewa Mobil")
    @Order(4)
    void testShowSewaMobil() {
        sewaMobilService.getAllSewaMobil().forEach(System.out::println);
    }

    @Test
    @DisplayName("Add")
    @Order(2)
    void testAdd() {
        Mobil mobil = new Mobil();
        mobil.setMerk("toyota");
        mobil.setTipe("yaris");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350000);
        mobil.setJumlah(1);

        Mobil mobil1 = mobilRepository.add(mobil);

        Assertions.assertEquals("toyota", mobil1.getMerk());
        Assertions.assertEquals("yaris", mobil1.getTipe());
        Assertions.assertEquals("l3195lb", mobil1.getNoKendaraan());
        Assertions.assertEquals(350000, mobil1.getHarga());
        Assertions.assertTrue(mobil1.isAvaible());
        Assertions.assertEquals(1, mobil1.getJumlah());
        Assertions.assertNotNull(mobil1.getId());

        // add sewa mobil
        SewaMobil newSewaMobil = new SewaMobil();
        newSewaMobil.setNamaCustomer("joko");
        newSewaMobil.setNoKtp("3465689486857");

        // find Mobil
        Mobil findMobil = mobilRepository.findById(135).orElse(null);

        newSewaMobil.setMobil(findMobil);

        SewaMobil addedSewaMobil = sewaMobilService.addSewaMobil(newSewaMobil);

        Assertions.assertNotNull(addedSewaMobil);

        Assertions.assertEquals("joko",addedSewaMobil.getNamaCustomer());
        Assertions.assertEquals("3465689486857",addedSewaMobil.getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"),addedSewaMobil.getTanggal());
        Assertions.assertEquals(135,addedSewaMobil.getMobil().getId());
        Assertions.assertEquals("toyota",addedSewaMobil.getMobil().getMerk());
        Assertions.assertEquals("yaris",addedSewaMobil.getMobil().getTipe());
        Assertions.assertEquals("l3195lb",addedSewaMobil.getMobil().getNoKendaraan());
        Assertions.assertEquals(350_000,addedSewaMobil.getMobil().getHarga());
        Assertions.assertEquals(0,addedSewaMobil.getMobil().getJumlah());
        Assertions.assertFalse(addedSewaMobil.getMobil().isAvaible());

        Assertions.assertNotNull(addedSewaMobil.getId());
        Assertions.assertNotNull(addedSewaMobil.getNamaCustomer());
        Assertions.assertNotNull(addedSewaMobil.getNoKtp());
        Assertions.assertNotNull(addedSewaMobil.getTanggal());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getId());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getMerk());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getTipe());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getNoKendaraan());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getHarga());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getJumlah());
        Assertions.assertFalse(addedSewaMobil.getMobil().isAvaible());
    }

    @Test
    @DisplayName("Add Failed")
    @Order(3)
    void testAddFailed() {

        SewaMobil newSewaMobil = new SewaMobil();
        newSewaMobil.setNamaCustomer("joko");
        newSewaMobil.setNoKtp("3465689486857");

        Mobil findMobil = mobilRepository.findById(135).orElse(null);

        newSewaMobil.setMobil(findMobil);

        SewaMobil addedSewaMobil = sewaMobilService.addSewaMobil(newSewaMobil);

        Assertions.assertNull(addedSewaMobil);
    }

    @Test
    @DisplayName("FindByNoKtp")
    @Order(5)
    void testFindByNoKtp() {
        SewaMobil carRentRepositoryByNoKtp = sewaMobilService.findByNoKtp("3465689486857");

        Assertions.assertEquals("joko",carRentRepositoryByNoKtp.getNamaCustomer());
        Assertions.assertEquals("3465689486857",carRentRepositoryByNoKtp.getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"),carRentRepositoryByNoKtp.getTanggal());
        Assertions.assertEquals(135,carRentRepositoryByNoKtp.getMobil().getId());
        Assertions.assertEquals("toyota", carRentRepositoryByNoKtp.getMobil().getMerk());
        Assertions.assertEquals("yaris", carRentRepositoryByNoKtp.getMobil().getTipe());
        Assertions.assertEquals("l3195lb", carRentRepositoryByNoKtp.getMobil().getNoKendaraan());
        Assertions.assertEquals(350000, carRentRepositoryByNoKtp.getMobil().getHarga());
        Assertions.assertEquals(0,carRentRepositoryByNoKtp.getMobil().getJumlah());
        Assertions.assertFalse(carRentRepositoryByNoKtp.getMobil().isAvaible());

        Assertions.assertNotNull(carRentRepositoryByNoKtp.getId());
        Assertions.assertNotNull(carRentRepositoryByNoKtp.getNamaCustomer());
        Assertions.assertNotNull(carRentRepositoryByNoKtp.getNoKtp());
        Assertions.assertNotNull(carRentRepositoryByNoKtp.getTanggal());
        Assertions.assertNotNull(carRentRepositoryByNoKtp.getMobil());

        System.out.println(carRentRepositoryByNoKtp.getId());
        System.out.println(carRentRepositoryByNoKtp.getNamaCustomer());
        System.out.println(carRentRepositoryByNoKtp.getNoKtp());
        System.out.println(carRentRepositoryByNoKtp.getTanggal());
        System.out.println(carRentRepositoryByNoKtp.getMobil());
    }

    @Test
    @DisplayName("Delete One")
    @Order(6)
    void testDeleteOne() {
        sewaMobilRepository.deleteOne(19);
        mobilRepository.deleteOne(135);
    }

    @Test
    @DisplayName("Close")
    @Order(7)
    void testClose() {
        dataSource.close();
    }

}
