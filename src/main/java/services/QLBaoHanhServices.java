/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BaoHanh;
import java.util.List;
import repositories.QLBaoHanhRepositories;

/**
 *
 * @author HANGOCHAN
 */
public class QLBaoHanhServices implements IQLBaoHanhServices{
    QLBaoHanhRepositories qlbhr;
    
    public QLBaoHanhServices(){
        qlbhr = new QLBaoHanhRepositories();
    }

    @Override
    public List<BaoHanh> getALL() {
        return qlbhr.getAll();
    }

    @Override
    public boolean add(BaoHanh m) {
        return qlbhr.add(m);
    }

    @Override
    public boolean update(BaoHanh m) {
        return qlbhr.update(m);
    }

    @Override
    public boolean delete(BaoHanh m) {
        return qlbhr.delete(m);
    }

    @Override
    public BaoHanh seachbyMa(String ma) {
        return qlbhr.seachbyMa(ma);
    }

    @Override
    public BaoHanh layMa() {
        return qlbhr.layMa();
    }
}
