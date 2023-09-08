/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BoNhoTrong;
import java.util.List;
import repositories.QLBoNhoTrongRepositories;

/**
 *
 * @author Tungt
 */
public class QLBoNhoTrongServices implements IQLBoNhoTrongServices {

    QLBoNhoTrongRepositories bntr;

    public QLBoNhoTrongServices() {
        bntr = new QLBoNhoTrongRepositories();
    }

    @Override
    public List<BoNhoTrong> getALL() {
        return bntr.getAll();
    }

    @Override
    public boolean add(BoNhoTrong m) {
        return bntr.add(m);
    }

    @Override
    public boolean update(BoNhoTrong m) {
        return bntr.update(m);
    }

    @Override
    public boolean delete(BoNhoTrong m) {
        return bntr.delete(m);
    }

    @Override
    public BoNhoTrong seachbyMa(String ma) {
        return bntr.seachbyMa(ma);
    }

    @Override
    public BoNhoTrong layMa() {
        return bntr.layMa();
    }

    @Override
    public List<BoNhoTrong> timKiembyTrangThai(Integer dungLuong) {
        return bntr.timKiembyTrangThai(dungLuong);
    }
  
}
