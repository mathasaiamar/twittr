import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterAnalyticsMain {

	public static void main(String[] args) {
		
		if(args.length <= 0)
		{
			System.out.println("Invalid number of arguments. Enter keywords to fetch");
			System.exit(0);
		}
		
		String keyword = args[0];
		
		ConfigurationBuilder cf=new ConfigurationBuilder();
		cf.setDebugEnabled(true)
		  .setOAuthConsumerKey("dLD7LTX5ZkiFetexSGgE6W12f")
		  .setOAuthConsumerSecret("iM5VkX5ZVM07lqh98jezUL63pxd8StwWkhzua5uESzDAribab4")
		  .setOAuthAccessToken("751374875940401153-oUsmJcFmo8m1uWL9USbAS8VxoYqPH4a")
		  .setOAuthAccessTokenSecret("Rpsf6fJ3Tdm8TcP5SvVewIBBlDCWJTSIne4mXiQJ7W67E");
		TwitterFactory tf=new TwitterFactory(cf.build());
		twitter4j.Twitter twitter=tf.getInstance();
		
		List<TweetData> tweets = new ArrayList<TweetData>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date dateBefore7Days = cal.getTime();
	        
        QueryResult result;
		try {
			Query query = new Query(keyword + " since:" + dateBefore7Days);
			result = twitter.search(query);
			do {
				List<Status> statuses = result.getTweets();
//				System.out.println(statuses.size());
			    for (Status status : statuses) {
			    	TweetData tweet = new TweetData();
			    	tweet.setTweet(status.getText());
			    	tweet.setUser(status.getUser().getScreenName());
			    	tweet.setLocation(status.getGeoLocation());
			    	tweets.add(tweet);
//		            System.out.println(status.getId());
//		        	System.out.println("@" + status.getUser().getScreenName() + " : " + status.getText() + " : " + status.getGeoLocation());
		        }
				System.out.println("-------" + statuses.get(statuses.size()-1).getId());
		        query.setMaxId(statuses.get(statuses.size()-1).getId()-1);
//			        System.out.println(query.getMaxId());
		        result = twitter.search(query);
			} while(result != null && result.getTweets().size() > 0);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		System.out.println(tweets.size());

	}

}
