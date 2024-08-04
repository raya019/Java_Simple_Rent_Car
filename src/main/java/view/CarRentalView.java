package view;

import entity.Mobil;
import entity.ReturnMobil;
import entity.SewaMobil;
import service.mobil.MobilService;
import service.returnmobil.ReturnMobilService;
import service.sewamobil.SewaMobilService;
import util.ScannerUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CarRentalView {

    private final String usernameAdmin = "admin";

    private final String passwordAdmin = "admin";

    private final String usernameUser = "user";

    private final String passwordUser = "user";

    private MobilService mobilService;

    private SewaMobilService sewaMobilService;

    private ReturnMobilService returnMobilService;

    public CarRentalView(MobilService mobilService, SewaMobilService sewaMobilService, ReturnMobilService returnMobilService) {
        this.mobilService = mobilService;
        this.sewaMobilService = sewaMobilService;
        this.returnMobilService = returnMobilService;
    }

//    public void loginView () {
//            System.out.println("Selamat Datang \n");
//
//            System.out.println("Masukkan Username dan Password");
//
//            String username = ScannerUtil.input("Masukkan Username");
//            String password = ScannerUtil.input("Masukkan Password");
//
//            if (Objects.equals(username, usernameAdmin) && Objects.equals(password, passwordAdmin)){
//                homeViewAdmin();
//            } else if (Objects.equals(username, usernameUser) && Objects.equals(password, passwordUser)) {
//
//            } else {
//                System.out.println("username dan password salah");
//            }
//    }

    public void homeView () {
        while(true){

            System.out.println("1. Tambah Data Mobil");
            System.out.println("2. Update Data Mobil");
            System.out.println("3. Hapus Data Mobil");
            System.out.println("4. Semua Data Mobil");
            System.out.println("5. Sewa Mobil");
            System.out.println("6. Semua Data Sewa Mobil");
            System.out.println("7. Return Mobil");
            System.out.println("8. Semua Data return Mobil");

            String input = ScannerUtil.input("Silahkan Pilih ");

            if (input.equals("1")){
                addMobilView();
            } else if (input.equals("2")) {
                updateMobilView();
            } else if (input.equals("3")) {
                deleteMobil();
            } else if (input.equals("4")) {
                allCar();
            } else if (input.equals("5")) {
                addSewaMobil();
            } else if (input.equals("6")) {
                allSewaMobil();
            } else if (input.equals("7")) {
                addReturnMobil();
            } else if (input.equals("8")) {
                allReturnCar();
            } else if (input.equals("x")) {
                System.out.println("Berhasil Log-out!");
                break;
            } else {
                System.out.println("Pilihan Tidak Dimengerti");
            }
        }
    }

    public void addMobilView () {
        var messageBlank = "Input tidak boleh Kosong";
        var message = "Gagal Menambahkan Data";

        var inputMerk = ScannerUtil.input("Masukkan Merk");
        if (inputMerk.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        var inputTipe = ScannerUtil.input("Masukkan Tipe");
        if (inputTipe.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        var inputNoKendaraan = ScannerUtil.input("Masukkan NoKendaraan");
        if (inputNoKendaraan.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        var inputHarga = ScannerUtil.inputInteger("Masukkan Harga");

        if (inputHarga == 0){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        var inputJumlah = ScannerUtil.inputInteger("Masukkan jumlah");
        if (inputJumlah == 0){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        Mobil mobil = new Mobil();
        mobil.setMerk(inputMerk);
        mobil.setTipe(inputTipe);
        mobil.setNoKendaraan(inputNoKendaraan);
        mobil.setHarga(inputHarga);
        mobil.setJumlah(inputJumlah);

        mobilService.save(mobil);
    }

    public void updateMobilView () {
        allCar();
        Supplier<Integer> inputInteger= () -> ScannerUtil.inputInteger("Pilih nomor untuk data yang ingin di ubah");

        Supplier<Mobil> mobilSupplier =  () -> mobilService.findById(inputInteger.get());
        Mobil updateMobil = mobilSupplier.get();

        while (true){

            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tDaftar Mobil");
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.print("| \tNo\t");
            System.out.print("|\tMerk\t\t\t");
            System.out.print("|\tTipe\t\t");
            System.out.print("|\tNo.Kendaraan\t");
            System.out.print("|\tHarga Sewa\t");
            System.out.println("|\tJumlah\t |");
            System.out.println("------------------------------------------------------------------------------------------------");

            System.out.printf("\t%-8d", 1);
            System.out.printf("%-14s" , updateMobil.getMerk());
            System.out.printf("\t\t%-15s" , updateMobil.getTipe());
            System.out.printf("\t%-15s" , updateMobil.getNoKendaraan());
            System.out.printf("\t\t%-15d" , updateMobil.getHarga());
            System.out.printf("\t%d\n" , updateMobil.getJumlah());
            System.out.println("------------------------------------------------------------------------------------------------");

            System.out.println("1. Merk");
            System.out.println("2. Tipe");
            System.out.println("3. No.kendaraan");
            System.out.println("4. Jumlah");
            System.out.println("5. Harga");
            System.out.println("6. Selesai");

            var inputUbah = ScannerUtil.input("Pilih dengan angka untuk data yang akan diubah");

            Mobil newUpdateMobil = updateMobil;

            if (inputUbah.equals("1")){
                var inputMerk = ScannerUtil.input("Data baru");
                updateMobil.setMerk(inputMerk);
                System.out.println(updateMobil.getMerk());
            } else if (inputUbah.equals("2")) {
                var inputTipe = ScannerUtil.input("Data baru");
                updateMobil.setTipe(inputTipe);
            } else if (inputUbah.equals("3")) {
                var inputNoKendaraan = ScannerUtil.input("Data baru");
                updateMobil.setNoKendaraan(inputNoKendaraan);
            } else if (inputUbah.equals("4")) {
                var inputJumlah = ScannerUtil.inputInteger("Data baru");
                updateMobil.setJumlah(inputJumlah);
            } else if (inputUbah.equals("5")){
                var inputHarga = ScannerUtil.inputInteger("Data baru");
                updateMobil.setHarga(inputHarga);
            } else if (inputUbah.equals("6")) {
                mobilService.save(updateMobil);
                break;
            } else {
                System.out.println("Pilihan Tidak Dimengerti");
            }
        }
    }

    public void deleteMobil() {
        allCar();
        var input = ScannerUtil.inputInteger("Pilih Nomor yang akan di hapus");

        Mobil mobil = mobilService.findById(input);

        System.out.println(mobil);

        var yesOrNo = ScannerUtil.input("yakin data akan dihapus");
        if (yesOrNo.equals("y")) {
            mobilService.delete(input);
        } else if (yesOrNo.equals("n")){
            System.out.println("Data Gagal di Ubah");
            return;
        } else {
            System.out.println("Pilihan Tidak di Mengerti");
        }
    }

    public void addSewaMobil () {
        var messageBlank = "Input tidak boleh Kosong";
        var message = "Gagal Menambahkan Data";

        var inputNama = ScannerUtil.input("Masukkan Nama");
        if (inputNama.isBlank() || inputNama.equals("x")){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }
        var inputNoKtp = ScannerUtil.input("Masukkan No. KTP");
        if (inputNoKtp.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        allCar();

        var input = ScannerUtil.input("Pilih Nomor");
        if (input.isBlank() || input.equals("x")){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        }

        Supplier<Mobil> mobilSupplier =  () -> mobilService.findById(Integer.valueOf(input));
        Mobil mobil = mobilSupplier.get();
        SewaMobil sewaMobil = new SewaMobil();
        sewaMobil.setNamaCustomer(inputNama);
        sewaMobil.setNoKtp(inputNoKtp);
        sewaMobil.setMobil(mobil);

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t \t \t \t \t \t \t \t Daftar Mobil");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("| \tNo\t");
        System.out.print("|\tNama Customer\t\t");
        System.out.print("|\tNo. KTP\t\t\t");
        System.out.print("|\tTanggal\t\t\t");
        System.out.print("|\tMerk\t\t\t");
        System.out.print("|\tTipe\t\t");
        System.out.print("|\tNo.Kendaraan\t");
        System.out.print("|\tHarga Sewa\t");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.print("\t"  + 1 + "\t");
        System.out.print("\t" + sewaMobil.getNamaCustomer() + "\t\t");
        System.out.print("\t\t\t" + sewaMobil.getNoKtp() + "\t");
        System.out.print("\t" + sewaMobil.getTanggal() + "\t\t");
        System.out.print("\t"+sewaMobil.getMobil().getMerk() + "\t\t");
        System.out.print("\t"+sewaMobil.getMobil().getTipe() + " \t\t");
        System.out.print("\t"+sewaMobil.getMobil().getNoKendaraan() + " \t\t");
        System.out.print("\t"+sewaMobil.getMobil().getHarga() + " \t\t");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        sewaMobilService.addSewaMobil(sewaMobil);
    }

    public void addReturnMobil() {
        var messageBlank = "Input tidak boleh Kosong";
        var message = "Gagal Menambahkan Data";


        var inputNoKtp = ScannerUtil.input("Masukkan NoKtp");
        if (inputNoKtp.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
        }

        SewaMobil sewaMobilServiceByNoKtp = sewaMobilService.findByNoKtp(inputNoKtp);
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t Data");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.print("| \tNo.\t");
        System.out.print("|\tNama\t\t\t");
        System.out.print("|\tNo.Ktp\t\t");
        System.out.print("|\tNama Merk Mobil\t");
        System.out.print("|\tNama Merk Tipe\t");
        System.out.print("|\tNomor Kendaraan\t");
        System.out.println("|\tTanggal Pinjam\t |");
        System.out.println("------------------------------------------------------------------------------------------------");


        System.out.printf("\t%-8d", 1.);
        System.out.printf("%-14s" , sewaMobilServiceByNoKtp.getNamaCustomer());
        System.out.printf("\t\t%-15s" , sewaMobilServiceByNoKtp.getNoKtp());
        System.out.printf("\t%-15s" , sewaMobilServiceByNoKtp.getMobil().getMerk());
        System.out.printf("\t\t%-15s" , sewaMobilServiceByNoKtp.getMobil().getTipe());
        System.out.printf("\t\t%-15s" , sewaMobilServiceByNoKtp.getMobil().getNoKendaraan());
        System.out.printf("\t%tF\n" , sewaMobilServiceByNoKtp.getTanggal());
        System.out.println("------------------------------------------------------------------------------------------------");


        var input = ScannerUtil.input("Apakah data Benar y/n");
        if (input.equals("y")){
            var inputJangkaSewa = ScannerUtil.inputInteger("Masukkan Jangka Sewa");
            if (inputJangkaSewa > 0){
                System.out.println(messageBlank);
                System.out.println(message);
                ReturnMobil returnMobil = new ReturnMobil();
                returnMobil.setJangkaSewa(inputJangkaSewa);
                returnMobil.setSewaMobil(sewaMobilServiceByNoKtp);

                returnMobilService.addReturnMobil(returnMobil);
                System.out.println("Data Berhasil Ditambahkan");
            }
        } else if (input.equals("n")) {
            System.out.println(message);
            return;
        } else if (input.isBlank()){
            System.out.println(messageBlank);
            System.out.println(message);
            return;
        } else {
            System.out.println("Pilihan Tidak Dimengerti");
            System.out.println(message);
            return;
        }
    }

    public void allCar() {
        List<Mobil> allCar = mobilService.showAllMobil();

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tDaftar Mobil");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.print("| \tNo\t");
        System.out.print("|\tMerk\t\t\t");
        System.out.print("|\tTipe\t\t");
        System.out.print("|\tNo.Kendaraan\t");
        System.out.print("|\tHarga Sewa\t");
        System.out.println("|\tJumlah\t |");
        System.out.println("------------------------------------------------------------------------------------------------");

        var no = 0;

        for (var car : allCar) {
            no++;
            System.out.printf("\t%-8d", no);
            System.out.printf("%-14s" , car.getMerk());
            System.out.printf("\t\t%-15s" , car.getTipe());
            System.out.printf("\t%-15s" , car.getNoKendaraan());
            System.out.printf("\t\t%-15d" , car.getHarga());
            System.out.printf("\t%d\n" , car.getJumlah());
            System.out.println("------------------------------------------------------------------------------------------------");
        }
    }

    public void allSewaMobil () {
        List<SewaMobil> allSewaMobil = sewaMobilService.getAllSewaMobil();

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tDaftar Sewa Mobil");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("|\t" + "no");
        System.out.print("\t|\tNama Customer");
        System.out.print("\t\t\t\t|\t\tNo. Ktp");
        System.out.print("\t\t\t\t|\tTanggal");
        System.out.print("\t\t|\tMerk");
        System.out.print("\t\t\t|\tTipe");
        System.out.print("\t|\tNo.Kendaraan");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        var no = 0;

        for (var sewaMobil : allSewaMobil) {
            no++;

            System.out.printf("\t%-8d", no);
            System.out.printf("%-30s" ,sewaMobil.getNamaCustomer());
            System.out.printf("\t%-25s",  sewaMobil.getNoKtp());
            System.out.printf("\t%-12tF" , sewaMobil.getTanggal());
            System.out.printf("\t%-14s" , sewaMobil.getMobil().getMerk());
            System.out.printf("\t%-15s" , sewaMobil.getMobil().getTipe());
            System.out.printf("\t%-15s" , sewaMobil.getMobil().getNoKendaraan());
            System.out.printf("\t\t%-15d" , sewaMobil.getMobil().getHarga());
            System.out.printf("\t\t%d\n" , sewaMobil.getMobil().getJumlah());
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void allReturnCar () {
        List<ReturnMobil> allCarRent = returnMobilService.getAllCarRent();

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tDaftar Semua Return Mobil");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("|\t" + "No");
        System.out.print("\t|\tNama Customer");
        System.out.print("\t\t\t\t|\t\tNomor Ktp");
        System.out.print("\t\t\t\t|\tTanggal Pinjam");
        System.out.print("\t\t\t\t|\tTanggal kembali");
        System.out.print("\t\t|\tMerk");
        System.out.print("\t\t\t|\tTipe");
        System.out.print("\t|\tNo.Kendaraan");
        System.out.print("\t|\tJangka Sewa");
        System.out.println("\t\t|\tJumlah Pembayaran\t|");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        var no = 0;

        for (var returnMobil : allCarRent) {
            no++;

            System.out.printf("\t%-8d", no);
            System.out.printf("%-30s" ,returnMobil.getSewaMobil().getNamaCustomer());
            System.out.printf("\t%-25s",  returnMobil.getSewaMobil().getNoKtp());
            System.out.printf("\t%-12tF" , returnMobil.getSewaMobil().getTanggal());
            System.out.printf("\t%-12tF" , returnMobil.getTanggalKembali());
            System.out.printf("\t%-14s" , returnMobil.getSewaMobil().getMobil().getMerk());
            System.out.printf("\t%-15s" , returnMobil.getSewaMobil().getMobil().getTipe());
            System.out.printf("\t%-15s" , returnMobil.getSewaMobil().getMobil().getNoKendaraan());
            System.out.printf("\t\t%-15d" , returnMobil.getJangkaSewa());
            System.out.printf("\t\t%d\n" , returnMobil.getJumlahPembayaran());
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

}
