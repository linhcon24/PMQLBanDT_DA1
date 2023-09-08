/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainmodels.KhuyenMai;
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
import services.IQLKhuyenMaiServices;
import services.QLKhuyenMaiServices;

/**
 *
 * @author HANGOCHAN
 */
public class QLKhuyenMaiPanel extends javax.swing.JPanel {

    /**
     * Creates new form QLKhuyenMaiPanel
     */
    private IQLKhuyenMaiServices khuyenMaiServices;

    public QLKhuyenMaiPanel() {
        try {
            initComponents();
            khuyenMaiServices = new QLKhuyenMaiServices();
            load();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngay = dtf.format(now);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngay);
            List<KhuyenMai> khuyenMais = khuyenMaiServices.getAll();
            if (khuyenMais.size() == 0) {
                return;
            }
            for (KhuyenMai khuyenMai : khuyenMais) {
                if (khuyenMai.getNgayKetThuc().after(date) == false) {
                    khuyenMaiServices.updatebyTrangThai(khuyenMai.getMaKM(), 1);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLKhuyenMaiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadMaKM() {

        String ma = "";
        List<KhuyenMai> khuyenMais = khuyenMaiServices.getAll();
        if (khuyenMais.size() == 0) {
            ma = "KM0";
        } else {
            KhuyenMai sp = khuyenMaiServices.layMa();
//          
            ma = sp.getMaKM();
        }

        String mangString[] = ma.split("");
        String so = "";
        for (int i = 2; i < mangString.length; i++) {
            so += mangString[i];
        }

        Integer sofinal = Integer.parseInt(so) + 1;
        String maMoi = "KM" + sofinal;
        txtMa.setText(maMoi);
    }

    public void load() {
        loadMaKM();
        DefaultTableModel model = (DefaultTableModel) tbbang.getModel();
        model.setRowCount(0);
        List<KhuyenMai> khuyenMais = khuyenMaiServices.getAll();
        for (KhuyenMai khuyenMai : khuyenMais) {
            String trangThai = "";
            if (khuyenMai.getTrangThai() == 0) {
                trangThai = "Đang Khuyến Mãi";
            } else {
                trangThai = "Dừng Khuyến Mãi";
            }
            Date date = khuyenMai.getNgayBatDau();
            String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
            Date date1 = khuyenMai.getNgayKetThuc();
            String sdf1 = new SimpleDateFormat("dd/MM/yyyy").format(date1);
            if (khuyenMai.getTrangThai() !=2) {
                Object[] data = new Object[]{
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getChietKhau(),
                sdf,
                sdf1,
                trangThai
            };
            model.addRow(data);
            }

        }

    }

    public void clear() {
        load();
        txttenkm.setText("");
        cbbtrietkhau.setSelectedItem(10);
        txtngaybatdau.setDate(null);
        txtngayketthuc.setDate(null);
        cbbtrangthai.setSelectedIndex(0);

    }

    public KhuyenMai LayTT() throws ParseException {
        String maKM = txtMa.getText();
        String tenKM = txttenkm.getText();
        String chietKhau = (String) cbbtrietkhau.getSelectedItem();
        Integer ck = Integer.parseInt(chietKhau);

        DateTimeFormatter tdf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayBatDau = tdf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayBatDau);
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime data = ZonedDateTime.now();
        String ngayKetThuc = ft.format(data);
        Date ngay = new SimpleDateFormat("MM-dd-yyyy").parse(ngayKetThuc);
        String trangThaiStr = (String) cbbtrangthai.getSelectedItem();
        Integer trangThai = 0;
        if (trangThaiStr.equals("Đang Khuyến Mãi")) {
            trangThai = 0;
        } else {
            trangThai = 1;
        }
        KhuyenMai km = new KhuyenMai();
        km.setMaKM(maKM);
        km.setTenKM(tenKM);
        km.setChietKhau(ck);
        km.setNgayBatDau(date);
        km.setNgayKetThuc(ngay);
        km.setTrangThai(trangThai);
        return km;

    }

    public void fill() throws ParseException {
        int index = tbbang.getSelectedRow();
        String ma = tbbang.getValueAt(index, 0).toString();
        String ten = tbbang.getValueAt(index, 1).toString();
        String ChietKhau = tbbang.getValueAt(index, 2).toString();
        cbbtrietkhau.setSelectedItem(ChietKhau);
        String ngayBatDau = tbbang.getValueAt(index, 3).toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayBatDau);
        String ngayKetThuc = tbbang.getValueAt(index, 4).toString();
        Date ngayKT = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThuc);
        String trangThai = tbbang.getValueAt(index, 5).toString();
        cbbtrangthai.setSelectedItem(trangThai);

        txtMa.setText(ma);
        txttenkm.setText(ten);

        txtngaybatdau.setDate(date);
        txtngayketthuc.setDate(ngayKT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        khuyenmai = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbbang = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txttenkm = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnThem = new keeptoo.KGradientPanel();
        btnAdd = new javax.swing.JLabel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        btnReset = new javax.swing.JLabel();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        btnDelete = new javax.swing.JLabel();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        btnUpdate = new javax.swing.JLabel();
        cbbtrietkhau = new javax.swing.JComboBox<>();
        txtngaybatdau = new com.toedter.calendar.JDateChooser();
        txtngayketthuc = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        cbbtrangthai = new javax.swing.JComboBox<>();
        txtMa = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();

        khuyenmai.setBackground(new java.awt.Color(255, 255, 255));

        tbbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Chiết khấu ", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbbang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbbangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbbangMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbbangMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbbang);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Mã KM");

        txttenkm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenkmActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tên KM");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Ngày Bắt Đầu");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Ngày  Kết Thúc");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Chiết Khấu");

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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAddMouseReleased(evt);
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

        cbbtrietkhau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100", "90", "80", "70", "60", "50", "40", "30", "20", "10" }));
        cbbtrietkhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbtrietkhauActionPerformed(evt);
            }
        });

        txtngaybatdau.setDateFormatString("dd/MM/yyyy");

        txtngayketthuc.setDateFormatString("dd/MM/yyyy");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Trạng Thái");

        cbbtrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Khuyến Mãi", "Dừng Khuyến Mãi" }));

        txtMa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMa.setText("jLabel1");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_search_30px.png"))); // NOI18N
        jLabel1.setText("Tìm theo mã");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout khuyenmaiLayout = new javax.swing.GroupLayout(khuyenmai);
        khuyenmai.setLayout(khuyenmaiLayout);
        khuyenmaiLayout.setHorizontalGroup(
            khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khuyenmaiLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(khuyenmaiLayout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(khuyenmaiLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(khuyenmaiLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(khuyenmaiLayout.createSequentialGroup()
                                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel28))
                                        .addGap(48, 48, 48)
                                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbbtrietkhau, 0, 208, Short.MAX_VALUE)
                                            .addComponent(txttenkm)
                                            .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(khuyenmaiLayout.createSequentialGroup()
                                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26)
                                            .addComponent(jLabel25))
                                        .addGap(23, 23, 23)
                                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtngaybatdau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(khuyenmaiLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(49, 49, 49)
                                        .addComponent(cbbtrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(khuyenmaiLayout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        khuyenmaiLayout.setVerticalGroup(
            khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khuyenmaiLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khuyenmaiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(khuyenmaiLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttenkm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbtrietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbtrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(khuyenmaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(kGradientPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kGradientPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khuyenmaiLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(khuyenmai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(khuyenmai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbbangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbbangMouseClicked

    }//GEN-LAST:event_tbbangMouseClicked

    private void tbbangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbbangMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbangMousePressed

    private void tbbangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbbangMouseReleased

        try {
            // TODO add your handling code here:
            fill();
        } catch (ParseException ex) {
            Logger.getLogger(QLKhuyenMaiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbbangMouseReleased

    private void txttenkmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenkmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenkmActionPerformed

    private void btnAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseExited

    }//GEN-LAST:event_btnAddMouseExited

    private void btnAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMousePressed
        try {
            // TODO add your handling code here:
            if (txtMa.getText().trim().isEmpty() || txttenkm.getText().trim().isEmpty() || txtngaybatdau.getDate() == null || txtngayketthuc.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Không được bỏ trống !");
                return;

            }
            if (txttenkm.getText().length() > 30) {
                JOptionPane.showMessageDialog(this, "Tên khuyến mãi nhỏ hơn 30 kí tự !");
                return;
            }
            if (txtngaybatdau.getDate().after(txtngayketthuc.getDate())) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc !");
                return;
            }
            KhuyenMai km = LayTT();
//            if (khuyenMaiServices.seachbyMa(km.getMaKM()) != null) {
//                JOptionPane.showMessageDialog(this, "Mã đã tồn tại !");
//                return;
//            }
            if (khuyenMaiServices.add(km) == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                load();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddMousePressed

    private void btnAddMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_btnAddMouseReleased

    private void btnResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMousePressed
        //        String ma = txtnakm.getText();
        //        KhuyenMai n = khuyenMaiServices.seachbyMa(ma);
        //        if (ma.trim().isEmpty()) {
        //            JOptionPane.showMessageDialog(this, "Nhập mã trước khi tìm kiếm !");
        //            load();
        //            return;
        //        }
        //        if (n == null) {
        //            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
        //            return;
        //        }
        //
        //        DefaultTableModel model = (DefaultTableModel) tbbang.getModel();
        //        model.setRowCount(0);
        //        if (n.getTrangThai() == 0) {
        //            JOptionPane.showMessageDialog(this, "Tìm thành công !");
        //
        //            Object[] data = new Object[]{
        //                n.getMaKM(),
        //                n.getTenKM(),
        //                n.getChietKhau(),
        //                n.getNgayBatDau(),
        //                n.getNgayKetThuc(),
        //                n.getTrangThai()
        //            };
        //            model.addRow(data);
        //        } else {
        //            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
        //            return;
        //
        //        }
        clear();

    }//GEN-LAST:event_btnResetMousePressed

    private void btnDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMousePressed
        

            int index = tbbang.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã cần xoá ");
                return;
            }
            int i = tbbang.getSelectedRowCount();
            if (i > 1) {
                JOptionPane.showMessageDialog(this, "Bạn chỉ được chọn 1 bản ghi !");
                return;
            }
            int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá không");
            if (xacNhan != JOptionPane.YES_OPTION) {
                return;
            }
            String ma = tbbang.getValueAt(index, 0).toString();
     
           khuyenMaiServices.updatebyTrangThai(ma,2) ;
                JOptionPane.showMessageDialog(this, "Xoá Thành Công");
                load();
            
       
    }//GEN-LAST:event_btnDeleteMousePressed

    private void btnUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMousePressed
        try {
            // TODO add your handling code here:
            KhuyenMai k = LayTT();
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không ?");
            if (check != JOptionPane.YES_OPTION) {
                return;

            }
            if (khuyenMaiServices.update(k) == true) {
                JOptionPane.showMessageDialog(this, "Sua Thành Công");
                load();
            } else {
                JOptionPane.showMessageDialog(this, "Sua That Bai");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLKhuyenMai.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnUpdateMousePressed

    private void cbbtrietkhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbtrietkhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbtrietkhauActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
        if (txtTimKiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng không được để trống !");
            return;
        }
        if (txtTimKiem.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã nhỏ hơn hoặc bằng 10 kí tự !");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tbbang.getModel();
        model.setRowCount(0);
        KhuyenMai khuyenMai = khuyenMaiServices.seachbyMa(txtTimKiem.getText());
        if (khuyenMai == null) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }
        if (khuyenMai.getTrangThai() == 2) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }
        String trangThai = "";
        if (khuyenMai.getTrangThai() == 0) {
            trangThai = "Đang Khuyến Mãi";
        } else {
            trangThai = "Dừng Khuyến Mãi";
        }
        if (khuyenMai.getTrangThai() != 2) {
            Date date = khuyenMai.getNgayBatDau();
            String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
            Date date1 = khuyenMai.getNgayKetThuc();
            String sdf1 = new SimpleDateFormat("dd/MM/yyyy").format(date1);
            Object[] data = new Object[]{
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getChietKhau(),
                sdf,
                sdf1,
                trangThai
            };
            model.addRow(data);
        }

        JOptionPane.showMessageDialog(this, "Tìm thành công !");
    }//GEN-LAST:event_jLabel1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnReset;
    private keeptoo.KGradientPanel btnThem;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JComboBox<String> cbbtrangthai;
    private javax.swing.JComboBox<String> cbbtrietkhau;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JPanel khuyenmai;
    private static javax.swing.JTable tbbang;
    private javax.swing.JLabel txtMa;
    private javax.swing.JTextField txtTimKiem;
    private com.toedter.calendar.JDateChooser txtngaybatdau;
    private com.toedter.calendar.JDateChooser txtngayketthuc;
    private javax.swing.JTextField txttenkm;
    // End of variables declaration//GEN-END:variables
}
