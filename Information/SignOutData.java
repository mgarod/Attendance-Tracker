package Information;

import java.util.ArrayList;

public class SignOutData {
    private ArrayList<Topics135> topics;
    private int levelOfLearning;
    private int emplId;

    public ArrayList<Topics135> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topics135> topics) {
        this.topics = topics;
    }

    public int getLevelOfLearning() {
        return levelOfLearning;
    }

    public void setLevelOfLearning(int levelOfLearning) {
        this.levelOfLearning = levelOfLearning;
    }

    public int getEmplId() {
        return emplId;
    }

    public void setEmplId(int emplId) {
        this.emplId = emplId;
    }
}
