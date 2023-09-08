/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import views.DoiMatKhauPanel;
import views.QLBanHangPanel;
import views.QLBaoHanhPanel;
import views.QLChiTietSanPhamPanel;
import views.QLHoaDonPanel;
import views.QLKhachHangPanel;
import views.QLKhuyenMaiPanel;
import views.QLNhanVienPanel;
import views.QLThongKePanel;

/**
 *
 * @author HANGOCHAN
 */
public class ChuyenManHinhServices {
    private Webcam webcam = null;
    private JPanel root;
    private String kinSelected;

    private List<MenuServices> listItem = null;

    public ChuyenManHinhServices(JPanel jpnroot) {
        this.root = jpnroot;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kinSelected = "sanpham";

        jpnItem.setBackground(new Color(96, 100, 191));
        jlbItem.setBackground(new Color(96, 100, 191));
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new QLChiTietSanPhamPanel());
        root.validate();
        root.repaint();

    }

    public void setEvent(List<MenuServices> listItems) {
        this.listItem = listItems;

        for (MenuServices item : listItems) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;

        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
             kinSelected = kind;
            jpnItem.setBackground(new Color(96,100,191));
            jlbItem.setBackground(new Color(96,100,191));
        }

        @Override
        public void mousePressed(MouseEvent e) {
           
            switch (kind) {
                case "sanpham":
                    node = new QLChiTietSanPhamPanel();
                    
                    break;
                    case "doimatkhau":
                    node = new DoiMatKhauPanel();
                    break;
                    case "nhanvien":
                    node = new QLNhanVienPanel();
                    break;
                    case "banhang":
                    node = new QLBanHangPanel();
                    break;
                    case "khuyenmai":
                    node = new QLKhuyenMaiPanel();
                    break;
                    case "khachhang":
                    node = new QLKhachHangPanel();
                    break;
                    case "thongke":
                    node = new QLThongKePanel();
                    break;
                    case "hoadon":
                    node = new QLHoaDonPanel();
                    break;
                    case "baohanh":
                    node = new QLBaoHanhPanel();
                    break;
                default:
//                    node = new QLChiTietSanPhamPanel();
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            jpnItem.setBackground(new Color(96,100,191));
//            jlbItem.setBackground(new Color(96,100,191));
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            if (!kinSelected.equalsIgnoreCase(kind)) {
//                jpnItem.setBackground(new Color(76,175,80));
//            jlbItem.setBackground(new Color(76,175,80));
//            }
        }
        private void setChangeBackground(String kind){
            for (MenuServices item : listItem) {
                if (item.getKind().equalsIgnoreCase(kind)) {
                    item.getJlb().setBackground(new Color(96,100,191));
                    item.getJpn().setBackground(new Color(96,100,191));
                }
                else{
                    item.getJlb().setBackground(new Color(76,175,80));
                    item.getJpn().setBackground(new Color(76,175,80));
                }
            }
        }
    }

}
