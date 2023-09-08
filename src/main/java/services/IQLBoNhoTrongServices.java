/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BoNhoTrong;
import java.util.List;

/**
 *
 * @author Tungt
 */
public interface IQLBoNhoTrongServices {

    public List<BoNhoTrong> getALL();

    public boolean add(BoNhoTrong m);

    public boolean update(BoNhoTrong m);

    public boolean delete(BoNhoTrong m);

    public BoNhoTrong seachbyMa(String ma);

    public BoNhoTrong layMa();

    public List<BoNhoTrong> timKiembyTrangThai(Integer dungLuong);

}
