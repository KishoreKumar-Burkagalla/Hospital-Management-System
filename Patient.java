class Patient {
    String name;
    String status;

    Patient(String name) {
        this.name = name;
        this.status = "waiting";
    }

    public String toString() {
        return name + "\t\t" + status;
    }


}