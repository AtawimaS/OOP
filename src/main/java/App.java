import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Main;

class data_guru {
    String ID_guru;
    String nama_guru;
    String Password_guru;
    String Email_guru;

    public static data_guru fromJson(String namaFILE) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(namaFILE)) {
            return gson.fromJson(reader, data_guru.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String toJson(data_guru obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }
}

class Student {
    String nama;
    String id;
    String gender;
    int umur;
    Kelas kelas;
    ArrayList<MataPelajaran> MataPelajaranList = new ArrayList<>();

    public void tambahMataPelajaran(MataPelajaran mataPelajaran) {
//        MataPelajaranList = new ArrayList<>();
        MataPelajaranList.add(mataPelajaran);
    }

    public void View(String namaFile) {
        Student siswaRead = Student.fromJson(namaFile);
        if (siswaRead != null) {
            System.out.println("ID      : " + siswaRead.id);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1. Nama    : " + siswaRead.nama);
            System.out.println("2. Gender  : " + siswaRead.gender);
            System.out.println("3. Umur    : " + siswaRead.umur);
            System.out.println("4. Kelas   : " + siswaRead.kelas.namaKelas);

            System.out.println("5. Mata Pelajaran:");
            for (MataPelajaran mataPelajaran : siswaRead.MataPelajaranList) {
                System.out.println("  - " + mataPelajaran.namaPelajaran + " (Guru: " + mataPelajaran.namaGuru + ")");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static Student fromJson(String namaFILE) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(namaFILE)) {
            return gson.fromJson(reader,  Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Kelas {
    String namaKelas;
    String walikelas;

    public void inputKelas(Scanner input) {
        System.out.print("Input nama kelas: ");
        this.namaKelas = input.next();
        System.out.print("Input wali kelas: ");
        this.walikelas = input.next();
    }
}

class MataPelajaran extends Student {
    String namaPelajaran;
    String namaGuru;

    public void inputMataPelajaran(Scanner input) {
        System.out.print("Input nama mata pelajaran: ");
        this.namaPelajaran = input.next();
        System.out.print("Input nama guru: ");
        this.namaGuru = input.next();
    }
}


public class App extends JFrame {
    private JPanel Login;
    private JLabel dataSiswaSMAStLabel;
    private JLabel Username;
    private JLabel Password;
    private JPasswordField dwasPasswordField;
    private JFormattedTextField dawdFormattedTextField;
    private JButton submitButton_LOGIN;
    private JPanel Daftar;
    private JPanel Main;
    private JPanel Menu;
    private JLabel Menu_Title;
    private JButton createButton;
    private JButton viewButton;
    private JButton editButton;
    private JButton exitButton;
    private JTextField Add_IDguru;
    private JTextField Add_Namaguru;
    private JTextField Add_Passwordguru;
    private JTextField Add_Emailguru;
    private JTabbedPane Login_Register;
    private JButton registerButton;
    private JPanel Create;
    private JTextField textField1;
    private JRadioButton maleRadioButton;
    private JTextField textField2;
    private JTextField textField3;
    private JSplitPane Awal;

    public App() {
        String eks = ".json";
//        setUndecorated(true);
//        initComponents();
////        setOpacity(0.7f);
//        setLocationRelativeTo(null);
//        setShape(new RoundRectangle2D.Double(0,0, getWidth(), getHeight(),20,20));
        setContentPane(Login_Register);
        setTitle("OOP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 200);
        setLocationRelativeTo(null);
        setVisible(true);


        submitButton_LOGIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = dawdFormattedTextField.getText();
                char[] passwordChars = dwasPasswordField.getPassword();
                String password = new String(passwordChars);
                String namaFile1 = username.concat(eks);

                if (!username.isEmpty() && password.length() > 0) {
                    try {
                        data_guru guru_Read = data_guru.fromJson(namaFile1);
                        if (guru_Read != null) {
                            if (guru_Read.Password_guru.equals(password)) {
                                JOptionPane.showMessageDialog(submitButton_LOGIN, "Hallo " + guru_Read.nama_guru);
                                App.this.setVisible(false);
                                JFrame menu1 = new JFrame("Menu");
                                menu1.setContentPane(Menu);
                                menu1.setSize(500, 800);
                                menu1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                menu1.setLocationRelativeTo(null);
                                menu1.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(submitButton_LOGIN, "Password tidak sesuai!!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(submitButton_LOGIN, "username anda belum terdaftar!!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(submitButton_LOGIN, "Terjadi kesalahan saat membaca file JSON");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(submitButton_LOGIN, "Username dan Password tidak boleh kosong!");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data_guru add_guru = new data_guru();
                add_guru.ID_guru = Add_IDguru.getText();
                add_guru.nama_guru = Add_Namaguru.getText();
                add_guru.Password_guru = Add_Passwordguru.getText();
                add_guru.Email_guru = Add_Emailguru.getText();
                if(!add_guru.ID_guru.isEmpty() && !add_guru.nama_guru.isEmpty() && !add_guru.Password_guru.isEmpty() && !add_guru.Email_guru.isEmpty()) {
                    String eks = ".json";
                    String namaFile = add_guru.ID_guru.concat(eks);
                    String path = System.getProperty("user.dir") + System.getProperty("file.separator") + namaFile;

                    String Gurujson = data_guru.toJson(add_guru);
                    try (FileWriter file = new FileWriter(path)) {
                        file.write(Gurujson);
                        System.out.print(Gurujson);
                        file.flush();
                        System.out.println("File JSON telah berhasil dibuat: " + path);
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(registerButton, "Semua harus di isi !!");
                }
            }
        });


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame create = new JFrame("Create");
                create.setContentPane(Create);
                create.setSize(500,500);
                create.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                create.setLocationRelativeTo(null);
                create.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dawdFormattedTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dwasPasswordField.requestFocus();
                }
            }
        });
        dwasPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitButton_LOGIN.doClick();
                }
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void initComponents() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }


}
