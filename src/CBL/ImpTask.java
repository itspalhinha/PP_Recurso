/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import java.time.LocalDate;
import java.util.Objects;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;

/**
 *
 * @author ROGER
 */
public class ImpTask implements Task{
    
    private LocalDate start;
    private LocalDate end;
    private int duration;
    private String title;
    private String description;
    private Submission[] submission;
    private int numberOfSubmissions;

    public ImpTask(LocalDate start, LocalDate end, int duration, String title, String description) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }
    
    

    @Override
    public LocalDate getStart() {
        return start;
    }

    @Override
    public LocalDate getEnd() {
        return end;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Submission[] getSubmissions() {
        Submission tempSubmission[] = new Submission[numberOfSubmissions];
        for(int i = 0; i < numberOfSubmissions; i++){
            tempSubmission[i] = submission[i];
        }
        return tempSubmission;
    }

    @Override
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }
    
    private boolean existsSubmission(Submission subm){
        for(int i = 0; i < this.submission.length; i++){
            if(submission[i] != null && submission[i].equals(subm)){
                return true;
            }
        }
        return false;
    }
    
    private void realloc(){
        Submission[] tempSubm = new Submission[this.submission.length*2];
        for(int i = 0; i < this.submission.length; i++){
            tempSubm[i] = this.submission[i];
        }
        this.submission = tempSubm;
    }

    @Override
    public void addSubmission(Submission sbmsn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void extendDeadline(int days) {
        if(days < 0){
            throw new IllegalArgumentException("Negative Value");
        }
        LocalDate tempDays = end.plusDays(days);
        this.end = tempDays;
    }

    @Override
    public int compareTo(Task task) {
        if(this == task){
            return 0;
        }
        return this.start.compareTo(task.getStart());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImpTask other = (ImpTask) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }
    
    
    
}
