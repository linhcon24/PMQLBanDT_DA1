/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import domainmodels.BaoHanhChiTiet;
import domainmodels.ChiTietSP;
import domainmodels.HoaDon;
import domainmodels.HoaDonChiTiet;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import services.ChiTietSPServices;
import services.HoaDonChiTietServices;
import services.IChiTietSPServices;
import services.IHoaDonChiTietServies;
import services.IQLBaoHanhChiTietServices;
import services.IQLBaoHanhServices;
import services.QLBaoHanhChiTietServices;
import services.QLBaoHanhServices;
import static views.QLBanHangPanel.tbGioHang;

/**
 *
 * @author HANGOCHAN
 */
public class QLBaoHanhPanel extends javax.swing.JPanel implements Runnable, ThreadFactory {

    /**
     * Creates new form QLBaoHanhPanel
     */
    private WebcamPanel panel1 = null;
    private static Webcam webcam1 = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private IQLBaoHanhServices baoHanhServices;
    private IQLBaoHanhChiTietServices baoHanhChiTietServices;
    private IChiTietSPServices chiTietSPServices;
    private IHoaDonChiTietServies hoaDonChiTietServies;

    public QLBaoHanhPanel() {
        initComponents();
        initWebcam();
        baoHanhServices = new QLBaoHanhServices();
        baoHanhChiTietServices = new QLBaoHanhChiTietServices();
        chiTietSPServices = new ChiTietSPServices();
        hoaDonChiTietServies = new HoaDonChiTietServices();
        cbbImei.removeAllItems();
    }
     public static void windowClosed() {
        if (webcam1 == null) {
            return;
        }
        webcam1.close();
        
    }

    private void initWebcam() {

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam1 = Webcam.getWebcams().get(0);
        webcam1.setViewSize(size);

        panel1 = new WebcamPanel(webcam1);
        panel1.setPreferredSize(size);
        panel1.setFPSDisplayed(true);
        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 250));
        executor.execute(this);

    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QLBaoHanhPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image = null;

            if (webcam1.isOpen()) {
                if ((image = webcam1.getImage()) == null) {
                    continue;
                }
            }
            if (image == null) {
                continue;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                //khong co du lieu
            }
            if (result != null) {
                try {                  
                    BaoHanhChiTiet bhct = baoHanhChiTietServices.seachbyMa(result.getText());
                    if (bhct == null) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm này không được bảo hành !");
                        continue;
                    }
                    ChiTietSP chiTietSP1 = chiTietSPServices.seachbyMa(result.getText());
                    if (chiTietSP1.getTrangThai() == 3) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm này đã được bảo hành !");
                        continue;
                    }
                    ChiTietSP c = chiTietSPServices.seachbyMa(result.getText());
                    String maSP = c.getSanPham().getMaSP();
                    String tenSP = c.getSanPham().getTenSP();
                    String maimei = c.getMaImei();
                    Date date = bhct.getNgayBatDau();
                    String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
                    txtNgayBatDau.setText(sdf);
                    Date date1 = bhct.getNgayKetThuc();
                    String sdf1 = new SimpleDateFormat("dd/MM/yyyy").format(date1);
                    txtNgayKetThuc.setText(sdf1);
                    String tranghThai = "Còn bảo hành";
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    ZonedDateTime now = ZonedDateTime.now();
                    String ngayTao = dtf.format(now);
                    Date date3 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayTao);

                    if (date1.after(date3) == false) {
                        tranghThai = "Hết bảo hành";
                        btnBaoHanh.setVisible(false);
                        cbbImei.setEnabled(false);
                        cbbImei.setEditable(false);
                    }
                    List<ChiTietSP> list = chiTietSPServices.count(maSP);
                    for (ChiTietSP chiTietSP : list) {
                        cbbImei.addItem(chiTietSP.getMaImei());
                    }
                    txtMa.setText(maSP);
                    txtTen.setText(tenSP);
                    txtMaImei.setText(maimei);
                    txtTrangThai.setText(tranghThai);
                    ChiTietSP c1 = chiTietSPServices.fill(maSP);
                    if (c.getAnh() == null) {
                        lbAnh.setIcon(null);
                    } else {
                        ImageIcon icon = new ImageIcon(c1.getAnh());
                        Image image1 = icon.getImage().getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon m = new ImageIcon(image1);
                        lbAnh.setIcon(m);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(QLBaoHanhPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;

    }

    public void clear() {
        txtMa.setText("");
        txtTen.setText("");
        txtMaImei.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtTrangThai.setText("");
        lbAnh.setIcon(null);
        cbbImei.removeAllItems();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        baohanh = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtImei = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnCheck = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        btnBaoHanh = new keeptoo.KGradientPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbbImei = new javax.swing.JComboBox<>();
        txtMa = new javax.swing.JLabel();
        txtTen = new javax.swing.JLabel();
        txtMaImei = new javax.swing.JLabel();
        txtNgayBatDau = new javax.swing.JLabel();
        txtNgayKetThuc = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        baohanh.setBackground(new java.awt.Color(255, 255, 255));
        baohanh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        baohanh.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 130, 410, 250));
        baohanh.add(txtImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 230, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Imei");
        baohanh.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 40, 30));

        btnCheck.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCheck.setForeground(new java.awt.Color(255, 255, 255));
        btnCheck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCheck.setText("CHECK");
        btnCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCheckMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        baohanh.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 80, 30));

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 26)); // NOI18N
        jLabel2.setText("Thông Tin Bảo Hành");
        baohanh.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 250, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Tên Sản Phẩm : ");
        baohanh.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 130, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Mã Imei : ");
        baohanh.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, 90, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Ngày Bắt Ðầu : ");
        baohanh.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Ngày Kết Thúc : ");
        baohanh.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 130, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Trạng Thái : ");
        baohanh.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 100, -1));

        lbAnh.setText("Image");
        baohanh.add(lbAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, 260, 290));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_protect_30px.png"))); // NOI18N
        jLabel12.setText("Bảo Hành");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel12MousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnBaoHanhLayout = new javax.swing.GroupLayout(btnBaoHanh);
        btnBaoHanh.setLayout(btnBaoHanhLayout);
        btnBaoHanhLayout.setHorizontalGroup(
            btnBaoHanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        btnBaoHanhLayout.setVerticalGroup(
            btnBaoHanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        baohanh.add(btnBaoHanh, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 600, 140, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Mã Sản Phẩm : ");
        baohanh.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, 120, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Imei Bảo Hành : ");
        baohanh.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 140, 30));

        cbbImei.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        baohanh.add(cbbImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 520, 200, 30));

        txtMa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 150, 20));

        txtTen.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 160, 20));

        txtMaImei.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtMaImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 150, 20));

        txtNgayBatDau.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtNgayBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 140, 20));

        txtNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtNgayKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, 140, 20));

        txtTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        baohanh.add(txtTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, 160, 20));

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 3, 14)); // NOI18N
        jLabel8.setText("Quét với QR-CODE/BAR-CODE");
        baohanh.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 230, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(baohanh, javax.swing.GroupLayout.PREFERRED_SIZE, 1259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(baohanh, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckMousePressed
        try {
            // TODO add your handling code here:
            String checkSo = "^[0-9]*$";
            if (txtImei.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn không được để trống !");
                return;
            }
            if (!txtImei.getText().matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Imei phải là số !");
                return;
            }
            if (txtImei.getText().length() != 15) {
                JOptionPane.showMessageDialog(this, "Imei phải 15 kí tự ");
                return;
            }

           
            BaoHanhChiTiet bhct = baoHanhChiTietServices.seachbyMa(txtImei.getText());
            if (bhct == null) {
                JOptionPane.showMessageDialog(this, "Sản phẩm này không được bảo hành !");
                return;
            }
             ChiTietSP chiTietSP1 = chiTietSPServices.seachbyMa(txtImei.getText());
            if (chiTietSP1.getTrangThai() == 3) {
                JOptionPane.showMessageDialog(this, "Sản phẩm này đã được bảo hành !");
                return;
            }
            ChiTietSP c = chiTietSPServices.seachbyMa(txtImei.getText());
            String maSP = c.getSanPham().getMaSP();
            String tenSP = c.getSanPham().getTenSP();
            String maimei = c.getMaImei();
            Date date = bhct.getNgayBatDau();
            String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
            txtNgayBatDau.setText(sdf);
            Date date1 = bhct.getNgayKetThuc();
            String sdf1 = new SimpleDateFormat("dd/MM/yyyy").format(date1);
            txtNgayKetThuc.setText(sdf1);
            String tranghThai = "Còn bảo hành";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngayTao = dtf.format(now);
            Date date3 = new SimpleDateFormat("dd/MM/yyyy").parse(ngayTao);

            if (date1.after(date3) == false) {
                tranghThai = "Hết bảo hành";
                btnBaoHanh.setVisible(false);
                cbbImei.setEnabled(false);
                cbbImei.setEditable(false);
            }
            List<ChiTietSP> list = chiTietSPServices.count(maSP);
            for (ChiTietSP chiTietSP : list) {
                cbbImei.addItem(chiTietSP.getMaImei());
            }
            txtMa.setText(maSP);
            txtTen.setText(tenSP);
            txtMaImei.setText(maimei);
            txtTrangThai.setText(tranghThai);
            ChiTietSP c1 = chiTietSPServices.fill(maSP);
            if (c.getAnh() == null) {
                lbAnh.setIcon(null);
            } else {
                ImageIcon icon = new ImageIcon(c1.getAnh());
                Image image = icon.getImage().getScaledInstance(lbAnh.getWidth(), lbAnh.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon m = new ImageIcon(image);
                lbAnh.setIcon(m);
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLBaoHanhPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCheckMousePressed

    private void jLabel12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MousePressed
        // TODO add your handling code here:
        if (txtMaImei.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu cần bảo hành !");
            return;
        }
        if (cbbImei.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Xin lỗi, hiện tại không có sản phẩm bảo hành thay thế !");
            return;
        }
        String ImeiMoi = (String) cbbImei.getSelectedItem();
        int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc bảo hành sản phẩm có Imei : "+txtMaImei.getText()+" sang Imei : "+ImeiMoi);
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        HoaDonChiTiet hdct = hoaDonChiTietServies.seachbyMaImei(txtMaImei.getText());
        HoaDonChiTiet hdct1 = new HoaDonChiTiet();
        
        hoaDonChiTietServies.updateImei(hdct.getMaHD().getMaHD(), txtMaImei.getText(), ImeiMoi);
        chiTietSPServices.updateImeiTrangThai(ImeiMoi, 2);
        chiTietSPServices.updateImeiTrangThai(txtMaImei.getText(), 3);
        baoHanhChiTietServices.xoaBaoHanh(txtMaImei.getText());
        JOptionPane.showMessageDialog(this, "Bảo hành thành công !");
        clear();
    }//GEN-LAST:event_jLabel12MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel baohanh;
    private keeptoo.KGradientPanel btnBaoHanh;
    private javax.swing.JLabel btnCheck;
    private javax.swing.JComboBox<String> cbbImei;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JTextField txtImei;
    private javax.swing.JLabel txtMa;
    private javax.swing.JLabel txtMaImei;
    private javax.swing.JLabel txtNgayBatDau;
    private javax.swing.JLabel txtNgayKetThuc;
    private javax.swing.JLabel txtTen;
    private javax.swing.JLabel txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
