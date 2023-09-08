/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.nio.file.Paths;

/**
 *
 * @author HANGOCHAN
 */
public class BarCodeServices {
    public void taoBarCode(String imei){
        try {
            String patch = "D:\\PRO1041\\barcode\\"+imei+".jpg";
            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(imei, BarcodeFormat.CODE_128, 1500, 700);
            
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(patch));
            
        } catch (Exception e) {
        }
    }
}
