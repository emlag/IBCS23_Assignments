package edu.cis.pset1_twitteranalysis.twitter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.io.PrintStream;

public class TwitterController
{
    private Twitter twitter;
    private ArrayList<Status> statuses;
    private ArrayList<String> tokens;
    private HashMap<String, Integer> wordCounts;
    ArrayList<String> commonWords;
    private String popularWord;
    private int frequencyMax;
    Context context;

    public TwitterController(Context currContext)
    {
        context = currContext;

        ConfigurationBuilder cb =  new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("**********")
                .setOAuthConsumerSecret("********************************")
                .setOAuthAccessToken("********************************")
                .setOAuthAccessTokenSecret("********************************s");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        statuses = new ArrayList<Status>();
        tokens = new ArrayList<String>();
        wordCounts = new HashMap<>();
        commonWords = new ArrayList<String>();
        getCommonWords();
    }

    /********** PART 1 *********/

    //can be used to get common words from the commonWords txt file
    public void getCommonWords() {

        try {
            AssetManager am = context.getAssets();

            //this file can be found in src/main/assets
            InputStream myFile = am.open("commonWords.txt");
            Scanner sc = new Scanner(myFile);
            while (sc.hasNextLine()) {
                commonWords.add(sc.nextLine());
            }
        } catch (Exception err) {
            Log.d("COMMON_WORDS", err.toString());
        }
    }

    public String postTweet(String message)
    {
        String statusTextToReturn = "";
        try
        {
            Status status = twitter.updateStatus(message);
            statusTextToReturn = status.getText();
        }
        catch (TwitterException e){
            System.out.println(e.getErrorMessage());
        }
        return statusTextToReturn;
    }

    // Example query with paging and file output.
    private void fetchTweets(String handle)
    {


        //Create a twitter paging object that will start at page 1 and hole 200 entries per page.
        Paging page = new Paging(1, 200);

        //Use a for loop to set the pages and get the necessary tweets.
        for (int i = 1; i <= 10; i++)
        {
            page.setPage(i);

            /* Ask for the tweets from twitter and add them all to the statuses ArrayList.
            Because we set the page to receive 200 tweets per page, this should return
            200 tweets every request. */
            try{
                statuses.addAll(twitter.getUserTimeline(handle, page));
            }
            catch (Exception err)
            {
                Log.d("fetchTweets", "could not get user timeline");
            }
        }

        //Write to the file a header message. Useful for debugging.
        int numberOfTweetsFound = statuses.size();
        System.out.println("Number of Tweets Found: " + numberOfTweetsFound);

        //Use enhanced for loop to print all the tweets found.
        int count = 1;
        for (Status tweet : statuses)
        {
            System.out.println(count+". "+tweet.getText());
            count++;
        }
    }

    /********** PART 2 *********/

    /*
     * TODO 2: this method splits a whole status into different words. Each word
     * is considered a token. Store each token in the "tokens" arrayList
     * provided. Loop through the "statuses" ArrayList.
     */
    private void splitIntoWords()
    {

    }


    /*
     * TODO 3: return a word after removing any punctuation and turn to lowercase from it.
     * If the word is "Edwin!!", this method should return "edwin".
     * We'll need this method later on.
     * If the word is a common word, return null
     */
    @SuppressWarnings("unchecked")
    private String cleanOneWord(String word)
    {
        return "";
    }


    /*
     * TODO 4: loop through each word, get a clean version of each word
     * and save the list with only clean words.
     */
    @SuppressWarnings("unchecked")
    private void createListOfCleanWords()
    {

    }

    /*
     * TODO 5: count each clean word using. Use the frequentWords Hashmap.
     */
    @SuppressWarnings("unchecked")
    private void countAllWords()
    {

    }


    //TODO 6: return the most frequent word's string in any appropriate format
    @SuppressWarnings("unchecked")
    public String getTopWord()
    {
        return "";

    }

    //TODO 7: return the most frequent word's count as an integer.
    @SuppressWarnings("unchecked")
    public int getTopWordCount()
    {
        return 0;
    }


    public String findUserStats(String handle)
    {
        /*
         * TODO 8: you put it all together here. Call the functions you
         * finished in TODO's 2-7. They have to be in the correct order for the
         * program to work.
         * Remember to use .clear() method on collections so that
         * consecutive requests don't count words from previous requests.
         */
        return "";
    }

    /*********** PART 3 **********/

    //TODO 9: Create your own method that recommends possible teaching candidates.

    // Example: A method that returns 100 tweets from keyword(s).
    public List<Status> searchKeywords(String keywords)
    {
        //Use the Query object from Twitter
        Query query = new Query(keywords);
        query.setCount(100);
        query.setSince("2015-12-1");

        //create an ArrayList to store results, which will be of type Status
        List<Status> searchResults = new ArrayList<>();
        try
        {
            //we try to get the results from twitter
            QueryResult result = twitter.search(query);
            searchResults = result.getTweets();
        }
        catch (TwitterException e)
        {
            //if an error happens, like the connection is interrupted,
            //we print the error and return an empty ArrayList
            e.printStackTrace();
        }
        return searchResults;
    }
}