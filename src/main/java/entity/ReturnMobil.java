package entity;

import java.time.LocalDate;
import java.util.Objects;

public class ReturnMobil {
    private Integer id;

    private Integer jumlahPembayaran;

    private Integer jangkaSewa;

    private LocalDate tanggalKembali;

    private SewaMobil SewaMobil;

    public ReturnMobil() {
    }

    public ReturnMobil(Integer id, Integer jumlahPembayaran, Integer jangkaSewa, LocalDate tanggalKembali, entity.SewaMobil sewaMobil) {
        this.id = id;
        this.jumlahPembayaran = jumlahPembayaran;
        this.jangkaSewa = jangkaSewa;
        this.tanggalKembali = tanggalKembali;
        SewaMobil = sewaMobil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public void setJumlahPembayaran(Integer jumlahPembayaran) {
        this.jumlahPembayaran = jumlahPembayaran;
    }

    public Integer getJangkaSewa() {
        return jangkaSewa;
    }

    public void setJangkaSewa(Integer jangkaSewa) {
        this.jangkaSewa = jangkaSewa;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public entity.SewaMobil getSewaMobil() {
        return SewaMobil;
    }

    public void setSewaMobil(entity.SewaMobil sewaMobil) {
        SewaMobil = sewaMobil;
    }


    @Override
    public String toString() {
        return "ReturnMobil{" +
                "id=" + id +
                ", jumlahPembayaran=" + jumlahPembayaran +
                ", jangkaSewa=" + jangkaSewa +
                ", tanggalKembali=" + tanggalKembali +
                ", SewaMobil=" + SewaMobil +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReturnMobil mobil = (ReturnMobil) o;

        if (!Objects.equals(id, mobil.id)) return false;
        if (!Objects.equals(jumlahPembayaran, mobil.jumlahPembayaran))
            return false;
        if (!Objects.equals(jangkaSewa, mobil.jangkaSewa)) return false;
        if (!Objects.equals(tanggalKembali, mobil.tanggalKembali))
            return false;
        return Objects.equals(SewaMobil, mobil.SewaMobil);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (jumlahPembayaran != null ? jumlahPembayaran.hashCode() : 0);
        result = 31 * result + (jangkaSewa != null ? jangkaSewa.hashCode() : 0);
        result = 31 * result + (tanggalKembali != null ? tanggalKembali.hashCode() : 0);
        result = 31 * result + (SewaMobil != null ? SewaMobil.hashCode() : 0);
        return result;
    }
}
