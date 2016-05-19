package com.segment.analytics.android.integrations.comscore;

import com.comscore.analytics.comScore;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;


public class ComScoreIntegration extends Integration<comScore> {
  public static final Factory FACTORY = new Factory() {
    @Override public Integration<?> create(ValueMap settings, Analytics analytics) {
      return new ComScoreIntegration(analytics, settings);
    }

    @Override public String key() {
      return COMSCORE_KEY;
    }
  };

  private static final String COMSCORE_KEY = "ComScore";
  final Logger logger;
  String customerC2;
  String publisherSecret;
  String appName;
  boolean useHTTPS;
  int autoUpdateInterval;
  String autoUpdateMode;

  ComScoreIntegration(Analytics analytics, ValueMap settings) {
    logger = analytics.logger(COMSCORE_KEY);
    customerC2 = settings.getString("customerC2");
    publisherSecret = settings.getString("publisherSecret");
    appName = settings.getString("appName");
    useHTTPS = settings.getBoolean("useHTTPS", true);
    autoUpdateInterval = settings.getInt("autoUpdateInterval", 60);
    autoUpdateMode = settings.getString("autoUpdateMode");

    comScore.setAppContext(analytics.getApplication());
    logger.verbose("comScore.setAppContext(analytics.getApplication())");
    comScore.setCustomerC2(customerC2);
    comScore.setPublisherSecret(publisherSecret);
    comScore.setAppName(appName);
    comScore.setSecure(useHTTPS);
    if (autoUpdateMode != null) {
      if (autoUpdateMode.equals("foreground")) {
        comScore.enableAutoUpdate(autoUpdateInterval, true);
      } else if (autoUpdateMode.equals("background")) {
        comScore.enableAutoUpdate(autoUpdateInterval, false);
      } else {
        comScore.disableAutoUpdate();
      }
    }
  }
}
