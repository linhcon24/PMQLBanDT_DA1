/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import domainmodels.BoNhoTrong;
import domainmodels.ChiTietSP;
import domainmodels.MauSac;
import domainmodels.SanPham;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
import services.IChiTietSPServices;
import services.IQLBoNhoTrongServices;
import services.IQLMauSacServices;
import services.IQLSanPhamServices;
import services.ISanPhamServices;
import services.ImeiServices;
import services.QLBoNhoTrongServices;
import services.QLMauSacServices;
import services.QLSanPhamServices;
import viewmodels.ChiTietSPViewModels;

/**
 *
 * @author HANGOCHAN
 */
public class QLChiTietSanPham extends javax.swing.JFrame {

    /**
     * Creates new form QLChiTietSanPham
     */
    private IQLSanPhamServices sanPhamServices;
    private IQLMauSacServices mauSacServices;
    private IQLBoNhoTrongServices boNhoTrongServices;
    private IChiTietSPServices chiTietSPServices;

    public QLChiTietSanPham() {
        initComponents();

        sanPhamServices = new QLSanPhamServices();
        mauSacServices = new QLMauSacServices();
        boNhoTrongServices = new QLBoNhoTrongServices();
        chiTietSPServices = new ChiTietSPServices();

        cbbMauSac.removeAllItems();
        cbbBoNhoTrong.removeAllItems();
        cbbImei.removeAllItems();

        List<MauSac> mauSacs = mauSacServices.getALL();
        for (MauSac mauSac : mauSacs) {
            if (mauSac.getTrangThai() == 0) {
                cbbMauSac.addItem(mauSac.getTenMauSac());
            }
        }

        List<BoNhoTrong> bnts = boNhoTrongServices.getALL();
        for (BoNhoTrong bnt : bnts) {
            if (bnt.getTrangThai() == 0) {
                cbbBoNhoTrong.addItem(String.valueOf(bnt.getDungLuong()));
            }
        }
        cbbSanPham.removeAllItems();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        for (SanPham sanPham : sanPhams) {
            if (sanPham.getTrangThai() == 0) {
                cbbSanPham.addItem(sanPham.getTenSP());
            }
        }
        hienThiSanPham();
    }

    public void hienThiSanPham() {
        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        String maSP = "";
        List<SanPham> sanPhams = sanPhamServices.getALL();
        for (SanPham sanPham : sanPhams) {
            maSP = sanPham.getMaSP();
            ChiTietSPViewModels c = chiTietSPServices.load(maSP);
            if (c == null) {
                continue;
            }
            if (c.getTrangThai() == 0) {
                JLabel label = new JLabel();
                ImageIcon icon = new ImageIcon(c.getAnh());
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                Object[] data = new Object[]{
                    c.getTenSP(),
                    c.getNsx(),
                    c.getMauSac(),
                    c.getBoNho(),
                    c.getTonKho(),
                    c.getGiaNhap(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);
            }

        }
        if (cbbSanPham.getItemCount() == 0) {

        } else {
            cbbSanPham.setSelectedIndex(0);
        }
        if (cbbBoNhoTrong.getItemCount() == 0) {

        } else {
            cbbBoNhoTrong.setSelectedIndex(0);
        }
        if (cbbMauSac.getItemCount() == 0) {

        } else {
            cbbMauSac.setSelectedIndex(0);
        }
    }

    public void loadHienThiSanPham(List<SanPham> list, ChiTietSPViewModels c) {
        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        String maSP = "";
        list = sanPhamServices.getALL();
        for (SanPham sanPham : list) {
            maSP = sanPham.getMaSP();
            c = chiTietSPServices.load(maSP);
            if (c == null) {
                continue;
            }
            if (c.getTrangThai() == 0) {
                JLabel label = new JLabel();
                ImageIcon icon = new ImageIcon(c.getAnh());
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                Object[] data = new Object[]{
                    c.getTenSP(),
                    c.getNsx(),
                    c.getMauSac(),
                    c.getBoNho(),
                    c.getTonKho(),
                    c.getGiaNhap(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);
            }

        }
        if (cbbSanPham.getItemCount() == 0) {

        } else {
            cbbSanPham.setSelectedIndex(0);
        }
    }

    class myTableCellRender implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            tbSanPham.setRowHeight(70);

            return (Component) value;
        }
    }

    public static void cbbSanPham(List<SanPham> items) {
        cbbSanPham.removeAllItems();

        for (SanPham sanPham : items) {
            if (sanPham.getTrangThai() == 0) {
                cbbSanPham.addItem(sanPham.getTenSP());
            }
        }

    }

    public static void cbbBoNhoTrong(List<BoNhoTrong> items) {
        cbbBoNhoTrong.removeAllItems();

        for (BoNhoTrong boNhoTrong : items) {
            if (boNhoTrong.getTrangThai() == 0) {
                cbbBoNhoTrong.addItem(String.valueOf(boNhoTrong.getDungLuong()));
            }
        }

    }

    public static void cbbMauSac(List<MauSac> items) {
        cbbMauSac.removeAllItems();

        for (MauSac m : items) {
            if (m.getTrangThai() == 0) {
                cbbMauSac.addItem(m.getTenMauSac());
            }
        }

    }

    public static void loadHienThiSanPham(Object[] data) {

        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        model.addRow(data);
    }

    public static void cbbImei(List<String> items) {
        cbbImei.removeAllItems();
        for (String item : items) {
            cbbImei.addItem(item);
        }
    }
//    public static void cbbBoNho(List<String> items){
//        cbbBoNhoTrong.removeAllItems();
//        for (String item : items) {
//            cbbBoNhoTrong.addItem(item);
//        }
//    }
//    public static void cbbMauSac(List<String> items){
//        for (String item : items) {
//            cbbMauSac.addItem(item);
//        }
//    }

    public static void txtSoLuong(String sl) {
        txtSoLuongTon.setText(sl);
    }

    public void fillSanPham() {
         AnhService anhService = new AnhService();
        anhService.setAnh(null);
        int index = tbSanPham.getSelectedRow();
        String tenSP = tbSanPham.getValueAt(index, 0).toString();
        String soLuong = tbSanPham.getValueAt(index, 4).toString();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        String maSP = "";
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                maSP = sanPham.getMaSP();
            }
        }
        cbbImei.removeAllItems();
        List<ChiTietSP> imei = chiTietSPServices.getImeibyMaSP(maSP);
        for (ChiTietSP chiTietSP : imei) {
            if (chiTietSP.getTrangThai() == 0) {
                cbbImei.addItem(chiTietSP.getMaImei());
            }
        }
        ChiTietSP c = chiTietSPServices.fill(maSP);
        if (c.getAnh() == null) {
            lbAnh.setIcon(null);
        } else {
            ImageIcon icon = new ImageIcon(c.getAnh());
            Image image = icon.getImage().getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon m = new ImageIcon(image);
            lbAnh.setIcon(m);
        }
        anhService.setAnh(c.getAnh());
       
        cbbSanPham.setSelectedItem(tenSP);
        txtGiaNhap.setText(String.valueOf(c.getGiaNhap()));
        txtGiaBan.setText(String.valueOf(c.getGiaBan()));
        txtGhiChu.setText(c.getMoTa());
        txtSoLuongTon.setText(soLuong);
       
        btnAdd.setEnabled(false);
        String ngayBh = "";
        if (c.getThoiGianBH() == 0) {
            ngayBh = "0";
        }
        if (c.getThoiGianBH() == 30) {
            ngayBh = "1 Tháng";
        }
        if (c.getThoiGianBH() == 90) {
            ngayBh = "3 Tháng";
        }
        if (c.getThoiGianBH() == 180) {
            ngayBh = "6 Tháng";
        }
        if (c.getThoiGianBH() == 360) {
            ngayBh = "12 Tháng";
        }
        cbbTGBH.setSelectedItem(ngayBh);
    }

    public ChiTietSP layTTSanPham() throws ParseException {
        String Imei = "";
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            Imei = cbbImei.getItemAt(i);
        }
        double giaNhapdb = Double.parseDouble(txtGiaNhap.getText());
        BigDecimal giaNhap = BigDecimal.valueOf(giaNhapdb);
        double giaBandb = Double.parseDouble(txtGiaBan.getText());
        BigDecimal giaBan = BigDecimal.valueOf(giaBandb);
        String moTa = txtGhiChu.getText();
        Integer thoiGianBaoHanh = 0;
        String thoiGian = (String) cbbTGBH.getSelectedItem();
        if (thoiGian.equals("1 Tháng")) {
            thoiGianBaoHanh = 30;
        }if (thoiGian.equals("3 Tháng")) {
            thoiGianBaoHanh = 90;
        }if(thoiGian.equals("6 tháng")) {
            thoiGianBaoHanh = 180;
        }if (thoiGian.equals("12 tháng")) {
            thoiGianBaoHanh = 360 ;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();
        String camera = (String) cbbCamera.getSelectedItem();
        String heDieuHanh = (String) cbbHeDieuHanh.getSelectedItem();
        String ramString = (String) cbbRam.getSelectedItem();
        Integer ram = Integer.parseInt(ramString);
        String cpu = txtCpu.getText();
        String manHinh = txtManHinh.getText();
        Integer pin = Integer.parseInt(txtPin.getText());
        String xuatXu = txtXuatXu.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPham.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
            }
        }
        MauSac mauSac = null;
        String tenMauSac = (String) cbbMauSac.getSelectedItem();
        List<MauSac> mauSacs = mauSacServices.getALL();
        for (MauSac mauSac1 : mauSacs) {
            if (tenMauSac.equals(mauSac1.getTenMauSac())) {
                mauSac = mauSac1;
            }
        }
        BoNhoTrong bnt = null;
        String boNho = (String) cbbBoNhoTrong.getSelectedItem();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        for (BoNhoTrong boNhoTrong : boNhoTrongs) {
            if (Integer.parseInt(boNho) == boNhoTrong.getDungLuong()) {
                bnt = boNhoTrong;
            }
        }

        ChiTietSP c = new ChiTietSP();
        c.setMaImei(Imei);
        c.setGiaNhap(giaNhap);
        c.setGiaBan(giaBan);
        c.setMoTa(moTa);
        c.setThoiGianBH(thoiGianBaoHanh);
        c.setAnh(anh);
       
        c.setNgayTao(date);
        c.setTrangThai(0);
        c.setSanPham(sp);
        return c;

    }

    public ChiTietSP layTTSuaSanPham() throws ParseException {
        String Imei = "";
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            Imei = cbbImei.getItemAt(i);
        }
        double giaNhapdb = Double.parseDouble(txtGiaNhap.getText());
        BigDecimal giaNhap = BigDecimal.valueOf(giaNhapdb);
        double giaBandb = Double.parseDouble(txtGiaBan.getText());
        BigDecimal giaBan = BigDecimal.valueOf(giaBandb);
        String moTa = txtGhiChu.getText();
        Integer thoiGianBaoHanh = 0;
        String thoiGian = (String) cbbTGBH.getSelectedItem();
        if (thoiGian.equals("0")) {
            thoiGianBaoHanh = 0 ;
        }
        else if (thoiGian.equals("1 Tháng")) {
            thoiGianBaoHanh = 30;
        } else if (thoiGian.equals("3 Tháng")) {
            thoiGianBaoHanh = 90;
        } else if (thoiGian.equals("6 Tháng")) {
            thoiGianBaoHanh = 180;
        } else {
            thoiGianBaoHanh = 360;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();
        String camera = (String) cbbCamera.getSelectedItem();
        String heDieuHanh = (String) cbbHeDieuHanh.getSelectedItem();
        String ramString = (String) cbbRam.getSelectedItem();
        Integer ram = Integer.parseInt(ramString);
        String cpu = txtCpu.getText();
        String manHinh = txtManHinh.getText();
        Integer pin = Integer.parseInt(txtPin.getText());
        String xuatXu = txtXuatXu.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPham.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        String masp = "";
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
                masp = sanPham.getMaSP();
            }
        }
        MauSac mauSac = null;
        String tenMauSac = (String) cbbMauSac.getSelectedItem();
        List<MauSac> mauSacs = mauSacServices.getALL();
        for (MauSac mauSac1 : mauSacs) {
            if (tenMauSac.equals(mauSac1.getTenMauSac())) {
                mauSac = mauSac1;
            }
        }
        BoNhoTrong bnt = null;
        String boNho = (String) cbbBoNhoTrong.getSelectedItem();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        for (BoNhoTrong boNhoTrong : boNhoTrongs) {
            if (Integer.parseInt(boNho) == boNhoTrong.getDungLuong()) {
                bnt = boNhoTrong;
            }
        }
        ChiTietSP c = new ChiTietSP();
        c.setMaImei(Imei);
        c.setGiaNhap(giaNhap);
        c.setGiaBan(giaBan);
        c.setMoTa(moTa);
        c.setThoiGianBH(thoiGianBaoHanh);
        ChiTietSP spp = chiTietSPServices.fill(masp);
        if (spp.getAnh() == null) {
            c.setAnh(anh);
        } else {
            c.setAnh(spp.getAnh());
        }
      
        c.setNgaySua(date);
        c.setTrangThai(0);
        c.setSanPham(sp);
        return c;

    }

    public ChiTietSP layTTSuaChiTietSanPham() throws ParseException {
        String Imei = "";
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            Imei = cbbImei.getItemAt(i);
        }
        double giaNhapdb = Double.parseDouble(txtGiaNhap.getText());
        BigDecimal giaNhap = BigDecimal.valueOf(giaNhapdb);
        double giaBandb = Double.parseDouble(txtGiaBan.getText());
        BigDecimal giaBan = BigDecimal.valueOf(giaBandb);
        String moTa = txtGhiChu.getText();
        Integer thoiGianBaoHanh = 0;
        String thoiGian = (String) cbbTGBH.getSelectedItem();
        if (thoiGian.equals("0")) {
            thoiGianBaoHanh = 0 ;
        }
        else if (thoiGian.equals("1 Tháng")) {
            thoiGianBaoHanh = 30;
        } else if (thoiGian.equals("3 Tháng")) {
            thoiGianBaoHanh = 90;
        } else if (thoiGian.equals("6 Tháng")) {
            thoiGianBaoHanh = 180;
        } else {
            thoiGianBaoHanh = 360;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();
        String camera = (String) cbbCamera.getSelectedItem();
        String heDieuHanh = (String) cbbHeDieuHanh.getSelectedItem();
        String ramString = (String) cbbRam.getSelectedItem();
        Integer ram = Integer.parseInt(ramString);
        String cpu = txtCpu.getText();
        String manHinh = txtManHinh.getText();
        Integer pin = Integer.parseInt(txtPin.getText());
        String xuatXu = txtXuatXu.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPham.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        String masp = "";
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
                masp = sanPham.getMaSP();
            }
        }
        MauSac mauSac = null;
        String tenMauSac = (String) cbbMauSac.getSelectedItem();
        List<MauSac> mauSacs = mauSacServices.getALL();
        for (MauSac mauSac1 : mauSacs) {
            if (tenMauSac.equals(mauSac1.getTenMauSac())) {
                mauSac = mauSac1;
            }
        }
        BoNhoTrong bnt = null;
        String boNho = (String) cbbBoNhoTrong.getSelectedItem();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        for (BoNhoTrong boNhoTrong : boNhoTrongs) {
            if (Integer.parseInt(boNho) == boNhoTrong.getDungLuong()) {
                bnt = boNhoTrong;
            }
        }
        ChiTietSP c = new ChiTietSP();
        c.setMaImei(Imei);
        c.setGiaNhap(giaNhap);
        c.setGiaBan(giaBan);
        c.setMoTa(moTa);
        c.setThoiGianBH(thoiGianBaoHanh);
        ChiTietSP spp = chiTietSPServices.fill(masp);
        if (spp.getAnh() == null) {
            c.setAnh(anh);
        } else {
            c.setAnh(spp.getAnh());
        }
      
        c.setNgaySua(date);
        c.setTrangThai(1);
        c.setSanPham(sp);
        
        return c;

    }

    public void clear() {
        lbAnh.setIcon(null);
        cbbSanPham.setSelectedIndex(0);
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        cbbImei.removeAllItems();
        txtSoLuongTon.setText("0");
        txtGhiChu.setText("");
        txtXuatXu.setText("");
        txtCpu.setText("");
        txtManHinh.setText("");
        txtPin.setText("");
        cbbRam.setSelectedIndex(0);
        cbbBoNhoTrong.setSelectedIndex(0);
        cbbHeDieuHanh.setSelectedIndex(0);
        cbbMauSac.setSelectedIndex(0);
        cbbCamera.setSelectedIndex(0);
        cbbTGBH.setSelectedIndex(0);
        hienThiSanPham();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sanpham = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        cbbSanPham = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbbImei = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtSoLuongTon = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        cbbBoNhoTrong = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cbbTGBH = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cbbCamera = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cbbHeDieuHanh = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cbbRam = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        txtCpu = new javax.swing.JTextField();
        txtManHinh = new javax.swing.JTextField();
        txtPin = new javax.swing.JTextField();
        btnThem = new keeptoo.KGradientPanel();
        btnAdd = new javax.swing.JLabel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        btnReset = new javax.swing.JLabel();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        btnDelete = new javax.swing.JLabel();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        btnUpdate = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        btnUpload = new javax.swing.JLabel();
        btnSanPham = new javax.swing.JLabel();
        btnImei = new javax.swing.JLabel();
        btnBoNho = new javax.swing.JLabel();
        btnMauSac = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sanpham.setBackground(new java.awt.Color(255, 255, 255));

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "NSX", "Màu Sắc", "Bộ Nhớ", "Tồn kho", "Giá nhập", "Giá bán", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Sản phẩm ");

        cbbSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSanPhamActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Giá nhập");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Giá bán");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("List Imei");

        cbbImei.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbImeiActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Số lượng tồn");

        txtSoLuongTon.setText("0");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Ghi Chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Bộ Nhớ");

        cbbBoNhoTrong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Màu Sắc");

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauSacActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Thời gian bảo hành");

        cbbTGBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1 Tháng", "3 Tháng", "6 Tháng", "12 Tháng" }));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Camera");

        cbbCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "32MP", "48MP", "64MP" }));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Hệ điều hành");

        cbbHeDieuHanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IOS", "ANDROID" }));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Ram");

        cbbRam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "6", "8", "12" }));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Cpu");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Màn hình");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Pin");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Xuất xứ");

        btnThem.setkGradientFocus(150);

        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_add_30px_6.png"))); // NOI18N
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddMousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnThemLayout = new javax.swing.GroupLayout(btnThem);
        btnThem.setLayout(btnThemLayout);
        btnThemLayout.setHorizontalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );
        btnThemLayout.setVerticalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        kGradientPanel5.setkGradientFocus(150);

        btnReset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_available_updates_30px.png"))); // NOI18N
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnResetMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        kGradientPanel6.setkGradientFocus(150);

        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_waste_30px.png"))); // NOI18N
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDeleteMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel6Layout = new javax.swing.GroupLayout(kGradientPanel6);
        kGradientPanel6.setLayout(kGradientPanel6Layout);
        kGradientPanel6Layout.setHorizontalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel6Layout.setVerticalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        kGradientPanel7.setkGradientFocus(150);

        btnUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_update_30px.png"))); // NOI18N
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUpdateMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel7Layout = new javax.swing.GroupLayout(kGradientPanel7);
        kGradientPanel7.setLayout(kGradientPanel7Layout);
        kGradientPanel7Layout.setHorizontalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel7Layout.setVerticalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        lbAnh.setText("Image");
        lbAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_vintage_camera_30px.png"))); // NOI18N
        btnUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUploadMousePressed(evt);
            }
        });

        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        btnSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSanPhamMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSanPhamMousePressed(evt);
            }
        });

        btnImei.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        btnImei.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnImeiMousePressed(evt);
            }
        });

        btnBoNho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        btnBoNho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBoNhoMousePressed(evt);
            }
        });

        btnMauSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        btnMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnMauSacMousePressed(evt);
            }
        });

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(cbbCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24)
                    .addComponent(jLabel21)
                    .addComponent(cbbTGBH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addComponent(cbbBoNhoTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBoNho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMauSac)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addComponent(cbbImei, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImei))))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sanphamLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(14, 14, 14)))
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtGiaNhap)
                                    .addComponent(cbbSanPham, 0, 222, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSanPham)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel18))
                        .addGap(23, 23, 23)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtManHinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCpu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtXuatXu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(sanphamLayout.createSequentialGroup()
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(sanphamLayout.createSequentialGroup()
                                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbbSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(btnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbbImei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnImei, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, 0)
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(sanphamLayout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(sanphamLayout.createSequentialGroup()
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbBoNhoTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(btnBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 55, Short.MAX_VALUE)
                        .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGap(650, 650, 650)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(kGradientPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(kGradientPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14))
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbHeDieuHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTGBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1248, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMousePressed
        // TODO add your handling code here:
        fillSanPham();
    }//GEN-LAST:event_tbSanPhamMousePressed

    private void cbbImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbImeiActionPerformed

    private void cbbMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMauSacActionPerformed

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        try {
            // TODO add your handling code here:
            String checkSo = "^[0-9]*$";
            if (btnAdd.isEnabled() == false) {
                JOptionPane.showMessageDialog(this, "Không thể thêm vì sản phẩm đã tồn tại !");
                return;
            }
            if (txtGiaNhap.getText().trim().isEmpty() || txtGiaBan.getText().trim().isEmpty() || txtGhiChu.getText().trim().isEmpty() || txtCpu.getText().trim().isEmpty() || txtXuatXu.getText().trim().isEmpty() || txtManHinh.getText().trim().isEmpty() || txtManHinh.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống !");
                return;
            }
            if (lbAnh.getIcon() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng tải ảnh sản phẩm lên !");
                return;
            }
            String giaNhapString = txtGiaNhap.getText().trim();
            String giaBanString = txtGiaBan.getText().trim();
            String ghiChu = txtGhiChu.getText().trim();
            String xuatXu = txtXuatXu.getText().trim();
            String cpu = txtCpu.getText().trim();
            String manHinh = txtManHinh.getText().trim();
            String pin = txtPin.getText().trim();
            if (!giaBanString.matches(checkSo) || !giaNhapString.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán là số !");
                return;
            }
            if (giaNhapString.length() > 10 || giaBanString.length() > 10) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán nhỏ hơn 9999999999 !");
                return;
            }

            Double giaNhap = Double.parseDouble(txtGiaNhap.getText());
            Double giaBan = Double.parseDouble(txtGiaBan.getText());
            if (giaNhap > giaBan) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập nhỏ hơn hoặc bằng giá bán !");
                return;
            }
            if (giaNhap <= 0 || giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán lớn hơn 0 !");
                return;
            }
            if (ghiChu.length() > 255) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ghi chú nhỏ hơn 255 kí tự !");
                return;
            }
            if (xuatXu.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ nhỏ hơn 100 kí tự !");
                return;
            }
            if (xuatXu.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ là chữ !");
                return;
            }
            if (cpu.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập cpu nhỏ hơn 50 kí tự !");
                return;
            }
            if (manHinh.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập màn hình nhỏ hơn 50 kí tự !");
                return;
            }
            if (!pin.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin là số !");
                return;
            }
            Integer pinS = Integer.parseInt(pin);
            if (pinS <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin lớn hơn 0 !");
                return;
            }
            if (pinS >= 10000) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin nhỏ hơn 9999 !");
                return;
            }
            if (cbbImei.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Imei rỗng !");
                return;
            }
            if (cbbSanPham.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm nào !");
                return;
            }
            if (cbbBoNhoTrong.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có Bộ nhớ trong nào !");
                return;
            }
            if (cbbBoNhoTrong.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có màu sắc nào !");
                return;
            }
            ChiTietSP c = layTTSanPham();
            List<ChiTietSP> list = chiTietSPServices.getImei();
            int row = cbbImei.getItemCount();
            for (ChiTietSP chiTietSP : list) {
                for (int j = 0; j < row; j++) {
                    if (chiTietSP.getMaImei().equals(cbbImei.getItemAt(j))) {
                        JOptionPane.showMessageDialog(this, "List Imei chứa Imei đã tồn tại trên hệ thống !");
                        return;
                    }
                }
            }

            String Imei = "";
            for (int i = 0; i < cbbImei.getItemCount(); i++) {
                Imei = cbbImei.getItemAt(i);

                c.setMaImei(Imei);
                chiTietSPServices.add(c);
            }

            JOptionPane.showMessageDialog(this, "Thêm thành công !");
            hienThiSanPham();
            clear();

        } catch (ParseException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddMousePressed

    private void btnResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMousePressed
        clear();
    }//GEN-LAST:event_btnResetMousePressed

    private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMousePressed
        String Imei = "";
        int index = tbSanPham.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
            return;
        }
        int e = tbSanPham.getSelectedRowCount();
        if (e > 1) {
            JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            try {

                Imei = cbbImei.getItemAt(i);
                ChiTietSP c = layTTSanPham();
                c.setMaImei(Imei);
                chiTietSPServices.delete(c);
            } catch (ParseException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(this, "Xóa thành công !");
        hienThiSanPham();
        clear();
    }//GEN-LAST:event_btnDeleteMousePressed

    private void btnUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMousePressed
        // TODO add your handling code here:
        String checkSo = "^[0-9]*$";
        try {
            int index = tbSanPham.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
                return;
            }
            int e = tbSanPham.getSelectedRowCount();
            if (e > 1) {
                JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
                return;
            }
            if (txtGiaNhap.getText().trim().isEmpty() || txtGiaBan.getText().trim().isEmpty() || txtGhiChu.getText().trim().isEmpty() || txtCpu.getText().trim().isEmpty() || txtXuatXu.getText().trim().isEmpty() || txtManHinh.getText().trim().isEmpty() || txtManHinh.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống !");
                return;
            }
            if (lbAnh.getIcon() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng tải ảnh sản phẩm lên !");
                return;
            }
            String giaNhapString = txtGiaNhap.getText().trim();
            String giaBanString = txtGiaBan.getText().trim();
            String ghiChu = txtGhiChu.getText().trim();
            String xuatXu = txtXuatXu.getText().trim();
            String cpu = txtCpu.getText().trim();
            String manHinh = txtManHinh.getText().trim();
            String pin = txtPin.getText().trim();
            if (!giaBanString.matches(checkSo) || !giaNhapString.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán là số !");
                return;
            }
            if (giaNhapString.length() > 10 || giaBanString.length() > 10) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán nhỏ hơn 9999999999 !");
                return;
            }

            Double giaNhap = Double.parseDouble(txtGiaNhap.getText());
            Double giaBan = Double.parseDouble(txtGiaBan.getText());
            if (giaNhap > giaBan) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập nhỏ hơn hoặc bằng giá bán !");
                return;
            }
            if (giaNhap <= 0 || giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá nhập hoặc giá bán lớn hơn 0 !");
                return;
            }
            if (ghiChu.length() > 255) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ghi chú nhỏ hơn 255 kí tự !");
                return;
            }
            if (xuatXu.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ nhỏ hơn 100 kí tự !");
                return;
            }
            if (xuatXu.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ là chữ !");
                return;
            }
            if (cpu.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập cpu nhỏ hơn 50 kí tự !");
                return;
            }
            if (manHinh.length() > 50) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập màn hình nhỏ hơn 50 kí tự !");
                return;
            }
            if (!pin.matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin là số !");
                return;
            }
            Integer pinS = Integer.parseInt(pin);
            if (pinS <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin lớn hơn 0 !");
                return;
            }
            if (pinS >= 10000) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin nhỏ hơn 9999 !");
                return;
            }
            if (cbbImei.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Imei rỗng !");
                return;
            }
            if (cbbSanPham.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm nào !");
                return;
            }
            if (cbbBoNhoTrong.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có Bộ nhớ trong nào !");
                return;
            }
            if (cbbBoNhoTrong.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có màu sắc nào !");
                return;
            }
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa ?");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            // TODO add your handling code here:
//            String anh = tbSanPham.getValueAt(index, 7).toString();
            ChiTietSP c = layTTSuaSanPham();
            int row = cbbImei.getItemCount();
            String Imei = "";
            chiTietSPServices.xoaImei(c.getSanPham().getMaSP());
            for (int i = 0; i < row; i++) {
                Imei = cbbImei.getItemAt(i);

                ChiTietSP c1 = layTTSanPham();
//                c1.setAnh(c.getAnh());
                c1.setMaImei(Imei);
                chiTietSPServices.add(c1);
                ChiTietSP c3 = layTTSuaSanPham();
                c3.setMaImei(Imei);
                c3.setAnh(c1.getAnh());

                chiTietSPServices.update(c3);
            }

            JOptionPane.showMessageDialog(this, "Sửa thành công !");
            hienThiSanPham();
            clear();

        } catch (ParseException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateMousePressed

    private void btnUploadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadMousePressed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser("D:\\PRO1041");
        int check = chooser.showOpenDialog(null);
        if (check == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File s = chooser.getSelectedFile();
        String fileType = chooser.getTypeDescription(s);
        if (!fileType.equals("JPG File") && !fileType.equals("PNG File")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file JPG hoặc PNG !");
            return;
        }
        String fileName = s.getAbsolutePath();
        AnhService anhService = new AnhService();
        anhService.setAnh(fileName);
        Image getAbso = null;
        ImageIcon icon = new ImageIcon(fileName);
        Image image = icon.getImage().getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon m = new ImageIcon(image);
        lbAnh.setIcon(m);
    }//GEN-LAST:event_btnUploadMousePressed

    private void btnSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSanPhamMousePressed
        // TODO add your handling code here:
        new QLSanPham().setVisible(true);
    }//GEN-LAST:event_btnSanPhamMousePressed

    private void btnImeiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImeiMousePressed
        // TODO add your handling code here:

        ImeiServices services = new ImeiServices();
        List<String> list = new ArrayList();
        String imei = "";
        for (int i = 0; i < cbbImei.getItemCount(); i++) {
            imei = cbbImei.getItemAt(i);
            list.add(imei);
            services.setList(list);
        }
        new ImeiForm().setVisible(true);
    }//GEN-LAST:event_btnImeiMousePressed

    private void btnBoNhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBoNhoMousePressed
        // TODO add your handling code here:
        new QLBoNhoTrong().setVisible(true);
    }//GEN-LAST:event_btnBoNhoMousePressed

    private void btnAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddMouseExited

    private void btnSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSanPhamMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSanPhamMouseExited

    private void cbbSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSanPhamActionPerformed
        // TODO add your handling code here:
        int row = tbSanPham.getRowCount();
        if (row == 0) {
            return;
        }
        if (row == 1) {
            String cbbSanPham = (String) QLChiTietSanPham.cbbSanPham.getSelectedItem();
            String tenSP = tbSanPham.getValueAt(0, 0).toString();
            if (tenSP.equals(cbbSanPham)) {
                btnAdd.setEnabled(false);
            } else {
                btnAdd.setEnabled(true);
            }
            return;
        }
        String tenSP = "";
        String cbbSanPham = (String) QLChiTietSanPham.cbbSanPham.getSelectedItem();
        if (cbbSanPham == null) {
            return;
        }
        for (int i = 0; i < row; i++) {
            tenSP = tbSanPham.getValueAt(i, 0).toString();
            if (cbbSanPham.equals(tenSP)) {
                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                return;
            }
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
            btnAdd.setEnabled(true);

        }


    }//GEN-LAST:event_cbbSanPhamActionPerformed

    private void btnMauSacMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMauSacMousePressed
        // TODO add your handling code here:
        new QLMauSac().setVisible(true);
    }//GEN-LAST:event_btnMauSacMousePressed

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
            java.util.logging.Logger.getLogger(QLChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLChiTietSanPham().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnBoNho;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnImei;
    private javax.swing.JLabel btnMauSac;
    private javax.swing.JLabel btnReset;
    private javax.swing.JLabel btnSanPham;
    private keeptoo.KGradientPanel btnThem;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JLabel btnUpload;
    private static javax.swing.JComboBox<String> cbbBoNhoTrong;
    private javax.swing.JComboBox<String> cbbCamera;
    private javax.swing.JComboBox<String> cbbHeDieuHanh;
    private static javax.swing.JComboBox<String> cbbImei;
    private static javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbRam;
    private static javax.swing.JComboBox<String> cbbSanPham;
    private javax.swing.JComboBox<String> cbbTGBH;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JPanel sanpham;
    private static javax.swing.JTable tbSanPham;
    private javax.swing.JTextField txtCpu;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtManHinh;
    private javax.swing.JTextField txtPin;
    private static javax.swing.JLabel txtSoLuongTon;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
