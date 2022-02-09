package FileHarvester;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class FileComparison {

    ArrayList<FileInfo> mergeList = new ArrayList<FileInfo>();
    ArrayList<FileInfo> uniqueList1 = new ArrayList<FileInfo>();
    ArrayList<FileInfo> uniqueList2 = new ArrayList<FileInfo>();


    public ArrayList<FileInfo> getMergeList() {
        return mergeList;
    }

    public ArrayList<FileInfo> getUniqueList1() {
        return uniqueList1;
    }

    public ArrayList<FileInfo> getUniqueList2() {
        return uniqueList2;
    }

    public void compareFileList(ArrayList<FileInfo> list1, ArrayList<FileInfo> list2) {
        int counter = 0;
        boolean listsInverted = false;
        ArrayList<FileInfo> biggerList, smallerList;

        if (list1.size() >= list2.size()) {
            biggerList = list1;
            smallerList = list2;
        }
        else {
            biggerList = list2;
            smallerList = list1;
            listsInverted = true;
        }

        for (FileInfo file1 : biggerList) {
            boolean pairingFound = false;
            for (int i = counter; i < smallerList.size(); i++){
                if (file1.getCheckSum().equals(smallerList.get(i).getCheckSum())) {
                    mergeList.add(file1);
                    counter++;
                    pairingFound = true;
                    break;
                }
            }
            if (!pairingFound){
                if (listsInverted) uniqueList2.add(file1);
                else uniqueList1.add(file1);
            }
        }
        while (counter < smallerList.size()){
            if (listsInverted) uniqueList1.add(smallerList.get(counter));
            else uniqueList2.add(smallerList.get(counter));
            counter++;
        }
    }

    public void printToFile() throws IOException {
        StringBuilder fileInfo = new StringBuilder();
        fileInfo.append( "List of similar files " + System.lineSeparator());
        for(FileInfo file : mergeList) fileInfo.append(file.getFileName() + " (" + file.getPath() + " )");
        fileInfo.append(System.lineSeparator() + "Unique files in first folder " + System.lineSeparator());
        for(FileInfo file : uniqueList1) fileInfo.append(file.getFileName() + " (" + file.getPath() + " )");
        fileInfo.append(System.lineSeparator() + "Unique files in second folder " + System.lineSeparator());
        for(FileInfo file : uniqueList2) fileInfo.append(file.getFileName() + " (" + file.getPath() + " )");
        FileUtils.writeStringToFile(new File("FileComparison.txt"), fileInfo.toString(), StandardCharsets.UTF_8);
    }
}
