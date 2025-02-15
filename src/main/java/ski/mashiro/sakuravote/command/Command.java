package ski.mashiro.sakuravote.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ski.mashiro.sakuravote.message.PluginMessage;
import ski.mashiro.sakuravote.storage.Data;
import ski.mashiro.sakuravote.storage.VoteInFile;
import ski.mashiro.sakuravote.timer.Timer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

/**
 * @author MashiroT
 */
public class Command implements CommandExecutor, TabCompleter {

    private static final String CREATE = "create";
    private static final String CONDITIONAL_VOTE_CREATE = "condcreate";
    private static final String DEL = "del";
    private static final String DELETE = "delete";
    private static final String APPROVE = "approve";
    private static final String DISAPPROVE = "disapprove";
    private static final String LIST = "list";
    private static final String SET = "set";
    private static final String CONDITIONAL_VOTE_SET = "condset";
    private static final String CANCEL = "cancel";
    private static final String RELOAD = "reload";
    private static final int TASK_NAME = 1;
    private static final int TASK_ID = 2;
    private static final int TASK_COMMAND = 3;
    private static final int TASK_RELEASE_TIME = 4;
    private static final int TASK_EFFECT_TIME = 5;
    private static final int TASK_CREATE_CORRECT_LENGTH = 6;
    private static final int CONDITIONAL_VOTE_PLAYER_NUM = 4;
    private static final int CONDITIONAL_VOTE_CORRECT_LENGTH = 6;
    private static final int TASK_DELETE_ID = 1;
    private static final int TASK_DELETE_CORRECT_LENGTH = 2;
    private static final int TASK_APPROVE_CORRECT_LENGTH = 2;
    private static final int TASK_DISAPPROVE_CORRECT_LENGTH = 2;
    private static final int LIST_TYPE = 1;
    private static final int LIST_CORRECT_LENGTH = 2;
    private static final int TASK_MODIFY_ID = 1;
    private static final int TASK_MODIFY_TYPE = 2;
    private static final int TASK_MODIFY_VALUE = 3;
    private static final int TASK_MODIFY_CORRECT_LENGTH = 4;
    private static final int TASK_CANCEL_ID = 1;
    private static final int TASK_CANCEL_CORRECT_LENGTH = 2;
    private static final int RELOAD_CORRECT_LENGTH = 1;
    public static final String LIST_TYPE_GOING = "going";
    public static final String LIST_TYPE_ALL = "all";
    public static final String PERMISSION_ADMIN_ALL = "vote.*";
    public static final String PERMISSION_ADMIN_CREATE = "vote.admin.create";
    public static final String PERMISSION_ADMIN_DELETE = "vote.admin.delete";
    public static final String PERMISSION_ADMIN_SET = "vote.admin.set";
    public static final String PERMISSION_ADMIN_CANCEL = "vote.admin.cancel";
    public static final String PERMISSION_ADMIN_RELOAD = "vote.admin.reload";
    public static final String PERMISSION_COMMON_LIST_ALL = "vote.list.all";
    public static final String PERMISSION_COMMON_LIST_GOING = "vote.list.going";
    public static final String PERMISSION_COMMON_APPROVE = "vote.approve";
    public static final String PERMISSION_COMMON_DISAPPROVE = "vote.disapprove";

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (strings.length == 0) {
            PluginMessage.showHelp(commandSender);
            return true;
        }
        String cmd = strings[0];
        switch (cmd.toLowerCase()) {
            case CREATE:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_CREATE)) {
                    try {
                        if (strings[TASK_NAME] != null && strings[TASK_ID]!= null && strings[TASK_COMMAND] != null
                                && strings[TASK_RELEASE_TIME] != null && strings[TASK_EFFECT_TIME] != null && strings.length == TASK_CREATE_CORRECT_LENGTH) {
                            if (Data.addVote(strings[TASK_NAME], strings[TASK_ID], strings[TASK_COMMAND], strings[TASK_RELEASE_TIME], strings[TASK_EFFECT_TIME])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票创建成功，id：" + strings[TASK_ID]);
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票创建失败，可能原因：[投票id]为数字，[投票id]重复，[投票名]重复，时间格式错误");
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "请输入/vote 查看使用说明");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            case CONDITIONAL_VOTE_CREATE:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_CREATE)) {
                    try {
                        if (strings[TASK_NAME] != null && strings[TASK_ID]!= null && strings[TASK_COMMAND] != null
                                && strings[CONDITIONAL_VOTE_PLAYER_NUM] != null && strings[TASK_EFFECT_TIME] != null && strings.length == CONDITIONAL_VOTE_CORRECT_LENGTH) {
                            if (Data.addConditionalVote(strings[TASK_NAME], strings[TASK_ID], strings[TASK_COMMAND], strings[CONDITIONAL_VOTE_PLAYER_NUM], strings[TASK_EFFECT_TIME])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票创建成功，id：" + strings[TASK_ID]);
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票创建失败，可能原因：[投票id]为数字，[投票id]重复，[投票名]重复");
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "请输入/vote 查看使用说明");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            case DEL:
            case DELETE:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_DELETE)) {
                    try {
                        if (strings[TASK_DELETE_ID] != null && strings.length == TASK_DELETE_CORRECT_LENGTH) {
                            if (Data.delVote(strings[TASK_DELETE_ID])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票删除成功");
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + "删除失败，id输入有误或任务不存在");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            case APPROVE:
                try {
                    if (strings[1] != null && strings.length == TASK_APPROVE_CORRECT_LENGTH) {
                        if (commandSender instanceof Player) {
                            if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_COMMON_APPROVE)) {
                                if (Data.approveVote((Player) commandSender, strings[1])){
                                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票成功");
                                } else {
                                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票失败，id输入有误或投票未开始");
                                }
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + "必须以玩家身份执行");
                        }
                    } else {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } catch (Exception e) {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                }
                break;

            case DISAPPROVE:
                try {
                    if (strings[1] != null && strings.length == TASK_DISAPPROVE_CORRECT_LENGTH) {
                        if (commandSender instanceof Player) {
                            if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_COMMON_DISAPPROVE)) {
                                if (Data.disApproveVote((Player) commandSender, strings[1])){
                                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票成功");
                                } else {
                                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "投票失败，id输入有误或投票未开始");
                                }
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "必须以玩家身份执行");
                        }
                    } else {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } catch (Exception e) {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                }
                break;

            case LIST:
                try {
                    if (strings[LIST_TYPE] != null && strings.length == LIST_CORRECT_LENGTH) {
                        if (!Data.showList(strings[LIST_TYPE], commandSender)) {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } else {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } catch (Exception e) {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                }
                break;

            case SET:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_SET)) {
                    try {
                        if (strings[TASK_MODIFY_ID] != null && strings[TASK_MODIFY_TYPE] != null &&
                                strings[TASK_MODIFY_VALUE] != null && strings.length == TASK_MODIFY_CORRECT_LENGTH) {
                            if (Data.modifyVote(strings[TASK_MODIFY_ID], strings[TASK_MODIFY_TYPE], strings[TASK_MODIFY_VALUE])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "修改成功");
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "修改失败，请重试");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;
            case CONDITIONAL_VOTE_SET:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_SET)) {
                    try {
                        if (strings[TASK_MODIFY_ID] != null && strings[TASK_MODIFY_TYPE] != null &&
                                strings[TASK_MODIFY_VALUE] != null && strings.length == TASK_MODIFY_CORRECT_LENGTH) {
                            if (Data.modifyConditionalVote(strings[TASK_MODIFY_ID], strings[TASK_MODIFY_TYPE], strings[TASK_MODIFY_VALUE])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "修改成功");
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "修改失败，请重试");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            case CANCEL:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_CANCEL)) {
                    try {
                        if (strings[TASK_CANCEL_ID] != null && strings.length == TASK_CANCEL_CORRECT_LENGTH) {
                            if (Timer.cancelTask(strings[TASK_CANCEL_ID])) {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "取消投票成功");
                            } else {
                                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "取消失败，原因可能为：不存在该投票, 投票已被取消, 该投票已过期");
                            }
                        } else {
                            commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                        }
                    } catch (Exception e) {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            case RELOAD:
                if (commandSender.hasPermission(PERMISSION_ADMIN_ALL) || commandSender.hasPermission(PERMISSION_ADMIN_RELOAD)) {
                    if (strings.length == RELOAD_CORRECT_LENGTH) {
                        Data.reloadTaskAndConfig();
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + AQUA + "重载成功");
                    } else {
                        commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                    }
                } else {
                    commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "权限不足");
                }
                break;

            default:
                commandSender.sendMessage(GREEN + "[SakuraVote] " + DARK_AQUA + "输入有误，请输入/vote 查看使用说明");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {

        if (strings.length == 0) {
            return null;
        }

        if (strings.length == 1) {
            String[] mainCommand = {CREATE, DELETE, SET, LIST, APPROVE, DISAPPROVE, CANCEL, CONDITIONAL_VOTE_CREATE, CONDITIONAL_VOTE_SET};
            return Arrays.stream(mainCommand).filter(str -> str.startsWith(strings[0])).collect(Collectors.toList());
        }

        if (strings[0].equalsIgnoreCase(LIST) && strings.length == LIST_TYPE + 1) {
            String[] listType = {LIST_TYPE_ALL, LIST_TYPE_GOING};
            return Arrays.stream(listType).filter(str -> str.startsWith(strings[1])).collect(Collectors.toList());
        }

        if (strings[0].equalsIgnoreCase(SET) && strings.length == TASK_MODIFY_TYPE + 1) {
            String[] setType = {VoteInFile.NAME, VoteInFile.COMMAND, VoteInFile.RELEASE_TIME, VoteInFile.EFFECT_TIME, VoteInFile.REUSE};
            return Arrays.stream(setType).filter(str -> str.startsWith(strings[2])).collect(Collectors.toList());
        }

        if (strings[0].equalsIgnoreCase(CONDITIONAL_VOTE_SET) && strings.length == TASK_MODIFY_TYPE + 1) {
            String[] setType = {VoteInFile.NAME, VoteInFile.COMMAND, VoteInFile.PLAYER_NUM, VoteInFile.EFFECT_TIME, VoteInFile.AUTOSTART};
            return Arrays.stream(setType).filter(str -> str.startsWith(strings[2])).collect(Collectors.toList());
        }

        return null;
    }
}
