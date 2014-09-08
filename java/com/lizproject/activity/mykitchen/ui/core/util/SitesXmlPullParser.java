package com.lizproject.activity.mykitchen.ui.core.util;

import android.content.Context;
import android.os.Environment;

import com.lizproject.activity.mykitchen.ui.core.presenter.IngredientsUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SitesXmlPullParser {

    static final String KEY_SITE = "ingredient";
    static final String KEY_NAME = "titulo";
    static final String KEY_STATUS = "status";
    static final String KEY_QUANTY = "quanty";
    static final String KEY_CLASS = "class";

    public static List<IngredientsUtil> getStackSitesFromFile(Context ctx) {

        // List of StackSites that we will return
        List<IngredientsUtil> stackSites;

        stackSites = new ArrayList<IngredientsUtil>();

        // temp holder for current StackSite while parsing
        IngredientsUtil curStackSite = null;
        // temp holder for current text value while parsing
        String curText = "";
        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            String path=Environment.getExternalStorageDirectory().getPath()+"/site.xml";

            File f = new File(path);

            if (f.exists()) {
                FileInputStream       fis = ctx.openFileInput("site.xml");
                BufferedReader    reader = new BufferedReader(new InputStreamReader(fis));
                xpp.setInput(reader);


            }
            else {

                InputStream    ist=ctx.getAssets().open("ingre.xml");
                    xpp.setInput(ist, "UTF-8");

            }


            // point the parser to our file.

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {

                // Get the current tag
                String tagname = xpp.getName();

                // React to different event types appropriately
                switch (eventType) {


                    case XmlPullParser.START_TAG:

                        if (tagname.equalsIgnoreCase(KEY_SITE)) {
                            // If we are starting a new <site> block we need
                            //a new StackSite object to represent it
                            curStackSite = new IngredientsUtil();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;


                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(KEY_SITE)) {
                            // if </site> then we are done with current Site
                            // add it to the list.
                            stackSites.add(curStackSite);
                        } else if (tagname.equalsIgnoreCase(KEY_NAME)) {
                            // if </name> use setName() on curSite
                            curStackSite.setName(curText);
                        }
				        else if (tagname.equalsIgnoreCase(KEY_QUANTY)) {
						// if </link> use setLink() on curSite
						curStackSite.setQuanty(curText);
					}
                        else if (tagname.equalsIgnoreCase(KEY_STATUS)) {
                            // if </about> use setAbout() on curSite
                            curStackSite.setStatus(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_CLASS)) {
                            // if </image> use setImgUrl() on curSite
                           curStackSite.setClasstype(curText);
                        }
                        break;

                    default:
                        break;
                }

                //move on to next iteration
                eventType = xpp.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return stackSites;
    }
}

