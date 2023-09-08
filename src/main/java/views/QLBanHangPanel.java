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
import domainmodels.BaoHanh;
import domainmodels.BaoHanhChiTiet;
import domainmodels.BoNhoTrong;
import domainmodels.ChiTietSP;
import domainmodels.DanhMuc;
import domainmodels.HoaDon;
import domainmodels.HoaDonChiTiet;
import domainmodels.KhachHang;
import domainmodels.KhuyenMai;
import domainmodels.MauSac;
import domainmodels.NhanVien;
import domainmodels.SanPham;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import services.ChiTietSPServices;
import services.DanhMucServices;
import services.GetImeiByMaSPServices;
import services.HoaDonChiTietServices;
import services.HoaDonServices;
import services.IChiTietSPServices;
import services.IDanhMucServices;
import services.IHoaDonChiTietServies;
import services.IHoaDonServices;
import services.IKhachHangService;
import services.INhanVienServices;
import services.IQLBaoHanhChiTietServices;
import services.IQLBaoHanhServices;
import services.IQLBoNhoTrongServices;
import services.IQLKhuyenMaiServices;
import services.IQLMauSacServices;
import services.IQLSanPhamServices;
import services.KhachHangService;
import services.NhanVienServices;
import services.PhanQuyenServices;
import services.QLBaoHanhChiTietServices;
import services.QLBaoHanhServices;
import services.QLBoNhoTrongServices;
import services.QLKhuyenMaiServices;
import services.QLMauSacServices;
import services.QLSanPhamServices;
import services.TaoHoaDonServices;
import viewmodels.ChiTietSPViewModels;
import static views.QLBanHang.tbGioHang;

/**
 *
 * @author HANGOCHAN
 */
public class QLBanHangPanel extends javax.swing.JPanel implements Runnable, ThreadFactory {

    /**
     * Creates new form QLBanHangPanel
     */
    private WebcamPanel panel = null;
    private static Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private IChiTietSPServices services;
    private IHoaDonServices hoaDonServices;
    private IQLSanPhamServices sanPhamServices;
    private IChiTietSPServices chiTietSPServices;
    private INhanVienServices nhanVienServices;
    private IHoaDonChiTietServies hoaDonChiTietServies;
    private IQLKhuyenMaiServices khuyenMaiServices;
    private IKhachHangService khachHangService;
    private IQLBaoHanhServices baoHanhServices;
    private IQLBaoHanhChiTietServices baoHanhChiTietServices;
    private IDanhMucServices danhMucServices;
    private IQLMauSacServices mauSacServices;
    private IQLBoNhoTrongServices boNhoTrongServices;
    private String maBH;

    public QLBanHangPanel() {
        initComponents();

        initWebcam();
        services = new ChiTietSPServices();
        sanPhamServices = new QLSanPhamServices();
        chiTietSPServices = new ChiTietSPServices();
        hoaDonServices = new HoaDonServices();
        nhanVienServices = new NhanVienServices();
        hoaDonChiTietServies = new HoaDonChiTietServices();
        khuyenMaiServices = new QLKhuyenMaiServices();
        khachHangService = new KhachHangService();
        baoHanhServices = new QLBaoHanhServices();
        baoHanhChiTietServices = new QLBaoHanhChiTietServices();
        danhMucServices = new DanhMucServices();
        mauSacServices = new QLMauSacServices();
        boNhoTrongServices = new QLBoNhoTrongServices();
        tbHoaDon.setDefaultRenderer(Object.class, new MyTableCell());
        tbHoaDon.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tbHoaDon.scrollRectToVisible(tbHoaDon.getCellRect(tbHoaDon.getRowCount() - 1, 0, true));
            }
        });
        cbbGiamGia.removeAllItems();
        hienThiHoaDon();
        hienThiSanPham();
        cbbKhachHang();
        cbbGiamGia();
        loadMaHD();
        loadCbbLocDanhMuc();
        loadCbbLocMauSac();
        loadCbbLocBoNho();
        rbTaiCuaHang.setSelected(true);
        txtDiaChiNhanHang.setText("Tại cửa hàng");
        txtDiaChiNhanHang.setEditable(false);
        txtDiaChiNhanHang.setEnabled(false);
        txtPhiship.setText("0");
        txtPhiship.setEditable(false);
        txtPhiship.setEnabled(false);
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
        List<MauSac> mauSacs = mauSacServices.getALL();
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

    public void loadMaHD() {

        String ma = "";
        List<HoaDon> hoaDon = hoaDonServices.getALL();
        if (hoaDon.size() == 0) {
            ma = "HD0";
        } else {
            HoaDon hd = hoaDonServices.layMa();
//          
            ma = hd.getMaHD();
        }

        String mangString[] = ma.split("");
        String so = "";
        for (int i = 2; i < mangString.length; i++) {
            so += mangString[i];
        }

        Integer sofinal = Integer.parseInt(so) + 1;
        String maMoi = "HD" + sofinal;
        txtMaHD.setText(maMoi);
    }

    public void loadMaBH() {

        String ma = "";
        List<BaoHanh> baoHanhs = baoHanhServices.getALL();
        if (baoHanhs.size() == 0) {
            ma = "BH0";
        } else {
            BaoHanh hd = baoHanhServices.layMa();
//          
            ma = hd.getMaBH();
        }

        String mangString[] = ma.split("");
        String so = "";
        for (int i = 2; i < mangString.length; i++) {
            so += mangString[i];
        }

        Integer sofinal = Integer.parseInt(so) + 1;
        maBH = "BH" + sofinal;
//        txtMaHD.setText(maMoi);
    }

    public void cbbKhachHang() {
        cbbKhachHang.removeAllItems();;
        List<KhachHang> list = khachHangService.getALL();
        for (KhachHang khachHang : list) {
            if (khachHang.getTrangThai() == 0) {
                cbbKhachHang.addItem(khachHang.getTenKH() + "--" + khachHang.getMaKH());
            }
        }
    }

    public void cbbGiamGia() {
        cbbGiamGia.addItem("");
        List<KhuyenMai> khuyenMais = khuyenMaiServices.getAll();
        for (KhuyenMai khuyenMai : khuyenMais) {
            if (khuyenMai.getTrangThai() == 0) {
                cbbGiamGia.addItem(khuyenMai.getMaKM() + "--" + khuyenMai.getTenKM() + "--" + khuyenMai.getChietKhau());
            }
        }
    }

    public static void windowClosed() {
        if (webcam == null) {
            return;
        }
        webcam.close();
    }

    public HoaDon layTTHoaDon() throws ParseException {
        String maHD = txtMaHD.getText();
        PhanQuyenServices phanQuyenServices = new PhanQuyenServices();
        String maNV = phanQuyenServices.getMa();
        String maKHString = (String) cbbKhachHang.getSelectedItem();
        String mangString[] = maKHString.split("--");
        String maKH = mangString[1];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String ngayTao = dtf.format(now);
        Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);
        HoaDon hd = new HoaDon();
        NhanVien nv = null;
        KhachHang khachHang = new KhachHang();
        khachHang.setMaKH(maKH);
        String tenKH = "";
        String sdt = "";
        String diaChi = "";
        List<KhachHang> khachHangs = khachHangService.getALL();
        for (KhachHang kh : khachHangs) {
            if (kh.getMaKH().equals(maKH)) {
                tenKH = kh.getTenKH();
                sdt = kh.getSdt();
                diaChi = kh.getDiaChi();
            }
        }
        List<NhanVien> nhanViens = nhanVienServices.getALL();
        for (NhanVien nhanVien : nhanViens) {
            if (maNV.equals(nhanVien.getMaNV())) {
                nv = nhanVien;

            }
        }

        hd.setMaHD(maHD);
        hd.setTenNguoiNhan(tenKH);
        hd.setSdt(sdt);
        hd.setDiaChiNhanHang(diaChi);
        hd.setKhachHang(khachHang);
        hd.setNhanVien(nv);
        hd.setNgayTao(date);
        hd.setTrangThai(0);
        return hd;
    }

    public void fillHoaDon() {
        clear();
        int index = tbHoaDon.getSelectedRow();
        String ma = tbHoaDon.getValueAt(index, 0).toString();
        GetImeiByMaSPServices getImeiByMaSPServices = new GetImeiByMaSPServices();
        getImeiByMaSPServices.setMaHD(ma);
        HoaDon hd1 = hoaDonServices.fill(ma);
        if (hd1.getTrangThai() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn này đã bị hủy !");
            return;
        }
        if (hd1.getTrangThai() == 2) {
            btnThanhToan.setEnabled(false);
            btnHuy.setEnabled(false);
            cbbGiamGia.setEnabled(false);
            cbbGiamGia.setSelectedIndex(0);
            cbbGiamGia.setPopupVisible(false);
            rbShiphang.setEnabled(false);
            rbShiphang.setSelected(false);
            txtDiaChiNhanHang.setEditable(false);
            txtPhiship.setEditable(false);
            txtDiaChiNhanHang.setEnabled(false);
            txtPhiship.setEnabled(false);
            txtGhichu.setEnabled(false);
            txtGhichu.setEditable(false);
            txtTienKhachDua.setEnabled(false);
            txtTienKhachDua.setEditable(false);
        }
        if (hd1.getTrangThai() == 0) {
            btnThanhToan.setEnabled(true);
            btnHuy.setEnabled(true);
//            btnIn.setEnabled(false);
            cbbGiamGia.setEnabled(true);
            rbShiphang.setEnabled(true);
            txtGhichu.setEnabled(true);
            txtGhichu.setEditable(true);
            txtTienKhachDua.setEnabled(true);
            txtTienKhachDua.setEditable(true);
        }

        List<HoaDonChiTiet> list = hoaDonChiTietServies.getALL(ma);
        List<ChiTietSP> chiTietSPs = chiTietSPServices.getImei();
        tbGioHang.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbGioHang.getModel();
        model.setRowCount(0);
        for (HoaDonChiTiet hoaDonChiTiet : list) {
            for (ChiTietSP c : chiTietSPs) {
                if (hoaDonChiTiet.getMaImei().getMaImei().equals(c.getMaImei())) {
                    JLabel label = new JLabel();
                    ImageIcon icon = new ImageIcon(c.getAnh());
                    Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));

                    Object[] data = new Object[]{
                        label,
                        c.getSanPham().getTenSP(),
                        c.getMaImei(),
                        "1",
                        c.getGiaBan(),};

                    model.addRow(data);

                }
            }
        }
        cbbGiamGia.setSelectedIndex(0);

        HoaDon hd = hoaDonServices.fill(ma);
        txtMaHoaDon.setText(hd.getMaHD());
        txtNhanVien.setText(hd.getNhanVien().getMaNV());
        txtKhachHang.setText(hd.getKhachHang().getTenKH());
        Date date = hd.getNgayTao();
        String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
        txtNgayTao.setText(sdf);

        rbTaiCuaHang.setSelected(true);
        txtDiaChiNhanHang.setText("Tại cửa hàng");
        txtDiaChiNhanHang.setEditable(false);
        txtDiaChiNhanHang.setEnabled(false);
        txtPhiship.setText("0");
        txtPhiship.setEditable(false);
        txtPhiship.setEnabled(false);
        int indexGioHang = tbGioHang.getRowCount();
        Integer tongTien = 0;
        for (int i = 0; i < indexGioHang; i++) {
            tongTien += Integer.parseInt(tbGioHang.getValueAt(i, 4).toString());
        }
        txtTongTien.setText(String.valueOf(tongTien));
        HoaDon hoaDon = hoaDonServices.seachbyMa(ma);
        Integer giamGia = 0;
        if (hoaDon.getGiamGia() == null) {
            giamGia = 0;
        } else {
            String giamGiasString = String.valueOf(hoaDon.getGiamGia());
            giamGia = Integer.parseInt(giamGiasString);
        }
        txtGiamGia.setText(String.valueOf(giamGia));
        Integer tienPhaiThanhToan = tongTien - giamGia;
        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));

    }

    public static void loadTongTien(String tongTienn) {
        txtTongTien.setText(tongTienn);
        rbTaiCuaHang.setSelected(true);
        txtPhiship.setEnabled(false);
        txtPhiship.setEditable(false);
        txtDiaChiNhanHang.setEnabled(false);
        txtDiaChiNhanHang.setEditable(false);
        txtPhiship.setText("0");
        txtDiaChiNhanHang.setText("");
        txtTienKhachDua.setText("");
        cbbGiamGia.setSelectedIndex(0);
        txtTienTraLai.setText("0");
        txtGiamGia.setText("0");
        txtPhiShip.setText("0");
        txtTienThanhToan.setText(tongTienn);
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
                    maSP,
                    c.getTenSP(),
                    c.getNsx(),
                    c.getMauSac(),
                    c.getBoNho(),
                    c.getTonKho(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);
            }

        }
    }

    public void hienThiHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tbHoaDon.getModel();
        model.setRowCount(0);
        List<HoaDon> hoaDons = hoaDonServices.getALL();

        for (HoaDon hoaDon : hoaDons) {
            if (hoaDon.getTrangThai() == 0) {
                Object[] data = new Object[]{
                    hoaDon.getMaHD()
                };

                model.addRow(data);
                changeTable(tbHoaDon, 0);
            }

        }

    }

    public void clear() {
        DefaultTableModel model = (DefaultTableModel) tbGioHang.getModel();
        model.setRowCount(0);
        txtMaHoaDon.setText("");
        txtNhanVien.setText("");
        txtKhachHang.setText("");
        txtNgayTao.setText("");
        rbTaiCuaHang.setSelected(true);
        txtPhiShip.setText("0");
        txtPhiship.setText("0");
        txtGhichu.setText("");
        cbbGiamGia.setSelectedIndex(0);
        txtTienKhachDua.setText("");
        txtTongTien.setText("0");
        txtGiamGia.setText("0");
        txtPhiShip.setText("0");
        txtTienThanhToan.setText("0");
        txtTienTraLai.setText("0");
        hienThiSanPham();

    }

    class MyTableCell extends DefaultTableCellRenderer {

        @Override
        public Color getBackground() {
            return super.getBackground();
        }
    }

    public void changeTable(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                List<HoaDon> hoaDons = hoaDonServices.getALL();
                for (HoaDon hoaDon : hoaDons) {
                    if (hoaDon.getMaHD().equals(tbHoaDon.getValueAt(row, column).toString())) {
                        if (hoaDon.getTrangThai() == 0) {
                            c.setBackground(new Color(102, 102, 255));
                            setHorizontalAlignment(JLabel.CENTER);
                            setForeground(Color.WHITE);
                        } else if (hoaDon.getTrangThai() == 1) {
                            c.setBackground(new Color(255, 51, 51));
                            setHorizontalAlignment(JLabel.CENTER);
                            setForeground(Color.WHITE);
                        } else {
                            c.setBackground(new Color(0, 255, 204));
                            setHorizontalAlignment(JLabel.CENTER);
                            setForeground(Color.WHITE);
                        }
                    }
                }

                return c;
            }
        });
    }

    private void initWebcam() {

        Dimension size = WebcamResolution.QVGA.getSize();;
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        jPanel3.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 250));
        executor.execute(this);

    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QLBanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
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
//               txtKetQua.setText(result.getText());
//                System.out.println(result.getText());
                int index = tbHoaDon.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi thêm sản phẩm vào giỏ hàng !");
                    continue;
                }
                ChiTietSP c = services.seachbyMa(result.getText());
                if (c == null) {
                    JOptionPane.showMessageDialog(this, "Mã QRCode/BARCODE không khớp với sản phẩm nào !");
                    continue;
                }

                String maHd = tbHoaDon.getValueAt(index, 0).toString();
                ChiTietSP c1 = chiTietSPServices.seachbyMa(result.getText());
                if (c1.getTrangThai() == 2) {
                    JOptionPane.showMessageDialog(this, "Imei này đã tồn tại trên Hóa đơn !");
                    continue;
                }
                chiTietSPServices.updateImeiTrangThai(result.getText(), 2);
                tbGioHang.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
                DefaultTableModel model = (DefaultTableModel) tbGioHang.getModel();
                JLabel label = new JLabel();
                ImageIcon icon = new ImageIcon(c.getAnh());
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
                if (c.getTrangThai() == 0) {
                    Object[] data = new Object[]{
                        label,
                        c.getSanPham().getTenSP(),
                        c.getMaImei(),
                        "1",
                        c.getGiaBan(),};

                    model.addRow(data);
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thành công !");
                }
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                HoaDon hd = null;
                List<HoaDon> hoaDons = hoaDonServices.getALL();
                for (HoaDon hoaDon : hoaDons) {
                    if (hoaDon.getMaHD().equals(maHd)) {
                        hd = hoaDon;
                    }
                }
                ChiTietSP chiTietSP = null;
                List<ChiTietSP> chiTietSPs = chiTietSPServices.getImei();
                for (ChiTietSP chiTietSP1 : chiTietSPs) {
                    if (chiTietSP1.getMaImei().equals(result.getText())) {
                        chiTietSP = chiTietSP1;
                    }
                }
                hdct.setMaHD(hd);
                hdct.setMaImei(chiTietSP);
                hdct.setSoLuong(1);
                hdct.setDonGia(c.getGiaBan());
                List<HoaDonChiTiet> list = hoaDonChiTietServies.getALL(maHd);
                for (HoaDonChiTiet hoaDonChiTiet : list) {
                    if (result.getText().equals(hoaDonChiTiet.getMaImei().getMaImei())) {
                        return;
                    }
                }
                hoaDonChiTietServies.add(hdct);

                Integer tongTien = 0;
                String tongTienstr = "";
                List<HoaDonChiTiet> hdcts = hoaDonChiTietServies.getALL(maHd);
                for (HoaDonChiTiet hdct1 : hdcts) {
                    tongTienstr = String.valueOf(hdct1.getDonGia());
                    tongTien += Integer.parseInt(tongTienstr);
                }
                txtTongTien.setText(String.valueOf(tongTien));
                txtTienThanhToan.setText(String.valueOf(tongTien));
                hienThiSanPham();

            }

        } while (true);
    }

    public static void loadGioHang(ChiTietSP c) {
        tbGioHang.getColumn("Ảnh").setCellRenderer(new myTableCellRender1());
        DefaultTableModel model = (DefaultTableModel) tbGioHang.getModel();
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(c.getAnh());
        Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        if (c.getTrangThai() == 0) {
            Object[] data = new Object[]{
                label,
                c.getSanPham().getTenSP(),
                c.getMaImei(),
                "1",
                c.getGiaBan(),};
            model.addRow(data);
        }

    }

    public static void loadSoLuongSanPham(Integer soLuong) {
        int row = tbSanPham.getSelectedRow();
        Integer soLuongHienTai = Integer.parseInt(tbSanPham.getValueAt(row, 5).toString());
        if (soLuongHienTai == 0) {
            JOptionPane.showMessageDialog(tbSanPham, "Số lượng sản phẩm đã hết !");
            return;
        }
        Integer soLuongCapNhat = soLuongHienTai - soLuong;
        tbSanPham.setValueAt(soLuongCapNhat, row, 5);
    }

    static class myTableCellRender1 implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            tbGioHang.setRowHeight(70);
            return (Component) value;
        }
    }

    class myTableCellRender implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            tbGioHang.setRowHeight(70);
            tbSanPham.setRowHeight(70);
            return (Component) value;
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        banhang = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JLabel();
        txtNhanVien = new javax.swing.JLabel();
        txtKhachHang = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JLabel();
        rbTaiCuaHang = new javax.swing.JRadioButton();
        rbShiphang = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiaChiNhanHang = new javax.swing.JTextArea();
        txtPhiship = new javax.swing.JTextField();
        txtGhichu = new javax.swing.JTextField();
        cbbGiamGia = new javax.swing.JComboBox<>();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JLabel();
        txtPhiShip = new javax.swing.JLabel();
        txtTienTraLai = new javax.swing.JLabel();
        txtTienThanhToan = new javax.swing.JLabel();
        btnThem = new keeptoo.KGradientPanel();
        btnThanhToan = new javax.swing.JLabel();
        btnThem2 = new keeptoo.KGradientPanel();
        btnHuy = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbbKhachHang = new javax.swing.JComboBox<>();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnTaoHoaDon = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnThem4 = new keeptoo.KGradientPanel();
        btnClear = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbbLocBoNho = new javax.swing.JComboBox<>();
        cbbLocDanhMuc = new javax.swing.JComboBox<>();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        cbbLocMauSac = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        banhang.setBackground(new java.awt.Color(255, 255, 255));

        tbGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ảnh", "Tên SP", "Imei", "Số lượng", "Đơn Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbGioHangMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbGioHang);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 3, 12)); // NOI18N
        jLabel1.setText("Quét với QR-CODE/BAR-CODE");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên SP", "NSX", "Màu Sắc", "Dung Lượng", "Số lượng tồn", "Giá bán", "Ảnh"
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
        jScrollPane2.setViewportView(tbSanPham);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Fast_Cart_30px.png"))); // NOI18N
        jLabel2.setText("Giỏ Hàng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_product_30px_2.png"))); // NOI18N
        jLabel3.setText("Sản Phẩm");

        tbHoaDon.setFont(new java.awt.Font("Bahnschrift", 1, 16)); // NOI18N
        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Hóa đơn chờ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbHoaDon.setRowHeight(70);
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbHoaDonMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbHoaDon);

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel7.setText("Mã HD:");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel8.setText("Nhân viên :");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel9.setText("Khách hàng:");

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel10.setText("Ngày Tạo:");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel11.setText("Địa chỉ nhận hàng:");

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel12.setText("Phí ship:");

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel13.setText("Ghi chú:");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel14.setText("Giảm giá :");

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel15.setText("Tiền khách đưa:");

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel16.setText("Tổng tiền:");

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel17.setText("Giảm giá:");

        jLabel18.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel18.setText("Phí ship:");

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel19.setText("Tiền trả lại:");

        jLabel20.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel20.setText("Tiền phải thanh toán:");

        buttonGroup1.add(rbTaiCuaHang);
        rbTaiCuaHang.setText("Tại cửa hàng");
        rbTaiCuaHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rbTaiCuaHangMousePressed(evt);
            }
        });
        rbTaiCuaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTaiCuaHangActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbShiphang);
        rbShiphang.setText("Ship hàng");
        rbShiphang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                rbShiphangMousePressed(evt);
            }
        });

        txtDiaChiNhanHang.setColumns(20);
        txtDiaChiNhanHang.setRows(5);
        jScrollPane3.setViewportView(txtDiaChiNhanHang);

        txtPhiship.setText("0");
        txtPhiship.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhishipKeyReleased(evt);
            }
        });

        cbbGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbbGiamGiaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbbGiamGiaMouseReleased(evt);
            }
        });
        cbbGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiamGiaActionPerformed(evt);
            }
        });

        txtTienKhachDua.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTienKhachDuaInputMethodTextChanged(evt);
            }
        });
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        txtTongTien.setText("0");

        txtGiamGia.setText("0");

        txtPhiShip.setText("0");

        txtTienTraLai.setText("0");

        txtTienThanhToan.setText("0");

        btnThem.setkGradientFocus(150);

        btnThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Pay_30px_2.png"))); // NOI18N
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThanhToanMousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnThemLayout = new javax.swing.GroupLayout(btnThem);
        btnThem.setLayout(btnThemLayout);
        btnThemLayout.setHorizontalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );
        btnThemLayout.setVerticalGroup(
            btnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        btnThem2.setkGradientFocus(150);

        btnHuy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_cancel_subscription_30px.png"))); // NOI18N
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHuyMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnHuyMousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnThem2Layout = new javax.swing.GroupLayout(btnThem2);
        btnThem2.setLayout(btnThem2Layout);
        btnThem2Layout.setHorizontalGroup(
            btnThem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHuy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );
        btnThem2Layout.setVerticalGroup(
            btnThem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHuy, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_users_30px.png"))); // NOI18N
        jLabel21.setText("Khách hàng:");

        cbbKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_add_30px_6.png"))); // NOI18N
        btnTaoHoaDon.setText("Tạo Hóa Đơn");
        btnTaoHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnTaoHoaDonMousePressed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_Key_Security_30px_1.png"))); // NOI18N
        jLabel23.setText("Mã HD:");

        btnThem4.setkGradientFocus(150);

        btnClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8_available_updates_30px.png"))); // NOI18N
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClearMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnClearMousePressed(evt);
            }
        });

        javax.swing.GroupLayout btnThem4Layout = new javax.swing.GroupLayout(btnThem4);
        btnThem4.setLayout(btnThem4Layout);
        btnThem4Layout.setHorizontalGroup(
            btnThem4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );
        btnThem4Layout.setVerticalGroup(
            btnThem4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        txtMaHD.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Màu sắc");

        cbbLocBoNho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbLocDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbLocDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocDanhMucActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        cbbLocMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Bộ nhớ");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Danh mục");

        javax.swing.GroupLayout banhangLayout = new javax.swing.GroupLayout(banhang);
        banhang.setLayout(banhangLayout);
        banhangLayout.setHorizontalGroup(
            banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banhangLayout.createSequentialGroup()
                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(banhangLayout.createSequentialGroup()
                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, banhangLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(banhangLayout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(214, 214, 214))
                                    .addGroup(banhangLayout.createSequentialGroup()
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, banhangLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, banhangLayout.createSequentialGroup()
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbLocMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbLocBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(32, 32, 32)
                                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(39, 39, 39))
                    .addGroup(banhangLayout.createSequentialGroup()
                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(banhangLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(banhangLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(banhangLayout.createSequentialGroup()
                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(78, 78, 78)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rbTaiCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rbShiphang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel14))
                                    .addGap(48, 48, 48)
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                        .addComponent(txtPhiship)
                                        .addComponent(txtGhichu)
                                        .addComponent(cbbGiamGia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(banhangLayout.createSequentialGroup()
                                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel16)
                                                .addComponent(jLabel17)
                                                .addComponent(jLabel18))
                                            .addGap(47, 47, 47))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, banhangLayout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPhiShip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTienKhachDua)
                                        .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addGap(3, 3, 3)
                                    .addComponent(txtTienThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTienTraLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(banhangLayout.createSequentialGroup()
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9))
                                    .addGap(62, 62, 62)
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(banhangLayout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThem4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        banhangLayout.setVerticalGroup(
            banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(banhangLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(banhangLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(banhangLayout.createSequentialGroup()
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(banhangLayout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(banhangLayout.createSequentialGroup()
                                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel6))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbbLocMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbbLocBoNho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(8, 8, 8))
                                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(banhangLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(rbTaiCuaHang)
                                    .addComponent(rbShiphang))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(banhangLayout.createSequentialGroup()
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel12)
                                            .addComponent(txtPhiship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13)
                                            .addComponent(txtGhichu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(cbbGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(txtTongTien))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(txtGiamGia))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18))
                                    .addComponent(txtPhiShip))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtTienThanhToan))
                                .addGap(3, 3, 3)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtTienTraLai))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(banhangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(banhangLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(banhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(banhang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbGioHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGioHangMousePressed
        // TODO add your handling code here:
        int indexHoaDon = tbHoaDon.getSelectedRow();
        String ma = tbHoaDon.getValueAt(indexHoaDon, 0).toString();
        HoaDon hoaDon = hoaDonServices.fill(ma);
        if (hoaDon.getTrangThai() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn này đã thanh toán !");
            return;
        }
        int index = tbGioHang.getSelectedRow();
        String imei = tbGioHang.getValueAt(index, 2).toString();
        int indexhd = tbHoaDon.getSelectedRow();
        String maHD = tbHoaDon.getValueAt(indexhd, 0).toString();
        int check = JOptionPane.showConfirmDialog(this, "Xóa sản phẩm khỏi giỏ hàng ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        hoaDonChiTietServies.delete(imei, maHD);
        chiTietSPServices.updateImeiTrangThai(imei, 0);
        JOptionPane.showMessageDialog(this, "Xóa thành công !");
        fillHoaDon();
        txtGiamGia.setText("0");
        txtPhiShip.setText("0");
        txtTienKhachDua.setText("");
        txtTienTraLai.setText("0");
        cbbGiamGia.setSelectedIndex(0);
        Integer tongTien = Integer.parseInt(txtTongTien.getText());
        Integer tienShip = 0;
        Integer giamGia = Integer.parseInt(txtGiamGia.getText());

        Integer tienPhaiThanhToan = tongTien + tienShip - giamGia;

        txtPhiship.setText("0");
        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));
        hienThiSanPham();
    }//GEN-LAST:event_tbGioHangMousePressed

    private void tbSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMousePressed
        // TODO add your handling code here:
        int check = tbHoaDon.getSelectedRow();

        if (check == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi thêm sản phẩm !");
            return;
        }
        String ma = tbHoaDon.getValueAt(check, 0).toString();
        HoaDon hoaDon = hoaDonServices.fill(ma);
        if (hoaDon.getTrangThai() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn này đã thanh toán !");
            return;
        }
        int index = tbSanPham.getSelectedRow();
        String maSP = tbSanPham.getValueAt(index, 0).toString();
        Integer soLuong = Integer.parseInt(tbSanPham.getValueAt(index, 5).toString());
        if (soLuong == 0) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm đã hết ");
            return;
        }
        GetImeiByMaSPServices getImeiByMaSPServices = new GetImeiByMaSPServices();
        getImeiByMaSPServices.setMaSP(maSP);

        new Imei().setVisible(true);
    }//GEN-LAST:event_tbSanPhamMousePressed

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void tbHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMousePressed
        // TODO add your handling code here:
        fillHoaDon();
    }//GEN-LAST:event_tbHoaDonMousePressed

    private void rbTaiCuaHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbTaiCuaHangMousePressed
        // TODO add your handling code here:
        txtDiaChiNhanHang.setText("Tại cửa hàng");
        txtDiaChiNhanHang.setEditable(false);
        txtDiaChiNhanHang.setEnabled(false);
        txtPhiship.setText("0");
        txtPhiship.setEditable(false);
        txtPhiship.setEnabled(false);
        Integer phiShip = Integer.parseInt(txtPhiship.getText());
        txtPhiShip.setText(String.valueOf(phiShip));
        Integer tongTien = Integer.parseInt(txtTongTien.getText());
        Integer giamGia = Integer.parseInt(txtGiamGia.getText());
        Integer tienPhaiThanhToan = tongTien + phiShip - giamGia;
        Integer tienKhachDua = 0;
        Integer tienTralai = 0;
        if (txtTienKhachDua.getText().equals("")) {
            tienKhachDua = 0;
            tienTralai = 0;
        } else {
            tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
            tienTralai = tienKhachDua - tienPhaiThanhToan;

        }

        if (tienTralai < 0) {
            txtTienTraLai.setText("Khách đưa chưa đủ !");
        } else {
            txtTienTraLai.setText(String.valueOf(tienTralai));
        }

        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));
    }//GEN-LAST:event_rbTaiCuaHangMousePressed

    private void rbTaiCuaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTaiCuaHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbTaiCuaHangActionPerformed

    private void rbShiphangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbShiphangMousePressed
        // TODO add your handling code here:
        txtDiaChiNhanHang.setText("");
        txtDiaChiNhanHang.setEditable(true);
        txtDiaChiNhanHang.setEnabled(true);
        txtPhiship.setText("");
        txtPhiship.setEditable(true);
        txtPhiship.setEnabled(true);
    }//GEN-LAST:event_rbShiphangMousePressed

    private void txtPhishipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhishipKeyReleased
        // TODO add your handling code here:
        String checkSo = "^[0-9]*$";
        if (!txtPhiship.getText().matches(checkSo)) {
            JOptionPane.showMessageDialog(this, "Tiền ship phải là số nguyên dương!");
            return;
        }
        if (txtPhiship.getText().length() > 6) {
            JOptionPane.showMessageDialog(this, "Tiền ship phải nhỏ hơn hoặc bằng 999999 ");
            txtPhiship.setText("0");
            return;
        }
        Integer phiship = Integer.parseInt(txtPhiship.getText());
        if (phiship < 0) {
            JOptionPane.showMessageDialog(this, "Tiền ship phải lớn hơn hoặc bằng 0");
            return;
        }
        Integer tongTien = Integer.parseInt(txtTongTien.getText());
        Integer tienShip = phiship;
        Integer giamGia = Integer.parseInt(txtGiamGia.getText());

        Integer tienPhaiThanhToan = tongTien + tienShip - giamGia;
        Integer tienKhachDua = 0;
        Integer tienTralai = 0;
        if (txtTienKhachDua.getText().equals("")) {
            tienKhachDua = 0;
            tienTralai = 0;
        } else {
            tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
            tienTralai = tienKhachDua - tienPhaiThanhToan;

        }

        if (tienTralai < 0) {
            txtTienTraLai.setText("Khách đưa chưa đủ !");
        } else {
            txtTienTraLai.setText(String.valueOf(tienTralai));
        }

        txtPhiShip.setText(String.valueOf(phiship));
        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));
    }//GEN-LAST:event_txtPhishipKeyReleased

    private void txtTienKhachDuaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTienKhachDuaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaInputMethodTextChanged

    private void txtTienKhachDuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaKeyPressed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // TODO add your handling code here:
        String checkSo = "^[0-9]*$";
        if (txtTienKhachDua.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không được bỏ trống !");
            return;
        }
        if (!txtTienKhachDua.getText().matches(checkSo)) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số nguyên dương!");
            return;
        }
        if (txtTienKhachDua.getText().startsWith("0")) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0 !");
                return;
            }
        if (txtTienKhachDua.getText().length() > 9) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải nhỏ hơn 9999999999 !");
            txtTienKhachDua.setText("");
            txtTienTraLai.setText("0");
            return;
        }
        Integer tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
        if (tienKhachDua < 0) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng 0 !");
            return;
        }
        Integer tongTien = Integer.parseInt(txtTongTien.getText());
        Integer tienShip = Integer.parseInt(txtPhiShip.getText());
        Integer giamGia = Integer.parseInt(txtGiamGia.getText());

        Integer tienPhaiThanhToan = tongTien + tienShip - giamGia;
        Integer tienTraLai = tienKhachDua - tienPhaiThanhToan;

        if (tienTraLai < 0) {
            txtTienTraLai.setText("Khách đưa chưa đủ !");
        } else {
            txtTienTraLai.setText(String.valueOf(tienTraLai));
        }
        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnThanhToanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThanhToanMouseExited

    private void btnThanhToanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMousePressed
        String checkSo = "^[0-9]*$";
        try {
            int indexHoaDon = tbHoaDon.getSelectedRow();
            if (indexHoaDon == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi thanh toán !");
                return;
            }
            if (btnThanhToan.isEnabled() == false) {
                JOptionPane.showMessageDialog(this, "Hóa đơn này đã thanh toán !");
                return;
            }
            int rowGioHang = tbGioHang.getRowCount();
            if (rowGioHang < 1) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng không có sản phẩm nào !");
                return;
            }

            if (txtTienKhachDua.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa không được bỏ trống !");
                return;
            }
            if (!txtTienKhachDua.getText().matches(checkSo)) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số nguyên dương!");
                return;
            }
            if (txtTienKhachDua.getText().startsWith("0")) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0 !");
                return;
            }
            if (txtTienKhachDua.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải nhỏ hơn 9999999999 !");
                txtTienKhachDua.setText("");
                txtTienTraLai.setText("0");
                return;
            }
            Integer tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());

            if (tienKhachDua <= 0) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn 0 !");
                return;
            }
            if (Integer.parseInt(txtTienKhachDua.getText()) < Integer.parseInt(txtTienThanhToan.getText())) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng tiền phải thanh toán !");
                return;
            }
            if (txtGhichu.getText().length() > 100) {
                JOptionPane.showMessageDialog(this, "Ghi chú nhỏ hơn 100 kí tự !");
                txtGhichu.setText("");
                return;
            }
            if (txtDiaChiNhanHang.getText().length() > 100) {
                JOptionPane.showMessageDialog(this, "Địa chỉ nhận hàng nhỏ hơn 100 kí tự !");
                txtDiaChiNhanHang.setText("");
                return;
            }
            String maHD = tbHoaDon.getValueAt(indexHoaDon, 0).toString();
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc thanh toán hóa đơn " + maHD + " !");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngayTao = dtf.format(now);
            Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);

            String ghiChu = txtGhichu.getText();
            Double tongTiendb = Double.parseDouble(txtTongTien.getText());
            BigDecimal tongTien = BigDecimal.valueOf(tongTiendb);
            Double giamGiadb = Double.parseDouble(txtGiamGia.getText());
            BigDecimal giamgia = BigDecimal.valueOf(giamGiadb);
            String makm = "";
            String khuyenMai = (String) cbbGiamGia.getSelectedItem();
            if (khuyenMai.equals("")) {
                makm = null;
            } else {
                String mang[] = khuyenMai.split("--");
                makm = mang[0];
            }
            String diaChi = "";
            if (rbTaiCuaHang.isSelected() == true) {
                diaChi = "Tai cua hang";
            }else{
                diaChi = txtDiaChiNhanHang.getText();
            }
            hoaDonServices.suaHD(maHD, ghiChu, tongTien, giamgia, 2, makm,diaChi);

            HoaDonChiTiet hdct = new HoaDonChiTiet();
            BaoHanh baoHanh = new BaoHanh();
            BaoHanhChiTiet bhct = new BaoHanhChiTiet();
            HoaDon hd = hoaDonServices.fill(maHD);
            loadMaBH();

            baoHanh.setMaBH(maBH);
            baoHanh.setKhachHang(hd.getKhachHang());
            baoHanh.setNgayTao(date);
            baoHanh.setTrangThai(0);
            baoHanhServices.add(baoHanh);
            String imei = "";
            BigDecimal donGia = null;
            BigDecimal giamgia1 = null;

            Calendar c1 = Calendar.getInstance();

            for (int i = 0; i < rowGioHang; i++) {
                c1.setTime(date);
                imei = tbGioHang.getValueAt(i, 2).toString();
                ChiTietSP c = null;
                List<ChiTietSP> chiTietSPs = chiTietSPServices.getImei();
                for (ChiTietSP chiTietSP : chiTietSPs) {
                    if (imei.equals(chiTietSP.getMaImei())) {
                        c = chiTietSP;

                    }
                }
                if (c.getThoiGianBH() == 0) {

                } else {
                    if (c.getThoiGianBH() == 30) {
                        c1.add(Calendar.MONTH, 1);
                    }
                    if (c.getThoiGianBH() == 90) {
                        c1.add(Calendar.MONTH, 3);
                    }
                    if (c.getThoiGianBH() == 180) {
                        c1.add(Calendar.MONTH, 6);
                    }
                    if (c.getThoiGianBH() == 360) {
                        c1.add(Calendar.MONTH, 12);
                    }

                    String d = new SimpleDateFormat("MM-dd-yyyy").format(c1.getTime());
                    Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(d);
                    bhct.setMaImei(c);
                    bhct.setMaBH(baoHanh);
                    bhct.setNgayBatDau(date);
                    bhct.setNgayKetThuc(date2);
                    baoHanhChiTietServices.add(bhct);
                }

            }

            for (int i = 0; i < rowGioHang; i++) {

                imei = tbGioHang.getValueAt(i, 2).toString();

                Double donGiaDb = Double.parseDouble(tbGioHang.getValueAt(i, 4).toString());
                donGia = BigDecimal.valueOf(donGiaDb);
                Integer chietKhau = 0;
                HoaDon hoaDon11 = hoaDonServices.seachbyMa(maHD);

                List<HoaDonChiTiet> list1 = hoaDonChiTietServies.getALL(maHD);
                for (HoaDonChiTiet hoaDonChiTiet : list1) {
                    if (hoaDon11.getKhuyenMai() == null) {
                        chietKhau = 0;
                        HoaDonChiTiet hdct1 = hoaDonChiTietServies.seachbyMaImei(hoaDonChiTiet.getMaImei().getMaImei());
                        String donGiaString = String.valueOf(hdct1.getDonGia());
                        Double giamgiadb = Double.parseDouble(donGiaString) * (chietKhau * 0.01);
                        hoaDonChiTietServies.updateGiamGia(maHD, hoaDonChiTiet.getMaImei().getMaImei(), BigDecimal.valueOf(giamgiadb));
                        continue;

                    }
                    chietKhau = hoaDon11.getKhuyenMai().getChietKhau();
                    HoaDonChiTiet hdct1 = hoaDonChiTietServies.seachbyMaImei(hoaDonChiTiet.getMaImei().getMaImei());
                    String donGiaString = String.valueOf(hdct1.getDonGia());
                    Double giamgiadb = Double.parseDouble(donGiaString) * (chietKhau * 0.01);
                    hoaDonChiTietServies.updateGiamGia(maHD, hoaDonChiTiet.getMaImei().getMaImei(), BigDecimal.valueOf(giamgiadb));

                }
//                if (hoaDon11.getKhuyenMai() == null) {
//                    return;
//                }
//                else{
//                    
//                }

//                 System.out.println(donGiaString);
//                 System.out.println(hoaDon11.getKhuyenMai().getChietKhau());
//                 System.out.println(Double.parseDouble(donGiaString));
//                 System.out.println(hoaDon11.getKhuyenMai().getChietKhau() * 0.01);
//                System.out.println(giamgiadb);
//                System.out.println(giamgiadb);
                HoaDon hd1 = null;
                List<HoaDon> hoaDons = hoaDonServices.getALL();
                for (HoaDon hoaDon : hoaDons) {
                    if (maHD.equals(hoaDon.getMaHD())) {
                        hd1 = hoaDon;

                    }
                }
                ChiTietSP c = null;
                List<ChiTietSP> chiTietSPs = chiTietSPServices.getImei();
                for (ChiTietSP chiTietSP : chiTietSPs) {
                    if (imei.equals(chiTietSP.getMaImei())) {
                        c = chiTietSP;

                    }
                }

//                hdct.setMaHD(hd1);
//                hdct.setMaImei(c);
//                hdct.setSoLuong(1);
//                hdct.setDonGia(donGia);
//                hdct.setGiamGia(BigDecimal.valueOf(1000));
                List<HoaDonChiTiet> list = hoaDonChiTietServies.getALL(maHD);
                for (HoaDonChiTiet hoaDonChiTiet : list) {
                    if (imei.equals(hoaDonChiTiet.getMaImei().getMaImei())) {
                        JOptionPane.showMessageDialog(this, "Thanh toán thành công !");
                        clear();
                        hienThiHoaDon();
                        hoaDonServices.updateNgayTT(date, maHD);
                        int check1 = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn ?");
                        if (check1 != JOptionPane.YES_OPTION) {
                            return;
                        }
                        TaoHoaDonServices taoHoaDonServices = new TaoHoaDonServices();
                        JOptionPane.showMessageDialog(this, "In thành công !");

                        try {
                            taoHoaDonServices.taoHoaDon(maHD);
                        } catch (IOException ex) {
                            Logger.getLogger(QLBanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        return;
                    }

                }

//                hoaDonChiTietServies.add(hdct);
            }

        } catch (ParseException ex) {
            Logger.getLogger(QLBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThanhToanMousePressed

    private void btnHuyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyMouseExited

    private void btnHuyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMousePressed
        try {
            // TODO add your handling code here:
            int indexHoaDon = tbHoaDon.getSelectedRow();
            if (indexHoaDon == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi thanh toán !");
                return;
            }
            if (btnHuy.isEnabled() == false) {
                JOptionPane.showMessageDialog(this, "Hóa đơn này đã thanh toán !");
                return;
            }

            int rowGioHang = tbGioHang.getRowCount();
            String ghiChu = txtGhichu.getText();
            String maHD = tbHoaDon.getValueAt(indexHoaDon, 0).toString();
            int check = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy " + maHD + " !");
            if (check != JOptionPane.YES_OPTION) {
                return;
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            ZonedDateTime now = ZonedDateTime.now();
            String ngayTao = dtf.format(now);
            Date date = new SimpleDateFormat("MM-dd-yyyy").parse(ngayTao);

            hoaDonServices.suaHD(maHD, ghiChu, BigDecimal.valueOf(0), BigDecimal.valueOf(0), 1, null,"");
            for (int i = 0; i < rowGioHang; i++) {
                String imei = tbGioHang.getValueAt(i, 2).toString();
                hoaDonChiTietServies.delete(imei, maHD);
                chiTietSPServices.updateImeiTrangThai(imei, 0);
            }
            hoaDonServices.updateNgaySua(date, maHD);
            JOptionPane.showMessageDialog(this, "Hủy đơn hàng thành công !");

            clear();
            hienThiHoaDon();
            hienThiSanPham();

        } catch (ParseException ex) {
            Logger.getLogger(QLBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHuyMousePressed

    private void btnTaoHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoHoaDonMousePressed

        try {
            if (txtMaHD.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không được để trống !");
                return;
            }
            if (cbbKhachHang.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Dữ liệu khách hàng bị rỗng !");
                return;
            }

            // TODO add your handling code here:
            HoaDon hd = layTTHoaDon();
            if (hd == null) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn đã tồn tại !");
                return;
            }
            if (hoaDonServices.add(hd) == true) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công !");
                hienThiHoaDon();
                loadMaHD();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại");
            }
        } catch (ParseException ex) {
            Logger.getLogger(QLBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTaoHoaDonMousePressed

    private void btnClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearMouseExited

    private void btnClearMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMousePressed
        // TODO add your handling code here:
        hienThiHoaDon();
        clear();
    }//GEN-LAST:event_btnClearMousePressed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel1MousePressed

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseExited

    private void cbbGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiamGiaActionPerformed
        // TODO add your handling code here:
        Integer chietKhau = 0;
        String chietKhauString = (String) cbbGiamGia.getSelectedItem();
//        System.out.println(chietKhauString);
        if (chietKhauString == null) {
            chietKhau = 0;
        }
        if (chietKhauString.equals("")) {
            chietKhau = 0;
        } else {
            String mangString[] = chietKhauString.split("--");
            chietKhau = Integer.parseInt(mangString[2]);
        }

        Integer tongTien = Integer.parseInt(txtTongTien.getText());

        Integer tienShip = Integer.parseInt(txtPhiShip.getText());
        Integer giamGia = tongTien * (chietKhau) / 100;
        txtGiamGia.setText(String.valueOf(giamGia));
        Integer tienPhaiThanhToan = tongTien + tienShip - giamGia;
        Integer tienKhachDua = 0;
        Integer tienTraLai = 0;
        txtTienThanhToan.setText(String.valueOf(tienPhaiThanhToan));
        if (txtTienKhachDua.getText().length() > 10) {
            tienKhachDua = 0;
        }
        if (txtTienKhachDua.getText().equals("")) {
            tienKhachDua = 0;
            tienTraLai = 0;
        } else {
            if (txtTienKhachDua.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải nhỏ hơn 9999999999 !");
                txtTienKhachDua.setText("0");
                return;
            }
            tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
            tienTraLai = tienKhachDua - tienPhaiThanhToan;
        }

        if (tienTraLai < 0) {
            txtTienTraLai.setText("Khách đưa chưa đủ !");
        } else {
            txtTienTraLai.setText(String.valueOf(tienTraLai));
        }


    }//GEN-LAST:event_cbbGiamGiaActionPerformed

    private void cbbGiamGiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbGiamGiaMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbGiamGiaMousePressed

    private void cbbGiamGiaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbGiamGiaMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbGiamGiaMouseReleased

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

        String danhMuc = (String) cbbLocDanhMuc.getSelectedItem();
        String mauSac = (String) cbbLocMauSac.getSelectedItem();
        Integer boNho = Integer.parseInt((String) cbbLocBoNho.getSelectedItem());
        tbSanPham.getColumn("Ảnh").setCellRenderer(new myTableCellRender());
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        //        Integer limit = 8;
        //        List<SanPham> sps = sanPhamServices.loc(danhMuc, mauSac, boNho);
        //        Integer count = sps.size();
        //        Integer soDu = count % limit;
        //        Integer soLamTron = 0;
        //        if (soDu == 0) {
        //            soLamTron = count / limit;
        //        }
        //        if (soDu != 0) {
        //            soLamTron = ((count - soDu) / limit) + 1;
        //        }
        //        Integer toltalPage = soLamTron;

        List<SanPham> sanPhams = sanPhamServices.locSanPham(danhMuc, mauSac, boNho);
        if (sanPhams.size() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu !");
            return;
        }
        String maSP = "";
        Integer tonKho = 0;
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
                    maSP,
                    c.getTenSP(),
                    c.getNsx(),
                    c.getMauSac(),
                    c.getBoNho(),
                    c.getTonKho(),
                    c.getGiaBan(),
                    label
                };
                model.addRow(data);
            }

        }
        JOptionPane.showMessageDialog(this, "Lọc thành công !");
        //        pagination1.setPagegination(1, toltalPage);

    }//GEN-LAST:event_jLabel5MousePressed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel banhang;
    private javax.swing.JLabel btnClear;
    private javax.swing.JLabel btnHuy;
    private javax.swing.JLabel btnTaoHoaDon;
    private javax.swing.JLabel btnThanhToan;
    private keeptoo.KGradientPanel btnThem;
    private keeptoo.KGradientPanel btnThem2;
    private keeptoo.KGradientPanel btnThem4;
    private javax.swing.ButtonGroup buttonGroup1;
    private static javax.swing.JComboBox<String> cbbGiamGia;
    private javax.swing.JComboBox<String> cbbKhachHang;
    private javax.swing.JComboBox<String> cbbLocBoNho;
    private javax.swing.JComboBox<String> cbbLocDanhMuc;
    private javax.swing.JComboBox<String> cbbLocMauSac;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private static javax.swing.JRadioButton rbShiphang;
    private static javax.swing.JRadioButton rbTaiCuaHang;
    public static javax.swing.JTable tbGioHang;
    private javax.swing.JTable tbHoaDon;
    private static javax.swing.JTable tbSanPham;
    private static javax.swing.JTextArea txtDiaChiNhanHang;
    private javax.swing.JTextField txtGhichu;
    private static javax.swing.JLabel txtGiamGia;
    private javax.swing.JLabel txtKhachHang;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtMaHoaDon;
    private javax.swing.JLabel txtNgayTao;
    private javax.swing.JLabel txtNhanVien;
    private static javax.swing.JLabel txtPhiShip;
    private static javax.swing.JTextField txtPhiship;
    private static javax.swing.JTextField txtTienKhachDua;
    private static javax.swing.JLabel txtTienThanhToan;
    private static javax.swing.JLabel txtTienTraLai;
    private static javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
