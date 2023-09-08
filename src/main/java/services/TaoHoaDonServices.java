/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.HoaDon;
import domainmodels.HoaDonChiTiet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import repositories.HoaDonChiTietRepositories;
import repositories.HoaDonRepositories;

/**
 *
 * @author HANGOCHAN
 */
public class TaoHoaDonServices {
    public void taoHoaDon(String maHD) throws FileNotFoundException, IOException{
        HoaDonRepositories hdr = new HoaDonRepositories();
        HoaDon hd = hdr.fill(maHD);
        Date date = hd.getNgayMua();
        String sdf = new SimpleDateFormat("dd/MM/yyyy").format(date);
        HoaDonChiTietRepositories hoaDonChiTietRepositories = new HoaDonChiTietRepositories();
        List<HoaDonChiTiet> services = hoaDonChiTietRepositories.getALL(maHD);
       String patch =  "D:\\PRO1041\\hoadon\\"+maHD+".txt" ;
        File file = new File(patch);
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write("-----------|----------------------------------------------------------|-------\n");
        outputStreamWriter.write("                                  F-PHONE                                     \n");
        outputStreamWriter.write("                            48 Ngo 99 Cau Dien , HN                          \n");
        outputStreamWriter.write("------------------------------------------------------------------------------\n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                             Hoa Don Thanh Toan                            \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("------------------------------------------------------------------------------\n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("  Hoa don             : " +hd.getMaHD() +                        "                  \n");
        outputStreamWriter.write("  Ngay thanh toan     : " +sdf+                        "                 \n");
        outputStreamWriter.write("  Nhan vien           : " +hd.getNhanVien().getMaNV()+           "                   \n");
        outputStreamWriter.write("  Khach hang          : " +hd.getKhachHang().getTenKH()+         "                   \n");
         outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("-----------------------------------------------------------------------------\n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("Ten SP                      Imei                 So luong     Don gia       \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                                            \n");
        for (HoaDonChiTiet service : services) {
        outputStreamWriter.write(""+service.getMaImei().getSanPham().getTenSP() +"                            \n"); 
        outputStreamWriter.write("                        "+ service.getMaImei().getMaImei() +"              1        " + service.getDonGia()+ " \n");
         outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("-----------------------------------------------------------------------------\n");
        }
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                 Tong tien  : " +hd.getTongTien()+" \n");
        outputStreamWriter.write("                                                 Giam gia   : " +hd.getGiamGia()+"   \n");
        outputStreamWriter.write("                                                 Thanh tien : " +hd.getTongTien().subtract(hd.getGiamGia())+"   \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("-----------------------------------------------------------------------------\n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("                      ----Cam on va hen gap lai !----                       \n");
        outputStreamWriter.write("                                                                            \n");
        outputStreamWriter.write("-----------|----------------------------------------------------------|------\n");
        outputStreamWriter.flush();
    }
       
}
