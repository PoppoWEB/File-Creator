package create;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public interface Create { 
    public default File Open() {
        try {
            JFileChooser fileChooser = new JFileChooser("c:");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int selected = fileChooser.showSaveDialog(null);
            if (selected == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            }
        } catch (Exception e) {
            messagelog("フォルダの設定に失敗しました。","Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public default void messagelog(String msg, String title, int type) {
        JOptionPane.showMessageDialog(null, msg, title, type);
    }

    public abstract String create(File file, int start, int end, String title, String ext);
    public abstract String delete(File file, String keyword);
}