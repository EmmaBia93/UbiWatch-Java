import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;
import java.security.KeyStore.CallbackHandlerProtection;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.*;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Command extends Thread {
    JSch jsch = new JSch();
    String tipo;
    Session session = null;
    ChannelExec channel = null;
    String command1, clientes, seg, usuarios, cable1;
    int Cli, segundos;
    String key_public = "";
    String ip;
    JLabel load;
    ByteArrayOutputStream captura1, captura2;
    Usuarios user;
    List<Usuarios> ListUsers;
    String USER = "ubnt";
    String pass1 = "", pass2 = "";
    int port = 23;

    @Override
    public void run() {

        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword(tipo.contains("AC") ? pass2 : pass1);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            if (tipo.contains("AC")) {
                channel.setCommand(
                        "wstalist -p |grep -c \"mac\" ; mca-status | grep uptime && mca-status | grep lanSpeed ");
            } else {
                channel.setCommand(
                        "wstalist |grep -c \"mac\" ; mca-status | grep uptime && mca-status | grep lanSpeed");
            }

            captura1 = new ByteArrayOutputStream();
            channel.setOutputStream(captura1);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            command1 = new String(captura1.toByteArray());
            if (command1 != "0") {
                int pos;
                String aux;

                clientes = command1.substring(0, (command1.indexOf("\n")));
                pos = command1.indexOf("uptime");
                aux = command1.substring(pos, command1.length());
                seg = aux.substring((aux.indexOf("=")) + 1, aux.indexOf("\n"));
                pos = aux.indexOf("lanSpeed");
                aux = aux.substring(pos, aux.length());
                cable1 = aux.substring(aux.indexOf("=") + 1, aux.indexOf("\n"));

            } else {
                clientes = "0";
                seg = "0";
                cable1 = "0";
            }

        } catch (JSchException e) {
            command1 = "0";

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        channel.disconnect();
        session.disconnect();
    }

    public Boolean REINICIAR(String ip) {
        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword("628819872");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("reboot");
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException e) {
            System.out.println(e);
            return false;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        channel.disconnect();
        session.disconnect();
        return true;

    }

    public void CambiarssidyFrecuencia(String ip, String ssid) {
        try {
            session = jsch.getSession(USER, ip, 23);
            session.setPassword(key_public);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("sed -i '/wireless.1.ssid/c\\wireless.1.ssid=" + ssid
                    + "' /tmp/system.cfg && sed -i '/wpasupplicant.profile.1.network.1.ssid/c\\wpasupplicant.profile.1.network.1.ssid="
                    + ssid
                    + "' /tmp/system.cfg &&sed -i '/wireless.1.scan_list.status/c\\wireless.1.scan_list.status=disabled' /tmp/system.cfg && sed -i '/wireless.1.scan_list.channels/c\\wireless.1.scan_list.channels=' /tmp/system.cfg && cfgmtd -wp /etc && reboot");
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException e) {
            System.out.println(e);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        channel.disconnect();
        session.disconnect();

    }

    public Boolean Frecuenciaout(String ip) {
        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword(key_public);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(
                    "sed -i '/wireless.1.scan_list.status/c\\wireless.1.scan_list.status=disabled' /tmp/system.cfg && sed -i '/wireless.1.scan_list.channels/c\\wireless.1.scan_list.channels=' /tmp/system.cfg && cfgmtd -wp /etc && reboot");
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException e) {
            System.out.println(e);
            return false;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        channel.disconnect();
        session.disconnect();

        return true;
    }

    public void BACKUP(String ip, String nom) throws JSchException, SftpException {
        JFileChooser guardar = new JFileChooser();
        guardar.showSaveDialog(null);

        guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        File ruta = guardar.getCurrentDirectory();

        nom = nom.replaceAll(" ", "");
        String rut = ruta.getAbsolutePath() + "\\" + nom + ".cfg";

        String cmd = "echo y | pscp -scp -P 23 -pw 628819872 ubnt@" + ip + ":/var/tmp/system.cfg " + rut;

        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println(cmd);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void set_ip(String ip, String tipo) {
        this.tipo = tipo;
        this.ip = ip;

    }

    public String HoraEnlaces(String ip, String tipo) throws JSchException, InterruptedException {

        session = jsch.getSession("ubnt", ip, port);

        session.setPassword(tipo.contains("AC") ? pass2 : pass1);

        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("mca-status | grep uptime");
        captura1 = new ByteArrayOutputStream();
        channel.setOutputStream(captura1);
        channel.connect();

        while (channel.isConnected()) {
            Thread.sleep(100);
        }
        command1 = new String(captura1.toByteArray());
        String cantH = command1.replaceAll("\\D+", "");
        return (cantH = get_horas(cantH));
    }

    public String HoraCliente(String ip) throws JSchException, InterruptedException {
        session = jsch.getSession("ubnt", ip, 23);
        session.setPassword("628819872");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("mca-status | grep uptime");
        captura1 = new ByteArrayOutputStream();
        channel.setOutputStream(captura1);
        channel.connect();

        while (channel.isConnected()) {
            Thread.sleep(100);
        }
        command1 = new String(captura1.toByteArray());
        String cantH = command1.replaceAll("\\D+", "");
        return (cantH = get_horas(cantH));
    }

    public void ReiniciarEnlaces(String ip, String op) throws JSchException, InterruptedException {

        session = jsch.getSession("ubnt", ip, port);
        session.setPassword(op.contains("AC") ? pass2 : pass1);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("reboot");
        captura1 = new ByteArrayOutputStream();
        channel.setOutputStream(captura1);
        channel.connect();

        while (channel.isConnected()) {
            Thread.sleep(100);
        }

    }

    public String CableEnlaces(String ip, String tipo) throws JSchException, InterruptedException {

        session = jsch.getSession("ubnt", ip, port);
        session.setPassword(tipo.contains("AC") ? pass2 : pass1);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("mca-status |grep lanSpeed");
        captura1 = new ByteArrayOutputStream();
        channel.setOutputStream(captura1);
        channel.connect();

        while (channel.isConnected()) {
            Thread.sleep(100);
        }
        command1 = new String(captura1.toByteArray());
        String cable = command1.replaceAll("\\D+", "");
        return (cable);

    }

    public String Cable() {
        cable1 = cable1.replaceAll("\\D+", "");
        return (cable1);

    }

    private String get_horas(String cantH) {

        int s = Integer.parseInt(cantH);
        int min = s / 60;

        int hora = min / 60;
        min %= 60;
        int dias = hora / 24;
        hora %= 24;
        return dias + "D:" + hora + "H:" + min + "m";
    }

    public int get_cantidad() {
        clientes = clientes.replaceAll("\\D+", "");

        Cli = Integer.parseInt(clientes);
        return Cli;
    }

    public String get_horas() {
        seg = seg.replaceAll("\\D+", "");
        int s = Integer.parseInt(seg);
        int min = s / 60;

        int hora = min / 60;
        min %= 60;
        int dias = hora / 24;
        hora %= 24;
        return dias + "D:" + hora + "H:" + min + "m";
    }

    public List<Usuarios> CargadeUsuarios(String ip) {

        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword("628819872");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            String comand = "wstalist |grep -A2 \"mac\" ";
            channel.setCommand(comand);
            captura1 = new ByteArrayOutputStream();
            channel.setOutputStream(captura1);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            usuarios = new String(captura1.toByteArray());
            usuarios = usuarios.replaceAll("--", "");
            usuarios = usuarios.replaceAll("\"", "");
            usuarios = usuarios.replaceAll(",", "");

        } catch (JSchException e) {
            System.out.println(e);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        channel.disconnect();
        session.disconnect();
        CrearListadeUsuario(usuarios);
        return ListUsers;
    }

    public String FrecuenciaySsid(String ip) throws InterruptedException, JSchException {
        session = jsch.getSession("ubnt", ip, 23);
        session.setPassword("628819872");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(
                "cat /tmp/system.cfg |grep scan_list.status && cat /tmp/system.cfg |grep wireless.1.ssid && cat /tmp/system.cfg |grep wireless.1.scan_list.channels");
        captura1 = new ByteArrayOutputStream();
        channel.setOutputStream(captura1);
        channel.connect();

        while (channel.isConnected()) {
            Thread.sleep(100);
        }
        command1 = new String(captura1.toByteArray());
        return command1;
    }

    private void CrearListadeUsuario(String us) {
        String extra;
        int ban = 0, posn, posm, posi, pos;
        ListUsers = new ArrayList<>();
        List<String> part = new ArrayList<>();
        while (us != null) {
            if (us.indexOf("mac") != -1) {
                us = us.substring(us.indexOf("mac"), us.length());
                part.add(us.substring(us.indexOf("mac") + 5, us.indexOf("\n")));

                us = us.substring(us.indexOf("name"), us.length());
                part.add(us.substring(us.indexOf("name") + 6, us.indexOf("\n")));
                us = us.substring(us.indexOf("lastip"), us.length());
                part.add(us.substring(us.indexOf("lastip") + 8, us.indexOf("\n")));

            } else {
                us = null;
            }

        }
        for (int i = 0; i < part.size(); i += 3) {
            user = new Usuarios(part.get(i + 1), part.get(i + 2), part.get(i));
            ListUsers.add(user);
        }

    }

    public void FrecuenciaySSid(String ip, String nom, String frec) {
        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword("628819872");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(
                    "sed -i '/wireless.1.scan_list.status/c\\wireless.1.scan_list.status=enabled' /tmp/system.cfg && sed -i '/wireless.1.scan_list.channels/c\\wireless.1.scan_list.channels="
                            + frec
                            + "' /tmp/system.cfg && sed -i '/wpasupplicant.profile.1.network.1.ssid/c\\wpasupplicant.profile.1.network.1.ssid="
                            + nom + "' /tmp/system.cfg && sed -i '/wireless.1.ssid/c\\wireless.1.ssid=" + nom
                            + "' /tmp/system.cfg cfgmtd -wp /etc && reboot");
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException e) {
            System.out.println(e);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        channel.disconnect();
        session.disconnect();

    }

    public void FijarSSID(String ip, String nom) {
        try {
            session = jsch.getSession("ubnt", ip, 23);
            session.setPassword("628819872");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(
                    "sed -i '/wpasupplicant.profile.1.network.1.ssid/c\\wpasupplicant.profile.1.network.1.ssid=" + nom
                            + "' /tmp/system.cfg && sed -i '/wireless.1.ssid/c\\wireless.1.ssid=" + nom
                            + "' /tmp/system.cfg && cfgmtd -wp /etc && reboot");
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException e) {
            System.out.println(e);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        channel.disconnect();
        session.disconnect();

    }

    public void BackupEnlaces(String ip, String op, String nom) throws JSchException, InterruptedException {

        String pass = op.contains("AC") ? pass2 : pass1;

        JFileChooser guardar = new JFileChooser();
        guardar.showSaveDialog(null);

        guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        File ruta = guardar.getCurrentDirectory();

        nom = nom.replaceAll(" ", "");
        String rut = ruta.getAbsolutePath() + "\\" + nom + ".cfg";

        String cmd = "pscp -scp -P " + port + " -pw " + pass + " ubnt@" + ip + ":/var/tmp/system.cfg " + rut;

        try {

            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
