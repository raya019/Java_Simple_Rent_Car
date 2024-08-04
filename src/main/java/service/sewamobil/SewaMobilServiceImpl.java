package service.sewamobil;

import entity.Mobil;
import entity.SewaMobil;
import repository.mobil.MobilRepository;
import repository.sewamobil.SewaMobilRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class SewaMobilServiceImpl implements SewaMobilService {


    private SewaMobilRepository sewaMobilRepository;

    private MobilRepository mobilRepository;

    public SewaMobilServiceImpl(SewaMobilRepository sewaMobilRepository, MobilRepository mobilRepository) {
        this.sewaMobilRepository = sewaMobilRepository;
        this.mobilRepository = mobilRepository;
    }

    public SewaMobilServiceImpl(SewaMobilRepository sewaMobilRepository) {
        this.sewaMobilRepository = sewaMobilRepository;
    }

    @Override
    public List<SewaMobil> getAllSewaMobil() {
        return sewaMobilRepository.getAllSewaMobil();
    }

    @Override
    public SewaMobil addSewaMobil(SewaMobil sewaMobil) {
        //memeriksa apakah mobil tersedia dan no ktp tidak tersedia
        boolean present = mobilRepository.findById(sewaMobil.getMobil().getId())
                .filter(mobil -> mobil.getJumlah() > 0 && mobil.isAvaible())
                .isPresent();

        boolean empty = sewaMobilRepository.findByNoKtp(sewaMobil.getNoKtp()).isEmpty();

        if (present && empty){
            //update jumlah dan ketersediaan mobil
            sewaMobil.getMobil().setJumlah(sewaMobil.getMobil().getJumlah() - 1);
            sewaMobil.getMobil().setAvaible(sewaMobil.getMobil().getJumlah() != 0);

            //menambahkan sewa mobil
            SewaMobil addedSewaMobil = sewaMobilRepository.addSewaMobil(sewaMobil);
            mobilRepository.update(sewaMobil.getMobil());

            System.out.println("Data Berhasil Di Tambahkan \n");

            return addedSewaMobil;
        } else {
            System.out.println("Mobil Kosong atau no Ktp sudah digunakan");
            return null;
        }

//        if (mobilRepository.isExistByid(sewaMobil.getMobil().getId())) {
//            SewaMobil addedSewaMobil = sewaMobilRepository.addSewaMobil(sewaMobil);
//            addedSewaMobil.getMobil().setJumlah(sewaMobil.getMobil().getJumlah() - 1);
//            addedSewaMobil.getMobil().setAvaible(addedSewaMobil.getMobil().getJumlah() != 0);
//            mobilRepository.update(sewaMobil.getMobil());
//            return addedSewaMobil;
//        } else {
//            System.out.println("Mobil Kosong");
//            return null;
//        }


    }

    @Override
    public SewaMobil findByNoKtp(String noKtp) {
        return sewaMobilRepository.findByNoKtp(noKtp)
                .orElseThrow(() -> new RuntimeException("Nomor KTP tidak di Temukan"));
    }

}
