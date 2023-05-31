public class MedicalSolutions {

    private String name;
    private String description;
    private String treatment;

    public MedicalSolutions(String name, String description, String treatment){
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTreatment()
    {
        return treatment;
    }
    public void setTreatment(String treatment)
    {
        this.treatment = treatment;
    }
}


