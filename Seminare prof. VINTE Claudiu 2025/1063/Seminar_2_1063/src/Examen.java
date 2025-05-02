import java.util.Arrays;

public class Examen {
    private String disciplina;
    private Tip tip;
    private Subiect[] subiecte;

    public Examen() {
        this.disciplina = null;
        this.tip = Tip.NECUNOSCUT;
        this.subiecte = null;
    }

    public Examen(String disciplina, Tip tip, Subiect[] subiecte) {
        this.disciplina = disciplina;
        this.tip = tip;
        this.subiecte = subiecte;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Subiect[] getSubiecte() {
        return subiecte;
    }

    public void setSubiecte(Subiect[] subiecte) {
        this.subiecte = subiecte;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "disciplina='" + disciplina + '\'' +
                ", tip=" + tip +
                ", subiecte=" + Arrays.toString(subiecte) +
                '}';
    }
}
