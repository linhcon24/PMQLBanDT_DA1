/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BaoHanhChiTiet;
import java.util.List;
import repositories.QLBaoHanhChiTietRepositories;

/**
 *
 * @author HANGOCHAN
 */
public class QLBaoHanhChiTietServices implements IQLBaoHanhChiTietServices{
    QLBaoHanhChiTietRepositories qlbhctr;
    
    public QLBaoHanhChiTietServices(){
        qlbhctr = new QLBaoHanhChiTietRepositories();
    }

    @Override
    public List<BaoHanhChiTiet> getAll(String ma) {
        return qlbhctr.getAll(ma);
    }

    @Override
    public boolean add(BaoHanhChiTiet m) {
        return qlbhctr.add(m);
    }

    @Override
    public boolean update(BaoHanhChiTiet m) {
        return qlbhctr.update(m);
    }

    @Override
    public boolean delete(BaoHanhChiTiet m) {
        return qlbhctr.delete(m);
    }

    @Override
    public BaoHanhChiTiet seachbyMa(String ma) {
        return qlbhctr.seachbyMa(ma);
    }

    @Override
    public void xoaBaoHanh(String ma) {
        qlbhctr.xoaBaoHanh(ma);
    }
}
