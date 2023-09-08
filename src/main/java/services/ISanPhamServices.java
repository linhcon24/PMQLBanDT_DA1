/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.SanPham;
import java.util.List;

/**
 *
 * @author HANGOCHAN
 */
public interface ISanPhamServices {
    public List<SanPham> getALL();
    public boolean add(SanPham sanPham);
    public boolean update(SanPham sanPham);
    public boolean delete(SanPham sanPham);
    public SanPham seachbyMa(String ma);
    
}
