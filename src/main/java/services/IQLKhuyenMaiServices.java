/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.KhuyenMai;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IQLKhuyenMaiServices {
    public List<KhuyenMai> getAll();
    public boolean add(KhuyenMai khuyenMai);
    public boolean update(KhuyenMai khuyenMai);
    public boolean delete(KhuyenMai khuyenMai);
    public KhuyenMai seachbyMa(String ma);
    public KhuyenMai layMa();
    public  List<KhuyenMai> getAllByMa(String ma);
    public void updatebyTrangThai(String maString, Integer trangThai);
    
}
