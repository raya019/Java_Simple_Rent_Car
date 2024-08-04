package repository.sewamobil;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import entity.SewaMobil;
import util.DatabaseUtil;

import java.sql.*;
import java.util.*;

public class SewaMobilRepositoryImpl implements SewaMobilRepository {

    private HikariDataSource hikariDataSource;

    public SewaMobilRepositoryImpl(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public List<SewaMobil> getAllSewaMobil() {
        var sql = "select * from sewa_mobil inner join mobil on mobil.id = sewa_mobil.id_mobil";

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<SewaMobil> carRentList = new ArrayList<>();

            while (resultSet.next()){
                SewaMobil sewaMobil = new SewaMobil();

                sewaMobil.setId(resultSet.getInt("id"));
                sewaMobil.setNamaCustomer(resultSet.getString("nama_customer"));
                sewaMobil.setNoKtp(resultSet.getString("no_ktp"));
                sewaMobil.setTanggal(resultSet.getDate("tanggal").toLocalDate());

                Mobil mobil = new Mobil();

                mobil.setId(resultSet.getInt("id"));
                mobil.setMerk(resultSet.getString("merk"));
                mobil.setTipe(resultSet.getString("tipe"));
                mobil.setNoKendaraan(resultSet.getString("no_kendaraan"));
                mobil.setHarga(resultSet.getInt("harga"));
                mobil.setJumlah(resultSet.getInt("jumlah"));
                mobil.setAvaible(resultSet.getBoolean("is_avaible"));

                sewaMobil.setMobil(mobil);

                carRentList.add(sewaMobil);
            }
            return carRentList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SewaMobil addSewaMobil(SewaMobil sewaMobil) {

        var sql1 = """
                insert into sewa_mobil (nama_customer,no_ktp,id_mobil)
                values (?,?,?)
                """;

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatement1.setString(1, sewaMobil.getNamaCustomer());
            preparedStatement1.setString(2, sewaMobil.getNoKtp());
            preparedStatement1.setInt(3, sewaMobil.getMobil().getId());
            preparedStatement1.executeUpdate();

            try (ResultSet resultSet = preparedStatement1.getGeneratedKeys()) {
                if (resultSet.next()) {
                    sewaMobil.setId(resultSet.getInt("id"));
                }
            }

            return sewaMobil;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteOne(Integer id) {
        var sql = """
                delete from sewa_mobil where id = ?
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(){
        var sql = """
                delete from sewa_mobil
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            Statement statement = connection.createStatement();) {

            statement.executeUpdate(sql);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SewaMobil> findByNoKtp (String noKtp) {
        var sql = """
                    select * from sewa_mobil
                     inner join mobil on mobil.id = sewa_mobil.id_mobil where no_ktp = ?
                """;

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1,noKtp);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    SewaMobil sewaMobil = new SewaMobil();
                    sewaMobil.setId(resultSet.getInt("id"));
                    sewaMobil.setNamaCustomer(resultSet.getString("nama_customer"));
                    sewaMobil.setNoKtp(resultSet.getString("no_ktp"));
                    sewaMobil.setTanggal(resultSet.getDate("tanggal").toLocalDate());

                    Mobil mobil = new Mobil();
                    mobil.setId(resultSet.getInt("id_mobil"));
                    mobil.setMerk(resultSet.getString("merk"));
                    mobil.setTipe(resultSet.getString("tipe"));
                    mobil.setNoKendaraan(resultSet.getString("no_kendaraan"));
                    mobil.setHarga(resultSet.getInt("harga"));
                    mobil.setJumlah(resultSet.getInt("jumlah"));
                    mobil.setAvaible(resultSet.getBoolean("is_avaible"));
                    sewaMobil.setMobil(mobil);

                    return Optional.ofNullable(sewaMobil);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
