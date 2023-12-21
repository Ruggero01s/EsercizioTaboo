public class Job {

    //dati del job
    int id, w, p, d, taboo;


    //costruttore
    public Job(int id, int w, int p, int d, int taboo) {
        this.id = id;
        this.w = w;
        this.p = p;
        this.d = d;
        this.taboo = taboo;
    }

    public int getTaboo() {
        return taboo;
    }

    public void setTaboo(int taboo) {
        this.taboo = taboo;
    }

    public int getW() {
        return w;
    }

    public int getP() {
        return p;
    }

    public int getD() {
        return d;
    }

    public int getId() {
        return id;
    }
}
