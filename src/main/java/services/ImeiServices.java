/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;

/**
 *
 * @author HANGOCHAN
 */
public class ImeiServices {
    public static List<String> list ;
    public static List<String> listXoa ;
    public static List<String> getList() {
        return list;
    }

    public static void setList(List<String> list) {
        ImeiServices.list = list;
    }

    public static List<String> getListXoa() {
        return listXoa;
    }

    public static void setListXoa(List<String> listXoa) {
        ImeiServices.listXoa = listXoa;
    }
    
    
    
}
