package mp.tables;

import java.util.Objects;

public class Worker {
    private int worker_ID;
    private String surname;
    private String name;

    public int getWorker_ID() {
        return worker_ID;
    }

    public void setWorker_ID(int worker_ID) {
        this.worker_ID = worker_ID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return worker_ID == worker.worker_ID && Objects.equals(surname, worker.surname) && Objects.equals(name, worker.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worker_ID, surname, name);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "worker_ID=" + worker_ID +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
