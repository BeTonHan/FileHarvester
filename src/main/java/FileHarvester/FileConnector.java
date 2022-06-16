
package FileHarvester;
/*
import java.util.ArrayList;

public class FileConnector {
    ArrayList<MetaFileMatch> matchList = new ArrayList<MetaFileMatch>();

    public void connectMetaToFiles(String[] surnames, String[] names, String[] fileNames){
        for(String fileName : fileNames) {
            for (int i = 0; i < names.length; i++) {
                //Vergleich Vorname Nachname
                compareNameFile(i,names[i] + " " + surnames[i], fileName);
                //Vergleich Nachname, Vorname
                compareNameFile(i,surnames[i] + ", " + names[i], fileName);
                //Vergleich Nachnamenskürzel, Vorname
                compareNameFile(i,surnames[i].charAt(0) + "., " + names[i], fileName);
                //Vergleich Nachnamenskürzel, Vornamenskürzel
                compareNameFile(i,surnames[i].charAt(0) + "., " + names[i].charAt(0) + ".", fileName);
            }
        }
    }

    private void compareNameFile(int index, String name, String fileName) {
            int searchMeLength = fileName.length();
            int findMeLength = name.length();
            MetaFileMatch metaFileMatch = new MetaFileMatch();
            metaFileMatch.setId(index);
            boolean foundIt = false;
            for (int i = 0;
                 i <= (searchMeLength - findMeLength);
                 i++) {
                if (fileName.regionMatches(i, name, 0, findMeLength)) {
                    foundIt = true;
                    System.out.println("Match zwischen Filename und Metadaten gefunden: " + fileName.substring(i, i + findMeLength));

                    break;
                }
            }
            if (!foundIt) {
                System.out.println("Kein Match gefunden für Filename " + fileName);
                MetaFileMatch metaFileMatch = new MetaFileMatch();
                metaFileMatch.setId(index);
                metaFileMatch.addFileName(fileName);
                matchList.add(metaFileMatch);
            }
        metaFileMatch.addFileName(fileName);
        matchList.add(metaFileMatch);
    }

    private void compareShortSurname(String[] field1, String[] field2) {
    }

    private void compareShortSurnameAndName(String[] field1, String[] field2) {
    }
    private void compareSurnameName(String[] field1, String[] field2) {
    }

    private void createBestMatch() {
    }

}
*/