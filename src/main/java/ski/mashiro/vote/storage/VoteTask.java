package ski.mashiro.vote.storage;

import org.bukkit.entity.Player;
import ski.mashiro.vote.timer.Timer;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author MashiroT
 */
public class VoteTask {

    private String taskName;
    private int taskId;
    private String command;
    private String releaseTime;
    private String effectTime;

    public HashMap<Integer, Player> votes = new HashMap<>();

    Timer timer = new Timer(this);

    public VoteTask() {
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public boolean setTaskIdOut(String taskId) {
        try {
            setTaskId(Integer.parseInt(taskId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        VoteTask voteTask = (VoteTask) o;
        return taskId == voteTask.taskId && Objects.equals(taskName, voteTask.taskName) && Objects.equals(command, voteTask.command)
                && Objects.equals(releaseTime, voteTask.releaseTime) && Objects.equals(effectTime, voteTask.effectTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskId, command, releaseTime, effectTime);
    }
}
