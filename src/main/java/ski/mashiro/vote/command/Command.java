package ski.mashiro.vote.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ski.mashiro.vote.message.Message;
import ski.mashiro.vote.storage.Data;
import ski.mashiro.vote.storage.VoteTask;

/**
 * @author MashiroT
 */
public class Command implements CommandExecutor {

    private static final String CREATE = "create";
    private static final String DEL = "del";
    private static final String DELETE = "delete";
    private static final String APPROVE = "approve";
    private static final String DISAPPROVE = "disapprove";
    private static final String LIST = "list";
    private static final String SET = "set";
    private static final int TASK_NAME = 1;
    private static final int TASK_ID = 2;
    private static final int TASK_COMMAND = 3;
    private static final int TASK_RELEASE_TIME = 4;
    private static final int TASK_EFFECT_TIME = 5;
    private static final int TASK_CREATE_CORRECT_LENGTH = 6;
    private static final int TASK_DELETE_CORRECT_LENGTH = 2;
    private static final int TASK_APPROVE_CORRECT_LENGTH = 2;
    private static final int TASK_DISAPPROVE_CORRECT_LENGTH = 2;

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (strings.length == 0) {
            Message.showHelp(commandSender);
            return true;
        }
        String cmd = strings[0];
        switch (cmd.toLowerCase()){
            case CREATE:
                try {
                    if (strings[TASK_NAME] != null && strings[TASK_ID]!= null && strings[TASK_COMMAND] != null
                            && strings[TASK_RELEASE_TIME] != null && strings[TASK_EFFECT_TIME] != null && strings.length == TASK_CREATE_CORRECT_LENGTH) {
                        if (Data.addVote(strings[TASK_NAME], strings[TASK_ID], strings[TASK_COMMAND], strings[TASK_RELEASE_TIME], strings[TASK_EFFECT_TIME])) {
                            commandSender.sendMessage("投票创建成功，id：" + strings[TASK_ID]);
                        }else {
                            commandSender.sendMessage("投票创建失败，[投票id]应为数字");
                        }
                    }
                }catch (Exception e){
                    Message.showCreateErrMessage(commandSender);
                }
                break;
            case DEL:
            case DELETE:
                if (strings[1] != null && strings.length == TASK_DELETE_CORRECT_LENGTH) {
                    if (Data.delVote(strings[1])) {
                        commandSender.sendMessage("投票删除成功");
                    }else {
                        Message.showDelErrMessage(commandSender);
                    }
                }
                break;

            case APPROVE:
                if (strings[1] != null && strings.length == TASK_APPROVE_CORRECT_LENGTH) {
                    if (commandSender instanceof Player) {
                        if (Data.approveVote((Player) commandSender, strings[1])){
                            commandSender.sendMessage("投票成功");
                        }else {
                            commandSender.sendMessage("投票失败，id输入有误或任务不存在");
                        }
                    }else {
                        commandSender.sendMessage("必须以玩家身份执行");
                    }
                }
                break;

            case DISAPPROVE:
                if (strings[1] != null && strings.length == TASK_DISAPPROVE_CORRECT_LENGTH) {
                    if (commandSender instanceof Player) {
                        if (Data.disApproveVote((Player) commandSender, strings[1])){
                            commandSender.sendMessage("投票成功");
                        }else {
                            commandSender.sendMessage("投票失败，id输入有误或任务不存在");
                        }
                    }else {
                        commandSender.sendMessage("必须以玩家身份执行");
                    }
                }
                break;

            case LIST:
                if (strings.length == 1) {
                    if (Data.voteTasks != null) {
                        for (VoteTask voteTask : Data.voteTasks) {
                            commandSender.sendMessage("投票id：" + voteTask.getTaskId() + "  投票名：" + voteTask.getTaskName()
                                    + "  执行指令：" + voteTask.getCommand() + "  定时：" + voteTask.getReleaseTime());
                        }
                    }else {
                        commandSender.sendMessage("暂无投票");
                    }

                }
                break;

            case SET:
                break;

            default:
                commandSender.sendMessage("输入有误，请输入/vote 查看使用说明");
        }
        return true;
    }
}
