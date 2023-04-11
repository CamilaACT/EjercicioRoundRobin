public class Proceso {
    private String cedula;
    private String ID;
    private int tiempo;

    public Proceso(String cedula, String ID, int tiempo) {
        this.cedula = cedula;
        this.ID = ID;
        this.tiempo = tiempo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getID() {
        return ID;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}
