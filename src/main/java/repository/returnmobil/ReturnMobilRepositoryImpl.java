package repository.returnmobil;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import entity.ReturnMobil;
import entity.SewaMobil;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnMobilRepositoryImpl implements ReturnMobilRepository {

    private HikariDataSource hikariDataSource;

    public ReturnMobilRepositoryImpl(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public List<ReturnMobil> getAllCarReturn() {
        var sql = """
                select * from return_mobil
                	inner join sewa_mobil on return_mobil.no_ktp_customer = sewa_mobil.no_ktp
                	inner join mobil on mobil.id = sewa_mobil.id_mobil ;
                """;

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<ReturnMobil> carReturnList = new ArrayList<>();

            while (resultSet.next()){
                Mobil mobil = new Mobil();
                mobil.setId(resultSet.getInt("id"));
                mobil.setMerk(resultSet.getString("merk"));
                mobil.setTipe(resultSet.getString("tipe"));
                mobil.setNoKendaraan(resultSet.getString("no_kendaraan"));
                mobil.setHarga(resultSet.getInt("harga"));
                mobil.setJumlah(resultSet.getInt("jumlah"));
                mobil.setAvaible(resultSet.getBoolean("is_avaible"));

                SewaMobil sewaMobil =  new SewaMobil();
                sewaMobil.setId(resultSet.getInt("id_mobil"));
                sewaMobil.setNoKtp(resultSet.getString("no_ktp"));
                sewaMobil.setNamaCustomer(resultSet.getString("nama_customer"));
                sewaMobil.setTanggal(resultSet.getDate("tanggal").toLocalDate());
                sewaMobil.setMobil(mobil);

                ReturnMobil returnMobil = new ReturnMobil();
                returnMobil.setId(resultSet.getInt("id"));
                returnMobil.setJumlahPembayaran(resultSet.getInt("jumlah_pembayaran"));
                returnMobil.setJangkaSewa(resultSet.getInt("jangka_sewa"));
                returnMobil.setTanggalKembali(resultSet.getDate("tanggal").toLocalDate());
                returnMobil.setSewaMobil(sewaMobil);

                carReturnList.add(returnMobil);
            }
            return carReturnList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnMobil addReturnMobil(ReturnMobil returnMobil){
        var sql1 = """
                insert into return_mobil(jumlah_pembayaran , jangka_sewa , tanggal_kembali , no_ktp_customer)
                values (?,?,?,?)
                """;

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
             ) {

             preparedStatement1.setInt(1,returnMobil.getJumlahPembayaran());
             preparedStatement1.setInt(2,returnMobil.getJangkaSewa());
             preparedStatement1.setDate(3, Date.valueOf(returnMobil.getTanggalKembali()));
             preparedStatement1.setString(4,returnMobil.getSewaMobil().getNoKtp());
             preparedStatement1.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement1.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    returnMobil.setId(generatedKeys.getInt("id"));
                }
            }

            return returnMobil;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(){
        var sql = """
                delete from return_mobil
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            Statement statement = connection.createStatement();) {

            statement.executeUpdate(sql);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOne(Integer id) {
        var sql = """
                delete from return_mobil where id = ?
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
