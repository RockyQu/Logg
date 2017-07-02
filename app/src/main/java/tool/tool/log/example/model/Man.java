package tool.tool.log.example.model;

public class Man extends People {

    private String address;

    private Child child = null;

    public Man() {

    }

    public Man(int id, String name, String address, Child child) {
        super.setId(id);
        super.setName(name);

        this.address = address;
        this.child = child;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
