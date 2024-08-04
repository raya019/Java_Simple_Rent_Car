package service.returnmobil;

import entity.Mobil;
import entity.ReturnMobil;
import entity.SewaMobil;
import repository.mobil.MobilRepository;
import repository.returnmobil.ReturnMobilRepository;
import repository.sewamobil.SewaMobilRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReturnMobilServiceImpl implements ReturnMobilService {

    private ReturnMobilRepository returnMobilRepository;

    private SewaMobilRepository sewaMobilRepository;

    private MobilRepository mobilRepository;

    public ReturnMobilServiceImpl(ReturnMobilRepository returnMobilRepository, SewaMobilRepository sewaMobilRepository, MobilRepository mobilRepository) {
        this.returnMobilRepository = returnMobilRepository;
        this.sewaMobilRepository = sewaMobilRepository;
        this.mobilRepository = mobilRepository;
    }

    @Override
    public List<ReturnMobil> getAllCarRent() {
        return returnMobilRepository.getAllCarReturn();
    }

    @Override
    public ReturnMobil addReturnMobil(ReturnMobil returnMobil) {

        boolean present = sewaMobilRepository.findByNoKtp(returnMobil.getSewaMobil().getNoKtp())
                .filter(
                        sewaMobil -> Objects.equals(returnMobil.getSewaMobil().getNoKtp(), sewaMobil.getNoKtp())
                                && Objects.equals(returnMobil.getSewaMobil().getMobil().getId(), sewaMobil.getMobil().getId())
                )
                .isPresent();

        if (present) {
           return Optional.of(returnMobil)
                    .map(value -> {
                        // mengupdate jumlah dan avaible mobil
                        value.getSewaMobil().getMobil().setJumlah(returnMobil.getSewaMobil().getMobil().getJumlah() + 1);
                        value.getSewaMobil().getMobil().setAvaible(value.getSewaMobil().getMobil().getJumlah() != 0);
                        mobilRepository.update(value.getSewaMobil().getMobil());

                        //menghitung jumlah pembayaran
                        value.setJumlahPembayaran(returnMobil.getSewaMobil().getMobil().getHarga()
                                * returnMobil.getJangkaSewa());
                        value.setJangkaSewa(returnMobil.getJangkaSewa());
                        value.setTanggalKembali(returnMobil.getSewaMobil().getTanggal()
                                .plusDays(returnMobil.getJangkaSewa()));

                        //menambahkan data pengembalian mobil
                        return returnMobilRepository.addReturnMobil(value);
                    }).orElseThrow(() -> new RuntimeException("Kosong"));
        } else {
            System.out.println("Data sewa mobil tidak ditemukan atau tidak sesuai.");
            return null;
        }
    }


}
