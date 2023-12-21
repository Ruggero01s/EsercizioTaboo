public class Move {

    // le variabili di questa classe sono i due job coi rispettivi indici
    Job j1, j2;
    int i1, i2;

    public Move(Job j1, Job j2, int i1, int i2) {
        this.j1 = j1;
        this.j2 = j2;
        this.i1 = i1;
        this.i2 = i2;
    }

    public Job getJ1() {
        return j1;
    }

    public void setJ1(Job j1) {
        this.j1 = j1;
    }

    public Job getJ2() {
        return j2;
    }

    public void setJ2(Job j2) {
        this.j2 = j2;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }

    @Override // per identificare due mosse uguali, in pratica due mosse sono uguali se coinvolgono gli stessi job e gli stessi indici rispettivi
    public boolean equals(Object obj) {
        if (!(obj instanceof Move))
            return false;
        Move m = (Move) obj;
        return (((this.j1.equals(m.j1) && this.j2.equals(m.j2)) && (this.i1==m.i1 && this.i2==m.i2)) ||
                ((this.j2.equals(m.j1) && this.j1.equals(m.j2)) && (this.i2==m.i1 && this.i1==m.i2)));
    }
}
