package create;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import error.FileNotCreateException;

public class FilesCreate implements Create {
    private static Create create = new FilesCreate();

    private FilesCreate() { }

    public static Create getCreate() {
        return create;
    }

    @Override
    public String create(File file, int start, int end, String title, String ext) {
        String path = file.toString();
        StringBuffer buffer = new StringBuffer();
        String dot = ".";

        if (ext.equals("")) { dot = "";}

        try {
            if (!file.exists()) { throw new FileNotCreateException(); }
            
            for (int i = start; i <= end; i++) {
                file = new File(path +"/"+ title + i + dot + ext);
                if (!file.createNewFile()) {
                    buffer.append("Failure: " + file.getName() + "\n");
                    throw new FileNotCreateException();
                } else { 
                    buffer.append("Create: " + file.getName() + "\n");
                }
            }
            messagelog("ファイル作成を終了します。", "Success", JOptionPane.INFORMATION_MESSAGE);
            return buffer.toString();
        } catch (FileNotCreateException e) {
            messagelog("ファイルの作成に失敗しました。", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            messagelog(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 

        buffer.append("=== Failure === \n");
        return buffer.toString();
    }

    @Override
    public String delete(File file, String keyword) {
        StringBuffer buffer = new StringBuffer();

        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String filename = files[i].getName();
                    if (filename.indexOf(keyword) != -1) {
                        files[i].delete();
                        buffer.append("Delete: " + files[i].getName() + "\n");
                    }
                } else if (files[i].isDirectory()) {
                    buffer.append(delete(files[i], keyword));
                }
            }
            return buffer.toString();
        } else {
            messagelog("フォルダが存在していません。", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        buffer.append("=== Failure === \n");
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "[File]";
    }
}