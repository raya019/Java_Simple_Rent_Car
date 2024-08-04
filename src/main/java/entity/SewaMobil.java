package entity;

import java.time.LocalDate;
import java.util.Objects;

public class SewaMobil {
    private Integer id;

    private String namaCustomer;

    private String noKtp;

    private LocalDate tanggal = LocalDate.now();

    private Mobil mobil;

    public SewaMobil() {
    }

    public SewaMobil(Integer id, String namaCustomer, String noKtp, LocalDate tanggal, Mobil mobil) {
        this.id = id;
        this.namaCustomer = namaCustomer;
        this.noKtp = noKtp;
        this.tanggal = tanggal;
        this.mobil = mobil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }


    public Mobil getMobil() {
        return mobil;
    }

    public void setMobil(Mobil mobil) {
        this.mobil = mobil;
    }

    @Override
    public String toString() {
        return "SewaMobil{" +
                "id=" + id +
                ", namaCustomer='" + namaCustomer + '\'' +
                ", noKtp='" + noKtp + '\'' +
                ", tanggal=" + tanggal +
                ", mobil=" + mobil +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SewaMobil sewaMobil = (SewaMobil) o;
        return Objects.equals(id, sewaMobil.id) && Objects.equals(namaCustomer, sewaMobil.namaCustomer) && Objects.equals(noKtp, sewaMobil.noKtp) && Objects.equals(tanggal, sewaMobil.tanggal) && Objects.equals(mobil, sewaMobil.mobil);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(namaCustomer);
        result = 31 * result + Objects.hashCode(noKtp);
        result = 31 * result + Objects.hashCode(tanggal);
        result = 31 * result + Objects.hashCode(mobil);
        return result;
    }
}
