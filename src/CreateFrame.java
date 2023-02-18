import javax.swing.*;
import javax.swing.border.*;

import create.Create;
import create.FilesCreate;
import create.FolderCreate;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class CreateFrame extends JFrame implements ActionListener {
    private File datafile;
    private Create create = FilesCreate.getCreate();
    private ButtonGroup filefolder = new ButtonGroup();
    private JRadioButton file = new JRadioButton("File",true);
    private JRadioButton folder = new JRadioButton("Folder",false);
    private Button Sourcebutton = new Button("Source");
    private Button Createbutton = new Button("Create");
    private Button Deletebutton = new Button("Delete");
    private Button Clearbutton = new Button("Clear");
    private TextArea Log = new TextArea(18,30);
    private TextField Ext = new TextField(10);
    private TextField Start = new TextField(3);
    private TextField End = new TextField(3);
    private TextField Title = new TextField(10);
    private TextField Keyword = new TextField(30);
    private TextField Source = new TextField(30);
    
    public CreateFrame(String title) {
        super(title);
        setBackground(Color.lightGray);

        JPanel mainbox = new JPanel();
        mainbox.setLayout(new BoxLayout(mainbox,BoxLayout.Y_AXIS));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.add(setradio());
        left.add(setsource());

        JPanel createpane = new JPanel();
        createpane.setLayout(new BoxLayout(createpane, BoxLayout.Y_AXIS));
        createpane.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Create"));
        createpane.add(setcreate());
        left.add(createpane);

        JPanel deletepane = new JPanel();
        deletepane.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Delete"));
        deletepane.add(setdelete());
        left.add(deletepane);
        
        JPanel right = new JPanel();
        right.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Log"));
        right.add(setLog());
        
        JPanel Component = new JPanel();
        Component.setLayout(new BoxLayout(Component,BoxLayout.X_AXIS));
        Component.add(left);
        Component.add(right);

        mainbox.add(Component);
        getContentPane().add(mainbox);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel setradio() {
        JPanel panel = new JPanel();

        file.addActionListener(this);
        folder.addActionListener(this);

        filefolder.add(file);
        filefolder.add(folder);

        panel.add(file);
        panel.add(folder);

        return panel;
    }

    private JPanel setsource() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        JPanel left = new JPanel();
        left.add(Sourcebutton);

        JPanel right = new JPanel();
        right.add(Source);

        Sourcebutton.addActionListener(this);
        Source.addActionListener(this);

        panel.add(left);
        panel.add(right);

        return panel;
    }

    private JPanel setcreate() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2,2));
        JLabel title = new JLabel("Title: ");
        center.add(title);
        center.add(Title);
        
        JLabel Extlabel = new JLabel("Ext: ");
        center.add(Extlabel);
        center.add(Ext);
        panel.add(center);

        JPanel number = new JPanel();
        number.setLayout(new GridLayout(1,2));

        JPanel starts = new JPanel();
        JLabel st = new JLabel("Start: ");
        starts.add(st);
        starts.add(Start);
        number.add(starts);

        JPanel and = new JPanel();
        and.add(new JLabel("----------->"));
        number.add(and);

        JPanel ends = new JPanel();
        JLabel ed = new JLabel("End: ");
        ends.add(ed);
        ends.add(End);
        number.add(ends);
        panel.add(number);

        JPanel buttonpane = new JPanel();
        Createbutton.addActionListener(this);
        buttonpane.add(Createbutton);
        panel.add(buttonpane);

        return panel;
    }

    private JPanel setdelete() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JPanel keypane = new JPanel();
        JLabel keyword = new JLabel("Keyword: ");
        
        keypane.add(keyword);
        keypane.add(Keyword);
        panel.add(keypane);

        JPanel delpane = new JPanel();
        Deletebutton.addActionListener(this);
        delpane.add(Deletebutton);
        panel.add(delpane);

        return panel;
    }

    private JPanel setLog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        Log.setEditable(false);
        Log.setBackground(Color.white);
        Clearbutton.addActionListener(this);
        panel.add(Log);
        panel.add(Clearbutton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == file) {
            create = FilesCreate.getCreate();
            Ext.setEditable(true);
        } else if (e.getSource() == folder) {
            create = FolderCreate.getCreate();
            Ext.setEditable(false);
        } else if (e.getSource() == Sourcebutton) {
            datafile = create.Open();
            if (datafile != null) {
                Source.setText(datafile.toString());
            }
        } else if (e.getSource() == Createbutton) {
            if (Source.getText().equals("")) {
                messagelog("Sourceが設定されていません。", "Setting");
            } else if (Title.getText().equals("")) {
                messagelog("Titleが設定されていません。", "Setting");
            } else if (Start.getText().equals("")) {
                messagelog("Startが設定されていません。", "Setting");
            } else if (End.getText().equals("")) {
                messagelog("Endが設定されていません。", "Setting");
            } else if (Integer.parseInt(Start.getText()) > Integer.parseInt(End.getText())) {
                messagelog("EndはStartより大きい値に設定してください。(Start < End)", "Setting");
            } else if (Ext.getText().indexOf(".") != -1 && file.isSelected()) {
                messagelog(". は付けられません。", "Setting");
            } else {
                Log.append(create + "\n" + create.create(datafile, Integer.parseInt(Start.getText()), Integer.parseInt(End.getText()), Title.getText() , Ext.getText()) + "\n");
            }

        } else if (e.getSource() == Deletebutton) {
            if (Source.getText().equals("")) {
                messagelog("Sourceが設定されていません。", "Setting");
            } else if (Keyword.getText().equals("")) {
                messagelog("Keywordが設定されていません。", "Setting");
            } else {
                Log.append(create + "\n" + create.delete(datafile, Keyword.getText()) + "\n");
                messagelog(create + "の削除が終了しました。", "Log");
            }
        } else if (e.getSource() == Clearbutton) {
            Log.setText("");
        }
    }

    private void messagelog(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}