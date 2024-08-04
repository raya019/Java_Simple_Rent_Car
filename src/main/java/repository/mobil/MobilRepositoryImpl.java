package repository.mobil;

import com.zaxxer.hikari.HikariDataSource;
import entity.Mobil;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MobilRepositoryImpl implements MobilRepository {

    private HikariDataSource dataSource;

    public MobilRepositoryImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Mobil> getAll() {
        var sql = """
                select * from mobil
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            List<Mobil> carList = new ArrayList<>();

            while (resultSet.next()){

                Mobil mobil = new Mobil();

                mobil.setId(resultSet.getInt("id"));
                mobil.setMerk(resultSet.getString("merk"));
                mobil.setTipe(resultSet.getString("tipe"));
                mobil.setNoKendaraan(resultSet.getString("no_kendaraan"));
                mobil.setHarga(resultSet.getInt("harga"));
                mobil.setJumlah(resultSet.getInt("jumlah"));
                mobil.setAvaible(resultSet.getBoolean("is_avaible"));


                carList.add(mobil);
            }
            return carList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mobil add(Mobil car) {
        var sql = """
                insert into mobil(merk,tipe,no_kendaraan,harga,jumlah)
                values(?,?,?,?,?)
                """ ;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatement.setString(1,car.getMerk());
            preparedStatement.setString(2, car.getTipe());
            preparedStatement.setString(3,car.getNoKendaraan());
            preparedStatement.setInt(4,car.getHarga());
            preparedStatement.setInt(5,car.getJumlah());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    car.setId(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public Mobil update(Mobil car) {
        var sql = """
                update mobil set  
                    merk = ?,
                    tipe = ?,
                    no_kendaraan = ?,
                    harga = ?,
                    jumlah= ?,
                    is_avaible = ?
                where id = ?
                """;

        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, car.getMerk());
                preparedStatement.setString(2, car.getTipe());
                preparedStatement.setString(3, car.getNoKendaraan());
                preparedStatement.setInt(4, car.getHarga());
                preparedStatement.setInt(5, car.getJumlah());
                preparedStatement.setBoolean(6, car.isAvaible());
                preparedStatement.setInt(7, car.getId());

                preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public void deleteOne(Integer id) {
        var sql = """
                delete from mobil where id = ?
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
                delete from mobil
                """;
        try(Connection connection = DatabaseUtil.getDataSource().getConnection();
            Statement statement = connection.createStatement();) {

            statement.executeUpdate(sql);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Mobil> findById(Integer id) {
        var sql = """
            select * from mobil where id = ?
            """;
        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Mobil mobil = new Mobil();

                        mobil.setId(resultSet.getInt("id"));
                        mobil.setMerk(resultSet.getString("merk"));
                        mobil.setTipe(resultSet.getString("tipe"));
                        mobil.setNoKendaraan(resultSet.getString("no_kendaraan"));
                        mobil.setHarga(resultSet.getInt("harga"));
                        mobil.setJumlah(resultSet.getInt("jumlah"));
                        mobil.setAvaible(resultSet.getBoolean("is_avaible"));

                        return Optional.ofNullable(mobil);
                    }
                        return Optional.empty();

                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public boolean isExistByid(Integer id){
//        var sql1 = """
//                select * from mobil where id = ? and is_avaible = true and jumlah > 0
//                """;
//
//        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
//             PreparedStatement preparedStatement1 = connection.prepareStatement(sql1)) {
//            preparedStatement1.setInt(1,id);
//
//            try (ResultSet resultSet = preparedStatement1.executeQuery()) {
//                if (resultSet.next()){
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public boolean isExistByNoKendaraan(String noKendaraan) {

        var sql = """
                select * from mobil where no_kendaraan = ?
                """;

        try (Connection connection = DatabaseUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1,noKendaraan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    return true;
                } else{
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
