import java.util.Arrays;

public class Student implements Comparable {
    private int id;
    private String nume;
    private float medie;
    private int nrMaterii;
    private float[] vectorNote;

    public Student(int id, String nume, float medie, int nrMaterii, float[] vectorNote) {
        this.id = id;
        this.nume = nume;
        this.medie = medie;
        this.nrMaterii = nrMaterii;
        this.vectorNote = vectorNote;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", medie=").append(medie);
        sb.append(", nrMaterii=").append(nrMaterii);
        sb.append(", vectorNote=").append(Arrays.toString(vectorNote));
        sb.append("} \n");
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getMedie() {
        return medie;
    }

    public void setMedie(float medie) {
        this.medie = medie;
    }

    public int getNrMaterii() {
        return nrMaterii;
    }

    public void setNrMaterii(int nrMaterii) {
        this.nrMaterii = nrMaterii;
    }

    public float[] getVectorNote() {
        return vectorNote;
    }

    public void setVectorNote(float[] vectorNote) {
        this.vectorNote = vectorNote;
    }

    @Override
    public int compareTo(Object o) {
        Student copie = (Student)o;
        if(this.medie > copie.medie)
            return 1;
        else if(this.medie < copie.medie)
            return -1;
        else
            return 0;
    }
}
