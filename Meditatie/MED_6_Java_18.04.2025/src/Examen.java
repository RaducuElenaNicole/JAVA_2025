import java.util.Date;

public class Examen implements Comparable<Examen>{
    private Date data;
    private String profesor;
    private String disciplina;
    private int nrStudentiInscrisi;
    private int nrStudentiExaminati;

    public Examen(Date data, String profesor, String disciplina, int nrStudentiInscrisi, int nrStudentiExaminati) {
        this.data = data;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.nrStudentiInscrisi = nrStudentiInscrisi;
        this.nrStudentiExaminati = nrStudentiExaminati;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Examen{");
        sb.append("data=").append(data);
        sb.append(", profesor='").append(profesor).append('\'');
        sb.append(", disciplina='").append(disciplina).append('\'');
        sb.append(", nrStudentiInscrisi=").append(nrStudentiInscrisi);
        sb.append(", nrStudentiExaminati=").append(nrStudentiExaminati);
        sb.append('}');
        return sb.toString();
    }

    public double absenteism(){
        return (double) ((this.nrStudentiInscrisi - this.nrStudentiExaminati) * 100)
                /this.nrStudentiInscrisi;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getNrStudentiInscrisi() {
        return nrStudentiInscrisi;
    }

    public void setNrStudentiInscrisi(int nrStudentiInscrisi) {
        this.nrStudentiInscrisi = nrStudentiInscrisi;
    }

    public int getNrStudentiExaminati() {
        return nrStudentiExaminati;
    }

    public void setNrStudentiExaminati(int nrStudentiExaminati) {
        this.nrStudentiExaminati = nrStudentiExaminati;
    }

    @Override
    public int compareTo(Examen ex) {
        if(this.nrStudentiExaminati > ex.nrStudentiExaminati){
            return 1;
        }else if(this.nrStudentiExaminati < ex.nrStudentiExaminati){
            return -1;
        }else{
            return 0;
        }
    }
}
