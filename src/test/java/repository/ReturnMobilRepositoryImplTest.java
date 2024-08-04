package repository;

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
import util.DatabaseUtil;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReturnMobilRepositoryImplTest {
    private HikariDataSource dataSource;

    private ReturnMobilRepository returnMobilRepository;

    private MobilRepository mobilRepository;

    private SewaMobilRepository sewaMobilRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        returnMobilRepository = new ReturnMobilRepositoryImpl(dataSource);
        sewaMobilRepository = new SewaMobilRepositoryImpl(dataSource);
        mobilRepository = new MobilRepositoryImpl(dataSource);
    }

    @Test
    @Order(1)
    void testDeleteAll() {
        returnMobilRepository.deleteAll();
        sewaMobilRepository.deleteAll();
        mobilRepository.deleteAll();
    }

    @Test
    @Order(3)
    void testGetAll() {
        returnMobilRepository.getAllCarReturn().forEach(System.out::println);
    }

    @Test
    @Order(2)
    void testAdd() {
        Mobil mobil  = new Mobil();
        mobil.setId(1);
        mobil.setMerk("honda");
        mobil.setTipe("jazz");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350_000);
        mobil.setJumlah(1);

        Mobil add = mobilRepository.add(mobil);

        SewaMobil sewaMobil = new SewaMobil();
        sewaMobil.setId(1);
        sewaMobil.setNamaCustomer("john");
        sewaMobil.setNoKtp("9845645222");
        sewaMobil.setMobil(add);

        SewaMobil addedSewaMobil = sewaMobilRepository.addSewaMobil(sewaMobil);


        ReturnMobil returnMobil = new ReturnMobil();
        returnMobil.setJumlahPembayaran(sewaMobil.getMobil().getHarga() * 5);
        returnMobil.setJangkaSewa(5);
        returnMobil.setTanggalKembali(sewaMobil.getTanggal().plusDays(5));
        returnMobil.setSewaMobil(addedSewaMobil);


        ReturnMobil addReturnMobil = returnMobilRepository.addReturnMobil(returnMobil);

        Assertions.assertNotNull(addReturnMobil);


        Assertions.assertEquals(1_750_000,addReturnMobil.getJumlahPembayaran());
        Assertions.assertEquals(5,addReturnMobil.getJangkaSewa());
        Assertions.assertEquals(LocalDate.now().plusDays(5),addReturnMobil.getTanggalKembali());
        Assertions.assertEquals("john",addReturnMobil.getSewaMobil().getNamaCustomer());
        Assertions.assertEquals("9845645222",addReturnMobil.getSewaMobil().getNoKtp());
        Assertions.assertEquals(LocalDate.parse("2024-06-17"),addReturnMobil.getSewaMobil().getTanggal());
        Assertions.assertEquals("honda",addReturnMobil.getSewaMobil().getMobil().getMerk());
        Assertions.assertEquals("jazz",addReturnMobil.getSewaMobil().getMobil().getTipe());
        Assertions.assertEquals("l3195lb",addReturnMobil.getSewaMobil().getMobil().getNoKendaraan());
        Assertions.assertEquals(350_000,addReturnMobil.getSewaMobil().getMobil().getHarga());
        Assertions.assertEquals(1,addReturnMobil.getSewaMobil().getMobil().getJumlah());
        Assertions.assertTrue(addReturnMobil.getSewaMobil().getMobil().isAvaible());

        Assertions.assertNotNull(addReturnMobil.getId());
        Assertions.assertNotNull(addReturnMobil.getJumlahPembayaran());
        Assertions.assertNotNull(addReturnMobil.getJangkaSewa());
        Assertions.assertNotNull(addReturnMobil.getTanggalKembali());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getId());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getNamaCustomer());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getNoKtp());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getTanggal());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getId());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getMerk());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getTipe());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getNoKendaraan());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getHarga());
        Assertions.assertNotNull(addReturnMobil.getSewaMobil().getMobil().getJumlah());
    }

    @Test
    @Order(4)
    void testDeleteOne() {
        returnMobilRepository.deleteOne(5);
        sewaMobilRepository.deleteOne(13);
        mobilRepository.deleteOne(125);
    }

    @Test
    @Order(5)
    void testClose() {
        dataSource.close();
    }
}
