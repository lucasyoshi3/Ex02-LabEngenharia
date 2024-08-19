import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrataCliente implements Runnable, Subject{
    private Socket cliente;
    private TrataCliente[] listaClientes;
    private List<Observer> observers;

    public String ler(){
        int data;
        InputStream in;
        StringBuilder texto =  new StringBuilder();
        try {
            in = cliente.getInputStream();
            data = in.read();
            while(data != -1){
                texto.append((char) data);
                data = in.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return texto.toString();
    }

    public void escrever(String texto){
        Scanner scan = new Scanner(System.in);
        try {
            OutputStream out = cliente.getOutputStream();
            String linhaDigitada = scan.next()+"\n\r";
            out.write(linhaDigitada.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scan.close();
    }

    @Override
    public void run() {
        while(true){
            System.out.println(ler());
            notifyServidores();
        }
    }

    public TrataCliente(Socket socket){
        this.cliente = socket;
        observers = new ArrayList<>();
    }

    @Override
    public void addServidor(Observer servidor) {
        observers.add(servidor);
    }

    @Override
    public void removeServidor(Observer servidor) {
        observers.remove(servidor);
    }

    @Override
    public void notifyServidores() {

    }

    @Override
    public void update(String msg) {
        for(Observer o : observers){
            o.update(msg);
        }
    }
}