package repository;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import org.junit.jupiter.api.*;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import util.DatabaseUtil;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MobilRepositoryImplTest {

    private HikariDataSource dataSource;

    private MobilRepository mobilRepository;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        mobilRepository = new MobilRepositoryImpl(dataSource);
    }

    @Test
    @DisplayName("Delete All")
    @Order(1)
    void deleteAll() {
        mobilRepository.deleteAll();
    }

    @Test
    @DisplayName("Add Mobil")
    @Order(2)
    void testAdd() {
        Mobil mobil = new Mobil();
        mobil.setMerk("daihatsu");
        mobil.setTipe("innova");
        mobil.setNoKendaraan("l2378ll");
        mobil.setHarga(450_000);
        mobil.setJumlah(2);

        Mobil add = mobilRepository.add(mobil);

        Assertions.assertNotNull(add);

        Assertions.assertNotNull(add.getMerk());
        Assertions.assertNotNull(add.getTipe());
        Assertions.assertNotNull(add.getNoKendaraan());
        Assertions.assertNotNull(add.getJumlah());
        Assertions.assertNotNull(add.getHarga());
        Assertions.assertNotNull(add.getId());
        Assertions.assertTrue(add.isAvaible());

        Assertions.assertEquals("daihatsu", add.getMerk());
        Assertions.assertEquals("innova", add.getTipe());
        Assertions.assertEquals("l2378ll", add.getNoKendaraan());
        Assertions.assertEquals(2, add.getJumlah());
        Assertions.assertEquals(450_000,add.getHarga());
        Assertions.assertTrue(add.isAvaible());
        Assertions.assertNotNull(add.getId());
    }

    @Test
    @DisplayName("FindbyId Success")
    @Order(4)
    void testfindByIdSuccess() {

        Mobil car = mobilRepository.findById(107).orElse(null);

        Assertions.assertNotNull(car);

        Assertions.assertNotNull(car.getMerk());
        Assertions.assertNotNull(car.getTipe());
        Assertions.assertNotNull(car.getNoKendaraan());
        Assertions.assertNotNull(car.getJumlah());
        Assertions.assertNotNull(car.getHarga());
        Assertions.assertNotNull(car.getId());
        Assertions.assertTrue(car.isAvaible());

        Assertions.assertEquals("honda", car.getMerk());
        Assertions.assertEquals("jazz", car.getTipe());
        Assertions.assertEquals("l3195lb", car.getNoKendaraan());
        Assertions.assertEquals(2, car.getJumlah());
        Assertions.assertEquals(350_000,car.getHarga());
        Assertions.assertNotNull(car.getId());
        Assertions.assertTrue(car.isAvaible());

    }

    @Test
    @Order(5)
    @DisplayName("FindByIdFailed")
    void testfindByIdFailed() {

        Mobil car = mobilRepository.findById(0).orElse(null);

        Assertions.assertNull(car);
    }

    @Test
    @DisplayName("Update")
    @Order(3)
    void testUpdate() {
        Mobil mobil = new Mobil();
        mobil.setId(107);
        mobil.setMerk("honda");
        mobil.setTipe("jazz");
        mobil.setNoKendaraan("l3195lb");
        mobil.setJumlah(2);
        mobil.setHarga(350_000);

        Mobil update = mobilRepository.update(mobil);

        Assertions.assertNotNull(update.getMerk());
        Assertions.assertNotNull(update.getTipe());
        Assertions.assertNotNull(update.getNoKendaraan());
        Assertions.assertNotNull(update.getJumlah());
        Assertions.assertNotNull(update.getHarga());
        Assertions.assertTrue(update.isAvaible());
        Assertions.assertNotNull(update.getId());

        Assertions.assertEquals("honda", update.getMerk());
        Assertions.assertEquals("jazz", update.getTipe());
        Assertions.assertEquals("l3195lb", update.getNoKendaraan());
        Assertions.assertEquals(2, update.getJumlah());
        Assertions.assertEquals(350_000,update.getHarga());
        Assertions.assertTrue(update.isAvaible());
        Assertions.assertEquals(107,update.getId());
    }

    @Test
    @DisplayName("GetMobil")
    @Order(6)
    void testGetAll() {
        List<Mobil> mobilRepositoryAll = mobilRepository.getAll();

        mobilRepositoryAll.forEach(System.out::println);
    }

    @Test
    @DisplayName("DeleteOne")
    @Order(7)
    void testDeleteOne() {
        mobilRepository.deleteOne(107);
    }

    @Test
    @DisplayName("Close Database")
    @Order(8)
    void testCloseDatabase() {
        dataSource.close();
    }
}
