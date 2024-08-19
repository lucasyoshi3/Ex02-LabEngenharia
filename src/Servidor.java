import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Observer{
    private List<TrataCliente> listaCliente;
    private ServerSocket server;

    public void executar(){
        Socket s = new Socket();
        try {
            s = server.accept();
            TrataCliente trataCliente = new TrataCliente(s);
            trataCliente.addServidor(this);
            Thread t1 = new Thread(String.valueOf(trataCliente));
            t1.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Servidor(){
        this.listaCliente= new ArrayList<>();
    }

    @Override
    public void update(String msg) {
        for(TrataCliente temp : listaCliente){
            temp.escrever(msg);
        }
    }
}
