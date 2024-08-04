package service;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import entity.ReturnMobil;
import entity.SewaMobil;
import org.junit.jupiter.api.*;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import repository.returnmobil.ReturnMobilRepository;
import repository.returnmobil.ReturnMobilRepositoryImpl;
import repository.sewamobil.SewaMobilRepository;
import repository.sewamobil.SewaMobilRepositoryImpl;
import service.returnmobil.ReturnMobilService;
import service.returnmobil.ReturnMobilServiceImpl;
import util.DatabaseUtil;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReturnMobilServiceImplTest {

    private HikariDataSource dataSource;

    private ReturnMobilRepository returnMobilRepository;

    private ReturnMobilService returnMobilService;

    private SewaMobilRepository sewaMobilRepository;

    private MobilRepository mobilRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        returnMobilRepository = new ReturnMobilRepositoryImpl(dataSource);
        sewaMobilRepository = new SewaMobilRepositoryImpl(dataSource);
        mobilRepository = new MobilRepositoryImpl(dataSource);
        returnMobilService = new ReturnMobilServiceImpl(returnMobilRepository,sewaMobilRepository,mobilRepository);
    }

    @Test
    @DisplayName("Delete All")
    @Order(1)
    void testDeleteAll() {
        returnMobilRepository.deleteAll();
        sewaMobilRepository.deleteAll();
        mobilRepository.deleteAll();
    }

    @Test
    @DisplayName("Get All")
    @Order(3)
    void testGetAll() {
        returnMobilRepository.getAllCarReturn().forEach(System.out::println);
    }

    @Test
    @DisplayName("Add")
    @Order(2)
    void testAdd() {

        Mobil mobil  = new Mobil();
        mobil.setMerk("honda");
        mobil.setTipe("jazz");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350_000);
        mobil.setJumlah(1);

        Mobil add = mobilRepository.add(mobil);

        Assertions.assertNotNull(add);
        Assertions.assertEquals("honda", add.getMerk());
        Assertions.assertEquals("jazz", add.getTipe());
        Assertions.assertEquals("l3195lb", add.getNoKendaraan());
        Assertions.assertEquals(350000, add.getHarga());
        Assertions.assertTrue(add.isAvaible());
        Assertions.assertEquals(1, add.getJumlah());
        Assertions.assertNotNull(add.getId());

        SewaMobil sewaMobil = new SewaMobil();
        sewaMobil.setNamaCustomer("joko");
        sewaMobil.setNoKtp("3984538495348");

        Mobil findMobil = mobilRepository.findById(1).orElse(null);

        System.out.println(findMobil);

        sewaMobil.setMobil(findMobil);

        SewaMobil addedSewaMobil = sewaMobilRepository.addSewaMobil(sewaMobil);

        addedSewaMobil.getMobil().setJumlah(addedSewaMobil.getMobil().getJumlah() - 1);
        addedSewaMobil.getMobil().setAvaible(addedSewaMobil.getMobil().getJumlah() != 0);

        Assertions.assertNotNull(addedSewaMobil);

        Assertions.assertEquals("joko",addedSewaMobil.getNamaCustomer());
        Assertions.assertEquals("3984538495348",addedSewaMobil.getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"),addedSewaMobil.getTanggal());
        Assertions.assertEquals(1,addedSewaMobil.getMobil().getId());
        Assertions.assertEquals("honda",addedSewaMobil.getMobil().getMerk());
        Assertions.assertEquals("jazz",addedSewaMobil.getMobil().getTipe());
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


        ReturnMobil returnMobil = new ReturnMobil();
        returnMobil.setJumlahPembayaran(sewaMobil.getMobil().getHarga());
        returnMobil.setJangkaSewa(5);
        returnMobil.setTanggalKembali(sewaMobil.getTanggal());
        returnMobil.setSewaMobil(addedSewaMobil);

        ReturnMobil returnMobil1 = returnMobilService.addReturnMobil(returnMobil);

        Assertions.assertEquals(1_750_000,returnMobil1.getJumlahPembayaran());
        Assertions.assertEquals(5,returnMobil1.getJangkaSewa());
        Assertions.assertEquals(LocalDate.parse("2024-06-22"), returnMobil1.getTanggalKembali());
        Assertions.assertEquals(1,returnMobil1.getSewaMobil().getId());
        Assertions.assertEquals("joko",returnMobil1.getSewaMobil().getNamaCustomer());
        Assertions.assertEquals("3984538495348",returnMobil1.getSewaMobil().getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"),returnMobil1.getSewaMobil().getTanggal());
        Assertions.assertEquals(1,returnMobil1.getSewaMobil().getMobil().getId());
        Assertions.assertEquals("honda",returnMobil1.getSewaMobil().getMobil().getMerk());
        Assertions.assertEquals("jazz",returnMobil1.getSewaMobil().getMobil().getTipe());
        Assertions.assertEquals("l3195lb",returnMobil1.getSewaMobil().getMobil().getNoKendaraan());
        Assertions.assertEquals(350_000,returnMobil1.getSewaMobil().getMobil().getHarga());
        Assertions.assertEquals(1,returnMobil1.getSewaMobil().getMobil().getJumlah());
        Assertions.assertTrue(returnMobil1.getSewaMobil().getMobil().isAvaible());

        Assertions.assertNotNull(returnMobil1.getId());
        Assertions.assertNotNull(returnMobil1.getJumlahPembayaran());
        Assertions.assertNotNull(returnMobil1.getJangkaSewa());
        Assertions.assertNotNull(returnMobil1.getTanggalKembali());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getId());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getNamaCustomer());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getNoKtp());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getTanggal());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getId());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getMerk());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getTipe());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getNoKendaraan());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getHarga());
        Assertions.assertNotNull(returnMobil1.getSewaMobil().getMobil().getJumlah());
    }

    @Test
    @Order(4)
    void testDeletOne() {
        returnMobilRepository.deleteOne(1);
        sewaMobilRepository.deleteOne(1);
        mobilRepository.deleteOne(1);
    }

    @Test
    @DisplayName("Close")
    @Order(5)
    void testClose() {
        dataSource.close();
    }
}
