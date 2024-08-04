package service.mobil;

import entity.Mobil;
import repository.mobil.MobilRepository;
import java.util.List;

public class MobilServiceImpl implements MobilService {
    private final MobilRepository mobilRepository;

    public MobilServiceImpl(MobilRepository mobilRepository) {
        this.mobilRepository = mobilRepository;
    }

    @Override
    public List<Mobil> showAllMobil() {
        return mobilRepository.getAll();
    }

    @Override
    public Mobil save(Mobil mobil) {
      if (mobil.getId() != null){
          //check id
          return mobilRepository.findById(mobil.getId())
                  // transform menjadi mobil pada parameter dan mengupdate mobil
                  .map(exist ->{
                      if (mobil.getJumlah() >= 0) {
                          mobil.setJumlah(mobil.getJumlah());
                          mobil.setAvaible(mobil.getJumlah() != 0);

                          Mobil update = mobilRepository.update(mobil);
                          System.out.println("Data Berhasil di Update");
                          return update;
                      } else {
                          // jika jumlah < 0 return null
                          System.out.println("Jumlah Tidak Boleh minus");
                          return null;
                      }
                  })
                  //jika id tidak ditemukan
                  .orElseThrow(() -> new RuntimeException("Mobil Tidak Ditemukan"));
      } else {
          //check Nokendaraan
          boolean isExist = mobilRepository.isExistByNoKendaraan(mobil.getNoKendaraan());

          if (mobil.getJumlah() > 0  && !isExist){
              Mobil add = mobilRepository.add(mobil);
              System.out.println("Data Berhasil Di Tambahkan \n");
            return add;
          } else {
              System.out.println(isExist ? "Nomor Kendaraan Sudah Terdaftar"
                      : "Jumlah Tidak Boleh nol atau minus");
              return null;
          }
      }
    }

    @Override
    public void delete(Integer id) {
        //check id ada atau tidak. jika ada delete
        mobilRepository.findById(id)
                .ifPresentOrElse(value -> {
                            mobilRepository.deleteOne(value.getId());
                            System.out.println("Data Berhasil di Hapus");
                            },
                        () -> System.out.println("Mobil Tidak Ditemukan")
                        );

    }

    @Override
    public Mobil findById(Integer id) {
        List<Mobil> mobilRepositoryAll = mobilRepository.getAll();
        int i = id - 1;
        Mobil mobil = mobilRepositoryAll.get(i);
        return mobilRepository.findById(mobil.getId())
                .orElseThrow(() -> new RuntimeException("Data Tidak Ditemukan"));
    }
}
