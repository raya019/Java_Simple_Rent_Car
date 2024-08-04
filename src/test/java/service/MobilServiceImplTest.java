package service;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.mobil.MobilRepository;
import repository.mobil.MobilRepositoryImpl;
import service.mobil.MobilServiceImpl;
import util.DatabaseUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MobilServiceImplTest {
    private HikariDataSource dataSource;

    private MobilRepository mobilRepository;

    private MobilServiceImpl mobilService;

    @BeforeEach
    void setUp() {
        dataSource = DatabaseUtil.getDataSource();
        mobilRepository = new MobilRepositoryImpl(dataSource);
        mobilService = new MobilServiceImpl(mobilRepository);
    }

    @Order(1)
    @DisplayName("Delete All")
    @Test
    void testDeleteAll() {
        mobilRepository.deleteAll();
    }

    @Order(2)
    @DisplayName("Add New")
    @Test
    void testSaveAdd() {
        Mobil mobil = new Mobil();
        mobil.setMerk("honda");
        mobil.setTipe("jazz");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(150000);
        mobil.setJumlah(1);

        Mobil save = mobilService.save(mobil);

        Assertions.assertNull(save);

        Assertions.assertEquals("honda", save.getMerk());
        Assertions.assertEquals("jazz", save.getTipe());
        Assertions.assertEquals("l3195lb", save.getNoKendaraan());
        Assertions.assertEquals(150000, save.getHarga());
        Assertions.assertTrue(save.isAvaible());
        Assertions.assertEquals(1, save.getJumlah());
        Assertions.assertNotNull(save.getId());
    }

    @Order(3)
    @DisplayName("Failed Add With 'Jumlah = 0'")
    @Test
    void testSaveAddFailed() {

        Mobil mobil = new Mobil();
        mobil.setMerk("honda");
        mobil.setTipe("jazz");
        mobil.setNoKendaraan("l3195la");
        mobil.setHarga(350000);
        mobil.setJumlah(0);

        Assertions.assertNull(mobilService.save(mobil));


    }

    @Order(5)
    @DisplayName("Update Jumlah and Avaible must be 'True'")
    @Test
    void testSaveUpdate() {

        Mobil mobil = new Mobil();
        mobil.setId(129);
        mobil.setMerk("toyota");
        mobil.setTipe("yaris");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350000);
        mobil.setJumlah(1);

        Mobil mobil1 = mobilService.save(mobil);

        Assertions.assertEquals("toyota", mobil1.getMerk());
        Assertions.assertEquals("yaris", mobil1.getTipe());
        Assertions.assertEquals("l3195lb", mobil1.getNoKendaraan());
        Assertions.assertEquals(350000, mobil1.getHarga());
        Assertions.assertTrue(mobil1.isAvaible());
        Assertions.assertEquals(1, mobil1.getJumlah());
        Assertions.assertEquals(129,mobil1.getId());
    }

    @Order(4)
    @DisplayName("Update Jumlah and Avaible Must be 'False'")
    @Test
    void testSaveUpdateWith() {

        Mobil mobil = new Mobil();
        mobil.setId(129);
        mobil.setMerk("toyota");
        mobil.setTipe("yaris");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350000);
        mobil.setJumlah(0);

        Mobil update = mobilService.save(mobil);

        Assertions.assertNotNull(update);

        Assertions.assertEquals("toyota", update.getMerk());
        Assertions.assertEquals("yaris", update.getTipe());
        Assertions.assertEquals("l3195lb", update.getNoKendaraan());
        Assertions.assertEquals(350000, update.getHarga());
        Assertions.assertFalse(update.isAvaible());
        Assertions.assertEquals(0, update.getJumlah());
        Assertions.assertEquals(129,update.getId());

    }

    @Order(6)
    @DisplayName("Update Failed With 'id' Not Found")
    @Test
    void testSaveUpdateFailed() {

        Mobil mobil = new Mobil();
        mobil.setId(49);
        mobil.setMerk("toyota");
        mobil.setTipe("yaris");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350000);
        mobil.setJumlah(1);

        Assertions.assertThrows(RuntimeException.class, () -> mobilService.save(mobil));
    }

    @Order(7)
    @DisplayName("Update Failed when 'Jumlah >= 0'")
    @Test
    void testSaveUpdateFailedWith() {

        Mobil mobil = new Mobil();
        mobil.setId(129);
        mobil.setMerk("toyota");
        mobil.setTipe("yaris");
        mobil.setNoKendaraan("l3195lb");
        mobil.setHarga(350000);
        mobil.setJumlah(-1);

        Assertions.assertThrows(RuntimeException.class, () -> mobilService.save(mobil));
    }

    @Order(9)
    @DisplayName("Delete Success")
    @Test
    void testDeleteSuccess() {
        mobilService.delete(129);
    }

    @Order(8)
    @DisplayName("FindById")
    @Test
    void testFindByid() {
        Mobil mobil = mobilService.findById(1);

        Assertions.assertNotNull(mobil);

        Assertions.assertEquals("toyota", mobil.getMerk());
        Assertions.assertEquals("yaris", mobil.getTipe());
        Assertions.assertEquals("l3195lb", mobil.getNoKendaraan());
        Assertions.assertEquals(350000, mobil.getHarga());
        Assertions.assertTrue(mobil.isAvaible());
        Assertions.assertEquals(1, mobil.getJumlah());
        Assertions.assertEquals(129,mobil.getId());
    }

    @Test
    @Order(10)
    void testCloseDatabase() {
        dataSource.close();
    }
}
