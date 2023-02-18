package create;

import java.io.File;

import javax.swing.JOptionPane;

import error.FolderNotCreateException;

public class FolderCreate implements Create{
    private static Create create = new FolderCreate();

    private FolderCreate() { }

    public static Create getCreate() {
        return create;
    }

    @Override
    public String create(File file, int start, int end, String title, String ext) {
        String path = file.toString();
        StringBuffer buffer = new StringBuffer();

        try {
            if (!file.exists()) { throw new FolderNotCreateException(); }
            
            for (int i = start; i <= end; i++) {
                file = new File(path + "/"+ title + i);
                if (!file.mkdir()) {
                    buffer.append("Failure: " + file.getName() + "\n");
                    throw new FolderNotCreateException();
                }
                buffer.append("Create: " + file.getName() + "\n");
            }
            messagelog("フォルダ作成を終了します。", "Success", JOptionPane.INFORMATION_MESSAGE);
            return buffer.toString();
        } catch (FolderNotCreateException e) {
            messagelog("フォルダの作成に失敗しました。", "Error", JOptionPane.ERROR_MESSAGE);
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
                if (files[i].isDirectory()) {
                    String foldername = files[i].getName();
                    if (foldername.indexOf(keyword) != -1) {
                        buffer.append(olldelete(files[i]));
                        buffer.append("Delete: " + foldername + "\n");
                        files[i].delete();
                    }
                }
            }
            return buffer.toString();
        } else {
            messagelog("フォルダが存在していません。", "Error", JOptionPane.ERROR_MESSAGE);
        }

        buffer.append("=== Failure === \n");
        return buffer.toString();
    }

    private String olldelete(File file) {
        StringBuffer buffer = new StringBuffer();
        File[] files = file.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
            } else if (files[i].isDirectory()) {
                buffer.append(olldelete(files[i]));
                files[i].delete();
            }
            buffer.append("Delete: " + files[i].getName() + "\n");
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "[Folder]";
    }
}