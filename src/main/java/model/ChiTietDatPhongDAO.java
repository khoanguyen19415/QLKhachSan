package model;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDatPhongDAO {

    public List<ChiTietDatPhong> getByDatPhong(int maDatPhong) {
        List<ChiTietDatPhong> list = new ArrayList<>();
        String sql = "SELECT ctdp.MaCTDP, ctdp.MaDatPhong, ctdp.MaPhong, p.TenPhong, ctdp.Gia, ctdp.GhiChu, ctdp.TrangThai "
                + "FROM ChiTietDatPhong ctdp JOIN Phong p ON ctdp.MaPhong = p.MaPhong WHERE ctdp.MaDatPhong = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatPhong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietDatPhong ct = new ChiTietDatPhong();
                    ct.setMaCTDP(rs.getInt("MaCTDP"));
                    ct.setMaDatPhong(rs.getInt("MaDatPhong"));
                    ct.setMaPhong(rs.getInt("MaPhong"));
                    ct.setTenPhong(rs.getString("TenPhong"));
                    ct.setGia(rs.getDouble("Gia"));
                    ct.setGhiChu(rs.getString("GhiChu"));
                    ct.setTrangThai(rs.getString("TrangThai"));
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertList(int maDatPhong, List<ChiTietDatPhong> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        String sql = "INSERT INTO ChiTietDatPhong (MaDatPhong, MaPhong, Gia, GhiChu, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietDatPhong ct : list) {
                ps.setInt(1, maDatPhong);
                ps.setInt(2, ct.getMaPhong());
                ps.setDouble(3, ct.getGia());
                ps.setString(4, ct.getGhiChu());
                ps.setString(5, ct.getTrangThai() == null ? "Chờ duyệt" : ct.getTrangThai());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByDatPhong(int maDatPhong) {
        String sql = "DELETE FROM ChiTietDatPhong WHERE MaDatPhong = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatPhong);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật trạng thái cho 1 chi tiết (sẽ đồng bộ phòng và đơn)
    public boolean updateTrangThai(int maCTDP, String trangThai) {
        String sqlUpdateCT = "UPDATE ChiTietDatPhong SET TrangThai = ? WHERE MaCTDP = ?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // dùng transaction

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateCT)) {
                ps.setString(1, trangThai);
                ps.setInt(2, maCTDP);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    int maPhong = 0;
                    int maDatPhong = 0;

                    String getInfo = "SELECT MaPhong, MaDatPhong FROM ChiTietDatPhong WHERE MaCTDP = ?";
                    try (PreparedStatement ps2 = conn.prepareStatement(getInfo)) {
                        ps2.setInt(1, maCTDP);
                        try (ResultSet rs = ps2.executeQuery()) {
                            if (rs.next()) {
                                maPhong = rs.getInt("MaPhong");
                                maDatPhong = rs.getInt("MaDatPhong");
                            }
                        }
                    }

                    // map trạng thái chi tiết -> trạng thái Phong hợp lệ theo CHECK constraint
                    String phongStatus;
                    if (trangThai != null && trangThai.equalsIgnoreCase("Đang ở")) {
                        phongStatus = "Đang ở";        // hợp lệ
                    } else if (trangThai != null && trangThai.equalsIgnoreCase("Đã duyệt")) {
                        phongStatus = "Đã đặt";       // admin duyệt -> phòng bị đánh là đã đặt
                    } else if (trangThai != null && (trangThai.equalsIgnoreCase("Hủy")
                            || trangThai.equalsIgnoreCase("Đã trả")
                            || trangThai.equalsIgnoreCase("Đã trả phòng"))) {
                        phongStatus = "Trống";        // hủy/đã trả -> trống
                    } else {
                        // mặc định giữ Trống để không phá constraint; "Chờ duyệt" không đổi trạng thái Phòng
                        phongStatus = "Trống";
                    }

                    String sqlPhong = "UPDATE Phong SET TrangThai = ? WHERE MaPhong = ?";
                    try (PreparedStatement ps3 = conn.prepareStatement(sqlPhong)) {
                        ps3.setString(1, phongStatus);
                        ps3.setInt(2, maPhong);
                        ps3.executeUpdate();
                    }

                    // Đồng bộ trạng thái đơn (DatPhong) dựa trên các chi tiết
                    syncTrangThaiDon(conn, maDatPhong);

                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }
            } catch (Exception ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm đồng bộ trạng thái đơn đặt phòng theo chi tiết phòng
    private void syncTrangThaiDon(Connection conn, int maDatPhong) throws SQLException {
        String sql = "UPDATE DatPhong "
                + "SET TrangThai = CASE "
                + " WHEN (SELECT COUNT(*) FROM ChiTietDatPhong WHERE MaDatPhong = ?) = "
                + "      (SELECT COUNT(*) FROM ChiTietDatPhong WHERE MaDatPhong = ? AND TrangThai = N'Đã trả') THEN N'Hoàn thành' "
                + " WHEN EXISTS (SELECT 1 FROM ChiTietDatPhong WHERE MaDatPhong = ? AND TrangThai = N'Đang ở') THEN N'Đang ở' "
                + " WHEN (SELECT COUNT(*) FROM ChiTietDatPhong WHERE MaDatPhong = ?) = "
                + "      (SELECT COUNT(*) FROM ChiTietDatPhong WHERE MaDatPhong = ? AND TrangThai = N'Hủy') THEN N'Hủy' "
                + " ELSE N'Đang xử lý' "
                + " END "
                + "WHERE MaDatPhong = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatPhong);
            ps.setInt(2, maDatPhong);
            ps.setInt(3, maDatPhong);
            ps.setInt(4, maDatPhong);
            ps.setInt(5, maDatPhong);
            ps.setInt(6, maDatPhong);

            ps.executeUpdate();
        }
    }
// ✅ Hủy tất cả đơn khác trùng phòng & thời gian

    public void huyDonTrungPhong(int maPhong, java.sql.Date ngayNhan, java.sql.Date ngayTra, int maCTDP) {
        String sql = "UPDATE ctdp "
                + "SET ctdp.TrangThai = N'Hủy' "
                + "FROM ChiTietDatPhong ctdp "
                + "JOIN DatPhong dp ON dp.MaDatPhong = ctdp.MaDatPhong "
                + "WHERE ctdp.MaPhong = ? "
                + "AND NOT (dp.NgayTra <= ? OR dp.NgayNhan >= ?) "
                + "AND ctdp.MaCTDP <> ? "
                + "AND ctdp.TrangThai IN (N'Chờ duyệt', N'Đã duyệt')";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhong);
            ps.setDate(2, ngayNhan);
            ps.setDate(3, ngayTra);
            ps.setInt(4, maCTDP);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChiTietDatPhong getById(int maCTDP) {
        String sql = "SELECT * FROM ChiTietDatPhong WHERE MaCTDP = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maCTDP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ChiTietDatPhong ct = new ChiTietDatPhong();
                    ct.setMaCTDP(rs.getInt("MaCTDP"));
                    ct.setMaDatPhong(rs.getInt("MaDatPhong"));
                    ct.setMaPhong(rs.getInt("MaPhong"));
                    ct.setGia(rs.getDouble("Gia"));
                    ct.setGhiChu(rs.getString("GhiChu"));
                    ct.setTrangThai(rs.getString("TrangThai"));
                    return ct;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
