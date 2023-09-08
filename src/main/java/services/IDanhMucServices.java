/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import domainmodels.DanhMuc;
import java.util.List;

/**
 *
 * @author adm
 */
public interface IDanhMucServices  {
     public List<DanhMuc> getALL();
    public boolean add(DanhMuc d);
    public boolean update(DanhMuc d);
    public boolean delete(DanhMuc d);
    public DanhMuc seachbyMa(String ma);
    public List<DanhMuc> timKiembyTrangThai(String ten);
    public DanhMuc layMa();
}
