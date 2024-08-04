package repository;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import entity.SewaMobil;
import org.junit.jupiter.api.*;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import repository.sewamobil.SewaMobilRepository;
import repository.sewamobil.SewaMobilRepositoryImpl;
import util.DatabaseUtil;

import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SewaMobilRepositoryTest {

    private HikariDataSource dataSource;

    private SewaMobilRepository sewaMobilRepository;

    private MobilRepository mobilRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        sewaMobilRepository = new SewaMobilRepositoryImpl(dataSource);
        mobilRepository = new MobilRepositoryImpl(dataSource);
    }

    @DisplayName("Delet All")
    @Order(1)
    @Test
    void testDeleteAll() {
        sewaMobilRepository.deleteAll();
        mobilRepository.deleteAll();
    }


    @DisplayName("Add")
    @Order(2)
    @Test
    void testAddSewaMobil() {
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


        SewaMobil newSewaMobil = new SewaMobil();
        newSewaMobil.setNamaCustomer("joko");
        newSewaMobil.setNoKtp("3465689486857");

        Mobil findMobil = mobilRepository.findById(120).orElse(null);

        newSewaMobil.setMobil(findMobil);

        SewaMobil addedSewaMobil = sewaMobilRepository.addSewaMobil(newSewaMobil);

        Assertions.assertNotNull(addedSewaMobil);

        Assertions.assertEquals("joko",addedSewaMobil.getNamaCustomer());
        Assertions.assertEquals("3465689486857",addedSewaMobil.getNoKtp());
        Assertions.assertEquals(LocalDate.now(),addedSewaMobil.getTanggal());
        Assertions.assertEquals("toyota",addedSewaMobil.getMobil().getMerk());
        Assertions.assertEquals("yaris",addedSewaMobil.getMobil().getTipe());
        Assertions.assertEquals("l3195lb",addedSewaMobil.getMobil().getNoKendaraan());
        Assertions.assertEquals(350_000,addedSewaMobil.getMobil().getHarga());
        Assertions.assertEquals(1,addedSewaMobil.getMobil().getJumlah());
        Assertions.assertTrue(addedSewaMobil.getMobil().isAvaible());

        Assertions.assertNotNull(addedSewaMobil.getId());
        Assertions.assertNotNull(addedSewaMobil.getNamaCustomer());
        Assertions.assertNotNull(addedSewaMobil.getNoKtp());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getId());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getMerk());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getTipe());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getNoKendaraan());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getHarga());
        Assertions.assertNotNull(addedSewaMobil.getMobil().getJumlah());
    }

    @DisplayName("Get All")
    @Order(3)
    @Test
    void testGetAllSewaMobil() {
        List<SewaMobil> allSewaMobil = sewaMobilRepository.getAllSewaMobil();

        allSewaMobil.forEach(System.out::println);

        Assertions.assertNotNull(allSewaMobil);

    }

    @DisplayName("Delete One")
    @Order(5)
    @Test
    void testDeleteOne() {
        sewaMobilRepository.deleteOne(9);
        mobilRepository.deleteOne(120);
    }

    @Test
    @Order(4)
    void findByNoKtp() {
        SewaMobil sewaMobil = sewaMobilRepository.findByNoKtp("3465689486857").get();

        Assertions.assertEquals("joko",sewaMobil.getNamaCustomer());
        Assertions.assertEquals("3465689486857",sewaMobil.getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"), sewaMobil.getTanggal());
        Assertions.assertEquals("toyota",sewaMobil.getMobil().getMerk());
        Assertions.assertEquals("yaris",sewaMobil.getMobil().getTipe());
        Assertions.assertEquals("l3195lb",sewaMobil.getMobil().getNoKendaraan());
        Assertions.assertEquals(350_000,sewaMobil.getMobil().getHarga());
        Assertions.assertEquals(1,sewaMobil.getMobil().getJumlah());
        Assertions.assertTrue(sewaMobil.getMobil().isAvaible());

        Assertions.assertNotNull(sewaMobil.getId());
        Assertions.assertNotNull(sewaMobil.getMobil().getId());
        Assertions.assertNotNull(sewaMobil.getNamaCustomer());
        Assertions.assertNotNull(sewaMobil.getNoKtp());
        Assertions.assertNotNull(sewaMobil.getTanggal());
        Assertions.assertNotNull(sewaMobil.getMobil());
    }


    @Test
    @DisplayName("Close")
    @Order(6)
    void testClose() {
        dataSource.close();
    }
}
