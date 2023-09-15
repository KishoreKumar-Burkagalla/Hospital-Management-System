class WaitingHall {
    int seatNo;
    Integer patientNo;

    WaitingHall(int seatNo) {
        this.seatNo = seatNo;
        this.patientNo = null;
    }

    public String toString() {
        return seatNo + "\t\t" + patientNo;
    }

}