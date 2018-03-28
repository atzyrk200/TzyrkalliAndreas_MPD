package atzyrk200.com.rssfeeds;

/**
 * Created by Tzyrkally Andreas S1435553
 */

public class RssFeedModel {

    public String title;
    public String description;
    public String link;
    public String georss;
    public String author;
    public String comments;
    public String pubDate;

    public RssFeedModel(String title, String link, String description, String georss, String  author, String comments, String pubDate ) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.georss = georss;
        this.author = author;
        this.comments = comments;
        this.pubDate = pubDate;
    }
 public void setTitle(String atitle) {
    atitle = title;
}

    public String getTitle(){
        return title;
    }
//

    public void setDescription(String adescription) {
        adescription = description;
    }

    public  String getDescription(){
        return  description;
    }

    public void setLink(String alink) {
       alink = link;
    }

    public String getLink(){
        return link;
    }

    public void setGeorss(String ageorss) {
        ageorss = georss;
    }

    public  String getGeorss(){
        return georss;
    }

    public void setAuthor(String aauthor){
        aauthor = author;
    }

    public String getAuthor(){
        return  author;
    }
    public void  setComments(String acomments){
        acomments = comments;
    }

    public String getComments(){
        return  comments;
    }

    public  void setPubDate(String apubDate){
        apubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }
public String toString()
{
    String temp;

    temp = title + " " + link + " " + description + " " + georss + " " +author + " " +comments + " " +pubDate;

    return temp;
}
}
