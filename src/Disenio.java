import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Disenio extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPrincipal;
    private JButton insertarButton;
    private JTextArea txtDatosQuemados;
    private JTextField txtID;
    private JTextField txtCedula;
    private JTextField txtTiempo;
    private JButton btnCrearNuevo;
    private JTextArea txtNuevoResultado;
    private JTextField txtIngresoQ;
    private JButton definirValorButton;
    private JLabel txtVaorQ;
    private JButton btnRoundRobin;
    private JTextArea txtResultadoRB;
    private JButton btnHistorial;
    private JButton btnConsultarProceso;
    private JButton btnBorrarHistorial;
    private JTextArea txtHistorial;
    private Queue<Proceso> precesos=new LinkedList<>();
    private int Quantum;
    private Stack<Proceso> pilaOrden = new Stack<Proceso>();



    public Disenio(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.pack();
        Quantum=35;
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quemarDatos();
            }
        });
        btnCrearNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarDato();
            }
        });
        definirValorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                definirValorQ();
            }
        });
        btnRoundRobin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roundrobin();
            }
        });
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirHistorial();
            }
        });
        btnConsultarProceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ultimoHistorial();
            }
        });
        btnBorrarHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarHistoria();
            }
        });
    }

    private void borrarHistoria() {
        pilaOrden.clear();
        txtHistorial.setText("");
    }

    private void ultimoHistorial() {
        if(pilaOrden.size()>0){
            Proceso copiaproceso=new Proceso("","",0);
            copiaproceso=pilaOrden.peek();
            txtHistorial.setText(" Cedula: "+copiaproceso.getCedula()+" ID: "+copiaproceso.getID());
        }

    }

    private void imprimirHistorial() {
        if(pilaOrden.size()>0){
            txtHistorial.setText(ImprimirPila(pilaOrden));
        }

    }
    private String ImprimirPila(Stack<Proceso> pila){
        String MensajeFinal="";
        Proceso copiaproceso=new Proceso("","",0);
        Stack<Proceso> pilaOrden2 = new Stack<Proceso>();
        pilaOrden2.addAll(pila);
        while(!pilaOrden2.isEmpty()) { // Mientras la pila no esté vacía
            //System.out.println(pila.pop());// Imprime y desapila el elemento de la cima
            copiaproceso=pilaOrden2.pop();
            MensajeFinal=" Cedula: "+copiaproceso.getCedula()+" ID: "+copiaproceso.getID()+MensajeFinal+"|";

        }
        return MensajeFinal;
    }

    private void roundrobin() {
        Proceso copiaproceso=new Proceso("","",0);
        int resultadoProcesamiento;
        int Tiempo=0;
        int ContadorConmutaciones=0;
        if(precesos.size()>1){
            while (precesos.size()>1) {
                copiaproceso=precesos.poll();
                //System.out.println("Valor QUANTUM: "+Quantum);
                //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución");
                System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución");
                txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución\n");
                //System.out.println("TIEMPO INCIAL:"+copiaproceso.getTiempo());
                resultadoProcesamiento=procesar(copiaproceso.getTiempo());

                if(resultadoProcesamiento<=0){
                    Tiempo=Tiempo+copiaproceso.getTiempo();
                    //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecución");
                    System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecución");
                    txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecuciónv\n");
                    pilaOrden.push(copiaproceso);
                }else{
                    Tiempo=Tiempo+Quantum;
                    ContadorConmutaciones++;
                    //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"se conmuta. Pendiente por ejecutar "+resultadoProcesamiento);
                    System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"se conmuta. Pendiente por ejecutar "+resultadoProcesamiento);
                    txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"se conmuta. Pendiente por ejecutar "+resultadoProcesamiento+"\n");
                    copiaproceso.setTiempo(resultadoProcesamiento);
                    precesos.offer(copiaproceso);
                }
                //System.out.println("TAMANIO COLA"+precesos.size());
            }
        }
        if (precesos.size()==1)
        {
            while (precesos.size()>0) {
                copiaproceso=precesos.poll();
                //System.out.println("Valor QUANTUM: "+Quantum);
                //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución");
                System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución");
                txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"entra en ejecución\n");
                //System.out.println("TIEMPO INCIAL:"+copiaproceso.getTiempo());
                resultadoProcesamiento=procesar(copiaproceso.getTiempo());

                if(resultadoProcesamiento<=0){
                    Tiempo=Tiempo+copiaproceso.getTiempo();
                    //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecución");
                    System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecución");
                    txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"finaliza ejecución\n");
                    pilaOrden.push(copiaproceso);
                }else{
                    Tiempo=Tiempo+Quantum;
                    //txtResultadoRB.setText("Tiempo "+Tiempo+":"+copiaproceso.getID()+"se conmuta. Pendiente por ejecutar "+resultadoProcesamiento);
                    System.out.println("Tiempo "+Tiempo+":"+copiaproceso.getID()+"continua ejecutandose");
                    txtResultadoRB.append("Tiempo "+Tiempo+":"+copiaproceso.getID()+"continua ejecutandose\n");
                    copiaproceso.setTiempo(resultadoProcesamiento);
                    precesos.offer(copiaproceso);
                }

            }
        }

        System.out.println("ESTADÍSTICAS GENERADAS");
        txtResultadoRB.append("ESTADÍSTICAS GENERADAS\n");
        System.out.println("Total tiempo de ejecución de todos los procesos: "+Tiempo+"ms");
        txtResultadoRB.append("Total tiempo de ejecución de todos los procesos: "+Tiempo+"ms\n");
        System.out.println("Total de conmutaciones:"+ContadorConmutaciones);
        txtResultadoRB.append("Total de conmutaciones:"+ContadorConmutaciones+"\n");


    }

    private int procesar(int tiempo) {
       return tiempo-Quantum;
    }

    private void definirValorQ() {
        Quantum=Integer.parseInt(txtIngresoQ.getText());
        txtVaorQ.setText("El nuevo valor del Quantum es: "+Quantum);
    }

    private void ingresarDato() {
        precesos.offer(new Proceso(txtCedula.getText(),txtID.getText(),Integer.parseInt(txtTiempo.getText())));
        txtNuevoResultado.setText(ImprimirCola(precesos));
    }

    private void quemarDatos() {
        Proceso p1=new Proceso("123","P1",100);
        Proceso p2=new Proceso("123","P2",15);
        Proceso p3=new Proceso("123","P3",40);
        precesos.offer(p1);
        precesos.offer(p2);
        precesos.offer(p3);
        txtDatosQuemados.setText(ImprimirCola(precesos));
        //Faltan quemar otros tres
    }
    private String ImprimirCola(Queue imprimir){
        Queue<Proceso> colaimpresion = new LinkedList<>();
        Proceso copiaproceso=new Proceso("","",0);
        String Mensaje="";
        colaimpresion.addAll(imprimir);

        while (!colaimpresion.isEmpty()) {
            //System.out.println(colaimpresion.poll());
            copiaproceso=colaimpresion.poll();
            Mensaje=Mensaje+" Cedula: "+copiaproceso.getCedula()+" ID: "+copiaproceso.getID()+" Tiempo: "+copiaproceso.getTiempo()+" | ";
        }
        return Mensaje;
    }

    public static void main(String[] args) {
        JFrame buscador= new Disenio("EJERCICIO");
        buscador.setVisible(true);
    }
}
