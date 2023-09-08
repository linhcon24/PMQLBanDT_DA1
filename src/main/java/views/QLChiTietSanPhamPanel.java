/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainmodels.BoNhoTrong;
import domainmodels.ChiTietSP;
import domainmodels.DanhMuc;
import domainmodels.MauSac;
import domainmodels.NSX;
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
import pagination.EventPagination;
import services.AnhService;
import services.BarCodeServices;
import services.ChiTietSPServices;
import services.DanhMucServices;
import services.IChiTietSPServices;
import services.IDanhMucServices;
import services.IQLBoNhoTrongServices;
import services.IQLMauSacServices;
import services.IQLNSXServices;
import services.IQLSanPhamServices;
import services.ImeiServices;
import services.QLBoNhoTrongServices;
import services.QLMauSacServices;
import services.QLNSXServices;
import services.QLSanPhamServices;
import viewmodels.ChiTietSPViewModels;

/**
 *
 * @author HANGOCHAN
 */
public class QLChiTietSanPhamPanel extends javax.swing.JPanel {

    /**
     * Creates new form QLChiTietSanPhamPanel
     */
    private IQLSanPhamServices sanPhamServices;
    private IChiTietSPServices chiTietSPServices;
    private BarCodeServices barCodeServices;
    private IDanhMucServices danhMucServices;
    private IQLNSXServices qLNSXServices;
    private IQLMauSacServices qLMauSacServices;
    private IQLBoNhoTrongServices boNhoTrongServices;
    private Integer check = 0;

    public QLChiTietSanPhamPanel() {
        initComponents();

        sanPhamServices = new QLSanPhamServices();
        barCodeServices = new BarCodeServices();
        chiTietSPServices = new ChiTietSPServices();
        danhMucServices = new DanhMucServices();
        qLNSXServices = new QLNSXServices();
        qLMauSacServices = new QLMauSacServices();
        boNhoTrongServices = new QLBoNhoTrongServices();
        cbbImei.removeAllItems();
        cbbSanPhamm.removeAllItems();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        for (SanPham sanPham : sanPhams) {
            if (sanPham.getTrangThai() == 0) {
                cbbSanPhamm.addItem(sanPham.getTenSP());
            }
        }
        loadCbbLocDanhMuc();
        loadCbbLocMauSac();
        loadCbbLocBoNho();
        hienThiSanPham(1);

        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {

                if (check == 0) {
                    hienThiSanPham(page);
                } else {
                    LocSanPham(page);
                }
            }
        });
    }

    private void loadCbbLocDanhMuc() {
        cbbLocDanhMuc.removeAllItems();
        List<DanhMuc> danhMucs = danhMucServices.getALL();
        for (DanhMuc danhMuc : danhMucs) {
            if (danhMuc.getTrangThai() == 0) {
                cbbLocDanhMuc.addItem(danhMuc.getTenDanhMuc());
            }
        }

    }

    private void loadCbbLocMauSac() {
        cbbLocMauSac.removeAllItems();
        List<MauSac> mauSacs = qLMauSacServices.getALL();
        for (MauSac m : mauSacs) {
            if (m.getTrangThai() == 0) {
                cbbLocMauSac.addItem(m.getTenMauSac());
            }
        }

    }

    private void loadCbbLocBoNho() {
        cbbLocBoNho.removeAllItems();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        for (BoNhoTrong m : boNhoTrongs) {
            if (m.getTrangThai() == 0) {
                cbbLocBoNho.addItem(String.valueOf(m.getDungLuong()));
            }
        }

    }

    public void hienThiSanPham(Integer page) {
        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        Integer limit = 5;
        List<SanPham> sps = sanPhamServices.getAllbyTrangThai(0);
        Integer count = sps.size();
        Integer soDu = count % limit;
//        Integer soLamTron = 0;
//        if (soDu == 0) {
//            soLamTron = count / limit;
//        }
//        if (soDu != 0) {
//            soLamTron = ((count - soDu) / limit) + 1;
//        }
        Integer toltalPage = (int) Math.ceil(count / (float) limit);
        String maSP = "";
        Integer tonKho = 0;
        List<SanPham> sanPhams = sanPhamServices.phanTrang(limit, page);

        for (SanPham sanPham : sanPhams) {
            maSP = sanPham.getMaSP();
            ChiTietSPViewModels c = chiTietSPServices.load(maSP);

            if (c == null) {
                continue;
            }
            if (c.getTonKho() != 0) {
                tonKho = c.getTonKho();
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
                    tonKho,
                    c.getGiaNhap(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);

            }

        }
        pagination1.setPagegination(page, toltalPage);
        if (cbbSanPhamm.getItemCount() == 0) {

        } else {
            cbbSanPhamm.setSelectedIndex(0);
        }
        check = 0;
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
        if (cbbSanPhamm.getItemCount() == 0) {

        } else {
            cbbSanPhamm.setSelectedIndex(0);
        }
    }

    class myTableCellRender implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            tbSanPham.setRowHeight(110);

            return (Component) value;
        }
    }

    public static void cbbSanPham(List<SanPham> items) {
        cbbSanPhamm.removeAllItems();

        for (SanPham sanPham : items) {
            if (sanPham.getTrangThai() == 0) {
                cbbSanPhamm.addItem(sanPham.getTenSP());
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

        cbbSanPhamm.setSelectedItem(tenSP);
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
        }
        if (thoiGian.equals("3 Tháng")) {
            thoiGianBaoHanh = 90;
        }
        if (thoiGian.equals("6 tháng")) {
            thoiGianBaoHanh = 180;
        }
        if (thoiGian.equals("12 tháng")) {
            thoiGianBaoHanh = 360;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPhamm.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
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
            thoiGianBaoHanh = 0;
        } else if (thoiGian.equals("1 Tháng")) {
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPhamm.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        String masp = "";
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
                masp = sanPham.getMaSP();
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
            thoiGianBaoHanh = 0;
        } else if (thoiGian.equals("1 Tháng")) {
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        SanPham sp = null;
        String tenSP = (String) cbbSanPhamm.getSelectedItem();
        List<SanPham> sanPhams = sanPhamServices.getALL();
        String masp = "";
        for (SanPham sanPham : sanPhams) {
            if (tenSP.equals(sanPham.getTenSP())) {
                sp = sanPham;
                masp = sanPham.getMaSP();
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
        cbbSanPhamm.setSelectedIndex(0);
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        cbbImei.removeAllItems();
        txtSoLuongTon.setText("0");
        txtGhiChu.setText("");
        ImeiServices imeiServices = new ImeiServices();
        imeiServices.setList(null);
        cbbTGBH.setSelectedIndex(0);
        hienThiSanPham(1);
//        pagination1.setVisible(true);
//        pagination2.setVisible(false);
        check = 0;
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
        cbbSanPhamm = new javax.swing.JComboBox<>();
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
        jLabel21 = new javax.swing.JLabel();
        cbbTGBH = new javax.swing.JComboBox<>();
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
        cbbLocDanhMuc = new javax.swing.JComboBox<>();
        cbbLocMauSac = new javax.swing.JComboBox<>();
        cbbLocBoNho = new javax.swing.JComboBox<>();
        pagination1 = new pagination.Pagination();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();

        sanpham.setBackground(new java.awt.Color(255, 255, 255));

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Danh mục", "Màu Sắc", "Bộ Nhớ", "Tồn kho", "Giá nhập", "Giá bán", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.setRowHeight(40);
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Sản phẩm ");

        cbbSanPhamm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSanPhamm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSanPhammActionPerformed(evt);
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

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Thời gian bảo hành");

        cbbTGBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1 Tháng", "3 Tháng", "6 Tháng", "12 Tháng" }));
        cbbTGBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTGBHActionPerformed(evt);
            }
        });

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

        cbbLocDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbLocDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocDanhMucActionPerformed(evt);
            }
        });

        cbbLocMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbLocBoNho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        pagination1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Danh mục");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Màu sắc");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Bộ nhớ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_conversion_25px.png"))); // NOI18N
        jLabel5.setText("Lọc");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sanphamLayout = new javax.swing.GroupLayout(sanpham);
        sanpham.setLayout(sanphamLayout);
        sanphamLayout.setHorizontalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLocMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLocBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addComponent(btnUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(sanphamLayout.createSequentialGroup()
                                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtGiaNhap)
                                            .addComponent(cbbSanPhamm, 0, 222, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSanPham)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(sanphamLayout.createSequentialGroup()
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
                                    .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(sanphamLayout.createSequentialGroup()
                                            .addComponent(jLabel21)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cbbTGBH, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(sanphamLayout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addGap(27, 27, 27)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 23, Short.MAX_VALUE)))
                        .addGap(25, 25, 25))
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE))))
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        sanphamLayout.setVerticalGroup(
            sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sanphamLayout.createSequentialGroup()
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbLocMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbLocBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8))
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(sanphamLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbSanPhamm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addGroup(sanphamLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbbTGBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(kGradientPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kGradientPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1261, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMousePressed
        // TODO add your handling code here:
        fillSanPham();
    }//GEN-LAST:event_tbSanPhamMousePressed

    private void cbbSanPhammActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSanPhammActionPerformed
        // TODO add your handling code here:
        List<ChiTietSP> sanPhams = chiTietSPServices.getImei();
        if (sanPhams == null) {
            return;
        }
//        int row = tbSanPham.getRowCount();
//        if (row == 0) {
//            return;
//        }
//        if (row == 1) {
//            String cbbSanPham = (String) cbbSanPhamm.getSelectedItem();
//            String tenSP = tbSanPham.getValueAt(0, 0).toString();
//            if (tenSP.equals(cbbSanPham)) {
//                btnAdd.setEnabled(false);
//            } else {
//                btnAdd.setEnabled(true);
//            }
//            return;
//        }
//        String tenSP = "";
        String cbbSanPham = (String) cbbSanPhamm.getSelectedItem();
//        if (cbbSanPham == null) {
//            return;
//        }
//        for (int i = 0; i < row; i++) {
//            tenSP = tbSanPham.getValueAt(i, 0).toString();
//            if (cbbSanPham.equals(tenSP)) {
//                btnAdd.setEnabled(false);
//                btnUpdate.setEnabled(true);
//                btnDelete.setEnabled(true);
//                return;
//            }
//            btnUpdate.setEnabled(false);
//            btnDelete.setEnabled(false);
//            btnAdd.setEnabled(true);
        for (ChiTietSP sanPham : sanPhams) {
            if (sanPham.getSanPham().getTenSP().equalsIgnoreCase(cbbSanPham)) {
                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                return;
            }
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
            btnAdd.setEnabled(true);
        }
        ImeiServices imeiServices = new ImeiServices();
        imeiServices.setList(null);

//        }

    }//GEN-LAST:event_cbbSanPhammActionPerformed

    private void cbbImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbImeiActionPerformed

    private void btnAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddMouseExited

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        try {
            // TODO add your handling code here:
            String checkSo = "^[0-9]*$";
            if (btnAdd.isEnabled() == false) {
                JOptionPane.showMessageDialog(this, "Không thể thêm vì sản phẩm đã tồn tại !");
                return;
            }
            if (txtGiaNhap.getText().trim().isEmpty() || txtGiaBan.getText().trim().isEmpty() || txtGhiChu.getText().trim().isEmpty()) {
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

            if (cbbImei.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Imei rỗng !");
                return;
            }
            if (cbbSanPhamm.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm nào !");
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
                barCodeServices.taoBarCode(Imei);
                c.setMaImei(Imei);
                chiTietSPServices.add(c);
            }

            JOptionPane.showMessageDialog(this, "Thêm thành công !");
            hienThiSanPham(1);
            clear();

        } catch (ParseException ex) {
            Logger.getLogger(QLChiTietSanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(QLChiTietSanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(this, "Xóa thành công !");
        hienThiSanPham(1);
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
            if (txtGiaNhap.getText().trim().isEmpty() || txtGiaBan.getText().trim().isEmpty() || txtGhiChu.getText().trim().isEmpty()) {
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

            if (cbbImei.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Imei rỗng !");
                return;
            }
            if (cbbSanPhamm.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm nào !");
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
//            chiTietSPServices.xoaImei(c.getSanPham().getMaSP());
            List<ChiTietSP> list = chiTietSPServices.getImeibyMaSP(c.getSanPham().getMaSP());
            for (int i = 0; i < row; i++) {
                Imei = cbbImei.getItemAt(i);

                ChiTietSP c1 = layTTSanPham();
                //                c1.setAnh(c.getAnh());
                c1.setMaImei(Imei);
                chiTietSPServices.add(c1);
                ChiTietSP c3 = layTTSuaSanPham();
                c3.setMaImei(Imei);
                c3.setAnh(c3.getAnh());
                c3.setNgayTao(c1.getNgayTao());
                chiTietSPServices.update(c3);
                ImeiServices imeiServices = new ImeiServices();
                List<String> listXoa = imeiServices.getListXoa();
                if (listXoa != null) {
                    for (String string : listXoa) {
                        chiTietSPServices.updateImeiTrangThai(string, 1);
                    }
                }
                barCodeServices.taoBarCode(Imei);

            }

            JOptionPane.showMessageDialog(this, "Sửa thành công !");
            hienThiSanPham(1);
            clear();

        } catch (ParseException ex) {
            Logger.getLogger(QLChiTietSanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
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

    private void btnSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSanPhamMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSanPhamMouseExited

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

    private void cbbTGBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTGBHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTGBHActionPerformed

    private void cbbLocDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLocDanhMucActionPerformed
        // TODO add your handling code here:
//        String danhMuc = (String) cbbLocDanhMuc.getSelectedItem();
//        String nsx = (String) cbbLocNSX.getSelectedItem();
//        String mauSac = (String) cbbLocMauSac.getSelectedItem();
//        String boNho = (String) cbbLocBoNho.getSelectedItem();
//        if (danhMuc == null || nsx == null || mauSac == null || boNho == null) {
//            hienThiSanPham(1);
//            return;
//        }
//        String dkDanhMuc = "";
//        String dkNsx = "";
//        String dkMauSac = "";
//        String dkBoNhoTrong = "";
//        
//            dkDanhMuc = cbbLocDanhMuc.getSelectedIndex() > 0 ? "where TenDM like 'Iphone'"   : "";
//            dkNsx = cbbLocNSX.getSelectedIndex() > 0 ? " and TenNSX like " + nsx : "";
//            dkMauSac = cbbLocMauSac.getSelectedIndex() > 0 ? " and TenMauSac like " + mauSac : "";
//            dkBoNhoTrong = cbbLocBoNho.getSelectedIndex() > 0 ? " and DungLuong = " + Integer.parseInt(boNho) : "";
//        
////        if (danhMuc.equals("")) {
////            if (!nsx.isEmpty()) {
//////                dkDanhMuc = cbbLocDanhMuc.getSelectedIndex() > 0 ? "where TenDM like "+danhMuc : "";
////                dkNsx = cbbLocNSX.getSelectedIndex() > 0 ? "where TenNSX like " + nsx : "";
////                dkMauSac = cbbLocMauSac.getSelectedIndex() > 0 ? " and TenMauSac like " + mauSac : "";
////                dkBoNhoTrong = cbbLocBoNho.getSelectedIndex() > 0 ? " and DungLuong = " + Integer.parseInt(boNho) : "";
////            }
////            if (nsx.isEmpty()) {
////                if (!mauSac.isEmpty()) {
////                    dkMauSac = cbbLocMauSac.getSelectedIndex() > 0 ? "where TenMauSac like " + mauSac : "";
////                    dkBoNhoTrong = cbbLocBoNho.getSelectedIndex() > 0 ? " and DungLuong = " + Integer.parseInt(boNho) : "";
////                }
////                if (mauSac.isEmpty()) {
////                    if (!boNho.isEmpty()) {
////                        dkBoNhoTrong = cbbLocBoNho.getSelectedIndex() > 0 ? "where DungLuong = " + Integer.parseInt(boNho) : "";
////                    }
////                }
////            }
////        }
//        String hql = "From SanPham where " + dkDanhMuc + dkNsx + dkMauSac + dkBoNhoTrong;


    }//GEN-LAST:event_cbbLocDanhMucActionPerformed

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        // TODO add your handling code here:
//        pagination1.setVisible(false);
//        pagination2.setVisible(true);
        check = 1;
        LocSanPham(1);
//        String danhMuc = (String) cbbLocDanhMuc.getSelectedItem();
//        String mauSac = (String) cbbLocMauSac.getSelectedItem();
//        Integer boNho = Integer.parseInt((String) cbbLocBoNho.getSelectedItem());
//        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
//        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
//        model.setRowCount(0);
////        Integer limit = 8;
////        List<SanPham> sps = sanPhamServices.loc(danhMuc, mauSac, boNho);
////        Integer count = sps.size();
////        Integer soDu = count % limit;
////        Integer soLamTron = 0;
////        if (soDu == 0) {
////            soLamTron = count / limit;
////        }
////        if (soDu != 0) {
////            soLamTron = ((count - soDu) / limit) + 1;
////        }
////        Integer toltalPage = soLamTron;
//        String maSP = "";
//        Integer tonKho = 0;
//        List<SanPham> sanPhams = sanPhamServices.locSanPham(danhMuc, mauSac, boNho);
//        if (sanPhams.size() == 0) {
//            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
//            return;
//        }
//        for (SanPham sanPham : sanPhams) {
//            maSP = sanPham.getMaSP();
//            ChiTietSPViewModels c = chiTietSPServices.load(maSP);
//            if (c == null) {
//                continue;
//            }
//            if (c.getTonKho() != 0) {
//                tonKho = c.getTonKho();
//            }
//
//            if (c.getTrangThai() == 0) {
//                JLabel label = new JLabel();
//                ImageIcon icon = new ImageIcon(c.getAnh());
//                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
//                label.setIcon(new ImageIcon(img));
//                Object[] data = new Object[]{
//                    c.getTenSP(),
//                    c.getNsx(),
//                    c.getMauSac(),
//                    c.getBoNho(),
//                    tonKho,
//                    c.getGiaNhap(),
//                    c.getGiaBan(),
//                    label
//                };
//                model.addRow(data);
//            }
//
//        }
//        JOptionPane.showMessageDialog(this, "Lọc thành công !");
////        pagination1.setPagegination(1, toltalPage);
//        if (cbbSanPhamm.getItemCount() == 0) {
//
//        } else {
//            cbbSanPhamm.setSelectedIndex(0);
//        }


    }//GEN-LAST:event_jLabel5MousePressed
    private void LocSanPham(Integer page) {
        String danhMuc = (String) cbbLocDanhMuc.getSelectedItem();
        String mauSac = (String) cbbLocMauSac.getSelectedItem();
        Integer boNho = Integer.parseInt((String) cbbLocBoNho.getSelectedItem());
        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        Integer limit = 8;
        List<SanPham> sps = sanPhamServices.locSanPham(danhMuc, mauSac, boNho);
        Integer count = sps.size();
        if (count == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }
        Integer soDu = count % limit;
//        Integer soLamTron = 0;
//        if (soDu == 0) {
//            soLamTron = count / limit;
//        }
//        if (soDu != 0) {
//            soLamTron = ((count - soDu) / limit) + 1;
//        }
        Integer toltalPage = (int) Math.ceil(count / (float) limit);
        String maSP = "";
        Integer tonKho = 0;
        List<SanPham> sanPhams = sanPhamServices.loc(danhMuc, mauSac, boNho, limit, page);
        for (SanPham sanPham : sanPhams) {
            maSP = sanPham.getMaSP();
            ChiTietSPViewModels c = chiTietSPServices.load(maSP);
            if (c == null) {
                continue;
            }
            if (c.getTonKho() != 0) {
                tonKho = c.getTonKho();
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
                    tonKho,
                    c.getGiaNhap(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);
            }

        }
        pagination1.setPagegination(page, toltalPage);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnImei;
    private javax.swing.JLabel btnReset;
    private javax.swing.JLabel btnSanPham;
    private keeptoo.KGradientPanel btnThem;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JLabel btnUpload;
    private static javax.swing.JComboBox<String> cbbImei;
    private javax.swing.JComboBox<String> cbbLocBoNho;
    private javax.swing.JComboBox<String> cbbLocDanhMuc;
    private javax.swing.JComboBox<String> cbbLocMauSac;
    private static javax.swing.JComboBox<String> cbbSanPhamm;
    private javax.swing.JComboBox<String> cbbTGBH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JLabel lbAnh;
    private pagination.Pagination pagination1;
    private javax.swing.JPanel sanpham;
    private static javax.swing.JTable tbSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private static javax.swing.JLabel txtSoLuongTon;
    // End of variables declaration//GEN-END:variables
}
