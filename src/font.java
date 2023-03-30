import java.io.File;

public class font {
    private File file;
    private int type;
    
    public font(File file, int type) {
        this.file = file;
        this.type = type;
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    
}
