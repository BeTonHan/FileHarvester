package FileHarvester;


import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OOFileManipulator {
    public String[] readColumn(String clnName) {
        String[] clnString = new String[0];
        try {
            SpreadSheet spread = new SpreadSheet(new File("C:\\Users\\BT\\Nextcloud\\FernUni Hagen\\Praktikum\\ArchivDB.ods"));
            System.out.println("Number of sheets: " + spread.getNumSheets());
            Range range = spread.getSheet(0).getDataRange();
            clnString = new String[range.getLastRow()];
            //System.out.println("Das Sheet hat " + range.getLastRow() + " Reihen und " + range.getLastColumn() + " Spalten." );
            for (int i = 0; i < range.getLastColumn(); i++) {
                //System.out.println("Spaltentitel lautet " + String.valueOf(range.getCell(0, i).getValue()));
                if (String.valueOf(range.getCell(0, i).getValue()).equals(clnName)) {
                    //System.out.println("Spalte " + clnName + " an Stelle " + i + " gefunden.");
                    for (int j = 0; j < range.getLastRow(); j++) {
                        clnString[j] = String.valueOf(range.getCell(j, i).getValue());
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clnString;
    }

    public void writeToSheet (ArrayList<FileInfo> fileInfo){
        try {
            File file = new File("FileInfo.ods");
            SpreadSheet spread = new SpreadSheet(file);
            int rows = fileInfo.size() + 1;
            int columns = 3;
            Sheet sheet = new Sheet("FileInfo", rows, columns);
            //SpreadSheet spread = new SpreadSheet(new File("FileInfo.ods"));

            Range range = sheet.getDataRange();
            range.getCell(0, 0).setValue("Dateiname");
            range.getCell(0, 1).setValue("Pfad");
            range.getCell(0, 2).setValue("Hashwert");
            for (int i = 1; i < rows; i++) {
                range.getCell(i, 0).setValue(fileInfo.get(i - 1).getFileName());
                range.getCell(i, 1).setValue(fileInfo.get(i - 1).getPath());
                range.getCell(i, 2).setValue(fileInfo.get(i - 1).getCheckSum());
            }
            spread.addSheet(sheet, spread.getNumSheets());
            spread.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
