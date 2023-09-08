/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainmodels.NhanVien;
import java.awt.Image;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pagination.EventPagination;
import services.AnhService;
import services.HashPasswordServices;
import services.INhanVienServices;
import services.NhanVienServices;

/**
 *
 * @author HANGOCHAN
 */
public class QLNhanVienPanel extends javax.swing.JPanel {

    /**
     * Creates new form QLNhanVienPanel
     */
    private INhanVienServices services;
    private Integer check = 0 ;
    public QLNhanVienPanel() {
        initComponents();

        services = new NhanVienServices();
        cbbVaiTro.removeAllItems();;
        cbbVaiTro.addItem("Quản Lý");
        cbbVaiTro.addItem("Nhân Viên");
//        hienThi();
        loadData(1);
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                if (check == 0) {
                    loadData(page);
                }
                if (check == 1) {
                    timKiem(page);
                }
                if(check == 2){
                    Loc(page);
                }
                
            }
        });
        cbbLoc.removeAllItems();
        cbbLoc.addItem("Quản Lý");
        cbbLoc.addItem("Nhân Viên");

    }

    public void loadData(Integer page) {
        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
        model.setRowCount(0);
        Integer limit = 10;
        List<NhanVien> nhanViens = services.getALLbyTrangThai(0);
        Integer count = nhanViens.size();
//        Integer soDu = count % 10;
//        Integer soLamTron = 0;
//        if (soDu == 0) {
//            soLamTron = count / 10;
//        }
//        if (soDu != 0) {
//            soLamTron = ((count - soDu) / 10) + 1;
//        }
        Integer toltalPage = (int) Math.ceil(count / (float) limit);

        List<NhanVien> kh = services.phanTrang(limit, page);
        String vaiTro = "";
        for (NhanVien nhanVien : kh) {
            if (nhanVien.getTrangThai() == 0) {

                if (nhanVien.getVaiTro() == 0) {
                    vaiTro = "Quản Lý";
                } else {
                    vaiTro = "Nhân Viên";
                }
                Object[] data = new Object[]{
                    nhanVien.getMaNV(),
                    nhanVien.getTenNV(),
                    nhanVien.getSdt(),
                    nhanVien.getEmail(),
                    nhanVien.getQueQuan(),
                    nhanVien.getMatKhau(),
                    vaiTro,};
                model.addRow(data);
            }
        }
        pagination1.setPagegination(page, toltalPage);
        check = 0;
    }

    public void hienThi() {
        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
        model.setRowCount(0);
        String vaiTro = "";
        List<NhanVien> list = services.getALL();
        for (NhanVien nhanVien : list) {
            if (nhanVien.getTrangThai() == 0) {
                if (nhanVien.getVaiTro() == 0) {
                    vaiTro = "Quản Lý";
                } else {
                    vaiTro = "Nhân Viên";
                }
                Object[] data = new Object[]{
                    nhanVien.getMaNV(),
                    nhanVien.getTenNV(),
                    nhanVien.getSdt(),
                    nhanVien.getEmail(),
                    nhanVien.getQueQuan(),
                    nhanVien.getMatKhau(),
                    vaiTro,};
                model.addRow(data);
            }
        }
    }

    public NhanVien layTT() throws ParseException {
        String ma = txtMa.getText();
        String ten = txtTen.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        String queQuan = txtQueQuan.getText();
        String matKhau = txtMatKhau.getText();
        String vaiTroString = (String) cbbVaiTro.getSelectedItem();
        Integer vaiTro = 0;
        if (vaiTroString.equalsIgnoreCase("Quản Lý")) {
            vaiTro = 0;
        } else {
            vaiTro = 1;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        HashPasswordServices a = new HashPasswordServices();
        NhanVien nv = new NhanVien();
        nv.setMaNV(ma);
        nv.setTenNV(ten);
        nv.setSdt(sdt);
        nv.setEmail(email);
        nv.setQueQuan(queQuan);
        nv.setMatKhau(a.HashPassword(matKhau));
        nv.setVaiTro(vaiTro);
        nv.setAnh(anh);
        nv.setNgayTao(date);
        nv.setTrangThai(0);
        return nv;
    }

    public NhanVien layTTSua() throws ParseException {
        String ma = txtMa.getText();
        String ten = txtTen.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        String queQuan = txtQueQuan.getText();
        String matKhau = txtMatKhau.getText();
        String vaiTroString = (String) cbbVaiTro.getSelectedItem();
        Integer vaiTro = 0;
        if (!vaiTroString.equalsIgnoreCase("Quản Lý")) {
            vaiTro = 1;
        }
        AnhService anhService = new AnhService();
        String anh = anhService.getAnh();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        HashPasswordServices a = new HashPasswordServices();
        int index = tbNhanVien.getSelectedRow();
        NhanVien nv = new NhanVien();
        nv.setMaNV(ma);
        nv.setTenNV(ten);
        nv.setSdt(sdt);
        nv.setEmail(email);
        nv.setQueQuan(queQuan);
        String pass = tbNhanVien.getValueAt(index, 5).toString();
        if (pass.equals(txtMatKhau.getText())) {
            nv.setMatKhau(matKhau);
        } else {
            nv.setMatKhau(a.HashPassword(matKhau));
        }
        NhanVien nv1 = services.fill(ma);
        if (anh == null) {
            nv.setAnh(nv1.getAnh());
        } else {
            nv.setAnh(anh);
        }
        nv.setVaiTro(vaiTro);
        nv.setNgayTao(nv1.getNgayTao());
        nv.setNgaySua(date);
        nv.setTrangThai(0);
        return nv;
    }

    public void fill() {
        int index = tbNhanVien.getSelectedRow();
        String ma = tbNhanVien.getValueAt(index, 0).toString();
        String ten = tbNhanVien.getValueAt(index, 1).toString();
        String sdt = tbNhanVien.getValueAt(index, 2).toString();
        String email = tbNhanVien.getValueAt(index, 3).toString();
        String queQuan = tbNhanVien.getValueAt(index, 4).toString();
        String matKhau = tbNhanVien.getValueAt(index, 5).toString();
        String vaiTro = tbNhanVien.getValueAt(index, 6).toString();
        NhanVien c = services.fill(ma);
        if (c.getAnh() == null) {

        } else {
            ImageIcon icon = new ImageIcon(c.getAnh());
            Image image = icon.getImage().getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon m = new ImageIcon(image);
            lbAnh.setIcon(m);
        }

        txtMa.setText(ma);
        txtTen.setText(ten);
        txtSDT.setText(sdt);
        txtEmail.setText(email);
        txtQueQuan.setText(queQuan);
        txtMatKhau.setText(matKhau);
        cbbVaiTro.setSelectedItem(vaiTro);
    }

    public void clear() {
        check = 0;
        txtTimKiem.setText("");
        txtMa.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtQueQuan.setText("");
        txtMatKhau.setText("");
        cbbVaiTro.setSelectedIndex(0);
        loadData(1);
        lbAnh.setIcon(null);
        pagination1.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nhanvien = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        btnAdd = new javax.swing.JLabel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        btnReset = new javax.swing.JLabel();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        btnDelete = new javax.swing.JLabel();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        btnUpdate = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        btnUpload = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtQueQuan = new javax.swing.JTextArea();
        jLabel27 = new javax.swing.JLabel();
        cbbVaiTro = new javax.swing.JComboBox<>();
        txtMatKhau = new javax.swing.JPasswordField();
        cbbLoc = new javax.swing.JComboBox<>();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnLoc = new javax.swing.JLabel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        btnTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        pagination1 = new pagination.Pagination();

        nhanvien.setBackground(new java.awt.Color(255, 255, 255));

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "SDT", "Email", "Quê Quán", "Mật Khẩu", "Vai Trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbNhanVien.setRowHeight(50);
        tbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbNhanVien);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Mã");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Tên");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("SDT");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel25.setText("Quê Quán");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel26.setText("Mật Khẩu");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Email");

        kGradientPanel2.setkGradientFocus(150);

        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_add_30px_6.png"))); // NOI18N
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        txtQueQuan.setColumns(20);
        txtQueQuan.setRows(5);
        jScrollPane2.setViewportView(txtQueQuan);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel27.setText("Vai Trò");

        cbbVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_conversion_25px_1.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLocMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_search_25px_1.png"))); // NOI18N
        btnTimKiem.setText("Tìm với tên");
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTimKiemMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pagination1.setOpaque(false);

        javax.swing.GroupLayout nhanvienLayout = new javax.swing.GroupLayout(nhanvien);
        nhanvien.setLayout(nhanvienLayout);
        nhanvienLayout.setHorizontalGroup(
            nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhanvienLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(nhanvienLayout.createSequentialGroup()
                                .addGap(321, 321, 321)
                                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nhanvienLayout.createSequentialGroup()
                                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(nhanvienLayout.createSequentialGroup()
                                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(nhanvienLayout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nhanvienLayout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel25)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nhanvienLayout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel26)))
                                            .addGroup(nhanvienLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel27)))
                                        .addGap(8, 8, 8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nhanvienLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(nhanvienLayout.createSequentialGroup()
                                        .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpload, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                    .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMatKhau)
                                        .addComponent(txtMa)
                                        .addComponent(txtTen)
                                        .addComponent(txtSDT)
                                        .addComponent(jScrollPane2)
                                        .addComponent(txtEmail)
                                        .addComponent(cbbVaiTro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(29, 29, 29))
                            .addGroup(nhanvienLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addComponent(cbbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        nhanvienLayout.setVerticalGroup(
            nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhanvienLayout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbLoc)
                    .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(kGradientPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kGradientPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(nhanvienLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1259, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(nhanvien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMousePressed
        // TODO add your handling code here:
        fill();
    }//GEN-LAST:event_tbNhanVienMousePressed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        String checkSo = "^[+?0-9]*$";
        String checkMail = "^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-z]+\\.)+[a-z]{2,4}$";
        try {
            if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || txtQueQuan.getText().trim().isEmpty() || txtMatKhau.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn không được để trống !");
                return;
            }
            if (lbAnh == null) {
                JOptionPane.showMessageDialog(this, "Ảnh không được để trống !");
                return;
            }
            if (txtMa.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Mã nhỏ hơn hoặc bằng 10 kí tự");
                return;
            }
            if (txtTen.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Tên nhỏ hơn hoặc bằng 50 kí tự");
                return;
            }
            if (!txtSDT.getText().matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số !");
                return;
            }
            if (txtSDT.getText().length() != 10) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải bằng 10 kí tự ");
                return;
            }
            if (txtEmail.getText().length() > 30) {
                JOptionPane.showMessageDialog(this, "Email nhỏ hơn hoặc bằng 30 kí tự ");
                return;
            }
            if (!txtEmail.getText().matches(checkMail)) {
                JOptionPane.showMessageDialog(this, "Email Không dùng định dạng Example@gmail.com :))");
                return;
            }
            if (txtQueQuan.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Quê quán nhỏ hơn hoặc bằng 50 kí tự ");
                return;
            }
            NhanVien nv1 = services.seachbyMa(txtMa.getText());
            if (nv1 != null) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại !");
                return;
            }
            if (txtMatKhau.getText().length() < 8 || txtMatKhau.getText().length() > 16) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải từ 8 đến 16 kí tự !");
                return;
            }
            NhanVien nv = layTT();
            if (services.add(nv) == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công !");
                loadData(1);
                
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại !");
            }

            //            if (chiTietSPServices.add(c) == true) {
            //                JOptionPane.showMessageDialog(this, "Thêm thành công !");
            //                hienThiSanPham();
            //            } else {
            //                JOptionPane.showMessageDialog(this, "Thêm thất bại !");
            //            }
        } catch (ParseException ex) {
            Logger.getLogger(QLNhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddMousePressed

    private void btnResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMousePressed
        clear();
    }//GEN-LAST:event_btnResetMousePressed

    private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMousePressed

        int index = tbNhanVien.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
            return;
        }
        int e = tbNhanVien.getSelectedRowCount();
        if (e > 1) {
            JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        String ma = tbNhanVien.getValueAt(index, 0).toString();
        if (services.delete(ma) == true) {
            JOptionPane.showMessageDialog(this, "Xóa thành công !");
            loadData(1);
            
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại !");
        }

    }//GEN-LAST:event_btnDeleteMousePressed

    private void btnUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMousePressed
        // TODO add your handling code here:
        String checkSo = "^[+?0-9]*$";
        String checkMail = "^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-z]+\\.)+[a-z]{2,4}$";
        try {
            int index = tbNhanVien.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn bản ghi nào !");
                return;
            }
            int e = tbNhanVien.getSelectedRowCount();
            if (e > 1) {
                JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
                return;
            }
            if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || txtQueQuan.getText().trim().isEmpty() || txtMatKhau.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn không được để trống !");
                return;
            }
            if (lbAnh == null) {
                JOptionPane.showMessageDialog(this, "Ảnh không được để trống !");
                return;
            }
            if (txtMa.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Mã nhỏ hơn hoặc bằng 10 kí tự");
                return;
            }
            if (txtTen.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Tên nhỏ hơn hoặc bằng 50 kí tự");
                return;
            }
            if (!txtSDT.getText().matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số !");
                return;
            }
            if (txtSDT.getText().length() != 10) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải bằng 10 kí tự ");
                return;
            }
            if (txtEmail.getText().length() > 30) {
                JOptionPane.showMessageDialog(this, "Email nhỏ hơn hoặc bằng 30 kí tự ");
                return;
            }
            if (!txtEmail.getText().matches(checkMail)) {
                JOptionPane.showMessageDialog(this, "Email Không dùng định dạng Example@gmail.com :))");
                return;
            }
            if (txtQueQuan.getText().length() > 50) {
                JOptionPane.showMessageDialog(this, "Quê quán nhỏ hơn hoặc bằng 50 kí tự ");
                return;
            }
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa ?");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            // TODO add your handling code here:
            NhanVien nv = layTTSua();
            if (services.update(nv) == true) {
                JOptionPane.showMessageDialog(this, "Sửa thành công !");
               loadData(1);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại !");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLNhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateMousePressed

    private void btnUploadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUploadMousePressed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser("D:\\PRO1041");
        chooser.showOpenDialog(null);
        //        if (JFileChooser.CANCEL_OPTION == 1) {
        //            return;
        //        }
        File s = chooser.getSelectedFile();
        if (s == null) {
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

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void btnLocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocMousePressed
        // TODO add your handling code here:
//        String cbb = (String) cbbLoc.getSelectedItem();
//        Integer vaiTro = 0;
//        if (cbb.equals("Quản Lý")) {
//            vaiTro = 0;
//        } else {
//            vaiTro = 1;
//        }
//        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
//        model.setRowCount(0);
//        String vaiTro1 = "";
//        List<NhanVien> list = services.getALLbyVaiTro(vaiTro);
//        for (NhanVien nhanVien : list) {
//            if (nhanVien.getTrangThai() == 0) {
//                if (nhanVien.getVaiTro() == 0) {
//                    vaiTro1 = "Quản Lý";
//                } else {
//                    vaiTro1 = "Nhân Viên";
//                }
//                Object[] data = new Object[]{
//                    nhanVien.getMaNV(),
//                    nhanVien.getTenNV(),
//                    nhanVien.getSdt(),
//                    nhanVien.getEmail(),
//                    nhanVien.getQueQuan(),
//                    nhanVien.getMatKhau(),
//                    vaiTro1,};
//                model.addRow(data);
//            }
//
//        }
        check = 2;
        Loc(1);
        JOptionPane.showMessageDialog(this, "Lọc thành công !");
    }//GEN-LAST:event_btnLocMousePressed

    private void btnTimKiemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMousePressed
        // TODO add your handling code here:
        if (txtTimKiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống !");
            return;
        }
        if (txtTimKiem.getText().length() > 30) {
            JOptionPane.showMessageDialog(this, "Tên nhỏ hơn hoặc bằng 30 kí tự !");
            return;
        }
        List<NhanVien> nhanViens = services.timKiembyTrangThai(txtTimKiem.getText());
        if (nhanViens.size() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }
        check = 1;
//        pagination1.setVisible(false);
        timKiem(1);
//        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
//        model.setRowCount(0);
//        String vaiTro1 = "";
//        NhanVien nhanVien = services.seachbyMa(txtTimKiem.getText());
//        if (nhanVien.getTrangThai() == 0) {
//            if (nhanVien.getVaiTro() == 0) {
//                vaiTro1 = "Quản Lý";
//            } else {
//                vaiTro1 = "Nhân Viên";
//            }
//            Object[] data = new Object[]{
//                nhanVien.getMaNV(),
//                nhanVien.getTenNV(),
//                nhanVien.getSdt(),
//                nhanVien.getEmail(),
//                nhanVien.getQueQuan(),
//                nhanVien.getMatKhau(),
//                vaiTro1,};
//            model.addRow(data);
//        }
        JOptionPane.showMessageDialog(this, "Tìm thành công !");

    }//GEN-LAST:event_btnTimKiemMousePressed
    public void timKiem(Integer page) {
//        pagination2.setVisible(true);
        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
        model.setRowCount(0);
        Integer limit = 10;
        List<NhanVien> nhanViens = services.timKiembyTrangThai(txtTimKiem.getText());
        Integer count = nhanViens.size();
//        Integer soDu = count % 10;
//        Integer soLamTron = 0;
//        if (soDu == 0) {
//            soLamTron = count / 10;
//        }
//        if (soDu != 0) {
//            soLamTron = ((count - soDu) / 10) + 1;
//        }
        Integer toltalPage = (int) Math.ceil(count / (float) limit);

        List<NhanVien> kh = services.timKiemPhanTrang(txtTimKiem.getText(), limit, page);
        String vaiTro = "";
        for (NhanVien nhanVien : kh) {
            if (nhanVien.getTrangThai() == 0) {

                if (nhanVien.getVaiTro() == 0) {
                    vaiTro = "Quản Lý";
                } else {
                    vaiTro = "Nhân Viên";
                }
                Object[] data = new Object[]{
                    nhanVien.getMaNV(),
                    nhanVien.getTenNV(),
                    nhanVien.getSdt(),
                    nhanVien.getEmail(),
                    nhanVien.getQueQuan(),
                    nhanVien.getMatKhau(),
                    vaiTro,};
                model.addRow(data);
            }
        }
        pagination1.setPagegination(page, toltalPage);
    }
public void Loc(Integer page) {
//        pagination2.setVisible(true);
        String cbb = (String) cbbLoc.getSelectedItem();
        Integer vaiTro = 0;
        if (cbb.equals("Quản Lý")) {
            vaiTro = 0;
        } else {
            vaiTro = 1;
        }
        DefaultTableModel model = (DefaultTableModel) tbNhanVien.getModel();
        model.setRowCount(0);
        Integer limit = 10;
        List<NhanVien> nhanViens = services.getALLbyVaiTro(vaiTro);
        Integer count = nhanViens.size();
//        Integer soDu = count % 10;
//        Integer soLamTron = 0;
//        if (soDu == 0) {
//            soLamTron = count / 10;
//        }
//        if (soDu != 0) {
//            soLamTron = ((count - soDu) / 10) + 1;
//        }
        Integer toltalPage = (int) Math.ceil(count / (float) limit);

        List<NhanVien> kh = services.locPhanTrang(vaiTro, limit, page);
        String vaiTro1 = "";
        for (NhanVien nhanVien : kh) {
            if (nhanVien.getTrangThai() == 0) {

                if (nhanVien.getVaiTro() == 0) {
                    vaiTro1 = "Quản Lý";
                } else {
                    vaiTro1 = "Nhân Viên";
                }
                Object[] data = new Object[]{
                    nhanVien.getMaNV(),
                    nhanVien.getTenNV(),
                    nhanVien.getSdt(),
                    nhanVien.getEmail(),
                    nhanVien.getQueQuan(),
                    nhanVien.getMatKhau(),
                    vaiTro1,};
                model.addRow(data);
            }
        }
        pagination1.setPagegination(page, toltalPage);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnLoc;
    private javax.swing.JLabel btnReset;
    private javax.swing.JLabel btnTimKiem;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JLabel btnUpload;
    private javax.swing.JComboBox<String> cbbLoc;
    private javax.swing.JComboBox<String> cbbVaiTro;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JPanel nhanvien;
    private pagination.Pagination pagination1;
    private javax.swing.JTable tbNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMa;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextArea txtQueQuan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
