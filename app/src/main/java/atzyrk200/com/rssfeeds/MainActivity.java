package atzyrk200.com.rssfeeds;
/**
 * Created by Tzyrkally Andreas S1435553
 */

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,View.OnClickListener {

    private static final String TAG = "MainActivity";

    //date picker
    Button tv;
    Calendar mCurrentDate;
    int day, month, year;


    private View topView;
    private TextView textView1;
    private PopupMenu popupMenu;
    private TextView anchorView;
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;

    private RecyclerView mRecyclerView;
    private EditText search, mEditText;

    private SwipeRefreshLayout mSwipeLayout;
    private TextView mFeedTitleTextView;
    private TextView mFeedLinkTextView;
    private TextView mFeedDescriptionTextView;
    private TextView mFeedLastBuildDateTextView;


    private List<RssFeedModel> mFeedModelList;
    private List<String> list = new ArrayList<String>();
    public RssFeedListAdapter mAdapter;
    private String mFeedTitle;
    private String mFeedLink;
    private String mFeedDescription;
    private String mFeedLastBuildDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (Button) findViewById(R.id.set);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mAdapter = new RssFeedListAdapter(list, this);
//        mRecyclerView.setAdapter(mAdapter);


//        search = (EditText) findViewById(R.id.search);
        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month = month + 1;


        tv.setText(day + "/" + month + "/" + year);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        tv.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }

        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mEditText = (EditText) findViewById(R.id.rssFeedEditText);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mFeedTitleTextView = (TextView) findViewById(R.id.feedTitle);
        mFeedDescriptionTextView = (TextView) findViewById(R.id.feedDescription);
        mFeedLastBuildDateTextView = (TextView) findViewById(R.id.feedLastBuildDate);
        mFeedLinkTextView = (TextView) findViewById(R.id.feedLink);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        topView = (View) findViewById(R.id.topView);
        textView1 = (TextView) findViewById(R.id.textView1);
        registerForContextMenu(textView1);

        popupMenu = new PopupMenu(this, findViewById(R.id.textView2));
        popupMenu.getMenu().add(Menu.NONE, ONE, Menu.NONE, "Light salmon");
        popupMenu.getMenu().add(Menu.NONE, TWO, Menu.NONE, "Light green");
        popupMenu.getMenu().add(Menu.NONE, THREE, Menu.NONE, "Light blue");
        popupMenu.setOnMenuItemClickListener(this);

        anchorView = (TextView) findViewById(R.id.anchor);
        anchorView.setOnClickListener(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });
    }

    @Override
    public void onClick(View v) {
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            //message
            case R.id.red:
                Toast.makeText(this, "Planned RoadWorks", Toast.LENGTH_LONG).show();
                new FetchFeedTaskPlanned().execute((Void) null);

                break;
            case R.id.green:
                Toast.makeText(this, "Current Incidents", Toast.LENGTH_LONG).show();
                new FetchFeedTask().execute((Void) null);
                break;
            case R.id.clear:
                Toast.makeText(this, "Search ", Toast.LENGTH_LONG).show();
                Clear();

                break;
            case R.id.purple:
                Toast.makeText(this, "Pick A Date", Toast.LENGTH_LONG).show();
                pickDate();
                break;

        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Colour Context Menu");
        menu.add(0, v.getId(), 0, "Red");
        menu.add(0, v.getId(), 0, "Green");
        menu.add(0, v.getId(), 0, "Blue");
        menu.add(0, v.getId(), 0, "Yellow");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Red") {
            Toast.makeText(this, "Red Option", Toast.LENGTH_LONG).show();
            textView1.setBackgroundColor(Color.RED);
        } else if (item.getTitle() == "Green") {
            textView1.setBackgroundColor(Color.GREEN);
        } else if (item.getTitle() == "Blue") {
            textView1.setBackgroundColor(Color.BLUE);
        } else if (item.getTitle() == "Yellow") {
            textView1.setBackgroundColor(Color.YELLOW);
        } else
            return false;

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView tv = (TextView) findViewById(R.id.selection);
        switch (item.getItemId()) {
            case ONE:
                tv.setText("Light Salmon");
                anchorView.setBackgroundResource(R.color.LightSalmon);
                break;
            case TWO:
                tv.setText("Light Green");
                anchorView.setBackgroundResource(R.color.LightGreen);
                break;
            case THREE:
                tv.setText("Light Blue");
                anchorView.setBackgroundResource(R.color.LightBlue);
                break;
        }
        return false;
    }

    public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        String georss = null;
        String author = null;
        String comments = null;
        String pubDate = null;
        String language = null;
        String copyright = null;
        String managingEditor = null;
        String webMaster = null;
        String lastBuildDate = null;

        String docs = null;
        String rating = null;
        String generator = null;
        String ttl = null;

        boolean isItem = false;
        List<RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);


            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;

                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                } else if (name.equalsIgnoreCase("georss:point")) {
                    georss = result;
                } else if (name.equalsIgnoreCase("author")) {
                    author = result;
                } else if (name.equalsIgnoreCase("comments")) {
                    comments = result;
                } else if (name.equalsIgnoreCase("pubDate")) {
                    pubDate = result;
                } else if (name.equalsIgnoreCase("lastBuildDate")) {
                    lastBuildDate = result;
                }
//                else if (name.equalsIgnoreCase("docs")) {
//                    docs = result;
//                } else if (name.equalsIgnoreCase("rating")) {
//                    rating = result;
//                } else if (name.equalsIgnoreCase("generator")) {
//                    generator = result;
//                } else if (name.equalsIgnoreCase("ttl")) {
//                    ttl = result;
//                }


                if (title != null && link != null && description != null) {
                    if (isItem) {
                        RssFeedModel item = new RssFeedModel(title, link, description, georss, author, comments, pubDate);
                        items.add(item);
                    } else {
                        mFeedTitle = title;
                        mFeedLink = link;
                        mFeedDescription = description;
//                        mFeedGeorss = georss;
//                        mFeedAuthor = author;
//                        mFeedComments = comments;
//                        mFeedPubDate = pubDate;
                        mFeedLastBuildDate = lastBuildDate;
                    }

                    title = null;
                    link = null;
                    description = null;
                    georss = null;
                    author = null;
                    comments = null;
                    pubDate = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

    private void Clear() {

        mFeedTitleTextView.setText("");
        mFeedDescriptionTextView.setText("");
        mFeedLinkTextView.setText("");
        mFeedLastBuildDateTextView.setText("");
        tv.setVisibility(View.INVISIBLE);
    }

    public void pickDate() {
        tv.setVisibility(View.VISIBLE);


    }


    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;


        @Override
        protected void onPreExecute() {

            tv.setVisibility(View.INVISIBLE);
            mSwipeLayout.setRefreshing(true);
            mFeedTitle = null;
            mFeedLink = null;
            mFeedDescription = null;
            mFeedLastBuildDate = null;
            mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
            mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
            mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
            mFeedLastBuildDateTextView.setText("Last Build Date: " + mFeedLastBuildDate);
            urlLink = "http://trafficscotland.org/rss/feeds/currentincidents.aspx";

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);


                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
                mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
                mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
                mFeedLastBuildDateTextView.setText("Last Build Date: " + mFeedLastBuildDate);
                // Fill RecyclerView
                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
            } else {
                Toast.makeText(MainActivity.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    private class FetchFeedTaskPlanned extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;


        @Override
        protected void onPreExecute() {
            tv.setVisibility(View.INVISIBLE);
            mSwipeLayout.setRefreshing(true);
            mFeedTitle = null;
            mFeedLink = null;
            mFeedDescription = null;
            mFeedLastBuildDate = null;
            mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
            mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
            mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
            mFeedLastBuildDateTextView.setText("Last Build Date: " + mFeedLastBuildDate);
            urlLink = "http://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
                mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
                mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
                mFeedLastBuildDateTextView.setText("Last Build Date " + mFeedLastBuildDate);
                // Fill RecyclerView
                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));

            } else {
                Toast.makeText(MainActivity.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}



//
//    public void addTextListener(){
//
//        search.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {}
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence query, int start, int before, int count) {
//
//                query = query.toString().toLowerCase();
//
//                final List<String> filteredList = new ArrayList<>();
//
//                for (int i = 0; i < list.size(); i++) {
//
//                    final String text = list.get(i).toLowerCase();
//                    if (text.contains(query)) {
//
//                        filteredList.add(list.get(i));
//                    }
//                }
//
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                mAdapter = new RssFeedListAdapter(filteredList, MainActivity.this);
//                mRecyclerView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();  // data set changed
//            }
//        });
//    }
//}