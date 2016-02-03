package Information;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

public class SignOutData {
    private ArrayList<Topics135> topics;
    private int levelOfLearning;
    private int emplId;
    private Tutor tutor;

    public SignOutData(int emplId, @Nullable ArrayList<Topics135> topics, int levelOfLearning, Tutor tutor) {
        this.emplId = emplId;
        this.topics = topics;
        this.levelOfLearning = levelOfLearning;
        this.tutor = tutor;
    }

    public ArrayList<Topics135> getTopics() {
        return topics;
    }

    public int getLevelOfLearning() {
        return levelOfLearning;
    }

    public int getEmplId() {
        return emplId;
    }

    public Tutor getTutor() { return tutor; }
}
