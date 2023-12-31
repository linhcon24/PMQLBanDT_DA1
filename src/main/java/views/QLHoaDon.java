/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import domainmodels.ChiTietSP;
import domainmodels.HoaDon;
import domainmodels.HoaDonChiTiet;
import domainmodels.KhachHang;
import domainmodels.NhanVien;
import domainmodels.SanPham;
import java.awt.Component;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import services.ChiTietSPServices;
import services.HoaDonChiTietServices;
import services.HoaDonServices;
import services.IChiTietSPServices;
import services.IHoaDonChiTietServies;
import services.IHoaDonServices;
import services.IKhachHangService;
import services.INhanVienServices;
import services.IQLSanPhamServices;
import services.ISanPhamServices;
import services.KhachHangService;
import services.NhanVienServices;
import services.QLSanPhamServices;
import viewmodels.ChiTietSPViewModels;

/**
 *
 * @author adm
 */
public class QLHoaDon extends javax.swing.JFrame {

    /**
     * Creates new form QLHoaDon
     */
    private IHoaDonServices hoaDonServices;
    private IHoaDonChiTietServies hoaDonChiTietServies;
    private IChiTietSPServices chiTietSPServices;
    private INhanVienServices nhanVienServices;
    private IKhachHangService khachHangService;
    private IQLSanPhamServices sanPhamServices;

    public QLHoaDon() {
        initComponents();

        hoaDonServices = new HoaDonServices();
        hoaDonChiTietServies = new HoaDonChiTietServices();
        chiTietSPServices = new ChiTietSPServices();
        nhanVienServices = new NhanVienServices();
        khachHangService = new KhachHangService();
        sanPhamServices = new QLSanPhamServices();

        loadHoaDonChiTiet();

    }

    public void loadHoaDonChiTiet() {
        tbHoaDonChiTiet.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbHoaDon.getModel();
        model.setRowCount(0);
        List<HoaDon> hoaDons = hoaDonServices.getALL();
        List<NhanVien> nhanViens = nhanVienServices.getALL();
        List<KhachHang> khachHangs = khachHangService.getALL();
        String tenNhanVien = "";
        String tenKH = "";
        for (HoaDon hoaDon : hoaDons) {
            for (KhachHang khachHang : khachHangs) {
                if (hoaDon.getKhachHang().getMaKH().equals(khachHang.getMaKH())) {
                    tenKH = khachHang.getTenKH();
                }
            }
            for (NhanVien nhanVien : nhanViens) {
                if (hoaDon.getNhanVien().getMaNV().equals(nhanVien.getMaNV())) {
                    tenNhanVien = nhanVien.getTenNV();
                }
            }
            Object[] data = new Object[]{
                hoaDon.getMaHD(),
                hoaDon.getTenNguoiNhan(),
                hoaDon.getNgayMua(),
                hoaDon.getGhiChu(),
                hoaDon.getDiaChiNhanHang(),
                tenKH,
                tenNhanVien,
                hoaDon.getGiamGia(),
                hoaDon.getTongTien(),
                hoaDon.getTrangThai()

            };
            model.addRow(data);

        }
    }

    class myTableCellRender implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            tbHoaDonChiTiet.setRowHeight(70);

            return (Component) value;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hoadon = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHoaDonChiTiet = new javax.swing.JTable();
        lbHoaDonChiTiet = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        hoadon.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_purchase_order_30px.png"))); // NOI18N
        jLabel23.setText("Hóa Đơn");

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HD", "Tên Người Nhận", "Ngày Mua", "Ghi Chú", "Địa Chỉ Nhận Hàng", "Tên Khách Hàng", "Tên Nhân Viên", "Giảm Giá ", "Tổng Tiền", "Trạng Thái"
            }
        ));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Invoice_30px_1.png"))); // NOI18N
        jLabel24.setText("Hóa Đơn Chi Tiết");

        tbHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ảnh", "Mã Imei", "Số Lượng", "Đơn Giá"
            }
        ));
        tbHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbHoaDonChiTiet);

        lbHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbHoaDonChiTiet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHoaDonChiTiet.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout hoadonLayout = new javax.swing.GroupLayout(hoadon);
        hoadon.setLayout(hoadonLayout);
        hoadonLayout.setHorizontalGroup(
            hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoadonLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)
                        .addComponent(jLabel24))
                    .addComponent(lbHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        hoadonLayout.setVerticalGroup(
            hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoadonLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        // TODO add your handling code here:
        int index = tbHoaDon.getSelectedRow();
        String ma = tbHoaDon.getValueAt(index, 0).toString();
        lbHoaDonChiTiet.setText(ma);
        List<HoaDonChiTiet> hdcts = hoaDonChiTietServies.getALL(ma);
        DefaultTableModel model = (DefaultTableModel) tbHoaDonChiTiet.getModel();
        model.setRowCount(0);
        List<ChiTietSPViewModels> chiTietSPs = chiTietSPServices.getALL();
        String anh = "";
        for (HoaDonChiTiet hdct : hdcts) {
            JLabel label = new JLabel();
            for (ChiTietSPViewModels chiTietSP : chiTietSPs) {
                if (chiTietSP.getAnh().equals(hdct.getMaImei().getAnh())) {
                    ImageIcon icon = new ImageIcon(chiTietSP.getAnh());
                    Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                }
            }

            Object[] data = new Object[]{
                label,
                hdct.getMaImei().getMaImei(),
                hdct.getSoLuong(),
                hdct.getDonGia()
            };
            model.addRow(data);
        }


    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void tbHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonChiTietMouseClicked
        // TODO add your handling code here:
//        
//        DefaultTableModel model = (DefaultTableModel) tbHoaDon.getModel();
//        model.setRowCount(0);
//        List<HoaDon> hoaDons = hoaDonServices.getALL();
//        List<NhanVien> nhanViens = nhanVienServices.getALL();
//        List<KhachHang> khachHangs = khachHangService.getALL();
//        String tenKhachHang = "";
//        String tenNV = "";
//        for (HoaDon hoaDon : hoaDons) {
//            for (KhachHang khachHang : khachHangs) {
//                if (hoaDon.getKhachHang().getMaKH().equals(khachHang.getMaKH())) {
//                    tenKhachHang = khachHang.getTenKH();
//                }
//            }
//            for (NhanVien nhanVien : nhanViens) {
//                if (hoaDon.getNhanVien().getMaNV().equals(nhanVien.getMaNV())) {
//                    tenNV = nhanVien.getTenNV();
//                }
//            }
//            Object[] data = new Object[]{
//                hoaDon.getMaHD(),
//                hoaDon.getTenNguoiNhan(),
//                hoaDon.getNgayMua(),
//                hoaDon.getGhiChu(),
//                hoaDon.getDiaChiNhanHang(),
//                tenKhachHang,
//                tenNV,
//                hoaDon.getGiamGia(),
//                hoaDon.getTongTien(),
//                hoaDon.getTrangThai()
//
//            };
//            model.addRow(data);
//        }
//             DefaultTableModel model = (DefaultTableModel) tbHoaDon.getModel();
//            model.setRowCount(0);
//            List<HoaDon> hoaDons = hoaDonService.layAllHoaDon();
//            List<NhanVien> nhanViens = nhanVienService.layAllNhanVien();
//            List<KhachHang> khachHangs = khachHangService.layAllKhachHang();
//            String tenKhachHang = "";
//            String tenNhanVien = "";
//            for (HoaDon hoaDon : hoaDons) {
//                for (KhachHang khachHang : khachHangs) {
//                    if (hoaDon.getMaKhachHang().equals(khachHang.getMaKhachHang())) {
//                        tenKhachHang = khachHang.getTenKhachHang();
//                    }
//                }
//                for (NhanVien nhanVien : nhanViens) {
//                    if (hoaDon.getMaNhanVien().equals(nhanVien.getMaNhanVien())) {
//                        tenNhanVien = nhanVien.getTenNhanVien();
//                    }
//                }

    }//GEN-LAST:event_tbHoaDonChiTietMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel hoadon;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbHoaDonChiTiet;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbHoaDonChiTiet;
    // End of variables declaration//GEN-END:variables
}
