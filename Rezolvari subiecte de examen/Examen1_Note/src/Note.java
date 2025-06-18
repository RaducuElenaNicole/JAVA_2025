public class Note {
    private int idStudent;
    private String materie;
    private double nota;

    public Note() {
    }

    public Note(int idStudent, String materie, double nota) {
        this.idStudent = idStudent;
        this.materie = materie;
        this.nota = nota;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Note{" +
                "idStudent=" + idStudent +
                ", materie='" + materie + '\'' +
                ", nota=" + nota +
                '}';
    }
}
