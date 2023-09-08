/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import domainmodels.BoNhoTrong;
import domainmodels.DanhMuc;
import domainmodels.MauSac;
import domainmodels.NSX;
import domainmodels.SanPham;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import services.ChiTietSPServices;
import services.DanhMucServices;
import services.IChiTietSPServices;
import services.IDanhMucServices;
import services.IQLBoNhoTrongServices;
import services.IQLMauSacServices;
import services.IQLNSXServices;
import services.IQLSanPhamServices;
import services.ISanPhamServices;
import services.QLBoNhoTrongServices;
import services.QLMauSacServices;
import services.QLNSXServices;
import services.QLSanPhamServices;

/**
 *
 * @author Admin
 */
public class QLSanPham extends javax.swing.JFrame {

    /**
     * Creates new form QLSanPham
     */
     private IQLSanPhamServices sanPhamServices;
     private IDanhMucServices danhMucServices;
    private IQLNSXServices nSXServices;
    private IChiTietSPServices chiTietSPServices;
    private IQLMauSacServices mauSacServices;
    private IQLBoNhoTrongServices boNhoTrongServices;
    public QLSanPham() {
        initComponents();
        
         sanPhamServices = new QLSanPhamServices();
        nSXServices = new QLNSXServices();
        chiTietSPServices = new ChiTietSPServices();
        danhMucServices = new DanhMucServices();
        mauSacServices = new QLMauSacServices();
        boNhoTrongServices = new QLBoNhoTrongServices();
        cbbHDH.removeAllItems();
        cbbHDH.addItem("IOS");
        cbbHDH.addItem("ANDROID");
        cbbCamera.removeAllItems();
        cbbCamera.addItem("32MP");
        cbbCamera.addItem("48MP");
        cbbCamera.addItem("64MP");
        cbbCamera.addItem("96MP");
        cbbRam.removeAllItems();
        cbbRam.addItem("3");
        cbbRam.addItem("4");
        cbbRam.addItem("6");
        cbbRam.addItem("8");
        cbbRam.addItem("12");
        loadCbbNSX();
        loadCbbDanhMuc();
        loadCbbBoNhoTrong();
        loadCbbMauSac();
        load();
       
    }
    public void loadMaSP() {

        String ma = "";
        List<SanPham> sanPhams = sanPhamServices.getALL();
        if (sanPhams.size() == 0) {
            ma = "SP0";
        } else {
            SanPham sp = sanPhamServices.layMa();
//          
            ma = sp.getMaSP();
        }

        String mangString[] = ma.split("");
        String so = "";
        for (int i = 2; i < mangString.length; i++) {
            so += mangString[i];
        }

        Integer sofinal = Integer.parseInt(so) + 1;
        String maMoi = "SP" + sofinal;
        txtMa.setText(maMoi);
    }
    public void loadCbbNSX(){
        List<NSX> nsxs = nSXServices.getALL();
        cbbNSX.removeAllItems();
        for (NSX nsx : nsxs) {
            if (nsx.getTrangThai() == 0) {
                cbbNSX.addItem(nsx.getTenNSX());
            }
        }
    }
    public void loadCbbMauSac(){
        List<MauSac> mauSacs = mauSacServices.getALL();
        cbbMauSac.removeAllItems();
        for (MauSac m : mauSacs) {
            if (m.getTrangThai() == 0) {
                cbbMauSac.addItem(m.getTenMauSac());
            }
        }
    }
    public void loadCbbBoNhoTrong(){
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        cbbBoNho.removeAllItems();
        for (BoNhoTrong m : boNhoTrongs) {
            if (m.getTrangThai() == 0) {
                cbbBoNho.addItem(String.valueOf(m.getDungLuong()));
            }
        }
    }
    public void loadCbbDanhMuc(){
        List<DanhMuc> d = danhMucServices.getALL();
        cbbdanhMuc.removeAllItems();
        for (DanhMuc danhMuc : d) {
            if (danhMuc.getTrangThai() == 0) {
                cbbdanhMuc.addItem(danhMuc.getTenDanhMuc());
            }
        }
    }
    

    public void load() {
        loadMaSP();
        DefaultTableModel model = (DefaultTableModel) tbQLSanPham.getModel();
        model.setRowCount(0);
        List<SanPham> list = sanPhamServices.getALL();
        for (SanPham sanPham : list) {
            if (sanPham.getTrangThai() == 0) {
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getNsx().getTenNSX(),
                    sanPham.getDanhmuc().getTenDanhMuc(),
                    sanPham.getMausac().getTenMauSac(),
                    sanPham.getBonhotrong().getDungLuong(),
                    sanPham.getHeDieuHanh(),
                    sanPham.getCamera(),
                    sanPham.getRam(),
                    sanPham.getCpu(),
                    sanPham.getManHinh(),
                    sanPham.getPin(),
                    sanPham.getXuatxu()
                    
                };
                model.addRow(data);
            }
        }
    }
    public void loadCbbSanPham(){
        List<SanPham> items = sanPhamServices.getALL();
        QLChiTietSanPham.cbbSanPham(items);
    }
    public void loadCbbSanPham1(){
        List<SanPham> items = sanPhamServices.getALL();
        QLChiTietSanPhamPanel.cbbSanPham(items);
    }
    public static  void loadCbbDanhMuc(List<DanhMuc> items){
        cbbdanhMuc.removeAllItems();
        for (DanhMuc item : items) {
            if (item.getTrangThai() == 0) {
                cbbdanhMuc.addItem(item.getTenDanhMuc());
            }
        }
    }
    public static  void loadCbbMauSacc(List<MauSac> items){
        cbbMauSac.removeAllItems();
        for (MauSac item : items) {
            if (item.getTrangThai() == 0) {
                cbbMauSac.addItem(item.getTenMauSac());
            }
        }
    }
     public static  void loadCbbBoNhoo(List<BoNhoTrong> items){
        cbbBoNho.removeAllItems();
        for (BoNhoTrong item : items) {
            if (item.getTrangThai() == 0) {
                cbbBoNho.addItem(String.valueOf(item.getDungLuong()));
            }
        }
    }
    public static  void loadCbbNsx(List<NSX> items){
        cbbNSX.removeAllItems();
        for (NSX item : items) {
            if (item.getTrangThai() == 0) {
                cbbNSX.addItem(item.getTenNSX());
            }
        }
    }
//    public void loadHienThiSanPham(){
//        tbQLSanPham.getColumn("Ảnh").setCellRenderer(new SanPhamForm.myTableCellRender());
//        String maSP = "";
//        List<SanPham> sanPhams = sanPhamServices.getALL();
//        for (SanPham sanPham : sanPhams) {
//            maSP = sanPham.getMaSP();
//            ChiTietSPViewModels c = chiTietSPServices.load(maSP);
//            if (c == null) {
//                continue;
//            }
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
//                    c.getTonKho(),
//                    c.getGiaNhap(),
//                    c.getGiaBan(),
//                    label
//                };
//                QLChiTietSanPham.loadHienThiSanPham(data);
//            }
//        }
//        
//    }
//     class myTableCellRender implements TableCellRenderer {
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            tbQLSanPham.setRowHeight(70);
//
//            return (Component) value;
//        }
//    }
    public SanPham layTT() throws ParseException{
        String ma = txtMa.getText();
        String ten = txtTen.getText();
        String nsxString = (String) cbbNSX.getSelectedItem();
        List<NSX> nsxs = nSXServices.getALL();
        NSX n = null ;
        for (NSX nsx : nsxs) {
            if (nsxString.equalsIgnoreCase(nsx.getTenNSX())) {
                n = nsx;
            }
        }
        String danhmucString = (String) cbbdanhMuc.getSelectedItem();
        List<DanhMuc> d = danhMucServices.getALL();
        DanhMuc b = null ;
        for (DanhMuc danhMuc : d) {
            if (danhmucString.equalsIgnoreCase(danhMuc.getTenDanhMuc())) {
                b = danhMuc;
            }
        }
        String mauString = (String) cbbMauSac.getSelectedItem();
        List<MauSac> mauSacs = mauSacServices.getALL();
        MauSac c = null ;
        for (MauSac m : mauSacs) {
            if (mauString.equalsIgnoreCase(m.getTenMauSac())) {
                c = m;
            }
        }
        String boNhoString = (String) cbbBoNho.getSelectedItem();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        BoNhoTrong e = null ;
        for (BoNhoTrong m : boNhoTrongs) {
            if (boNhoString.equalsIgnoreCase(String.valueOf(m.getDungLuong()))) {
                e = m;
            }
        }
        String heDieuHanhString = (String) cbbHDH.getSelectedItem();
        String cameraString = (String) cbbCamera.getSelectedItem();
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
        SanPham sp = new SanPham();
        sp.setMaSP(ma);
        sp.setTenSP(ten);
        sp.setNgayTao(date);
        sp.setNsx(n);
        sp.setDanhmuc(b);
        sp.setMausac(c);
        sp.setBonhotrong(e);
        sp.setHeDieuHanh(heDieuHanhString);
        sp.setCamera(cameraString);
        sp.setRam(ram);
        sp.setCpu(cpu);
        sp.setManHinh(manHinh);
        sp.setPin(pin);
        sp.setXuatxu(xuatXu);
        sp.setTrangThai(0);
        return sp ;
    }
    public SanPham layTTSua() throws ParseException{
        String ma = txtMa.getText();
        String ten = txtTen.getText();
        String nsxString = (String) cbbNSX.getSelectedItem();
        List<NSX> nsxs = nSXServices.getALL();
        NSX n = null ;
        for (NSX nsx : nsxs) {
            if (nsxString.equalsIgnoreCase(nsx.getTenNSX())) {
                n = nsx;
            }
        }
        String danhmucString = (String) cbbdanhMuc.getSelectedItem();
        List<DanhMuc> d = danhMucServices.getALL();
        DanhMuc b = null ;
        for (DanhMuc danhMuc : d) {
            if (danhmucString.equalsIgnoreCase(danhMuc.getTenDanhMuc())) {
                b = danhMuc;
            }
        }
        String mauString = (String) cbbMauSac.getSelectedItem();
        List<MauSac> mauSacs = mauSacServices.getALL();
        MauSac c = null ;
        for (MauSac m : mauSacs) {
            if (mauString.equalsIgnoreCase(m.getTenMauSac())) {
                c = m;
            }
        }
        String boNhoString = (String) cbbBoNho.getSelectedItem();
        List<BoNhoTrong> boNhoTrongs = boNhoTrongServices.getALL();
        BoNhoTrong e = null ;
        for (BoNhoTrong m : boNhoTrongs) {
            if (boNhoString.equalsIgnoreCase(String.valueOf(m.getDungLuong()))) {
                e = m;
            }
        }
        String heDieuHanhString = (String) cbbHDH.getSelectedItem();
        String cameraString = (String) cbbCamera.getSelectedItem();
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
        SanPham sp = new SanPham();
        sp.setMaSP(ma);
        sp.setTenSP(ten);
        sp.setNgayTao(date);
        sp.setNsx(n);
        sp.setDanhmuc(b);
        sp.setMausac(c);
        sp.setBonhotrong(e);
        sp.setHeDieuHanh(heDieuHanhString);
        sp.setCamera(cameraString);
        sp.setRam(ram);
        sp.setCpu(cpu);
        sp.setManHinh(manHinh);
        sp.setPin(pin);
        sp.setXuatxu(xuatXu);
        sp.setTrangThai(0);
        return sp ;
    }
    public void fill(){
        int index = tbQLSanPham.getSelectedRow();
        String ma = tbQLSanPham.getValueAt(index, 0).toString();
        String ten = tbQLSanPham.getValueAt(index, 1).toString();
        String nsx = tbQLSanPham.getValueAt(index, 2).toString();
        String danhMuc = tbQLSanPham.getValueAt(index, 3).toString();
        String mauSac = tbQLSanPham.getValueAt(index, 4).toString();
        String boNho = tbQLSanPham.getValueAt(index, 5).toString();
        String heDieuHanh = tbQLSanPham.getValueAt(index, 6).toString();
        String camera = tbQLSanPham.getValueAt(index, 7).toString();
        String ram = tbQLSanPham.getValueAt(index, 8).toString();
        String cpu = tbQLSanPham.getValueAt(index, 9).toString();
        String manHinh = tbQLSanPham.getValueAt(index, 10).toString();
        String pin = tbQLSanPham.getValueAt(index, 11).toString();
        String xuatXu = tbQLSanPham.getValueAt(index, 12).toString();
        txtMa.setText(ma);
        txtTen.setText(ten);
        cbbNSX.setSelectedItem(nsx);
        cbbdanhMuc.setSelectedItem(danhMuc);
        cbbMauSac.setSelectedItem(mauSac);
        cbbBoNho.setSelectedItem(boNho);
        cbbHDH.setSelectedItem(heDieuHanh);
        cbbCamera.setSelectedItem(camera);
        cbbRam.setSelectedItem(ram);
        txtCpu.setText(cpu);
        txtManHinh.setText(manHinh);
        txtPin.setText(pin);
        txtXuatXu.setText(xuatXu);
    }
    public static void loadCbbNSX(List<NSX> items){
        cbbNSX.removeAllItems();
        for (NSX item : items) {
             if (item.getTrangThai() == 0) {
                cbbNSX.addItem(item.getTenNSX());
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbQLSanPham = new javax.swing.JTable();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnUpdate = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        btnAdd = new javax.swing.JLabel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        btnDelete = new javax.swing.JLabel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        btnSearch = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbbNSX = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbbdanhMuc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbMauSac = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbbBoNho = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbbHDH = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbbCamera = new javax.swing.JComboBox<>();
        cbbRam = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtCpu = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtManHinh = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPin = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        txtMa = new javax.swing.JLabel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        btnSearch1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sản Phẩm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mã");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tên");

        tbQLSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "NSX", "Danh mục", "Màu Sắc", "Bộ Nhớ", "HÐH", "Camera", "Ram", "Cpu", "Màn Hình", "Pin", "Xuất Xứ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbQLSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbQLSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbQLSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbQLSanPham);

        btnUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_update_30px.png"))); // NOI18N
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUpdateMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_add_30px_6.png"))); // NOI18N
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_waste_30px.png"))); // NOI18N
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDeleteMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        btnSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_search_30px.png"))); // NOI18N
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSearchMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("NSX");

        cbbNSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNSXActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Danh Mục");

        cbbdanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbdanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbdanhMucActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Màu Sắc");

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauSacActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Bộ Nhớ");

        cbbBoNho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbBoNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbBoNhoActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_joyent_30px.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("HÐH");

        cbbHDH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbHDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHDHActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("CAMERA");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("RAM");

        cbbCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCameraActionPerformed(evt);
            }
        });

        cbbRam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbRamActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("CPU");

        txtCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpuActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Màn Hình");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("PIN");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Xuất Xứ");

        txtMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnSearch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_available_updates_30px.png"))); // NOI18N
        btnSearch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSearch1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbbdanhMuc, 0, 227, Short.MAX_VALUE)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbNSX, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel10)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbHDH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPin, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtManHinh, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbCamera, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbRam, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCpu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(106, 106, 106))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(339, 339, 339))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(cbbdanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cbbHDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cbbCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cbbRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtCpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtPin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbQLSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbQLSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbQLSanPhamMouseClicked

    private void tbQLSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbQLSanPhamMousePressed
        // TODO add your handling code here:
        fill();
    }//GEN-LAST:event_tbQLSanPhamMousePressed

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked

    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMousePressed
        // TODO add your handling code here:
        String checkSo = "^[0-9]*$";
        try {
            // TODO add your handling code here:
            int index = tbQLSanPham.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
                return;
            }
            int i = tbQLSanPham.getSelectedRowCount();
            if (i > 1) {
                JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
                return;
            }
            if(txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtCpu.getText().trim().isEmpty() || txtXuatXu.getText().trim().isEmpty() || txtManHinh.getText().trim().isEmpty() || txtPin.getText().isEmpty() ){
                JOptionPane.showMessageDialog(this, "Không được bo trống ");
                return;
            }
            if(txtMa.getText().length() > 10){
                JOptionPane.showMessageDialog(this, "Mã nhỏ hơn hoặc bằng 10 ký tự");
                return;
            }
            if(txtTen.getText().length() > 30){
                JOptionPane.showMessageDialog(this, "Tên nhỏ hơn hoặc bằng 30 ký tự");
                return;
            }
            String xuatXu = txtXuatXu.getText().trim();
            String cpu = txtCpu.getText().trim();
            String manHinh = txtManHinh.getText().trim();
            String pin = txtPin.getText().trim();
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
            if (cbbdanhMuc.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có danh mục nào !");
                return;
            }
            if (cbbNSX.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có nsx nào !");
                return;
            }
            if (cbbBoNho.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có Bộ nhớ trong nào !");
                return;
            }
            if (cbbMauSac.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có màu sắc nào !");
                return;
            }
            
            SanPham n = layTTSua();
            
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa ?");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            if (sanPhamServices.update(n) == true) {
                JOptionPane.showMessageDialog(this, "Sửa thành công !");
                load();
//               loadCbbSanPham();
               loadCbbSanPham1();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại !");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateMousePressed

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked

    }//GEN-LAST:event_btnAddMouseClicked

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        // TODO add your handling code here:
            String checkSo = "^[0-9]*$";
        try {
            if(txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtCpu.getText().trim().isEmpty() || txtXuatXu.getText().trim().isEmpty() || txtManHinh.getText().trim().isEmpty() || txtPin.getText().isEmpty() ){
                JOptionPane.showMessageDialog(this, "Không được bo trống ");
                return;
            }
            if(txtMa.getText().length() > 10){
                JOptionPane.showMessageDialog(this, "Mã nhỏ hơn hoặc bằng 10 ký tự");
                return;
            }
            if(txtTen.getText().length() > 30){
                JOptionPane.showMessageDialog(this, "Tên nhỏ hơn hoặc bằng 30 ký tự");
                return;
            }
            String xuatXu = txtXuatXu.getText().trim();
            String cpu = txtCpu.getText().trim();
            String manHinh = txtManHinh.getText().trim();
            String pin = txtPin.getText().trim();
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
                JOptionPane.showMessageDialog(this, "Vui lòng nhập pin là số nguyên dương !");
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
            if (cbbdanhMuc.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có danh mục nào !");
                return;
            }
            if (cbbNSX.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có nsx nào !");
                return;
            }
            if (cbbBoNho.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có Bộ nhớ trong nào !");
                return;
            }
            if (cbbMauSac.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có màu sắc nào !");
                return;
            }
            // TODO add your handling code here:
            SanPham n = layTT();
            if (sanPhamServices.seachbyMa(n.getMaSP()) != null) {
                JOptionPane.showMessageDialog(this, "Mã đã tồn tại !");
                return;
            }
            if (sanPhamServices.add(n) == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công !");
                load();
//                loadCbbSanPham();
                loadCbbSanPham1();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddMousePressed

    private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMousePressed
        try {
            
            // TODO add your handling code here:
            int index = tbQLSanPham.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
                return;
            }
            int i = tbQLSanPham.getSelectedRowCount();
            if (i > 1) {
                JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
                return;
            }
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa ?");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            SanPham n = layTT();
            if (sanPhamServices.delete(n) == true) {
                JOptionPane.showMessageDialog(this, "Xóa thành công !");
                load();
//                loadCbbSanPham();
                loadCbbSanPham1();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại !");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteMousePressed

    private void btnSearchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMousePressed
        // TODO add your handling code here:
        String ten = txtTen.getText();
        SanPham n = sanPhamServices.seachbyMa(ten);
        if (ten.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập Tên trước khi tìm kiếm !");
            load();
            return;
        }
        List<SanPham> sanPhams = sanPhamServices.timKiembyTrangThai(ten);
        if (sanPhams.size() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tbQLSanPham.getModel();
        model.setRowCount(0);
        for (SanPham sanPham : sanPhams) {
            if (sanPham.getTrangThai() == 0) {
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getNsx().getTenNSX(),
                    sanPham.getDanhmuc().getTenDanhMuc(),
                    sanPham.getMausac().getTenMauSac(),
                    sanPham.getBonhotrong().getDungLuong(),
                    sanPham.getHeDieuHanh(),
                    sanPham.getCamera(),
                    sanPham.getRam(),
                    sanPham.getCpu(),
                    sanPham.getManHinh(),
                    sanPham.getPin(),
                    sanPham.getXuatxu()
                    
                };
                model.addRow(data);
            }
        }
    }//GEN-LAST:event_btnSearchMousePressed
    public void clear(){
        load();
        txtCpu.setText("");
        txtManHinh.setText("");
        txtPin.setText("");
        txtTen.setText("");
        txtXuatXu.setText("");
        cbbNSX.setSelectedIndex(0);
        cbbdanhMuc.setSelectedIndex(0);
        cbbMauSac.setSelectedIndex(0);
        cbbBoNho.setSelectedIndex(0);
        cbbHDH.setSelectedIndex(0);
        cbbCamera.setSelectedIndex(0);
        cbbRam.setSelectedIndex(0);
    }
    private void cbbNSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNSXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbNSXActionPerformed

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        // TODO add your handling code here:
        new QLNSX().setVisible(true);
    }//GEN-LAST:event_jLabel5MousePressed

    private void cbbdanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbdanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbdanhMucActionPerformed

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        // TODO add your handling code here:
        new QLDanhMuc().setVisible(true);
    }//GEN-LAST:event_jLabel7MousePressed

    private void cbbMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMauSacActionPerformed

    private void cbbBoNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbBoNhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbBoNhoActionPerformed

    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed
        // TODO add your handling code here:
        new QLBoNhoTrong().setVisible(true);
    }//GEN-LAST:event_jLabel10MousePressed

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        // TODO add your handling code here:
        new QLMauSac().setVisible(true);
    }//GEN-LAST:event_jLabel11MousePressed

    private void cbbHDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbHDHActionPerformed

    private void cbbCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCameraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbCameraActionPerformed

    private void cbbRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbRamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbRamActionPerformed

    private void txtCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpuActionPerformed

    private void btnSearch1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearch1MousePressed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnSearch1MousePressed

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
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLSanPham().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JLabel btnSearch1;
    private javax.swing.JLabel btnUpdate;
    private static javax.swing.JComboBox<String> cbbBoNho;
    private static javax.swing.JComboBox<String> cbbCamera;
    private static javax.swing.JComboBox<String> cbbHDH;
    private static javax.swing.JComboBox<String> cbbMauSac;
    private static javax.swing.JComboBox<String> cbbNSX;
    private static javax.swing.JComboBox<String> cbbRam;
    private static javax.swing.JComboBox<String> cbbdanhMuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel5;
    private javax.swing.JTable tbQLSanPham;
    private javax.swing.JTextField txtCpu;
    private javax.swing.JLabel txtMa;
    private javax.swing.JTextField txtManHinh;
    private javax.swing.JTextField txtPin;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
