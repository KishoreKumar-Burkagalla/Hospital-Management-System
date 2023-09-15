import java.util.*;

public class Hospital {
    static Map<Integer, Patient> listOfPatients = new HashMap<>();
    static Map<Integer, WaitingHall> waitingHall = new HashMap<>();
    static Map<Integer, Bed> beds = new HashMap<>();
    static Map<Integer, Doctor> doctors = new HashMap<>();
    static int noOfBeds;

    public static void main(String[] args) {
        patientUpdates();
    }

    private static void patientUpdates() {
        // No of Patients
        System.out.print("Enter the no. of patients: ");
        Scanner sc = new Scanner(System.in);
        int noOfPatients;
        try {
            noOfPatients = sc.nextInt();
            // No of Beds
            beds();
            int noOfDocs = doctors();
            print();
            menu(sc, noOfPatients, noOfDocs);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("Please Enter Valid Patient number: ");
            patientUpdates();
        }

    }

    private static void patientsEntry(Scanner sc, int noOfPatients) {
        System.out.println("Enter the names:");
        String[] names = new String[noOfPatients];
        names = sc.next().replaceAll("\\s", "").split(",");
        if (noOfPatients == names.length) {
            for (int i = 1; i <= noOfPatients; i++) {
                listOfPatients.put(i, new Patient(names[i - 1]));
                waitingHall.put(i, new WaitingHall(i));
            }
        } else {
            System.out.println("Enter valid patients names ");
            patientsEntry(sc, noOfPatients);
        }
    }

    private static void menu(Scanner sc, int noOfPatients, int noOfDocs) {
        int n;
        Scanner scanner = new Scanner(System.in);
        try {
            do {
                System.out.println("Menu\n\t-----------");
                System.out.println(" 1.Create Consultation\n 2.Doctor Decision\n 3.Discharge Patient\n 4.Send Patient to a consultation room\n ");
                System.out.print("\t\tEnter the option: ");
                n = scanner.nextInt();
                switch (n) {
                    case 1 -> consultation(noOfPatients);
                    case 2 -> doctorDecision();
                    case 3 -> discharge();
                    case 4 -> assignDoctors(noOfDocs);
                    case 5 -> {
                        System.out.println("Exit....!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Entry");
                }
            } while (n < 6);
        } catch (Exception e) {
            System.out.println("Please enter valid option");
            menu(sc, noOfPatients, noOfDocs);
        }
    }


    private static void beds() {
        System.out.print("Enter the no.  of Beds: ");
        Scanner obj = new Scanner(System.in);
        try {

            noOfBeds = obj.nextInt();
            if (noOfBeds > 0) {
                for (int i = 1; i <= noOfBeds; i++) {
                    beds.put(i, new Bed(i));
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("Please Enter Valid Integer: ");
            beds();
        }
    }

    private static int doctors() {
        System.out.print("Enter the no. of Doctors: ");
        int noOfDocs = 0;
        Scanner sc = new Scanner(System.in);
        try {
            noOfDocs = sc.nextInt();
            for (int i = 1; i <= noOfDocs; i++) {
                doctors.put(i, new Doctor("Doc " + i));
            }

        } catch (Exception e) {
            System.out.println("The number is not an integer: ");
            doctors();
        }
        return noOfDocs;
    }

    public static void consultation(int noOfPatients) {
        Scanner sc = new Scanner(System.in);
        patientsEntry(sc, noOfPatients);
        for (int i = 1; i <= noOfPatients; i++) {
            waitingHall.get(i).patientNo = i;
        }
        System.out.println("Patients are waiting for Doctor's Consultation");
        print();
    }

    public static void assignDoctors(int noOfDocs) {
        for (int j = 1; j <= waitingHall.size(); j++) {
            if (waitingHall.get(j).patientNo != null) {
                for (int i = 1; i <= doctors.size(); i++) {
                    if (doctors.get(i).patientNo == null) {
                        doctors.get(i).patientNo = waitingHall.get(j).patientNo;
                        waitingHall.get(j).patientNo = null;
                    }
                }
            }
        }
        System.out.println("Patients are assigned for the Doctor's Consultation");
        print();
    }

    public static void admit(Integer patientNo) {
        for (int j = 1; j <= beds.size(); j++) {
            if (beds.get(j).patientNo == null) {
                beds.get(j).patientNo = patientNo;
                System.out.println(patientNo + " Admitted");
                return;
            }
        }
    }

    public static void doctorDecision() {

        int size = doctors.size();
        for (int i = 1; i <= size; i++) {
            if (doctors.get(i).patientNo == null)
                System.out.println("No patients are available for Doctor: " + i);
            else if (doctors.get(i).patientNo != null) {
                System.out.println("Enter the decision of " + doctors.get(i).name + ":\n\t 1. Admit\n\t 2. Quarantine\n");
                Scanner sc = new Scanner(System.in);

                int decision = sc.nextInt();
                if (decision == 1) {
                    admit(doctors.get(i).patientNo);
                    listOfPatients.get(doctors.get(i).patientNo).status = "Admitted";
                } else if (decision == 2) {
                    listOfPatients.get(doctors.get(i).patientNo).status = "Quarantine";
                } else if (decision != 1 || decision != 2) {
                    System.out.println("Enter valid decision 1 or 2");
                    doctorDecision();
                }
                doctors.get(i).patientNo = null;
            }
        }
        print();
    }

    public static void discharge() {
        System.out.println("Enter the bed number:");
        Scanner sc = new Scanner(System.in);

        int bedNo = sc.nextInt();
        if (bedNo <= beds.size()) {

            if (beds.get(bedNo).patientNo == null) {
                System.out.println("No patient available in that bed");
            } else if (bedNo <= beds.size()) {
                listOfPatients.get(beds.get(bedNo).patientNo).status = "Discharged";
                beds.get(bedNo).patientNo = null;
            } else {
                System.out.println("Enter the correct bed number");
                discharge();
            }
        }
        print();
    }

    public static void print() {
        System.out.println("Patients:\t");
        for (int i = 1; i <= listOfPatients.size(); i++) {
            System.out.println(listOfPatients.get(i));
        }
        System.out.println("Waiting Hall:\t" + "Patient Name\t");
        for (int i = 1; i <= waitingHall.size(); i++) {
            String name = waitingHall.get(i).patientNo != null ? listOfPatients.get(waitingHall.get(i).patientNo).name : null;
            System.out.println("Seat " + waitingHall.get(i).seatNo + "\t\t" + name);

        }
        System.out.println("Beds:\t" + "Patient Name\t");
        for (int i = 1; i <= beds.size(); i++) {
            String name = beds.get(i).patientNo != null ? listOfPatients.get(beds.get(i).patientNo).name : null;
            System.out.println("Bed " + beds.get(i).bedNo + "\t\t" + name);
        }
        System.out.println("Doctors:\n");
        for (int i = 1; i <= doctors.size(); i++) {
            String name = doctors.get(i).patientNo != null ? listOfPatients.get(doctors.get(i).patientNo).name : null;
            System.out.println(doctors.get(i).name + "\t\t" + name);
        }
    }
}