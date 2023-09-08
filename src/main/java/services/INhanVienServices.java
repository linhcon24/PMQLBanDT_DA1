/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.NhanVien;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HANGOCHAN
 */
public interface INhanVienServices {
    public List<NhanVien> getALL();
    public boolean add(NhanVien nhanVien);
    public boolean update(NhanVien nhanVien);
    public boolean delete(String ma);
    public NhanVien seachbyMa(String ma);
    public NhanVien fill(String maNV);
    public List<NhanVien> getALLbyVaiTro(Integer vaiTro);
    public List<NhanVien> phanTrang(Integer limitPage, Integer page);
    public List<NhanVien> getALLbyTrangThai(Integer trangThai);
    public List<NhanVien> timKiembyTrangThai(String ten);
    public List<NhanVien> timKiemPhanTrang(String ten , Integer limitPage, Integer page);
    public List<NhanVien> locPhanTrang(Integer vaitro , Integer limitPage, Integer page);
    
}
