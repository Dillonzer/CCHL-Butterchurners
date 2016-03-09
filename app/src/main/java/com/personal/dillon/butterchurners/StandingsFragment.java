package com.personal.dillon.butterchurners;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;

/**
 * Created by Dillon on 2016-03-02
 */
public class StandingsFragment extends Fragment {
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //CALL CLASS
        new JsoupRun().execute();
    }



    private class JsoupRun extends AsyncTask<Void, Void, Void> {
        String url = "http://stats.adultrechockey.ca/c-regina/ashnregina/en/stats/statdisplay.php?type=standings&subType=8&season_id=70&tournament_id=0&leagueId=6&division_id=185&confId=0&team_id=3642";
        Elements leftAlign;
        Elements centerAlign;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Standings");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get all the TD
                //td class 'textAlignCenter' = rank;GP;W;L;T;OTL;PTS;WPCT;GF;GA;GPCT;PIMS skip 12 then start
                //td class 'textAlignLeft' = team;IN-DIV;STK;last5 skip 4 then start
                leftAlign = document.getElementsByClass("textAlignLeft");
                centerAlign = document.getElementsByClass("textAlignCenter");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // CODE GOES HERE
            TextView tv_rank = (TextView) getView().findViewById(R.id.tv_rank_standings);
            TextView tv_team = (TextView) getView().findViewById(R.id.tv_team_standings);
            TextView tv_gp = (TextView) getView().findViewById(R.id.tv_gp_standings);
            TextView tv_w = (TextView)  getView().findViewById(R.id.tv_w_standings);
            TextView tv_l = (TextView) getView().findViewById(R.id.tv_l_standings);
            TextView tv_t = (TextView) getView().findViewById(R.id.tv_t_standings);
            TextView tv_otl = (TextView) getView().findViewById(R.id.tv_otl_standings);
            TextView tv_pts = (TextView) getView().findViewById(R.id.tv_pts_standings);
            TextView tv_wpct = (TextView) getView().findViewById(R.id.tv_wpct_standings);
            TextView tv_gf = (TextView) getView().findViewById(R.id.tv_gf_standings);
            TextView tv_ga = (TextView) getView().findViewById(R.id.tv_ga_standings);
            TextView tv_gpct = (TextView) getView().findViewById(R.id.tv_gpct_standings);
            TextView tv_pims = (TextView) getView().findViewById(R.id.tv_pims_standings);
            TextView tv_record = (TextView) getView().findViewById(R.id.tv_record_standings);
            TextView tv_streak = (TextView) getView().findViewById(R.id.tv_strk_standings);
            TextView tv_l5 = (TextView) getView().findViewById(R.id.tv_lastfive_standings);

            String s_rank ="";
            String s_team ="";
            String s_gp ="";
            String s_w = "";
            String s_l ="";
            String s_t ="";
            String s_otl ="";
            String s_pts ="";
            String s_wpct ="";
            String s_gf ="";
            String s_ga ="";
            String s_gpct ="";
            String s_pims ="";
            String s_record ="";
            String s_streak ="";
            String s_l5 ="";

            int ca_count = 0;
            int la_count = 0;

            for(Element ca:centerAlign)
            {
                if(ca_count < 12)
                {
                    ca_count++;
                }else
                {
                    //rank;GP;W;L;T;OTL;PTS;WPCT;GF;GA;GPCT;PIMS
                    switch(ca_count)
                    {
                        case 12:
                            s_rank += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 13:
                            s_gp += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 14:
                            s_w += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 15:
                            s_l += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 16:
                            s_t += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 17:
                            s_otl += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 18:
                            s_pts += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 19:
                            s_wpct += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 20:
                            s_gf += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 21:
                            s_ga += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 22:
                            s_gpct += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 23:
                            s_pims += ca.text() + "\n";
                            ca_count=12;
                            break;
                        default:
                            ca_count=12;
                            break;
                    }
                }
            }

            for(Element la:leftAlign)
            {
                if(la_count < 4)
                {
                    la_count++;
                } else
                {
                    switch(la_count)
                    {
                        //team;IN-DIV;STK;last5
                        case 4:
                            s_team += la.text() + "\n";
                            la_count++;
                            break;
                        case 5:
                            s_record += la.text() + "\n";
                            la_count++;
                            break;
                        case 6:
                            s_streak += la.text() + "\n";
                            la_count++;
                            break;
                        case 7:
                            s_l5 += la.text() + "\n";
                            la_count=4;
                            break;
                        default:
                            la_count=4;
                            break;
                    }
                }
            }

            tv_rank.setText(s_rank);
            tv_team.setText(s_team);
            tv_gp.setText(s_gp);
            tv_w.setText(s_w);
            tv_l.setText(s_l);
            tv_t.setText(s_t);
            tv_otl.setText(s_otl);
            tv_pts.setText(s_pts);
            tv_wpct.setText(s_wpct);
            tv_gf.setText(s_gf);
            tv_ga.setText(s_ga);
            tv_gpct.setText(s_gpct);
            tv_pims.setText(s_pims);
            tv_record.setText(s_record);
            tv_streak.setText(s_streak);
            tv_l5.setText(s_l5);


            mProgressDialog.dismiss();
        }
    }
}


