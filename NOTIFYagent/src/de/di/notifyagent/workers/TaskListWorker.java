package de.di.notifyagent.workers;

import de.di.dokinform.util.DateFormat;
import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.handlers.MailQueueHandler;
import de.di.notifyagent.jobs.Schedulable;
import de.elo.ix.client.Reminder;
import de.elo.ix.client.UserInfo;
import de.elo.ix.client.UserInfoC;
import de.elo.ix.client.UserTask;
import de.elo.ix.client.UserTaskPriorityC;
import de.elo.utils.net.RemoteException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author A. Sopicki
 */
public class TaskListWorker extends Worker {

    private Logger logger = null;
    private int minPriority = UserTaskPriorityC.HIGHEST;
    private int maxPriority = UserTaskPriorityC.HIGHEST;
    private String from;
    private String subject;
    private String messageTemplate = null;

    public TaskListWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);

        logger = Logger.getLogger(getClass());
    }

    @Override
    protected void doWork() {
        if (messageTemplate == null) {
            logger.error("Unable to read template file");
            schedulable.abort();
            shutdown();
            return;
        }

        ELOClient client = schedulable.getEloClient();
        UserInfo[] infos = null;

        try {
            logger.trace("Getting users from ELO");
            infos = client.getGroupMembers(new String[]{settings.getProperty("Application.Groupname")});
            logger.trace("Got " + infos.length + " users from ELO");
        } catch (Exception ex) {
            logger.error("An exception occured while retrieving the users", ex);
            schedulable.abort();
            shutdown();
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

        if (infos != null) {
            for (UserInfo user : infos) {
                logger.trace("------ User: " + user.getName() + " ------");
//                if (user.getType() == UserInfoC.TYPE_GROUP){
//                    logger.debug("ignoring group : " + user.getName());
//                    continue;
//                }
                String[] props = user.getUserProps();

                String email = props[UserInfoC.PROP_NAME_EMAIL];
                String flag = props[PROP_E4];

                if (email != null && email.length() > 0 && flag != null) {

                    String userName = user.getName();
                    logger.trace("Checking for user tasks for user " + userName);

                    java.util.List<UserTask> tasks = null;
                    int numberOfTasks = 0;

                    StringBuffer taskList = null;
                    ELOClient runAs = null;

                    try {
                        java.util.Date isoDate = new java.util.Date();
                        String endDate = DateFormat.toIsoDate(isoDate);
                        runAs = getRunAs(user.getName());
                        tasks = runAs.getTaskList(user.getName(), minPriority,
                                maxPriority, maxWorkflows, endDate);

                        if (tasks.isEmpty()) {
                            logger.trace("User " + userName + " has no active tasks. Skipping.");
                            logger.trace("------ END USER ------");
                            continue;
                        }

                        logger.trace("Got " + tasks.size() + " possible tasks");

                        //sort by highest priority first
                        java.util.Collections.sort(tasks, new PriorityComparator());

                        taskList = new StringBuffer("<ul>");

                        for (UserTask task : tasks) {
                            Reminder reminder = task.getReminder();

                            if (reminder.getReceiverId() != user.getId()
                                    && reminder.getSenderId() != user.getId()) {
                                continue;
                            }
                            ++numberOfTasks;

                            taskList.append("<li>");


                            String promptDate = "";

                            try {
                                promptDate = reminder.getPromptDateIso();

                                if (promptDate.length() > 8) {
                                    promptDate = promptDate.substring(0, 8);
                                }
                                promptDate = DateFormat.toUserDate(DateFormat.fromIsoString(promptDate));
                            } catch (java.text.ParseException pex) {
                                logger.debug("Error parsing due date " + reminder.getPromptDateIso(), pex);
                            }

                            taskList.append(priorityToString(task.getReminder().getPrio()));
                            taskList.append(" - ");
                            taskList.append(promptDate);
                            taskList.append(" - ");
                            if (includeURL) {
                                taskList.append(
                                        toDocUrl(Integer.toString(reminder.getObjId()), reminder.getName()));
                            } else {
                                taskList.append(reminder.getName());
                            }
                            taskList.append("</li>");
                        }

                        taskList.append("</ul>");

                    } catch (RemoteException rex) {
                        logger.warn("Unable to get tasks for user " + user.getName(), rex);
                        logger.trace("------ END USER ------");
                        continue;
                    } catch (Exception ex) {
                        logger.warn("Unable to get tasks for user " + user.getName() + ". Aborting.", ex);
                        break;
                    } finally {
                        try {
                            runAs.logoff();;
                            runAs = null;
                        } catch (Exception ex) {
                        }
                    }

                    if (numberOfTasks == 0) {
                        logger.trace("User " + userName + " has no tasks to report. Skipping.");
                        logger.trace("------ END USER ------");
                        continue;
                    }

                    logger.trace("Found " + numberOfTasks + " tasks for the user");

                    String msgText = messageTemplate;


                    msgText = msgText.replace("{##tasklist##}", taskList);
                    msgText = msgText.replace("{##user##}", user.getName());
                    msgText = msgText.replace("{##timestamp##}", format.format(new java.util.Date()));
                    msgText = msgText.replace("{##numberoftasks##}", Integer.toString(numberOfTasks));

                    try {

                        MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(),
                                email, from, subject);
                        handler.initialDir = new File(settings.getProperty("TaskList.messageTemplate")).getParentFile();
                                
                        handler.handleSchedulable(schedulable);
                    } catch (Exception ex) {
                        logger.error("An exception occured while handling the input files", ex);
                        schedulable.abort();
                        shutdown();
                        return;
                    }
                    logger.trace("------ END USER ------");
                } else {
                    logger.debug("No email notification for " + user.getName());
                    logger.trace("------ END USER ------");
                }
            }
        }

        schedulable.finished();
        shutdown();
    }

    @Override
    protected void init() {
        parsePriorities(settings.getProperty("TaskList.priorities"));

        logger.trace("Min priority: " + minPriority);
        logger.trace("Max priority: " + maxPriority);
        logger.trace("Profile: " + schedulable.getProfile().getProfileName());
        String limit = settings.getProperty("TaskList.maxWorkflows");

        try {
            maxWorkflows = Integer.parseInt(limit);
        } catch (NumberFormatException nfex) {
        }

        subject = settings.getProperty("TaskList.subject");
        from = settings.getProperty("TaskList.from");

        String templateFileName = settings.getProperty("TaskList.messageTemplate");

        messageTemplate = readTemplateFile(new java.io.File(templateFileName));
    }

    private String priorityToString(int priority) {
        if (priority == UserTaskPriorityC.HIGHEST) {
            return "A";
        } else if (priority == UserTaskPriorityC.LOWEST) {
            return "C";
        }

        return "B";
    }

    private void parsePriorities(String property) {
        for (int i = 0; i < property.length(); i++) {
            if (property.charAt(i) == 'A') {
                if (UserTaskPriorityC.HIGHEST < maxPriority) {
                    maxPriority = UserTaskPriorityC.HIGHEST;
                }

                if (minPriority < UserTaskPriorityC.HIGHEST) {
                    minPriority = UserTaskPriorityC.HIGHEST;
                }
            }

            if (property.charAt(i) == 'B') {
                if (UserTaskPriorityC.LOWEST - 1 < maxPriority) {
                    maxPriority = UserTaskPriorityC.LOWEST - 1;
                }

                if (minPriority < UserTaskPriorityC.LOWEST - 1) {
                    minPriority = UserTaskPriorityC.LOWEST - 1;
                }
            }

            if (property.charAt(i) == 'C') {
                if (UserTaskPriorityC.LOWEST < maxPriority) {
                    maxPriority = UserTaskPriorityC.LOWEST;
                }

                if (minPriority < UserTaskPriorityC.LOWEST) {
                    minPriority = UserTaskPriorityC.LOWEST;
                }
            }
        }
    }

    private ELOClient getRunAs(String name) throws RemoteException, IllegalStateException {
        String ix = settings.getProperty("IndexServer.URL");
        String user = settings.getProperty("IndexServer.User");
        String password = settings.getProperty("IndexServer.Password");

        ELOClient eloClient = new ELOClient();
        eloClient.setConnectionUrl(ix);
        eloClient.setUserName(user);
        eloClient.setPassword(password);
        eloClient.setRunAs(name);

        eloClient.login();

        return eloClient;
    }

    private class PriorityComparator implements java.util.Comparator<UserTask> {

        @Override
        public int compare(UserTask o1, UserTask o2) {
            int prio1 = o1.getReminder().getPrio();
            int prio2 = o2.getReminder().getPrio();
            if (prio1 < prio2) {
                return -1;
            } else if (prio1 == prio2) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
