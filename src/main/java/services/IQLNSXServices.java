/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import domainmodels.NSX;
import java.util.List;

/**
 *
 * @author hodangquan
 */
public interface IQLNSXServices {
    public List<NSX> getALL();
    public boolean add(NSX nsx);
    public boolean update(NSX nsx);
    public boolean delete(NSX nsx);
    public NSX seachbyMa(String ma);
    public NSX layMa();
    public List<NSX> timKiembyTrangThai(String ten);
}
