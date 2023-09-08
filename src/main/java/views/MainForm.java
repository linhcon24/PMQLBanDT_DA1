/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import domainmodels.BoNhoTrong;
import domainmodels.ChiTietSP;
import domainmodels.MauSac;
import domainmodels.SanPham;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import services.AnhService;
import services.ChiTietSPServices;
import services.ChuyenManHinhServices;
import services.IChiTietSPServices;
import services.IQLBoNhoTrongServices;
import services.IQLMauSacServices;
import services.IQLSanPhamServices;
import services.ImeiServices;
import services.MenuServices;

import services.PhanQuyenServices;
import services.QLBoNhoTrongServices;
import services.QLMauSacServices;
import services.QLSanPhamServices;
import viewmodels.ChiTietSPViewModels;

/**
 *
 * @author HANGOCHAN
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    CardLayout cardLayout;
    CardLayout cl;
    CardLayout c2;
    private IQLSanPhamServices sanPhamServices;
    private IQLMauSacServices mauSacServices;
    private IQLBoNhoTrongServices boNhoTrongServices;
    private IChiTietSPServices chiTietSPServices;
    private ChuyenManHinhServices chuyenManHinhServices;
    private PhanQuyenServices pqs;

    public MainForm() {
        initComponents();

        setTitle("Hệ Thống Bán Điện Thoại");
//        ImageIcon icon = new ImageIcon("/image/icons8_add_30px_6.png");
//        Image image = icon.getImage();
//        setIconImage(image);
        pqs = new PhanQuyenServices();
        cardLayout = (CardLayout) giaodien.getLayout();

        lbChao.setText("Xin chào : " + pqs.getHoTen());
        String chucVu = "";
        if (pqs.getVaiTro() == 0) {
            chucVu = "Quản lý";

        } else {
            chucVu = "Nhân viên";
            btnQLNhanVien.setEnabled(false);
            btnQLSanPham.setEnabled(false);
            btnThongKe.setEnabled(false);
            btnQLKhuyenMai.setEnabled(false);
        }
        lbChucVu.setText("Chức vụ : " + chucVu);
        chuyenManHinhServices = new ChuyenManHinhServices(main);
//        chuyenManHinhServices.setView(sanpham, btnQLSanPham);
        List<MenuServices> listItems = new ArrayList<>();
        if (pqs.getVaiTro() == 0) {
            listItems.add(new MenuServices("sanpham", sanpham, btnQLSanPham));
            listItems.add(new MenuServices("doimatkhau", doimatkhau, btnDoiMatKhau));
            listItems.add(new MenuServices("nhanvien", nhanvien, btnQLNhanVien));
            listItems.add(new MenuServices("banhang", banhang, btnQLBanHang));
            listItems.add(new MenuServices("khuyenmai", khuyenmai, btnQLKhuyenMai));
            listItems.add(new MenuServices("khachhang", khachhang, btnKhachHang));
            listItems.add(new MenuServices("thongke", thongke, btnThongKe));
            listItems.add(new MenuServices("hoadon", hoadon, btnQLHoaDon));
            listItems.add(new MenuServices("baohanh", baohanh, btnQLBaoHanh));
        } else {
            btnQLNhanVien.setEnabled(false);
            btnQLSanPham.setEnabled(false);
            btnThongKe.setEnabled(false);
            btnQLKhuyenMai.setEnabled(false);
            listItems.add(new MenuServices("doimatkhau", doimatkhau, btnDoiMatKhau));
            listItems.add(new MenuServices("banhang", banhang, btnQLBanHang));
            listItems.add(new MenuServices("khachhang", khachhang, btnKhachHang));
            listItems.add(new MenuServices("hoadon", hoadon, btnQLHoaDon));
            listItems.add(new MenuServices("baohanh", baohanh, btnQLBaoHanh));
        }

        chuyenManHinhServices.setEvent(listItems);
        pqs = new PhanQuyenServices();
        cardLayout = (CardLayout) giaodien.getLayout();

//        lbChao.setText("Xin chào : " + pqs.getHoTen());
//        String chucVu = "";
//        if (pqs.getVaiTro() == 0) {
//            chucVu = "Quản lý";
//
//        } else {
//            chucVu = "Nhân viên";
//            btnQLNhanVien.setEnabled(false);
//            btnQLSanPham.setEnabled(false);
//            btnThongKe.setEnabled(false);
//            btnQLKhuyenMai.setEnabled(false);
//        }
//        lbChucVu.setText("Chức vụ : " + chucVu);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        btnKhachHang = new javax.swing.JLabel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        btnQLSanPham = new javax.swing.JLabel();
        kGradientPanel12 = new keeptoo.KGradientPanel();
        btnQLNhanVien = new javax.swing.JLabel();
        kGradientPanel14 = new keeptoo.KGradientPanel();
        btnQLBanHang = new javax.swing.JLabel();
        kGradientPanel15 = new keeptoo.KGradientPanel();
        btnQLHoaDon = new javax.swing.JLabel();
        kGradientPanel16 = new keeptoo.KGradientPanel();
        btnThongKe = new javax.swing.JLabel();
        kGradientPanel17 = new keeptoo.KGradientPanel();
        btnQLKhuyenMai = new javax.swing.JLabel();
        kGradientPanel18 = new keeptoo.KGradientPanel();
        btnQLBaoHanh = new javax.swing.JLabel();
        kGradientPanel19 = new keeptoo.KGradientPanel();
        btnDoiMatKhau = new javax.swing.JLabel();
        kGradientPanel20 = new keeptoo.KGradientPanel();
        btnDangXuat = new javax.swing.JLabel();
        lbChao = new javax.swing.JLabel();
        lbChucVu = new javax.swing.JLabel();
        giaodien = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sanpham = new javax.swing.JPanel();
        doimatkhau = new javax.swing.JPanel();
        nhanvien = new javax.swing.JPanel();
        banhang = new javax.swing.JPanel();
        khuyenmai = new javax.swing.JPanel();
        khachhang = new javax.swing.JPanel();
        thongke = new javax.swing.JPanel();
        hoadon = new javax.swing.JPanel();
        baohanh = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        kGradientPanel1.setkGradientFocus(1000);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logomini.png"))); // NOI18N

        kGradientPanel3.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel3.setkGradientFocus(300);
        kGradientPanel3.setkStartColor(new java.awt.Color(204, 204, 0));

        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_users_30px.png"))); // NOI18N
        btnKhachHang.setText("Quản Lý Khách Hàng");
        btnKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnKhachHangMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel4.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel4.setkGradientFocus(300);
        kGradientPanel4.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnQLSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_product_30px_1.png"))); // NOI18N
        btnQLSanPham.setText("Quản Lý Sản Phẩm");
        btnQLSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLSanPhamMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel12.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel12.setkGradientFocus(300);
        kGradientPanel12.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnQLNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_add_user_group_man_man_30px.png"))); // NOI18N
        btnQLNhanVien.setText("Quản Lý Nhân Viên");
        btnQLNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLNhanVienMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel12Layout = new javax.swing.GroupLayout(kGradientPanel12);
        kGradientPanel12.setLayout(kGradientPanel12Layout);
        kGradientPanel12Layout.setHorizontalGroup(
            kGradientPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel12Layout.setVerticalGroup(
            kGradientPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel14.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel14.setkGradientFocus(300);
        kGradientPanel14.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLBanHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnQLBanHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_click_&_collect_30px.png"))); // NOI18N
        btnQLBanHang.setText("Quản Lý Bán Hàng");
        btnQLBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLBanHangMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel14Layout = new javax.swing.GroupLayout(kGradientPanel14);
        kGradientPanel14.setLayout(kGradientPanel14Layout);
        kGradientPanel14Layout.setHorizontalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel14Layout.setVerticalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel15.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel15.setkGradientFocus(300);
        kGradientPanel15.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnQLHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_purchase_order_30px.png"))); // NOI18N
        btnQLHoaDon.setText("Quản Lý Hóa Đơn");
        btnQLHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLHoaDonMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel15Layout = new javax.swing.GroupLayout(kGradientPanel15);
        kGradientPanel15.setLayout(kGradientPanel15Layout);
        kGradientPanel15Layout.setHorizontalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel15Layout.setVerticalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel16.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel16.setkGradientFocus(300);
        kGradientPanel16.setkStartColor(new java.awt.Color(204, 204, 0));

        btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_analytics_30px.png"))); // NOI18N
        btnThongKe.setText("Thống Kê Doanh Thu");
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel16Layout = new javax.swing.GroupLayout(kGradientPanel16);
        kGradientPanel16.setLayout(kGradientPanel16Layout);
        kGradientPanel16Layout.setHorizontalGroup(
            kGradientPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel16Layout.setVerticalGroup(
            kGradientPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel17.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel17.setkGradientFocus(300);
        kGradientPanel17.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnQLKhuyenMai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_gift_card_30px_1.png"))); // NOI18N
        btnQLKhuyenMai.setText("Quản Lý Khuyến Mãi");
        btnQLKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLKhuyenMaiMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel17Layout = new javax.swing.GroupLayout(kGradientPanel17);
        kGradientPanel17.setLayout(kGradientPanel17Layout);
        kGradientPanel17Layout.setHorizontalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel17Layout.setVerticalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel18.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel18.setkGradientFocus(300);
        kGradientPanel18.setkStartColor(new java.awt.Color(204, 204, 0));

        btnQLBaoHanh.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnQLBaoHanh.setForeground(new java.awt.Color(255, 255, 255));
        btnQLBaoHanh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQLBaoHanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_refresh_barcode_30px.png"))); // NOI18N
        btnQLBaoHanh.setText("Quản Lý Bảo Hành");
        btnQLBaoHanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQLBaoHanhMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel18Layout = new javax.swing.GroupLayout(kGradientPanel18);
        kGradientPanel18.setLayout(kGradientPanel18Layout);
        kGradientPanel18Layout.setHorizontalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLBaoHanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel18Layout.setVerticalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLBaoHanh, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel19.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel19.setkGradientFocus(300);
        kGradientPanel19.setkStartColor(new java.awt.Color(204, 204, 0));

        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_available_updates_30px.png"))); // NOI18N
        btnDoiMatKhau.setText("Đổi Mật Khẩu");
        btnDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDoiMatKhauMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel19Layout = new javax.swing.GroupLayout(kGradientPanel19);
        kGradientPanel19.setLayout(kGradientPanel19Layout);
        kGradientPanel19Layout.setHorizontalGroup(
            kGradientPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel19Layout.setVerticalGroup(
            kGradientPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        kGradientPanel20.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel20.setkGradientFocus(300);
        kGradientPanel20.setkStartColor(new java.awt.Color(204, 204, 0));

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_shutdown_30px.png"))); // NOI18N
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel20Layout = new javax.swing.GroupLayout(kGradientPanel20);
        kGradientPanel20.setLayout(kGradientPanel20Layout);
        kGradientPanel20Layout.setHorizontalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel20Layout.setVerticalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        lbChao.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lbChao.setForeground(new java.awt.Color(255, 255, 255));
        lbChao.setText("jLabel12");

        lbChucVu.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lbChucVu.setForeground(new java.awt.Color(255, 255, 255));
        lbChucVu.setText("jLabel12");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(lbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 88, Short.MAX_VALUE))
                    .addComponent(lbChao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbChao, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kGradientPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        giaodien.setBackground(new java.awt.Color(255, 255, 255));
        giaodien.setLayout(new java.awt.CardLayout());

        main.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 32)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Hệ Thống Quản Lý Bán Điện Thoại");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_windows_client_100px.png"))); // NOI18N

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGap(392, 392, 392)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(302, Short.MAX_VALUE))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                .addContainerGap(346, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );

        giaodien.add(main, "main");

        sanpham.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(sanpham, "sanpham");
        sanpham.getAccessibleContext().setAccessibleName("");

        doimatkhau.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout doimatkhauLayout = new javax.swing.GroupLayout(doimatkhau);
        doimatkhau.setLayout(doimatkhauLayout);
        doimatkhauLayout.setHorizontalGroup(
            doimatkhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        doimatkhauLayout.setVerticalGroup(
            doimatkhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(doimatkhau, "doimatkhau");

        nhanvien.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout nhanvienLayout = new javax.swing.GroupLayout(nhanvien);
        nhanvien.setLayout(nhanvienLayout);
        nhanvienLayout.setHorizontalGroup(
            nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        nhanvienLayout.setVerticalGroup(
            nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(nhanvien, "nhanvien");

        banhang.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout banhangLayout = new javax.swing.GroupLayout(banhang);
        banhang.setLayout(banhangLayout);
        banhangLayout.setHorizontalGroup(
            banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        banhangLayout.setVerticalGroup(
            banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(banhang, "banhang");

        khuyenmai.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout khuyenmaiLayout = new javax.swing.GroupLayout(khuyenmai);
        khuyenmai.setLayout(khuyenmaiLayout);
        khuyenmaiLayout.setHorizontalGroup(
            khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        khuyenmaiLayout.setVerticalGroup(
            khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(khuyenmai, "khuyenmai");

        khachhang.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout khachhangLayout = new javax.swing.GroupLayout(khachhang);
        khachhang.setLayout(khachhangLayout);
        khachhangLayout.setHorizontalGroup(
            khachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        khachhangLayout.setVerticalGroup(
            khachhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(khachhang, "khachhang");

        thongke.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(thongke, "thongke");

        hoadon.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout hoadonLayout = new javax.swing.GroupLayout(hoadon);
        hoadon.setLayout(hoadonLayout);
        hoadonLayout.setHorizontalGroup(
            hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        hoadonLayout.setVerticalGroup(
            hoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(hoadon, "hoadon");

        baohanh.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout baohanhLayout = new javax.swing.GroupLayout(baohanh);
        baohanh.setLayout(baohanhLayout);
        baohanhLayout.setHorizontalGroup(
            baohanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
        );
        baohanhLayout.setVerticalGroup(
            baohanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );

        giaodien.add(baohanh, "baohanh");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(giaodien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(giaodien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        giaodien.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseClicked
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Đăng xuất thành công !");
        new LoginForm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDangXuatMouseClicked

    private void btnQLSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLSanPhamMousePressed
        // TODO add your handling code here:
//       new QLChiTietSanPham().setVisible(true);
//       cardLayout.show(giaodien, "sanpham");

        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
        if (btnQLSanPham.isEnabled() == false) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sử dụng chức năng này !");
            return;
        }
    }//GEN-LAST:event_btnQLSanPhamMousePressed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel1MousePressed

    private void btnQLNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLNhanVienMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
        if (btnQLNhanVien.isEnabled() == false) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sử dụng chức năng này !");
            return;
        }
    }//GEN-LAST:event_btnQLNhanVienMousePressed

    private void btnDoiMatKhauMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMatKhauMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
    }//GEN-LAST:event_btnDoiMatKhauMousePressed

    private void btnKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhachHangMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
    }//GEN-LAST:event_btnKhachHangMousePressed

    private void btnQLHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLHoaDonMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
    }//GEN-LAST:event_btnQLHoaDonMousePressed

    private void btnThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
         QLBaoHanhPanel.windowClosed();
        if (btnThongKe.isEnabled() == false) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sử dụng chức năng này !");
            return;
        }
    }//GEN-LAST:event_btnThongKeMousePressed

    private void btnQLKhuyenMaiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLKhuyenMaiMousePressed
        // TODO add your handling code here:
        QLBanHangPanel.windowClosed();
        QLBaoHanhPanel.windowClosed();
        if (btnQLKhuyenMai.isEnabled() == false) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền sử dụng chức năng này !");
            return;
        }
    }//GEN-LAST:event_btnQLKhuyenMaiMousePressed

    private void btnQLBaoHanhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLBaoHanhMousePressed
        // TODO add your handling code here:
//         QLBaoHanhPanel.windowClosed();
        QLBanHangPanel.windowClosed();
        QLBaoHanhPanel.windowClosed();
    }//GEN-LAST:event_btnQLBaoHanhMousePressed

    private void btnQLBanHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLBanHangMousePressed
        // TODO add your handling code here:
        QLBaoHanhPanel.windowClosed();
        QLBanHangPanel.windowClosed();
//         


    }//GEN-LAST:event_btnQLBanHangMousePressed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel banhang;
    private javax.swing.JPanel baohanh;
    private javax.swing.JLabel btnDangXuat;
    private javax.swing.JLabel btnDoiMatKhau;
    private javax.swing.JLabel btnKhachHang;
    private javax.swing.JLabel btnQLBanHang;
    private javax.swing.JLabel btnQLBaoHanh;
    private javax.swing.JLabel btnQLHoaDon;
    private javax.swing.JLabel btnQLKhuyenMai;
    private javax.swing.JLabel btnQLNhanVien;
    private javax.swing.JLabel btnQLSanPham;
    private javax.swing.JLabel btnThongKe;
    private javax.swing.JPanel doimatkhau;
    private javax.swing.JPanel giaodien;
    private javax.swing.JPanel hoadon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel12;
    private keeptoo.KGradientPanel kGradientPanel14;
    private keeptoo.KGradientPanel kGradientPanel15;
    private keeptoo.KGradientPanel kGradientPanel16;
    private keeptoo.KGradientPanel kGradientPanel17;
    private keeptoo.KGradientPanel kGradientPanel18;
    private keeptoo.KGradientPanel kGradientPanel19;
    private keeptoo.KGradientPanel kGradientPanel20;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private javax.swing.JPanel khachhang;
    private javax.swing.JPanel khuyenmai;
    private javax.swing.JLabel lbChao;
    private javax.swing.JLabel lbChucVu;
    private javax.swing.JPanel main;
    private javax.swing.JPanel nhanvien;
    private javax.swing.JPanel sanpham;
    private javax.swing.JPanel thongke;
    // End of variables declaration//GEN-END:variables
}
