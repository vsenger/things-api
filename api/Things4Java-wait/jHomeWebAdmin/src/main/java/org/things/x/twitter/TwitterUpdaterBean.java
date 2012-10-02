package org.things.x.twitter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.things.x.component.Sensor;
import java.util.Collection;
import java.util.HashSet;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import org.things.Things;
import twitter4j.*;

/**
 *
 * @author vsenger
 */
@Singleton
@Startup
public class TwitterUpdaterBean {

  boolean i;
  public String CONFERENCE_NAME = "profissaojava";
  final Object LOCK = new Object();
  HashSet tweetList = new HashSet<Tweet>();
  Twitter twitter = null;
  User user = null;
  @EJB
  private Things deviceManager;
  @EJB
  private Sensor environment;
  @Resource
  TimerService timerService;
  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")

  public void init() {
    Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.INFO, "Starting jHome Twitter Module");

    twitter = new TwitterFactory().getInstance();
    i = true;
    /*try {
      user = twitter.verifyCredentials();
      i = true;
      //startUpdater(15000);
    } catch (TwitterException ex) {
      Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.SEVERE,
              "Error connecting to Twitter:" + ex.getMessage(), ex);
    }*/
  }


  @PreDestroy
  public void stopTimer() {
    Collection<Timer> timers = timerService.getTimers();
    for (Timer t : timers) {
      t.cancel();
    }
  }

  public boolean check(Tweet tweet) {
    if (tweetList.contains(tweet)) {
      return false;
    } else {
      tweetList.add(tweet);
      return true;
    }
  }

  @Timeout
  private void execute(Timer timer) {
    if (!i) {
      init();
    }
    //List<Status> statuses;
    List<Tweet> statuses;
    try {
      //statuses = twitter.getMentions();
      statuses = twitter.search(new Query("@jhomeautomation")).getTweets();
      
    } catch (TwitterException ex) {
      Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.SEVERE, "Error reading twitter mentions: " + ex.getMessage(), ex);
      return;
    }
    Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.INFO, "Checking Twitter Messages");

    for (Tweet tweet : statuses) {
      //Tweet tweet = new Tweet(status.getCreatedAt(),
      //        status.getUser().getScreenName(),
      //        status.getText());
      if (check(tweet)) {
        /*Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.INFO,
                "New tweet: " + status.getCreatedAt() + " - "
                + status.getUser().getScreenName() + status.getText());*/
        try {
          if (tweet.getText().toLowerCase().indexOf(CONFERENCE_NAME) == 0) {
            return;
          }
          if (tweet.getText().toLowerCase().indexOf("desligar liquidificador") != -1) {
            deviceManager.execute("relay1", "0");
          } else if (tweet.getText().toLowerCase().indexOf("ligar liquidificador") != -1) {
            deviceManager.execute("relay1", "1");
          } else if (tweet.getText().toLowerCase().indexOf("desligar lampada2") != -1) {
            deviceManager.execute("relay2", "0");
          } else if (tweet.getText().toLowerCase().indexOf("ligar lampada2") != -1) {
            deviceManager.execute("relay2", "1");
          } else if (tweet.getText().toLowerCase().indexOf("desligar vermelho") != -1) {
            deviceManager.execute("red", "0");
          } else if (tweet.getText().toLowerCase().indexOf("desligar verde") != -1) {
            deviceManager.execute("green", "0");
          } else if (tweet.getText().toLowerCase().indexOf("desligar azul") != -1) {
            deviceManager.execute("blue", "0");
          } else if (tweet.getText().toLowerCase().indexOf("ligar vermelho") != -1) {
            deviceManager.execute("red", "255");
          } else if (tweet.getText().toLowerCase().indexOf("ligar verde") != -1) {
            deviceManager.execute("green", "255");
          } else if (tweet.getText().toLowerCase().indexOf("ligar azul") != -1) {
            deviceManager.execute("blue", "255");
          }
        } catch (Exception ex) {
          Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.SEVERE, "Error executing jHome command " + ex.getMessage(), ex);
        }
      }
    }
  }

  public void startUpdater(long interval) {
    Logger.getLogger(TwitterUpdaterBean.class.getName()).log(Level.INFO,
            "Initializing TwitterUpdater. I'm going to check each " + interval / 1000 + "seconds");

    timerService.createTimer(
            0, interval, "");
  }
}
