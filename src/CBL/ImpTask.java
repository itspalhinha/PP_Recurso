/*
 * Nome: Rafael Filipe Silva Medina Coronel
 * Número: 8190348
 * Turma: LSIRCT1
 *
 * Nome: Roger Seiji Hernandez Nakauchi
 * Número: 8210005
 * Turma: LSIRCT1
 */
package CBL;

import java.time.LocalDate;
import java.util.Objects;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ROGER
 */
public class ImpTask implements Task{
    
    /*
    * Variable that define Task start
    */
    private LocalDate start;
    /*
    * Variable that define Task end
    */
    private LocalDate end;
    /*
    * Variable that define Task duration
    */
    private int duration;
    /*
    * Variable that define Task title
    */
    private String title;
    /*
    * Variable that define Task description
    */
    private String description;
    /*
    * Array that stores submissions
    */
    private Submission[] submission;
    /*
    * Variable that define Task number of submissions
    */
    private int numberOfSubmissions;
    
    /*
    * This is the constructor method for Task
    *
    * @param start Task start date
    * @param end Task end date
    * @param duration Task duration
    * @param title Task title
    * @param description Task description
    */
    public ImpTask(LocalDate start, LocalDate end, int duration, String title, String description) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    ImpTask(String title, String description, LocalDate start, LocalDate end, int duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getStart() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getEnd() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDuration() {
        return this.duration;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submission[] getSubmissions() {
        Submission tempSubmission[] = new Submission[numberOfSubmissions];
        for(int i = 0; i < numberOfSubmissions; i++){
            tempSubmission[i] = submission[i];
        }
        return tempSubmission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }
    
    /*
    * This method checks if a submission exists in the list
    *
    * @param sbmsn
    * @return true if submission exists
    * @return false if submission does not exist
    */
    private boolean existsSubmission(Submission sbmsn){
        for(int i = 0; i < this.submission.length; i++){
            if(submission[i] != null && submission[i].equals(sbmsn)){
                return true;
            }
        }
        return false;
    }
    
    /*
    * This method adds more space on Submission list
    */
    private void realloc(){
        Submission[] tempSubm = new Submission[this.submission.length*2];
        for(int i = 0; i < this.submission.length; i++){
            tempSubm[i] = this.submission[i];
        }
        this.submission = tempSubm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSubmission(Submission sbmsn) {
        if(sbmsn == null){
            throw new IllegalArgumentException("Null value for submission");
        }
        if(numberOfSubmissions == this.submission.length){
            realloc();
            this.submission[numberOfSubmissions] = sbmsn;
            numberOfSubmissions++;
        }else{
            this.submission[numberOfSubmissions] = sbmsn;
            numberOfSubmissions++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extendDeadline(int days) {
        if(days < 0){
            throw new IllegalArgumentException("Negative Value");
        }
        LocalDate tempDays = end.plusDays(days);
        this.end = tempDays;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Task task) {
        if(this == task){
            return 0;
        }
        return this.start.compareTo(task.getStart());
    }

    //metodos JSON
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("start", start.toString());
        jsonObject.put("end", end.toString());
        jsonObject.put("duration", duration);
        jsonObject.put("numberOfSubmissions", numberOfSubmissions);

        JSONArray submissionsArray = new JSONArray();
        for (int i = 0; i < numberOfSubmissions; i++) {
            submissionsArray.add(((ImpSubmission) submission[i]).toJsonObj());
        }
        jsonObject.put("submissions", submissionsArray);

        return jsonObject;
    }

    public static Task fromJsonObj(JSONObject jsonObject) {
        String title = (String) jsonObject.get("title");
        String description = (String) jsonObject.get("description");
        LocalDate start = LocalDate.parse((String) jsonObject.get("start"));
        LocalDate end = LocalDate.parse((String) jsonObject.get("end"));
        int duration = ((Long) jsonObject.get("duration")).intValue();

        ImpTask task = new ImpTask(title, description, start, end, duration);

        JSONArray submissionsArray = (JSONArray) jsonObject.get("submissions");

        for (int i = 0; i < submissionsArray.size(); i++) {
            JSONObject submissionJson = (JSONObject) submissionsArray.get(i);
            task.addSubmission(ImpSubmission.fromJsonObj(submissionJson));
        }

        return task;
    }
    
    //metodos CSV
     



    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        final ImpTask other = (ImpTask) obj;
        return this.title.equals(this.getTitle());
    }
    
    
    
}
