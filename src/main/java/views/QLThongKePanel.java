/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import domainmodels.HoaDon;
import domainmodels.KhuyenMai;
import domainmodels.SanPham;
import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import services.HoaDonChiTietServices;
import services.HoaDonServices;
import services.IHoaDonChiTietServies;
import services.IHoaDonServices;
import services.IKhachHangService;
import services.IQLKhuyenMaiServices;
import services.IQLSanPhamServices;
import services.KhachHangService;
import services.QLKhuyenMaiServices;
import services.QLSanPhamServices;
import viewmodels.ThongKeDoanhThu;
import viewmodels.ThongKeDoanhThuTheoDanhMuc;
import viewmodels.ThongKeKhachHang;
import viewmodels.ThongKeSanPham;
import viewmodels.ThongKeSoLuongTheoDanhMuc;

/**
 *
 * @author HANGOCHAN
 */
public class QLThongKePanel extends javax.swing.JPanel {

    /**
     * Creates new form QLThongKePanel
     */
    private IHoaDonServices hoaDonServices;
    private IHoaDonChiTietServies hoaDonChiTietServies;
    private IQLSanPhamServices sanPhamServices;
    private IKhachHangService khachHangService;
    private IQLKhuyenMaiServices khuyenMaiServices;

    public QLThongKePanel() {
        initComponents();

        hoaDonServices = new HoaDonServices();
        hoaDonChiTietServies = new HoaDonChiTietServices();
        sanPhamServices = new QLSanPhamServices();
        khachHangService = new KhachHangService();
        khuyenMaiServices = new QLKhuyenMaiServices();
        hienThiTop5TheoNgay();
        hienThiDoanhThu();
        txtDate.setMaxSelectableDate(new Date());
        cbbThang.setVisible(false);
            cbbNam.setVisible(false);
            btnThongKeThang.setVisible(false);
            btnThongKeNam.setVisible(false);
           
    }

    public void hienThiTop5TheoNgay() {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
            model.setRowCount(0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngayHomNay = dtf.format(now);

            Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayHomNay);
            List<ThongKeSanPham> thongKeSanPhams = hoaDonChiTietServies.thongKeSoLuongDaBanTheoNgay(date);
            Integer soLuongDaBan = 0;
            for (ThongKeSanPham thongKeSanPham : thongKeSanPhams) {
                soLuongDaBan += thongKeSanPham.getSoLuongBan().intValue();
            }
            txtSLDaBan.setText(String.valueOf(soLuongDaBan));
            List<ThongKeSanPham> sanPhams = hoaDonChiTietServies.thongKeTheoNgay(date);
            for (ThongKeSanPham sanPham : sanPhams) {
                dataset.setValue(sanPham.getSoLuongBan(), "Quantity", sanPham.getTenSP());
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getSoLuongBan()
                };
                model.addRow(data);
            }
            JFreeChart chart = ChartFactory.createBarChart("Số Lượng Bán ", "", "Số lượng bán", dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color rColor3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, rColor3);
            ChartPanel barpChartPanel = new ChartPanel(chart);
            jPanel1.removeAll();
            jPanel1.add(barpChartPanel, BorderLayout.CENTER);
            jPanel1.validate();
            DefaultTableModel model1 = (DefaultTableModel) tbKhachHang.getModel();
            model1.setRowCount(0);
            List<ThongKeKhachHang> thongKeKhachHangs = khachHangService.thongKeTheoNgay(date);
            for (ThongKeKhachHang thongKeKhachHang : thongKeKhachHangs) {
                Object[] data = new Object[]{
                    thongKeKhachHang.getMaKH(),
                    thongKeKhachHang.getTenKH(),
                    thongKeKhachHang.getSoLanMua()
                };
                model1.addRow(data);
            }

        } catch (ParseException ex) {
            Logger.getLogger(QLThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hienThiDoanhThu() {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngayHomNay = dtf.format(now);

            Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayHomNay);
            String date1 = new SimpleDateFormat("dd/MM/yyyy").format(date);
            txtThongKe.setText("Thống kê ngày " + date1);
            List<HoaDon> hoaDons = hoaDonServices.countHoaDontheoNgay(2, date);
            txtSoLuongDaBan.setText(String.valueOf(hoaDons.size()));

            List<HoaDon> b = hoaDonServices.countHoaDonHuytheoNgay(1, date);
            txtHoaDonHuy.setText(String.valueOf(b.size()));
            List<ThongKeDoanhThu> list = hoaDonChiTietServies.thongKeDoanhThuTheoNgay(date);
            String doanhthuString = "";
            Double doanhThudb = 0.0;
            String giamGiaString = "";
            Double giamGiadb = 0.0;
            Double doanhThudb1 = 0.0;

            for (ThongKeDoanhThu thongKeDoanhThu : list) {

                dataset.setValue(thongKeDoanhThu.getDoanhThu().subtract(thongKeDoanhThu.getGiamGia()), "Price", thongKeDoanhThu.getTenSP());
                doanhthuString = String.valueOf(thongKeDoanhThu.getDoanhThu());
                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
                doanhThudb += Double.parseDouble(doanhthuString);
                giamGiadb += Double.parseDouble(giamGiaString);
//                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
//                giamGiadb += Double.parseDouble(giamGiaString);
            }
            Long doanhThu = doanhThudb.longValue() - giamGiadb.longValue();
            txtDoanhThu.setText(String.valueOf(doanhThu));

            JFreeChart chart = ChartFactory.createBarChart("Doanh Thu", "", "Tiền doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color rColor3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, rColor3);
            ChartPanel barpChartPanel = new ChartPanel(chart);
            jPanel4.removeAll();
            jPanel4.add(barpChartPanel, BorderLayout.CENTER);
            jPanel4.validate();
            
            
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuTheoDanhMucs = hoaDonChiTietServies.thongKeDoanhThuDanhMucTheoNgay(date);
            for (ThongKeDoanhThuTheoDanhMuc thongKeDoanhThuTheoDanhMuc : thongKeDoanhThuTheoDanhMucs) {
                pieDataset.setValue(thongKeDoanhThuTheoDanhMuc.getTenDM(),thongKeDoanhThuTheoDanhMuc.getTongTien().subtract(thongKeDoanhThuTheoDanhMuc.getGiamGia()));
            }
            JFreeChart chart1 = ChartFactory.createPieChart("Doanh thu theo danh mục", pieDataset, false, true, false);
            PiePlot piePlot = (PiePlot) chart1.getPlot();
            piePlot.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel = new ChartPanel(chart1);
            jPanel6.removeAll();
            jPanel6.add(pieChartPanel,BorderLayout.CENTER);
            jPanel6.validate();
            
            
            DefaultPieDataset pieDataset1 = new DefaultPieDataset();
            List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongTheoDanhMucs = hoaDonChiTietServies.thongKeSoLuongDanhMucTheoNgay(date);
            for (ThongKeSoLuongTheoDanhMuc thongKeSoLuongTheoDanhMuc : thongKeSoLuongTheoDanhMucs) {
                pieDataset1.setValue(thongKeSoLuongTheoDanhMuc.getTenDM(),thongKeSoLuongTheoDanhMuc.getSoLuong().intValue());
            }
            JFreeChart chart2 = ChartFactory.createPieChart("Số lượng đã bán theo danh mục", pieDataset1, false, true, false);
            PiePlot piePlot2 = (PiePlot) chart2.getPlot();
            piePlot2.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel2 = new ChartPanel(chart2);
            jPanel8.removeAll();
            jPanel8.add(pieChartPanel2,BorderLayout.CENTER);
            jPanel8.validate();

        } catch (ParseException ex) {
            Logger.getLogger(QLThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
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

        thongke = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuongDaBan = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel7 = new javax.swing.JLabel();
        txtHoaDonHuy = new javax.swing.JLabel();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jLabel9 = new javax.swing.JLabel();
        txtDoanhThu = new javax.swing.JLabel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSLDaBan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        cbbThongKe = new javax.swing.JComboBox<>();
        txtThongKe = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtDate = new com.toedter.calendar.JDateChooser();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        btnTheoNgay = new javax.swing.JLabel();
        cbbThang = new javax.swing.JComboBox<>();
        cbbNam = new javax.swing.JComboBox<>();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        btnThongKeThang = new javax.swing.JLabel();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        btnThongKeNam = new javax.swing.JLabel();

        thongke.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_tips_50px.png"))); // NOI18N

        txtSoLuongDaBan.setFont(new java.awt.Font("Bahnschrift", 1, 22)); // NOI18N
        txtSoLuongDaBan.setForeground(new java.awt.Color(255, 255, 255));
        txtSoLuongDaBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSoLuongDaBan.setText("0");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuongDaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(txtSoLuongDaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_cancel_subscription_50px.png"))); // NOI18N

        txtHoaDonHuy.setFont(new java.awt.Font("Bahnschrift", 1, 22)); // NOI18N
        txtHoaDonHuy.setForeground(new java.awt.Color(255, 255, 255));
        txtHoaDonHuy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtHoaDonHuy.setText("0");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoaDonHuy, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtHoaDonHuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Banknotes_50px.png"))); // NOI18N

        txtDoanhThu.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        txtDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        txtDoanhThu.setText("0");

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(txtDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Price_Tag_Euro_50px.png"))); // NOI18N

        txtSLDaBan.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        txtSLDaBan.setForeground(new java.awt.Color(255, 255, 255));
        txtSLDaBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSLDaBan.setText("0");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSLDaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(txtSLDaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel1.setText("Tổng hóa đơn đã bán");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel2.setText("Tổng hóa đơn đã hủy");

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel3.setText("Tổng doanh thu");

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel4.setText("Tổng số lượng đã bán");

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng đã bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel6.setText("TOP 5 Sản phẩm bán chạy nhất");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel8.setText("TOP 5 Khách hàng mua nhiều nhất");

        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã KH", "Tên KH", "Số lần mua"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbKhachHang);

        cbbThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày", "Theo tháng", "Theo năm" }));
        cbbThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbThongKeActionPerformed(evt);
            }
        });

        txtThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtThongKe.setText("jLabel10");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Doanh thu từng sản phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 942, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Số lượng đã bán từng sản phẩm", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Doanh thu theo danh mục", jPanel5);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Số lượng đã bán theo danh mục", jPanel7);

        txtDate.setDateFormatString("dd/MM/yyyy");

        btnTheoNgay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTheoNgay.setForeground(new java.awt.Color(255, 255, 255));
        btnTheoNgay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTheoNgay.setText("Thống Kê");
        btnTheoNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTheoNgayMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTheoNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        cbbThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThongKeThang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKeThang.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKeThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThongKeThang.setText("Thống kê");
        btnThongKeThang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeThangMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel6Layout = new javax.swing.GroupLayout(kGradientPanel6);
        kGradientPanel6.setLayout(kGradientPanel6Layout);
        kGradientPanel6Layout.setHorizontalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKeThang, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        kGradientPanel6Layout.setVerticalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKeThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnThongKeNam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKeNam.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKeNam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThongKeNam.setText("Thống Kê");
        btnThongKeNam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeNamMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel7Layout = new javax.swing.GroupLayout(kGradientPanel7);
        kGradientPanel7.setLayout(kGradientPanel7Layout);
        kGradientPanel7Layout.setHorizontalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKeNam, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
        );
        kGradientPanel7Layout.setVerticalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongKeNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout thongkeLayout = new javax.swing.GroupLayout(thongke);
        thongke.setLayout(thongkeLayout);
        thongkeLayout.setHorizontalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thongkeLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(thongkeLayout.createSequentialGroup()
                        .addComponent(cbbThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(cbbThang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(thongkeLayout.createSequentialGroup()
                        .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(thongkeLayout.createSequentialGroup()
                                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(thongkeLayout.createSequentialGroup()
                                    .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(41, 41, 41)
                                    .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        thongkeLayout.setVerticalGroup(
            thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thongkeLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbThang)
                    .addComponent(cbbNam)
                    .addComponent(kGradientPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thongke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTheoNgayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTheoNgayMousePressed
        try {
            if (txtDate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trước khi thống kê !");
                return;
            }
           
            // TODO add your handling code here:
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            String date = new SimpleDateFormat("MM-dd-yyyy").format(txtDate.getDate());
            Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(date);
            String date1 = new SimpleDateFormat("dd/MM/yyyy").format(date2);
            txtThongKe.setText("Thống kê ngày " + date1);
            List<HoaDon> hoaDons = hoaDonServices.countHoaDontheoNgay(2, date2);
            txtSoLuongDaBan.setText(String.valueOf(hoaDons.size()));

            List<HoaDon> b = hoaDonServices.countHoaDonHuytheoNgay(1, date2);
            txtHoaDonHuy.setText(String.valueOf(b.size()));
            List<ThongKeDoanhThu> list = hoaDonChiTietServies.thongKeDoanhThuTheoNgay(date2);
            String doanhthuString = "";
            Double doanhThudb = 0.0;
            String giamGiaString = "";
            Double giamGiadb = 0.0;
            Double doanhThudb1 = 0.0;

            for (ThongKeDoanhThu thongKeDoanhThu : list) {

                dataset.setValue(thongKeDoanhThu.getDoanhThu().subtract(thongKeDoanhThu.getGiamGia()), "Price", thongKeDoanhThu.getTenSP());
                doanhthuString = String.valueOf(thongKeDoanhThu.getDoanhThu());
                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
                doanhThudb += Double.parseDouble(doanhthuString);
                giamGiadb += Double.parseDouble(giamGiaString);
//                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
//                giamGiadb += Double.parseDouble(giamGiaString);
            }
            Long doanhThu = doanhThudb.longValue() - giamGiadb.longValue();
            txtDoanhThu.setText(String.valueOf(doanhThu));

            JFreeChart chart = ChartFactory.createBarChart("Doanh Thu", "", "Tiền doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color rColor3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, rColor3);
            ChartPanel barpChartPanel = new ChartPanel(chart);
            jPanel4.removeAll();
            jPanel4.add(barpChartPanel, BorderLayout.CENTER);
            jPanel4.validate();

            DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
            DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
            model.setRowCount(0);

            List<ThongKeSanPham> thongKeSanPhams = hoaDonChiTietServies.thongKeSoLuongDaBanTheoNgay(date2);
            Integer soLuongDaBan = 0;
            for (ThongKeSanPham thongKeSanPham : thongKeSanPhams) {
                soLuongDaBan += thongKeSanPham.getSoLuongBan().intValue();
            }
            txtSLDaBan.setText(String.valueOf(soLuongDaBan));
            List<ThongKeSanPham> sanPhams = hoaDonChiTietServies.thongKeTheoNgay(date2);
            for (ThongKeSanPham sanPham : sanPhams) {
                dataset1.setValue(sanPham.getSoLuongBan(), "Quantity", sanPham.getTenSP());
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getSoLuongBan()
                };
                model.addRow(data);
            }
            JFreeChart chart1 = ChartFactory.createBarChart("Số Lượng Bán ", "", "Số lượng bán", dataset1, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot1 = chart1.getCategoryPlot();
            categoryPlot1.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer1 = (BarRenderer) categoryPlot1.getRenderer();
            Color rColor31 = new Color(204, 0, 51);
            renderer1.setSeriesPaint(0, rColor31);
            ChartPanel barpChartPanel1 = new ChartPanel(chart1);
            jPanel1.removeAll();
            jPanel1.add(barpChartPanel1, BorderLayout.CENTER);
            jPanel1.validate();
            DefaultTableModel model1 = (DefaultTableModel) tbKhachHang.getModel();
            model1.setRowCount(0);
            List<ThongKeKhachHang> thongKeKhachHangs = khachHangService.thongKeTheoNgay(date2);
            for (ThongKeKhachHang thongKeKhachHang : thongKeKhachHangs) {
                Object[] data = new Object[]{
                    thongKeKhachHang.getMaKH(),
                    thongKeKhachHang.getTenKH(),
                    thongKeKhachHang.getSoLanMua()
                };
                model1.addRow(data);
            }
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuTheoDanhMucs = hoaDonChiTietServies.thongKeDoanhThuDanhMucTheoNgay(date2);
            for (ThongKeDoanhThuTheoDanhMuc thongKeDoanhThuTheoDanhMuc : thongKeDoanhThuTheoDanhMucs) {
                pieDataset.setValue(thongKeDoanhThuTheoDanhMuc.getTenDM(),thongKeDoanhThuTheoDanhMuc.getTongTien().subtract(thongKeDoanhThuTheoDanhMuc.getGiamGia()));
            }
            JFreeChart chart2 = ChartFactory.createPieChart("Doanh thu theo danh muc", pieDataset, false, true, false);
            PiePlot piePlot = (PiePlot) chart2.getPlot();
            piePlot.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel = new ChartPanel(chart2);
            jPanel6.removeAll();
            jPanel6.add(pieChartPanel,BorderLayout.CENTER);
            jPanel6.validate();
            
            DefaultPieDataset pieDataset1 = new DefaultPieDataset();
            List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongTheoDanhMucs = hoaDonChiTietServies.thongKeSoLuongDanhMucTheoNgay(date2);
            for (ThongKeSoLuongTheoDanhMuc thongKeSoLuongTheoDanhMuc : thongKeSoLuongTheoDanhMucs) {
                pieDataset1.setValue(thongKeSoLuongTheoDanhMuc.getTenDM(),thongKeSoLuongTheoDanhMuc.getSoLuong().intValue());
            }
            JFreeChart chart3 = ChartFactory.createPieChart("Số lượng đã bán theo danh mục", pieDataset1, false, true, false);
            PiePlot piePlot2 = (PiePlot) chart3.getPlot();
            piePlot2.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel2 = new ChartPanel(chart3);
            jPanel8.removeAll();
            jPanel8.add(pieChartPanel2,BorderLayout.CENTER);
            jPanel8.validate();
        } catch (ParseException ex) {
            Logger.getLogger(QLThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTheoNgayMousePressed

    private void cbbThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbThongKeActionPerformed
        // TODO add your handling code here:
        String cbb = (String) cbbThongKe.getSelectedItem();
        if (cbb.equalsIgnoreCase("Theo tháng")) {
            txtDate.setVisible(false);
            btnTheoNgay.setVisible(false);
            cbbThang.setVisible(true);
            cbbNam.setVisible(true);
            btnThongKeThang.setVisible(true);
            btnThongKeNam.setVisible(false);
            cbbThang.removeAllItems();
            for (int i = 1; i <= 12; i++) {
                cbbThang.addItem(String.valueOf(i));
            }
            cbbNam.removeAllItems();
            DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            
            for (int i = Integer.parseInt(dtf.format(now)); i >= 2000; i--) {
                cbbNam.addItem(String.valueOf(i));
            }
            
        } else if (cbb.equalsIgnoreCase("Theo năm")) {
            txtDate.setVisible(false);
            btnTheoNgay.setVisible(false);
            cbbThang.setVisible(false);
            cbbNam.setVisible(true);
            btnThongKeNam.setVisible(true);
            btnThongKeThang.setVisible(false);
            cbbNam.removeAllItems();
            DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            
            for (int i = Integer.parseInt(dtf.format(now)); i >= 2000; i--) {
                cbbNam.addItem(String.valueOf(i));
            }
        }
            else{
            txtDate.setVisible(true);
            btnTheoNgay.setVisible(true);
            cbbThang.setVisible(false);
            cbbNam.setVisible(false);
            btnThongKeThang.setVisible(false);
            btnThongKeNam.setVisible(false);
           
        }
    }//GEN-LAST:event_cbbThongKeActionPerformed

    private void btnThongKeThangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeThangMousePressed
        // TODO add your handling code here:
        Integer thang = Integer.parseInt((String) cbbThang.getSelectedItem());
        Integer nam = Integer.parseInt((String) cbbNam.getSelectedItem());
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

 
            txtThongKe.setText("Thống kê tháng " + thang +" năm "+nam);
            List<HoaDon> hoaDons = hoaDonServices.countHoaDontheoThang(2, thang,nam);
            txtSoLuongDaBan.setText(String.valueOf(hoaDons.size()));

            List<HoaDon> b = hoaDonServices.countHoaDonHuytheoThang(1, thang,nam);
            txtHoaDonHuy.setText(String.valueOf(b.size()));
            List<ThongKeDoanhThu> list = hoaDonChiTietServies.thongKeDoanhThuTheoThang(thang,nam);
            String doanhthuString = "";
            Double doanhThudb = 0.0;
            String giamGiaString = "";
            Double giamGiadb = 0.0;
            Double doanhThudb1 = 0.0;

            for (ThongKeDoanhThu thongKeDoanhThu : list) {

                dataset.setValue(thongKeDoanhThu.getDoanhThu().subtract(thongKeDoanhThu.getGiamGia()), "Price", thongKeDoanhThu.getTenSP());
                doanhthuString = String.valueOf(thongKeDoanhThu.getDoanhThu());
                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
                doanhThudb += Double.parseDouble(doanhthuString);
                giamGiadb += Double.parseDouble(giamGiaString);
//                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
//                giamGiadb += Double.parseDouble(giamGiaString);
            }
            Long doanhThu = doanhThudb.longValue() - giamGiadb.longValue();
            txtDoanhThu.setText(String.valueOf(doanhThu));

            JFreeChart chart = ChartFactory.createBarChart("Doanh Thu", "", "Tiền doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color rColor3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, rColor3);
            ChartPanel barpChartPanel = new ChartPanel(chart);
            jPanel4.removeAll();
            jPanel4.add(barpChartPanel, BorderLayout.CENTER);
            jPanel4.validate();

            DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
            DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
            model.setRowCount(0);

            List<ThongKeSanPham> thongKeSanPhams = hoaDonChiTietServies.thongKeSoLuongDaBanTheoThang(thang,nam);
            Integer soLuongDaBan = 0;
            for (ThongKeSanPham thongKeSanPham : thongKeSanPhams) {
                soLuongDaBan += thongKeSanPham.getSoLuongBan().intValue();
            }
            txtSLDaBan.setText(String.valueOf(soLuongDaBan));
            List<ThongKeSanPham> sanPhams = hoaDonChiTietServies.thongKeTheoThang(thang,nam);
            for (ThongKeSanPham sanPham : sanPhams) {
                dataset1.setValue(sanPham.getSoLuongBan(), "Quantity", sanPham.getTenSP());
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getSoLuongBan()
                };
                model.addRow(data);
            }
            JFreeChart chart1 = ChartFactory.createBarChart("Số Lượng Bán ", "", "Số lượng bán", dataset1, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot1 = chart1.getCategoryPlot();
            categoryPlot1.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer1 = (BarRenderer) categoryPlot1.getRenderer();
            Color rColor31 = new Color(204, 0, 51);
            renderer1.setSeriesPaint(0, rColor31);
            ChartPanel barpChartPanel1 = new ChartPanel(chart1);
            jPanel1.removeAll();
            jPanel1.add(barpChartPanel1, BorderLayout.CENTER);
            jPanel1.validate();
            DefaultTableModel model1 = (DefaultTableModel) tbKhachHang.getModel();
            model1.setRowCount(0);
            List<ThongKeKhachHang> thongKeKhachHangs = khachHangService.thongKeTheoThang(thang,nam);
            for (ThongKeKhachHang thongKeKhachHang : thongKeKhachHangs) {
                Object[] data = new Object[]{
                    thongKeKhachHang.getMaKH(),
                    thongKeKhachHang.getTenKH(),
                    thongKeKhachHang.getSoLanMua()
                };
                model1.addRow(data);
            }
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuTheoDanhMucs = hoaDonChiTietServies.thongKeDoanhThuDanhMucTheoThang(thang,nam);
            for (ThongKeDoanhThuTheoDanhMuc thongKeDoanhThuTheoDanhMuc : thongKeDoanhThuTheoDanhMucs) {
                pieDataset.setValue(thongKeDoanhThuTheoDanhMuc.getTenDM(),thongKeDoanhThuTheoDanhMuc.getTongTien().subtract(thongKeDoanhThuTheoDanhMuc.getGiamGia()));
            }
            JFreeChart chart2 = ChartFactory.createPieChart("Doanh thu theo danh muc", pieDataset, false, true, false);
            PiePlot piePlot = (PiePlot) chart2.getPlot();
            piePlot.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel = new ChartPanel(chart2);
            jPanel6.removeAll();
            jPanel6.add(pieChartPanel,BorderLayout.CENTER);
            jPanel6.validate();
            
            DefaultPieDataset pieDataset1 = new DefaultPieDataset();
            List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongTheoDanhMucs = hoaDonChiTietServies.thongKeSoLuongDanhMucTheoThang(thang,nam);
            for (ThongKeSoLuongTheoDanhMuc thongKeSoLuongTheoDanhMuc : thongKeSoLuongTheoDanhMucs) {
                pieDataset1.setValue(thongKeSoLuongTheoDanhMuc.getTenDM(),thongKeSoLuongTheoDanhMuc.getSoLuong().intValue());
            }
            JFreeChart chart3 = ChartFactory.createPieChart("Số lượng đã bán theo danh mục", pieDataset1, false, true, false);
            PiePlot piePlot2 = (PiePlot) chart3.getPlot();
            piePlot2.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel2 = new ChartPanel(chart3);
            jPanel8.removeAll();
            jPanel8.add(pieChartPanel2,BorderLayout.CENTER);
            jPanel8.validate();
       
    }//GEN-LAST:event_btnThongKeThangMousePressed

    private void btnThongKeNamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeNamMousePressed
        // TODO add your handling code here:
        Integer nam = Integer.parseInt((String) cbbNam.getSelectedItem());
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

 
            txtThongKe.setText("Thống kê năm "+nam);
            List<HoaDon> hoaDons = hoaDonServices.countHoaDontheoNam(2,nam);
            txtSoLuongDaBan.setText(String.valueOf(hoaDons.size()));

            List<HoaDon> b = hoaDonServices.countHoaDonHuytheoNam(1,nam);
            txtHoaDonHuy.setText(String.valueOf(b.size()));
            List<ThongKeDoanhThu> list = hoaDonChiTietServies.thongKeDoanhThuTheoNam(nam);
            String doanhthuString = "";
            Double doanhThudb = 0.0;
            String giamGiaString = "";
            Double giamGiadb = 0.0;
            Double doanhThudb1 = 0.0;

            for (ThongKeDoanhThu thongKeDoanhThu : list) {

                dataset.setValue(thongKeDoanhThu.getDoanhThu().subtract(thongKeDoanhThu.getGiamGia()), "Price", thongKeDoanhThu.getTenSP());
                doanhthuString = String.valueOf(thongKeDoanhThu.getDoanhThu());
                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
                doanhThudb += Double.parseDouble(doanhthuString);
                giamGiadb += Double.parseDouble(giamGiaString);
//                giamGiaString = String.valueOf(thongKeDoanhThu.getGiamGia());
//                giamGiadb += Double.parseDouble(giamGiaString);
            }
            Long doanhThu = doanhThudb.longValue() - giamGiadb.longValue();
            txtDoanhThu.setText(String.valueOf(doanhThu));

            JFreeChart chart = ChartFactory.createBarChart("Doanh Thu", "", "Tiền doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color rColor3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, rColor3);
            ChartPanel barpChartPanel = new ChartPanel(chart);
            jPanel4.removeAll();
            jPanel4.add(barpChartPanel, BorderLayout.CENTER);
            jPanel4.validate();

            DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
            DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
            model.setRowCount(0);

            List<ThongKeSanPham> thongKeSanPhams = hoaDonChiTietServies.thongKeSoLuongDaBanTheoNam(nam);
            Integer soLuongDaBan = 0;
            for (ThongKeSanPham thongKeSanPham : thongKeSanPhams) {
                soLuongDaBan += thongKeSanPham.getSoLuongBan().intValue();
            }
            txtSLDaBan.setText(String.valueOf(soLuongDaBan));
            List<ThongKeSanPham> sanPhams = hoaDonChiTietServies.thongKeTheoNam(nam);
            for (ThongKeSanPham sanPham : sanPhams) {
                dataset1.setValue(sanPham.getSoLuongBan(), "Quantity", sanPham.getTenSP());
                Object[] data = new Object[]{
                    sanPham.getMaSP(),
                    sanPham.getTenSP(),
                    sanPham.getSoLuongBan()
                };
                model.addRow(data);
            }
            JFreeChart chart1 = ChartFactory.createBarChart("Số Lượng Bán ", "", "Số lượng bán", dataset1, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot categoryPlot1 = chart1.getCategoryPlot();
            categoryPlot1.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer1 = (BarRenderer) categoryPlot1.getRenderer();
            Color rColor31 = new Color(204, 0, 51);
            renderer1.setSeriesPaint(0, rColor31);
            ChartPanel barpChartPanel1 = new ChartPanel(chart1);
            jPanel1.removeAll();
            jPanel1.add(barpChartPanel1, BorderLayout.CENTER);
            jPanel1.validate();
            DefaultTableModel model1 = (DefaultTableModel) tbKhachHang.getModel();
            model1.setRowCount(0);
            List<ThongKeKhachHang> thongKeKhachHangs = khachHangService.thongKeTheoNam(nam);
            for (ThongKeKhachHang thongKeKhachHang : thongKeKhachHangs) {
                Object[] data = new Object[]{
                    thongKeKhachHang.getMaKH(),
                    thongKeKhachHang.getTenKH(),
                    thongKeKhachHang.getSoLanMua()
                };
                model1.addRow(data);
            }
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuTheoDanhMucs = hoaDonChiTietServies.thongKeDoanhThuDanhMucTheoNam(nam);
            for (ThongKeDoanhThuTheoDanhMuc thongKeDoanhThuTheoDanhMuc : thongKeDoanhThuTheoDanhMucs) {
                pieDataset.setValue(thongKeDoanhThuTheoDanhMuc.getTenDM(),thongKeDoanhThuTheoDanhMuc.getTongTien().subtract(thongKeDoanhThuTheoDanhMuc.getGiamGia()));
            }
            JFreeChart chart2 = ChartFactory.createPieChart("Doanh thu theo danh muc", pieDataset, false, true, false);
            PiePlot piePlot = (PiePlot) chart2.getPlot();
            piePlot.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel = new ChartPanel(chart2);
            jPanel6.removeAll();
            jPanel6.add(pieChartPanel,BorderLayout.CENTER);
            jPanel6.validate();
            
            DefaultPieDataset pieDataset1 = new DefaultPieDataset();
            List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongTheoDanhMucs = hoaDonChiTietServies.thongKeSoLuongDanhMucTheoNam(nam);
            for (ThongKeSoLuongTheoDanhMuc thongKeSoLuongTheoDanhMuc : thongKeSoLuongTheoDanhMucs) {
                pieDataset1.setValue(thongKeSoLuongTheoDanhMuc.getTenDM(),thongKeSoLuongTheoDanhMuc.getSoLuong().intValue());
            }
            JFreeChart chart3 = ChartFactory.createPieChart("Số lượng đã bán theo danh mục", pieDataset1, false, true, false);
            PiePlot piePlot2 = (PiePlot) chart3.getPlot();
            piePlot2.setBackgroundPaint(Color.white);
            ChartPanel pieChartPanel2 = new ChartPanel(chart3);
            jPanel8.removeAll();
            jPanel8.add(pieChartPanel2,BorderLayout.CENTER);
            jPanel8.validate();
    }//GEN-LAST:event_btnThongKeNamMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnTheoNgay;
    private javax.swing.JLabel btnThongKeNam;
    private javax.swing.JLabel btnThongKeThang;
    private javax.swing.JComboBox<String> cbbNam;
    private javax.swing.JComboBox<String> cbbThang;
    private javax.swing.JComboBox<String> cbbThongKe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JPanel thongke;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JLabel txtDoanhThu;
    private javax.swing.JLabel txtHoaDonHuy;
    private javax.swing.JLabel txtSLDaBan;
    private javax.swing.JLabel txtSoLuongDaBan;
    private javax.swing.JLabel txtThongKe;
    // End of variables declaration//GEN-END:variables
}
