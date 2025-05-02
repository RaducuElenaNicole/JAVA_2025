import java.util.Arrays;

public class Examen extends Object
{
    private String disciplina;
    private Tip tipExamen;
    private Subiect[] subiecte;

    public Examen(){
        this.disciplina = null;
        this.tipExamen = tipExamen.NECUNOSCUT;
        this.subiecte = null;
    }

    public Examen(String disciplina, Tip tipExamen, Subiect[] subiecte) {
        this.disciplina = disciplina;
        this.tipExamen = tipExamen;
        this.subiecte = subiecte;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Tip getTipExamen() {
        return tipExamen;
    }

    public void setTipExamen(Tip tipExamen) {
        this.tipExamen = tipExamen;
    }

    public Subiect[] getSubiecte() {
        return subiecte;
    }

    public void setSubiecte(Subiect[] subiecte) {
        this.subiecte = subiecte;
    }

    @Override
    public String toString() {
        return "examen.txt {" +
                "disciplina = '" + disciplina + '\'' +
                " | tipExamen = " + tipExamen +
                " | subiecte = " + Arrays.toString(subiecte) +
                '}';
    }
}
