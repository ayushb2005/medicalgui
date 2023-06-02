/**
 * This class is where the solutions for the symptoms have the getters and setters
 * @author Ayush Bhanushali
 * @version 5/29/23
 */
public class MedicalSolutions {
    private String name;
    private String description;
    private String treatment;

    /**
     * Constructor
     * @param name of symptoms
     * @param description of symptoms
     * @param treatment for solution of symptom
     */
    public MedicalSolutions(String name, String description, String treatment){
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }

    /**
     * 
     * @return name of symptom
     */
    public String getName()
    {
        return name;
    }
    /**
     * 
     * @param name for setting the new symptom
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 
     * @return description for symptom  
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * 
     * @param description to be set for new symptom
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    /**
     * 
     * @return treatment for symptom
     */
    public String getTreatment()
    {
        return treatment;
    }
    /**
     * 
     * @param treatment to be set for new symptom
     */
    public void setTreatment(String treatment)
    {
        this.treatment = treatment;
    }
}
