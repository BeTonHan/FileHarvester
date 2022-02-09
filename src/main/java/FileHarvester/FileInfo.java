package FileHarvester;

public class FileInfo  implements  Comparable<FileInfo> {
    private String path;
    private String fileName;
    private String checkSum;

    public FileInfo(String path, String fileName, String checkSum){
        this.path = path;
        this.fileName = fileName;
        this.checkSum = checkSum;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCheckSum() {
        return checkSum;
    }

    @Override
    public int compareTo(FileInfo o) {
        return checkSum.compareTo(o.getCheckSum());
    }
}
