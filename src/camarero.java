import java.util.ArrayList;
public class camarero extends Thread{
    private String nombrecama;
    private ArrayList<cliente> list_clientes;
    private ArrayList<cliente> list_atendidos;

    public camarero(String nombrecama,ArrayList<cliente> list_clientes,ArrayList<cliente> list_atendidos){
        this.nombrecama=nombrecama;
        this.list_clientes=list_clientes;
        this.list_atendidos=list_atendidos;
    }

    @Override
    public void run(){
        System.out.println(nombrecama+" ha comenzado a atender....\n");
        cliente cliente_actual;
        for (cliente clientes:list_clientes){
            if(cliente_actual==clientes){

            }
        }
    }
}
